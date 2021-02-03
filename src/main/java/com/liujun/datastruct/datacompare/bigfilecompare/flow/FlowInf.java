package com.liujun.datastruct.datacompare.bigfilecompare.flow;

/**
 * 运行流程的任务掊口
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/09/11
 */
public interface FlowInf {

  /**
   * 流程执行
   *
   * @param context 流程的上下文对象，运行流程所需的对象都在此对象中
   * @return true countine,false return
   */
  boolean invokeFlow(ContextContainer context);
}
