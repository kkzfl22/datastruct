package com.liujun.datastruct.datacompare.bigfilecompare.constant;

/**
 * 数据对比的配制操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class CompareConfig {

  /** 文本文件统一的文件后缀名 */
  public static final String TEXT_SUFFIX_NAME = ".txt";

  /** 文本文件修改的文件统一后缀名 */
  public static final String TEXT_SUFFIX_NAME_ING = ".ing";

  /** 删除临时文件 */
  public static final boolean DELETE_TMP_FILE_FLAG = false;

  /** 保存布隆过滤器 */
  public static final boolean SAVE_BLOOM_FILTER = true;
}
