package backend.developer.project.services;

import backend.developer.project.graph.Graph;
import backend.developer.project.graph.Node;
import backend.developer.project.graph.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteService {

    @Autowired
    private Graph graph;

    public List<String> findRoute(String origin, String destination){

        if(!validParameters(origin, destination))
                return null;

        origin = origin.toUpperCase();
        destination = destination.toUpperCase();

        HashSet<Node> visited = new HashSet<>();
        Queue<Node> adjacent = new LinkedList<>();
        HashMap<String, Route> routes = new HashMap<>();

        adjacent.add(graph.getCountryByKey(origin));
        routes.put(graph.getCountryByKey(origin).getName(), new Route(0, graph.getCountryByKey(origin)));

        while (!adjacent.isEmpty()) {
            Node current = adjacent.remove();

            if (current.getName().equals(destination))
                return getShortestRoute(routes, origin, destination);

            for (int i = 0; i < current.getChildNodes().size(); i++) {
                Node adjacentNode = current.getChildNodes().get(i);
                if (!visited.contains(adjacentNode) && !adjacent.contains((adjacentNode))) {
                    adjacent.add(current.getChildNodes().get(i));
                    routes.put(current.getChildNodes().get(i).getName(), new Route(routes.get(current.getName()).getDistance()+1, current));
                }
            }
            visited.add(current);
        }
        return new ArrayList<>();
    }

    private List<String> getShortestRoute(HashMap<String, Route> routes, String origin, String destination){
        boolean isComplete = false;
        String current = destination;
        List<String> shortestRoute = new ArrayList<>();

        shortestRoute.add(current);

        while(!isComplete){
            current = routes.get(current).getPreviousNode().getName();
            shortestRoute.add(current);

            if(current.equals(origin))
                isComplete = true;
        }
        Collections.reverse(shortestRoute);
        return shortestRoute;
    }

    private boolean validParameters(String origin, String destination){
        if(isEmptyString(origin) || isEmptyString(destination))
            return false;

        return true;
    }

    private boolean isEmptyString(String str) {
        return str == null || str.length() == 0;
    }
}
