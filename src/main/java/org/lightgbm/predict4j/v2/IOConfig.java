package org.lightgbm.predict4j.v2;

import java.util.Map;


/**
 * @author lyg5623
 */
public class IOConfig extends ConfigBase {
    public int num_iteration_predict = -1;
    public boolean is_predict_leaf_index = false;
    public boolean is_predict_raw_score = false;
    /* ! \brief Set to true if want to use early stop for the prediction */
    public boolean pred_early_stop = false;
    /* ! \brief Frequency of checking the pred_early_stop */
    public int pred_early_stop_freq = 10;
    /* ! \brief Threshold of margin of pred_early_stop */
    public double pred_early_stop_margin = 10.0;

    public void set(Map<String, String> params) {
        Integer x;
        Boolean b;
        Double d;
        x = getInt(params, "num_iteration_predict");
        if (x != null)
            num_iteration_predict = x;
        b = getBool(params, "is_predict_raw_score");
        if (b != null)
            is_predict_raw_score = b;
        b = getBool(params, "is_predict_leaf_index");
        if (b != null)
            is_predict_leaf_index = b;

        b = getBool(params, "pred_early_stop");
        if (b != null)
            pred_early_stop = b;
        x = getInt(params, "pred_early_stop_freq");
        if (x != null)
            pred_early_stop_freq = x;
        d = getDouble(params, "pred_early_stop_margin");
        if (d != null)
            pred_early_stop_margin = d;
    }
}
