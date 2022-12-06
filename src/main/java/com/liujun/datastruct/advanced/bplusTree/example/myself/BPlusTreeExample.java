package com.liujun.datastruct.advanced.bplusTree.example.myself;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 博客地址
 *
 * <p>https://blog.csdn.net/qq_36456827/article/details/122192984
 *
 * @author liujun
 * @since 2022/11/10
 */
public class BPlusTreeExample<K extends Comparable<K>, E> {

  private final int KEY_UPPER_BOUND;

  private final int UNDERFLOW_BOUND;

  private BPlusTreeNode root;

  public BPlusTreeExample(int order) {
    this.KEY_UPPER_BOUND = order - 1;
    this.UNDERFLOW_BOUND = KEY_UPPER_BOUND / 2;
  }

  public BPlusTreeExample() {
    this.KEY_UPPER_BOUND = 8;
    this.UNDERFLOW_BOUND = KEY_UPPER_BOUND / 2;
  }

  public void insert(K entry, E value) {

    if (root == null) {
      root = new BPlusTreeLeafNode(asList(entry), asList(asSet(value)));
      return;
    }

    BPlusTreeNode newChildNode = root.insert(entry, value);
    // 如果当前返回的根节点发生了分裂，则构建新的root顶点
    if (newChildNode != null) {
      K newRootEntry =
          // 如果当煎研为叶子节点，直接返回味道节点的首个key，即为新的root顶点
          newChildNode.getClass().equals(BPlusTreeLeafNode.class)
              ? newChildNode.keys.get(0)
              // 否则需要查找元素的首个顶点，作为元素首值值
              : ((BPlusTreeNonLeafNode) newChildNode).findLeafEntry(newChildNode);
      // 以此为构建新的root顶点
      root = new BPlusTreeNonLeafNode(asList(newRootEntry), asList(root, newChildNode));
    }
  }

  public List<E> query(K entry) {
    if (root == null) {
      return Collections.emptyList();
    }
    return root.query(entry);
  }

  public List<E> rangeQuery(K startInclude, K endExclude) {
    if (root == null) {
      return Collections.emptyList();
    }
    return root.rangeQuery(startInclude, endExclude);
  }

  public boolean update(K entry, E oldValue, E newValue) {
    if (root == null) {
      return false;
    }

    return root.update(entry, oldValue, newValue);
  }

  public boolean remove(K entry, E value) {
    if (root == null) {
      return false;
    }

    RemoveResult removeResult = root.remove(entry, value);
    if (!removeResult.isRemoved) {
      return false;
    }

    if (root.keys.isEmpty()) {
      this.handleRootUnderflow();
    }

    return true;
  }

  public boolean remove(K entry) {
    if (root == null) {
      return false;
    }

    RemoveResult removeResult = root.remove(entry);
    if (!removeResult.isRemoved) {
      return false;
    }

    if (root.keys.isEmpty()) {
      this.handleRootUnderflow();
    }

    return true;
  }

  private void handleRootUnderflow() {
    root =
        root.getClass().equals(BPlusTreeLeafNode.class)
            ? null
            : ((BPlusTreeNonLeafNode) root).children.get(0);
  }

  @SafeVarargs
  private final <T> List<T> asList(T... e) {
    List<T> res = new ArrayList<>();
    Collections.addAll(res, e);
    return res;
  }

  @SafeVarargs
  private final <T> Set<T> asSet(T... e) {
    Set<T> res = new HashSet<>();
    Collections.addAll(res, e);
    return res;
  }

  @Override
  public String toString() {
    return root.toString();
  }

  private abstract class BPlusTreeNode {

    protected List<K> keys;

    /**
     * 当前的节点是否已经满了
     *
     * @return
     */
    protected boolean isFull() {
      return keys.size() == KEY_UPPER_BOUND;
    }

    /**
     * 是否需要合并
     *
     * @return
     */
    protected boolean isUnderflow() {
      return keys.size() < UNDERFLOW_BOUND;
    }

    /**
     * 一半的大小
     *
     * @return
     */
    protected int getMedianIndex() {
      return KEY_UPPER_BOUND / 2;
    }

    protected int findEntryIndex(K entry) {
      int l = 0;
      int r = keys.size() - 1;
      int index = keys.size();
      while (l <= r) {
        int mid = l + ((r - l) >> 1);
        if (keys.get(mid).compareTo(entry) >= 0) {
          index = mid;
          r = mid - 1;
        } else {
          l = mid + 1;
        }
      }
      return index;
    }

    public abstract List<E> rangeQuery(K startInclude, K endExclude);

    public abstract List<E> query(K entry);

