package com.liujun.datastruct.advanced.bplusTree.codedemo;

import org.junit.Assert;
import org.junit.Test;

public class BPlusTreeTest {
  @Test
  public void test() {
    BPlusTree<Integer, String> bpt = new BPlusTree<Integer, String>(4);
    bpt.insert(0, "a");
    bpt.insert(1, "b");
    bpt.insert(2, "c");
    bpt.insert(3, "d");
    bpt.insert(4, "e");
    bpt.insert(5, "f");
    bpt.insert(6, "g");
    bpt.insert(7, "h");
    bpt.insert(8, "i");
    bpt.insert(9, "j");
    bpt.delete(1);
    bpt.delete(3);
    bpt.delete(5);
    bpt.delete(7);
    bpt.delete(9);
    Assert.assertEquals(bpt.search(0), "a");
    Assert.assertEquals(bpt.search(1), null);
    Assert.assertEquals(bpt.search(2), "c");
    Assert.assertEquals(bpt.search(3), null);
    Assert.assertEquals(bpt.search(4), "e");
    Assert.assertEquals(bpt.search(5), null);
    Assert.assertEquals(bpt.search(6), "g");
    Assert.assertEquals(bpt.search(7), null);
    Assert.assertEquals(bpt.search(8), "i");
    Assert.assertEquals(bpt.search(9), null);
  }

  @Test
  public void testSearchRange() {
    BPlusTree<Integer, String> bpt = new BPlusTree<Integer, String>(4);
    bpt.insert(0, "a");
    bpt.insert(1, "b");
    bpt.insert(2, "c");
    bpt.insert(3, "d");
    bpt.insert(4, "e");
    bpt.insert(5, "f");
    bpt.insert(6, "g");
    bpt.insert(7, "h");
    bpt.insert(8, "i");
    bpt.insert(9, "j");
    Assert.assertArrayEquals(
        bpt.searchRange(3, BPlusTree.RangePolicy.EXCLUSIVE, 7, BPlusTree.RangePolicy.EXCLUSIVE)
            .toArray(),
        new String[] {"e", "f", "g"});
    Assert.assertArrayEquals(
        bpt.searchRange(3, BPlusTree.RangePolicy.INCLUSIVE, 7, BPlusTree.RangePolicy.EXCLUSIVE)
            .toArray(),
        new String[] {"d", "e", "f", "g"});
    Assert.assertArrayEquals(
        bpt.searchRange(3, BPlusTree.RangePolicy.EXCLUSIVE, 7, BPlusTree.RangePolicy.INCLUSIVE)
            .toArray(),
        new String[] {"e", "f", "g", "h"});
    Assert.assertArrayEquals(
        bpt.searchRange(3, BPlusTree.RangePolicy.INCLUSIVE, 7, BPlusTree.RangePolicy.INCLUSIVE)
            .toArray(),
        new String[] {"d", "e", "f", "g", "h"});
  }
}
