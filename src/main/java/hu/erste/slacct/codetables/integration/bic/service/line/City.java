package hu.erste.slacct.codetables.integration.bic.service.line;

import hu.erste.slacct.codetables.integration.bic.entity.BicInfo;
import hu.erste.slacct.codetables.integration.util.Item;
import hu.erste.slacct.codetables.integration.util.exception.Error;
import hu.erste.slacct.codetables.integration.util.exception.ParserException;


public class City implements Item<BicInfo> {

    static final String CHECK = "[" + ALPHA + NUMBER + SEPARATOR + SPACE + "]*";

    @Override
    public String regex() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Item<BicInfo> next() {
        throw new UnsupportedOperationException();
    }


    @Override
    public int getGroup() {
        return 25;
    }

    @Override
    public void setRoutingTableWith(BicInfo table, String value) throws ParserException {
        check(value);
        table.setCity(trim(value));
    }


    void check(String value) throws ParserException {
        if (!value.matches(CHECK)) {
            throw new ParserException(new Error("CITY", value));
        }
    }
}
