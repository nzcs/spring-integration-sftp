package hu.erste.slacct.codetables.integration.util.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class ParserException extends Exception {

    final List<Error> errors = new ArrayList<>();

    public ParserException(Error error) {
        this.errors.add(error);
    }


    public void addAll(List<Error> errors) {
        this.errors.addAll(errors);
    }


    @Override
    public String getMessage() {
        return errors.toString();
    }

    public void throwException() throws ParserException {
        if (!errors.isEmpty()) {
            throw this;
        }
    }
}
