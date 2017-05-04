package org.lightgbm.predict4j;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public abstract class ConfigBase {
    private static final Logger logger = LoggerFactory.getLogger(ConfigBase.class);

    public abstract void set(Map<String, String> params);

    public String getString(Map<String, String> params, String name) {
        return params.get(name);
    }

    public Integer getInt(Map<String, String> params, String name) {
        String v = params.get(name);
        if (v == null)
            return null;
        if (!v.matches("\\d+")) {
            logger.error(String.format("Parameter %s should be of type int, got \"%s\"", name, v));
            return null;
        }
        return Integer.parseInt(v);
    }

    public Double getDouble(Map<String, String> params, String name) {
        String v = params.get(name);
        if (v == null)
            return null;
        try {
            double d = Double.parseDouble(v);
            return d;
        } catch (RuntimeException e) {
            return null;
        }
    }

    public Boolean getBool(Map<String, String> params, String name) {
        String value = params.get(name);
        if (value == null)
            return null;
        value = value.toLowerCase();
        if (value.equals("false") || value.equals("-")) {
            return false;
        } else if (value.equals("true") || value.equals("+")) {
            return true;
        } else {
            logger.error(
                    String.format("Parameter %s should be \"true\"/\"+\" or \"false\"/\"-\", got \"%s\"", name, value));
            return null;
        }
    }

    public Map<String, String> str2Map(String parameters) {
        Map<String, String> params = new HashMap<String, String>();
        String[] args = parameters.split("\\s+");
        for (String arg : args) {
            String[] tmpStrs = arg.split("=");
            if (tmpStrs.length == 2) {
                String key = tmpStrs[0].trim();
                String value = tmpStrs[1].trim();
                if (key.length() <= 0) {
                    continue;
                }
                params.put(key, value);
            } else if (arg.trim().length() > 0) {
                logger.warn("Unknown parameter " + arg);
            }
        }
        ParameterAlias.keyAliasTransform(params);
        return params;
    }
}
