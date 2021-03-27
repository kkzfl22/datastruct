package com.liujun.datastruct.base.leetcode.slide.code0059;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试队列
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestMaxQueue {

  /**
   * Your MaxQueue object will be instantiated and called as such: MaxQueue obj = new MaxQueue();
   * int param_1 = obj.max_value(); obj.push_back(value); int param_3 = obj.pop_front();
   */
  @Test
  public void slideQueue() {
    MaxQueue obj = new MaxQueue();
    int maxRun = 100;
    for (int i = 0; i < maxRun; i++) {
      obj.push_back(i);
    }

    Assert.assertEquals(maxRun - 1, obj.max_value());

    for (int i = 0; i < maxRun; i++) {
      Assert.assertEquals(obj.pop_front(), i);
    }
  }

  @Test
  public void slideQueue2() {
    MaxQueue obj = new MaxQueue();
    int maxRun = 68;
    for (int i = 0; i < maxRun; i++) {
      obj.push_back(i);
    }

    Assert.assertEquals(maxRun - 1, obj.max_value());

    for (int i = 0; i < maxRun; i++) {
      Assert.assertEquals(obj.pop_front(), i);
    }
  }

  @Test
  public void slideQueue3() {
    MaxQueue obj = new MaxQueue();
    int maxRun = 1024;
    for (int i = 0; i < maxRun; i++) {
      obj.push_back(i);
      Assert.assertEquals(i, obj.max_value());
      Assert.assertEquals(obj.pop_front(), i);
    }
  }

  @Test
  public void complex() {
    MaxQueue obj = new MaxQueue();
    this.assertMax(obj, -1);
    this.assertPopFront(obj, -1);
    this.assertMax(obj, -1);
    obj.push_back(46);
    this.assertMax(obj, 46);
    this.assertPopFront(obj, 46);
    this.assertMax(obj, -1);
    this.assertPopFront(obj, -1);
    obj.push_back(868);
    this.assertPopFront(obj, 868);
    this.assertPopFront(obj, -1);
    this.assertPopFront(obj, -1);
    obj.push_back(525);
    this.assertPopFront(obj, 525);
    this.assertMax(obj, -1);
    this.assertPopFront(obj, -1);
    this.assertMax(obj, -1);
    obj.push_back(123);
    obj.push_back(646);
    this.assertMax(obj, 646);
    obj.push_back(229);
    this.assertMax(obj, 646);
    this.assertMax(obj, 646);
    this.assertMax(obj, 646);
    obj.push_back(871);
    this.assertPopFront(obj, 123);
    this.assertMax(obj, 871);
    obj.push_back(285);
    this.assertMax(obj, 871);
    this.assertMax(obj, 871);
    this.assertMax(obj, 871);
    this.assertPopFront(obj, 646);
    obj.push_back(45);
    obj.push_back(140);
    obj.push_back(837);
    obj.push_back(545);
    this.assertPopFront(obj, 229);
    this.assertPopFront(obj, 871);
    this.assertMax(obj, 837);
    this.assertPopFront(obj, 285);
    this.assertPopFront(obj, 45);
    this.assertMax(obj, 837);
    obj.push_back(561);
    obj.push_back(237);
    this.assertPopFront(obj, 140);
    obj.push_back(633);
    obj.push_back(98);
    obj.push_back(806);
    obj.push_back(717);
    this.assertPopFront(obj, 837);
    this.assertMax(obj, 806);
    obj.push_back(186);
    this.assertMax(obj, 806);
    this.assertMax(obj, 806);
    this.assertPopFront(obj, 545);
    this.assertMax(obj, 806);
    this.assertMax(obj, 806);
    this.assertMax(obj, 806);
    obj.push_back(268);
    this.assertPopFront(obj, 561);
    obj.push_back(29);
    this.assertPopFront(obj, 237);
    this.assertMax(obj, 806);
    this.assertMax(obj, 806);
    this.assertMax(obj, 806);
    obj.push_back(866);
    this.assertPopFront(obj, 633);
    obj.push_back(239);
    obj.push_back(3);
    obj.push_back(850);
    this.assertPopFront(obj, 98);
    this.assertMax(obj, 866);
    this.assertPopFront(obj, 806);
    this.assertMax(obj, 866);
    this.assertMax(obj, 866);
    this.assertMax(obj, 866);
    this.assertPopFront(obj, 717);
    obj.push_back(310);
    this.assertPopFront(obj, 186);
    obj.push_back(674);
    obj.push_back(770);
    this.assertPopFront(obj, 268);
    obj.push_back(525);
    this.assertPopFront(obj, 29);
    obj.push_back(425);
    this.assertPopFront(obj, 866);
    this.assertPopFront(obj, 239);
    obj.push_back(720);
    this.assertPopFront(obj, 3);
    this.assertPopFront(obj, 850);
    this.assertPopFront(obj, 310);
    obj.push_back(373);
    obj.push_back(411);
    this.assertMax(obj, 770);
    obj.push_back(831);
    this.assertPopFront(obj, 674);
    obj.push_back(765);
    obj.push_back(701);
    this.assertPopFront(obj, 770);
  }

  @Test
  public void complex2() {
    MaxQueue obj = new MaxQueue();
    this.assertPopFront(obj, -1);
    obj.push_back(629);
    this.assertPopFront(obj, 629);
    obj.push_back(590);
    this.assertMax(obj, 590);
    this.assertMax(obj, 590);
    this.assertPopFront(obj, 590);
    this.assertMax(obj, -1);
    this.assertPopFront(obj, -1);
    this.assertMax(obj, -1);
    this.assertMax(obj, -1);
    obj.push_back(199);
    this.assertPopFront(obj, 199);
    obj.push_back(63);
    obj.push_back(105);
    obj.push_back(424);
    this.assertMax(obj, 424);
    this.assertPopFront(obj, 63);
    obj.push_back(562);
    this.assertPopFront(obj, 105);
    obj.push_back(297);
    this.assertMax(obj, 562);
    this.assertMax(obj, 562);
    this.assertMax(obj, 562);
    obj.push_back(109);
    this.assertMax(obj, 562);
    this.assertMax(obj, 562);
    obj.push_back(647);
    obj.push_back(638);
    obj.push_back(612);
    obj.push_back(137);
    obj.push_back(558);
    obj.push_back(16);
    this.assertPopFront(obj, 424);
    obj.push_back(140);
    this.assertMax(obj, 647);
    obj.push_back(674);
    this.assertMax(obj, 674);
    obj.push_back(566);
    this.assertPopFront(obj, 562);
    this.assertPopFront(obj, 297);
    this.assertMax(obj, 674);
    this.assertPopFront(obj, 109);
    obj.push_back(658);
    this.assertPopFront(obj, 647);
    this.assertPopFront(obj, 638);
    this.assertMax(obj, 674);
    obj.push_back(82);
    this.assertMax(obj, 674);
    this.assertMax(obj, 674);
    this.assertMax(obj, 674);
    this.assertMax(obj, 674);
    this.assertPopFront(obj, 612);
    this.assertPopFront(obj, 137);
    obj.push_back(125);
    obj.push_back(128);
    this.assertMax(obj, 674);
    obj.push_back(50);
    this.assertMax(obj, 674);
    obj.push_back(865);
    obj.push_back(688);
    this.assertPopFront(obj, 558);
    this.assertMax(obj, 865);
    this.assertMax(obj, 865);
    obj.push_back(384);
    this.assertMax(obj, 865);
    obj.push_back(477);
    this.assertPopFront(obj, 16);
    this.assertPopFront(obj, 140);
    this.assertPopFront(obj, 674);
    this.assertPopFront(obj, 566);
    this.assertPopFront(obj, 658);
    this.assertPopFront(obj, 82);
    this.assertPopFront(obj, 125);
    obj.push_back(235);
    this.assertPopFront(obj, 128);
    this.assertMax(obj, 865);
    this.assertPopFront(obj, 50);
    this.assertMax(obj, 865);
    this.assertMax(obj, 865);
    this.assertMax(obj, 865);
    obj.push_back(911);
    this.assertPopFront(obj, 865);
    this.assertPopFront(obj, 688);
    this.assertMax(obj, 911);
    this.assertMax(obj, 911);
    obj.push_back(857);
    this.assertPopFront(obj, 384);
    this.assertPopFront(obj, 477);
    this.assertMax(obj, 911);
    obj.push_back(474);
    this.assertMax(obj, 911);
    this.assertMax(obj, 911);
    obj.push_back(109);
    obj.push_back(843);
    this.assertMax(obj, 911);
    this.assertPopFront(obj, 235);
    obj.push_back(382);
    this.assertPopFront(obj, 911);
  }

  private void assertMax(MaxQueue instance, int target) {
    int value = instance.max_value();
    Assert.assertEquals(value, target);
  }

  private void assertPopFront(MaxQueue instance, int target) {
    int value = instance.pop_front();
    Assert.assertEquals(value, target);
  }
}
