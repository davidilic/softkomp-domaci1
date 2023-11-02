package room;

import java.util.List;

public interface IRoomFileLoader {
    List<IRoom> readRooms(String filePath);
}


