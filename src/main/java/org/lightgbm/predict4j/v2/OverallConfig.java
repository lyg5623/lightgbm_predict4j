package org.lightgbm.predict4j.v2;

import java.util.Map;

/**
 * @author lyg5623
 */
public class OverallConfig extends ConfigBase {
    public IOConfig io_config = new IOConfig();

    public void set(Map<String, String> params) {
        io_config.set(params);
    }
}
