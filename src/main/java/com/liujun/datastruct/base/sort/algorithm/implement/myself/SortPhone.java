package com.liujun.datastruct.base.sort.algorithm.implement.myself;

/**
 * 进行手机号码的排序，基于基数排序
 *
 * <p>首先分隔出独立的位，按位使用稳定的排序算法，
 *
 * <p>经过11次排序后就是有序的数据了
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/12/24
 */
public class SortPhone {

  class PhoneData {
    /** 位上的数据 */
    private int bitData;
    /** 手机号 */
    private String phone;
  }

  public void sortPhone(String[] phone) {
    if (null == phone || phone.length == 0) {
      return;
    }

    PhoneData[] phoneSortArray = new PhoneData[phone.length];
    // 首个位的数据设置
    for (int i = 0; i < phone.length; i++) {
      phoneSortArray[i] = this.firstBitData(phone[i]);
    }
    this.sort(phoneSortArray);
    int dataLength = phone[0].length();
    for (int i = dataLength - 1; i > 0; i--) {
      for (int j = 0; j < phone.length; j++) {
        // 针对每个项进行位的值的设置操作
        this.setBitData(phoneSortArray[j], i);
      }
      // 再次使用插入排序进行排序
      this.sort(phoneSortArray);
    }

    for (int i = 0; i < phoneSortArray.length; i++) {
      // 进行手机号的排序操作
      System.out.println(phoneSortArray[i].phone);
    }
  }

  /**
   * 使用稳定的插入排序算法对数据进行排序操作
   *
   * @param phoneSortArray 待排序的数组对象信息
   */
  private void sort(PhoneData[] phoneSortArray) {
    PhoneData dataItem = null;
    // 然后再使用稳定的插入排序算法对数据进行排序
    for (int i = 1; i < phoneSortArray.length; i++) {
      dataItem = phoneSortArray[i];
      int insertIndex = i;
      // 查找插入位置
      for (int j = 0; j < i; j++) {
        if (phoneSortArray[j].bitData > phoneSortArray[i].bitData) {
          insertIndex = j;
          break;
        }
      }
      // 进行数据的移动,需从后向前移动，否则会将数据覆盖
      for (int j = i; j > insertIndex; j--) {
        phoneSortArray[j] = phoneSortArray[j - 1];
      }
      // 将数据插入
      phoneSortArray[insertIndex] = dataItem;
    }
  }

  /**
   * 进行首次资源的初始化
   *
   * @param data 比较数据的原始内容
   * @return
   */
  private PhoneData firstBitData(String data) {
    PhoneData phoneSortData = new PhoneData();
    phoneSortData.bitData = Integer.parseInt(data.substring(data.length() - 1));
    phoneSortData.phone = data;
    return phoneSortData;
  }

  private void setBitData(PhoneData phoneData, int bitIndex) {
    String dataItem = phoneData.phone.substring(bitIndex - 1, bitIndex);
    phoneData.bitData = Integer.parseInt(dataItem);
  }
}
