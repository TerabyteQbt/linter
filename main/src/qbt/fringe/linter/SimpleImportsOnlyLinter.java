package qbt.fringe.linter;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.tuple.Pair;

public class SimpleImportsOnlyLinter implements Linter {
    private static final Pattern NORMAL_IMPORT_PATTERN = Pattern.compile("import ([^ ]*);\n");

    @Override
    public List<Pair<Integer, String>> check(List<String> lines) {
        ImmutableList.Builder<Pair<Integer, String>> b = ImmutableList.builder();
        for(int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            if(line.startsWith("import static ")) {
                b.add(Pair.of(i, "Static import"));
            }
            if(line.startsWith("import ") && line.endsWith(".*;\n")) {
                b.add(Pair.of(i, "Wildcard import"));
            }
            Matcher m = NORMAL_IMPORT_PATTERN.matcher(line);
            if(m.matches()) {
                String clazz = m.group(1);
                String[] segments = clazz.split("\\.");
                for(int j = 0; j < segments.length; ++j) {
                    String segment = segments[j];
                    if(segment.isEmpty()) {
                        b.add(Pair.of(i, "Malformed import (empty segment)"));
                    }
                    else {
                        char c = segment.charAt(0);
                        if(j < segments.length - 1) {
                            if(Character.isUpperCase(c)) {
                                b.add(Pair.of(i, "Malformed import (capital non-final segment " + segment + ")"));
                            }
                        }
                        else {
                            if(!Character.isUpperCase(c)) {
                                b.add(Pair.of(i, "Malformed import (non-capital final segment " + segment + ")"));
                            }
                        }
                    }
                }
            }
        }
        return b.build();
    }
}
