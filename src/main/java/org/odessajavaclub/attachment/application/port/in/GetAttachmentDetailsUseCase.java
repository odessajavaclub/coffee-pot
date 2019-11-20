package org.odessajavaclub.attachment.application.port.in;

import org.odessajavaclub.attachment.domain.AttachmentDetailsResponse;

public interface GetAttachmentDetailsUseCase {

    AttachmentDetailsResponse getAttachmentsDetails(long id);
}
