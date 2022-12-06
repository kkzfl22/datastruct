package com.liujun.zfl;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author liujun
 * @since 2022/11/4
 */
public class TestFileReader {

  @Test
  public void fileReader() {

    String pathFile = "D:\\temp\\zfl\\A01_V2.0DFC.asc";
    String outPathFile = "D:\\temp\\zfl\\A01_V2.0DFC.asc.txt";

    try (FileReader fileReader = new FileReader(pathFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        FileWriter outWrite = new FileWriter(outPathFile);
        BufferedWriter bufferedWriter = new BufferedWriter(outWrite); ) {
      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        FileLineEntity data = FileLineEntity.readerParse(line);

        // 跳过不能解析的数据
        if (null == data) {
          continue;
        }

        String outLine = data.outLine();

        bufferedWriter.write(outLine);
        bufferedWriter.newLine();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
