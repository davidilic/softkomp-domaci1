package term;

import date.DateRange;

import time.TimeRange;
import interfaces.IPropertyValue;
import interfaces.ITerm;

import java.util.HashMap;

public class Term implements ITerm {
    private final DateRange dateRange;
    private final TimeRange timeRange;
    private final HashMap<String, IPropertyValue<?>> properties;

    public Term(DateRange dateRange, TimeRange timeRange, HashMap<String, IPropertyValue<?>> properties) {
        this.dateRange = dateRange;
        this.timeRange = timeRange;
        this.properties = properties;
    }

    @Override
    public void addProperty(String propertyName, IPropertyValue<?> propertyValue) {
        properties.put(propertyName, propertyValue);
    }

    @Override
    public void updateProperty(String propertyName, IPropertyValue<?> propertyValue) {
        properties.put(propertyName, propertyValue);
    }

    @Override
    public IPropertyValue<?> getProperty(String propertyName) {
        if (!properties.containsKey(propertyName)) {
            System.out.println("No such property");
            return null;
        }
        return properties.get(propertyName);
    }

    @Override
    public HashMap<String, IPropertyValue<?>> getProperties() {
        return properties;
    }

    @Override
    public DateRange getDateRange() {
        return dateRange;
    }

    @Override
    public TimeRange getTimeRange() {
        return timeRange;
    }

    @Override
    public void removeProperty(String propertyName) {
        properties.remove(propertyName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ITerm t)) {
            return false;
        }
        return t.getDateRange().equals(dateRange) && t.getTimeRange().equals(timeRange);
    }
}
