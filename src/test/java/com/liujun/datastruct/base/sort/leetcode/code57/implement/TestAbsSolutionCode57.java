package com.liujun.datastruct.base.sort.leetcode.code57.implement;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试插入区间
 *
 * @author liujun
 * @version 0.0.1
 * @date 2020/01/07
 */
public abstract class TestAbsSolutionCode57 {

  class TestData {
    private int[][] data;

    private int[] insertdata;

    private int[][] rsp;

    public TestData(int[][] data, int[] insertdata, int[][] rsp) {
      this.data = data;
      this.insertdata = insertdata;
      this.rsp = rsp;
    }
  }

  public abstract SolutionCode57Interface getInstance();

  @Test
  public void testInsertMerge() {
    SolutionCode57Interface instance = this.getInstance();

    List<TestData> outList = getTestData();

    for (TestData outData : outList) {
      int[][] dataOut = instance.insert(outData.data, outData.insertdata);

      for (int[] dataItem : dataOut) {
        System.out.println(Arrays.toString(dataItem));
      }

      System.out.println("-------------------------------");

      Assert.assertThat(dataOut, Matchers.arrayContainingInAnyOrder(outData.rsp));
    }
  }

  public List<TestData> getTestData() {
    List<TestData> dataList = new ArrayList<>();

    int[][] data9 = new int[1][2];
    data9[0] = new int[] {1, 5};
    int[] insert9 = new int[] {0, 0};
    int[][] rsp9 = new int[2][2];
    rsp9[0] = new int[] {0, 0};
    rsp9[1] = new int[] {1, 5};
    dataList.add(new TestData(data9, insert9, rsp9));

    int[][] data8 = new int[5][2];
    data8[0] = new int[] {1, 2};
    data8[1] = new int[] {3, 5};
    data8[2] = new int[] {6, 7};
    data8[3] = new int[] {8, 10};
    data8[4] = new int[] {12, 16};
    int[] insert8 = new int[] {4, 8};
    int[][] rsp8 = new int[3][2];
    rsp8[0] = new int[] {1, 2};
    rsp8[1] = new int[] {3, 10};
    rsp8[2] = new int[] {12, 16};
    dataList.add(new TestData(data8, insert8, rsp8));

    int[][] data7 = new int[1][2];
    data7[0] = new int[] {1, 5};
    int[] insert7 = new int[] {0, 3};
    int[][] rsp7 = new int[1][2];
    rsp7[0] = new int[] {0, 5};
    dataList.add(new TestData(data7, insert7, rsp7));

    int[][] data6 = new int[1][2];
    data6[0] = new int[] {1, 5};
    int[] insert6 = new int[] {6, 8};
    int[][] rsp6 = new int[2][2];
    rsp6[0] = new int[] {1, 5};
    rsp6[1] = new int[] {6, 8};
    dataList.add(new TestData(data6, insert6, rsp6));

    int[][] data5 = new int[2][2];
    data5[0] = new int[] {1, 3};
    data5[1] = new int[] {6, 9};
    int[] insert5 = new int[] {2, 5};
    int[][] rsp5 = new int[2][2];
    rsp5[0] = new int[] {1, 5};
    rsp5[1] = new int[] {6, 9};
    dataList.add(new TestData(data5, insert5, rsp5));

    int[][] data = new int[2][2];
    data[0] = new int[] {1, 3};
    data[1] = new int[] {6, 9};
    int[] insert = new int[] {2, 5};
    int[][] rsp = new int[2][2];
    rsp[0] = new int[] {1, 5};
    rsp[1] = new int[] {6, 9};
    dataList.add(new TestData(data, insert, rsp));

    // [1,2],[3,5],[6,7],[8,10],[12,16]
    int[][] data2 = new int[5][2];
    data2[0] = new int[] {1, 2};
    data2[1] = new int[] {3, 5};
    data2[2] = new int[] {6, 7};
    data2[3] = new int[] {8, 10};
    data2[4] = new int[] {12, 16};
    int[] insert2 = new int[] {4, 8};
    int[][] rsp2 = new int[3][2];
    rsp2[0] = new int[] {1, 2};
    rsp2[1] = new int[] {3, 10};
    rsp2[2] = new int[] {12, 16};
    dataList.add(new TestData(data2, insert2, rsp2));

    int[][] data3 = new int[0][2];
    int[] insert3 = new int[] {5, 7};
    int[][] rsp3 = new int[1][2];
    rsp3[0] = new int[] {5, 7};
    dataList.add(new TestData(data3, insert3, rsp3));

    int[][] data4 = new int[1][2];
    data4[0] = new int[] {1, 5};
    int[] insert4 = new int[] {2, 7};
    int[][] rsp4 = new int[1][2];
    rsp4[0] = new int[] {1, 7};
    dataList.add(new TestData(data4, insert4, rsp4));

    return dataList;
  }
}
