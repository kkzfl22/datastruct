package com.liujun.zfl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liujun
 * @since 2022/11/4
 */
@Setter
@Getter
@ToString
public class FileLineEntity {

  /** 值信息 */
  private String timeFlag;

  private String liFlag;

  private String outLine;

  private String num1;

  private String num2;

  private String num3;

  private static final String LI_FLAG = "Li";

  public static FileLineEntity readerParse(String line) {
    if (line.length() < 63) {
      return null;
    }

    FileLineEntity result = null;
    try {
      result = new FileLineEntity();

      result.setOutLine(line);

      int index = 0;

      result.setTimeFlag(line.substring(index, index + 11));
      index += 12;
      String liFlag = line.substring(index, index + 2);
      result.setLiFlag(liFlag);
      // 过滤掉其他的数据
      if (!LI_FLAG.equals(liFlag)) {
        return null;
      }
      index += 40;
      result.setNum1(hexCheck(line.substring(index, index + 2)));
      index += 3;
      result.setNum2(hexCheck(line.substring(index, index + 2)));
      index += 3;
      result.setNum3(hexCheck(line.substring(index, index + 2)));

      return result;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public String outLine() {
    StringBuilder outValue = new StringBuilder();

    outValue.append(timeFlag.trim()).append("\t");
    outValue.append(liFlag.trim()).append("\t");
    outValue.append(num1.trim()).append("\t");
    outValue.append(num2.trim()).append("\t");
    outValue.append(num3.trim());

    return outValue.toString();
  }

  private static Integer toHex(String value) {
    return Integer.parseInt(value, 16);
  }

  private static String hexCheck(String value) {
    Integer outValue = toHex(value);
    Integer.toHexString(outValue);

    return value;
  }
}
