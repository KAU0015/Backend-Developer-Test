package backend.developer.project.graph;

public class Route {

    private int distance;
    private Node previousNode;

    public Route(int distance, Node previousNode) {
        this.distance = distance;
        this.previousNode = previousNode;
    }

    public int getDistance() {
        return distance;
    }

    public Node getPreviousNode() {
        return previousNode;
    }
}
