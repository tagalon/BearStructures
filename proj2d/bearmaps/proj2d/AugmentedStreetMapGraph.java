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
                locationPrefixes.add(cleanNode);
                if (!locations.containsKey(cleanNode)) {
                    HashSet<Node> input = new HashSet<>();
                    input.add(n);
                    locations.put(cleanNode, input);
                } else {
                    locations.get(cleanNode).add(n);
                }
                if (!duplicates.containsKey(cleanNode)) {
                    LinkedList<Node> inputL = new LinkedList<>();
                    inputL.add(n);
                    duplicates.put(cleanNode, inputL);
                } else {
                    duplicates.get(cleanNode).add(n);
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
//        TrieSet locationPrefixes = new TrieSet();
//        int index = 0;
//        for (Node node : listNodes) {
//            if (node.name() == null) {
//                continue;
//            }
//            String cleanWord = cleanString(node.name());
//            if (cleanWord.startsWith(prefix)) {
//                locationPrefixes.add(cleanWord);
//                locations.put(index, node.name());
//                index += 1;
//                }
//            }
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
//        while (!names.isEmpty()) {
        int length = names.size();
        for (int i = 0; i < length; i++) {
            Node n = (Node) names.removeFirst();
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
    public static void main(String[] args) {
        String[] s1 = {"P and M Market", "P224 GPS Observatory", "Pappy's", "Papa John's", "Pardee",
                "Party Sushi", "Pariah House", "Paris Baguette", "Park Avenue Bar & Grill", "Park Day School",
        "Park Hills Fountain", "Pacific Boychoir Academy", "Pacific Salces - Kitchen", "Bath",
                "Electronics", "Pacific Standard Taproom", "Pacific E-Bike", "Pacific Film Archive Theater",
                "Paco Collars", "Pasta Pomodoro", "Padre", "Pat Brown's Grille", "Paul's Shoe Repair", "Pavé",
                "Paisan", "Payn's Statonery - Office Supplies", "Panoramic Hill", "Premier Auto Body",
                "Premier Cru Fine Wines", "Premier Gru Wines", "Priya Indian Cuisine", "Prince st",
                "Propaganda", "Professor Squirrel Gift Shop", "Progressive Baptist Church", "Prolube",
        "Public Parking", "Public Storage", "Purplekow", "Pure Barre", "Pucquio", "Punjab Bazaar",
                "Peralta Child Development Center", "Perry Temple Church of God in Christ", "Pet Club", "Pet Food Express",
        "PETS Emergency and Referral", "Petco", "Peter's Automotive Service", "PetFood Express", "Peets", "Peet's Coffee",
        "Peets Coffee & Tea", "Peet's Coffee & Tea", "Peet's Coffee and Tea", "Pegasus", "Pegasus Books",
                "Peoples Car Wash", "People's Cafe", "Pf Chang's Chinese Bistro", "Pharmaca", "Pharmica",
                "Phat Beets Produce", "Phat Matt's BBQ", "Philips Temple Church", "Philz Coffee", "Physics-Astronomy Library",
        "Pipeline Coffee Cafe", "PiQ", "Picnic Area #1", "Picnic Area #2", "Picoso", "Pier One Imports",
        "Piedmont", "Piedmont & 41st", "Piedmont & Yosemite", "Piedmont Avenue", "Piedmont Avenue Branch Oakland Public Library",
                "Piedmont Recreation Center", "Piedmont Center for the Arts", "Piedmont Chiropractic: Laurie Wonnell",
        "DC", "Piedmont Station Oakland Post Office", "Piedmont USD (office)", "Piedmont Grocery",
                "Piedmont House", "Piedmont Lane Gallery", "Pieology", "Pizza My Heart", "Pizza Moda",
                "Pizzahhh!", "Pizzaiolo", "Pin-up Parlor", "Pioneer Bicycles", "Pyung Chang Tofu House",
                "Place for sustainable living", "Platano", "Plate IQ", "Plearn Thai Restaurant",
        "Popeyes Louisiana Kitchen", "Poplar Playground", "Pottery Barn", "Poets Corner",
                "Poulet", "Powell's Sweet Shoppe", "Powis Parker Inc.", "Poki Poke", "Polished Nail Salon",
        "Pollo's", "Ponzo Charles Dimensions in Hair for Men and Women"};
//        String[] s2 =[Pacific Boychoir Academy, Pacific E-Bike, Pacific Film Archive Theater,
//                Pacific Salces - Kitchen, Bath, Electronics, Pacific Standard Taproom,
//        Paco Collars, Padre, Paisan, Panoramic Hill, Papa John's, Pappy's, Pardee,
//                Pariah House, Paris Baguette, Park Avenue Bar & Grill, Park Day School,
//        Park Hills Fountain, Party Sushi, Pasta Pomodoro, Pat Brown's Grille,
//        Paul's Shoe Repair, Pavé, Payn's Statonery - Office Supplies, Peets, Peet's Coffee,
//        Peet's Coffee and Tea, Peet's Coffee & Tea, Pegasus, Pegasus Books, People's Cafe,
//        Peoples Car Wash, Peralta Child Development Center,
//                Perry Temple Church of God in Christ, Petco, Peter's Automotive Service,
//        PetFood Express, PETS Emergency and Referral, Pet Club, Pet Food Express,
//        Pf Chang's Chinese Bistro, Pharmaca, Pharmica, Phat Beets Produce,
//        Phat Matt's BBQ, Philips Temple Church, Philz Coffee, Physics-Astronomy Library,
//        Picnic Area #2, Picoso, Piedmont, Piedmont Avenue,
//                Piedmont Avenue Branch Oakland Public Library, Piedmont Center for the Arts,
//        Piedmont Chiropractic: Laurie Wonnell, DC, Piedmont Grocery, Piedmont House,
//                Piedmont Lane Gallery, Piedmont Recreation Center,
//        Piedmont Station Oakland Post Office, Piedmont USD (office),
//                Piedmont & 41st, Piedmont & Yosemite, Pieology, Pier One Imports, Pin-up Parlor,
//                Pioneer Bicycles, Pipeline Coffee Cafe, PiQ, Pizzahhh!, Pizzaiolo,
//                Pizza Moda, Pizza My Heart, Place for sustainable living, Platano, Plate IQ,
//                Plearn Thai Restaurant, Poets Corner, Poki Poke, Polished Nail Salon, Pollo's,
//        Ponzo Charles Dimensions in Hair for Men and Women, Popeyes Louisiana Kitchen,
//        Poplar Playground, Pottery Barn, Poulet, Powell's Sweet Shoppe, Powis Parker Inc.,
//        Premier Auto Body, Premier Cru Fine Wines, Premier Gru Wines, Prince st,
//                Priya Indian Cuisine, Professor Squirrel Gift Shop, Progressive Baptist Church,
//        Prolube, Propaganda, Public Parking, Public Storage, Pucquio, Punjab Bazaar,
//                Pure Barre"", "Purplekow", "Pyung Chang Tofu House", "P and M Market", "P224 GPS Observatory"};
        String test1 = "Peets Coffee & Tea";
        String test2 = "Peet's Coffee & Tea";
        System.out.println(cleanString(test1) + " " + cleanString(test2));
    }
}
