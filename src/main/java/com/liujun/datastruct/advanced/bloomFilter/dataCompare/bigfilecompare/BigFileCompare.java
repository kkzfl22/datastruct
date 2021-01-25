package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare;

import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.DataCompare;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.FileDataCompareRsp;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.ManyFileReader;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.updfile.BigFileSort;

import java.util.List;

/**
 * 大型文件比较
 *
 * @author liujun
 * @version 0.0.1
 */
public class BigFileCompare<V> {

  private static final int READ_SIZE = 128;

  /**
   * 执行源文件的比较
   *
   * @param inputEntity 输入对比的相关信息
   * @param compareKey 对比相关的函数
   * @return 对比结果 true 对比成功 false 对比失败
   */
  public boolean fileCompare(
      BigFileCompareInputEntity inputEntity,
      BigCompareKeyInf compareKey,
      DataParseInf<V> dataParse) {
    if (inputEntity == null || compareKey == null) {
      return false;
    }

    // 1,获得对比的原始文件
    ManyFileReader readSrc = new ManyFileReader(inputEntity.getSrcPath());
    ManyFileReader readTarget = new ManyFileReader(inputEntity.getTargetPath());
    FileDataCompareRsp output = new FileDataCompareRsp(inputEntity.getCompareOutPath());
    // 执行数据对比的操作
    DataCompare compare = new DataCompare(compareKey, output, dataParse);

    // 读取源文件，加载到对比
    putSrcData(readSrc, compare);
    // 读取目标数据，加载对比
    putTargetData(readTarget, compare);

    try {
      // 执行数据的对比操作
      putSrcDataCompare(readSrc, compare);

      putTargetDataCompare(readTarget, compare);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // 执行相关的对比输出的关闭操作
      output.closeWrite();
    }

    // 完成后执行重命名操作
    output.rename();

    // 2,当对比完成后，需要对数据做进一步的处理，由于现在数据是无序的，让两个修改的文件变得有序的。
    // 修改之前的文件排序
    String beforeSortPath = fileSort(output.getUpdBeforeFilePath(), dataParse);
    // 修改之后的文件排序
    String afterSortPath = fileSort(output.getUpdAfterFilePath(), dataParse);

    // 进行最后的文件输出


    return true;
  }

  /**
   * 文件排序操作
   *
   * @param fileSortPath 文件路径
   * @param dataParse 对比函数
   */
  private String fileSort(String fileSortPath, DataParseInf<V> dataParse) {
    BigFileSort fileSort = new BigFileSort(fileSortPath, dataParse);
    // 执行大文件排序操作
    return fileSort.bigFileSort();
  }

  /**
   * 将原始数据放入到对比相关文件中
   *
   * @param readSrc 读取的原始文件中
   * @param compare 对比处理逻辑
   */
  private void putSrcData(ManyFileReader readSrc, DataCompare compare) {
    readSrc.open();
    try {
      while (true) {
        List<String> dataList = readSrc.readLineMany(READ_SIZE);
        if (dataList.isEmpty()) {
          break;
        }
        compare.putSrcList(dataList);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      readSrc.close();
    }
  }

  /**
   * 将目标数据装载到布隆过滤器中
   *
   * @param readTarget
   * @param compare
   */
  private void putTargetData(ManyFileReader readTarget, DataCompare compare) {
    readTarget.open();
    try {
      while (true) {
        List<String> dataList = readTarget.readLineMany(READ_SIZE);
        if (dataList.isEmpty()) {
          break;
        }
        compare.putTargetList(dataList);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      readTarget.close();
    }
  }

  /**
   * 将原始数据放入到对比相关文件中
   *
   * @param readSrc 读取的原始文件中
   * @param compare 对比处理逻辑
   */
  private void putSrcDataCompare(ManyFileReader readSrc, DataCompare compare) {

    // 重新加载操作
    readSrc.reload();

    readSrc.open();
    try {
      while (true) {
        List<String> dataList = readSrc.readLineMany(READ_SIZE);
        if (dataList.isEmpty()) {
          break;
        }
        compare.compareSrc(dataList);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      readSrc.close();
    }
  }

  /**
   * 将目标数据装载到布隆过滤器中
   *
   * @param readTarget
   * @param compare
   */
  private void putTargetDataCompare(ManyFileReader readTarget, DataCompare compare) {
    // 重新加载操作
    readTarget.reload();
    readTarget.open();
    try {
      while (true) {
        List<String> dataList = readTarget.readLineMany(READ_SIZE);
        if (dataList.isEmpty()) {
          break;
        }
        compare.compareTarget(dataList);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      readTarget.close();
    }
  }
}
