package org.odessajavaclub.attachment.application.port.in;

import org.odessajavaclub.attachment.domain.AttachmentRequest;

public interface UpdateAttachmentUseCase {

    void updateAttachment(AttachmentRequest attachmentRequest);
}
