package bearmaps.proj2d;

import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.proj2c.WeightedEdge;
import bearmaps.proj2c.streetmap.StreetMapGraph;
import bearmaps.proj2c.streetmap.Node;
import edu.princeton.cs.algs4.TrieSET;

import javax.naming.ldap.HasControls;
import javax.print.attribute.HashAttributeSet;
import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private WeirdPointSet nearestPoint;
    private HashMap<Point, Node> searchMap;
    private List<Node> listNodes;
    HashMap<String, Set<Node>> locations;
    HashMap<String, LinkedList<Node>> duplicates;
    private int index;
    private TrieSet locationPrefixes;
    private String p;
    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        index = 0;
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        listNodes = nodes;
        searchMap = new HashMap<>();
        locations = new HashMap<>();
        duplicates = new HashMap<>();
        locationPrefixes = new TrieSet();
        for (Node n : nodes) {
            List<WeightedEdge<Long>> neighbors = neighbors(n.id());
            if (!neighbors.isEmpty()) {
                double x = n.lon();
                double y = n.lat();
                searchMap.put(new Point(x, y), n);
            }
            if (n.name() == null) {
                continue;
            } else {
                String cleanNode = cleanString(n.name());
                if (duplicates.get(cleanNode) == null) {
                    LinkedList<Node> inputL = new LinkedList<>();
                    inputL.add(n);
                    duplicates.put(cleanNode, inputL);
                } else {
                    duplicates.get(cleanNode).add(n);
                }
                locationPrefixes.add(cleanNode);
                if (!locations.containsKey(cleanNode)) {
                    HashSet<Node> input = new HashSet<>();
                    input.add(n);
                    locations.put(cleanNode, input);
                } else {
                    locations.get(cleanNode).add(n);
                }
            }
        }
        List keys = new ArrayList(searchMap.keySet());
        nearestPoint = new WeirdPointSet(keys);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point closestPoint = nearestPoint.nearest(lon, lat);
        return searchMap.get(closestPoint).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        p = prefix;
        Iterable<String> locationIter = locationPrefixes.keysWithPrefix(prefix);
        List<String> finalLoc = new ArrayList<>();
        for (String s : locationIter) {
            for (Node n : locations.get(s)) {
                finalLoc.add(n.name());
            }
        }
        return finalLoc;
    }


    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        LinkedList names = duplicates.get(cleanString(locationName));
        ArrayList<Map<String, Object>> allNames = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            Node n = (Node) names.get(i);
            HashMap<String, Object> input = new HashMap<>();
            input.put("name", n.name());
            input.put("lon", n.lon());
            input.put("id", n.id());
            input.put("lat", n.lat());
            allNames.add(input);
        }
        return allNames;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }
}
