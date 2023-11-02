package term;

import date.DateRange;
import interfaces.*;
import room.Room;
import time.TimeRange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TermCreator implements ITermCreator {
    private final ITermListMap termListMap;
    private final HashMap<String, IPropertyValue<?>> defaultProperties;

    public TermCreator(ITermListMap termListMap, HashMap<String, IPropertyValue<?>> defaultProperties) {
        this.termListMap = termListMap;
        this.defaultProperties = defaultProperties;
    }

    @Override
    public void addTerm(IRoom room, TimeRange timeRange, DateRange dateRange) {
        HashMap<String, IPropertyValue<?>> properties = new HashMap<>(defaultProperties);
        termListMap.addTerm(room, new Term(dateRange, timeRange, properties));
    }

    public void addTerm(IRoom room, ITerm term){

        if(termListMap.getAllTerms().contains(term)){
            return;
        }

        for(String propertyName : defaultProperties.keySet()){
            if(!term.getProperties().containsKey(propertyName)){
                term.addProperty(propertyName, defaultProperties.get(propertyName));
            }
        }

        termListMap.addTerm(room, term);
    }

    public void addTerms(IRoom room, List<ITerm> terms){
        for(ITerm term : terms){
            addTerm(room, term);
        }
    }

    public void addTerms(Map<String, List<ITerm>> roomNameTermMap){
        for(Map.Entry<String, List<ITerm>> entry : roomNameTermMap.entrySet()){
            String roomName = entry.getKey();
            List<ITerm> terms = entry.getValue();
            this.addTerms(new Room(roomName, new HashMap<>()), terms);
        }
    }

    @Override
    public void deleteTerm(IRoom room, TimeRange timeRange, DateRange dateRange) {
        ITerm term = new Term(dateRange, timeRange, new HashMap<>());
        termListMap.deleteTerm(room, term);
    }

    @Override
    public void addPropertyToAllTerms(String propertyName, IPropertyValue<?> defaultValue) {
        defaultProperties.put(propertyName, defaultValue);
        for (ITerm term : termListMap.getAllTerms()) {
            term.addProperty(propertyName, defaultValue);
        }
    }

    @Override
    public void removePropertyFromAllTerms(String propertyName) {
        defaultProperties.remove(propertyName);
        for (ITerm term : termListMap.getAllTerms()) {
            term.removeProperty(propertyName);
        }
    }

    @Override
    public void updateDefaultValue(String propertyName, IPropertyValue<?> newDefault) {
        defaultProperties.put(propertyName, newDefault);
        for (ITerm term : termListMap.getAllTerms()) {
            term.updateProperty(propertyName, newDefault);
        }
    }
}
