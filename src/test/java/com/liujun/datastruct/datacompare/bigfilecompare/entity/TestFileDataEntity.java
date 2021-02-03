package com.liujun.datastruct.datacompare.bigfilecompare.entity;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试相关的类型转换
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestFileDataEntity {

  @Test
  public void testParse() {
    FileDataEntity entity = FileDataEntity.lineToEntity("68373724,25800023356300,0,1,2,3,4");
    Assert.assertEquals("68373724,25800023356300,0,1,2,3,4", entity.entityToLine());
  }
}
