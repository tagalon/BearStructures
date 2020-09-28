package es.datastructur.synthesizer;

import edu.princeton.cs.algs4.StdRandom;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        double l = SR/frequency;
        long cap = Math.round(l);
        int capacity = (int) cap;
        buffer = new ArrayRingBuffer<>(capacity);
        for(int count = 0; 0 < buffer.capacity(); count++) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        for(int count = 0; 0 < buffer.capacity(); count++) {
            buffer.dequeue();
        }
        for(int count = 0; 0 < buffer.capacity(); count++) {
            double r = Math.random() - 0.5;
            buffer.enqueue(r);
        }
        return;
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
        double firstItem = buffer.dequeue();
        double nextItem = buffer.peek();
        buffer.enqueue(DECAY * (.5 * (firstItem + nextItem)));
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.
        double frontItem = buffer.peek();
        return frontItem;
    }

}
    // TODO: Remove all comments that say TODO when you're done.
