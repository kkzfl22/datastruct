package com.liujun.datastruct.utils;

import com.config.Symbol;
import org.apache.commons.lang3.StringUtils;

/**
 * 文件的路径处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/18
 */
public class PathUtils {

  private static final String LEFT_LINE = "//";

  private static final String RIGHT_ONE = "\\";

  private static final String RIGHT_LINE = "\\\\";

  /**
   * 进行路径的处理操作
   *
   * <p>主要用于\\与//的替换操作
   *
   * @param path
   * @return
   */
  public static String PathProc(String path) {
    if (StringUtils.isEmpty(path)) {
      return path;
    }

    while (path.indexOf(LEFT_LINE) != -1 || path.indexOf(RIGHT_ONE) != -1) {
      path = path.replaceAll(LEFT_LINE, Symbol.PATH);
      path = path.replaceAll(RIGHT_LINE,  Symbol.PATH);
    }

    return path;
  }

  /**
   * 获取路径的方法
   *
   * @param path
   * @return
   */
  public static String GetPath(String path) {
    int indexOf = path.lastIndexOf( Symbol.PATH);

    if (indexOf != -1) {
      path = path.substring(0, indexOf);
      return path;
    }

    return path;
  }

  /**
   * 获取相对路径的方法
   *
   * @param path
   * @return
   */
  public static String GetRelativePath(String path) {
    int indexOf = path.lastIndexOf( Symbol.PATH);

    if (indexOf != -1) {
      path = path.substring(0, indexOf);
      return path;
    }

    return  Symbol.PATH;
  }

  /**
   * 获取路径的方法
   *
   * @param path
   * @return
   */
  public static String GetFileName(String path) {
    int indexOf = path.lastIndexOf( Symbol.PATH);

    if (indexOf != -1) {
      path = path.substring(indexOf + 1);
      return path;
    }

    return path;
  }

  /**
   * 获取不带后缀名的文件名
   *
   * @param name
   * @return
   */
  public static String GetPrefixName(String name) {
    int indexOf = name.lastIndexOf(Symbol.POINT);

    if (indexOf != -1) {
      name = name.substring(0, indexOf);
      return name;
    }

    return name;
  }

  /**
   * 获取后缀名
   *
   * @param name
   * @return
   */
  public static String GetSuffixName(String name) {
    int indexOf = name.lastIndexOf(Symbol.POINT);

    if (indexOf != -1) {
      name = name.substring(indexOf);
      return name;
    }

    return null;
  }

  public static String Rename(String outName, String flag) {
    String result = outName;

    String prefixName = PathUtils.GetPrefixName(result);
    String suffixName = PathUtils.GetSuffixName(result);

    if (StringUtils.isNotEmpty(suffixName)) {
      result = prefixName + flag + suffixName;
    } else {
      result = prefixName + flag;
    }

    return result;
  }

  /**
   * 获取根路径
   *
   * @param path 路径
   * @return 根路径名称
   */
  public static String GetRootPath(String path) {

    if (StringUtils.isEmpty(path)) {
      return path;
    }

    if (Symbol.PATH.equals(path)) {
      return Symbol.PATH;
    }

    String tempPath = path;

    // 进行路径中的多个符号的转换
    tempPath = PathProc(tempPath);

    int oneIndex = 0;
    if (tempPath.indexOf(Symbol.PATH) == 0) {
      oneIndex = tempPath.indexOf(Symbol.PATH, 1);
    } else {
      oneIndex = tempPath.indexOf(Symbol.PATH, 1);
    }

    if (oneIndex != -1) {
      return tempPath.substring(1, oneIndex);
    } else {
      return tempPath.substring(1);
    }
  }


}
