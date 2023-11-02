package interfaces;

import date.Date;

import java.util.List;
import java.util.Map;

public interface ITermFileLoader {
    Map<String, List<ITerm>> readTerms(String filePath, Date startDate, Date endDate, List<Date> excludedDays);
}
