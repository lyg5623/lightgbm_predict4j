package org.lightgbm.predict4j.v2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.lightgbm.predict4j.Common;
import org.lightgbm.predict4j.SparseVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public class Tree implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Tree.class);
    private static final long serialVersionUID = 2538421701128456080L;
    private final static double kMissingValueRange = 1e-20f;
    private static Decision<Double>[] decision_funs;

    static {
        decision_funs = new Decision[2];
        decision_funs[0] = new NumericalDecision<Double>();
        decision_funs[1] = new CategoricalDecision<Double>();
    }

    /* ! \brief Number of current levas */
    private int numLeaves;
    /* ! \brief A non-leaf node's left child */
    private int[] leftChild;
    /* ! \brief A non-leaf node's right child */
    private int[] rightChild;
    /* ! \brief A non-leaf node's split feature, the original index */
    private int[] splitFeature;
    private double[] threshold;
    /* ! \brief Decision type, 0 for '<='(numerical feature), 1 for 'is'(categorical feature) */
    private int[] decisionType;
    /* ! \brief Default values for the na/0 feature values */
    private double[] defaultValue;
    /* ! \brief Output of leaves */
    private double[] leafValue;
    private boolean hasCategorical;

    Tree(int maxLeaves) {
        numLeaves = 0;
        leftChild = new int[maxLeaves - 1];
        rightChild = new int[maxLeaves - 1];
        splitFeature = new int[maxLeaves - 1];
        threshold = new double[maxLeaves - 1];
        decisionType = new int[maxLeaves - 1];
        defaultValue = new double[maxLeaves - 1];
        leafValue = new double[maxLeaves];
        numLeaves = 1;
        hasCategorical = false;
    }


    Tree(String str) {
        String[] lines = str.split("\r?\n");
        Map<String, String> key_vals = new HashMap<>();
        for (String line : lines) {
            String[] tmp_strs = line.split("=");
            if (tmp_strs.length == 2) {
                String key = tmp_strs[0].trim();
                String val = tmp_strs[1].trim();
                if (key.length() > 0 && val.length() > 0) {
                    key_vals.put(key, val);
                }
            }
        }
        if (!key_vals.containsKey("num_leaves")) {
            logger.error("Tree model should contain num_leaves field.");
        }
        numLeaves = Integer.parseInt(key_vals.get("num_leaves"));

        if (numLeaves <= 1) {
            return;
        }

        if (key_vals.containsKey("left_child")) {
            leftChild = Common.stringToArrayInt(key_vals.get("left_child"), " ", numLeaves - 1);
        } else {
            logger.error("Tree model string format error, should contain left_child field");
        }

        if (key_vals.containsKey("right_child")) {
            rightChild = Common.stringToArrayInt(key_vals.get("right_child"), " ", numLeaves - 1);
        } else {
            logger.error("Tree model string format error, should contain right_child field");
        }

        if (key_vals.containsKey("split_feature")) {
            splitFeature = Common.stringToArrayInt(key_vals.get("split_feature"), " ", numLeaves - 1);
        } else {
            logger.error("Tree model string format error, should contain split_feature field");
        }

        if (key_vals.containsKey("threshold")) {
            threshold = Common.stringToArrayDouble(key_vals.get("threshold"), " ", numLeaves - 1);
        } else {
            logger.error("Tree model string format error, should contain threshold field");
        }

        if (key_vals.containsKey("default_value")) {
            defaultValue = Common.stringToArrayDouble(key_vals.get("default_value"), " ", numLeaves - 1);
        } else {
            logger.debug("Assuming default values as 0.0");
            this.defaultValue = new double[threshold.length];
            for(int i=0; i<threshold.length; i++) {
                this.defaultValue[i] = 0.0;
            }
        }

        if (key_vals.containsKey("leaf_value")) {
            leafValue = Common.stringToArrayDouble(key_vals.get("leaf_value"), " ", numLeaves);
        } else {
            logger.error("Tree model string format error, should contain leaf_value field");
        }

        if (key_vals.containsKey("decision_type")) {
            decisionType = Common.stringToArrayInt(key_vals.get("decision_type"), " ", numLeaves - 1);
        } else {
            decisionType = new int[numLeaves - 1];
        }

        if (key_vals.containsKey("has_categorical")) {
            int t = 0;
            t = Integer.parseInt(key_vals.get("has_categorical"));
            hasCategorical = t > 0;
        } else {
            hasCategorical = false;
        }

    }

    int PredictLeafIndex(SparseVector feature_values) {
        if (numLeaves > 1) {
            int leaf = getLeaf(feature_values);
            return leaf;
        } else {
            return 0;
        }
    }

    double Predict(SparseVector feature_values) {
        if (numLeaves > 1) {
            int leaf = getLeaf(feature_values);
            return leafOutput(leaf);
        } else {
            return 0.0f;
        }
    }

    private double leafOutput(int leaf) {
        return leafValue[leaf];
    }

    private int getLeaf(SparseVector feature_values) {
        int node = 0;
        if (hasCategorical) {
            while (node >= 0) {
                double fval = defaultValueForZero(feature_values.get(splitFeature[node], 0), kMissingValueRange,
                        defaultValue[node]);
                if (decision_funs[decisionType[node]].decision(fval, threshold[node])) {
                    node = leftChild[node];
                } else {
                    node = rightChild[node];
                }
            }
        } else {
            while (node >= 0) {
                double fval = defaultValueForZero(feature_values.get(splitFeature[node], 0), kMissingValueRange,
                        defaultValue[node]);
                if (new NumericalDecision<Double>().decision(fval, threshold[node])) {
                    node = leftChild[node];
                } else {
                    node = rightChild[node];
                }
            }
        }
        return ~node;
    }

    private static double defaultValueForZero(double fval, double zero, double out) {
        if (fval > -zero && fval <= zero) {
            return out;
        } else {
            return fval;
        }
    }
}
