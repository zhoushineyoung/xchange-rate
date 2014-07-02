package com.ebj.conf;

import org.joda.time.Period;

public class JettyServerConfig {
    // @JsonProperty
    // @Min(1)
    private static int numThreads = Math.max(10, Runtime.getRuntime().availableProcessors() + 1);

    // @JsonProperty
    // @NotNull
    private Period maxIdleTime = new Period("PT5m");

    public int getNumThreads() {
        return numThreads;
    }

    public Period getMaxIdleTime() {
        return maxIdleTime;
    }
}
