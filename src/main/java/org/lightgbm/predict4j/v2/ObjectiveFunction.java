package org.lightgbm.predict4j.v2;

import java.io.Serializable;

/**
 * @author lyg5623
 */
public abstract class ObjectiveFunction implements Serializable {
    private static final long serialVersionUID = -8470947473512567132L;

    static ObjectiveFunction createObjectiveFunction(String str) {
        String[] strs = str.split(" ");
        String type = strs[0];
        if (type.equals("regression")) {
            return new RegressionL2loss(strs);
        } else if (type.equals("regression_l1")) {
            return new RegressionL1loss(strs);
        } else if (type.equals("huber")) {
            return new RegressionHuberLoss(strs);
        } else if (type.equals("fair")) {
            return new RegressionFairLoss(strs);
        } else if (type.equals("poisson")) {
            return new RegressionPoissonLoss(strs);
        } else if (type.equals("binary")) {
            return new BinaryLogloss(strs);
        } else if (type.equals("lambdarank")) {
            return new LambdarankNDCG(strs);
        } else if (type.equals("multiclass")) {
            return new MulticlassSoftmax(strs);
        } else if (type.equals("multiclassova")) {
            return new MulticlassOVA(strs);
        }
        return null;
    }

    boolean needAccuratePrediction() {
        return true;
    }

    void convertOutput(double[] input, double[] output) {
        output[0] = input[0];
    }
}
