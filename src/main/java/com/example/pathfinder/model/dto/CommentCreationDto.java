package com.example.pathfinder.model.dto;

public class CommentCreationDto {

    private String username;
    private Long routeId;
    private String message;

    public CommentCreationDto(String username, Long routeId, String message) {
        this.username = username;
        this.routeId = routeId;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public CommentCreationDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public Long getRouteId() {
        return routeId;
    }

    public CommentCreationDto setRouteId(Long routeId) {
        this.routeId = routeId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommentCreationDto setMessage(String message) {
        this.message = message;
        return this;
    }
}
