package org.lightgbm.predict4j;

import java.util.Map;

/**
 * @author lyg5623
 */
public class IOConfig extends ConfigBase {
    private int numIterationPredict = -1;
    private boolean predictLeafIndex = false;
    private boolean predictRawScore = false;


    public void set(Map<String, String> params) {
        Integer i;
        i = getInt(params, "num_iteration_predict");
        if (i != null)
            numIterationPredict = i;
        Boolean b;
        b = getBool(params, "is_predict_raw_score");
        if (b != null)
            predictRawScore = b;
        b = getBool(params, "is_predict_leaf_index");
        if (b != null)
            predictLeafIndex = b;
    }


    public int getNumIterationPredict() {
        return numIterationPredict;
    }


    public boolean isPredictLeafIndex() {
        return predictLeafIndex;
    }


    public boolean isPredictRawScore() {
        return predictRawScore;
    }

}
