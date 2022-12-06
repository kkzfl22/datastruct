package com.liujun.datastruct.advanced.bplusTree.disk.v1.convert;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 数据类型值标识
 *
 * @author liujun
 * @since 2022/11/21
 */
public class ValueToBufferParse {

  public static final ValueToBufferParse INSTANCE = new ValueToBufferParse();

  @FunctionalInterface
  public interface valueParse {
    <K> void valueToBuffer(Object value, ByteBuffer buffer);
  }

  /**
   * 将int的值放入至buffer中
   *
   * @param value
   * @param buffer
   */
  public void putByteToBuffer(Object value, ByteBuffer buffer) {
    Byte byteValue = (Byte) value;
    buffer.put(byteValue);
  }

  /**
   * 将short的值放入至buffer中
   *
   * @param value
   * @param buffer
   */
  public void putShortToBuffer(Object value, ByteBuffer buffer) {
    Short intValue = (Short) value;
    buffer.putShort(intValue);
  }

  /**
   * 将char的值放入至buffer中
   *
   * @param value
   * @param buffer
   */
  public void putCharToBuffer(Object value, ByteBuffer buffer) {
    Character intValue = (Character) value;
    buffer.putChar(intValue);
  }

  /**
   * 将int的值放入至buffer中
   *
   * @param value
   * @param buffer
   */
  public void putIntegerToBuffer(Object value, ByteBuffer buffer) {
    Integer intValue = (Integer) value;
    buffer.putInt(intValue);
  }

  /**
   * 将int的值放入至buffer中
   *
   * @param value
   * @param buffer
   */
  public void putLongToBuffer(Object value, ByteBuffer buffer) {
    Long intValue = (Long) value;
    buffer.putLong(intValue);
  }

  /**
   * 将float的值放入至buffer中
   *
   * @param value
   * @param buffer
   */
  public void putFloatToBuffer(Object value, ByteBuffer buffer) {
    Float intValue = (Float) value;
    buffer.putFloat(intValue);
  }

  /**
   * 将float的值放入至buffer中
   *
   * @param value
   * @param buffer
   */
  public void putDoubleToBuffer(Object value, ByteBuffer buffer) {
    Double intValue = (Double) value;
    buffer.putDouble(intValue);
  }

  /**
   * 将String类型放入
   *
   * @param value 值信息
   * @param buffer 缓冲区
   */
  public void putStringToBuffer(Object value, ByteBuffer buffer) {
    String dataStr = String.valueOf(value);
    byte[] dataValue = dataStr.getBytes(StandardCharsets.UTF_8);
    // 先写入长度，再写入值
    buffer.putInt(dataValue.length);
    buffer.put(dataValue);
  }
}
