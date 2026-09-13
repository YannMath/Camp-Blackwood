package com.test.objects;

public class GameState {
    private int second = 0;
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year = 0; // Not relevant for gameplay

    private String location; // TODO: Make a whole location class and add it here

    public GameState(int m, int d, int h, int min, String location) {
        this.month = m;
        this.day = d;
        this.hour = h;
        this.minute = min;
        this.location = location;
    }

    public void update(int seconds) {
        second += seconds;
        minute += second / 60;
        second %= 60;
        hour += minute / 60;
        minute %= 60;

        day += hour / 24;
        hour %= 24;

        while (day > calculateDaysCurrentMonth()) {
            day -= calculateDaysCurrentMonth();
            month++;
            if (month > 12) {
                month = 1;
                year++;
                year %= 4;
            }
        }
    }

    private int calculateDaysCurrentMonth() {
        if (year % 4 == 0 && month == 2) {
            return 29;
        }
        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12:
                return 31;
            case 2:
                return 28;
            default:
                return 30;
        }
    }

    public void setMinute(int minute) {this.minute = minute;}
    public void setHour(int hour) {this.hour = hour;}
    public void setDay(int day) {this.day = day;}
    public void setMonth(int month) {this.month = month % 12;}
    public void setLocation(String location) {this.location = location;}

    public int getMinute() {return minute;}
    public int getHour() {return hour;}
    public int getDay() {return day;}
    public int getMonth() {return month;}
    public String getLocation() {return location;}
}