package org.lightgbm.predict4j;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public class Common {
    private static final Logger logger = LoggerFactory.getLogger(Common.class);

    public static String findFromLines(String[] lines, String keyWord) {
        for (String line : lines) {
            if (line.contains(keyWord))
                return line;
        }
        return "";
    }

    public static String join(String[] strs, String delimiter) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        StringBuilder strBuf = new StringBuilder();
        strBuf.append(strs[0]);
        for (int i = 1; i < strs.length; ++i) {
            strBuf.append(delimiter).append(strs[i]);
        }
        return strBuf.toString();
    }

    public static String join(List<Double> strs, String delimiter) {
        if (strs == null || strs.size() == 0) {
            return "";
        }
        StringBuilder strBuf = new StringBuilder();
        strBuf.append(strs.get(0));
        for (int i = 1; i < strs.size(); ++i) {
            strBuf.append(delimiter).append(strs.get(i));
        }
        return strBuf.toString();
    }

    public static String join(String[] strs, int start, int end, String delimiter) {
        if (end - start <= 0) {
            return "";
        }
        start = Math.min(start, strs.length - 1);
        end = Math.min(end, strs.length);
        StringBuilder strBuf = new StringBuilder();
        strBuf.append(strs[start]);
        for (int i = start + 1; i < end; ++i) {
            strBuf.append(delimiter).append(strs[i]);
        }
        return strBuf.toString();
    }


    public static int[] stringToArrayInt(String str, String delimiter, int n) {
        String[] strs = str.split(delimiter);
        if (strs.length != n) {
            logger.error("StringToArray error, size doesn't match.");
        }
        int[] ret = new int[n];
        for (int i = 0; i < n; ++i) {
            ret[i] = Integer.parseInt(strs[i]);
        }
        return ret;
    }

    public static double[] stringToArrayDouble(String str, String delimiter, int n) {
        String[] strs = str.split(delimiter);
        if (strs.length != n) {
            logger.error("StringToArray error, size doesn't match.");
        }
        double[] ret = new double[n];
        for (int i = 0; i < n; ++i) {
            ret[i] = Double.parseDouble(strs[i]);
        }
        return ret;
    }

    /*
     * ! \brief Do inplace softmax transformaton on p_rec \param p_rec The input/output vector of the values.
     */
    public static void softmax(double[] pRec) {
        double[] rec = pRec;
        double wmax = rec[0];
        for (int i = 1; i < rec.length; ++i) {
            wmax = Math.max(rec[i], wmax);
        }
        double wsum = 0.0f;
        for (int i = 0; i < rec.length; ++i) {
            rec[i] = Math.exp(rec[i] - wmax);
            wsum += rec[i];
        }
        for (int i = 0; i < rec.length; ++i) {
            rec[i] /= wsum;
        }
    }



    public static List<String> split(String str, String delimiters) {
        List<String> ret = new ArrayList<>();
        int i = 0;
        int pos = 0;
        while (pos < str.length()) {
            boolean met_delimiters = false;
            for (int j = 0; j < delimiters.length(); ++j) {
                if (str.charAt(pos) == delimiters.charAt(j)) {
                    met_delimiters = true;
                    break;
                }
            }
            if (met_delimiters) {
                if (i < pos) {
                    ret.add(str.substring(i, pos));
                }
                ++pos;
                i = pos;
            } else {
                ++pos;
            }
        }
        if (i < pos) {
            ret.add(str.substring(i));
        }
        return ret;
    }


    public static List<String> split(String str, char delimiter) {
        List<String> ret = new ArrayList<>();
        int i = 0;
        int pos = 0;
        while (pos < str.length()) {
            if (str.charAt(pos) == delimiter) {
                if (i < pos) {
                    ret.add(str.substring(i, pos));
                }
                ++pos;
                i = pos;
            } else {
                ++pos;
            }
        }
        if (i < pos) {
            ret.add(str.substring(i));
        }
        return ret;
    }

    public static String removeQuotationSymbol(String str) {
        int from = 0;
        while (from < str.length() && (str.charAt(from) == '\'' || str.charAt(from) == '"'))
            from++;
        int to = str.length() - 1;
        while (to >= 0 && (str.charAt(to) == '\'' || str.charAt(to) == '"'))
            to--;
        to = to + 1;
        if (from >= to)
            return "";
        else
            return str.substring(from, to);
    }

    public static void softmax(double[] input, double[] output, int len) {
        double wmax = input[0];
        for (int i = 1; i < len; ++i) {
            wmax = Math.max(input[i], wmax);
        }
        double wsum = 0.0f;
        for (int i = 0; i < len; ++i) {
            output[i] = Math.exp(input[i] - wmax);
            wsum += output[i];
        }
        for (int i = 0; i < len; ++i) {
            output[i] /= wsum;
        }
    }
}
