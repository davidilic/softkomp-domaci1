package interfaces;

import date.Date;
import date.DateRange;
import time.Time;
import time.TimeRange;

import java.util.List;
import java.util.Map;

public interface ITermQueryEngine {
    ITerm getTerm(IRoom room, TimeRange timeRange, DateRange dateRange);
    boolean isRoomOccupiedDuringTerm(IRoom room, TimeRange timeRange, DateRange dateRange);
    List<ITerm> getAllOccupiedTerms();
    List<ITerm> getFreeTerms(IRoom room);
    List<ITerm> filterTermsByDateOrTime(IRoom room, Date startDate, Date endDate, Time startTime, Time endTime);
    List<ITerm> filterTermsByProperties(IRoom room, Map<String, IPropertyValue<?>> properties);
}
