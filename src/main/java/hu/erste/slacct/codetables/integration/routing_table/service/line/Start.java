package hu.erste.slacct.codetables.integration.routing_table.service.line;

import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import hu.erste.slacct.codetables.integration.util.Item;

public class Start implements Item<RoutingTable> {

    @Override
    public String regex() {
        return next().regex();
    }

    @Override
    public Item<RoutingTable> next() {
        return new GiroCode();
    }

    @Override
    public void setRoutingTableWith(RoutingTable routingTable, String value) {
    }
}
