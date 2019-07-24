package com.liujun.utils;

import com.liujun.constant.TestPropertyEnum;
import com.liujun.datastruct.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 只读属性文件，
 *
 * @author liujun
 * @date 2014年6月10日
 * @vsersion 0.0.1
 */
public class PropertiesUtils {

  private static final String DEF_FILENAME = "config.properties";

  private Properties prop = new Properties();

  private static final PropertiesUtils PROINSTANCE = new PropertiesUtils();

  public PropertiesUtils() {
    // loader default property fiile application.properties
    loadProperties(DEF_FILENAME);
  }

  public static PropertiesUtils getInstance() {
    return PROINSTANCE;
  }

  public void loadProperties(String fileName) {
    if (prop.isEmpty()) {
      InputStream in = null;
      InputStreamReader read = null;

      try {
        in = PropertiesUtils.class.getResourceAsStream(fileName);
        if (in == null) {
          in = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
        }
        if (in == null) {
          in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        }

        if (in != null) {
          read = new InputStreamReader(in, StandardCharsets.UTF_8);
          prop.load(read);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      } finally {
        IOUtils.Close(read);
        IOUtils.Close(in);
      }
    }
  }

  /**
   * 获取值的方法
   *
   * @param key key信息
   * @return 返回
   */
  public String getValue(TestPropertyEnum key) {
    return prop.getProperty(key.getKey());
  }

  /**
   * 获取对象类型的数据
   *
   * @param key key信息
   * @return
   */
  public int getIntValue(TestPropertyEnum key) {

    String value = prop.getProperty(key.getKey());

    if (null != value) {
      return Integer.parseInt(value);
    }

    return 0;
  }

  /**
   * 获取值带默认的方法
   *
   * @param key key信息
   * @param defValue 默认值
   * @return
   */
  public int getIntegerValueOrDef(TestPropertyEnum key, int defValue) {
    String value = prop.getProperty(key.getKey());

    if (StringUtils.isNotEmpty(value)) {
      value = value.trim();
      if (StringUtils.isNumeric(value)) {
        return Integer.parseInt(value);
      }
      return defValue;
    } else {
      return defValue;
    }
  }

  /**
   * 获取值带默认的方法
   *
   * @param key key信息
   * @param defValue 默认值
   * @return
   */
  public String getStringValueOrDef(TestPropertyEnum key, String defValue) {
    String value = prop.getProperty(key.getKey());
    if (StringUtils.isNotEmpty(value)) {
      return value.trim();
    } else {
      return defValue;
    }
  }

  /**
   * 获取值带默认的方法
   *
   * @param key key信息
   * @param defValue 默认值
   * @return
   */
  public boolean getBooleanValueOrDef(TestPropertyEnum key, boolean defValue) {
    String value = prop.getProperty(key.getKey());

    if (StringUtils.isNotEmpty(value)) {
      value = value.trim();
      return Boolean.parseBoolean(value);
    } else {
      return defValue;
    }
  }
}
