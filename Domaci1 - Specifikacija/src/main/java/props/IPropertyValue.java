package props;

public interface IPropertyValue<T> {
    boolean isEqual(IPropertyValue<?> other);
    int compareTo(IPropertyValue<?> other);
    boolean matchesQuery(String query);
    String toString();
}