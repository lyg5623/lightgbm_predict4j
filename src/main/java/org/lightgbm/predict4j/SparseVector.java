package org.lightgbm.predict4j;

import java.util.Arrays;

/**
 * @author lyg5623
 */
public class SparseVector {
    private double[] values;
    private int[] indices;

    public SparseVector(double[] values, int[] indices) {
        this.values = values;
        this.indices = indices;
    }

    public double get(int idx, double defaultValue) {
        int pos = Arrays.binarySearch(indices, idx);
        if (pos < 0)
            return defaultValue;
        return values[pos];
    }
}
