package bearmaps.proj2c;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.HashMap;
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

    /* The constructor for solving the best short path from start to goal */
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

    private void solutionList(HashMap h, Vertex goal) {
        Vertex e = goal;
        if (h.get(goal) == null && s.equals(goal)) {
            solution.add(s);
            return;
        }
        while (h.containsKey(goal) && !h.get(goal).equals(s)) {
            solution.addFirst((Vertex) h.get(goal));
            goal = (Vertex) h.get(goal);
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
