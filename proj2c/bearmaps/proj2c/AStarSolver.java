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

    /* The constructor solves the best short path from start to goal under the timeout,
    and if the timeout exceeds or the PQ is empty, a best short path doesn't exist. */
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

    /* This function checks whether or not an item is in the
    Hashmap distTo, and if it is not, input with infinite priority.
     */
    private void nullChecker(Vertex v) {
        if (!distTo.containsKey(v)) {
            distTo.put(v, Double.POSITIVE_INFINITY);
        }
    }

    /* This function creates the best short path in the order of vertexes,
    and returns all these vertexes including start and end into a LinkedList.
     */
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

    /* This function returns whether a best short path has been found. */
    public SolverOutcome outcome() {
        return outcome;
    }
    /* This function returns the best short path
    as a list of vertices from start to end. */
    public List<Vertex> solution() {
        return solution;
    }
    /* This function returns the cost of the best path as a double. */
    public double solutionWeight() {
        return solutionWeight;
    }

    /* This function returns the number of states explored in the PQ. */
    public int numStatesExplored() {
        return dequeued;
    }
    /* This function returns how long it took to find the solution in seconds. */
    public double explorationTime() {
        return timeSpent;
    }
}
