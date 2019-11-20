package org.odessajavaclub.attachment.application;

import org.odessajavaclub.attachment.application.port.in.GetAttachmentDetailsUseCase;
import org.odessajavaclub.attachment.domain.AttachmentDetailsResponse;
import org.springframework.stereotype.Service;

@Service
public class GetAttachmentDetailsService implements GetAttachmentDetailsUseCase {

    @Override
    public AttachmentDetailsResponse getAttachmentsDetails(long id) {
        return null;
    }
}
