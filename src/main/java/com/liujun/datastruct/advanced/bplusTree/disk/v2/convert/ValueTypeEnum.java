package com.liujun.datastruct.advanced.bplusTree.disk.v2.convert;

import lombok.Getter;
import lombok.ToString;

/**
 * 数据类型值标识
 *
 * @author liujun
 * @since 2022/11/21
 */
@Getter
@ToString
public enum ValueTypeEnum {

  /** 字节信息 */
  BYTE(
      "byte",
      Byte.class,
      1,
      ValueToBufferParse.INSTANCE::putByteToBuffer,
      BufferToValueParse.INSTANCE::bufferToByte,
      1),

  /** 短整形 */
  SHORT(
      "short",
      Short.class,
      2,
      ValueToBufferParse.INSTANCE::putShortToBuffer,
      BufferToValueParse.INSTANCE::bufferToShort,
      2),

  /** 字符信息 */
  CHAR(
      "char",
      Character.class,
      3,
      ValueToBufferParse.INSTANCE::putCharToBuffer,
      BufferToValueParse.INSTANCE::bufferToChar,
      3),

  /** 类型信息 */
  INT(
      "int",
      Integer.class,
      4,
      ValueToBufferParse.INSTANCE::putIntegerToBuffer,
      BufferToValueParse.INSTANCE::bufferToInteger,
      4),

  /** 长整形 */
  LONG(
      "long",
      Long.class,
      5,
      ValueToBufferParse.INSTANCE::putLongToBuffer,
      BufferToValueParse.INSTANCE::bufferToLong,
      8),

  /** 浮点数 */
  FLOAT(
      "flat",
      Float.class,
      6,
      ValueToBufferParse.INSTANCE::putFloatToBuffer,
      BufferToValueParse.INSTANCE::bufferToFloat,
      4),

  /** 双精度浮点数 */
  DOUBLE(
      "double",
      Double.class,
      7,
      ValueToBufferParse.INSTANCE::putDoubleToBuffer,
      BufferToValueParse.INSTANCE::bufferToDouble,
      8),

  /** 字符信息 */
  STRING(
      "string",
      String.class,
      8,
      ValueToBufferParse.INSTANCE::putStringToBuffer,
      BufferToValueParse.INSTANCE::bufferToString,
      -1),
  ;

  /** 类型描述符 */
  private String type;

  /** java中的类型信息 */
  private Class javaClass;

  /** 类型信息 */
  private Integer typeFlag;

  /** 转换方法 */
  private ValueToBufferParse.valueParse valueToBuffer;

  /** 缓冲区数据转换为java对象方法 */
  private BufferToValueParse.BufferToValue bufferToValue;

  /** 占用字节的长度,-1，未知 */
  private int byteLength;

  ValueTypeEnum(
      String type,
      Class javaClass,
      Integer typeFlag,
      ValueToBufferParse.valueParse valueToBuffer,
      BufferToValueParse.BufferToValue bufferToValue,
      int byteLength) {
    this.type = type;
    this.javaClass = javaClass;
    this.typeFlag = typeFlag;
    this.valueToBuffer = valueToBuffer;
    this.bufferToValue = bufferToValue;
    this.byteLength = byteLength;
  }

  public static ValueTypeEnum getType(int typeFlag) {
    if (typeFlag < 0 || typeFlag > values().length) {
      return null;
    }

    return values()[typeFlag - 1];
  }
}
