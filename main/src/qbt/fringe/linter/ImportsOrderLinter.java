package qbt.fringe.linter;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class ImportsOrderLinter implements Linter {
    enum State {
        BEFORE,
        IN,
        AFTER;
    }

    @Override
    public List<Pair<Integer, String>> check(List<String> lines) {
        State s = State.BEFORE;
        String lastImportLine = null;
        ImmutableList.Builder<Pair<Integer, String>> b = ImmutableList.builder();
        for(int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            switch(s) {
                case BEFORE:
                    if(isImport(line)) {
                        lastImportLine = line;
                        s = State.IN;
                    }
                    break;

                case IN:
                    if(isImport(line)) {
                        if(line.compareTo(lastImportLine) <= 0) {
                            b.add(Pair.of(i, "Non-ascending import"));
                        }
                        lastImportLine = line;
                    }
                    else {
                        s = State.AFTER;
                    }
                    break;

                case AFTER:
                    if(isImport(line)) {
                        b.add(Pair.of(i, "Multiple import blocks"));
                    }
                    break;
            }
        }
        return b.build();
    }

    private static boolean isImport(String line) {
        return line.startsWith("import ") && line.endsWith(";\n");
    }
}
