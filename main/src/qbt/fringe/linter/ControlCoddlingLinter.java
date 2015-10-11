package qbt.fringe.linter;

import java.util.regex.Pattern;

public class ControlCoddlingLinter extends AbstractRegexLinter {
    public ControlCoddlingLinter() {
        super(Pattern.compile(" *} *(catch[ (]|else |else if[ (]|finally).*"));
    }
}
