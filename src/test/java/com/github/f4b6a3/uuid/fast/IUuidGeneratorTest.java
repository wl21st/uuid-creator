package com.github.f4b6a3.uuid.fast;

import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.fast.common.IUuidGenerator;
import com.github.f4b6a3.uuid.fast.impl.InstantUuidGenerator;
import com.github.f4b6a3.uuid.fast.impl.NanoUuidGenerator;
import org.junit.Test;

import java.util.UUID;

public class IUuidGeneratorTest {

    @Test
    public void testTimedOrderedUuidCreator() {
        for (int i = 0; i < 10; i++) {
            UUID uuid = UuidCreator.getTimeOrdered();
            System.out.printf("%d=%s%n", i, uuid.toString());
        }
    }

    @Test
    public void testNanoTimeHexValue() {
        IUuidGenerator generator = new NanoUuidGenerator();

        for (int i = 0; i < 10; i++) {
            System.out.printf("%d=%s%n", i, generator.generate().toString());
        }
    }

    @Test
    public void testInstantHexValue() {
        IUuidGenerator generator = new InstantUuidGenerator();

        for (int i = 0; i < 10; i++) {
            System.out.printf("%d=%s%n", i, generator.generate().toString());
        }
    }


    @Test
    public void testNanoTimeGap() {
        long last = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            long now = System.nanoTime();

            System.out.printf("%d=%d(%,.6f ms)%n", i, now, (now - last) / 1000000d);

            last = now;
        }
    }
}
