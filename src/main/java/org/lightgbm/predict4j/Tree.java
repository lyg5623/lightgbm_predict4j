package org.lightgbm.predict4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public class Tree implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Serializable.class);
    private static final long serialVersionUID = 7056004069640778115L;
    private static List<DecisionFun> decisionFuns = new ArrayList<DecisionFun>();

    static {
        decisionFuns.add(new NumericalDecision());
        decisionFuns.add(new CategoricalDecision());

    }

    private transient int numLeaves;
    private transient double[] splitGain;
    private transient int[] internalCount;
    private transient double[] internalValue;
    private transient int[] leafCount;
    private transient int[] leafParent;

    private int[] leftChild;
    private int[] rightChild;
    private int[] splitFeatureReal;
    private double[] threshold;
    private int[] decisionType;
    private double[] leafValue;



    public Tree(String str) {
        String[] lines = str.split("\n");
        Map<String, String> keyVals = new HashMap<String, String>();
        for (String line : lines) {
            String[] tmpStrs = line.split("=");
            if (tmpStrs.length == 2) {
                String key = tmpStrs[0].trim();
                String val = tmpStrs[1].trim();
                if (key.length() > 0 && val.length() > 0) {
                    keyVals.put(key, val);
                }
            }
        }
        if (!keyVals.containsKey("num_leaves") || !keyVals.containsKey("split_feature")
                || !keyVals.containsKey("split_gain") || !keyVals.containsKey("threshold")
                || !keyVals.containsKey("left_child") || !keyVals.containsKey("right_child")
                || !keyVals.containsKey("leaf_parent") || !keyVals.containsKey("leaf_value")
                || !keyVals.containsKey("internal_value") || !keyVals.containsKey("internal_count")
                || !keyVals.containsKey("leaf_count") || !keyVals.containsKey("decision_type")) {
            logger.error("Tree model string format error");
        }
        numLeaves = Integer.parseInt(keyVals.get("num_leaves"));

        leftChild = Common.stringToArrayInt(keyVals.get("left_child"), " ", numLeaves - 1);
        rightChild = Common.stringToArrayInt(keyVals.get("right_child"), " ", numLeaves - 1);
        splitFeatureReal = Common.stringToArrayInt(keyVals.get("split_feature"), " ", numLeaves - 1);
        threshold = Common.stringToArrayDouble(keyVals.get("threshold"), " ", numLeaves - 1);
        splitGain = Common.stringToArrayDouble(keyVals.get("split_gain"), " ", numLeaves - 1);
        internalCount = Common.stringToArrayInt(keyVals.get("internal_count"), " ", numLeaves - 1);
        internalValue = Common.stringToArrayDouble(keyVals.get("internal_value"), " ", numLeaves - 1);
        decisionType = Common.stringToArrayInt(keyVals.get("decision_type"), " ", numLeaves - 1);

        leafCount = Common.stringToArrayInt(keyVals.get("leaf_count"), " ", numLeaves);
        leafParent = Common.stringToArrayInt(keyVals.get("leaf_parent"), " ", numLeaves);
        leafValue = Common.stringToArrayDouble(keyVals.get("leaf_value"), " ", numLeaves);

    }

    public int predictLeafIndex(SparseVector vector) {
        int leaf = getLeaf(vector);
        return leaf;
    }

    public double predict(SparseVector vector) {
        int leaf = getLeaf(vector);
        return leafOutput(leaf);
    }

    private double leafOutput(int leaf) {
        return leafValue[leaf];
    }

    private int getLeaf(SparseVector vector) {
        int node = 0;
        while (node >= 0) {
            if (decisionFuns.get(decisionType[node]).decision(vector.get(splitFeatureReal[node], 0), threshold[node])) {
                node = leftChild[node];
            } else {
                node = rightChild[node];
            }
        }
        return ~node;
    }

}


interface DecisionFun {
    public boolean decision(double x, double y);
}


class CategoricalDecision implements DecisionFun {
    @Override
    public boolean decision(double x, double y) {
        return x == y;
    }

}


class NumericalDecision implements DecisionFun {
    @Override
    public boolean decision(double x, double y) {
        return x <= y;
    }

}
