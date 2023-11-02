package room;

import interfaces.IRoomFileLoader;
import interfaces.IRoomFileSaver;
import interfaces.IRoomManager;

public class RoomManager implements IRoomManager {
    private final RoomCreator roomCreator;
    private final RoomQueryEngine roomQueryEngine;
    private final IRoomFileLoader roomFileLoader;
    private final IRoomFileSaver roomFileSaver;

    public RoomManager(IRoomFileLoader roomFileLoader, IRoomFileSaver roomFileSaver) {
        RoomList roomList = new RoomList();
        this.roomCreator = new RoomCreator(roomList);
        this.roomQueryEngine = new RoomQueryEngine(roomList);
        this.roomFileLoader = roomFileLoader;
        this.roomFileSaver = roomFileSaver;
    }

    @Override
    public RoomCreator getRoomCreator() {
        return roomCreator;
    }

    @Override
    public RoomQueryEngine getRoomQueryEngine() {
        return roomQueryEngine;
    }

    @Override
    public IRoomFileLoader getRoomFileLoader() { return roomFileLoader; }

    @Override
    public IRoomFileSaver getRoomFileSaver() { return roomFileSaver; }


}