package qbt.fringe.linter;

import java.util.regex.Pattern;

public class ControlSpacingLinter extends AbstractRegexLinter {
    public ControlSpacingLinter() {
        super(Pattern.compile(" *(catch|else if|for|if|switch|synchronized|try|while) +\\(.*"));
    }
}
