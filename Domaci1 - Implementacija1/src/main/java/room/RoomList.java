package room;

import interfaces.IRoom;
import interfaces.IRoomList;

import java.util.ArrayList;

public class RoomList implements IRoomList {
    private final ArrayList<IRoom> rooms = new ArrayList<>();

    @Override
    public void addRoom(IRoom room) {

        for (IRoom r : rooms) {
            if (r.getName().equals(room.getName())) {
                return;
            }
        }

        rooms.add(room);
    }

    @Override
    public void deleteRoom(String name) {
        rooms.removeIf(room -> room.getName().equals(name));
    }

    @Override
    public IRoom getRoom(String name) {
        for (IRoom room : rooms) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    @Override
    public ArrayList<IRoom> getAllRooms() {
        return rooms;
    }
}