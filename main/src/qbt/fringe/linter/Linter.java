package qbt.fringe.linter;

import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public interface Linter {
    public List<Pair<Integer, String>> check(List<String> lines);
}
