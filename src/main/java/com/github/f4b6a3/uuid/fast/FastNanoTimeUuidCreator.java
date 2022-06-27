
package com.github.f4b6a3.uuid.fast;

import com.github.f4b6a3.uuid.utils.NumUtils;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.codec.binary.Hex;

/**
 * System fast uuid creator.
 *
 * @author sfuser
 */
public class FastNanoTimeUuidCreator {

  static class UuidWrapper {

    private UuidWrapper(long msb, long lsb) {
      this.msb = msb;
      this.lsb = lsb;
    }

    private final long msb;
    private final long lsb;
  }

  private final int hostId;
  private final long startNanoTime;

  private final long baseNanoTime;

  private int sequence = 0;

  private ReentrantLock lock = new ReentrantLock();
  private long lastNanoTime;

  public FastNanoTimeUuidCreator() {
    this.startNanoTime = System.nanoTime();
    Instant now = Instant.now();

    this.baseNanoTime = now.getEpochSecond() * 1000_000_000L + now.getNano();
    this.hostId = 0x7f6e5d4c;
  }

  private UuidWrapper generate() {
    lock.lock();
    try {
      long currentNanoTime = baseNanoTime + (System.nanoTime() - startNanoTime);

      if (this.lastNanoTime == currentNanoTime) {
        sequence++;
      } else {
        sequence = 0;
      }

      UuidWrapper result = new UuidWrapper(currentNanoTime, hostId << 32 + sequence);

      this.lastNanoTime = currentNanoTime;

      return result;
    } finally {
      lock.unlock();
    }
  }

  public byte[] generateBytes() {
    UuidWrapper generated = generate();

    byte[] result = new byte[16];

    NumUtils.serializeLongToBytes(generated.msb, result, 0);
    NumUtils.serializeLongToBytes(generated.lsb, result, 8);

    return result;
  }

  public String generateHex() {
//    generate();

//    char[] result = new char[32];
//
//    NumUtils.serializeLongToHexString(msb, result, 0);
//    NumUtils.serializeLongToHexString(lsb, result, 16);
//
//    return new String(result);

    return Hex.encodeHexString(generateBytes());
  }

  public UUID generateUuid() {
    UuidWrapper generated = generate();

    return new UUID(generated.msb, generated.lsb);
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
