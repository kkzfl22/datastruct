package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.entity;

import java.util.List;

/**
 * 文件对比的实体信息
 *
 * @author liujun
 * @version 0.0.1
 */
public class CompareInputEntity {

  /** 源文件 */
  private List<String> srcFile;

  /** 与源文件比较的目标文件 */
  private List<String> targetFile;

  public CompareInputEntity(List<String> srcFile, List<String> targetFile) {
    this.srcFile = srcFile;
    this.targetFile = targetFile;
  }

  public List<String> getSrcFile() {
    return srcFile;
  }

  public List<String> getTargetFile() {
    return targetFile;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CompareInputEntity{");
    sb.append("srcFile=").append(srcFile);
    sb.append(", targetFile=").append(targetFile);
    sb.append('}');
    return sb.toString();
  }
}
