package com.lzb.clock_machine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 打卡机<br/>
 * 1.假设考勤时间是上午[08:30~09:30],下午[16:00~18:00 ~18:00],早上晚于09:00算迟到,下午早于17:00 算早退
 * 2.每天打够8个小时算弹性,不计迟到和早退,缺失任一考勤时间间算缺勤,既迟到和早退也算缺勤
 * 3.现在假设考勤数据在考勤机中损毁了,请自行生成20天或 20天 或N天数据[可以考虑排除周末日期]
 * 4.统计某个员工的期间段考勤总小时数,统计员工的迟到日期,早退日期,甚至计算对应天数等
 * 5.合理面向对象抽象
 * 6.尽可能保证程序的扩展性
 * Created on : 2024-02-17 21:04
 *
 * @author lizebin
 */
class ClockMachineTest {

    private ClockMachine clockMachine;

    @BeforeEach
    void setUp() {
        clockMachine = new ClockMachine();
    }

    @Test
    @DisplayName("初始化打卡机，默认30天（从今天算起）")
    void should_init_clock_machine() {
        assertEquals(30, clockMachine.getDays());
    }

    @Test
    @DisplayName("A员工打上班卡")
    void should_clock_in_by_A() {

        String person = "A";
        LocalDateTime clockInTime = LocalDateTime.of(LocalDate.of(2024, 2, 17), LocalTime.of(9, 0, 0));
        clockMachine.clockIn(person, clockInTime);

        List<ClockRecord> clockRecordsForA = clockMachine.listClockRecord(person);
        assertEquals(1, clockRecordsForA.size());
        ClockRecord clockRecord = clockRecordsForA.get(0);
        assertEquals(person, clockRecord.getPerson());
        assertEquals(clockInTime, clockRecord.getClockInTime());
    }

    @Test
    @DisplayName("A员工打下班卡")
    void should_clock_out_by_A() {

        String person = "A";
        LocalDateTime clockOutTime = LocalDateTime.of(LocalDate.of(2024, 2, 17), LocalTime.of(9, 0, 0));
        clockMachine.clockOut(person, clockOutTime);

        List<ClockRecord> clockRecordsForA = clockMachine.listClockRecord(person);
        assertEquals(1, clockRecordsForA.size());
        ClockRecord clockRecord = clockRecordsForA.get(0);
        assertEquals(person, clockRecord.getPerson());
        assertEquals(clockOutTime, clockRecord.getClockOutTime());

    }


}
