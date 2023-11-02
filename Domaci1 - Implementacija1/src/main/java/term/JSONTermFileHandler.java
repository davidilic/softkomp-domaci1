package term;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import date.Date;
import date.DateRange;
import interfaces.ITerm;
import interfaces.ITermFileLoader;
import interfaces.ITermFileSaver;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONTermFileHandler implements ITermFileLoader, ITermFileSaver {
    private final Gson gson = new Gson();

    @Override
    public Map<String, List<ITerm>> readTerms(String filePath, Date startDate, Date endDate, List<Date> excludedDays) {
        Map<String, List<ITerm>> roomTermMap = new HashMap<>();
        try (FileReader reader = new FileReader(filePath)) {
            roomTermMap = gson.fromJson(reader, new TypeToken<Map<String, List<ITerm>>>(){}.getType());

            for (Map.Entry<String, List<ITerm>> entry : roomTermMap.entrySet()) {
                List<ITerm> filteredTerms = new ArrayList<>();
                for (ITerm term : entry.getValue()) {
                    DateRange dateRange = term.getDateRange();
                    if (dateRange.getStart().compareTo(startDate) >= 0 && dateRange.getEnd().compareTo(endDate) <= 0) {
                        if (excludedDays == null || !excludedDays.contains(dateRange.getStart())) {
                            filteredTerms.add(term);
                        }
                    }
                }
                entry.setValue(filteredTerms);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return roomTermMap;
    }

    @Override
    public void saveTerms(String room, Map<String, List<ITerm>> roomTermMap, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Serialize the roomTermMap to JSON
            gson.toJson(roomTermMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
