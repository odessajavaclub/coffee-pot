package org.odessajavaclub.topic.application.port.out;

import org.odessajavaclub.topic.domain.Topic;

public interface UpdateTopicPort {
    Topic updateTopic(Topic topic);
}
