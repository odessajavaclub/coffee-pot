package org.odessajavaclub.topic.adapter.in.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.odessajavaclub.topic.domain.enumeration.TopicStatus;
import org.odessajavaclub.topic.domain.enumeration.TopicType;

// TODO: add swagger annotations e.q for date
@Data
@ApiModel
public class TopicDto {
  @ApiModelProperty(notes = "The database generated topic ID")
  private final long id;

  private final String title;
  // @ApiModelProperty({type:'string', format:'date-time', example:'21/01/2020 13:00'})
  @ApiModelProperty(notes = "Date with time, format like: 21/01/2020 13:00")
  private final String event;

  @ApiModelProperty(dataType = "string")
  private final TopicType type;

  private final String author;
  private final int score;

  @ApiModelProperty(dataType = "string")
  private final TopicStatus status;
}
