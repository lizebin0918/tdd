package com.lzb.clock_machine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2024-02-17 21:20
 *
 * @author lizebin
 */
@Getter
@EqualsAndHashCode
public class ClockRecord {

    private final String person;
    private final LocalDate date;

    @EqualsAndHashCode.Exclude
    private LocalDateTime clockInTime;

    @EqualsAndHashCode.Exclude
    private LocalDateTime clockOutTime;

    public ClockRecord(String person, LocalDate date) {
        this.person = person;
        this.date = date;
    }

    public void clockOut(LocalDateTime clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public void clockIn(LocalDateTime clockInTime) {
        this.clockInTime = clockInTime;
    }

}
