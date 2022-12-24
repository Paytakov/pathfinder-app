package com.example.pathfinder.service;

import com.example.pathfinder.model.dto.CommentCreationDto;
import com.example.pathfinder.model.entity.Comment;
import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.model.view.CommentDisplayView;
import com.example.pathfinder.repository.CommentRepository;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private RouteRepository routeRepository;
    private UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          RouteRepository routeRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
    }

    public CommentDisplayView createComment(CommentCreationDto commentDto) {
        User author = userRepository.findByUsername(commentDto.getUsername()).get();

        Comment comment = createNewComment(commentDto, author);

        commentRepository.save(comment);

        return new CommentDisplayView(
                comment.getId(),
                author.getFullName(),
                comment.getText()
        );
    }

    private Comment createNewComment(CommentCreationDto commentDto, User author) {
        Comment comment = new Comment();
        comment.setRoute(routeRepository.getById(commentDto.getRouteId()));
        comment.setAuthor(author);
        comment.setApproved(true);
        comment.setCreated(LocalDateTime.now());
        comment.setText(commentDto.getMessage());
        return comment;
    }
}
