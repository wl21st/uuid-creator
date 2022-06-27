package com.github.f4b6a3.uuid.utils;

public class NumUtils {

  public static void serializeLongToBytes(long value, byte[] bytes, int beginOffset) {
    // MSB
    for (int i = 0, offset = beginOffset + 7; i < 8; i++, offset--) {
      bytes[offset] = (byte) (value >> i * 8);
    }
  }

  public static void serializeIntToBytes(int value, byte[] bytes, int beginOffset) {
    // MSB
    for (int i = 0, offset = beginOffset + 4; i < 4; i++, offset--) {
      bytes[offset] = (byte) (value >> i * 8);
    }
  }

}
