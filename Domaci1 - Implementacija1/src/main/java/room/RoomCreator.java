package room;

import interfaces.IPropertyValue;
import interfaces.IRoom;
import interfaces.IRoomCreator;
import interfaces.IRoomList;

import java.util.HashMap;
import java.util.List;

public class RoomCreator implements IRoomCreator {
    private final IRoomList roomList;
    private final HashMap<String, IPropertyValue<?>> defaultProperties = new HashMap<>();

    public RoomCreator(IRoomList roomList) {
        this.roomList = roomList;
    }

    public void addRoom(String name) {
        roomList.addRoom(new Room(name, new HashMap<>(this.defaultProperties)));
    }

    public void addRoom(IRoom room) {

        if(roomList.getRoom(room.getName()) != null) {
            return;
        }

        for (String propertyName : defaultProperties.keySet()) {
            if (!room.hasProperty(propertyName)) {
                room.setProperty(propertyName, defaultProperties.get(propertyName));
            }
        }

        roomList.addRoom(room);
    }

    public void addRooms(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            this.addRoom(room);
        }
    }

    public void deleteRoom(String name) {
        roomList.deleteRoom(name);
    }

    public void addPropertyToAllRooms(String propertyName, IPropertyValue<?> defaultValue) {
        defaultProperties.put(propertyName, defaultValue);
        for (IRoom room : roomList.getAllRooms()) {
            room.setProperty(propertyName, defaultValue);
        }
    }

    public void removePropertyFromAllRooms(String propertyName) {
        defaultProperties.remove(propertyName);
        for (IRoom room : roomList.getAllRooms()) {
            room.removeProperty(propertyName);
        }
    }

    public void updateDefaultValue(String propertyName, IPropertyValue<?> newDefault) {
        defaultProperties.put(propertyName, newDefault);
    }
}