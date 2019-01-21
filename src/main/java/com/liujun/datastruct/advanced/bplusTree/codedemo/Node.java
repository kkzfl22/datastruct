package com.liujun.datastruct.advanced.bplusTree.codedemo;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/21
 */
public abstract class Node<K extends Comparable<? super K>, V> extends BPlusTree<K, V> {

  List<K> keys;

  int keyNumber() {
    return keys.size();
  }

  abstract V getValue(K key);

  abstract void deleteValue(K key);

  abstract void insertValue(K key, V value);

  abstract K getFirstLeafKey();

  abstract List<V> getRange(
      K key1, BPlusTree.RangePolicy policy1, K key2, BPlusTree.RangePolicy policy2);

  abstract void merge(Node<K, V> sibling);

  abstract Node<K, V> split();

  abstract boolean isOverflow();

  abstract boolean isUnderflow();

  public String toString() {
    return keys.toString();
  }
}
