package hu.erste.slacct.codetables.integration.routing_table.service.line;

import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import hu.erste.slacct.codetables.integration.util.Item;
import hu.erste.slacct.codetables.integration.util.exception.Error;
import hu.erste.slacct.codetables.integration.util.exception.ParserException;


public class GiroGid implements Item<RoutingTable> {

    static final String CHECK = "[" + NUMBER + SPACE + "]{4}";

    @Override
    public String regex() {
        return "(.{4})" + next().regex();
    }

    @Override
    public Item<RoutingTable> next() {
        return new ViberTandt();
    }

    @Override
    public void setRoutingTableWith(RoutingTable routingTable, String value) throws ParserException {
        check(value);
        routingTable.setGiroGid(trim(value));
    }


    void check(String value) throws ParserException {
        if (!value.matches(CHECK)) {
            throw new ParserException(new Error("GIRO_GID", value));
        }
    }
}
