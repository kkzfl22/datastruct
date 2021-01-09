package com.liujun.datastruct.base.datastruct.hash.myhashmap;

/**
 * 自己实现一个linkedhashmap
 *
 * <p>特征： 1. 支持动态扩容。 每次以2倍大小进行扩容。
 *
 * <p>2. 初始化大小为16
 *
 * <p>3。 装载因子为0.75
 *
 * <p>4. 键冲突时，在8个以内，使用链表法，当超过8个时，使用跳表。以保证logn的时间复杂度的操作。
 *
 * <p>5. 插入时，检查数据是否已经存在，已经存在，则删除将数据移动至末尾。否则直接移动至末尾。
 *
 * <p>6. 获取一个数据时，移动至末尾。
 *
 * <p>7，删除时默认从队头做删除。
 *
 * @author liujun
 * @version 0.0.1
 */
public class MyLinkedHashMap {}
