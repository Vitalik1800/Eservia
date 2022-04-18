package com.eservia.utils;


import androidx.annotation.NonNull;

import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class RRuleUtil {

    private static final String RULE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String RULE_DELIMITER = ";";
    private static final String RULE_INNER_DELIMITER = "=";

    private static final String DTSTART = "DTSTART";
    private static final String DTEND = "DTEND";
    private static final String UNTIL = "UNTIL";

    public static List<Day> getDaysByRuleList(List<Rule> rules, int durationMonth) throws Exception {
        List<Day> workDays = new ArrayList<>();
        List<Day> exclusions = new ArrayList<>();
        for (Rule rule : rules) {
            if (!rule.isExclusion()) {
                workDays.addAll(getDaysByRule(rule, durationMonth));
            } else {
                exclusions.addAll(getExclusionsByRule(rule, durationMonth));
            }
        }
        List<Day> filtered = filter(workDays, exclusions);
        Collections.sort(filtered);
        return filtered;
    }

    private static List<Day> getDaysByRule(Rule rule, int durationMonth) throws Exception {
        LogUtils.debug(Contract.LOG_TAG, rule.toString());
        List<Day> result = new ArrayList<>();

        List<String> splitRule = new ArrayList<>(Arrays.asList(rule.getRule().split(RULE_DELIMITER)));

        DateTime start = startDate(splitRule);
        start = DateTime.now().withYear(start.getYear()).withDayOfYear(start.getDayOfYear());

        DateTime until = untilDate(splitRule);
        if (until != null) {
            until = DateTime.now().withYear(until.getYear()).withDayOfYear(until.getDayOfYear());
        }

        Time timeStart = timeStart(splitRule);
        Time timeEnd = timeEnd(splitRule);

        removeDateStartDateEnd(splitRule);

        String recur = buildRecur(splitRule);
        RecurrenceRule recurrenceRule = new RecurrenceRule(recur);
        if (until == null) {
            recurrenceRule.setUntil(new org.dmfs.rfc5545.DateTime(until(durationMonth).getMillis()));
        } else {
            recurrenceRule.setUntil(new org.dmfs.rfc5545.DateTime(until.getMillis()));
        }

        RecurrenceRuleIterator it = recurrenceRule.iterator(
                new org.dmfs.rfc5545.DateTime(start.getMillis()));

        while (it.hasNext() && (!recurrenceRule.isInfinite())) {
            DateTime dateTime = new DateTime(it.nextDateTime().getTimestamp());
            result.add(new Day(dateTime, timeStart, timeEnd));
        }
        return result;
    }

    private static List<Day> getExclusionsByRule(Rule rule, int durationMonth) throws Exception {
        return new ArrayList<>(getDaysByRule(rule, durationMonth));
    }

    private static List<Day> filter(List<Day> workDays, List<Day> exclusions) {
        ListIterator<Day> iterator = workDays.listIterator();
        while (iterator.hasNext()) {
            Day day = iterator.next();
            for (Day exclusion : exclusions) {
                if (day.dateTime.dayOfYear().equals(exclusion.dateTime.dayOfYear())
                        && day.dateTime.year().equals(exclusion.dateTime.year())) {
                    if (day.isValid() && exclusion.isValid()) {
                        if (biggerOrEqual(day.timeStart, exclusion.timeStart)
                                && biggerOrEqual(exclusion.timeEnd, day.timeEnd)) {
                            iterator.remove();
                            break;
                        }
                    } else {
                        iterator.remove();
                        break;
                    }
                }
            }
        }
        return workDays;
    }

    private static void removeDateStartDateEnd(List<String> splitRule) {
        ListIterator<String> iterator = splitRule.listIterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.startsWith(DTSTART) || s.startsWith(DTEND)) {
                iterator.remove();
            }
        }
    }

    private static DateTime startDate(List<String> splitRule) {
        for (String s : splitRule) {
            if (s.startsWith(DTSTART)) {
                String time = s.split(RULE_INNER_DELIMITER)[1];
                return DateTime.parse(time, DateTimeFormat.forPattern(RULE_TIME_PATTERN));
            }
        }
        return DateTime.now();
    }

    @Nullable
    private static DateTime untilDate(List<String> splitRule) {
        for (String s : splitRule) {
            if (s.startsWith(UNTIL)) {
                String time = s.split(RULE_INNER_DELIMITER)[1];
                return DateTime.parse(time, DateTimeFormat.forPattern(RULE_TIME_PATTERN));
            }
        }
        return null;
    }

    @Nullable
    private static Time timeStart(List<String> splitRule) {
        for (String s : splitRule) {
            if (s.startsWith(DTSTART)) {
                String time = s.split(RULE_INNER_DELIMITER)[1];
                DateTime dateTime = DateTime.parse(time, DateTimeFormat.forPattern(RULE_TIME_PATTERN));
                int hour = dateTime.getHourOfDay();
                int min = dateTime.getMinuteOfHour();
                int sec = dateTime.getSecondOfMinute();
                return new Time(timeStr(hour), timeStr(min), timeStr(sec));
            }
        }
        return null;
    }

    @Nullable
    private static Time timeEnd(List<String> splitRule) {
        for (String s : splitRule) {
            if (s.startsWith(DTEND)) {
                String time = s.split(RULE_INNER_DELIMITER)[1];
                DateTime dateTime = DateTime.parse(time, DateTimeFormat.forPattern(RULE_TIME_PATTERN));
                int hour = dateTime.getHourOfDay();
                int min = dateTime.getMinuteOfHour();
                int sec = dateTime.getSecondOfMinute();
                return new Time(timeStr(hour), timeStr(min), timeStr(sec));
            }
        }
        return null;
    }

    private static String timeStr(int time) {
        String result = String.valueOf(time);
        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;
    }

    private static String buildRecur(List<String> splitRule) {
        StringBuilder builder = new StringBuilder();
        for (String s : splitRule) {
            builder.append(s);
            builder.append(RULE_DELIMITER);
        }
        return removeLastChar(builder.toString());
    }

    private static DateTime until(int durationMonth) {
        return DateTime.now().plusMonths(durationMonth);
    }

    private static String removeLastChar(String s) {
        String result = "";
        if (s != null && !s.isEmpty()) {
            result = s.replaceFirst(".$", "");
        }
        return result;
    }

    private static boolean biggerOrEqual(Time bigger, Time other) {
        if (intVal(bigger.hour) > intVal(other.hour)) {
            return true;
        }
        if (intVal(bigger.hour) < intVal(other.hour)) {
            return false;
        }
        if (intVal(bigger.min) > intVal(other.min)) {
            return true;
        }
        if (intVal(bigger.min) < intVal(other.min)) {
            return false;
        }
        return intVal(bigger.sec) >= intVal(other.sec);
    }

    private static int intVal(String s) {
        return Integer.parseInt(s);
    }

    public static class Rule {

        private final String rule;

        private final Boolean isExclusion;

        public Rule(String rule, Boolean isExclusion) {
            this.rule = rule;
            this.isExclusion = isExclusion;
        }

        @NonNull
        @Override
        public String toString() {
            if (rule == null || isExclusion == null) {
                return super.toString();
            }
            if (isExclusion) {
                return "EX " + rule;
            } else {
                return "   " + rule;
            }
        }

        public String getRule() {
            return rule;
        }

        public Boolean isExclusion() {
            return isExclusion;
        }

    }

    public static class Day implements Validator, Comparable<Day> {

        private DateTime dateTime;
        private Time timeStart;
        private final Time timeEnd;

        public Day(DateTime dateTime, Time timeStart, Time timeEnd) {
            this.dateTime = dateTime;
            this.timeStart = timeStart;
            this.timeEnd = timeEnd;
        }

        public DateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Time getTimeStart() {
            return timeStart;
        }

        public void setTimeStart(Time timeStart) {
            this.timeStart = timeStart;
        }

        @Override
        public boolean isValid() {
            return dateTime != null
                    && timeStart != null
                    && timeEnd != null
                    && timeStart.isValid()
                    && timeEnd.isValid();
        }

        @NonNull
        @Override
        public String toString() {
            if (!isValid()) {
                return super.toString();
            }
            return "START " + timeStart.toString() +
                    " END " + timeEnd.toString() +
                    " : " + dateTime.toString();
        }

        @Override
        public int compareTo(@NonNull Day day) {
            return this.dateTime.compareTo(day.getDateTime());
        }
    }

    public static class Time implements Validator {

        private String hour;
        private String min;
        private String sec;

        public Time(String hour, String min, String sec) {
            this.hour = hour;
            this.min = min;
            this.sec = sec;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getSec() {
            return sec;
        }

        public void setSec(String sec) {
            this.sec = sec;
        }

        @Override
        public boolean isValid() {
            return hour != null && min != null && sec != null;
        }

        @Override
        public boolean equals(Object obj) {
            if (getClass() != obj.getClass())
                return false;
            Time o = (Time) obj;
            return isValid()
                    && o.isValid()
                    && hour.equals(o.hour)
                    && min.equals(o.min)
                    && sec.equals(o.sec);
        }

        @NonNull
        @Override
        public String toString() {
            if (!isValid()) {
                return super.toString();
            }
            return hour + ":" + min + ":" + sec;
        }
    }

    private interface Validator {
        boolean isValid();
    }
}
