package com.liujun.datastruct.hash.uniqueflag;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件唯一标识提取
 *
 * <p>实现原理，通过对文件几个点取室长的字节，
 *
 * <p>计算hash值，再将几个hash值求一个总的hash值，作为文件唯一标识
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/09
 */
public class FileUnique {

  /** 大小在超过此大小手才使用多段标标识求hash */
  private static final int spitSize = 1024;

  /** 每次读取的长度 */
  private static final int READ_LENGTH = 128;

  private static MessageDigest MD;

  static {
    try {
      MD = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  /**
   * 文件获取唯一标识的方法
   *
   * <p>在文件头，文件中部，及文件尾三个位置提取数据，然后使用md5来进行哈希码的生成
   *
   * @param path 文件路径
   * @return 文件信息
   */
  public String fileUnique(String path) {
    FileInputStream input = null;
    FileChannel inputChannel = null;

    try {
      input = new FileInputStream(path);

      if (input.available() > spitSize) {

        inputChannel = input.getChannel();

        long maxSize = inputChannel.size();

        ByteBuffer buffer = ByteBuffer.allocate(READ_LENGTH * 3 + 8);
        buffer.limit(READ_LENGTH);
        inputChannel.read(buffer);

        // 计算中间位置的唯一标识
        long mid = maxSize / 2;
        inputChannel.position(mid);
        buffer.limit(READ_LENGTH * 2);
        inputChannel.read(buffer);

        // 计算文件尾部的唯一标识
        long last = maxSize - (buffer.capacity() * 2);
        inputChannel.position(last);
        buffer.limit(READ_LENGTH * 3);
        inputChannel.read(buffer);

        buffer.limit(READ_LENGTH * 3 + 8);
        buffer.putLong(maxSize);

        return getMD5(buffer.array());
      } else {
        // 计算hash值
        byte[] countByte = new byte[READ_LENGTH];
        input.read(countByte);
        return getMD5(countByte);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != input) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != inputChannel) {
        try {
          inputChannel.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return null;
  }

  /**
   * 对字符串md5加密
   *
   * @param countsrc 计算数据
   * @return
   */
  public static String getMD5(byte[] countsrc) {
    try {
      // 计算md5函数
      MD.update(countsrc);

      StringBuilder hexString = new StringBuilder();

      byte[] hash = MD.digest();
      for (int i = 0; i < hash.length; i++) {
        if ((0xff & hash[i]) < 0x10) {
          hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
        } else {
          hexString.append(Integer.toHexString(0xFF & hash[i]));
        }
      }

      return hexString.toString();

      // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
      // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
      //      return new BigInteger(1, md.digest()).toString(16);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public static void main(String[] args) {
    FileUnique unique = new FileUnique();
    long start = System.currentTimeMillis();
    for (int i = 0; i < 10; i++) {

      String code = unique.fileUnique("D:\\java\\test\\meda\\_DSC0001.JPG");
      System.out.println(code);
    }

    long end = System.currentTimeMillis();

    byte[] bys = new byte[] {0, 0, 0, 0, 0, 75, -127, -65};
    ByteBuffer buffer = ByteBuffer.allocate(8);
    buffer.put(bys, 0, bys.length);
    buffer.flip(); // need flip

    System.out.println("大小:" + buffer.getLong());

    System.out.println("用时:" + (end - start));
    String code3 = unique.fileUnique("D:\\java\\test\\meda\\1.txt");
    System.out.println(code3);

    System.out.println(Long.MAX_VALUE);
    System.out.println(Long.MAX_VALUE / 1024 / 1024 / 1024 / 1024 / 1024 + "P");
    System.out.println(Integer.MAX_VALUE / 1024 / 1024 / 1024f + "G");
  }
}
