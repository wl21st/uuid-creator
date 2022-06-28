
package com.github.f4b6a3.uuid.benchmark;

import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.fast.impl.InstantUuidGenerator;
import com.github.f4b6a3.uuid.fast.impl.NanoUuidGenerator;
import com.github.f4b6a3.uuid.utils.NumUtils;
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
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 3)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class UuidGeneratorThroughput {

    private InstantUuidGenerator instantUuidGenerator = new InstantUuidGenerator();

    private NanoUuidGenerator nanoUuidGenerator = new NanoUuidGenerator();

    @Benchmark
    public void system_01_get_nano_seconds(Blackhole blackhole) {
        // The range of nanoTime is 317 years, don't have to worry about negative case.
        blackhole.consume(System.nanoTime());
    }

    @Benchmark
    public void system_02_get_current_millis(Blackhole blackhole) {
        // The range of the system current time millis is 317,097,919.84 years, this will never overflow
        blackhole.consume(System.currentTimeMillis());
    }

    @Benchmark
    public void instant_uuid_generator_01_hex(Blackhole blackhole) {
        blackhole.consume(instantUuidGenerator.generate().toString());
    }

    @Benchmark
    public void instant_uuid_generator_02_bytes(Blackhole blackhole) {
        blackhole.consume(NumUtils.toBytes(instantUuidGenerator.generate()));
    }

    @Benchmark
    public void instant_uuid_generator_03_direct(Blackhole blackhole) {
        blackhole.consume(instantUuidGenerator.generate());
    }

    @Benchmark
    public void nano_uuid_generator_01_hex(Blackhole blackhole) {
        blackhole.consume(nanoUuidGenerator.generate().toString());
    }

    @Benchmark
    public void nano_uuid_generator_02_bytes(Blackhole blackhole) {
        blackhole.consume(NumUtils.toBytes(nanoUuidGenerator.generate()));
    }

    @Benchmark
    public void nano_uuid_generator_03_direct(Blackhole blackhole) {
        blackhole.consume(nanoUuidGenerator.generate());
    }

    @Benchmark
    public void uuid_creator_01_time_ordered(Blackhole blackhole) {
        blackhole.consume(UuidCreator.getTimeOrdered());
    }

    @Benchmark
    public void uuid_creator_02_time_ordered_tostring(Blackhole blackhole) {
        UUID uuid = UuidCreator.getTimeOrdered();

        blackhole.consume(uuid.toString());
    }

    @Benchmark
    public void uuid_creator_03_time_ordered_epoch1(Blackhole blackhole) {
        blackhole.consume(UuidCreator.getTimeOrderedEpochPlus1());
    }

    @Benchmark
    public void uuid_creator_04_time_ordered_epoch1_tostring(Blackhole blackhole) {
        blackhole.consume(UuidCreator.getTimeOrderedEpochPlus1().toString());
    }


}