package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

/**
 * 使用归并排序对1Tb的数据进行排序操作
 *
 * <p>1,归并排序的思想是将文件切分成足够的小文件，
 *
 * <p>2,然后将小文件进行排序
 *
 * <p>3,最后将有充文件进行合并
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/23
 */
public class MergeSort {

    /**
     * 进行大文件的排序操作
     *
     * @param srcPath
     * @param outPath
     */
    public void sort(String srcPath, String outPath) {
        //1, 按指定的大小进行文件的切分操作(但需要保证完整的行，所以非完整的指定大小，会比指定文件略小)
        //2， 将所有已经切分的文件进行排序操作
        //3， 针对已经排序完成的所有小文件合并为一个有序大文件
    }



}
