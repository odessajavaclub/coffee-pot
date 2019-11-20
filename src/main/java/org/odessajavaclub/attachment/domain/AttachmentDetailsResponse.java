package org.odessajavaclub.attachment.domain;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.odessajavaclub.attachment.enumeration.AttachmentType;

@Data
@NoArgsConstructor
public class AttachmentDetailsResponse {

    private Long meetingId; // field to relate to exact meeting
    private String name;
    private AttachmentType type;
    private Long userId;
    private String body; //TODO: use more generic type in the future
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdateTimestamp;
}
