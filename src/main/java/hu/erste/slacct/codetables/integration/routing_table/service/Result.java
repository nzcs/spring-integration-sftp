package hu.erste.slacct.codetables.integration.routing_table.service;

import hu.erste.slacct.codetables.integration.util.exception.Error;
import hu.erste.slacct.codetables.integration.util.exception.ParserException;
import lombok.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Value
public class Result<T> {

    List<T> rows = new ArrayList<>();
    Map<Integer, List<Error>> errors = new HashMap<>();


    public void add(T row) {
        rows.add(row);
    }

    public void add(int rowIndex, ParserException e) {
        errors.put(rowIndex, e.getErrors());
    }
}
