package term;

import date.AbstractDateRange;
import props.IPropertyValue;
import room.IRoom;
import time.AbstractTimeRange;

import java.util.List;

public interface ITermCreator {
    void addTerm(IRoom room, AbstractTimeRange timeRange, AbstractDateRange dateRange);
    void addTerm(IRoom room, ITerm term);
    void addTerms(IRoom room, List<ITerm> terms);
    void deleteTerm(IRoom room, AbstractTimeRange timeRange, AbstractDateRange dateRange);
    void addPropertyToAllTerms(String propertyName, IPropertyValue<?> defaultValue);
    void removePropertyFromAllTerms(String propertyName);
    void updateDefaultValue(String propertyName, IPropertyValue<?> newDefault);
}
