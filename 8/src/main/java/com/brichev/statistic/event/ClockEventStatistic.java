package com.brichev.statistic.event;

import com.brichev.statistic.clock.Clock;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClockEventStatistic implements EventStatistic {
    private final Clock clock;
    private final Map<String, List<Long>> events;
    private final Long MINUTES_IN_HOUR = 60L;
    private final Long SECONDS_IN_HOUR = 3600L;

    public ClockEventStatistic(Clock clock) {
        this.clock = clock;
        events = new HashMap<>();
    }

    @Override
    public void incEvent(String name) {
        var now = clock.now();
        events.putIfAbsent(name, new ArrayList<>());
        events.get(name).add(now.getEpochSecond());
    }

    @Override
    public Double getEventStatisticByName(String name) {
        if (!events.containsKey(name)) {
            return 0.0;
        }
        var eventsByName = events.get(name);
        var now = clock.now().getEpochSecond();
        var hourAgo = now - SECONDS_IN_HOUR >= 0 ? now - SECONDS_IN_HOUR : 0;
        var eventsByNameLastHourCount = eventsByName.stream()
                .filter(event -> event >= hourAgo && now >= event)
                .count();
        return eventsByNameLastHourCount / MINUTES_IN_HOUR.doubleValue();
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        return events.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> getEventStatisticByName(e.getKey())
                ));
    }

    @Override
    public void printStatistic() {
        events.forEach((key, value) -> {
            var validEventsCount = value.stream()
                    .filter(time -> time > 0)
                    .count();
            System.out.println("Event: " + key + ", rpm: " + validEventsCount / MINUTES_IN_HOUR.doubleValue());
        });
    }
}
