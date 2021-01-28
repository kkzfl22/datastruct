package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare;

import com.config.Symbol;
import com.liujun.datastruct.utils.FileUtils;

import java.io.File;

/**
 * 布隆过滤器管理
 *
 * @author liujun
 * @version 0.0.1
 */
public class BoomFilterManager {

  /**
   * The maximum capacity, used if a higher value is implicitly specified by either of the
   * constructors with arguments. MUST be a power of two <= 1<<30.
   */
  private static final int MAXIMUM_CAPACITY = 1 << 30;

  /** 数据 */
  private static final String FILE_NAME = "bloom_data";

  /** 布隆过滤器后缀 */
  private static final String SUFFIX_NAME = ".bloom";

  /** 预估的数据量 */
  private final long mightNum;

  /** 数据长度 */
  private final int dataLength;

  /** 布隆过滤器的数量 */
  private final BoomFilterOperator[] boomFilter;

  /**
   * 进行布降过滤器管理器的初始化
   *
   * @param mightNum 预估的布隆过滤器的大小，不用特别精确
   */
  public BoomFilterManager(long mightNum) {
    this.mightNum = mightNum * 4;
    // 用于计算需要使用的存储的容量
    int dataNum = (int) this.mightNum / BoomFilterOperator.DATA_NUM;
    // 由于此处限制使用2的幂，可以更好的做到散列的平均
    int dataArrayLength = tableSizeFor(dataNum);
    this.dataLength = dataArrayLength;
    // 初始化布隆过滤器
    boomFilter = new BoomFilterOperator[this.dataLength];
    init();
  }

  private void init() {
    // 布隆过滤器初始化
    for (int i = 0; i < this.dataLength; i++) {
      this.boomFilter[i] = new BoomFilterOperator();
    }
  }

  public BoomFilterManager(String path) {
    this.mightNum = 0;
    this.boomFilter = this.load(path);
    this.dataLength = this.boomFilter.length;
  }

  /** Returns a power of two size for the given target capacity. */
  private static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
  }

  /** 来jdk源码中hashMap的hash函数 */
  static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }

  /**
   * 放入操作
   *
   * @param key 当前待存入的键
   */
  public void put(String key) {
    boomFilter[(dataLength - 1) & hash(key)].put(key);
  }

  /**
   * 检查数据是否存在，可能存在，也可能不存在，如果说不存在，那是一定不存在，存在，则才有可能出现问题
   *
   * @param key 当前存储的键操作
   * @return
   */
  public boolean mightContain(String key) {
    return boomFilter[(dataLength - 1) & hash(key)].mightContain(key);
  }

  public void save(String path) {
    FileUtils.checkAndMakeDir(path);

    for (int i = 0; i < boomFilter.length; i++) {
      boomFilter[i].save(outName(path, i));
    }
  }

  public BoomFilterOperator[] load(String path) {
    File[] bloomFiles = FileUtils.getFileList(path);

    if (bloomFiles.length < 1) {
      return new BoomFilterOperator[0];
    }

    BoomFilterOperator[] outBloomOperator = new BoomFilterOperator[bloomFiles.length];

    for (int i = 0; i < bloomFiles.length; i++) {
      int index = this.getIndex(bloomFiles[i]);
      outBloomOperator[index] = new BoomFilterOperator(bloomFiles[i].getPath());
    }

    return outBloomOperator;
  }

  /**
   * 获取文件索引号
   *
   * @param fileItem
   * @return
   */
  public int getIndex(File fileItem) {
    String name = fileItem.getName();
    int startPosition = name.lastIndexOf(Symbol.MINUS);
    int endPosition = name.lastIndexOf(SUFFIX_NAME);
    String indexStr = name.substring(startPosition + Symbol.MINUS.length(), endPosition);

    return Integer.parseInt(indexStr);
  }

  private String outName(String path, int index) {
    StringBuilder outName = new StringBuilder();

    outName.append(path);
    outName.append(Symbol.PATH);
    outName.append(FILE_NAME);
    outName.append(Symbol.MINUS);
    outName.append(index);
    outName.append(SUFFIX_NAME);
    return outName.toString();
  }
}
