package org.odessajavaclub.attachment.application.port.in;

import org.odessajavaclub.attachment.domain.AttachmentRequest;

public interface CreateAttachmentUseCase {

    void createAttachment(AttachmentRequest attachmentRequest);
}
