package term;

import date.Date;
import date.DateRange;
import interfaces.*;
import time.Time;
import time.TimeRange;

import java.util.*;
import java.util.stream.Collectors;

public class TermQueryEngine implements ITermQueryEngine {
    private final ITermListMap termListMap;

    public TermQueryEngine(ITermListMap termListMap) {
        this.termListMap = termListMap;
    }

    @Override
    public ITerm getTerm(IRoom room, TimeRange timeRange, DateRange dateRange) {
        ArrayList<ITerm> terms = termListMap.getTerms(room);
        for (ITerm term : terms) {
            if (term.getDateRange().equals(dateRange) && term.getTimeRange().equals(timeRange)) {
                return term;
            }
        }
        return null;
    }

    @Override
    public boolean isRoomOccupiedDuringTerm(IRoom room, TimeRange timeRange, DateRange dateRange) {
        ArrayList<ITerm> terms = termListMap.getTerms(room);
        if (terms == null) {
            return false;
        }
        for (ITerm term : terms) {
            if (term.getTimeRange().overlaps(timeRange) && term.getDateRange().overlaps(dateRange)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<ITerm> getAllOccupiedTerms() {
        return termListMap.getAllTerms();
    }

    @Override
    public List<ITerm> filterTermsByDateOrTime(IRoom room, Date startDate, Date endDate, Time startTime, Time endTime) {
        ArrayList<ITerm> terms = termListMap.getTerms(room);
        if (terms == null) {
            return new ArrayList<>();
        }
        return terms.stream()
                .filter(term -> (startDate == null || term.getDateRange().getStart().compareTo(startDate) >= 0) &&
                        (endDate == null || term.getDateRange().getEnd().compareTo(endDate) <= 0) &&
                        (startTime == null || term.getTimeRange().getStart().compareTo(startTime) >= 0) &&
                        (endTime == null || term.getTimeRange().getEnd().compareTo(endTime) <= 0))
                .collect(Collectors.toList());
    }

    @Override
    public List<ITerm> filterTermsByProperties(IRoom room, Map<String, IPropertyValue<?>> properties) {
        ArrayList<ITerm> terms = termListMap.getTerms(room);
        if (terms == null) {
            return new ArrayList<>();
        }
        return terms.stream()
                .filter(term -> properties.entrySet().stream()
                        .allMatch(entry -> {
                            IPropertyValue<?> termProp = term.getProperty(entry.getKey());
                            return termProp != null && termProp.equals(entry.getValue());
                        }))
                .collect(Collectors.toList());
    }

    @Override
    public List<ITerm> getFreeTerms(IRoom room) {
        ArrayList<ITerm> occupiedTerms = termListMap.getTerms(room);
        if (occupiedTerms == null) {
            return new ArrayList<>();
        }

        occupiedTerms.sort(Comparator.comparing(ITerm::getDateRange).thenComparing(ITerm::getTimeRange));

        List<ITerm> freeTerms = new ArrayList<>();
        ITerm lastOccupied = null;

        for (ITerm current : occupiedTerms) {
            if (lastOccupied == null) {
                lastOccupied = current;
                continue;
            }

            if (!lastOccupied.getDateRange().getEnd().equals(current.getDateRange().getStart()) ||
                    !lastOccupied.getTimeRange().getEnd().equals(current.getTimeRange().getStart())) {
                freeTerms.add(new Term(
                        new DateRange(lastOccupied.getDateRange().getEnd(), current.getDateRange().getStart()),
                        new TimeRange(lastOccupied.getTimeRange().getEnd(), current.getTimeRange().getStart()),
                        new HashMap<>()
                ));
            }

            if (lastOccupied.getDateRange().getEnd().compareTo(current.getDateRange().getEnd()) < 0 ||
                    lastOccupied.getTimeRange().getEnd().compareTo(current.getTimeRange().getEnd()) < 0) {
                lastOccupied = current;
            }
        }

        return freeTerms;
    }


}
