package org.lightgbm.predict4j;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public class GBDT extends Boosting {
    private static final Logger logger = LoggerFactory.getLogger(GBDT.class);
    private static final long serialVersionUID = -7503007999906064466L;
    private transient int maxFeatureIdx = 0;
    private transient String[] featureNames;
    private transient List<String> featureInfos = new ArrayList<String>();

    private int numClass = 1;
    private double sigmoid = 1.0;
    private int numIterationForPred = 0;
    private List<Tree> models = new ArrayList<Tree>();


    /*
     * (non-Javadoc)
     * 
     * @see lightgbm.Boosting#LoadModelFromString(java.lang.String)
     */
    @Override
    public boolean loadModelFromString(String modelStr) {

        // use serialized string to restore this object
        models.clear();
        String[] lines = modelStr.split("\n");

        // get number of classes
        String line = Common.findFromLines(lines, "num_class=");
        if (line.length() > 0) {
            numClass = Integer.parseInt(line.split("=")[1]);
        } else {
            logger.error("Model file doesn't specify the number of classes");
            return false;
        }

        // get max_feature_idx first
        line = Common.findFromLines(lines, "max_feature_idx=");
        if (line.length() > 0) {
            maxFeatureIdx = Integer.parseInt(line.split("=")[1]);
        } else {
            logger.error("Model file doesn't specify max_feature_idx");
            return false;
        }
        // get sigmoid parameter
        line = Common.findFromLines(lines, "sigmoid=");
        if (line.length() > 0) {
            sigmoid = Double.parseDouble(line.split("=")[1]);
        } else {
            sigmoid = -1.0f;
        }
        // get feature names
        line = Common.findFromLines(lines, "feature_names=");
        if (line.length() > 0) {
            featureNames = line.substring("feature_names=".length()).split(" ");
            if (featureNames.length != maxFeatureIdx + 1) {
                logger.error("Wrong size of feature_names");
                return false;
            }
        } else {
            logger.error("Model file doesn't contain feature names");
            return false;
        }



        // load feature information
        {
            int finfoLineIdx = findStringLineno(lines, "feature information:");

            if (finfoLineIdx >= lines.length) {
                logger.error("Model file doesn't contain feature information");
                return false;
            }

            // search for each feature name
            for (int i = 0; i < maxFeatureIdx + 1; i++) {
                String featName = featureNames[i];
                int lineIdx = findStringLineno(lines, featName + "=", finfoLineIdx + 1);
                if (lineIdx >= lines.length) {
                    logger.error("Model file doesn't contain feature information for feature " + featName);
                    return false;
                }

                String thisLine = lines[lineIdx];
                featureInfos.add(thisLine.substring((featName + "=").length()));
            }
        }

        // get tree models
        int i = 0;
        while (i < lines.length) {
            int findPos = lines[i].indexOf("Tree=");
            if (findPos >= 0) {
                ++i;
                int start = i;
                while (i < lines.length && !lines[i].contains("Tree=")) {
                    ++i;
                }
                int end = i;
                String treeStr = Common.join(lines, start, end, "\n");
                Tree newTree = new Tree(treeStr);
                models.add(newTree);
            } else {
                ++i;
            }
        }
        logger.info(String.format("Finished loading %d models", models.size()));
        numIterationForPred = models.size() / numClass;

        return true;
    }

    int findStringLineno(String[] lines, String str) {
        return findStringLineno(lines, str, 0);
    }

    // returns offset, or lines.size() if not found.
    int findStringLineno(String[] lines, String str, int startLine) {
        int i = startLine;
        while (i < lines.length) {
            if (lines[i].contains(str))
                return i;
            ++i;
        }
        return i;
    }



    public void setNumIterationForPred(int numIteration) {
        numIterationForPred = models.size() / numClass;
        if (numIteration > 0) {
            numIterationForPred = Math.min(numIteration, numIterationForPred);
        }
    }


    public List<Integer> predictLeafIndex(SparseVector vector) {
        List<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < numIterationForPred; ++i) {
            for (int j = 0; j < numClass; ++j) {
                ret.add(models.get(i * numClass + j).predictLeafIndex(vector));
            }
        }
        return ret;
    }

    public List<Double> predictRaw(SparseVector vector) {
        double[] ret = new double[numClass];
        for (int i = 0; i < numIterationForPred; ++i) {
            for (int j = 0; j < numClass; ++j) {
                ret[j] += models.get(i * numClass + j).predict(vector);
            }
        }
        List<Double> retlist = new ArrayList<Double>();
        for (double d : ret)
            retlist.add(d);
        return retlist;
    }


    public List<Double> predict(SparseVector vector) {
        double[] ret = new double[numClass];
        for (int i = 0; i < numIterationForPred; ++i) {
            for (int j = 0; j < numClass; ++j) {
                ret[j] += models.get(i * numClass + j).predict(vector);
            }
        }
        // if need sigmoid transform
        if (sigmoid > 0 && numClass == 1) {
            ret[0] = 1.0f / (1.0f + Math.exp(-2.0f * sigmoid * ret[0]));
        } else if (numClass > 1) {
            Common.softmax(ret);
        }
        List<Double> retlist = new ArrayList<Double>();
        for (double d : ret)
            retlist.add(d);
        return retlist;
    }

}
