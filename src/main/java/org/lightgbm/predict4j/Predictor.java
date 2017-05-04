package org.lightgbm.predict4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyg5623
 */
public class Predictor {
    /* ! \brief Boosting model */
    private Boosting boosting;
    /* ! \brief function for prediction */
    private PredictFunction predictFun;

    class PredictFunction1 extends PredictFunction {
        @Override
        public List<Double> predict(SparseVector vector) {
            // get result for leaf index
            List<Integer> result = boosting.predictLeafIndex(vector);
            List<Double> ret = new ArrayList<Double>();
            for (int x : result)
                ret.add((double) x);
            return ret;
        }

    }

    class PredictFunction2 extends PredictFunction {
        @Override
        public List<Double> predict(SparseVector vector) {
            // get result without sigmoid transformation
            return boosting.predictRaw(vector);
        }

    }

    class PredictFunction3 extends PredictFunction {
        @Override
        public List<Double> predict(SparseVector vector) {
            return boosting.predict(vector);
        }

    }


    public Predictor(Boosting boosting, boolean isRawScore, boolean isPredictLeafIndex) {
        this.boosting = boosting;

        if (isPredictLeafIndex) {
            predictFun = new PredictFunction1();
        } else {
            if (isRawScore) {
                predictFun = new PredictFunction2();
            } else {
                predictFun = new PredictFunction3();
            }
        }
    }


    /*
     * ! \brief predicting on data, then saving result to disk \param data_filename Filename of data \param has_label
     * True if this data contains label \param result_filename Filename of output result
     */
    void Predict(String data_filename, String result_filename) throws IOException {
        BufferedWriter result_file = new BufferedWriter(new FileWriter(result_filename));

        BufferedReader br = new BufferedReader(new FileReader(data_filename));
        String line;
        List<Pair<Integer, Double>> oneline_features = new ArrayList<Pair<Integer, Double>>();
        while ((line = br.readLine()) != null) {
            if (line.length() == 0)
                continue;
            oneline_features.clear();
            String[] ss = line.split("\\s+");
            for (int i = 1; i < ss.length; i++) {
                String[] ss2 = ss[i].split(":");
                Pair<Integer, Double> p = new Pair<Integer, Double>();
                p.f = Integer.parseInt(ss2[0]);
                p.s = Double.parseDouble(ss2[1]);
                oneline_features.add(p);
            }
            double[] values = new double[oneline_features.size()];
            int[] indices = new int[oneline_features.size()];
            for (int i = 0; i < oneline_features.size(); i++) {
                values[i] = oneline_features.get(i).s;
                indices[i] = oneline_features.get(i).f;
            }
            SparseVector sv = new SparseVector(values, indices);
            List<Double> predicts = predictFun.predict(sv);
            String pred_result = Common.join(predicts, "\t");
            result_file.write(pred_result + "\r\n");
        }
        br.close();
        result_file.close();
    }


    public List<Double> predict(SparseVector sparseVector) {
        return predictFun.predict(sparseVector);
    }

}
