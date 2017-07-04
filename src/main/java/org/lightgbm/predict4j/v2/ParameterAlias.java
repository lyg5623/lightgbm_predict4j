package org.lightgbm.predict4j.v2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lyg5623
 */
public class ParameterAlias {
    private static final Logger logger = LoggerFactory.getLogger(ParameterAlias.class);
    private static Map<String, String> aliasTable = new HashMap<>();
    private static Set<String> parameterSet = new HashSet<>();

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
        aliasTable.put("enable_sparse", "is_enable_sparse");
        aliasTable.put("pre_partition", "is_pre_partition");
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
        aliasTable.put("unbalanced_sets", "is_unbalance");
        aliasTable.put("bagging_fraction_seed", "bagging_seed");
        aliasTable.put("num_boost_round", "num_iterations");

        parameterSet.add("config");
        parameterSet.add("config_file");
        parameterSet.add("task");
        parameterSet.add("device");
        parameterSet.add("num_threads");
        parameterSet.add("seed");
        parameterSet.add("boosting_type");
        parameterSet.add("objective");
        parameterSet.add("data");
        parameterSet.add("output_model");
        parameterSet.add("input_model");
        parameterSet.add("output_result");
        parameterSet.add("valid_data");
        parameterSet.add("is_enable_sparse");
        parameterSet.add("is_pre_partition");
        parameterSet.add("is_training_metric");
        parameterSet.add("ndcg_eval_at");
        parameterSet.add("min_data_in_leaf");
        parameterSet.add("min_sum_hessian_in_leaf");
        parameterSet.add("num_leaves");
        parameterSet.add("feature_fraction");
        parameterSet.add("num_iterations");
        parameterSet.add("bagging_fraction");
        parameterSet.add("bagging_freq");
        parameterSet.add("learning_rate");
        parameterSet.add("tree_learner");
        parameterSet.add("num_machines");
        parameterSet.add("local_listen_port");
        parameterSet.add("use_two_round_loading");
        parameterSet.add("machine_list_file");
        parameterSet.add("is_save_binary_file");
        parameterSet.add("early_stopping_round");
        parameterSet.add("verbose");
        parameterSet.add("has_header,label_column");
        parameterSet.add("weight_column");
        parameterSet.add("group_column");
        parameterSet.add("ignore_column");
        parameterSet.add("categorical_column");
        parameterSet.add("is_predict_raw_score");
        parameterSet.add("is_predict_leaf_index");
        parameterSet.add("min_gain_to_split");
        parameterSet.add("top_k");
        parameterSet.add("lambda_l1");
        parameterSet.add("lambda_l2");
        parameterSet.add("num_class");
        parameterSet.add("is_unbalance");
        parameterSet.add("max_depth");
        parameterSet.add("subsample_for_bin");
        parameterSet.add("max_bin");
        parameterSet.add("bagging_seed");
        parameterSet.add("drop_rate");
        parameterSet.add("skip_drop");
        parameterSet.add("max_drop");
        parameterSet.add("uniform_drop");
        parameterSet.add("xgboost_dart_mode");
        parameterSet.add("drop_seed");
        parameterSet.add("top_rate");
        parameterSet.add("other_rate");
        parameterSet.add("min_data_in_bin");
        parameterSet.add("data_random_seed");
        parameterSet.add("bin_construct_sample_cnt");
        parameterSet.add("num_iteration_predict");
        parameterSet.add("pred_early_stop");
        parameterSet.add("pred_early_stop_freq");
        parameterSet.add("pred_early_stop_margin");
        parameterSet.add("use_missing");
        parameterSet.add("sigmoid");
        parameterSet.add("huber_delta");
        parameterSet.add("fair_c");
        parameterSet.add("poission_max_delta_step");
        parameterSet.add("scale_pos_weight");
        parameterSet.add("boost_from_average");
        parameterSet.add("max_position");
        parameterSet.add("label_gain");
        parameterSet.add("metric");
        parameterSet.add("metric_freq");
        parameterSet.add("time_out");
        parameterSet.add("gpu_platform_id");
        parameterSet.add("gpu_device_id");
        parameterSet.add("gpu_use_dp");
        parameterSet.add("convert_model");
        parameterSet.add("convert_model_language");
        parameterSet.add("feature_fraction_seed");
        parameterSet.add("enable_bundle");
        parameterSet.add("data_filename");
        parameterSet.add("valid_data_filenames");
        parameterSet.add("snapshot_freq");
        parameterSet.add("verbosity");
        parameterSet.add("sparse_threshold");
        parameterSet.add("enable_load_from_binary_file");
        parameterSet.add("max_conflict_rate");
        parameterSet.add("poisson_max_delta_step");
        parameterSet.add("gaussian_eta");
        parameterSet.add("histogram_pool_size");
        parameterSet.add("output_freq");
        parameterSet.add("is_provide_training_metric");
        parameterSet.add("machine_list_filename");
    }

    public static void keyAliasTransform(Map<String, String> params) {
        Map<String, String> tmp_map = new HashMap<>();
        for (String key : params.keySet()) {
            if (aliasTable.containsKey(key))
                tmp_map.put(aliasTable.get(key), params.get(key));
            else if (!parameterSet.contains(key))
                logger.error("Unknown parameter: " + key);
        }
        for (String key : tmp_map.keySet()) {
            if (!params.containsKey(key))
                params.put(key, tmp_map.get(key));
        }
    }
}
