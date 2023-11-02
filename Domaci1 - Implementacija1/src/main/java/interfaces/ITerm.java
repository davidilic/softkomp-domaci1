package interfaces;

import date.DateRange;
import time.TimeRange;

import java.util.HashMap;

public interface ITerm {
    void addProperty(String propertyName, IPropertyValue<?> propertyValue);
    void updateProperty(String propertyName, IPropertyValue<?> propertyValue);
    IPropertyValue<?> getProperty(String propertyName);
    HashMap<String, IPropertyValue<?>> getProperties();
    DateRange getDateRange();
    TimeRange getTimeRange();
    void removeProperty(String propertyName);
    boolean equals(Object o);
}
