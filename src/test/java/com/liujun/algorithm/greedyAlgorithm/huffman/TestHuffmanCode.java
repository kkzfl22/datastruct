package com.liujun.algorithm.greedyAlgorithm.huffman;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/18
 */
public class TestHuffmanCode {

  @Test
  public void testCode() {

    for (int i = 0; i < 100; i++) {
      int valueRand = ThreadLocalRandom.current().nextInt(1, 50);

      StringBuilder msg = new StringBuilder();

      for (int j = 0; j < valueRand; j++) {
        msg.append((char) ThreadLocalRandom.current().nextInt(90, 200));
      }

      String src = "我我我我我我我我我我我我是是是是是是小小小小小小恩恩泽我我我我我我是是是小小小小小小恩恩泽thisis" + msg.toString();

      Map<Character, Integer> conMap = StrProc.countCharset(src);

      System.out.println(conMap);

      HuffmanCode instance = new HuffmanCode();
      Map<Character, String> huffCode = instance.getHuffManCode(conMap);
      System.out.println(huffCode);

      Integer value = Integer.parseInt("1010", 2);
      System.out.println(value);

      // Map<Character, Byte> parseTwo = instance.encodeHuf(huffCode);

      // String hufOutValue = instance.parseHuffman(src, parseTwo);
      String hufOutValue = instance.parseHuffman2(src, huffCode);

      // DataOutputStreamHuffman.OUTPUT.outtoFile(src.getBytes(StandardCharsets.UTF_8));
      // DataOutputStreamHuffman.OUTPUT.outHuffmantoFile(hufOutValue.getBytes());

      String deValue = instance.decodeHuffman(hufOutValue, instance.root);
      System.out.println("原始" + src);
      System.out.println("结果" + deValue);

      Assert.assertEquals(src, deValue);

      System.out.println(
          "--------------------------------------------------------------------------------");
    }
  }
}
