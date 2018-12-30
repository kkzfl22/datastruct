package com.liujun.math.chapter04Conclude;

/**
 * 使用代码来实现归纳法的推导操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class Conclude {

  /** 结果对象信息 */
  public class Result {
    /** 当前棋子格的麦粒数 */
    public long wheatNum = 0;

    /** 到当前棋格子的麦粒总数 */
    public long wheatTotalNum = 0;
  }

  public Result getInstance() {
    return new Result();
  }

  /**
   * 使用函数的递归调用，进行数学归纳法的证明
   *
   * @param k 当前格式
   * @param result 统计结果
   * @return 放到第k格是否成立
   */
  public boolean prove(int k, Result result) {

    // 1,基本的证明，证明基本的情况（通常是n=1）是否成立
    if (k == 1) {
      if (Math.pow(2, 1) - 1 == 1) {
        result.wheatNum = 1;
        result.wheatTotalNum = 1;
        return true;
      } else {
        return false;
      }
    }
    // 2,假设m=k-1也是成立，再证明n=k，也是成立，(k为任意大于1的自然数)
    else {
      boolean porveValue = prove(k - 1, result);
      result.wheatNum = result.wheatNum * 2;
      result.wheatTotalNum = result.wheatTotalNum + result.wheatNum;

      boolean provCheckFlag = false;

      if (result.wheatTotalNum == Math.pow(2, k) - 1) {
        provCheckFlag = true;
      }

      if (porveValue && provCheckFlag) {
        System.out.println("当前结果:" + result.wheatTotalNum);
        return true;
      } else {
        return false;
      }
    }
  }
}
