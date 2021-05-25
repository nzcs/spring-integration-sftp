package hu.erste.slacct.codetables.integration.routing_table.service.line;

import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import hu.erste.slacct.codetables.integration.util.Item;
import hu.erste.slacct.codetables.integration.util.exception.ParserException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LineParser {


    public static RoutingTable parse(String line) throws ParserException {
        RoutingTable rt = new RoutingTable();
        ParserException exception = new ParserException();
        Item<RoutingTable> item = new Start();

        Matcher matcher = Pattern.compile(item.regex()).matcher(line);
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                try {
                    item.setRoutingTableWith(rt, matcher.group(i));
                } catch (ParserException e) {
                    exception.addAll(e.getErrors());
                }
                item = item.next();
            }
        }

        exception.throwException();

        return rt;
    }
}
