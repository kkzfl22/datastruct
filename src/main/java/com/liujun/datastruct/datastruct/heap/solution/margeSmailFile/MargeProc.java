package com.liujun.datastruct.datastruct.heap.solution.margeSmailFile;

import com.liujun.datastruct.datastruct.heap.solution.margeSmailFile.pojo.FileMargeBusi;
import com.liujun.datastruct.datastruct.heap.solution.margeSmailFile.pojo.ByteHeadInfo;
import com.liujun.datastruct.datastruct.heap.solution.margeSmailFile.pojo.OutFileBusi;

import java.io.File;
import java.util.PriorityQueue;

/**
 * 进行合并有序文件的测试
 *
 * <p>使用多种测试安例对程序进行测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/29
 */
public class MargeProc {

  /** 实例信息 */
  public static final MargeProc INSTANCE = new MargeProc();

  public FileMargeBusi[] getMargeBean(String path) {
    FileMargeBusi[] result = null;

    File file = new File(path);

    if (file.exists()) {
      File[] fileChilds = file.listFiles();
      result = new FileMargeBusi[fileChilds.length];

      for (int i = 0; i < fileChilds.length; i++) {
        result[i] = FileMargeProc.INSTANCE.openFile(fileChilds[i]);
      }
    }

    return result;
  }

  public void reader(FileMargeBusi[] margeFile, String outPath) {
    // 1,每个节点读取一个缓冲区的数据
    for (int i = 0; i < margeFile.length; i++) {
      FileMargeProc.INSTANCE.readFile(margeFile[i]);
    }

    // 开始合并操作
    // 1,构建小顶堆操作`
    PriorityQueue<ByteHeadInfo> smallHeap =
        new PriorityQueue<>(
            margeFile.length,
            (o1, o2) -> {
              if (o1.getValue() > o2.getValue()) {
                return 1;
              } else if (o1.getValue() < o2.getValue()) {
                return -1;
              }

              return 0;
            });

    // 遍历所有文件，都取一个放入到小顶堆中
    for (int i = 0; i < margeFile.length; i++) {
      if (margeFile[i].getBufferReadIndex() <= margeFile[i].getFileReadIndex()) {
        smallHeap.offer(
            new ByteHeadInfo(margeFile[i].getBuffer()[margeFile[i].getBufferReadIndex()], i));
        // 将索引向前推进一步
        margeFile[i].setBufferReadIndex(margeFile[i].getBufferReadIndex() + 1);
      }
    }

    OutFileBusi outFile = FileOutProcess.INSTANCE.openFile(outPath);

    ByteHeadInfo currByte;

    // 如果当前文件未结束，则继续遍历
    while (!checkFinish(margeFile) || !smallHeap.isEmpty()) {
      currByte = smallHeap.poll();

      // 如果当前缓冲区未满，先写入缓冲区
      if (outFile.getOutIndex() < outFile.getMaxIndex()) {
        outFile.getBuffer()[outFile.getOutIndex()] = currByte.getValue();
        outFile.setOutIndex(outFile.getOutIndex() + 1);
      }

      // 当缓冲区满了，写入文件中
      if (outFile.getOutIndex() == outFile.getMaxIndex()) {
        FileOutProcess.INSTANCE.fileWrite(outFile);
      }

      // 将当前文件的下一个数据加入到当前小顶堆中
      this.readFileNextByte(margeFile[currByte.getIndex()], currByte.getIndex(), smallHeap);
    }

    FileOutProcess.INSTANCE.fileWrite(outFile);

    // 做最后的文件关闭操作
    FileMargeProc.INSTANCE.close(margeFile);
    // 关闭输出文件
    FileOutProcess.INSTANCE.close(outFile);
  }

  /**
   * 读取下一个字节信息
   *
   * @param margeFile 合并文件信息
   * @param smallHeap 小顶堆信息
   */
  private void readFileNextByte(
      FileMargeBusi margeFile, int index, PriorityQueue<ByteHeadInfo> smallHeap) {
    // 如果当前中的缓冲区还未读取完，从先从缓冲区中读取
    if (margeFile.getBufferReadIndex() < margeFile.getFileReadIndex()) {
      smallHeap.offer(
          new ByteHeadInfo(margeFile.getBuffer()[margeFile.getBufferReadIndex()], index));
      // 将索引向前推进一步
      margeFile.setBufferReadIndex(margeFile.getBufferReadIndex() + 1);
    }
    // 如果已经读取完成,则写入磁盘文件中
    if (margeFile.getBufferReadIndex() == margeFile.getFileReadIndex()) {
      FileMargeProc.INSTANCE.readFile(margeFile);
    }
  }

  /**
   * 检查文件是否已经全部结束
   *
   * @param marge
   * @return
   */
  private boolean checkFinish(FileMargeBusi[] marge) {
    for (int i = 0; i < marge.length; i++) {
      if (!marge[i].isFinish()) {
        return false;
      }
    }

    return true;
  }
}
