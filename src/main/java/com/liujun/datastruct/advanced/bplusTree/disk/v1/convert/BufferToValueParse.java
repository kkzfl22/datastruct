package com.liujun.datastruct.advanced.bplusTree.disk.v1.convert;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 将缓冲区中的数据读取为java类型
 *
 * @author liujun
 * @since 2022/11/21
 */
public class BufferToValueParse {

  public static final BufferToValueParse INSTANCE = new BufferToValueParse();

  @FunctionalInterface
  public interface BufferToValue {
    Object bufferToValue(ByteBuffer buffer);
  }

  /**
   * 将buffer读取出一个字节
   *
   * @param buffer
   * @param buffer
   */
  public Byte bufferToByte(ByteBuffer buffer) {
    return buffer.get();
  }

  /**
   * 将buffer读取出short
   *
   * @param buffer
   * @param buffer
   */
  public Short bufferToShort(ByteBuffer buffer) {
    return buffer.getShort();
  }

  /**
   * 将buffer读取出char
   *
   * @param buffer
   * @param buffer
   */
  public Character bufferToChar(ByteBuffer buffer) {
    return buffer.getChar();
  }

  /**
   * 将buffer读取出Integer
   *
   * @param buffer
   * @param buffer
   */
  public Integer bufferToInteger(ByteBuffer buffer) {
    return buffer.getInt();
  }

  /**
   * 将buffer读取出Long
   *
   * @param buffer
   * @param buffer
   */
  public Long bufferToLong(ByteBuffer buffer) {
    return buffer.getLong();
  }

  /**
   * 将buffer读取出Long
   *
   * @param buffer
   * @param buffer
   */
  public Float bufferToFloat(ByteBuffer buffer) {
    return buffer.getFloat();
  }

  /**
   * 将buffer读取出Long
   *
   * @param buffer
   * @param buffer
   */
  public Double bufferToDouble(ByteBuffer buffer) {
    return buffer.getDouble();
  }

  /**
   * 将buffer读取出Long
   *
   * @param buffer
   * @param buffer
   */
  public String bufferToString(ByteBuffer buffer) {
    int getLength = buffer.getInt();
    int position = buffer.position();
    byte[] strBytes = new byte[getLength];
    buffer.get(strBytes, 0, getLength);

    return new String(strBytes, StandardCharsets.UTF_8);
  }
}
