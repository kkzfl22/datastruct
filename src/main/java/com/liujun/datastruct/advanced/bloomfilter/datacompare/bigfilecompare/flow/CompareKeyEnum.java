package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.flow;

/**
 * 数据对比的枚举信息
 *
 * @author liujun
 * @version 0.0.1
 */
public enum CompareKeyEnum {

  /** 用指定相关的文件搜索路径 */
  INPUT_BIGFILE_PATH("input_bigFile_path"),

  /** 用于指定对比的转换方法，主要是从行数据与实体之间的转换 */
  INPUT_COMPARE_PARSE("input_compare_parse"),

  /** 用于指定对比的主键对比与全量对比的键的接口实现 */
  INPUT_COMPARE_KEY("input_compare_key"),

  /** 对比中行数据的相关的java的实体信息 */
  INPUT_COMPARE_ENTITY_CLASS("input_compare_entity_class"),

  /** 原始数据去除重复并合并为有序 */
  PROC_REMOVE_DUPLICATE_OUTPUT_SRC("proc_remove_duplication_src"),

  /** 数据目标去除重复并合并为有序数据 */
  PROC_REMOVE_DUPLICATE_OUTPUT_TARGET("proc_remove_duplication_target"),

  /** 处理处理器的实例对象 */
  PROC_COMPARE_INSTANCE_OBJECT("proc_compare_instance_object"),

  /** 原始数据的读取器操作 */
  PROC_COMPARE_MANY_READER_SRC("proc_compare_many_reader_src"),

  /** 目标数据读取器 */
  PROC_COMPARE_MANY_READER_TARGET("proc_compare_may_reader_target"),

  /** 数据对比输出操作 */
  PROC_COMPARE_MANY_OUTPUT("proc_compare_many_output"),
  ;

  private String key;

  CompareKeyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CompareKeyEnum{");
    sb.append("key='").append(key).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
