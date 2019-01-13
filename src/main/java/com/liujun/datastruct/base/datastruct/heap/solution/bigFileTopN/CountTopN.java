package com.liujun.datastruct.base.datastruct.heap.solution.bigFileTopN;

import com.liujun.datastruct.base.datastruct.heap.solution.bigFileTopN.pojo.KeyBusi;

import java.util.*;

/**
 * 进行分片文件的TopN求解问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class CountTopN {
  /** 静态实例化对象 */
  public static final CountTopN INSTANCE = new CountTopN();

  /** 进行统计的map操作 */
  private Map<String, Integer> countMap = new HashMap<>(10240);

  /** 比较器对象 */
  private Comparator<KeyBusi> comp =
      (o1, o2) -> {
        if (o1.getCountNum() > o2.getCountNum()) {
          return 1;
        } else if (o1.getCountNum() < o2.getCountNum()) {
          return -1;
        }
        return 0;
      };

  public void dataClear() {
    countMap.clear();
  }

  /**
   * 将数据放入到map中以进行统计
   *
   * @param data 数据信息
   */
  public void putData(String data) {
    Integer outValue = countMap.get(data);

    if (null == outValue) {
      outValue = 0;
    }
    outValue++;

    countMap.put(data, outValue);
  }

  /**
   * 求解topk的问题
   *
   * @return
   */
  public KeyBusi[] getTopN(int numk) {

    PriorityQueue<KeyBusi> topN = new PriorityQueue<>(numk, comp);

    Iterator<Map.Entry<String, Integer>> iter = countMap.entrySet().iterator();

    Map.Entry<String, Integer> entry;

    while (iter.hasNext()) {
      entry = iter.next();

      if (topN.size() < numk) {
        topN.offer(new KeyBusi(entry.getKey(), entry.getValue()));
      } else {
        // 如果当前数据比小顶求的队头大，则加入，否则丢弃
        if (topN.peek().getCountNum() < entry.getValue()) {
          topN.poll();
          topN.offer(new KeyBusi(entry.getKey(), entry.getValue()));
        }
      }
    }

    // 结果集
    KeyBusi[] result = new KeyBusi[numk];
    topN.toArray(result);

    return result;
  }

  /**
   * 最终的TopN求解
   *
   * @return
   */
  public KeyBusi[] getTopN(List<KeyBusi[]> list, int numk) {

    // 进行求解
    PriorityQueue<KeyBusi> topN = new PriorityQueue<>(numk, comp);

    for (KeyBusi[] keys : list) {

      for (KeyBusi busi : keys) {

        if (busi == null) {
          break;
        }

        if (topN.size() < numk) {
          topN.offer(new KeyBusi(busi.getKey(), busi.getCountNum()));
        } else {
          // 如果当前数据比小顶求的队头大，则加入，否则丢弃
          if (topN.peek().getCountNum() < busi.getCountNum()) {
            topN.poll();
            topN.offer(busi);
          }
        }
      }
    }

    // 结果集
    KeyBusi[] result = new KeyBusi[numk];
    topN.toArray(result);

    return result;
  }
}
