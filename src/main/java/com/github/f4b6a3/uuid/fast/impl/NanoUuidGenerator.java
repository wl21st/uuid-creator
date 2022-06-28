
package com.github.f4b6a3.uuid.fast.impl;

import com.github.f4b6a3.uuid.fast.common.IUuidGenerator;

import java.time.Instant;
import java.util.SplittableRandom;
import java.util.concurrent.locks.ReentrantLock;

/**
 * NanoTime-based Uuid creator.
 */
public class NanoUuidGenerator implements IUuidGenerator {

    private final int uniqueId;
    private final long startNanoTime;

    private final long baseNanoTime;

    private int sequence = 0;

    private ReentrantLock lock = new ReentrantLock();
    private long lastNanoTime;

    public NanoUuidGenerator() {
        this.startNanoTime = System.nanoTime();
        Instant now = Instant.now();

        this.baseNanoTime = now.getEpochSecond() * 1000_000_000L + now.getNano();
        this.uniqueId = new SplittableRandom().nextInt();
    }

    public Uuid generate() {
        lock.lock();
        try {
            long currentNanoTime = baseNanoTime + (System.nanoTime() - startNanoTime);

            if (this.lastNanoTime == currentNanoTime) {
                sequence++;
            } else {
                sequence = 0;
            }

            Uuid result = new Uuid(currentNanoTime, sequence << 32 | uniqueId);

            this.lastNanoTime = currentNanoTime;

            return result;
        } finally {
            lock.unlock();
        }
    }

}
