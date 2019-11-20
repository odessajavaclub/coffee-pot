package org.odessajavaclub.attachment.adapter.in.web;

import org.odessajavaclub.attachment.application.port.in.CreateAttachmentUseCase;
import org.odessajavaclub.attachment.application.port.in.DeleteAttachmentUseCase;
import org.odessajavaclub.attachment.application.port.in.GetAttachmentDetailsUseCase;
import org.odessajavaclub.attachment.application.port.in.GetSliceOfAttachmentsUseCase;
import org.odessajavaclub.attachment.application.port.in.UpdateAttachmentUseCase;
import org.odessajavaclub.attachment.domain.AttachmentDetailsResponse;
import org.odessajavaclub.attachment.domain.AttachmentRequest;
import org.odessajavaclub.attachment.domain.SliceOfAttachmentsResponse;
import org.odessajavaclub.auth.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/attachment")
public class AttachmentController {

    private CreateAttachmentUseCase createAttachmentUseCase;
    private UpdateAttachmentUseCase updateAttachmentUseCase;
    private DeleteAttachmentUseCase deleteAttachmentUseCase;
    private GetAttachmentDetailsUseCase getAttachmentDetailsUseCase;
    private GetSliceOfAttachmentsUseCase getSliceOfAttachmentsUseCase;
    private AuthenticationFacade authenticationFacade;

    @Autowired
    public AttachmentController(CreateAttachmentUseCase createAttachmentUseCase,
            UpdateAttachmentUseCase updateAttachmentUseCase,
            DeleteAttachmentUseCase deleteAttachmentUseCase,
            GetAttachmentDetailsUseCase getAttachmentDetailsUseCase,
            GetSliceOfAttachmentsUseCase getSliceOfAttachmentsUseCase,
            AuthenticationFacade authenticationFacade) {
        this.createAttachmentUseCase = createAttachmentUseCase;
        this.updateAttachmentUseCase = updateAttachmentUseCase;
        this.deleteAttachmentUseCase = deleteAttachmentUseCase;
        this.getAttachmentDetailsUseCase = getAttachmentDetailsUseCase;
        this.getSliceOfAttachmentsUseCase = getSliceOfAttachmentsUseCase;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/list")
    public ResponseEntity<SliceOfAttachmentsResponse> getAttachmentsList(
            @RequestParam(value = "meetingId", required = true) Long meetingId,
            @RequestParam(value = "pageNumber", required = true) Integer pageNumber,
            @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return ResponseEntity.ok(getSliceOfAttachmentsUseCase.getAttachmentsList(meetingId, pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttachmentDetailsResponse> getAttachmentsDetails(@PathVariable("id") long id) {
        return ResponseEntity.ok(getAttachmentDetailsUseCase.getAttachmentsDetails(id));
    }

    @PostMapping
    public ResponseEntity createAttachment(@RequestBody AttachmentRequest attachmentRequest) {
        createAttachmentUseCase.createAttachment(attachmentRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity updateAttachment(@RequestBody AttachmentRequest attachmentRequest) {
        updateAttachmentUseCase.updateAttachment(attachmentRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity deleteAttachment(@RequestParam long id) {
        deleteAttachmentUseCase.deleteAttachment(id);
        return ResponseEntity.noContent().build();
    }

}
