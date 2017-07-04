package org.lightgbm.predict4j.v2;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public class PredictionEarlyStopInstance {
    private static final Logger logger = LoggerFactory.getLogger(PredictionEarlyStopInstance.class);
    EarlyStopFunction callbackFunction; // callback function itself
    int roundPeriod;

    static PredictionEarlyStopInstance createPredictionEarlyStopInstance(String type, PredictionEarlyStopConfig config) {
        if (type.equals("none")) {
            return createNone(config);
        } else if (type.equals("multiclass")) {
            return createMulticlass(config);
        } else if (type.equals("binary")) {
            return createBinary(config);
        } else {
            throw new RuntimeException("Unknown early stopping type: " + type);
        }
    }

   static  PredictionEarlyStopInstance createBinary(PredictionEarlyStopConfig config) {
        // margin_threshold will be captured by value
        final double margin_threshold = config.marginThreshold;
        PredictionEarlyStopInstance ret = new PredictionEarlyStopInstance();
        ret.roundPeriod = config.roundPeriod;
        ret.callbackFunction = new EarlyStopFunction() {
            @Override
            public boolean callback(double[] pred, int sz) {
                if (sz != 1) {
                    logger.error("Binary early stopping needs predictions to be of length one");
                }
                double margin = 2.0 * Math.abs(pred[0]);
                if (margin > margin_threshold) {
                    return true;
                }
                return false;
            }
        };
        return ret;
    }

   static PredictionEarlyStopInstance createMulticlass(PredictionEarlyStopConfig config) {
        // margin_threshold will be captured by value
        final double margin_threshold = config.marginThreshold;
        PredictionEarlyStopInstance ret = new PredictionEarlyStopInstance();
        ret.roundPeriod = config.roundPeriod;
        ret.callbackFunction = new EarlyStopFunction() {
            @Override
            public boolean callback(double[] pred, int sz) {
                if (sz < 2) {
                    logger.error("Multiclass early stopping needs predictions to be of length two or larger");
                }
                // copy and sort
                double[] votes = new double[sz];
                for (int i = 0; i < sz; ++i) {
                    votes[i] = pred[i];
                }
                Arrays.sort(votes);

                double margin = votes[sz - 1] - votes[sz - 2];

                if (margin > margin_threshold) {
                    return true;
                }

                return false;
            }
        };
        return ret;
    }


    static PredictionEarlyStopInstance createNone(PredictionEarlyStopConfig config) {
        PredictionEarlyStopInstance ret = new PredictionEarlyStopInstance();
        ret.callbackFunction = new EarlyStopFunction() {
            @Override
            public boolean callback(double[] d, int i) {
                return false;
            }
        };
        ret.roundPeriod = Integer.MAX_VALUE;
        return ret;
    }

}


class PredictionEarlyStopConfig {
    int roundPeriod;
    double marginThreshold;
}
