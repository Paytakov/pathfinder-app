package com.example.pathfinder.service;

import com.example.pathfinder.exception.RouteNotFoundException;
import com.example.pathfinder.model.dto.CommentCreationDto;
import com.example.pathfinder.model.entity.Comment;
import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.model.view.CommentDisplayView;
import com.example.pathfinder.repository.CommentRepository;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

        return createCommentDisplayView(comment.getId(), author.getFullName(), comment.getText());
    }

    public List<CommentDisplayView> getAllCommentsForRoute(Long id) {
        Route route = routeRepository.findById(id).orElseThrow(RouteNotFoundException::new);

        return commentRepository.findAllByRoute(route)
                .stream()
                .map(comment -> createCommentDisplayView(
                        comment.getId(),
                        comment.getAuthor().getFullName(),
                        comment.getText()))
                .collect(Collectors.toList());
    }

    private CommentDisplayView createCommentDisplayView(Long id, String authorName, String text) {
        return new CommentDisplayView(
                id,
                authorName,
                text
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
