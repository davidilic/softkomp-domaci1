package interfaces;

import java.util.List;

public interface IRoomQueryEngine {
    IRoom getRoom(String name);
    List<IRoom> findRoomsByExactProperty(String propertyName, IPropertyValue<?> value);
    List<IRoom> findRoomsByPropertyComparison(String propertyName, IPropertyValue<?> value, int comparisonResult);
    List<IRoom> findRoomsByPropertyQuery(String propertyName, String query);
}