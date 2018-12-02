package com.liujun.datastruct.heap.solution.bigFileTopN;

import com.liujun.datastruct.heap.solution.bigFileTopN.pojo.KeyBusi;
import org.junit.Test;

import java.util.Arrays;

/**
 * 进行topn的问题求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class TestTopN {

  @Test
  public void topn() {
    String file = "D:/java/test/run/keyworkd.txt";

    BigFileTopN topn = new BigFileTopN();
    KeyBusi[] topns = topn.topN(file, 1);
    System.out.println(Arrays.toString(topns));
  }
}
