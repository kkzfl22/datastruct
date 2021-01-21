package com.liujun.datastruct.advanced.bloomFilter.dataCompare;

/**
 * 对比key
 * 
 * @author liujun
 * @version 0.0.1
 */
public interface CompareKeyInterface<V> {

    /**
     * 获取的key的信息
     * 
     * @param data
     * @return
     */
    String getKey(V data);

    /**
     * 多结果集对比的key
     * 
     * @param data
     * @return
     */
    String getKeyMany(V data);

}
