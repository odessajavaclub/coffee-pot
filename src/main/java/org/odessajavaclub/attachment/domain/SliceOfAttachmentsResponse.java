package org.odessajavaclub.attachment.domain;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SliceOfAttachmentsResponse {

    private List<AttachmentDetailsResponse> list; //TODO: use less detailed response for getting a list - to discuss later
    private Boolean hasNext;
}
