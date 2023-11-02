package interfaces;

import java.util.List;

public interface IRoomList {
    void addRoom(IRoom room);
    void deleteRoom(String name);
    IRoom getRoom(String name);
    List<IRoom> getAllRooms();
}