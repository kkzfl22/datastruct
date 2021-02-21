package com.liujun.datastruct.base.datastruct.hash.consistenthash;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestHashCode {
  @Test
  public void countHashCode() {
    int nodeSum = 4;
    this.countNode("jiansheng", nodeSum);
    this.countNode("feifei", nodeSum);
    this.countNode("meilin", nodeSum);
    this.countNode("manggo", nodeSum);
  }

  private void countNode(String key, int nodeSum) {
    int hashCode = HashCode.hash(key);
    // 由于hashcode可能存在负数，进一步运算变正数
    hashCode = hashCode & Integer.MAX_VALUE;
    int nodeIndex = hashCode % nodeSum;
    System.out.println("key : " + key + "-->" + hashCode + "-->" + +nodeIndex);
  }
}
