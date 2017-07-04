package org.lightgbm.predict4j.v2;

/**
 * @author lyg5623
 */
public abstract class Decision<T> {
   abstract boolean decision(T fval, T threshold);
}
