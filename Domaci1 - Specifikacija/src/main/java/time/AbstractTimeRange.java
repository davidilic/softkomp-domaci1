package time;

public abstract class AbstractTimeRange implements Comparable<AbstractTimeRange> {
        private final AbstractTime start;
        private final AbstractTime end;

        public AbstractTimeRange(AbstractTime start, AbstractTime end) {
            this.start = start;
            this.end = end;
        }

        public AbstractTime getStart() {
            return start;
        }

        public AbstractTime getEnd() {
            return end;
        }

        public boolean contains(AbstractTime t) {
            return start.compareTo(t) <= 0 && end.compareTo(t) >= 0;
        }

        public boolean overlaps(AbstractTimeRange other) {
            return contains(other.start) || contains(other.end) || other.contains(start) || other.contains(end);
        }

        public boolean equals(Object o) {
            if (o instanceof AbstractTimeRange other) {
                return start.equals(other.start) && end.equals(other.end);
            }
            return false;
        }

        public String toString() {
            return start + " - " + end;
        }

    @Override
    public int compareTo(AbstractTimeRange o) {
        if (start.compareTo(o.start) != 0) {
            return start.compareTo(o.start);
        } else {
            return end.compareTo(o.end);
        }
    }
}
