package hu.erste.slacct.codetables.integration.util.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RowException extends Exception {

    private final Map<Integer, List<Error>> errors = new HashMap<>();


    public void add(int rowIndex, ParserException e) {
        this.errors.put(rowIndex, e.getErrors());
    }

    public void throwException() throws RowException {
        if (!errors.isEmpty()) {
            throw this;
        }
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }
}
