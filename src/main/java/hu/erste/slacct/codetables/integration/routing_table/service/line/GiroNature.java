package hu.erste.slacct.codetables.integration.routing_table.service.line;

import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import hu.erste.slacct.codetables.integration.util.Item;
import hu.erste.slacct.codetables.integration.util.exception.Error;
import hu.erste.slacct.codetables.integration.util.exception.ParserException;


class GiroNature implements Item<RoutingTable> {

    static final String CHECK = "[PI ]{1}";

    @Override
    public String regex() {
        return "(.{1})" + next().regex();
    }

    @Override
    public Item<RoutingTable> next() {
        return new GiroDirect();
    }

    @Override
    public void setRoutingTableWith(RoutingTable routingTable, String value) throws ParserException {
        check(value);
        routingTable.setGiroNature(trim(value));
    }


    void check(String value) throws ParserException {
        if (!value.matches(CHECK)) {
            throw new ParserException(new Error("GIRO_NATURE", value));
        }
    }
}
