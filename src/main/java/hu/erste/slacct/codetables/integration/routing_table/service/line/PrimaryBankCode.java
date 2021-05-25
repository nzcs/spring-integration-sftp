package hu.erste.slacct.codetables.integration.routing_table.service.line;

import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import hu.erste.slacct.codetables.integration.util.Item;
import hu.erste.slacct.codetables.integration.util.exception.Error;
import hu.erste.slacct.codetables.integration.util.exception.ParserException;

import static java.lang.Integer.parseInt;


class PrimaryBankCode implements Item<RoutingTable> {

    static final String CHECK = "[" + NUMBER + "]{3}";

    @Override
    public String regex() {
        return "(.{3})" + next().regex();
    }

    @Override
    public Item<RoutingTable> next() {
        return new End();
    }

    @Override
    public void setRoutingTableWith(RoutingTable routingTable, String value) throws ParserException {
        check(value);
        routingTable.setPrimaryBankCode(parseInt(value));
    }


    void check(String value) throws ParserException {
        if (!value.matches(CHECK)) {
            throw new ParserException(new Error("PRIMARY_BANK_CODE", value));
        }
    }
}
