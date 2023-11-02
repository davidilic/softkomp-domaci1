package date;

public abstract class AbstractDateRange implements Comparable<AbstractDateRange> {

    private final AbstractDate start;
    private final AbstractDate end;

    public AbstractDateRange(AbstractDate start, AbstractDate end) {
        this.start = start;
        this.end = end;
    }

    public AbstractDate getStart() {
        return start;
    }

    public AbstractDate getEnd() {
        return end;
    }

    public boolean contains(AbstractDate d) {
        return start.compareTo(d) <= 0 && end.compareTo(d) >= 0;
    }

    public boolean overlaps(AbstractDateRange other) {
        return contains(other.start) || contains(other.end) || other.contains(start) || other.contains(end);
    }

    public boolean equals(Object o) {
        if (o instanceof AbstractDateRange other) {
            return start.equals(other.start) && end.equals(other.end);
        }
        return false;
    }

    public String toString() {
        return start + " - " + end;
    }

    @Override
    public int compareTo(AbstractDateRange o) {
        if (start.compareTo(o.start) != 0) {
            return start.compareTo(o.start);
        } else {
            return end.compareTo(o.end);
        }
    }
}
