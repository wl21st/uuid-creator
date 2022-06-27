
package com.github.f4b6a3.uuid.benchmark;

import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.fast.FastInstantUuidCreator;
import com.github.f4b6a3.uuid.fast.FastNanoTimeUuidCreator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
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

import java.util.concurrent.TimeUnit;

@Fork(1)
@Threads(1)
@State(Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SystemThroughput {

  /**
   * The range of nanoTime is 317 years, don't have to worry about negative case.
   *
   * @param blackhole
   */
  @Benchmark
  public void sys01_nanoTime(Blackhole blackhole) {
    blackhole.consume(System.nanoTime());
  }

  /**
   * The range of the system current time millis is 317,097,919.84 years, this will never overflow
   *
   * @param blackhole
   */
  @Benchmark
  public void sys01_currentMillis(Blackhole blackhole) {
    blackhole.consume(System.currentTimeMillis());
  }

  private FastInstantUuidCreator fastInstantUuidCreator = new FastInstantUuidCreator();

  private FastNanoTimeUuidCreator fastNanoTimeUuidCreator = new FastNanoTimeUuidCreator();

  @Benchmark
  public void sys02_fastInstantsGenerateHex(Blackhole blackhole) {
    blackhole.consume(fastInstantUuidCreator.generateHex());
  }

  @Benchmark
  public void sys02_fastInstantGenerateBytes(Blackhole blackhole) {
    blackhole.consume(fastInstantUuidCreator.generateBytes());
  }

  @Benchmark
  public void sys02_fastNanoTimeGenerateHex(Blackhole blackhole) {
    blackhole.consume(fastNanoTimeUuidCreator.generateHex());
  }

  @Benchmark
  public void sys02_fastNanoTimeGenerateBytes(Blackhole blackhole) {
    blackhole.consume(fastNanoTimeUuidCreator.generateBytes());
  }

  @Benchmark
  public void sys02_fastNanoTimeGenerateUuid(Blackhole blackhole) {
    blackhole.consume(fastNanoTimeUuidCreator.generateUuid());
  }

  @Benchmark
  public void UuidCreator01_TimeOrdered(Blackhole blackhole) {
    UUID uuid = UuidCreator.getTimeOrdered();

    blackhole.consume(uuid.toString());
  }

}