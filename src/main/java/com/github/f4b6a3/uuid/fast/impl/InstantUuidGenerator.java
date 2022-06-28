package com.github.f4b6a3.uuid.fast.impl;

import com.github.f4b6a3.uuid.fast.common.IUuidGenerator;

import java.time.Instant;
import java.util.SplittableRandom;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Java Instant-based Uuid generator.
 * TODO: Add the host-specific id to the Uuid.
 *
 * @author mluo
 */
public class InstantUuidGenerator implements IUuidGenerator {

    private Instant lastInstant = Instant.now();

    private int sequence = 0;

    private int uniqueId = new SplittableRandom().nextInt();

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public UUID generate() {
        lock.lock();
        try {
            Instant now = Instant.now();

            // Check if we are in the exact time in ns precision
            // Very rare and .
            if (now.equals(lastInstant)) {
                sequence++;
            } else {
                lastInstant = now;
                sequence = 0;
            }

            return new UUID(now.getEpochSecond() << 20 | now.getNano() & 0xFFFFFF, sequence << 32 | uniqueId);
        } finally {
            lock.unlock();
        }
    }

}
