package sample.json.performance;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map;
import java.util.HashMap;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.commons.io.FilenameUtils;

import sample.json.performance.model.ArchiveCommentModel;
import sample.json.performance.model.TimestampModel;
import sample.json.performance.util.Util;
import sample.json.performance.task.DoubleCheckTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;

import java.io.IOException;

/**
 * resources配下のjsonファイルを読み込む
 */
public class App {
  // public static final int GENARATE_THREAD_COUNT = 10;

  private static ObjectMapper objectMapper = new ObjectMapper();
  
  public static void main( String[] args ) throws IOException {
    List<Path> jsonFileList = Files.walk(Paths.get(System.getProperty("user.dir"), "src", "main", "resources"))
                                   .filter(path -> !Files.isDirectory(path))
                                   .collect(Collectors.toList());
    // singleThread(jsonFileList);
    multiThread(jsonFileList, Integer.parseInt(args[0]));
  }
  
  /**
   * jsonファイルを読み込んで重複チェックを行う。
   * ファイル単位でマルチスレッド処理する。
   * @param List<Path> jsonファイルパスのリスト
   */ 
  public static void multiThread(List<Path> jsonFileList, int threadCount) throws IOException {

    int fileCount = 0;
    Map<String, List<ArchiveCommentModel>> fileListMap = new HashMap<String, List<ArchiveCommentModel>>();
    for(Path jsonFilePath : jsonFileList) {
      if(App.isContinue(jsonFilePath)){
        continue;
      }

      fileListMap.put(
        jsonFilePath.toString(),
        objectMapper.readValue(jsonFilePath.toFile(), new TypeReference<List<ArchiveCommentModel>>(){}));
        fileCount++;
    }

    ExecutorService exec = Executors.newFixedThreadPool(threadCount);
    CountDownLatch countDownLatch = new CountDownLatch(fileCount);

    System.out.println(
      String.format("CountDownLatch count:%d",countDownLatch.getCount()));

    for(Map.Entry<String, List<ArchiveCommentModel>> entry : fileListMap.entrySet()) {
      exec.submit(
        new DoubleCheckTask(
          new TimestampModel(entry.getKey(), entry.getValue(), countDownLatch)));
    }
    
    try {
      countDownLatch.await();
      exec.shutdown();
      if( !exec.awaitTermination(60, TimeUnit.SECONDS) ){
        exec.shutdownNow();
        if(!exec.awaitTermination(60, TimeUnit.SECONDS)){
          System.err.println("cannot shutdown");
        }
      }
    } catch (InterruptedException e){
      exec.shutdownNow();
      Thread.currentThread().interrupt();
    }
    
  }
  
  // /**
  // * jsonファイルを読み込んで重複チェックを行う。
  // * @param List<Path> jsonファイルパスのリスト
  // */ 
  // public static void singleThread(List<Path> jsonFileList) throws IOException  {
  //   List<ArchiveCommentModel> commentList = new ArrayList<ArchiveCommentModel>();
  //   for(Path jsonFilePath : jsonFileList) {
  //     if(App.isContinue(jsonFilePath)){
  //       continue;
  //     }
  //     commentList
  //       = Stream.concat(
  //           commentList.stream(), 
  //           objectMapper.readValue(jsonFilePath.toFile(), new TypeReference<List<ArchiveCommentModel>>(){}).stream() )
  //             .collect(Collectors.toList());
  //   }
  //   long start = System.currentTimeMillis();
  //   Util.doubleCheck(commentList);
  //   long finish  = System.currentTimeMillis();
  //   System.out.println(finish - start);
  // }

  /**
   * ファイルを読み飛ばすかどうか判断する。
   * @param Path ファイルのPath
   * @return trueで読み飛ばす。
   */
  private static boolean isContinue(Path path) {
    return FilenameUtils.getExtension(path.toString()).equals("gitkeep");
  }
}
