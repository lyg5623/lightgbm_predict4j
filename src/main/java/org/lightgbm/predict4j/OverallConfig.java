package org.lightgbm.predict4j;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public class OverallConfig extends ConfigBase {
    private static final Logger logger = LoggerFactory.getLogger(OverallConfig.class);
    private IOConfig ioConfig = new IOConfig();
    private String boostingType = "gbdt";


    public void set(Map<String, String> params) {
        // load main config types

        getBoostingType(params);

        // sub-config setup
        ioConfig.set(params);

    }


    private void getBoostingType(Map<String, String> params) {
        String value = getString(params, "boosting_type");
        if (value != null) {
            value = value.toLowerCase();
            if (value.equals("gbdt") || value.equals("gbrt")) {
                boostingType = "gbdt";
            } else if (value.equals("dart")) {
                boostingType = "dart";
            } else {
                logger.warn("Unknown boosting type " + value);
            }
        }
    }


    public IOConfig getIoConfig() {
        return ioConfig;
    }


    public String getBoostingType() {
        return boostingType;
    }



}
