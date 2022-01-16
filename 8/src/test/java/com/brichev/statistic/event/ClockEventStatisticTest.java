package com.brichev.statistic.event;

import com.brichev.statistic.clock.SettableClock;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClockEventStatisticTest {

    private final Integer MINUTES_IN_HOUR = 60;
    private final Integer SECONDS_IN_HOUR = 3600;

    private ClockEventStatistic getClockEventsStatistic() {
        return new ClockEventStatistic(new SettableClock(Instant.ofEpochSecond(0L)));
    }

    private String getEventName() {
        return "Some event";
    }

    @Test
    void noEventsTest() {
        var eventStatistic = getClockEventsStatistic();
        assertEquals(0.0, eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(0, eventStatistic.getAllEventStatistic().size());
    }

    @Test
    void fixedTimeOneEventStatsForNonExistingTest() {
        var eventStatistic = getClockEventsStatistic();
        var anotherEventName = "Next event";
        eventStatistic.incEvent(getEventName());
        assertEquals(0.0, eventStatistic.getEventStatisticByName(anotherEventName));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());
    }

    @Test
    void fixedTimeOneEventOneIncrementTest() {
        var eventStatistic = getClockEventsStatistic();
        eventStatistic.incEvent(getEventName());
        assertEquals(1.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());
    }

    @Test
    void fixedTimeOneEventManyIncrementsTest() {
        var eventStatistic = getClockEventsStatistic();
        for (int i = 1; i < 500; i++) {
            eventStatistic.incEvent(getEventName());
            assertEquals(i / 60.0, eventStatistic.getEventStatisticByName(getEventName()));
            assertEquals(1, eventStatistic.getAllEventStatistic().size());
        }
    }
    
    @Test
    void changingTimeOneEventOneIncrementTest() {
        var clock = new SettableClock(Instant.ofEpochSecond(0L));
        var eventStatistic = new ClockEventStatistic(clock);
        eventStatistic.incEvent(getEventName());

        assertEquals(1.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());

        clock.setNow(Instant.ofEpochSecond(123));
        assertEquals(1.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());
    }

    @Test
    void changingTimeOneEventOneIncrementHourPassedTest() {
        var clock = new SettableClock(Instant.ofEpochSecond(0L));
        var eventStatistic = new ClockEventStatistic(clock);
        eventStatistic.incEvent(getEventName());

        assertEquals(1.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());

        clock.setNow(Instant.ofEpochSecond(123));
        assertEquals(1.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());

        clock.setNow(Instant.ofEpochSecond(3600));
        assertEquals(1.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());

        clock.setNow(Instant.ofEpochSecond(3601));
        assertEquals(0.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());
    }

    @Test
    void changingTimeOneEventFewIncrementsWithinAnHourTest() {
        var clock = new SettableClock(Instant.ofEpochSecond(0L));
        var eventStatistic = new ClockEventStatistic(clock);
        eventStatistic.incEvent(getEventName());

        assertEquals(1.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());

        clock.setNow(Instant.ofEpochSecond(123));
        assertEquals(1.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());

        clock.setNow(Instant.ofEpochSecond(3600));
        assertEquals(1.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());

        eventStatistic.incEvent(getEventName());
        clock.setNow(Instant.ofEpochSecond(3600));
        assertEquals(2.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());

        clock.setNow(Instant.ofEpochSecond(3601));
        assertEquals(1.0 / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(getEventName()));
        assertEquals(1, eventStatistic.getAllEventStatistic().size());
    }

    @Test
    void changingTimeManyEventsManyIncrementsTest() {
        var clock = new SettableClock(Instant.ofEpochSecond(0L));
        var eventStatistic = new ClockEventStatistic(clock);
        var events = List.of("event0", "event1", "event2", "event3", "event4", "event5", "event6", "event7");
        var periods = List.of(10, 11, 24, 31, 47, 53, 66, 79);
        var count = 500;
        for (int i = 1; i < count; i++) {
            for (int j = 0; j < events.size(); j++) {
                var eventName = events.get(j);
                var period = periods.get(j);
                clock.setNow(Instant.ofEpochSecond(i * period.longValue()));
                eventStatistic.incEvent(eventName);
            }
        }
        assertEquals(events.size(), eventStatistic.getAllEventStatistic().size());

        clock.setNow(Instant.ofEpochSecond(0L));
        for (String eventName : events) {
            assertEquals(0.0, eventStatistic.getEventStatisticByName(eventName));
        }

        var max = count * periods.get(periods.size() - 1);
        for (int i = 0; i < max; i++) {
            clock.setNow(Instant.ofEpochSecond(i));
            for (int j = 0; j < events.size(); j++) {
                var eventName = events.get(j);
                var period = periods.get(j);
                if (i < count * period) {
                    var first = period * ((i - SECONDS_IN_HOUR) / period);
                    if ((i - SECONDS_IN_HOUR) % period != 0) {
                        first += period;
                    }
                    var start = period > first ? period : first;
                    var cnt = Math.max(0, (i - start + period) / period);

                    assertEquals(cnt / MINUTES_IN_HOUR.doubleValue(), eventStatistic.getEventStatisticByName(eventName));
                }
            }
        }
    }
}
