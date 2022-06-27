package com.github.f4b6a3.uuid.fast;

import com.github.f4b6a3.uuid.utils.NumUtils;
import java.time.Instant;

/**
 * System fast uuid creator.
 *
 * @author sfuser
 */
public class FastInstantUuidCreator {

  private Instant current = Instant.now();

  private int sequence = 0;

  private long msb = 0L;

  private long lsb = 0L;

  private synchronized void generate() {
    Instant now = Instant.now();

    if (current.equals(now)) {
      sequence++;
    } else {
      current = now;
      sequence = 0;
    }

    this.msb = current.getEpochSecond();
    this.lsb = current.getNano() << 32 | sequence;
  }

  public byte[] generateBytes() {
    generate();

    byte[] result = new byte[16];

    NumUtils.serializeLongToBytes(msb, result, 0);
    NumUtils.serializeLongToBytes(lsb, result, 8);

    return result;
  }

  public String generateHex() {
    generate();

    return Long.toHexString(msb) + Long.toHexString(lsb);
  }

  private static final char[] HEX_VALUES = "0123456789ABCDEF".toCharArray();

  public static String convertBytesToHex(byte[] bytes) {
    int len = bytes.length;

    // storing the hexadecimal values
    char[] result = new char[len * 2];

    // using  byte operation converting byte
    // array to hexadecimal value
    for (int i = 0; i < len; i++) {
      int v = bytes[i] & 0xFF;
      result[i * 2] = HEX_VALUES[v >>> 4];
      result[i * 2 + 1] = HEX_VALUES[v & 0x0F];
    }

    return new String(result);
  }

}
