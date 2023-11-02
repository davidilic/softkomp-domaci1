package proptypes;

import interfaces.IPropertyValue;

public class Equipment implements IPropertyValue<Equipment> {
    private final String name;

    public Equipment(String name) {
        this.name = name;
    }

    @Override
    public boolean isEqual(IPropertyValue<?> other) {
        if (other instanceof Equipment) {
            return this.name.equals(((Equipment) other).name);
        }
        return false;
    }

    @Override
    public int compareTo(IPropertyValue<?> other) {
        if (other instanceof Equipment) {
            return this.name.compareTo(((Equipment) other).name);
        }
        return -2;
    }

    @Override
    public boolean matchesQuery(String query) {
        return this.name.contains(query);
    }

    @Override
    public String toString() {
        return name;
    }
}