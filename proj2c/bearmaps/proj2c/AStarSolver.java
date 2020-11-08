package bearmaps.proj2c;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private HashMap<Vertex, Double> distTo;
    private DoubleMapPQ<Vertex> sPQ;
    private SolverOutcome outcome;
    private double solutionWeight;
    private LinkedList<Vertex> solution;
    private double timeSpent;
    private int dequeued;
    private Vertex s;
    private HashMap<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        s = start;
        solution = new LinkedList<>();
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
//                while (!(edgeTo.get(end).equals(s))) {
//                    solution.addFirst(edgeTo.get(end));
//                    end = edgeTo.get(end);
//                }
                solutionList(edgeTo, end);
                solutionWeight = distTo.get(removed);
                outcome = SolverOutcome.SOLVED;
                timeSpent = sw.elapsedTime();
                break;
            } else if (sw.elapsedTime() >= timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solution = new LinkedList<>();
                solutionWeight = 0;
            }
            if (sPQ.size() == 0) {
                outcome = SolverOutcome.UNSOLVABLE;
            }
        }
    }
    private void nullChecker(Vertex v) {
        if (!distTo.containsKey(v)) {
            distTo.put(v, Double.POSITIVE_INFINITY);
        }
    }

    private void solutionList(HashMap<Vertex, Vertex> edgeTo, Vertex goal) {
        Vertex e = goal;
//        if (!edgeTo.containsKey(goal)) {
//            solution = new LinkedList<>();
//            return;
//        }
        if (edgeTo.get(goal) == null && s.equals(goal)) {
            solution.add(s);
            return;
        }
        while (edgeTo.containsKey(goal) && !edgeTo.get(goal).equals(s)) {
            solution.addFirst(edgeTo.get(goal));
            goal = edgeTo.get(goal);
        }
        solution.addFirst(s);
        solution.addLast(e);
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
