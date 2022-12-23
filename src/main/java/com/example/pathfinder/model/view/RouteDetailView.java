package com.example.pathfinder.model.view;

import java.util.List;

public class RouteDetailView {

    private Long id;
    private String gpxCoordinates;
    private String level;
    private String name;
    private String description;
    private String authorName;
    private String videoUrl;
    private List<String> pictureUrls;

    public RouteDetailView(Long id,
                           String gpxCoordinates,
                           String level,
                           String name,
                           String description,
                           String authorName,
                           String videoUrl,
                           List<String> pictureUrls) {
        this.id = id;
        this.gpxCoordinates = gpxCoordinates;
        this.level = level;
        this.name = name;
        this.description = description;
        this.authorName = authorName;
        this.videoUrl = videoUrl;
        this.pictureUrls = pictureUrls;
    }

    public Long getId() {
        return id;
    }

    public RouteDetailView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getGpxCoordinates() {
        return gpxCoordinates;
    }

    public RouteDetailView setGpxCoordinates(String gpxCoordinates) {
        this.gpxCoordinates = gpxCoordinates;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public RouteDetailView setLevel(String level) {
        this.level = level;
        return this;
    }

    public String getName() {
        return name;
    }

    public RouteDetailView setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RouteDetailView setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public RouteDetailView setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public RouteDetailView setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public RouteDetailView setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
        return this;
    }
}
