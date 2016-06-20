//   Copyright 2016 Keith Amling
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
//
package qbt.fringe.linter;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.tuple.Pair;

public abstract class AbstractRegexLinter implements Linter {
    private final Pattern pattern;

    public AbstractRegexLinter(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public List<Pair<Integer, String>> check(List<String> lines) {
        ImmutableList.Builder<Pair<Integer, String>> b = ImmutableList.builder();
        for(int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            while(line.endsWith("\n") || line.endsWith("\r")) {
                line = line.substring(0, line.length() - 1);
            }
            if(pattern.matcher(line).matches()) {
                b.add(Pair.of(i, ""));
            }
        }
        return b.build();
    }
}
