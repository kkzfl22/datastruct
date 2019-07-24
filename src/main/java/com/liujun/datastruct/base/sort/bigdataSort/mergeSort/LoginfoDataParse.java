package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.liujun.datastruct.base.sort.bigdataSort.mergeSort.bean.LogInfoBUSI;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/24
 */
public class LoginfoDataParse {

  public static final LoginfoDataParse INSTANCE = new LoginfoDataParse();

  /**
   * 将行数据转换为javabean对象
   *
   * @param line 行数据
   * @return 转换信息
   */
  public LogInfoBUSI parseLine(String line) {

    LogInfoBUSI loginBean = new LogInfoBUSI();

    if (line.indexOf(" ") == -1) {
      System.out.println("error");
    }

    loginBean.setTimeCurr(Long.parseLong(line.substring(0, line.indexOf(" "))));
    loginBean.setLoginfo(line);

    return loginBean;
  }
}
