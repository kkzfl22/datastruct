package com.liujun.datastruct.datacompare.bigfilecompare.flow;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文件的容器对象，用于进行流程相关的数据存储操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/09/11
 */
public class ContextContainer {

  /** 对数存储对象 */
  private Map<String, Object> commonParam = new HashMap<>();

  /**
   * 向公共参数中添加参数
   *
   * @param key 存入参数的key
   * @param value 存入参数的值
   */
  public void put(String key, Object value) {
    this.commonParam.put(key, value);
  }

  /**
   * 获取java对象的方法
   *
   * @param key 存入key
   * @return 获取的数据信息
   */
  public Object get(String key) {
    return this.commonParam.get(key);
  }

  /**
   * 进行对象的移除操作
   *
   * @param key
   */
  public void remove(String key) {
    this.commonParam.remove(key);
  }

  /** 清空参数 */
  public void cleanParam() {
    this.commonParam.clear();
  }

  /**
   * 检查key是否存在
   *
   * @param key
   * @return
   */
  public boolean containsKey(String key) {
    return this.commonParam.containsKey(key);
  }

  /**
   * 当值不存在时需要返回返回值的类型
   *
   * @param key 存入的key信息
   * @param defaultValue 默认的值信息，需要与存入的类型相同，否则会报错误
   * @return
   */
  public Object getValueOrDef(String key, Object defaultValue) {
    Object value = this.commonParam.get(key);
    if (null != value) {
      return value;
    } else {
      return defaultValue;
    }
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ContextContainer{");
    sb.append("commonParam=").append(commonParam);
    sb.append('}');
    return sb.toString();
  }
}
