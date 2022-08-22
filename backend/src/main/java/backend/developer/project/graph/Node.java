package backend.developer.project.graph;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private String name;
    private List<Node> childNodes;
    private List<String> borders;

    public Node(String name, List<String> borders){
        this.name = name;
        this.borders = borders;
        childNodes = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public List<Node> getChildNodes(){
        return childNodes;
    }

    public List<String> getBorders(){
        return borders;
    }

    public void addChildNode(Node n){
        childNodes.add(n);
    }
}
