package org.odessajavaclub.attachment.application;

import org.odessajavaclub.attachment.application.port.in.GetSliceOfAttachmentsUseCase;
import org.odessajavaclub.attachment.domain.SliceOfAttachmentsResponse;
import org.springframework.stereotype.Service;

@Service
public class GetSliceOfAttachmentsService implements GetSliceOfAttachmentsUseCase {

    @Override
    public SliceOfAttachmentsResponse getAttachmentsList(Integer pageNumber, Integer pageSize) {
        return null;
    }
}
