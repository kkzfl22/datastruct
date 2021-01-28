package com.liujun.datastruct.advanced.bloomfilter;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/15
 */
public class BloomFilter {

  private static final int HASHNUM = 5;

  private BitMap bitMap = new BitMap(Integer.MAX_VALUE - 1);

  private int[] getHashArrays(String url) {
    int[] hashArrays = new int[HASHNUM];
    hashArrays[0] = Math.abs(HashAlgorithms.pjwHash(url));
    hashArrays[1] = Math.abs(HashAlgorithms.apHash(url));
    hashArrays[2] = Math.abs(HashAlgorithms.bkdrHash(url));
    hashArrays[3] = Math.abs(HashAlgorithms.dekHash(url));
    hashArrays[4] = Math.abs(HashAlgorithms.jsHash(url));
    return hashArrays;
  }

  private boolean exists(int[] hashArrays) {
    boolean exists = false;

    for (int i = 0; i < HASHNUM; i++) {
      boolean rsp = bitMap.get(hashArrays[i]);
      if (rsp) {
        exists = true;
        break;
      }
    }

    return exists;
  }

  public boolean exists(String key) {

    int[] hashArrays = this.getHashArrays(key);

    return exists(hashArrays);
  }

  public void putUrl(String url) {

    int[] hashArrays = this.getHashArrays(url);

    boolean exists = this.exists(hashArrays);

    if (!exists) {
      for (int i = 0; i < HASHNUM; i++) {
        bitMap.set(hashArrays[i]);
      }
    }
  }
}
