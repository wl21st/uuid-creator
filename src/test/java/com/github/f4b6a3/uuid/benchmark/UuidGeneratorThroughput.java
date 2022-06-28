
package com.github.f4b6a3.uuid.benchmark;

import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.fast.impl.InstantUuidGenerator;
import com.github.f4b6a3.uuid.fast.impl.NanoUuidGenerator;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Fork(1)
@Threads(1)
@State(Scope.Benchmark)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 3, time = 3)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class UuidGeneratorThroughput {

    private InstantUuidGenerator instantUuidGenerator = new InstantUuidGenerator();

    private NanoUuidGenerator nanoUuidGenerator = new NanoUuidGenerator();

    @Benchmark
    public void b01_get_nano_seconds(Blackhole blackhole) {
        // The range of nanoTime is 317 years, don't have to worry about negative case.
        blackhole.consume(System.nanoTime());
    }

    @Benchmark
    public void b02_get_current_millis(Blackhole blackhole) {
        // The range of the system current time millis is 317,097,919.84 years, this will never overflow
        blackhole.consume(System.currentTimeMillis());
    }

    @Benchmark
    public void b03_instant_uuid_generator_hex(Blackhole blackhole) {
        blackhole.consume(instantUuidGenerator.generate().hexValue());
    }

    @Benchmark
    public void b04_instant_uuid_generator_bytes(Blackhole blackhole) {
        blackhole.consume(instantUuidGenerator.generate().bytesValue());
    }

    @Benchmark
    public void b05_instant_uuid_generator_direct(Blackhole blackhole) {
        blackhole.consume(instantUuidGenerator.generate());
    }

    @Benchmark
    public void b06_nano_uuid_generator_hex(Blackhole blackhole) {
        blackhole.consume(nanoUuidGenerator.generate().hexValue());
    }

    @Benchmark
    public void b07_nano_uuid_generator_bytes(Blackhole blackhole) {
        blackhole.consume(nanoUuidGenerator.generate().bytesValue());
    }

    @Benchmark
    public void b08_nano_uuid_generator_direct(Blackhole blackhole) {
        blackhole.consume(nanoUuidGenerator.generate());
    }

    @Benchmark
    public void b09_uuid_creator_01_time_ordered(Blackhole blackhole) {
        blackhole.consume(UuidCreator.getTimeOrdered());
    }

    @Benchmark
    public void b10_uuid_creator_02_time_ordered_tostring(Blackhole blackhole) {
        UUID uuid = UuidCreator.getTimeOrdered();

        blackhole.consume(uuid.toString());
    }

}