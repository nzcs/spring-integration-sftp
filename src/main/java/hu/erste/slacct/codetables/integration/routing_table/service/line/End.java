package hu.erste.slacct.codetables.integration.routing_table.service.line;

import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import hu.erste.slacct.codetables.integration.util.Item;

class End implements Item<RoutingTable> {

    @Override
    public String regex() {
        return "(.*)";
    }

    @Override
    public Item<RoutingTable> next() {
        return null;
    }

    @Override
    public void setRoutingTableWith(RoutingTable routingTable, String value) {
    }
}
