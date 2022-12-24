package com.example.pathfinder.web.rest;

import com.example.pathfinder.model.dto.CommentCreationDto;
import com.example.pathfinder.model.dto.CommentMessageDto;
import com.example.pathfinder.model.view.CommentDisplayView;
import com.example.pathfinder.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class CommentRestController {

    private CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{routeId}/comments")
    public ResponseEntity<CommentDisplayView> createComment(
            @PathVariable("routeId") Long routeId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CommentMessageDto commentDto) {

        CommentCreationDto commentToCreate = new CommentCreationDto(
                userDetails.getUsername(),
                routeId,
                commentDto.getMessage()
        );

        CommentDisplayView comment = commentService.createComment(commentToCreate);

        return ResponseEntity
                .created(URI.create(
                        String.format("/api/%d/comments/%d", routeId, comment.getId())))
                .body(comment);

    }
}