    public abstract BPlusTreeNode insert(K entry, E value);

    public abstract boolean update(K entry, E oldValue, E newValue);

    public abstract RemoveResult remove(K entry);

    public abstract RemoveResult remove(K entry, E value);

    public abstract void combine(BPlusTreeNode neighbor, K parentEntry);

    public abstract void borrow(BPlusTreeNode neighbor, K parentEntry, boolean isLeft);
  }

  private class BPlusTreeNonLeafNode extends BPlusTreeNode {

    public List<BPlusTreeNode> children;

    public BPlusTreeNonLeafNode(List<K> entries, List<BPlusTreeNode> children) {
      this.keys = entries;
      this.children = children;
    }

    @Override
    public List<E> rangeQuery(K startInclude, K endExclude) {
      return children
          .get(findChildIndex(findEntryIndex(startInclude), startInclude))
          .rangeQuery(startInclude, endExclude);
    }

    @Override
    public List<E> query(K entry) {
      return children.get(findChildIndex(findEntryIndex(entry), entry)).query(entry);
    }

    @Override
    public boolean update(K entry, E oldValue, E newValue) {
      return children
          .get(findChildIndex(findEntryIndex(entry), entry))
          .update(entry, oldValue, newValue);
    }

    @Override
    public BPlusTreeNode insert(K entry, E value) {
      BPlusTreeNode newChildNode =
          children.get(findChildIndex(findEntryIndex(entry), entry)).insert(entry, value);

      if (newChildNode != null) {
        K newEntry = findLeafEntry(newChildNode);
        int newEntryIndex = findEntryIndex(newEntry);
        if (isFull()) {
          BPlusTreeNonLeafNode newNonLeafNode = split();
          int medianIndex = getMedianIndex();
          if (newEntryIndex < medianIndex) {
            keys.add(newEntryIndex, newEntry);
            children.add(newEntryIndex + 1, newChildNode);
          } else {
            int rightIndex = newNonLeafNode.findEntryIndex(newEntry);
            newNonLeafNode.keys.add(rightIndex, newEntry);
            newNonLeafNode.children.add(rightIndex, newChildNode);
          }
          newNonLeafNode.keys.remove(0);
          return newNonLeafNode;
        }

        keys.add(newEntryIndex, newEntry);
        children.add(newEntryIndex + 1, newChildNode);
      }

      return null;
    }

    @Override
    public RemoveResult remove(K entry) {
      int entryIndex = findEntryIndex(entry);
      int childIndex = findChildIndex(entryIndex, entry);
      BPlusTreeNode childNode = children.get(childIndex);
      RemoveResult removeResult = childNode.remove(entry);
      if (!removeResult.isRemoved) {
        return removeResult;
      }

      if (removeResult.isUnderflow) {
        this.handleUnderflow(childNode, childIndex, entryIndex);
      }

      return new RemoveResult(true, isUnderflow());
    }

    @Override
    public RemoveResult remove(K entry, E value) {
      int entryIndex = findEntryIndex(entry);
      int childIndex = findChildIndex(entryIndex, entry);
      BPlusTreeNode childNode = children.get(childIndex);
      RemoveResult removeResult = childNode.remove(entry, value);
      if (!removeResult.isRemoved) {
        return removeResult;
      }

      if (removeResult.isUnderflow) {
        this.handleUnderflow(childNode, childIndex, entryIndex);
      }

      return new RemoveResult(true, isUnderflow());
    }

    @Override
    public void combine(BPlusTreeNode neighbor, K parentEntry) {
      BPlusTreeNonLeafNode nonLeafNode = (BPlusTreeNonLeafNode) neighbor;
      this.keys.add(parentEntry);
      this.keys.addAll(nonLeafNode.keys);
      this.children.addAll(nonLeafNode.children);
    }

    @Override
    public void borrow(BPlusTreeNode neighbor, K parentEntry, boolean isLeft) {
      BPlusTreeNonLeafNode nonLeafNode = (BPlusTreeNonLeafNode) neighbor;
      if (isLeft) {
        this.keys.add(0, parentEntry);
        this.children.add(0, nonLeafNode.children.get(nonLeafNode.children.size() - 1));
        nonLeafNode.children.remove(nonLeafNode.children.size() - 1);
        nonLeafNode.keys.remove(nonLeafNode.keys.size() - 1);
      } else {
        this.keys.add(parentEntry);
        this.children.add(nonLeafNode.children.get(0));
        nonLeafNode.keys.remove(0);
        nonLeafNode.children.remove(0);
      }
    }

