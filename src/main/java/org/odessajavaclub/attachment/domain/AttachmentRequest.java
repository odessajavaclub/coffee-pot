package org.odessajavaclub.attachment.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.odessajavaclub.attachment.enumeration.AttachmentType;

@Data
@NoArgsConstructor
public class AttachmentRequest {

    private Long meetingId; // field to relate to exact meeting
    private String name;
    private AttachmentType type;
    private String body; //TODO: use more generic type in the future
}
