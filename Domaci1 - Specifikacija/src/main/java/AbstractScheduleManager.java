import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractScheduleManager {

    protected final Map<String, Map<String, Object>> rooms = new HashMap<>();
    protected final Map<String, List<Map<String, Object>>> terms = new HashMap<>();
    protected final Map<String, Object> defaultRoomValues = new HashMap<>();
    protected final Map<String, Object> defaultTermValues = new HashMap<>();

    public AbstractScheduleManager() {
        initializeDefaultValues();
    }

    protected abstract void initializeDefaultValues();

    public abstract void setRoomDefaultPropertyValue(String propertyKey, Object propertyValue);
    public abstract Object getRoomDefaultPropertyValue(String propertyKey);
    public abstract void setTermDefaultPropertyValue(String propertyKey, Object propertyValue);
    public abstract Object getTermDefaultPropertyValue(String propertyKey);

    // Room-related Methods
    public abstract void addRoom(String roomId);
    public abstract void addRooms(List<String> roomIds);
    public abstract Map<String, Object> getRoom(String roomId);
    public abstract void deleteRoom(String roomId);
    public abstract void setRoomProperty(String roomId, String propertyKey, Object propertyValue);
    public abstract Object getRoomProperty(String roomId, String propertyKey);
    public abstract boolean hasRoomProperty(String roomId, String propertyKey);
    public abstract List<String> filterRoomsByProperties(Map<String, Object> criteria);

    // Term-related Methods
    public abstract void addTerm(String roomId, Date startDate, Date endDate);
    public abstract void addTerms(String roomId, List<Map<String, Object>> terms);
    public abstract void deleteTerm(String roomId, Date startDate, Date endDate);
    public abstract void moveTerm(String roomId, Date originalStartDate, Date originalEndDate, Date newStartDate, Date newEndDate);
    public abstract void setTermProperty(String roomId, Date startDate, Date endDate, String propertyKey, Object propertyValue);
    public abstract boolean isRoomOccupiedDuringTerm(String roomId, Date startDate, Date endDate);
    public abstract List<Map<String, Object>> filterTermsByProperties(Map<String, Object> criteria);
    public abstract List<Map<String, Object>> filterTermsByComparison(String propertyKey, Comparable<?> comparisonValue, int comparisonResult);
    public abstract List<Map<String, Object>> getFreeTerms(String roomId, Date startDate, Date endDate);

    // File-related Methods
    public abstract boolean loadScheduleFromFile(String filePath, Date startDate, Date endDate, List<Date> excludedDays);
    public abstract boolean saveScheduleToFile(String filePath);
    public abstract boolean loadRoomsFromFile(String filePath);
    public abstract boolean saveRoomsToFile(String filePath);
}
