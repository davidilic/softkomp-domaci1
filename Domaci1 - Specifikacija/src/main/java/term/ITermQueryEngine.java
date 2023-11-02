package term;

import date.AbstractDate;
import date.AbstractDateRange;
import props.IPropertyValue;
import room.IRoom;
import time.AbstractTime;
import time.AbstractTimeRange;

import java.util.List;
import java.util.Map;

public interface ITermQueryEngine {
    ITerm getTerm(IRoom room, AbstractTimeRange timeRange, AbstractDateRange dateRange);
    boolean isRoomOccupiedDuringTerm(IRoom room, AbstractTimeRange timeRange, AbstractDateRange dateRange);
    List<ITerm> getAllOccupiedTerms();
    List<ITerm> getFreeTerms(IRoom room);
    List<ITerm> filterTermsByDateOrTime(IRoom room, AbstractDate startDate, AbstractDate endDate, AbstractTime startTime, AbstractTime endTime);
    List<ITerm> filterTermsByProperties(IRoom room, Map<String, IPropertyValue<?>> properties);
}
