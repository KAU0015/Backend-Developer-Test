package backend.developer.project.controllers;

import backend.developer.project.services.RouteService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RouteController {

    @Autowired
    private RouteService routeService;

    @RequestMapping("/routing/{origin}/{destination}")
    public ResponseEntity findRoute(@PathVariable String origin, @PathVariable String destination){

        List<String> shortestRoute = routeService.findRoute(origin, destination);

        if(shortestRoute == null)
            return ResponseEntity.badRequest().body("Wrong params were provided!");
        else if(shortestRoute.size() == 0)
            return ResponseEntity.badRequest().body(String.format("Land route from %s to %s does not exists!", origin, destination));

        JSONObject result = new JSONObject();
        result.put("route", shortestRoute);

        return ResponseEntity.ok(result);
    }
}
