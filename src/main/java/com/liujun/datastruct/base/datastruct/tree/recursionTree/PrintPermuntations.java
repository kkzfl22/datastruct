package com.liujun.datastruct.base.datastruct.tree.recursionTree;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/26
 */
public class PrintPermuntations {

  public void printPermuntation(int[] data, int currLength) {

    if (currLength == 1) {
      int length = data.length;
      for (int i = 0; i < length; i++) {
        System.out.print(data[i] + "\t");
      }
      System.out.println();
    }

    int tempValue = 0;
    for (int i = 0; i < currLength; i++) {
      tempValue = data[i];
      data[i] = data[currLength - 1];
      data[currLength - 1] = tempValue;

      // 进行全排列的打印操作
      printPermuntation(data, currLength - 1);

      tempValue = data[i];
      data[i] = data[currLength - 1];
      data[currLength - 1] = tempValue;
    }
  }

  public static void main(String[] args) {
    PrintPermuntations instance = new PrintPermuntations();
    int[] data = new int[] {1, 2, 3, 4};
    instance.printPermuntation(data, 4);
  }
}
