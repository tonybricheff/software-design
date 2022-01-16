package com.brichev.statistic.clock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public class SettableClock implements Clock {
    private Instant now;

    @Override
    public Instant now() {
        return now;
    }
}
