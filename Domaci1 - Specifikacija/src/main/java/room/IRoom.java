package room;

import props.IPropertyValue;

public interface IRoom {
    String getName();
    void removeProperty(String propertyName);
    void setProperty(String propertyName, IPropertyValue<?> propertyValue);
    boolean hasProperty(String propertyName);
    IPropertyValue<?> getProperty(String propertyName);
}