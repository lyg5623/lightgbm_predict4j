package org.lightgbm.predict4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.lightgbm.predict4j.Boosting;
import org.lightgbm.predict4j.OverallConfig;
import org.lightgbm.predict4j.Predictor;
import org.lightgbm.predict4j.SparseVector;

/**
 * @author lyg5623
 */
public class UseageTest {
    private static String modelPath = "LightGBM_model.txt";

    @Test
    public void test() throws FileNotFoundException, IOException {
        String path = UseageTest.class.getClassLoader().getResource(modelPath).getPath();
        path = URLDecoder.decode(path, "utf8");

        Boosting boosting = Boosting.createBoosting(path);
        // predict config, just like predict.conf in LightGBM
        Map<String, String> map = new HashMap<String, String>();
        OverallConfig config = new OverallConfig();
        config.set(map);
        Predictor predictor = new Predictor(boosting, config.getIoConfig().isPredictRawScore(),
                config.getIoConfig().isPredictLeafIndex());

        // your data to predict
        int[] indices = {2, 6, 9};
        double[] values = {0.2, 0.4, 0.7};

        SparseVector v = new SparseVector(values, indices);
        List<Double> predicts = predictor.predict(v);
        System.out.println("predict values " + predicts.toString());

    }

}
