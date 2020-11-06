package bearmaps.proj2c;
import bearmaps.proj2c.lectureexample.WeightedDirectedGraph;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private HashMap<Vertex, Double> distTo;
    private DoubleMapPQ<Vertex> sPQ;
    private AStarGraph<Vertex> i;
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private int dequeued;
    private ArrayList<Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        i = input;
        distTo = new HashMap<>();
        sPQ = new DoubleMapPQ<>();
        edgeTo = new ArrayList<>();
        sPQ.add(start, 0);
        distTo.put(start, 0.0);
        edgeTo.add(null);
        dequeued = 0;
        //Some while loop that runs until PQ is empty, run the for loop inside
        //Take the smallest thing off the PQ
        //Iterator all the neighers of vertex v, and relax each neighbor
        //Update the best distance for these neighbors ^
        //Update priority
        Stopwatch sw = new Stopwatch();
        while (sPQ.size() != 0) {
            Vertex removed = sPQ.removeSmallest();
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(removed);
            dequeued += 1;
            for (WeightedEdge<Vertex> n : neighborEdges) {
                Vertex v = n.from();
                Vertex t = n.to();
                double w = n.weight();
                nullChecker(t);
                if (distTo.get(v) + w < distTo.get(t)) {
                    hashPut(t, distTo.get(v) + w);
                    double sourceP = input.estimatedDistanceToGoal(start, t);
                    double h = input.estimatedDistanceToGoal(t, end);
                    edgeTo.add(t);
                    if (sPQ.contains(t)) {
                        sPQ.changePriority(t, sourceP + h);
                        hashPut(t, sourceP + h);
                    } else if (!sPQ.contains(t)) {
                        sPQ.add(t, sourceP + h);
                        hashPut(t, sourceP + h);
                    }
                }
                if (removed.equals(end)) {
//                    solution = List.of(start, end);
//                    solutionWeight = n.weight();
                    outcome = SolverOutcome.SOLVED;
                    timeSpent = sw.elapsedTime();
                    break;
                } else if ( sw.elapsedTime() >= timeout) {
                    outcome = SolverOutcome.UNSOLVABLE;
                }
            }
        }

//        List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(start);
//        for (WeightedEdge<Vertex> n: neighborEdges) {
//            double sourceP = input.estimatedDistanceToGoal(start, n.from());
//            double h = input.estimatedDistanceToGoal(n.from(), goal);
//            sPQ.add(n.from(), sourceP + h);
//            hashPut(n.from(), sourceP + h);
//            if (n.from() == end) {
//                break;
//            }
//        }
//        while (true) {
//            Vertex removed = sPQ.removeSmallest();
//            relax(removed);
//            if (removed == goal) {
//                break;
//            }
//        }

        // First, I'm gonna be taking the previous point's priority and adding it to the point, I'm adding upon, as well as the heuristic estimate
        // for that point to the goal itself
    }
    private void nullChecker(Vertex v) {
        if (!distTo.containsKey(v)) {
            distTo.put(v, Double.POSITIVE_INFINITY);
        }
    }
    private void hashPut(Vertex k, Double v) {
        if (distTo.containsKey(k)) {
            if (distTo.get(k) > v) {
                distTo.put(k, v);
            } else {
                return;
            }
        } else {
            distTo.put(k, v);
        }
    }

    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        return solutionWeight;
    }
    public int numStatesExplored() {
        return dequeued;
    }
    public double explorationTime() {
        return timeSpent;
    }
}
