package com.example.pathfinder.web;

import com.example.pathfinder.model.view.RouteDetailView;
import com.example.pathfinder.model.view.RouteIndexView;
import com.example.pathfinder.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public String getAllRoutes(Model model) {
        List<RouteIndexView> allRoutes = routeService.getAllRoutes();

        model.addAttribute("routes", allRoutes);

        return "routes";
    }

    @GetMapping("/details/{id}")
    public String getAllRoutes(@PathVariable("id") Long id, Model model) {
        RouteDetailView route = routeService.getRouteById(id);

        model.addAttribute("route", route);

        return "route-details";
    }
}
