package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.constant;

/**
 * 数据写入的运行时异常。
 *
 * @author liujun
 * @version 0.0.1
 */
public class DataWriteException extends RuntimeException {

  public DataWriteException(String message, Throwable cause) {
    super(message, cause);
  }
}
