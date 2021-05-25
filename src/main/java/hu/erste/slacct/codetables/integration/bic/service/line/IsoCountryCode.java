package hu.erste.slacct.codetables.integration.bic.service.line;

import hu.erste.slacct.codetables.integration.bic.entity.BicInfo;
import hu.erste.slacct.codetables.integration.util.Item;
import hu.erste.slacct.codetables.integration.util.exception.Error;
import hu.erste.slacct.codetables.integration.util.exception.ParserException;


public class IsoCountryCode implements Item<BicInfo> {

    static final String CHECK = "[" + ALPHA + "]{2}";

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
        return 29;
    }

    @Override
    public void setRoutingTableWith(BicInfo table, String value) throws ParserException {
        check(value);
        table.setIsoCountryCode(trim(value));
    }


    void check(String value) throws ParserException {
        if (!value.matches(CHECK)) {
            throw new ParserException(new Error("ISO_COUNTRY_CODE", value));
        }
    }
}
