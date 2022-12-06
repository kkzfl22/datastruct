package com.liujun.datastruct.advanced.bplusTree.disk.v2.disk;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.treestruct.Node;

import java.io.File;

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

  public RootNodeOperator(String filePath) {
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
   * 读取根节点
   *
   * @return
   */
  public Node readerRootNode() {

    throw new IllegalArgumentException("root node exception");
  }

  /**
   * 将数据保存至
   *
   * @param node
   */
  public void writeRootNode(Node node) {}
}
