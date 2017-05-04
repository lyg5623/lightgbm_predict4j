package org.lightgbm.predict4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private OverallConfig config = new OverallConfig();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        args = "config=cluster_test.conf".split("\\s+");
        String modelPath = "LightGBM_model.txt";
        Application app = new Application(args);
        String dataPath = "lightgbm_test.txt";
        String outputPath = "LightGBM_predict_result.txt";
        app.run(modelPath, dataPath, outputPath);
    }

    public Application(String[] argv) throws FileNotFoundException, IOException {
        loadParameters(argv);
    }

    private void loadParameters(String[] argv) throws FileNotFoundException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        for (int i = 0; i < argv.length; ++i) {
            String[] tmp_strs = argv[i].split("=");
            if (tmp_strs.length == 2) {
                String key = tmp_strs[0].trim();
                String value = tmp_strs[1].trim();
                if (key.length() <= 0) {
                    continue;
                }
                params.put(key, value);
            } else {
                logger.warn(String.format("Unknown parameter in command line: %s", argv[i]));
            }
        }
        // check for alias
        ParameterAlias.keyAliasTransform(params);
        // read parameters from config file
        if (params.containsKey("config_file")) {
            List<String> lines = IOUtils.readLines(new FileInputStream(params.get("config_file")));
            if (lines != null) {
                for (String line : lines) {
                    line = line.trim();
                    // remove str after "#"
                    if (line.startsWith("#"))
                        continue;
                    if (line.length() == 0) {
                        continue;
                    }
                    String[] tmp_strs = line.split("=");
                    if (tmp_strs.length == 2) {
                        String key = tmp_strs[0].trim();
                        String value = tmp_strs[1].trim();
                        if (key.length() <= 0) {
                            continue;
                        }
                        // Command-line has higher priority
                        if (!params.containsKey(key))
                            params.put(key, value);
                    } else {
                        logger.warn("Unknown parameter in config file: " + line);
                    }
                }
            }
        }
        // check for alias again
        ParameterAlias.keyAliasTransform(params);
        // load configs
        config.set(params);
        logger.info("Finished loading parameters");
    }


    private void run(String modelPath, String dataPath, String outputPath) throws FileNotFoundException, IOException {
        Boosting boosting = initPredict(modelPath);
        predict(dataPath, outputPath, boosting);
    }

    private Boosting initPredict(String modelPath) throws FileNotFoundException, IOException {
        Boosting boosting = Boosting.createBoosting(modelPath);
        logger.info("Finished initializing prediction");
        return boosting;
    }

    private void predict(String dataPath, String outputPath, Boosting boosting) throws IOException {
        boosting.setNumIterationForPred(config.getIoConfig().getNumIterationPredict());
        // create predictor
        Predictor predictor = new Predictor(boosting, config.getIoConfig().isPredictRawScore(),
                config.getIoConfig().isPredictLeafIndex());
        predictor.Predict(dataPath, outputPath);
        logger.info("Finished prediction");
    }

}
