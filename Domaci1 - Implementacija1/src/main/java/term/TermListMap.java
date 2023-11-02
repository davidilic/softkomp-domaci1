package term;

import interfaces.IRoom;
import interfaces.ITerm;
import interfaces.ITermListMap;

import java.util.ArrayList;
import java.util.HashMap;

public class TermListMap implements ITermListMap {
    private final HashMap<String, ArrayList<ITerm>> roomTermsMapper = new HashMap<>();

    @Override
    public void addTerm(IRoom room, ITerm term) {
        String roomName = room.getName();
        roomTermsMapper.computeIfAbsent(roomName, k -> new ArrayList<>()).add(term);
    }

    @Override
    public void deleteTerm(IRoom room, ITerm term) {
        String roomName = room.getName();
        ArrayList<ITerm> terms = roomTermsMapper.get(roomName);
        if (terms != null) {
            terms.remove(term);
        }
    }

    @Override
    public ArrayList<ITerm> getTerms(IRoom room) {
        String roomName = room.getName();
        return roomTermsMapper.getOrDefault(roomName, new ArrayList<>());
    }

    @Override
    public ArrayList<ITerm> getAllTerms() {
        ArrayList<ITerm> allTerms = new ArrayList<>();
        roomTermsMapper.values().forEach(allTerms::addAll);
        return allTerms;
    }

    @Override
    public ArrayList<String> getAllRoomNames() {
        return new ArrayList<>(roomTermsMapper.keySet());
    }
}
