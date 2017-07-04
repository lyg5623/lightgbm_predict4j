package org.lightgbm.predict4j.v2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lightgbm.predict4j.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public abstract class ConfigBase {
    private static final Logger logger = LoggerFactory.getLogger(ConfigBase.class);

    public abstract void set(Map<String, String> params);

    /*
     * ! \brief Get string value by specific name of key \param params Store the key and value for params \param name
     * Name of key \param out Value will assign to out if key exists \return True if key exists
     */
    String getString(Map<String, String> params, String name) {
        return params.get(name);
    }

    /*
     * ! \brief Get int value by specific name of key \param params Store the key and value for params \param name Name
     * of key \param out Value will assign to out if key exists \return True if key exists
     */
    Integer getInt(Map<String, String> params, String name) {
        String s = params.get(name);
        if (s == null)
            return null;
        return Integer.valueOf(s);
    }

    /*
     * ! \brief Get double value by specific name of key \param params Store the key and value for params \param name
     * Name of key \param out Value will assign to out if key exists \return True if key exists
     */
    Double getDouble(Map<String, String> params, String name) {
        String s = params.get(name);
        if (s == null)
            return null;
        return Double.valueOf(s);
    }

    /*
     * ! \brief Get bool value by specific name of key \param params Store the key and value for params \param name Name
     * of key \param out Value will assign to out if key exists \return True if key exists
     */
    Boolean getBool(Map<String, String> params, String name) {
        String s = params.get(name);
        if (s == null)
            return null;
        return Boolean.valueOf(s);
    }

    static Map<String, String> str2Map(String parameters) {
        Map<String, String> params = new HashMap<>();
        List<String> args = Common.split(parameters, " \t\n\r");
        for (String arg : args) {
            List<String> tmp_strs = Common.split(arg, '=');
            if (tmp_strs.size() == 2) {
                String key = Common.removeQuotationSymbol(tmp_strs.get(0).trim());
                String value = Common.removeQuotationSymbol(tmp_strs.get(1).trim());
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
