package term;

import date.AbstractDate;

import java.util.List;
import java.util.Map;

public interface ITermFileLoader {
    Map<String, List<ITerm>> readTerms(String filePath, AbstractDate startDate, AbstractDate endDate, List<AbstractDate> excludedDays);
}
