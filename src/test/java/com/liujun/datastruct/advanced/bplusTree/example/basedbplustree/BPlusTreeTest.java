package com.liujun.datastruct.advanced.bplusTree.example.basedbplustree;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author liujun
 * @since 2022/11/10
 */
public class BPlusTreeTest {

    private final int ENTRY_BOUND = 100;

    @Test
    public void insert() {
        BPlusTree<Integer, Integer> t = new BPlusTree<>(11);
        List<Integer> data = new ArrayList<>();
        for (int i = 9; i >= 0; --i) {
            t.insert(i, i);
            data.add(i);
        }
        t.insert(0, 10);
        data.sort(Integer::compareTo);
        Assertions.assertEquals(t.toString(), data.toString());
        t = new BPlusTree<>(10);
        for (int i = 9; i >= 0; --i) {
            t.insert(i, i);
            data.add(i);
        }
        List<Integer> root = Arrays.asList(5);
        List<Integer> leftChild = Arrays.asList(0, 1, 2, 3, 4);
        List<Integer> rightChild = Arrays.asList(5, 6, 7, 8, 9);
        Assertions.assertEquals(t.toString(), root + "  \n" + leftChild + "  " + rightChild + "  \n");
    }

    @Test
    public void equivalentQuery() {
        BPlusTree<Integer, Integer> t = new BPlusTree<>(5);
        Assertions.assertEquals(t.query(0), Collections.emptyList());
        this.load(t);
        for (int i = 0; i < 1000; ++i) {
            int query = ThreadLocalRandom.current().nextInt(ENTRY_BOUND);
            Assertions.assertEquals(t.query(query).toString(), VITEquivalentQuery(query).toString());
        }

    }

    @Test
    public void rangeQuery() {
        BPlusTree<Integer, Integer> t = new BPlusTree<>(5);
        Assertions.assertEquals(t.rangeQuery(0, 10), Collections.emptyList());
        this.load(t);
        for (int i = 0; i < 1000; ++i) {
            int startInclude = ThreadLocalRandom.current().nextInt(ENTRY_BOUND - 1);
            int endExclude = ThreadLocalRandom.current().nextInt(startInclude, ENTRY_BOUND);
            Assertions.assertEquals(t.rangeQuery(startInclude, endExclude).toString(), VITRangeQuery(startInclude, endExclude).toString());
        }
    }

    @Test
    public void testUpdate() {
        BPlusTree<Integer, Integer> t = new BPlusTree<>();
        Assertions.assertFalse(t.update(0, 1, 2));
        this.load(t);
        Assertions.assertEquals(t.query(2), Arrays.asList(2));
        Assertions.assertTrue(t.update(2, 2, 101));
        Assertions.assertEquals(t.query(2), Arrays.asList(101));
        Assertions.assertFalse(t.update(2, 102, 103));
        Assertions.assertFalse(t.update(100, 2, 3));
    }

    @Test
    public void testRemove() {
        BPlusTree<Integer, Integer> t = new BPlusTree<>();
        Assertions.assertFalse(t.remove(0, 1));
        this.load(t);
        Assertions.assertFalse(t.remove(100, 100));
        for (int i = 0; i < 100; ++i) {
            t.remove(i, i);
        }
        this.reverseLoad(t);
        for (int i = ENTRY_BOUND - 1; i >= 0; --i) {
            t.remove(i, i);
        }
        for (int i = 0; i < ENTRY_BOUND; i += 2) {
            t.insert(i, i);
        }
        t.insert(7, 7);
        Assertions.assertTrue(t.remove(8, 8));
        for (int i = 30; i >= 0; i -= 2) {
            t.remove(i, i);
        }
    }

    @Test
    public void testRemoveInBatch() {
        BPlusTree<Integer, Integer> t = new BPlusTree<>();
        Assertions.assertFalse(t.remove(0));
        this.reverseLoad(t);
        Assertions.assertFalse(t.remove(ENTRY_BOUND));
        for (int i = ENTRY_BOUND - 1; i >= 0; --i) {
            Assertions.assertTrue(t.remove(i));
        }
    }

    private List<Integer> VITEquivalentQuery(int query) {
        Set<Integer> temp = new HashSet<>();
        for (int dataItem = 0; dataItem < ENTRY_BOUND; ++dataItem) {
            if (dataItem == query) {
                temp.add(dataItem);
            }
        }

        List<Integer> res = new ArrayList<>(temp);
        res.sort(Integer::compareTo);
        return res;
    }

    private List<Integer> VITRangeQuery(int startInclude, int endExclude) {
        Set<Integer> temp = new HashSet<>();
        for (int dataItem = 0; dataItem < ENTRY_BOUND; ++dataItem) {
            if (dataItem >= startInclude && dataItem < endExclude) {
                temp.add(dataItem);
            }
        }

        List<Integer> res = new ArrayList<>(temp);
        res.sort(Integer::compareTo);
        return res;
    }

    private void load(BPlusTree<Integer, Integer> bPlusTree) {
        for (int i = 0; i < ENTRY_BOUND; ++i) {
            bPlusTree.insert(i, i);
        }
    }

    private void reverseLoad(BPlusTree<Integer, Integer> bPlusTree) {
        for (int i = ENTRY_BOUND - 1; i >= 0; --i) {
            bPlusTree.insert(i, i);
        }
    }
}