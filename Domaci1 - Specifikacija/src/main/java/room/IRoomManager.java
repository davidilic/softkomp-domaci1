package room;

public interface IRoomManager {
    IRoomCreator getRoomCreator();
    IRoomQueryEngine getRoomQueryEngine();

    IRoomFileLoader getRoomFileLoader();

    IRoomFileSaver getRoomFileSaver();
}
