package com.liujun.constant;

/**
 * 属性文件的枚举对象
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/01
 */
public enum TestPropertyEnum {

  /** 测试输出的基础路径 */
  TEST_BASE_PATH("test.base.path"),
  ;

  private String key;

  TestPropertyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
