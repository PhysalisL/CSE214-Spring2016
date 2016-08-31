/**
 * Light Values for TwoWayRoad object to abide by.
 * Rules:
 * GREEN means right and middle lanes may proceed, but the left lane cannot.
 * RED means no lane may proceed.
 * LEFT_SIGNAL means only left can proceed.
 *
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public enum LightValue {
    GREEN, RED, LEFT_SIGNAL;
}
