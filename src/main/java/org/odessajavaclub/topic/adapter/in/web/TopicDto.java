package org.odessajavaclub.topic.adapter.in.web;

import lombok.Data;
import java.util.Date;

@Data
public class TopicDto {
    private final long id;
    private final String title;
    private final Date event;
    private final String type;
    private final String author;
    private final int score;
    private final String status;
}
