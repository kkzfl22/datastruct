package com.liujun.datastruct.advanced.bplusTree.example;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * B+树的算法实现，此为理解其内在逻辑
 *
 * @author liujun
 * @since 2022/11/7
 */
public class BPlusTreeMySelfOld<K extends Comparable<K>, E> {

  /** 树的上限节点 */
  private final int KEY_UPPER_BOUND;

  /** 分例的节点 */
  private final int UNDERFLOW_BOUND;

  /** 根节点对象 */
  private BPlusTreeNode root;

  public void insert(K key, E value) {
    // 首次创建根节点
    if (null == root) {
      root = new BPlusTreeLeafNode(asList(key), asList(asSet(value)));
      return;
    }
    // 执行数据的插入操作
    BPlusTreeNode newRoot = root.insert(key, value);

    // 如果当前根节点发生了分裂，则构建新的根节点
    if (newRoot != null) {
      K newRootKey = null;

      // 如果当前为叶子节点，则直接返回当前首个节点
      if (newRoot.getClass().equals(BPlusTreeLeafNode.class)) {
        newRootKey = newRoot.keys.get(0);
      }
      // 如果当前为非叶子节点，则进行查找操作
      else {
        newRootKey = ((BPlusTreeNonLeafNode) (newRoot)).findLeafEntry(newRoot);
      }
      root = new BPlusTreeNonLeafNode(asList(newRootKey), asList(root, newRoot));
    }
  }

  /**
   * 数据查找操作
   *
   * @param key 数据查找的key
   * @return
   */
  public List<E> query(K key) {
    if (root == null) {
      return Collections.emptyList();
    }

    return root.query(key);
  }

  /**
   * 将参数封装成List对象 @SafeVarargs 抑制编译器警告
   *
   * @param e 参数信息
   * @param <T> 泛型对象
   * @return 集合对象
   */
  @SafeVarargs
  private static final <T> List<T> asList(T... e) {
    List<T> res = new ArrayList<>();
    Collections.addAll(res, e);
    return res;
  }

  /**
   * 将参数封装到Set集合中
   *
   * @param e 对象信息
   * @param <T> 泛型对象
   * @return Set集合对象
   */
  private static final <T> Set<T> asSet(T... e) {
    Set<T> res = new HashSet<>();
    Collections.addAll(res, e);
    return res;
  }

  /**
   * 按指定的叶子数量构建B+树
   *
   * @param order 叶子节点的数量
   */
  public BPlusTreeMySelfOld(int order) {
    this.KEY_UPPER_BOUND = order - 1;
    this.UNDERFLOW_BOUND = this.KEY_UPPER_BOUND / 2;
  }

  /** 默认的B+树大小 */
  public BPlusTreeMySelfOld() {
    this.KEY_UPPER_BOUND = 8;
    this.UNDERFLOW_BOUND = this.KEY_UPPER_BOUND / 2;
  }

  /** 基本的树形节点对象 */
  private abstract class BPlusTreeNode {

    /** 放入树形节点key对象信息 */
    protected List<K> keys;

    /**
     * 节点是否已经充满了
     *
     * @return
     */
    protected boolean isFull() {
      return this.keys.size() == KEY_UPPER_BOUND;
    }

    /**
     * 是否需要进行合并操作
     *
     * @return
     */
    protected boolean isUnderFlow() {
      return this.keys.size() < UNDERFLOW_BOUND;
    }

    /**
     * 索引中位数大小
     *
     * @return
     */
    protected int getMedianIndex() {
      return KEY_UPPER_BOUND / 2;
    }

    /**
     * 查询实体的索引位置
     *
     * @param key
     * @return
     */
    protected int findEntryIndex(K key) {
      // 可使用二分查找来解决
      int low = 0;
      int high = keys.size() - 1;
      int index = keys.size();
      while (low <= high) {
        int mid = (high + low) >> 1;

        // 当中位数值，大于了查找的值，则在左区间继续搜索
        if (keys.get(mid).compareTo(key) >= 0) {
          index = mid;
          high = mid - 1;
        }
        // 否则，在右区间继续搜索
        else {
          low = mid + 1;
        }
      }
      return index;
    }

    /**
     * 区间查找
     *
     * @param start 开始区间
     * @param end 结束区间
     * @return 区间返回值
     */
    public abstract List<E> rangeQuery(K start, K end);

    /**
     * 准确的值查找
     *
     * @param key 查找的值
     * @return
     */
    public abstract List<E> query(K key);

    /**
     * 进行插入操作
     *
     * @param key key的信息
     * @param value 值信息
     * @return 节点信息
     */
    public abstract BPlusTreeNode insert(K key, E value);

