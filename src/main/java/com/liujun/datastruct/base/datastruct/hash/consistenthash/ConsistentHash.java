package com.liujun.datastruct.base.datastruct.hash.consistenthash;

import com.config.Symbol;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一致性hash算法
 *
 * @author liujun
 * @version 0.0.1
 */
public class ConsistentHash {

  /** 用来存储当前的节点信息 */
  private static final Map<String, Integer> DATA_MAP = new ConcurrentHashMap<>();

  /** 用于记录当前一致性hasp的节点信息 */
  private static final SortedMap<Integer, String> SORT_MAP = new TreeMap<>();

  /**
   * 添加节点
   *
   * @param dataServerIp
   */
  public void addNode(String dataServerIp) {
    int hashCode = Hashing.murmur3_32().hashString(dataServerIp, StandardCharsets.UTF_8).asInt();
    DATA_MAP.put(dataServerIp, hashCode);
    SORT_MAP.put(hashCode, dataServerIp);
  }

  /**
   * 默认的服务器的IP
   *
   * @param serverIp
   */
  public void defaultNode(String serverIp) {
    SORT_MAP.put(Integer.MAX_VALUE, serverIp);
  }

  /**
   * 执行节点下线操作
   *
   * @param serviceIp
   */
  public void dataDown(String serviceIp) {
    Integer hashCode = DATA_MAP.get(serviceIp);

    if (null != hashCode) {
      DATA_MAP.remove(serviceIp);
      // 下线节点
      SORT_MAP.remove(hashCode);
      // 检查下线节点是否为默认节点,如果是，则需要替换节点
      String dataIp = SORT_MAP.get(Integer.MAX_VALUE);
      if (serviceIp.equals(dataIp)) {
        Iterator<Map.Entry<String, Integer>> dataIter = DATA_MAP.entrySet().iterator();
        while (dataIter.hasNext()) {
          Map.Entry<String, Integer> dataValue = dataIter.next();

          if (!serviceIp.equals(dataValue.getKey())) {
            SORT_MAP.put(Integer.MAX_VALUE, dataValue.getKey());
          }
        }
      } else {
        SORT_MAP.remove(hashCode);
      }
    }
  }

  /**
   * 获取客户端的节点信息
   *
   * @param clientIp
   * @return
   */
  public String getNode(String clientIp) {
    int hashCode = Hashing.murmur3_32().hashString(clientIp, StandardCharsets.UTF_8).asInt();
    SortedMap<Integer, String> dataNode = SORT_MAP.tailMap(hashCode);
    return dataNode.get(dataNode.firstKey());
  }
}
