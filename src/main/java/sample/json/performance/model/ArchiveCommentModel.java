package sample.json.performance.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
  "authorExternalChannelId",
  "user",
  "timestampUsec",
  "time",
  "authorbadge",
  "text",
  "purchaseAmount",
  "type",
  "video_id",
  "Chat_No"
})

@Data
public class ArchiveCommentModel {
  @JsonProperty
  private String authorExternalChannelId;
  @JsonProperty
  private String user;
  @JsonProperty
  private String timestampUsec;
  @JsonProperty
  private String time;
  @JsonProperty
  private String authorbadge;
  @JsonProperty
  private String text;
  @JsonProperty
  private String purchaseAmount;
  @JsonProperty
  private String type;
  @JsonProperty
  private String video_id;
  @JsonProperty
  private String Chat_No; 
}
