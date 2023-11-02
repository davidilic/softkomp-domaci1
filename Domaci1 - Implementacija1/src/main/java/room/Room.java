package room;

import interfaces.IPropertyValue;
import interfaces.IRoom;

import java.util.HashMap;

public class Room implements IRoom {
    private final String name;
    private final HashMap<String, IPropertyValue<?>> properties;

    public Room(String name, HashMap<String, IPropertyValue<?>> properties) {
        this.name = name;
        this.properties = properties;
    }

    @Override
    public void removeProperty(String propertyName) {
        properties.remove(propertyName);
    }

    @Override
    public void setProperty(String propertyName, IPropertyValue<?> propertyValue) {
        properties.put(propertyName, propertyValue);
    }

    @Override
    public IPropertyValue<?> getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    @Override
    public boolean hasProperty(String propertyName) {
        return properties.containsKey(propertyName);
    }

    @Override
    public String getName() {
        return name;
    }

}
