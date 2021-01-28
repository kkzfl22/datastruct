package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 布隆过滤器操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class BoomFilterOperator {

  /** 1,3421,7728 占用1.3亿个位置 */
  public static final int DEFAULT_SIZE = 2 << 27;

  /** 占用8分之1的空间，存数据 */
  public static final int DATA_NUM = 2 << 24;

  /** 出现冲突的概率 */
  private static final double DEFAULT_FPP = 0.125;

  /** 原始数据填充布隆过滤器 */
  private final BloomFilter<String> bloomFilter;

  /** 默认，创建新的布隆过滤器 */
  public BoomFilterOperator() {
    this.bloomFilter =
        BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), DEFAULT_SIZE, DEFAULT_FPP);
  }

  /**
   * 加载已经存在的布隆过滤器
   *
   * @param filePath
   */
  public BoomFilterOperator(String filePath) {
    this.bloomFilter = this.load(filePath);
  }

  /**
   * 向布隆过滤器中装载数据
   *
   * @param data 数据内容
   */
  public void put(String data) {
    bloomFilter.put(data);
  }

  /**
   * 检查数据是否存在，可能存在，也可能不存在，如果说不存在，那是一定不存在，存在，则才有可能出现问题
   *
   * @param data
   * @return
   */
  public boolean mightContain(String data) {
    return bloomFilter.mightContain(data);
  }

  /**
   * 将布隆过滤器保存到文件中
   *
   * @param filePath
   */
  public void save(String filePath) {
    try (OutputStream outputStream = new FileOutputStream(filePath);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream); ) {
      this.bloomFilter.writeTo(bufferedOutputStream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public BloomFilter<String> load(String filePath) {

    BloomFilter<String> filterInstance = null;
    try (InputStream input = new FileInputStream(filePath);
        BufferedInputStream bufferedOutputStream = new BufferedInputStream(input); ) {
      filterInstance =
          BloomFilter.readFrom(bufferedOutputStream, Funnels.stringFunnel(StandardCharsets.UTF_8));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return filterInstance;
  }
}