    public K findLeafEntry(BPlusTreeNode cur) {
      if (cur.getClass().equals(BPlusTreeLeafNode.class)) {
        return cur.keys.get(0);
      }
      return findLeafEntry(((BPlusTreeNonLeafNode) cur).children.get(0));
    }

    private void handleUnderflow(BPlusTreeNode childNode, int childIndex, int entryIndex) {
      BPlusTreeNode neighbor;
      if (childIndex > 0
          && (neighbor = this.children.get(childIndex - 1)).keys.size() > UNDERFLOW_BOUND) {

        childNode.borrow(neighbor, this.keys.get(reviseEntryIndex(entryIndex)), true);
        this.keys.set(
            reviseEntryIndex(entryIndex),
            childNode.getClass().equals(BPlusTreeNonLeafNode.class)
                ? findLeafEntry(childNode)
                : childNode.keys.get(0));

      } else if (childIndex < this.children.size() - 1
          && (neighbor = this.children.get(childIndex + 1)).keys.size() > UNDERFLOW_BOUND) {

        childNode.borrow(neighbor, this.keys.get(entryIndex), false);
        this.keys.set(
            entryIndex,
            childNode.getClass().equals(BPlusTreeNonLeafNode.class)
                ? findLeafEntry(neighbor)
                : neighbor.keys.get(0));

      } else {

        if (childIndex > 0) {
          // combine current child to left child
          neighbor = this.children.get(childIndex - 1);
          neighbor.combine(childNode, this.keys.get(reviseEntryIndex(entryIndex)));
          this.children.remove(childIndex);

        } else {
          // combine right child to current child
          neighbor = this.children.get(childIndex + 1);
          childNode.combine(neighbor, this.keys.get(entryIndex));
          this.children.remove(childIndex + 1);
        }

        this.keys.remove(reviseEntryIndex(entryIndex));
      }
    }

    private int reviseEntryIndex(int entryIndex) {
      return Math.min(this.keys.size() - 1, entryIndex);
    }

    private int findChildIndex(int entryIndex, K entry) {
      return (entryIndex == keys.size() || entry.compareTo(keys.get(entryIndex)) < 0)
          ? entryIndex
          : entryIndex + 1;
    }

    private BPlusTreeNonLeafNode split() {
      int medianIndex = getMedianIndex();
      List<K> allEntries = keys;
      List<BPlusTreeNode> allChildren = children;

      this.keys = new ArrayList<>(allEntries.subList(0, medianIndex));
      this.children = new ArrayList<>(allChildren.subList(0, medianIndex + 1));

      List<K> rightEntries = new ArrayList<>(allEntries.subList(medianIndex, allEntries.size()));
      List<BPlusTreeNode> rightChildren =
          new ArrayList<>(allChildren.subList(medianIndex + 1, allChildren.size()));
      return new BPlusTreeNonLeafNode(rightEntries, rightChildren);
    }

