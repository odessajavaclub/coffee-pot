package org.odessajavaclub.attachment.domain;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SliceOfAttachmentsResponse {

    private List<AttachmentDetailsResponse> list;
    private Boolean hasNext;
}
