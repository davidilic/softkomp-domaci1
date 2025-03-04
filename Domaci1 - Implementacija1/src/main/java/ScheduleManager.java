import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class ScheduleManager extends AbstractScheduleManager {

    private final Map<String, Map<String, Object>> rooms = new HashMap<>();
    private final Map<String, List<Map<String, Object>>> terms = new HashMap<>();
    private final Map<String, Object> defaultRoomValues = new HashMap<>();
    private final Map<String, Object> defaultTermValues = new HashMap<>();

    public ScheduleManager() {
        initializeDefaultValues();
    }

    @Override
    protected void initializeDefaultValues() {
        defaultRoomValues.put("capacity", 0);
        defaultRoomValues.put("computerCount", 0);
        defaultRoomValues.put("hasWhiteboard", false);
        defaultRoomValues.put("hasProjector", false);

        defaultTermValues.put("startDate", new Date(0));
        defaultTermValues.put("endDate", new Date(0));
    }

    // Default values
    public void setRoomDefaultPropertyValue(String propertyKey, Object propertyValue) {
        defaultRoomValues.put(propertyKey, propertyValue);
    }

    public Object getRoomDefaultPropertyValue(String propertyKey) {
        return defaultRoomValues.get(propertyKey);
    }

    public void setTermDefaultPropertyValue(String propertyKey, Object propertyValue) {
        defaultTermValues.put(propertyKey, propertyValue);
    }

    public Object getTermDefaultPropertyValue(String propertyKey) {
        return defaultTermValues.get(propertyKey);
    }

    // Room-related Methods

    public void addRoom(String roomId) {
        if (rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " already exists.");
        }

        rooms.put(roomId, new HashMap<>());
        terms.put(roomId, new ArrayList<>());
    }

    public void addRooms(List<String> roomIds) {
        for (String roomId : roomIds) {
            addRoom(roomId);
        }
    }

    public Map<String, Object> getRoom(String roomId) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist.");
        }
        return rooms.get(roomId);
    }

    public void deleteRoom(String roomId) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist.");
        }

        rooms.remove(roomId);
        terms.remove(roomId);
    }

    public void setRoomProperty(String roomId, String propertyKey, Object propertyValue) {

        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist.");
        }

        if (!defaultRoomValues.containsKey(propertyKey)) {
            this.setRoomDefaultPropertyValue(propertyKey, propertyValue);
            System.out.println("Property " + propertyKey + " does not exist yet. The entered value + " + propertyValue + " has been set as default.");
        }

        rooms.get(roomId).put(propertyKey, propertyValue);
    }

    public Object getRoomProperty(String roomId, String propertyKey) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist.");
        }

        if (!defaultRoomValues.containsKey(propertyKey)) {
            throw new IllegalArgumentException("Property " + propertyKey + " does not exist.");
        }

        return rooms.get(roomId).getOrDefault(propertyKey, defaultRoomValues.get(propertyKey));
    }

    public boolean hasRoomProperty(String roomId, String propertyKey) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist.");
        }

        if (!defaultRoomValues.containsKey(propertyKey)) {
            throw new IllegalArgumentException("Property " + propertyKey + " does not exist.");
        }

        return rooms.get(roomId).containsKey(propertyKey) || defaultRoomValues.containsKey(propertyKey);
    }

    public List<String> filterRoomsByProperties(Map<String, Object> criteria) {
        List<String> filteredRooms = new ArrayList<>();

        for (String roomId : rooms.keySet()) {
            boolean roomMatchesCriteria = true;

            for (String propertyKey : criteria.keySet()) {
                if (!hasRoomProperty(roomId, propertyKey) || !getRoomProperty(roomId, propertyKey).equals(criteria.get(propertyKey))) {
                    roomMatchesCriteria = false;
                    break;
                }
            }

            if (roomMatchesCriteria) {
                filteredRooms.add(roomId);
            }
        }

        return filteredRooms;
    }

    public void addTerm(String roomId, Date startDate, Date endDate) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist.");
        }

        if (this.isRoomOccupiedDuringTerm(roomId, startDate, endDate)) {
            throw new IllegalArgumentException("Room with id " + roomId + " is occupied during term.");
        }

        Map<String, Object> termMap = new HashMap<>();
        termMap.put("startDate", startDate);
        termMap.put("endDate", endDate);
        terms.get(roomId).add(termMap);
    }


    public void addTerms(String roomId, List<Map<String, Object>> terms) {
        for (Map<String, Object> term : terms) {
            if (!term.containsKey("startDate") || !term.containsKey("endDate")) {
                throw new IllegalArgumentException("Term must contain startDate and endDate.");
            }

            Date startDate = (Date) term.get("startDate");
            Date endDate = (Date) term.get("endDate");

            this.addTerm(roomId, startDate, endDate);
        }
    }

    public void deleteTerm(String roomId, Date startDate, Date endDate) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist.");
        }

        terms.get(roomId).removeIf(term -> term.get("startDate").equals(startDate) && term.get("endDate").equals(endDate));
    }

    public void moveTerm(String roomId, Date originalStartDate, Date originalEndDate, Date newStartDate, Date newEndDate) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist.");
        }

        boolean termExists = false;
        Map<String, Object> existingTerm = null;
        for (Map<String, Object> term : terms.get(roomId)) {
            if (term.get("startDate").equals(originalStartDate) && term.get("endDate").equals(originalEndDate)) {
                termExists = true;
                existingTerm = term;
                break;
            }
        }

        if (!termExists) {
            throw new IllegalArgumentException("Term with start date " + originalStartDate + " and end date " + originalEndDate + " does not exist for room " + roomId + ".");
        }

        if (this.isRoomOccupiedDuringTerm(roomId, newStartDate, newEndDate)) {
            throw new IllegalArgumentException("Room with id " + roomId + " is occupied during the new term dates.");
        }

        existingTerm.put("startDate", newStartDate);
        existingTerm.put("endDate", newEndDate);
    }


    public void setTermProperty(String roomId, Date startDate, Date endDate, String propertyKey, Object propertyValue) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist.");
        }

        if (!defaultTermValues.containsKey(propertyKey)) {
            this.setTermDefaultPropertyValue(propertyKey, propertyValue);
            System.out.println("Property " + propertyKey + " does not exist yet. The entered value + " + propertyValue + " has been set as default.");
        }

        for (Map<String, Object> term : terms.get(roomId)) {
            if (term.get("startDate").equals(startDate) && term.get("endDate").equals(endDate)) {
                term.put(propertyKey, propertyValue);
            }
        }
    }


    public boolean isRoomOccupiedDuringTerm(String roomId, Date startDate, Date endDate) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist.");
        }

        for (Map<String, Object> term : terms.get(roomId)) {
            Date termStartDate = (Date) term.get("startDate");
            Date termEndDate = (Date) term.get("endDate");

            if (startDate.before(termEndDate) && endDate.after(termStartDate)) {
                return true;
            }
        }

        return false;
    }

    public List<Map<String, Object>> filterTermsByProperties(Map<String, Object> criteria) {
        List<Map<String, Object>> filteredTerms = new ArrayList<>();

        for (String roomId : terms.keySet()) {
            for (Map<String, Object> term : terms.get(roomId)) {
                boolean termMatchesCriteria = true;

                for (String propertyKey : criteria.keySet()) {
                    if (!term.containsKey(propertyKey) || !term.get(propertyKey).equals(criteria.get(propertyKey))) {
                        termMatchesCriteria = false;
                        break;
                    }
                }

                if (termMatchesCriteria) {
                    filteredTerms.add(term);
                }
            }
        }

        return filteredTerms;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> filterTermsByComparison(String propertyKey, Comparable<?> comparisonValue, int comparisonResult) {
        List<Map<String, Object>> filteredTerms = new ArrayList<>();

        for (String roomId : terms.keySet()) {
            for (Map<String, Object> term : terms.get(roomId)) {

                if (!term.containsKey(propertyKey) || !(term.get(propertyKey) instanceof Comparable)) {
                    throw new IllegalArgumentException("Term does not contain property " + propertyKey + " or it is not comparable.");
                }

                Comparable<Object> termValue = (Comparable<Object>) term.get(propertyKey);
                int compareResult = termValue.compareTo(comparisonValue);

                if (compareResult == comparisonResult) {
                    filteredTerms.add(term);
                }
            }
        }

        return filteredTerms;
    }



    public List<Map<String, Object>> getFreeTerms(String roomId, Date startDate, Date endDate) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist.");
        }

        // returns a list of all free terms in a room between startDate and endDate.
        List<Map<String, Object>> freeTerms = new ArrayList<>();

        for (Map<String, Object> term : terms.get(roomId)) {
            Date termStartDate = (Date) term.get("startDate");
            Date termEndDate = (Date) term.get("endDate");

            if (startDate.before(termStartDate) && endDate.after(termEndDate)) {
                freeTerms.add(Map.of("startDate", termStartDate, "endDate", termEndDate));
            } else if (startDate.before(termStartDate) && endDate.after(termStartDate) && endDate.before(termEndDate)) {
                freeTerms.add(Map.of("startDate", termStartDate, "endDate", endDate));
            } else if (startDate.after(termStartDate) && startDate.before(termEndDate) && endDate.after(termEndDate)) {
                freeTerms.add(Map.of("startDate", startDate, "endDate", termEndDate));
            } else if (startDate.after(termStartDate) && endDate.before(termEndDate)) {
                freeTerms.add(Map.of("startDate", startDate, "endDate", endDate));
            }
        }

        return freeTerms;

    }

    // File-related Methods

    public boolean loadScheduleFromFile(String filePath, Date startDate, Date endDate, List<Date> excludedDays) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Assuming the date format in the JSON is "yyyy-MM-dd"
        try (FileReader reader = new FileReader(filePath)) {
            Type scheduleType = new TypeToken<Map<String, List<Map<String, Object>>>>() {}.getType();
            Map<String, List<Map<String, Object>>> loadedSchedule = gson.fromJson(reader, scheduleType);

            for (String roomId : loadedSchedule.keySet()) {
                if (!rooms.containsKey(roomId)) {
                    addRoom(roomId);
                }
                for (Map<String, Object> term : loadedSchedule.get(roomId)) {
                    Date termStartDate = sdf.parse((String) term.get("startDate"));
                    Date termEndDate = sdf.parse((String) term.get("endDate"));

                    if (termStartDate.before(startDate) || termEndDate.after(endDate)) {
                        continue;
                    }

                    if (excludedDays.stream().anyMatch(date -> date.after(termStartDate) && date.before(termEndDate))) {
                        continue;
                    }

                    addTerm(roomId, termStartDate, termEndDate);
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            return false;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean saveScheduleToFile(String filePath) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(terms, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loadRoomsFromFile(String filePath) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

        try (FileReader reader = new FileReader(filePath)) {
            Type roomsType = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> loadedRooms = gson.fromJson(reader, roomsType);

            for (String roomId : loadedRooms.keySet()) {
                if (!rooms.containsKey(roomId)) {
                    addRoom(roomId);
                }
                for (String propertyKey : loadedRooms.get(roomId).keySet()) {
                    setRoomProperty(roomId, propertyKey, loadedRooms.get(roomId).get(propertyKey));
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveRoomsToFile(String filePath) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(rooms, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}