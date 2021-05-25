package hu.erste.slacct.codetables.integration.filter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FileNameMatcher {

    public static final String REGEX = "HT([0-9]{4})([0-9]{2}).V([0-9]{2})$";

    private final String fileName;
    private final Matcher matcher;


    public static FileNameMatcher of(String fileName) {
        return new FileNameMatcher(fileName, Pattern.compile(REGEX).matcher(fileName));
    }


    public boolean matches() {
        return matcher.matches();
    }

    public int getVersion() {
        return get(m -> Integer.parseInt(matcher.group(3)));
    }

    public LocalDate getDate() {
        return get(m -> LocalDate.of(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), 1));
    }

    private <R> R get(Function<Matcher, R> func) {
        if (matcher.matches()) {
            return func.apply(matcher);
        } else {
            throw new IllegalStateException("No match found: " + fileName);
        }
    }
}
