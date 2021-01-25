package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.entity;

/**
 * @author liujun
 * @version 0.0.1
 */
public class BigFileCompareInputEntity<V> {

  /** 源文件路径 */
  private String srcPath;

  /** 目标文件路径 */
  private String targetPath;

  /** 对比结束的路径 */
  private String compareOutPath;

  public BigFileCompareInputEntity(String srcPath, String targetPath, String compareOutPath) {
    this.srcPath = srcPath;
    this.targetPath = targetPath;
    this.compareOutPath = compareOutPath;
  }

  public String getSrcPath() {
    return srcPath;
  }

  public void setSrcPath(String srcPath) {
    this.srcPath = srcPath;
  }

  public String getTargetPath() {
    return targetPath;
  }

  public void setTargetPath(String targetPath) {
    this.targetPath = targetPath;
  }

  public String getCompareOutPath() {
    return compareOutPath;
  }

  public void setCompareOutPath(String compareOutPath) {
    this.compareOutPath = compareOutPath;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BigFIleCompareInputEntity{");
    sb.append("srcPath='").append(srcPath).append('\'');
    sb.append(", targetPath='").append(targetPath).append('\'');
    sb.append(", compareOutPath='").append(compareOutPath).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
