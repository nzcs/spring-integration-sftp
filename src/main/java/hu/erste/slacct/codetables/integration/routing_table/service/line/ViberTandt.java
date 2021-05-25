package hu.erste.slacct.codetables.integration.routing_table.service.line;

import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import hu.erste.slacct.codetables.integration.util.Item;
import hu.erste.slacct.codetables.integration.util.exception.Error;
import hu.erste.slacct.codetables.integration.util.exception.ParserException;


public class ViberTandt implements Item<RoutingTable> {

    static final String CHECK = "[" + ALPHA + NUMBER + SPACE + "]{11}";

    @Override
    public String regex() {
        return "(.{11})" + next().regex();
    }

    @Override
    public Item<RoutingTable> next() {
        return new ViberSend();
    }

    @Override
    public void setRoutingTableWith(RoutingTable routingTable, String value) throws ParserException {
        check(value);
        routingTable.setViberTandt(trim(value));
    }


    void check(String value) throws ParserException {
        if (!value.matches(CHECK)) {
            throw new ParserException(new Error("VIBER_TANDT", value));
        }
    }
}
