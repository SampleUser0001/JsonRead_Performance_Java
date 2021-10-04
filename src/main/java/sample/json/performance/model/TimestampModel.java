package sample.json.performance.model;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import sample.json.performance.model.ArchiveCommentModel;

public class TimestampModel {
  private String filepath;
  private List<ArchiveCommentModel> commentList;
  private long start = 0;
  private long finish = 0;
  private CountDownLatch countDownLatch;
  
  public TimestampModel(String filepath, List<ArchiveCommentModel> commentList, CountDownLatch countDownLatch){
    this.filepath = filepath;
    this.commentList = commentList;
    this.countDownLatch = countDownLatch;
  }

  public void start() {
    this.start = System.currentTimeMillis();
  }
  
  public void finish() {
    this.finish = System.currentTimeMillis();
    this.countDownLatch.countDown();
  }

  public List<ArchiveCommentModel> getCommentList() {
    return this.commentList;
  }

  public String toString() {
    return String.format("filepath:%s, count:%d, start:%d, finish:%d, time:%d, countDown:%d", filepath, commentList.size(), start, finish, finish-start, countDownLatch.getCount());
  }
  
}