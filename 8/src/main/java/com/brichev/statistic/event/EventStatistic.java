package com.brichev.statistic.event;

import java.util.Map;

public interface EventStatistic {
    void incEvent(String name);

    Double getEventStatisticByName(String name);

    Map<String, Double> getAllEventStatistic();

    void printStatistic();
}
