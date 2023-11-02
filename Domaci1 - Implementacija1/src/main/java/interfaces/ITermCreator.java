package interfaces;

import date.DateRange;
import time.TimeRange;

import java.util.List;

public interface ITermCreator {
    void addTerm(IRoom room, TimeRange timeRange, DateRange dateRange);
    void addTerm(IRoom room, ITerm term);
    void addTerms(IRoom room, List<ITerm> terms);
    void deleteTerm(IRoom room, TimeRange timeRange, DateRange dateRange);
    void addPropertyToAllTerms(String propertyName, IPropertyValue<?> defaultValue);
    void removePropertyFromAllTerms(String propertyName);
    void updateDefaultValue(String propertyName, IPropertyValue<?> newDefault);
}
