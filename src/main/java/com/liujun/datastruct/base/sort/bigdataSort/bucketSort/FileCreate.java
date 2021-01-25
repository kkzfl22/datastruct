package com.liujun.datastruct.base.sort.bigdataSort.bucketSort;

import com.liujun.datastruct.utils.IOUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class FileCreate {

  public static final FileCreate INSTANCE = new FileCreate();

  public static final ZoneOffset ZONE8 = ZoneOffset.of("+8");

  private static final Random random = new Random();

  public void writeFileNum(String filePath, int num) {
    FileWriter fileWriter = null;
    BufferedWriter bufferedWriter = null;

    try {
      fileWriter = new FileWriter(filePath, true);
      bufferedWriter = new BufferedWriter(fileWriter);

      int index = 0;

      while (index <= num) {

        LocalDateTime localDateTime =
            LocalDateTime.of(2018, 10, 29, 16, random.nextInt(59), random.nextInt(59));

        String line =
            localDateTime.toInstant(FileCreate.ZONE8).toEpochMilli()
                + " "
                + System.currentTimeMillis();

        bufferedWriter.write(line);
        bufferedWriter.newLine();

        index++;
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.close(bufferedWriter);
      IOUtils.close(fileWriter);
    }
  }

  public void writeMoreFile(int num) {

    for (int i = 0; i < num; i++) {
      int rowNUm = 100000;
      String line =
          "D:\\java\\test\\datastruct\\sort\\bigdata\\out_"
              + rowNUm
              + "_"
              + System.currentTimeMillis()
              + ".log";

      FileCreate.INSTANCE.writeFileNum(line, rowNUm);
    }
  }

  public static void main(String[] args) {
    LocalDateTime localDateTime =
        LocalDateTime.of(2018, 10, 29, 16, random.nextInt(59), random.nextInt(59));
    System.out.println(localDateTime.toString());

    FileCreate.INSTANCE.writeMoreFile(5);
  }
}
