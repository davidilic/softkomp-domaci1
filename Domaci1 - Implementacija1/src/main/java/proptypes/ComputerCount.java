package proptypes;

import interfaces.IPropertyValue;

public class ComputerCount implements IPropertyValue<ComputerCount> {
    private final int count;

    public ComputerCount(int count) {
        this.count = count;
    }

    @Override
    public boolean isEqual(IPropertyValue<?> other) {
        if (other instanceof ComputerCount) {
            return this.count == ((ComputerCount) other).count;
        }
        return false;
    }

    @Override
    public int compareTo(IPropertyValue<?> other) {
        if (other instanceof ComputerCount) {
            return Integer.compare(this.count, ((ComputerCount) other).count);
        }
        return -2;
    }

    @Override
    public boolean matchesQuery(String query) {
        return String.valueOf(this.count).contains(query);
    }

    @Override
    public String toString() {
        return String.valueOf(count);
    }
}