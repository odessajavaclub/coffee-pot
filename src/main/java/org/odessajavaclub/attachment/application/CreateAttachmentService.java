package org.odessajavaclub.attachment.application;

import org.odessajavaclub.attachment.application.port.in.CreateAttachmentUseCase;
import org.odessajavaclub.attachment.domain.AttachmentRequest;
import org.springframework.stereotype.Service;

@Service
public class CreateAttachmentService implements CreateAttachmentUseCase {

    @Override
    public void createAttachment(AttachmentRequest attachmentRequest) {

    }
}