    @Override
    public String toString() {
      StringBuilder res = new StringBuilder();
      Queue<BPlusTreeNode> queue = new LinkedList<>();
      queue.add(this);
      while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; ++i) {
          BPlusTreeNode cur = queue.poll();
          assert cur != null;
          res.append(cur.keys).append("  ");
          if (cur.getClass().equals(BPlusTreeNonLeafNode.class)) {
            queue.addAll(((BPlusTreeNonLeafNode) cur).children);
          }
        }
        res.append('\n');
      }
      return res.toString();
    }
  }

  private class BPlusTreeLeafNode extends BPlusTreeNode {

    public List<Set<E>> value;

    public BPlusTreeLeafNode next;

    public BPlusTreeLeafNode(List<K> keys, List<Set<E>> value) {
      this.keys = keys;
      this.value = value;
    }

    @Override
    public List<E> rangeQuery(K startInclude, K endExclude) {
      List<E> res = new ArrayList<>();
      int start = findEntryIndex(startInclude);
      int end = findEntryIndex(endExclude);
      for (int i = start; i < end; ++i) {
        res.addAll(value.get(i));
      }
      BPlusTreeLeafNode nextLeafNode = next;
      while (nextLeafNode != null) {
        for (int i = 0; i < nextLeafNode.keys.size(); ++i) {
          if (nextLeafNode.keys.get(i).compareTo(endExclude) < 0) {
            res.addAll(nextLeafNode.value.get(i));
          } else {
            return res;
          }
        }
        nextLeafNode = nextLeafNode.next;
      }
      return res;
    }

    @Override
    public List<E> query(K entry) {
      int entryIndex = getEqualEntryIndex(entry);
      return entryIndex == -1 ? Collections.emptyList() : new ArrayList<>(value.get(entryIndex));
    }

    @Override
    public boolean update(K entry, E oldValue, E newValue) {
      int entryIndex = getEqualEntryIndex(entry);
      if (entryIndex == -1 || !value.get(entryIndex).contains(oldValue)) {
        return false;
      }

      value.get(entryIndex).remove(oldValue);
      value.get(entryIndex).add(newValue);
      return true;
    }

    @Override
    public RemoveResult remove(K entry) {
      int entryIndex = getEqualEntryIndex(entry);
      if (entryIndex == -1) {
        return new RemoveResult(false, false);
      }

      this.keys.remove(entryIndex);
      this.value.remove(entryIndex);

      return new RemoveResult(true, isUnderflow());
    }

    @Override
    public RemoveResult remove(K entry, E value) {
      int entryIndex = getEqualEntryIndex(entry);
      if (entryIndex == -1 || !this.value.get(entryIndex).contains(value)) {
        return new RemoveResult(false, false);
      }

      this.value.get(entryIndex).remove(value);
      if (this.value.get(entryIndex).isEmpty()) {
        this.keys.remove(entryIndex);
        this.value.remove(entryIndex);
      }

      return new RemoveResult(true, isUnderflow());
    }

    @Override
    public void combine(BPlusTreeNode neighbor, K parentEntry) {
      BPlusTreeLeafNode leafNode = (BPlusTreeLeafNode) neighbor;
      this.keys.addAll(leafNode.keys);
      this.value.addAll(leafNode.value);
      this.next = leafNode.next;
    }

    @Override
    public void borrow(BPlusTreeNode neighbor, K parentEntry, boolean isLeft) {
      BPlusTreeLeafNode leafNode = (BPlusTreeLeafNode) neighbor;
      int borrowIndex;

      if (isLeft) {
        borrowIndex = leafNode.keys.size() - 1;
        this.keys.add(0, leafNode.keys.get(borrowIndex));
        this.value.add(0, leafNode.value.get(borrowIndex));
      } else {
        borrowIndex = 0;
        this.keys.add(leafNode.keys.get(borrowIndex));
        this.value.add(leafNode.value.get(borrowIndex));
      }

      leafNode.keys.remove(borrowIndex);
      leafNode.value.remove(borrowIndex);
    }

    @Override
    public BPlusTreeNode insert(K entry, E value) {
      int equalEntryIndex = getEqualEntryIndex(entry);
      if (equalEntryIndex != -1) {
        this.value.get(equalEntryIndex).add(value);
        return null;
      }

      int index = findEntryIndex(entry);

      if (isFull()) {
        BPlusTreeLeafNode newLeafNode = split();
        int medianIndex = getMedianIndex();

        if (index < medianIndex) {
          keys.add(index, entry);
          this.value.add(index, asSet(value));
        } else {
          int rightIndex = index - medianIndex;
          newLeafNode.keys.add(rightIndex, entry);
          newLeafNode.value.add(rightIndex, asSet(value));
        }
        return newLeafNode;
      }

      keys.add(index, entry);
      this.value.add(index, asSet(value));
      return null;
    }

    private BPlusTreeLeafNode split() {
      int medianIndex = getMedianIndex();
      List<K> allEntries = keys;
      List<Set<E>> allData = value;

      this.keys = new ArrayList<>(allEntries.subList(0, medianIndex));
      this.value = new ArrayList<>(allData.subList(0, medianIndex));

      List<K> rightEntries = new ArrayList<>(allEntries.subList(medianIndex, allEntries.size()));
      List<Set<E>> rightData = new ArrayList<>(allData.subList(medianIndex, allData.size()));
      BPlusTreeLeafNode newLeafNode = new BPlusTreeLeafNode(rightEntries, rightData);

      newLeafNode.next = this.next;
      this.next = newLeafNode;
      return newLeafNode;
    }

    private int getEqualEntryIndex(K entry) {
      int l = 0;
      int r = keys.size() - 1;
      while (l <= r) {
        int mid = l + ((r - l) >> 1);
        int compare = keys.get(mid).compareTo(entry);
        if (compare == 0) {
          return mid;
        } else if (compare > 0) {
          r = mid - 1;
        } else {
          l = mid + 1;
        }
      }
      return -1;
    }

    @Override
    public String toString() {
      return keys.toString();
    }
  }

  private static class RemoveResult {

    public boolean isRemoved;

    public boolean isUnderflow;

    public RemoveResult(boolean isRemoved, boolean isUnderflow) {
      this.isRemoved = isRemoved;
      this.isUnderflow = isUnderflow;
    }
  }
}
