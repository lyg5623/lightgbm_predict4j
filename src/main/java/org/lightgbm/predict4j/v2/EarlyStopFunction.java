package org.lightgbm.predict4j.v2;

/**
 * @author lyg5623
 */
public interface EarlyStopFunction {
     boolean callback( double[] d, int i);
}
