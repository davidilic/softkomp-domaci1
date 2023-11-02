package interfaces;

import java.util.ArrayList;

public interface ITermListMap {
    void addTerm(IRoom room, ITerm term);
    void deleteTerm(IRoom room, ITerm term);
    ArrayList<ITerm> getTerms(IRoom room);
    ArrayList<ITerm> getAllTerms();
    ArrayList<String> getAllRoomNames();
}
