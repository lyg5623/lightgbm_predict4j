package org.lightgbm.predict4j.v2;

/**
 * @author lyg5623
 */
public class CategoricalDecision<T extends Comparable<T>> extends Decision<T> {
    boolean decision(T fval, T threshold) {
        if (Integer.parseInt(fval.toString()) == Integer.parseInt(threshold.toString())) {
            return true;
        } else {
            return false;
        }
    }
}
