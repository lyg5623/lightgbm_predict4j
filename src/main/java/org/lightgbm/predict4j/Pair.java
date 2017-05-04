package org.lightgbm.predict4j;

/**
 * @author lyg5623
 */
public class Pair<F, S> {
    F f;
    S s;

    public String toString() {
        return f + ":" + s;
    }
}
