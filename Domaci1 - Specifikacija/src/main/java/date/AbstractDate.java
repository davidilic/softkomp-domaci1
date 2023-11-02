package date;

public abstract class AbstractDate implements Comparable<AbstractDate> {

    private final int year;
    private final int month;
    private final int day;

    public AbstractDate(int year, int month, int day) {
        if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31) {
            throw new IllegalArgumentException();
        }
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int compareTo(AbstractDate other) {
        if (year != other.year) {
            return year - other.year;
        } else if (month != other.month) {
            return month - other.month;
        } else {
            return day - other.day;
        }
    }

    public boolean equals(Object o) {
        if (o instanceof AbstractDate other) {
            return year == other.year && month == other.month && day == other.day;
        }
        return false;
    }

    public String toString() {
        return year + "-" + month + "-" + day;
    }

}
