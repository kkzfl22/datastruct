package com.liujun.datastruct.stack.bracket;

/**
 * 括号检查
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/23
 */
public enum BracketEnum {
  SMALL("(", ")"),

  MID("[", "]"),

  BIG("{", "}");

  private String left;

  private String right;

  BracketEnum(String left, String right) {
    this.left = left;
    this.right = right;
  }

  public String getLeft() {
    return left;
  }

  public String getRight() {
    return right;
  }

  /**
   * 进行括号匹配检查
   *
   * @param left 左括号
   * @param right 右括号
   * @return true 匹配，false 不匹配
   */
  public static boolean bracketMatch(String left, String right) {
    for (BracketEnum brack : values()) {
      // 检查左括号匹配
      if (brack.left.equals(left)) {
        // 如果右括号匹配，则返回true
        if (brack.right.equals(right)) {
          return true;
        }
        // 否则不匹配
        else {
          return false;
        }
      }
    }
    return false;
  }
}