    /**
     * 进行值的更新操作
     *
     * @param key key的信息
     * @param oldValue 旧值
     * @param newValue 新值
     * @return
     */
    public abstract boolean update(K key, E oldValue, E newValue);

    /**
     * 节点的删除操作
     *
     * @param key
     * @return
     */
    public abstract RemoveResult remove(K key);

    /**
     * 按节点和值进行删除操作
     *
     * @param key 标识数据的键的信息
     * @param value 数据的值的信息
     * @return
     */
    public abstract RemoveResult remove(K key, E value);

    /**
     * 节点的合并操作
     *
     * @param neighbor
     * @param parentEntry
     */
    public abstract void combine(BPlusTreeNode neighbor, K parentEntry);

    /**
     * 节点的合并操作
     *
     * @param neighbor 树形节点信息
     * @param parentEntry 父级实体
     * @param isLeaf 是否为子节点
     */
    public abstract void borrow(BPlusTreeNode neighbor, K parentEntry, boolean isLeaf);
  }

  /** 移除节点的结果信息 */
  @Getter
  @ToString
  private static class RemoveResult {
    private boolean remove;
    private boolean underFlow;

    public RemoveResult(boolean isRemove, boolean isUnderFlow) {
      this.remove = isRemove;
      this.underFlow = isUnderFlow;
    }
  }

  /** 树形节点的叶子节点 */
  private class BPlusTreeLeafNode extends BPlusTreeNode {

    /** 存储的值信息 */
    private List<Set<E>> value;

    /** 指向树形节点的下一个节点 */
    private BPlusTreeLeafNode next;

    public BPlusTreeLeafNode(List<K> keys, List<Set<E>> value) {
      this.keys = keys;
      this.value = value;
    }

    @Override
    public List<E> rangeQuery(K start, K end) {
      return null;
    }

    @Override
    public List<E> query(K key) {
      int keyIndex = this.findEntryIndex(key);

      if (keyIndex == -1) {
        return Collections.emptyList();
      }

      return new ArrayList<>(value.get(keyIndex));
    }

    @Override
    public BPlusTreeNode insert(K key, E value) {

      // 1，查找到当前数据插入的位置
      int index = getEqualsEntryIndex(key);

      // 如果值可以被找到，则直接覆盖原值即可
      if (index != -1) {
        this.value.set(index, asSet(value));
        // 覆盖值，不发生分裂操作
        return null;
      }

      // 查找当前插入的位置
      int insertIndex = this.findEntryIndex(key);

      // 检查当前是否满足分裂条件
      if (isFull()) {
        BPlusTreeLeafNode rightNode = spit();

        // 获取中间位置
        int medianIndex = this.getMedianIndex();

        // 如果小于了中间节点的索引，则在左子树插入
        if (insertIndex < medianIndex) {
          this.keys.add(insertIndex, key);
          this.value.add(insertIndex, asSet(value));
        }
        // 如果大于了中间节点的索引，则在右子树写入
        else {
          int rightIndex = insertIndex - medianIndex;
          rightNode.keys.add(rightIndex, key);
          rightNode.value.add(rightIndex, asSet(value));
        }
        return rightNode;
      }

      this.keys.add(insertIndex, key);
      this.value.add(insertIndex, asSet(value));

      return null;
    }

    /**
     * 进行分裂操作
     *
     * @return
     */
    private BPlusTreeLeafNode spit() {
      int medianIndex = getMedianIndex();
      List<K> allKey = keys;
      List<Set<E>> allValue = value;

      // 将原来的节点，只留下左半边数据
      this.keys = new ArrayList<>(allKey.subList(0, medianIndex));
      this.value = new ArrayList<>(allValue.subList(0, medianIndex));

      List<K> rightKey = new ArrayList<>(allKey.subList(medianIndex, allKey.size()));
      List<Set<E>> rightValue = new ArrayList<>(allValue.subList(medianIndex, allValue.size()));

      BPlusTreeLeafNode newLeaf = new BPlusTreeLeafNode(rightKey, rightValue);

      newLeaf.next = this.next;
      this.next = newLeaf;

      return newLeaf;
    }

    private int getEqualsEntryIndex(K key) {
      int low = 0;
      int high = keys.size() - 1;

      while (low < high) {
        int mid = (high + low) >> 1;

        int comp = keys.get(mid).compareTo(key);
        if (comp == 0) {
          return mid;
        }
        // 当中位数，大于了查找了key，则在左区间继续查找
        else if (comp > 0) {
          high = mid - 1;
        }
        // 否则即为小于了查找的吸，则在右区间继续查找
        else {
          low = mid + 1;
        }
      }
      return -1;
    }

