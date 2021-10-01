package sample.json.performance;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import sample.json.performance.model.ArchiveCommentModel;

import java.io.IOException;

/**
 * resources配下のjsonファイルを読み込む
 */
public class App {
  public static void main( String[] args ) throws IOException {
    List<Path> jsonFileList = Files.walk(Paths.get(System.getProperty("user.dir"), "src", "main", "resources"))
                                   .filter(path -> !Files.isDirectory(path))
                                   .collect(Collectors.toList());

    List<ArchiveCommentModel> commentList = new ArrayList<ArchiveCommentModel>();
    ObjectMapper objectMapper = new ObjectMapper();
    for(Path jsonFilePath : jsonFileList) {
      commentList
        = Stream.concat(
            commentList.stream(), 
            objectMapper.readValue(jsonFilePath.toFile(), new TypeReference<List<ArchiveCommentModel>>(){}).stream() )
              .collect(Collectors.toList());
    }
    for(int i=0 ; i<commentList.size() ; i++) {
      ArchiveCommentModel comment_i = commentList.get(i);
      for(int j=i+1 ; j<commentList.size() ; j++){
        ArchiveCommentModel comment_j = commentList.get(j);
        if( comment_i.getVideo_id().equals(comment_j.getVideo_id())
        &&  comment_i.getAuthorExternalChannelId().equals(comment_j.getAuthorExternalChannelId())
        &&  comment_i.getTimestampUsec().equals(comment_j.getTimestampUsec())) {
          System.out.println("comment_i");
          System.out.println(comment_i);
          System.out.println("comment_j");
          System.out.println(comment_j);
        }
        
      }
    }
  }
}
