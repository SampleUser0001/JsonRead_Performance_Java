package sample.json.performance.util;

import java.util.List;
import sample.json.performance.model.ArchiveCommentModel;
import sample.json.performance.model.TimestampModel;

public interface Util {
  /**
   * 重複チェックを行う。
   */
  public static void doubleCheck(TimestampModel timestamp) {
    timestamp.start();
    List<ArchiveCommentModel> commentList = timestamp.getCommentList();
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
    timestamp.finish();
    System.out.println(timestamp.toString());
  }
}