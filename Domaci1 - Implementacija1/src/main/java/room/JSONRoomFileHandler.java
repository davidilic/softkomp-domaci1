package room;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import interfaces.IRoom;
import interfaces.IRoomFileLoader;
import interfaces.IRoomFileSaver;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONRoomFileHandler implements IRoomFileLoader, IRoomFileSaver {
    private final Gson gson = new Gson();

    @Override
    public List<IRoom> readRooms(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, new TypeToken<List<IRoom>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveRooms(List<IRoom> rooms, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(rooms, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}