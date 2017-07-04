package org.lightgbm.predict4j.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public class BinaryLogloss extends ObjectiveFunction {
    private static final long serialVersionUID = 5231147900456677657L;
    private static final Logger logger = LoggerFactory.getLogger(BinaryLogloss.class);
    /* ! \brief Sigmoid parameter */
    double sigmoid;

    BinaryLogloss(String[] strs) {
        sigmoid = -1;
        for (String str : strs) {
            String[] tokens = str.split(":");
            if (tokens.length == 2) {
                if (tokens[0].equals("sigmoid")) {
                    sigmoid = Double.parseDouble(tokens[1]);
                }
            }
        }
        if (sigmoid <= 0.0) {
            logger.error("Sigmoid parameter " + sigmoid + " should be greater than zero");
        }
    }

    void convertOutput(double[] input, double[] output) {
        output[0] = 1.0f / (1.0f + Math.exp(-sigmoid * input[0]));
    }
}
