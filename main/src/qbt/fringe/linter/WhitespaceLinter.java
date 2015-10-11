package qbt.fringe.linter;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class WhitespaceLinter implements Linter {
    @Override
    public List<Pair<Integer, String>> check(List<String> lines) {
        ImmutableList.Builder<Pair<Integer, String>> b = ImmutableList.builder();
        for(int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            if(line.endsWith(" \n")) {
                b.add(Pair.of(i, "Trailing whitespace"));
            }
            if(line.contains("\t")) {
                b.add(Pair.of(i, "Hard tab"));
            }
        }
        return b.build();
    }
}
