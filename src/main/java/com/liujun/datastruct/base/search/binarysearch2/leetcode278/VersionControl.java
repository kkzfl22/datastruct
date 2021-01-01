package com.liujun.datastruct.base.search.binarysearch2.leetcode278;

/**
 * @author liujun
 * @version 0.0.1
 */
public abstract class VersionControl {

  private int firstBedVersion;

  public void setFirstBedVersion(int firstBedVersion) {
    this.firstBedVersion = firstBedVersion;
  }

  public boolean isBadVersion(int nums) {

    if (firstBedVersion <= nums) {
      return true;
    }

    return false;
  }

  public abstract int firstBadVersion(int n);
}
