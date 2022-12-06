package com.liujun.datastruct.advanced.bplusTree.disk.v1.disk;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bplusTree.disk.v1.treestruct.BPlusTree;
import com.liujun.datastruct.advanced.bplusTree.disk.v1.treestruct.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 磁盘操作的类
 *
 * @author liujun
 * @since 2022/11/18
 */
public class RootNodeOperator {

  /** 文件路径 */
  private final String filePath;

  /** 根节点的名称 */
  public static final String ROOT_FILE_NAME = "root.node";

  /** 文件路径 */
  private final String outFilePath;

  /** 树节点对象 */
  private BPlusTree tree;

  /** 最后的位置信息 */
  private static final int WRITE_LENGTH = 8;

  private ByteBuffer readerBuffer =
      ByteBuffer.allocateDirect(SystemInfo.getPageSize() + WRITE_LENGTH);

  public RootNodeOperator(BPlusTree tree, String filePath) {
    this.tree = tree;
    this.filePath = filePath;
    this.outFilePath = this.filePath + Symbol.PATH + ROOT_FILE_NAME;
  }

  /**
   * 文件是否存在
   *
   * @return
   */
  public boolean exists() {
    return new File(this.outFilePath).exists();
  }

  /**
   * 写入根节点的信息
   *
   * @return
   */
  public void writeRootNode() {
    Node root = tree.getRoot();

    readerBuffer.clear();
    // 将root节点持久化
    tree.getChunkStream().dataToBuffer(root);
    ByteBuffer buffer = tree.getChunkStream().getBuffer();

    // 拷贝数据
    buffer.position(0);
    readerBuffer.put(buffer);
    readerBuffer.putLong(tree.getLastWritePosition());

    // 转换读写模式
    readerBuffer.flip();

    try (FileOutputStream outputStream = new FileOutputStream(outFilePath);
        FileChannel channel = outputStream.getChannel(); ) {
      channel.write(readerBuffer, 0);

      readerBuffer.clear();
      return;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    throw new IllegalArgumentException("root node exception");
  }

  /** 读取节点的信息 */
  public void readerRootNode() {

    readerBuffer.clear();

    try (FileInputStream inputStream = new FileInputStream(outFilePath);
        FileChannel channel = inputStream.getChannel(); ) {
      channel.read(readerBuffer, 0);

      // 转换读写模式
      readerBuffer.flip();

      byte[] data = new byte[SystemInfo.getPageSize()];
      readerBuffer.get(data, 0, SystemInfo.getPageSize());

      ByteBuffer buffer = tree.getChunkStream().getBuffer();
      buffer.put(data);
      // 从写转换为读模式
      buffer.flip();

      tree.setRoot(tree.getChunkStream().readerLeafNonByBuffer());

      readerBuffer.position(SystemInfo.getPageSize());
      tree.setLastWritePosition(readerBuffer.getLong());

      readerBuffer.clear();

      return;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    throw new IllegalArgumentException("root node exception");
  }
}
