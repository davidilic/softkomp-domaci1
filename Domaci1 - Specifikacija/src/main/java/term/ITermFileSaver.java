package term;

import java.util.List;
import java.util.Map;

public interface ITermFileSaver {
    void saveTerms(String room, Map<String, List<ITerm>> roomTermMapper, String filePath);
}
