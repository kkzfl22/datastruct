B+树Java代码实现以及测试
M阶B+树的定义：

任意非叶子结点最多有M个子节点；且M>2；
除根结点以外的非叶子结点至少有 M/2个子节点；
根结点至少有2个子节点；
除根节点外每个结点存放至少M/2和至多M个关键字；（至少2个关键字）
非叶子结点的子树指针与关键字个数相同；
所有结点的关键字：K[1], K[2], …, K[M]；且K[i] < K[i+1]；
非叶子结点的子树指针P[i]，指向关键字值属于[K[i], K[i+1])的子树；
所有叶子结点位于同一层；
为所有叶子结点增加一个链指针；
所有关键字都在叶子结点出现；
　　B+树是对B树的一种变形树，它与B树的差异在于：

有k个子结点的结点必然有k个关键码；
非叶结点仅具有索引作用，跟记录有关的信息均存放在叶结点中。
树的所有叶结点构成一个有序链表，可以按照关键码排序的次序遍历全部记录。
　　如下图，是一个B+树:
![B+树](file:///D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\advanced\bplusTree\md\base.jpg)

 
 下图是B+树的插入动画：
 
 file:///D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\advanced\bplusTree\md\1010726-20191026024724004-1911928782.gif

 
　　　　

　　代码如下：

　　BPlusTree定义如下：

```java


/**
 * B+树的定义：
 * 1.任意非叶子结点最多有M个子节点；且M>2；M为B+树的阶数
 * 2.除根结点以外的非叶子结点至少有 (M+1)/2个子节点；
 * 3.根结点至少有2个子节点；
 * 4.除根节点外每个结点存放至少（M-1）/2和至多M-1个关键字；（至少1个关键字）
 * 5.非叶子结点的子树指针比关键字多1个；
 * 6.非叶子节点的所有key按升序存放，假设节点的关键字分别为K[0], K[1] … K[M-2],指向子女的指针分别为P[0], P[1]…P[M-1]。则有：
 * 　　　P[0] < K[0] <= P[1] < K[1] …..< K[M-2] <= P[M-1]
 * 7.所有叶子结点位于同一层；
 * 8.为所有叶子结点增加一个链指针；
 * 9.所有关键字都在叶子结点出现
 */

@SuppressWarnings("all")
public class BPlusTree<K extends Comparable<K>, V> {

    // 根节点
    protected BPlusNode<K, V> root;

    // 阶数，M值
    protected int order;

    // 叶子节点的链表头
    protected BPlusNode<K, V> head;

    // 树高
    protected int height = 0;

    public BPlusNode<K, V> getHead() {
        return head;
    }

    public void setHead(BPlusNode<K, V> head) {
        this.head = head;
    }

    public BPlusNode<K, V> getRoot() {
        return root;
    }

    public void setRoot(BPlusNode<K, V> root) {
        this.root = root;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public V get(K key) {
        return root.get(key);
    }

    public V remove(K key) {
        return root.remove(key, this);
    }

    public void insertOrUpdate(K key, V value) {
        root.insertOrUpdate(key, value, this);
    }

    public BPlusTree(int order) {
        if (order < 3) {
            System.out.print("order must be greater than 2");
            System.exit(0);
        }
        this.order = order;
        root = new BPlusNode<K, V>(true, true);
        head = root;
    }

    public void printBPlusTree() {
        this.root.printBPlusTree(0);
    }
}
```

复制代码
　　BplusNode定义如下：

复制代码
```java


@SuppressWarnings("all")
public class BPlusNode<K extends Comparable<K>, V> {

    // 是否为叶子节点
    protected boolean isLeaf;

    // 是否为根节点
    protected boolean isRoot;

    // 父节点
    protected BPlusNode<K, V> parent;

    // 叶节点的前节点
    protected BPlusNode<K, V> previous;

    // 叶节点的后节点
    protected BPlusNode<K, V> next;

    // 节点的关键字列表
    protected List<Entry<K, V>> entries;

    // 子节点列表
    protected List<BPlusNode<K, V>> children;

    public BPlusNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        entries = new ArrayList();

        if (!isLeaf) {
            children = new ArrayList();
        }
    }

    public BPlusNode(boolean isLeaf, boolean isRoot) {
        this(isLeaf);
        this.isRoot = isRoot;
    }

    public V get(K key) {
        //如果是叶子节点 
        if (isLeaf) {
            int low = 0, high = entries.size() - 1, mid;
            int comp;
            while (low <= high) {
                mid = (low + high) / 2;
                comp = entries.get(mid).getKey().compareTo(key);
                if (comp == 0) {
                    return entries.get(mid).getValue();
                } else if (comp < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            //未找到所要查询的对象 
            return null;
        }
        //如果不是叶子节点 
        //如果key小于节点最左边的key，沿第一个子节点继续搜索 
        if (key.compareTo(entries.get(0).getKey()) < 0) {
            return children.get(0).get(key);
            //如果key大于等于节点最右边的key，沿最后一个子节点继续搜索 
        } else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
            return children.get(children.size() - 1).get(key);
            //否则沿比key大的前一个子节点继续搜索 
        } else {
            int low = 0, high = entries.size() - 1, mid = 0;
            int comp;
            while (low <= high) {
                mid = (low + high) / 2;
                comp = entries.get(mid).getKey().compareTo(key);
                if (comp == 0) {
                    return children.get(mid + 1).get(key);
                } else if (comp < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return children.get(low).get(key);
        }
    }

    public void insertOrUpdate(K key, V value, BPlusTree<K, V> tree) {
        //如果是叶子节点 
        if (isLeaf) {
            //不需要分裂，直接插入或更新 
            if (contains(key) != -1 || entries.size() < tree.getOrder()) {
                insertOrUpdate(key, value);
                if (tree.getHeight() == 0) {
                    tree.setHeight(1);
                }
                return;
            }
            //需要分裂 
            //分裂成左右两个节点 
            BPlusNode<K, V> left = new BPlusNode<K, V>(true);
            BPlusNode<K, V> right = new BPlusNode<K, V>(true);
            //设置链接 
            if (previous != null) {
                previous.next = left;
                left.previous = previous;
            }
            if (next != null) {
                next.previous = right;
                right.next = next;
            }
            if (previous == null) {
                tree.setHead(left);
            }

            left.next = right;
            right.previous = left;
            previous = null;
            next = null;

            //复制原节点关键字到分裂出来的新节点 
            copy2Nodes(key, value, left, right, tree);

            //如果不是根节点 
            if (parent != null) {
                //调整父子节点关系 
                int index = parent.children.indexOf(this);
                parent.children.remove(this);
                left.parent = parent;
                right.parent = parent;
                parent.children.add(index, left);
                parent.children.add(index + 1, right);
                parent.entries.add(index, right.entries.get(0));
                entries = null; //删除当前节点的关键字信息
                children = null; //删除当前节点的孩子节点引用

                //父节点插入或更新关键字 
                parent.updateInsert(tree);
                parent = null; //删除当前节点的父节点引用
                //如果是根节点     
            } else {
                isRoot = false;
                BPlusNode<K, V> parent = new BPlusNode<K, V>(false, true);
                tree.setRoot(parent);
                left.parent = parent;
                right.parent = parent;
                parent.children.add(left);
                parent.children.add(right);
                parent.entries.add(right.entries.get(0));
                entries = null;
                children = null;
            }
            return;

        }
        //如果不是叶子节点
        //如果key小于等于节点最左边的key，沿第一个子节点继续搜索 
        if (key.compareTo(entries.get(0).getKey()) < 0) {
            children.get(0).insertOrUpdate(key, value, tree);
            //如果key大于节点最右边的key，沿最后一个子节点继续搜索 
        } else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
            children.get(children.size() - 1).insertOrUpdate(key, value, tree);
            //否则沿比key大的前一个子节点继续搜索 
        } else {
            int low = 0, high = entries.size() - 1, mid = 0;
            int comp;
            while (low <= high) {
                mid = (low + high) / 2;
                comp = entries.get(mid).getKey().compareTo(key);
                if (comp == 0) {
                    children.get(mid + 1).insertOrUpdate(key, value, tree);
                    break;
                } else if (comp < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            if (low > high) {
                children.get(low).insertOrUpdate(key, value, tree);
            }
        }
    }

    private void copy2Nodes(K key, V value, BPlusNode<K, V> left,
                            BPlusNode<K, V> right, BPlusTree<K, V> tree) {
        //左右两个节点关键字长度 
        int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2;
        boolean b = false;//用于记录新元素是否已经被插入
        for (int i = 0; i < entries.size(); i++) {
            if (leftSize != 0) {
                leftSize--;
                if (!b && entries.get(i).getKey().compareTo(key) > 0) {
                    left.entries.add(new SimpleEntry<K, V>(key, value));
                    b = true;
                    i--;
                } else {
                    left.entries.add(entries.get(i));
                }
            } else {
                if (!b && entries.get(i).getKey().compareTo(key) > 0) {
                    right.entries.add(new SimpleEntry<K, V>(key, value));
                    b = true;
                    i--;
                } else {
                    right.entries.add(entries.get(i));
                }
            }
        }
        if (!b) {
            right.entries.add(new SimpleEntry<K, V>(key, value));
        }
    }

    /**
     * 插入节点后中间节点的更新
     */
    protected void updateInsert(BPlusTree<K, V> tree) {

        //如果子节点数超出阶数，则需要分裂该节点    
        if (children.size() > tree.getOrder()) {
            //分裂成左右两个节点 
            BPlusNode<K, V> left = new BPlusNode<K, V>(false);
            BPlusNode<K, V> right = new BPlusNode<K, V>(false);
            //左右两个节点子节点的长度 
            int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2;
            int rightSize = (tree.getOrder() + 1) / 2;
            //复制子节点到分裂出来的新节点，并更新关键字 
            for (int i = 0; i < leftSize; i++) {
                left.children.add(children.get(i));
                children.get(i).parent = left;
            }
            for (int i = 0; i < rightSize; i++) {
                right.children.add(children.get(leftSize + i));
                children.get(leftSize + i).parent = right;
            }
            for (int i = 0; i < leftSize - 1; i++) {
                left.entries.add(entries.get(i));
            }
            for (int i = 0; i < rightSize - 1; i++) {
                right.entries.add(entries.get(leftSize + i));
            }

            //如果不是根节点 
            if (parent != null) {
                //调整父子节点关系 
                int index = parent.children.indexOf(this);
                parent.children.remove(this);
                left.parent = parent;
                right.parent = parent;
                parent.children.add(index, left);
                parent.children.add(index + 1, right);
                parent.entries.add(index, entries.get(leftSize - 1));
                entries = null;
                children = null;

                //父节点更新关键字 
                parent.updateInsert(tree);
                parent = null;
                //如果是根节点     
            } else {
                isRoot = false;
                BPlusNode<K, V> parent = new BPlusNode<K, V>(false, true);
                tree.setRoot(parent);
                tree.setHeight(tree.getHeight() + 1);
                left.parent = parent;
                right.parent = parent;
                parent.children.add(left);
                parent.children.add(right);
                parent.entries.add(entries.get(leftSize - 1));
                entries = null;
                children = null;
            }
        }
    }

    /**
     * 删除节点后中间节点的更新
     */
    protected void updateRemove(BPlusTree<K, V> tree) {

        // 如果子节点数小于M / 2或者小于2，则需要合并节点 
        if (children.size() < tree.getOrder() / 2 || children.size() < 2) {
            if (isRoot) {
                // 如果是根节点并且子节点数大于等于2，OK 
                if (children.size() >= 2) return;
                // 否则与子节点合并 
                BPlusNode<K, V> root = children.get(0);
                tree.setRoot(root);
                tree.setHeight(tree.getHeight() - 1);
                root.parent = null;
                root.isRoot = true;
                entries = null;
                children = null;
                return;
            }
            //计算前后节点  
            int currIdx = parent.children.indexOf(this);
            int prevIdx = currIdx - 1;
            int nextIdx = currIdx + 1;
            BPlusNode<K, V> previous = null, next = null;
            if (prevIdx >= 0) {
                previous = parent.children.get(prevIdx);
            }
            if (nextIdx < parent.children.size()) {
                next = parent.children.get(nextIdx);
            }

            // 如果前节点子节点数大于M / 2并且大于2，则从其处借补 
            if (previous != null
                    && previous.children.size() > tree.getOrder() / 2
                    && previous.children.size() > 2) {
                //前叶子节点末尾节点添加到首位 
                int idx = previous.children.size() - 1;
                BPlusNode<K, V> borrow = previous.children.get(idx);
                previous.children.remove(idx);
                borrow.parent = this;
                children.add(0, borrow);
                int preIndex = parent.children.indexOf(previous);

                entries.add(0, parent.entries.get(preIndex));
                parent.entries.set(preIndex, previous.entries.remove(idx - 1));
                return;
            }

            // 如果后节点子节点数大于M / 2并且大于2，则从其处借补
            if (next != null
                    && next.children.size() > tree.getOrder() / 2
                    && next.children.size() > 2) {
                //后叶子节点首位添加到末尾 
                BPlusNode<K, V> borrow = next.children.get(0);
                next.children.remove(0);
                borrow.parent = this;
                children.add(borrow);
                int preIndex = parent.children.indexOf(this);
                entries.add(parent.entries.get(preIndex));
                parent.entries.set(preIndex, next.entries.remove(0));
                return;
            }

            // 同前面节点合并 
            if (previous != null
                    && (previous.children.size() <= tree.getOrder() / 2
                    || previous.children.size() <= 2)) {
                for (int i = 0; i < children.size(); i++) {
                    previous.children.add(children.get(i));
                }
                for (int i = 0; i < previous.children.size(); i++) {
                    previous.children.get(i).parent = this;
                }
                int indexPre = parent.children.indexOf(previous);
                previous.entries.add(parent.entries.get(indexPre));
                for (int i = 0; i < entries.size(); i++) {
                    previous.entries.add(entries.get(i));
                }
                children = previous.children;
                entries = previous.entries;

                //更新父节点的关键字列表
                parent.children.remove(previous);
                previous.parent = null;
                previous.children = null;
                previous.entries = null;
                parent.entries.remove(parent.children.indexOf(this));
                if ((!parent.isRoot
                        && (parent.children.size() >= tree.getOrder() / 2
                        && parent.children.size() >= 2))
                        || parent.isRoot && parent.children.size() >= 2) {
                    return;
                }
                parent.updateRemove(tree);
                return;
            }

            // 同后面节点合并 
            if (next != null
                    && (next.children.size() <= tree.getOrder() / 2
                    || next.children.size() <= 2)) {
                for (int i = 0; i < next.children.size(); i++) {
                    BPlusNode<K, V> child = next.children.get(i);
                    children.add(child);
                    child.parent = this;
                }
                int index = parent.children.indexOf(this);
                entries.add(parent.entries.get(index));
                for (int i = 0; i < next.entries.size(); i++) {
                    entries.add(next.entries.get(i));
                }
                parent.children.remove(next);
                next.parent = null;
                next.children = null;
                next.entries = null;
                parent.entries.remove(parent.children.indexOf(this));
                if ((!parent.isRoot && (parent.children.size() >= tree.getOrder() / 2
                        && parent.children.size() >= 2))
                        || parent.isRoot && parent.children.size() >= 2) {
                    return;
                }
                parent.updateRemove(tree);
                return;
            }
        }
    }

    public V remove(K key, BPlusTree<K, V> tree) {
        //如果是叶子节点 
        if (isLeaf) {
            //如果不包含该关键字，则直接返回 
            if (contains(key) == -1) {
                return null;
            }
            //如果既是叶子节点又是根节点，直接删除 
            if (isRoot) {
                if (entries.size() == 1) {
                    tree.setHeight(0);
                }
                return remove(key);
            }
            //如果关键字数大于M / 2，直接删除 
            if (entries.size() > tree.getOrder() / 2 && entries.size() > 2) {
                return remove(key);
            }
            //如果自身关键字数小于M / 2，并且前节点关键字数大于M / 2，则从其处借补 
            if (previous != null &&
                    previous.parent == parent
                    && previous.entries.size() > tree.getOrder() / 2
                    && previous.entries.size() > 2) {
                //添加到首位 
                int size = previous.entries.size();
                entries.add(0, previous.entries.remove(size - 1));
                int index = parent.children.indexOf(previous);
                parent.entries.set(index, entries.get(0));
                return remove(key);
            }
            //如果自身关键字数小于M / 2，并且后节点关键字数大于M / 2，则从其处借补 
            if (next != null
                    && next.parent == parent
                    && next.entries.size() > tree.getOrder() / 2
                    && next.entries.size() > 2) {
                entries.add(next.entries.remove(0));
                int index = parent.children.indexOf(this);
                parent.entries.set(index, next.entries.get(0));
                return remove(key);
            }

            //同前面节点合并 
            if (previous != null
                    && previous.parent == parent
                    && (previous.entries.size() <= tree.getOrder() / 2
                    || previous.entries.size() <= 2)) {
                V returnValue = remove(key);
                for (int i = 0; i < entries.size(); i++) {
                    //将当前节点的关键字添加到前节点的末尾
                    previous.entries.add(entries.get(i));
                }
                entries = previous.entries;
                parent.children.remove(previous);
                previous.parent = null;
                previous.entries = null;
                //更新链表 
                if (previous.previous != null) {
                    BPlusNode<K, V> temp = previous;
                    temp.previous.next = this;
                    previous = temp.previous;
                    temp.previous = null;
                    temp.next = null;
                } else {
                    tree.setHead(this);
                    previous.next = null;
                    previous = null;
                }
                parent.entries.remove(parent.children.indexOf(this));
                if ((!parent.isRoot && (parent.children.size() >= tree.getOrder() / 2
                        && parent.children.size() >= 2))
                        || parent.isRoot && parent.children.size() >= 2) {
                    return returnValue;
                }
                parent.updateRemove(tree);
                return returnValue;
            }
            //同后面节点合并
            if (next != null
                    && next.parent == parent
                    && (next.entries.size() <= tree.getOrder() / 2
                    || next.entries.size() <= 2)) {
                V returnValue = remove(key);
                for (int i = 0; i < next.entries.size(); i++) {
                    //从首位开始添加到末尾 
                    entries.add(next.entries.get(i));
                }
                next.parent = null;
                next.entries = null;
                parent.children.remove(next);
                //更新链表 
                if (next.next != null) {
                    BPlusNode<K, V> temp = next;
                    temp.next.previous = this;
                    next = temp.next;
                    temp.previous = null;
                    temp.next = null;
                } else {
                    next.previous = null;
                    next = null;
                }
                //更新父节点的关键字列表
                parent.entries.remove(parent.children.indexOf(this));
                if ((!parent.isRoot && (parent.children.size() >= tree.getOrder() / 2
                        && parent.children.size() >= 2))
                        || parent.isRoot && parent.children.size() >= 2) {
                    return returnValue;
                }
                parent.updateRemove(tree);
                return returnValue;
            }
        }
        /*如果不是叶子节点*/

        //如果key小于等于节点最左边的key，沿第一个子节点继续搜索 
        if (key.compareTo(entries.get(0).getKey()) < 0) {
            return children.get(0).remove(key, tree);
            //如果key大于节点最右边的key，沿最后一个子节点继续搜索 
        } else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
            return children.get(children.size() - 1).remove(key, tree);
            //否则沿比key大的前一个子节点继续搜索 
        } else {
            int low = 0, high = entries.size() - 1, mid = 0;
            int comp;
            while (low <= high) {
                mid = (low + high) / 2;
                comp = entries.get(mid).getKey().compareTo(key);
                if (comp == 0) {
                    return children.get(mid + 1).remove(key, tree);
                } else if (comp < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return children.get(low).remove(key, tree);
        }
    }

    // 判断当前节点是否包含该关键字
    protected int contains(K key) {
        int low = 0, high = entries.size() - 1, mid;
        int comp;
        while (low <= high) {
            mid = (low + high) / 2;
            comp = entries.get(mid).getKey().compareTo(key);
            if (comp == 0) {
                return mid;
            } else if (comp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // 插入到当前节点的关键字中
    protected void insertOrUpdate(K key, V value) {
        //二叉查找，插入
        int low = 0, high = entries.size() - 1, mid;
        int comp;
        while (low <= high) {
            mid = (low + high) / 2;
            comp = entries.get(mid).getKey().compareTo(key);
            if (comp == 0) {
                entries.get(mid).setValue(value);
                break;
            } else if (comp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        if (low > high) {
            entries.add(low, new SimpleEntry<K, V>(key, value));
        }
    }

    // 删除节点
    protected V remove(K key) {
        int low = 0, high = entries.size() - 1, mid;
        int comp;
        while (low <= high) {
            mid = (low + high) / 2;
            comp = entries.get(mid).getKey().compareTo(key);
            if (comp == 0) {
                return entries.remove(mid).getValue();
            } else if (comp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("isRoot: ");
        sb.append(isRoot);
        sb.append(", ");
        sb.append("isLeaf: ");
        sb.append(isLeaf);
        sb.append(", ");
        sb.append("keys: ");
        for (Entry<K, V> entry : entries) {
            sb.append(entry.getKey());
            sb.append(", ");
        }
        sb.append(", ");
        return sb.toString();
    }

    public void printBPlusTree(int index) {
        if (this.isLeaf) {
            System.out.print("层级：" + index + ",叶子节点，keys为: ");
            for (int i = 0; i < entries.size(); ++i)
                System.out.print(entries.get(i) + " ");
            System.out.println();
        } else {
            System.out.print("层级：" + index + ",非叶子节点，keys为: ");
            for (int i = 0; i < entries.size(); ++i)
                System.out.print(entries.get(i) + " ");
            System.out.println();
            for (int i = 0; i < children.size(); ++i)
                children.get(i).printBPlusTree(index + 1);
        }
    }
}
```

　　测试类：

```java
public class BPlusTreeTest {
    // 测试
    public static void main(String[] args) {

        int size = 10;
        int order = 3;
        testRandomInsert(size, order);

        testOrderInsert(size, order);

        testRandomSearch(size, order);

        testOrderSearch(size, order);

        testRandomRemove(size, order);

        testOrderRemove(size, order);
    }

    private static void testOrderRemove(int size, int order) {
        BPlusTree<Integer, Integer> tree = new BPlusTree<Integer, Integer>(order);
        System.out.println("\nTest order remove " + size + " datas, of order:"
                + order);
        System.out.println("Begin order insert...");
        for (int i = 0; i < size; i++) {
            tree.insertOrUpdate(i, i);
        }
        tree.printBPlusTree();
        System.out.println("Begin order remove...");
        long current = System.currentTimeMillis();
        for (int j = 0; j < size; j++) {
            if (tree.remove(j) == null) {
                System.err.println("得不到数据:" + j);
                break;
            }
        }
        long duration = System.currentTimeMillis() - current;
        System.out.println("time elpsed for duration: " + duration);
        tree.printBPlusTree();
        System.out.println(tree.getHeight());
    }

    private static void testRandomRemove(int size, int order) {
        BPlusTree<Integer, Integer> tree = new BPlusTree<Integer, Integer>(order);
        System.out.println("\nTest random remove " + size + " datas, of order:"
                + order);
        Random random = new Random();
        boolean[] a = new boolean[size + 10];
        List<Integer> list = new ArrayList<Integer>();
        int randomNumber = 0;
        System.out.println("Begin random insert...");
        for (int i = 0; i < size; i++) {
            randomNumber = random.nextInt(size);
            a[randomNumber] = true;
            list.add(randomNumber);
            tree.insertOrUpdate(randomNumber, randomNumber);
        }
        tree.printBPlusTree();
        System.out.println("Begin random remove...");
        long current = System.currentTimeMillis();
        for (int j = 0; j < size; j++) {
            randomNumber = list.get(j);
            if (a[randomNumber]) {
                if (tree.remove(randomNumber) == null) {
                    System.err.println("得不到数据:" + randomNumber);
                    break;
                } else {
                    a[randomNumber] = false;
                }
            }
        }
        long duration = System.currentTimeMillis() - current;
        System.out.println("time elpsed for duration: " + duration);
        tree.printBPlusTree();
        System.out.println(tree.getHeight());
    }

    private static void testOrderSearch(int size, int order) {
        BPlusTree<Integer, Integer> tree = new BPlusTree<Integer, Integer>(order);
        System.out.println("\nTest order search " + size + " datas, of order:"
                + order);
        System.out.println("Begin order insert...");
        for (int i = 0; i < size; i++) {
            tree.insertOrUpdate(i, i);
        }
        tree.printBPlusTree();
        System.out.println("Begin order search...");
        long current = System.currentTimeMillis();
        for (int j = 0; j < size; j++) {
            if (tree.get(j) == null) {
                System.err.println("得不到数据:" + j);
                break;
            }
        }
        long duration = System.currentTimeMillis() - current;
        System.out.println("time elpsed for duration: " + duration);
    }

    private static void testRandomSearch(int size, int order) {
        BPlusTree<Integer, Integer> tree = new BPlusTree<Integer, Integer>(order);
        System.out.println("\nTest random search " + size + " datas, of order:"
                + order);
        Random random = new Random();
        boolean[] a = new boolean[size + 10];
        int randomNumber = 0;
        System.out.println("Begin random insert...");
        for (int i = 0; i < size; i++) {
            randomNumber = random.nextInt(size);
            a[randomNumber] = true;
            tree.insertOrUpdate(randomNumber, randomNumber);
        }
        tree.printBPlusTree();
        System.out.println("Begin random search...");
        long current = System.currentTimeMillis();
        for (int j = 0; j < size; j++) {
            randomNumber = random.nextInt(size);
            if (a[randomNumber]) {
                if (tree.get(randomNumber) == null) {
                    System.err.println("得不到数据:" + randomNumber);
                    break;
                }
            }
        }
        long duration = System.currentTimeMillis() - current;
        System.out.println("time elpsed for duration: " + duration);
    }

    private static void testRandomInsert(int size, int order) {
        BPlusTree<Integer, Integer> tree = new BPlusTree<Integer, Integer>(order);
        System.out.println("\nTest random insert " + size + " datas, of order:"
                + order);
        Random random = new Random();
        int randomNumber = 0;
        long current = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            randomNumber = random.nextInt(size * 10);
            System.out.print(randomNumber + " ");
            tree.insertOrUpdate(randomNumber, randomNumber);
        }
        long duration = System.currentTimeMillis() - current;
        System.out.println("time elpsed for duration: " + duration);
        tree.printBPlusTree();
        System.out.println(tree.getHeight());
    }

    private static void testOrderInsert(int size, int order) {
        BPlusTree<Integer, Integer> tree = new BPlusTree<Integer, Integer>(order);
        System.out.println("\nTest order insert " + size + " datas, of order:"
                + order);
        long current = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            tree.insertOrUpdate(i, i);
        }
        long duration = System.currentTimeMillis() - current;
        System.out.println("time elpsed for duration: " + duration);
        tree.printBPlusTree();
    }

}

```