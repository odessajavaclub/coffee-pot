package org.odessajavaclub.attachment.application.port.in;

import org.odessajavaclub.attachment.domain.SliceOfAttachmentsResponse;

public interface GetSliceOfAttachmentsUseCase {

    SliceOfAttachmentsResponse getAttachmentsList(Long meetingId, Integer pageNumber, Integer pageSize);
}
