package org.lightgbm.predict4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lyg5623
 */
public class ParameterAlias {
    private static Map<String, String> aliasTable = new HashMap<String, String>();

    static {
        aliasTable.put("config", "config_file");
        aliasTable.put("nthread", "num_threads");
        aliasTable.put("random_seed", "seed");
        aliasTable.put("num_thread", "num_threads");
        aliasTable.put("boosting", "boosting_type");
        aliasTable.put("boost", "boosting_type");
        aliasTable.put("application", "objective");
        aliasTable.put("app", "objective");
        aliasTable.put("train_data", "data");
        aliasTable.put("train", "data");
        aliasTable.put("model_output", "output_model");
        aliasTable.put("model_out", "output_model");
        aliasTable.put("model_input", "input_model");
        aliasTable.put("model_in", "input_model");
        aliasTable.put("predict_result", "output_result");
        aliasTable.put("prediction_result", "output_result");
        aliasTable.put("valid", "valid_data");
        aliasTable.put("test_data", "valid_data");
        aliasTable.put("test", "valid_data");
        aliasTable.put("is_sparse", "is_enable_sparse");
        aliasTable.put("tranining_metric", "is_training_metric");
        aliasTable.put("train_metric", "is_training_metric");
        aliasTable.put("ndcg_at", "ndcg_eval_at");
        aliasTable.put("eval_at", "ndcg_eval_at");
        aliasTable.put("min_data_per_leaf", "min_data_in_leaf");
        aliasTable.put("min_data", "min_data_in_leaf");
        aliasTable.put("min_child_samples", "min_data_in_leaf");
        aliasTable.put("min_sum_hessian_per_leaf", "min_sum_hessian_in_leaf");
        aliasTable.put("min_sum_hessian", "min_sum_hessian_in_leaf");
        aliasTable.put("min_hessian", "min_sum_hessian_in_leaf");
        aliasTable.put("min_child_weight", "min_sum_hessian_in_leaf");
        aliasTable.put("num_leaf", "num_leaves");
        aliasTable.put("sub_feature", "feature_fraction");
        aliasTable.put("colsample_bytree", "feature_fraction");
        aliasTable.put("num_iteration", "num_iterations");
        aliasTable.put("num_tree", "num_iterations");
        aliasTable.put("num_round", "num_iterations");
        aliasTable.put("num_trees", "num_iterations");
        aliasTable.put("num_rounds", "num_iterations");
        aliasTable.put("sub_row", "bagging_fraction");
        aliasTable.put("subsample", "bagging_fraction");
        aliasTable.put("subsample_freq", "bagging_freq");
        aliasTable.put("shrinkage_rate", "learning_rate");
        aliasTable.put("tree", "tree_learner");
        aliasTable.put("num_machine", "num_machines");
        aliasTable.put("local_port", "local_listen_port");
        aliasTable.put("two_round_loading", "use_two_round_loading");
        aliasTable.put("two_round", "use_two_round_loading");
        aliasTable.put("mlist", "machine_list_file");
        aliasTable.put("is_save_binary", "is_save_binary_file");
        aliasTable.put("save_binary", "is_save_binary_file");
        aliasTable.put("early_stopping_rounds", "early_stopping_round");
        aliasTable.put("early_stopping", "early_stopping_round");
        aliasTable.put("verbosity", "verbose");
        aliasTable.put("header", "has_header");
        aliasTable.put("label", "label_column");
        aliasTable.put("weight", "weight_column");
        aliasTable.put("group", "group_column");
        aliasTable.put("query", "group_column");
        aliasTable.put("query_column", "group_column");
        aliasTable.put("ignore_feature", "ignore_column");
        aliasTable.put("blacklist", "ignore_column");
        aliasTable.put("categorical_feature", "categorical_column");
        aliasTable.put("cat_column", "categorical_column");
        aliasTable.put("cat_feature", "categorical_column");
        aliasTable.put("predict_raw_score", "is_predict_raw_score");
        aliasTable.put("predict_leaf_index", "is_predict_leaf_index");
        aliasTable.put("raw_score", "is_predict_raw_score");
        aliasTable.put("leaf_index", "is_predict_leaf_index");
        aliasTable.put("min_split_gain", "min_gain_to_split");
        aliasTable.put("topk", "top_k");
        aliasTable.put("reg_alpha", "lambda_l1");
        aliasTable.put("reg_lambda", "lambda_l2");
        aliasTable.put("num_classes", "num_class");
    }


    public static void keyAliasTransform(Map<String, String> params) {
        for (String key : params.keySet()) {
            if (aliasTable.containsKey(key))
                params.put(aliasTable.get(key), params.get(key));
        }
    }
}
