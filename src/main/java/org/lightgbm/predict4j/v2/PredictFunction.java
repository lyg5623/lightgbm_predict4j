package org.lightgbm.predict4j.v2;

import java.util.List;

import org.lightgbm.predict4j.SparseVector;

/**
 * @author lyg5623
 */
public abstract class PredictFunction {
    abstract List<Double> predict(SparseVector vector);
}
