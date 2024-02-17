package com.lzb.clock_machine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2024-02-17 21:10
 *
 * @author lizebin
 */
@Getter
public class ClockMachine {

    private int days = 30;
    private final List<ClockRecord> clockRecords = new ArrayList<>();

    public ClockMachine() {
    }

    public ClockMachine(int days) {
        this.days = days;
    }

    public void clockIn(String person, LocalDateTime time) {
        ClockRecord clockRecord = new ClockRecord(person, time.toLocalDate());
        clockRecord.clockIn(time);
        clockRecords.add(clockRecord);
    }

    public List<ClockRecord> listClockRecord(String person) {
        return clockRecords.stream().filter(byPerson(person)).toList();
    }

    private Predicate<? super ClockRecord> byPerson(String person) {
        return clockRecord -> clockRecord.getPerson().equals(person);
    }

    public Optional<ClockRecord> listClockRecord(String person, LocalDate time) {
        return clockRecords.stream().filter(byPerson(person)).filter(byDate(time)).findFirst();
    }

    private Predicate<? super ClockRecord> byDate(LocalDate time) {
        return clockRecord -> clockRecord.getDate().equals(time);
    }

    public void clockOut(String person, LocalDateTime time) {
        ClockRecord clockRecord = listClockRecord(person, time.toLocalDate())
            .orElseGet(() -> {
                ClockRecord newRecord = new ClockRecord(person, time.toLocalDate());
                clockRecords.add(newRecord);
                return newRecord;
            });
        clockRecord.clockOut(time);
    }
}
