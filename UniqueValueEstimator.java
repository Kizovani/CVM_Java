import java.util.HashSet;
import java.util.Iterator;

/**
 * Class to calculate an estimate of the amount of discrete elements inputted by a stream
 * @author oelba
 * @param <T> 
 */
class UniqueValueEstimator<T> {
    private int round, threshold;
    private HashSet <T> buffer;

    /**
     * Constructor method for the class
     * @param threshold - The amount of elements allowed in the buffer, a higher threshold will produce a more accurate estimate.
     */
    public void UniqueValueEstimator(int threshold) {
        this.round = 0;
        this.threshold = threshold;
        this.buffer = new HashSet();
    }

    /**
     * Proccess the inputted element, each element within a stream must go through this method for the class to keep track of all
     * the elements within the stream.
     * @param Element - Element in the stream.
     */
    public void processElement(T Element) {
        boolean success = this.buffer.add(Element);
        if (!success) {
            if (Math.random() <= (1/Math.pow(2, this.round))) {
                this.buffer.remove(Element);
            }
        }
        if (this.buffer.size() == this.threshold) {
            bufferHalving();
        }
    }

    /**
     * Private method that only runs when the amount of elements in the buffer reaches the inputted threshold,
     * deletes approximately half the set to advance the round variable. */
    private void bufferHalving() {
        Iterator<T> iter = this.buffer.iterator();
        while (iter.hasNext()) {
            iter.next();
            if (Math.random() < 0.5) {
                iter.remove();
            }
        }
        this.round += 1;
    }
    /**
     * Returns the current discrete count
     * @return 
     */
    public double getCount() {
        return this.buffer.size() * Math.pow(2, this.round);
    }
    
}