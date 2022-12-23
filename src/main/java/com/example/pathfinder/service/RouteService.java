package com.example.pathfinder.service;

import com.example.pathfinder.exception.RouteNotFoundException;
import com.example.pathfinder.model.entity.Picture;
import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.view.RouteDetailView;
import com.example.pathfinder.model.view.RouteIndexView;
import com.example.pathfinder.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getMostCommented() {
        return routeRepository.findMostCommented();
    }

    public List<RouteIndexView> getAllRoutes() {
        return routeRepository
                .findAll()
                .stream()
                .map(route -> new RouteIndexView(
                        route.getId(),
                        route.getName(),
                        route.getDescription(),
                        route.getPictures()
                                .stream()
                                .findFirst()
                                .get()
                                .getUrl()
                ))
                .collect(Collectors.toList());
    }

    public RouteDetailView getRouteById(Long routeId) {
        return routeRepository
                .findById(routeId)
                .map(route -> new RouteDetailView(
                        route.getId(),
                        route.getGpxCoordinates(),
                        route.getLevel().name(),
                        route.getName(),
                        route.getDescription(),
                        route.getAuthor().getFullName(),
                        route.getVideoUrl(),
                        route.getPictures()
                                .stream()
                                .map(Picture::getUrl)
                                .collect(Collectors.toList())
                ))
                .orElseThrow(RouteNotFoundException::new);
    }
}
