package bearmaps.proj2c;
import bearmaps.proj2c.lectureexample.WeightedDirectedGraph;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private HashMap<Vertex, Double> distTo;
    private DoubleMapPQ<Vertex> sPQ;
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private int dequeued;
    private Vertex s;
    private HashMap<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        s = start;
        solution = new ArrayList<>();
        solution.add(start);
        distTo = new HashMap<>();
        sPQ = new DoubleMapPQ<>();
        edgeTo = new HashMap<>();
        sPQ.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);
        edgeTo.put(start, null);
        dequeued = 0;
        //Some while loop that runs until PQ is empty, run the for loop inside
        //Take the smallest thing off the PQ
        //Iterator all the neighers of vertex v, and relax each neighbor
        //Update the best distance for these neighbors ^
        //Update priority
        Stopwatch sw = new Stopwatch();
        while (sPQ.size() != 0) {
            Vertex removed = sPQ.removeSmallest();
            dequeued += 1;
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(removed);
            for (WeightedEdge<Vertex> n : neighborEdges) {
                Vertex v = n.from();
                Vertex t = n.to();
                double w = n.weight();
                nullChecker(t);
                if (distTo.get(v) + w < distTo.get(t)) {
                    distTo.put(t, distTo.get(v) + w);
//                    double sourceP = input.estimatedDistanceToGoal(start, t);
                    double h = input.estimatedDistanceToGoal(t, end);
                    edgeTo.put(t, v);
                    if (sPQ.contains(t)) {
                        sPQ.changePriority(t, distTo.get(t) + h);
                    } else if (!sPQ.contains(t)) {
                        sPQ.add(t, distTo.get(t) + h);
                    }
                }
            }
            if (removed.equals(end)) {
                solutionList(edgeTo, end);
                solution.add(0, start);
                solution.add(solution.size(), end);
                solutionWeight = distTo.get(removed);
                outcome = SolverOutcome.SOLVED;
                timeSpent = sw.elapsedTime();
                break;
            } else if (sw.elapsedTime() >= timeout) {
                outcome = SolverOutcome.UNSOLVABLE;
                solution = new ArrayList<>();
                solutionWeight = 0;
            }
        }
        // First, I'm gonna be taking the previous point's priority and adding it to the point, I'm adding upon, as well as the heuristic estimate
        // for that point to the goal itself
    }
    private void nullChecker(Vertex v) {
        if (!distTo.containsKey(v)) {
            distTo.put(v, Double.POSITIVE_INFINITY);
        }
    }

    private void solutionList(HashMap<Vertex, Vertex> distTo, Vertex goal) {
        List<Vertex> reverse = new ArrayList<>();
        while (distTo.get(goal) != s) {
            solution.add(distTo.get(goal));
            goal = distTo.get(goal);
        }
        while (solution.size() != 1) {
            reverse.add(solution.get(solution.size() - 1));
            solution.remove(solution.size() - 1);
        }
        solution = reverse;
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
