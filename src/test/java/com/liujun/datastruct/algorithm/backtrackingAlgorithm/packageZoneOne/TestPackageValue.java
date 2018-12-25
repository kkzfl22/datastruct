package com.liujun.datastruct.algorithm.backtrackingAlgorithm.packageZoneOne;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 使用回塑来计算总价值
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/25
 */
public class TestPackageValue {

  @Test
  public void testMaxPkgValue() {
    PkgValue[] pkgs = new PkgValue[5];

    pkgs[0] = new PkgValue("苹果", 10, 100);
    pkgs[1] = new PkgValue("香蕉", 20, 80);
    pkgs[2] = new PkgValue("香蕉", 30, 120);
    pkgs[3] = new PkgValue("菠萝", 25, 125);
    pkgs[4] = new PkgValue("橙子", 15, 160);

    PackageValue instance = new PackageValue();

    instance.countMaxPkg(0, 0, 0, pkgs, 5, 85);
    System.out.println("最大总价值为 ：" + instance.maxValue);
    System.out.println("最大总重量为 ：" + instance.sumMaxWeight);
  }
}
