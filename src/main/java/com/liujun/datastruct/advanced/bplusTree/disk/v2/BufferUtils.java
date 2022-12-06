package com.liujun.datastruct.advanced.bplusTree.disk.v2;

import java.nio.ByteBuffer;

/**
 * @author liujun
 * @since 2022/11/22
 */
public class BufferUtils {

  /** 一个字节点用8位 */
  private static final int BYTE_ONE = 8;

  /** 两个字节的占用16位 */
  private static final int BYTE_TWO = 16;

  /** 三个字节占用24位 */
  private static final int BYTE_THIRD = 24;

  /** 4字节占用32位 */
  private static final int BYTE_FOUR = 32;

  /** 5字节占用 */
  private static final int BYTE_FIVE = 40;

  /** 6字节占用 */
  private static final int BYTE_SIX = 48;

  /** 7位占用 */
  private static final int BYTE_SEVEN = 56;

  /** 高位获&的值 */
  private static final int HIGH_GET = 0xff;

  /** byte的最大值 */
  private static final int BYTE_MAX_ONE = 251;

  /** 两个byte的最大值 */
  private static final int BYTE_MAX_TWO = (int) 0x10000L;

  /** 三个byte的最大值 */
  private static final int BYTE_MAX_THIRD = (int) 0x1000000L;

  /**
   * 写入两位字节
   *
   * @param buffer buffer信息
   * @param i 数据
   */
  public static final void writeUB2(ByteBuffer buffer, int i) {
    buffer.put((byte) (i & HIGH_GET));
    buffer.put((byte) (i >>> BYTE_ONE));
  }

  /**
   * 写入三个字节
   *
   * @param buffer buffer信息
   * @param i 三个字节的信息
   */
  public static final void writeUB3(ByteBuffer buffer, int i) {
    buffer.put((byte) (i & HIGH_GET));
    buffer.put((byte) (i >>> BYTE_ONE));
    buffer.put((byte) (i >>> BYTE_TWO));
  }

  /**
   * 写入4位字节的信息
   *
   * @param buffer
   * @param i
   */
  public static final void writeInt(ByteBuffer buffer, int i) {
    buffer.put((byte) (i & HIGH_GET));
    buffer.put((byte) (i >>> BYTE_ONE));
    buffer.put((byte) (i >>> BYTE_TWO));
    buffer.put((byte) (i >>> BYTE_THIRD));
  }

  /**
   * 写入浮点数
   *
   * @param buffer
   * @param f
   */
  public static final void writeFloat(ByteBuffer buffer, float f) {
    writeInt(buffer, Float.floatToIntBits(f));
  }

  /**
   * 写入4位的长整数
   *
   * @param buffer
   * @param l
   */
  public static final void writeUB4(ByteBuffer buffer, long l) {
    buffer.put((byte) (l & HIGH_GET));
    buffer.put((byte) (l >>> BYTE_ONE));
    buffer.put((byte) (l >>> BYTE_TWO));
    buffer.put((byte) (l >>> BYTE_THIRD));
  }

  /**
   * 写入BIT_EIGHT位的长整数
   *
   * @param buffer
   * @param l
   */
  public static final void writeLong(ByteBuffer buffer, long l) {
    buffer.put((byte) (l & HIGH_GET));
    buffer.put((byte) (l >>> BYTE_ONE));
    buffer.put((byte) (l >>> BYTE_TWO));
    buffer.put((byte) (l >>> BYTE_THIRD));
    buffer.put((byte) (l >>> BYTE_FOUR));
    buffer.put((byte) (l >>> BYTE_FIVE));
    buffer.put((byte) (l >>> BYTE_SIX));
    buffer.put((byte) (l >>> BYTE_SEVEN));
  }

  /**
   * 双精度浮点数
   *
   * @param buffer
   * @param d
   */
  public static final void writeDouble(ByteBuffer buffer, double d) {
    writeLong(buffer, Double.doubleToLongBits(d));
  }

  public static final void writeLength(ByteBuffer buffer, long l) {
    if (l < BYTE_MAX_ONE) {
      buffer.put((byte) l);
    } else if (l < BYTE_MAX_TWO) {
      buffer.put((byte) 252);
      writeUB2(buffer, (int) l);
    } else if (l < BYTE_MAX_THIRD) {
      buffer.put((byte) 253);
      writeUB3(buffer, (int) l);
    } else {
      buffer.put((byte) 254);
      writeLong(buffer, l);
    }
  }

  public static final void writeWithNull(ByteBuffer buffer, byte[] src) {
    buffer.put(src);
    buffer.put((byte) 0);
  }

  /**
   * 按长度进行写入
   *
   * @param buffer
   * @param src
   */
  public static final void writeWithLength(ByteBuffer buffer, byte[] src) {
    int length = src.length;
    if (length < BYTE_MAX_ONE) {
      buffer.put((byte) length);
    } else if (length < BYTE_MAX_TWO) {
      buffer.put((byte) 252);
      writeUB2(buffer, length);
    } else if (length < BYTE_MAX_THIRD) {
      buffer.put((byte) 253);
      writeUB3(buffer, length);
    } else {
      buffer.put((byte) 254);
      writeLong(buffer, length);
    }
    buffer.put(src);
  }

  public static final void writeWithLength(ByteBuffer buffer, byte[] src, byte nullValue) {
    if (src == null) {
      buffer.put(nullValue);
    } else {
      writeWithLength(buffer, src);
    }
  }

  public static final int getLength(long length) {
    if (length < BYTE_MAX_ONE) {
      return 1;
    } else if (length < BYTE_MAX_TWO) {
      return 3;
    } else if (length < BYTE_MAX_THIRD) {
      return 4;
    } else {
      return 9;
    }
  }

  /**
   * 进行按长度的读取操作
   *
   * @param src
   * @return
   */
  public static final int getLength(byte[] src) {
    int length = src.length;
    if (length < BYTE_MAX_ONE) {
      return 1 + length;
    } else if (length < BYTE_MAX_TWO) {
      return 3 + length;
    } else if (length < BYTE_MAX_THIRD) {
      return 4 + length;
    } else {
      return 9 + length;
    }
  }
}
