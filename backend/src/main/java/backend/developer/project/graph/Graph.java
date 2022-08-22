package backend.developer.project.graph;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
@Scope("singleton")
public class Graph {

    private HashMap<String, Node> countries;

    public Graph(){
        countries = new HashMap<>();
    }

    @PostConstruct
    public void init(){
        try {
            loadJSONData();
            setBorders();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void loadJSONData() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new BufferedReader(new InputStreamReader(Objects.requireNonNull(Graph.class.getClassLoader().getResourceAsStream("countries.json")), StandardCharsets.UTF_8)));

        JSONArray jsonArray =  (JSONArray) obj;

        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String name = (String) jsonObject.get("cca3");
            JSONArray bordersArray = (JSONArray) jsonObject.get("borders");

            List<String> bordersList = new ArrayList<>();
            for(int j = 0; j < bordersArray.size(); j++){
                bordersList.add(bordersArray.get(j).toString());
            }

            countries.put(name, new Node(name, bordersList));
        }
    }

    private void setBorders(){
        for (HashMap.Entry<String, Node> entry : countries.entrySet()) {
            Node node = entry.getValue();

            for(String countryName : node.getBorders()){
                Node n = countries.get(countryName);
                node.addChildNode(n);
            }
        }
    }

    public HashMap<String, Node> getCountries() {
        return countries;
    }

    public Node getCountryByKey(String key) {
        return countries.get(key);
    }
}
