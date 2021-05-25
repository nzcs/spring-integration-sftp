package hu.erste.slacct.codetables.integration.routing_table.service;

import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import hu.erste.slacct.codetables.integration.routing_table.service.line.LineParser;
import hu.erste.slacct.codetables.integration.util.exception.ParserException;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


public class Parser {

    public static Result<RoutingTable> parse(Stream<String> lines) {
        Result<RoutingTable> result = new Result<>();

        AtomicInteger i = new AtomicInteger(1);
        lines.forEach(line -> {
            try {
                result.add(LineParser.parse(line));
            } catch (ParserException e) {
                result.add(i.get(), e);
            }
            i.incrementAndGet();
        });

        return result;
    }
}
