package time;

public abstract class AbstractTime implements Comparable<AbstractTime> {

    private final int hour;
    private final int minute;

    public AbstractTime(int hour, int minute) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new IllegalArgumentException();
        }
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int compareTo(AbstractTime other) {
        if (hour != other.hour) {
            return hour - other.hour;
        } else {
            return minute - other.minute;
        }
    }

    public boolean equals(Object o) {
        if (o instanceof AbstractTime other) {
            return hour == other.hour && minute == other.minute;
        }
        return false;
    }

    public String toString() {
        return hour + ":" + minute;
    }
}
