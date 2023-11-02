package room;

import props.IPropertyValue;

import java.util.List;

public interface IRoomCreator {
    void addRoom(String name);
    void addRoom(IRoom room);
    void addRooms(List<IRoom> rooms);
    void deleteRoom(String name);
    void addPropertyToAllRooms(String propertyName, IPropertyValue<?> defaultValue);
    void removePropertyFromAllRooms(String propertyName);
    void updateDefaultValue(String propertyName, IPropertyValue<?> newDefault);
}