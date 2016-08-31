/**
 * BooleanSource class determines whether or not the random number
 * generator has landed on the given probability range.
 *
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class BooleanSource {
    private double probability;

    /**
     * Constructor which initialize probability.
     *
     * @param initProbability
     * Probability used to construct this BooleanSource object.
     *
     * <dt>Preconditions
     * <dd>0 < probability <= 1.
     *
     * @throws IllegalArgumentException
     * Indicate initProbability ≤ 0 or initProbability > 1.
     */
    public BooleanSource(double initProbability)
            throws IllegalArgumentException{
        if(initProbability<=0 || initProbability>1)
            throw new IllegalArgumentException("Invalid probability range");

        probability = initProbability;
    }

    /**
     * Return true if the random number generator land within the range of
     * probability.
     *
     * <dt>Preconditions
     * <dd>Probability is valid. (0 < probability <= 1).
     *
     * @return
     * Has the event occurred?
     */
    public boolean occurs(){
        return Math.random()<probability;
    }
}
