package room;

import interfaces.IPropertyValue;
import interfaces.IRoom;
import interfaces.IRoomList;
import interfaces.IRoomQueryEngine;

import java.util.ArrayList;
import java.util.List;

public class RoomQueryEngine implements IRoomQueryEngine {
    private final IRoomList roomList;

    public RoomQueryEngine(IRoomList roomList) {
        this.roomList = roomList;
    }

    public IRoom getRoom(String name) {
        return roomList.getRoom(name);
    }

    public List<IRoom> findRoomsByExactProperty(String propertyName, IPropertyValue<?> value) {
        List<IRoom> filteredRooms = new ArrayList<>();
        for (IRoom room : roomList.getAllRooms()) {
            IPropertyValue<?> property = room.getProperty(propertyName);
            if (property != null && property.equals(value)) {
                filteredRooms.add(room);
            }
        }
        return filteredRooms;
    }

    public List<IRoom> findRoomsByPropertyComparison(String propertyName, IPropertyValue<?> value, int comparisonResult) {
        List<IRoom> filteredRooms = new ArrayList<>();
        for (IRoom room : roomList.getAllRooms()) {
            IPropertyValue<?> property = room.getProperty(propertyName);

            if (property != null && property.compareTo(value) == comparisonResult) {
                filteredRooms.add(room);
            }
        }
        return filteredRooms;
    }

    public List<IRoom> findRoomsByPropertyQuery(String propertyName, String query) {
        List<IRoom> filteredRooms = new ArrayList<>();
        for (IRoom room : roomList.getAllRooms()) {
            IPropertyValue<?> property = room.getProperty(propertyName);
            if (property != null && property.matchesQuery(query)) {
                filteredRooms.add(room);
            }
        }
        return filteredRooms;
    }
}