    @Override
    public boolean update(K key, E oldValue, E newValue) {
      return false;
    }

    @Override
    public RemoveResult remove(K key) {
      return null;
    }

    @Override
    public RemoveResult remove(K key, E value) {
      return null;
    }

    @Override
    public void combine(BPlusTreeNode neighbor, K parentEntry) {}

    @Override
    public void borrow(BPlusTreeNode neighbor, K parentEntry, boolean isLeaf) {}
  }

  /** 非叶子节点 */
  private class BPlusTreeNonLeafNode extends BPlusTreeNode {

    /** 子节点信息 */
    private List<BPlusTreeNode> children;

    public BPlusTreeNonLeafNode(List<K> key, List<BPlusTreeNode> children) {
      this.keys = key;
      this.children = children;
    }

    /**
     * 查找叶子节点的首个值
     *
     * @param cur
     * @return
     */
    public K findLeafEntry(BPlusTreeNode cur) {

      if (cur.getClass().equals(BPlusTreeLeafNode.class)) {
        return ((BPlusTreeLeafNode) cur).keys.get(0);
      }

      return findLeafEntry(((BPlusTreeNonLeafNode) cur).children.get(0));
    }

    @Override
    public List<E> rangeQuery(K start, K end) {
      return null;
    }

    @Override
    public List<E> query(K key) {

      int findEntryIndex = findEntryIndex(key);
      int childIndex = findChildIndex(findEntryIndex, key);

      // 进行索引层的搜索操作
      return children.get(childIndex).query(key);
    }

    @Override
    public BPlusTreeNode insert(K key, E value) {

      // 1,找到当前key的索引位置
      int keyIndex = this.findEntryIndex(key);
      // 找到子节点的索引位置
      int childrenIndex = this.findChildIndex(keyIndex, key);
      // 获取子节点
      BPlusTreeNode childrenNode = children.get(childrenIndex);
      // 插入操作
      BPlusTreeNode newChildrenNode = childrenNode.insert(key, value);

      // 如果当前发生了分裂,则需要检查父节点是否要进行分裂操作
      if (newChildrenNode != null) {
        K leafKey = findLeafEntry(newChildrenNode);
        // 找到key所在的索引
        int findKeyIndex = findEntryIndex(leafKey);
        // 如果当前节点已经满了，进行分裂操作
        if (isFull()) {
          BPlusTreeNonLeafNode newParentNode = this.split();
          int medianIndex = this.getMedianIndex();
          // 如果插入的位置，未超过中位数，则在左子树中插入
          if (findKeyIndex < medianIndex) {
            this.keys.add(findKeyIndex, leafKey);
            this.children.add(findKeyIndex + 1, newChildrenNode);
          }
          // 如果超过了，在右子树中插入
          else {
            int rightIndex = newParentNode.findEntryIndex(key);

            newParentNode.keys.add(rightIndex, leafKey);
            newParentNode.children.add(rightIndex, newChildrenNode);
          }
          newParentNode.keys.remove(0);
          return newParentNode;
        }
        keys.add(findKeyIndex, leafKey);
        children.add(findKeyIndex + 1, newChildrenNode);
      }

      return null;
    }

    /**
     * 树节点的分裂操作
     *
     * @return
     */
    private BPlusTreeNonLeafNode split() {
      int medianIndex = this.getMedianIndex();

      List<K> allKey = keys;
      List<BPlusTreeNode> allChildren = children;

      // 左子树
      this.keys = new ArrayList<>(allKey.subList(0, medianIndex));
      this.children = new ArrayList<>(allChildren.subList(0, medianIndex + 1));

      // 右子树
      List<K> rightKeys = new ArrayList<>(allKey.subList(medianIndex, allKey.size()));
      List<BPlusTreeNode> rightChildren =
          new ArrayList<>(allChildren.subList(medianIndex + 1, allChildren.size()));

      return new BPlusTreeNonLeafNode(rightKeys, rightChildren);
    }

    private int findChildIndex(int indexEntry, K key) {
      if (indexEntry == keys.size() || key.compareTo(keys.get(indexEntry)) < 0) {
        return indexEntry;
      } else {
        return indexEntry + 1;
      }
    }

    @Override
    public boolean update(K key, E oldValue, E newValue) {
      return false;
    }

    @Override
    public RemoveResult remove(K key) {
      return null;
    }

    @Override
    public RemoveResult remove(K key, E value) {
      return null;
    }

    @Override
    public void combine(BPlusTreeNode neighbor, K parentEntry) {}

    @Override
    public void borrow(BPlusTreeNode neighbor, K parentEntry, boolean isLeaf) {}
  }
}
