package com.liujun.datastruct.advanced.bplusTree.codedemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/21
 */
public class InternalNode<K extends Comparable<? super K>, V> extends Node<K, V> {
  List<Node<K, V>> children;

  InternalNode() {
    this.keys = new ArrayList<>();
    this.children = new ArrayList<>();
  }

  @Override
  V getValue(K key) {
    return getChild(key).getValue(key);
  }

  @Override
  void deleteValue(K key) {
    Node<K, V> child = getChild(key);
    child.deleteValue(key);
    if (child.isUnderflow()) {
      Node<K, V> childLeftSibling = getChildLeftSibling(key);
      Node<K, V> childRightSibling = getChildRightSibling(key);
      Node<K, V> left = childLeftSibling != null ? childLeftSibling : child;
      Node<K, V> right = childLeftSibling != null ? child : childRightSibling;
      left.merge(right);
      deleteChild(right.getFirstLeafKey());
      if (left.isOverflow()) {
        Node<K, V> sibling = left.split();
        insertChild(sibling.getFirstLeafKey(), sibling);
      }
      if (root.keyNumber() == 0) root = left;
    }
  }

  @Override
  void insertValue(K key, V value) {
    Node child = getChild(key);
    child.insertValue(key, value);
    if (child.isOverflow()) {
      Node<K, V> sibling = child.split();
      insertChild(sibling.getFirstLeafKey(), sibling);
    }
    if (root.isOverflow()) {
      Node sibling = split();
      InternalNode newRoot = new InternalNode();
      newRoot.keys.add(sibling.getFirstLeafKey());
      newRoot.children.add(this);
      newRoot.children.add(sibling);
      root = newRoot;
    }
  }

  @Override
  K getFirstLeafKey() {
    return children.get(0).getFirstLeafKey();
  }

  @Override
  List<V> getRange(K key1, BPlusTree.RangePolicy policy1, K key2, BPlusTree.RangePolicy policy2) {
    return getChild(key1).getRange(key1, policy1, key2, policy2);
  }

  @Override
  void merge(Node<K, V> sibling) {
    @SuppressWarnings("unchecked")
    InternalNode<K, V> node = (InternalNode) sibling;
    keys.add(node.getFirstLeafKey());
    keys.addAll(node.keys);
    children.addAll(node.children);
  }

  @Override
  Node split() {
    int from = keyNumber() / 2 + 1, to = keyNumber();
    InternalNode sibling = new InternalNode();
    sibling.keys.addAll(keys.subList(from, to));
    sibling.children.addAll(children.subList(from, to + 1));

    keys.subList(from - 1, to).clear();
    children.subList(from, to + 1).clear();

    return sibling;
  }

  @Override
  boolean isOverflow() {
    return children.size() > branchingFactor;
  }

  @Override
  boolean isUnderflow() {
    return children.size() < (branchingFactor + 1) / 2;
  }

  Node<K, V> getChild(K key) {
    int loc = Collections.binarySearch(keys, key);
    int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
    return children.get(childIndex);
  }

  void deleteChild(K key) {
    int loc = Collections.binarySearch(keys, key);
    if (loc >= 0) {
      keys.remove(loc);
      children.remove(loc + 1);
    }
  }

  void insertChild(K key, Node child) {
    int loc = Collections.binarySearch(keys, key);
    int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
    if (loc >= 0) {
      children.set(childIndex, child);
    } else {
      keys.add(childIndex, key);
      children.add(childIndex + 1, child);
    }
  }

  Node getChildLeftSibling(K key) {
    int loc = Collections.binarySearch(keys, key);
    int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
    if (childIndex > 0) return children.get(childIndex - 1);

    return null;
  }

  Node getChildRightSibling(K key) {
    int loc = Collections.binarySearch(keys, key);
    int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
    if (childIndex < keyNumber()) return children.get(childIndex + 1);

    return null;
  }
}
