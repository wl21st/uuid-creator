package com.github.f4b6a3.uuid.fast;

import com.github.f4b6a3.uuid.UuidCreator;
import java.util.UUID;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class FastSysTest {

  @Test
  public void testTimedOrderedValues() {
    for (int i = 0; i < 10; i++) {
      UUID uuid = UuidCreator.getTimeOrdered();
      System.out.printf("%d=%s%n", i, uuid.toString());
    }
  }

  @Test
  public void testFastInstantImpl() {
    FastInstantUuidCreator creator = new FastInstantUuidCreator();

    for (int i = 0; i < 10; i++) {
      byte[] uuidBytes = creator.generateBytes();
      System.out.printf("%d=%s%n", i, Hex.encodeHexString(uuidBytes));
    }
  }

  @Test
  public void testFastNanoTimeImpl() {
    FastNanoTimeUuidCreator creator = new FastNanoTimeUuidCreator();

    for (int i = 0; i < 10; i++) {
      byte[] uuidBytes = creator.generateBytes();
      System.out.printf("%d=%s%n", i, Hex.encodeHexString(uuidBytes));
    }
  }

  @Test
  public void testFastNanoTimeHex() {
    FastNanoTimeUuidCreator creator = new FastNanoTimeUuidCreator();

    for (int i = 0; i < 10; i++) {
      System.out.printf("%d=%s%n", i, creator.generateHex());
    }
  }

  @Test
  public void testSystem_NanoTime() {
    long last = System.nanoTime();
    for (int i = 0; i < 10; i++) {
      long now = System.nanoTime();

      System.out.printf("%d=%d(%,.6fms)%n", i, now, (now - last)/1000000d);

      last = now;
    }
  }
}
