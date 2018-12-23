package com.liujun.datastruct.datastruct.stack.dataCount;

import com.liujun.datastruct.datastruct.stack.ArrayStack;
import com.liujun.datastruct.datastruct.stack.ArrayStrStack;

/**
 * 使用栈进行数据计算操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/22
 */
public class dataCount {

  public static void main(String[] args) {
    // String countValue = "3+5*8/2-2";
    String countValue = "9+2*3*5/5*8/8-9/3*2-2";

    // 用于存储数值的栈
    ArrayStack stackVal = new ArrayStack(10);
    // 操作符栈
    ArrayStrStack stackOpe = new ArrayStrStack(10);

    String[] data = countValue.split("");

    for (int i = 0; i < data.length; i++) {
      // 非符号，则加入数值栈
      if (!CountEnum.checkFlag(data[i])) {
        stackVal.push(Integer.parseInt(data[i]));
      } else {
        // 如果当前为操作符，检查是否为首个，首个直接放入
        if (stackOpe.size() == 0) {
          stackOpe.push(data[i]);
        } else {
          // 检查操作符的做优先级
          int currOrder = CountEnum.getFlag(data[i]).getOrder();
          String operValue = stackOpe.pop();
          int stackOpValue = CountEnum.getFlag(operValue).getOrder();

          // 如果当前的优先级比栈中的数据优先级高，则压入操作数栈
          if (currOrder > stackOpValue) {
            stackOpe.push(operValue);
            stackOpe.push(data[i]);
          }
          // 如果当前优先级与上一次的优先级相同，才进行运算
          else if (currOrder == stackOpValue) {
            int val1 = stackVal.pop();
            int val2 = stackVal.pop();

            System.out.println("当前计算" + val2 + ", 操作符:" + operValue + "," + val1);

            int coutValue = CountEnum.FunCount(operValue, val2, val1);
            // 将结果夺入操作数栈
            stackVal.push(coutValue);
            stackOpe.push(data[i]);
          }
          // 如果相同，或者比当前做优先级低于之前的，则需要进行计算
          else {
            stackOpe.push(operValue);
            // 进行计算
            stackCount(stackVal, stackOpe);
            stackOpe.push(data[i]);
          }
        }
      }
    }

    // 基本后的计算
    stackCount(stackVal, stackOpe);
    // 结果
    System.out.println(stackVal.pop());
  }

  public static void stackCount(ArrayStack value, ArrayStrStack operStack) {

    String currOp = null;
    while ((currOp = operStack.pop()) != null) {
      int val1 = value.pop();
      int val2 = value.pop();

      System.out.println("当前计算" + val2 + ", 操作符:" + currOp + "," + val1);

      int coutValue = CountEnum.FunCount(currOp, val2, val1);
      // 将结果夺入操作数栈
      value.push(coutValue);
    }
  }
}
