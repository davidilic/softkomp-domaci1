package term;

import date.AbstractDateRange;
import props.IPropertyValue;
import time.AbstractTimeRange;

import java.util.HashMap;

public interface ITerm {
    void addProperty(String propertyName, IPropertyValue<?> propertyValue);
    void updateProperty(String propertyName, IPropertyValue<?> propertyValue);
    IPropertyValue<?> getProperty(String propertyName);
    HashMap<String, IPropertyValue<?>> getProperties();
    AbstractDateRange getDateRange();
    AbstractTimeRange getTimeRange();
    void removeProperty(String propertyName);
    boolean equals(Object o);
}
