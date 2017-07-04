package org.lightgbm.predict4j.v2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.lightgbm.predict4j.SparseVector;

/**
 * @author lyg5623
 */
public class UseageTest {
    // your model path
    private static String modelPath = "LightGBM_modelv2.txt";

    @Test
    public void testv2() throws FileNotFoundException, IOException {
        String path = UseageTest.class.getClassLoader().getResource(modelPath).getPath();
        // your model path
        path = URLDecoder.decode(path, "utf8");

        Boosting boosting = Boosting.createBoosting(path);
        // predict config, just like predict.conf in LightGBM
        Map<String, String> map = new HashMap<String, String>();
        OverallConfig config = new OverallConfig();
        config.set(map);
        // create predictor
        Predictor predictor =
                new Predictor(boosting, config.io_config.num_iteration_predict, config.io_config.is_predict_raw_score,
                        config.io_config.is_predict_leaf_index, config.io_config.pred_early_stop,
                        config.io_config.pred_early_stop_freq, config.io_config.pred_early_stop_margin);

        // your data to predict
        int[] indices = {2, 6, 9};
        double[] values = {0.2, 0.4, 0.7};

        SparseVector v = new SparseVector(values, indices);
        List<Double> predicts = predictor.predict(v);
        System.out.println("predict values " + predicts.toString());

    }

}
