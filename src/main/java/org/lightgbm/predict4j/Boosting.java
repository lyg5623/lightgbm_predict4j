package org.lightgbm.predict4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public abstract class Boosting implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Boosting.class);
    private static final long serialVersionUID = -661844499486913306L;

    public static Boosting createBoosting(String modelPath) throws FileNotFoundException, IOException {
        Boosting boosting = null;
        String type = getBoostingTypeFromModelFile(modelPath);
        if (type.equals("tree")) {
            boosting = new GBDT();
        } else {
            logger.error("unknow submodel type in model file " + modelPath);
        }
        loadFileToBoosting(boosting, modelPath);
        return boosting;
    }


    private static boolean loadFileToBoosting(Boosting boosting, String modelPath)
            throws FileNotFoundException, IOException {
        if (boosting != null) {
            StringBuilder sb = new StringBuilder();
            List<String> lines = IOUtils.readLines(new FileInputStream(modelPath));
            for (String line : lines) {
                sb.append(line).append("\n");
            }
            if (!boosting.loadModelFromString(sb.toString()))
                return false;
        }

        return true;
    }

    public abstract boolean loadModelFromString(String modelStr);

    private static String getBoostingTypeFromModelFile(String modelPath) throws FileNotFoundException, IOException {
        List<String> lines = IOUtils.readLines(new FileInputStream(modelPath));
        return lines.get(0);
    }

    public abstract void setNumIterationForPred(int numIteration);

    public abstract List<Integer> predictLeafIndex(SparseVector vector);

    public abstract List<Double> predictRaw(SparseVector vector);

    public abstract List<Double> predict(SparseVector vector);
}
