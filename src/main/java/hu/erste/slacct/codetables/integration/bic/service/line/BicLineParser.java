package hu.erste.slacct.codetables.integration.bic.service.line;

import hu.erste.slacct.codetables.integration.bic.entity.BicInfo;
import hu.erste.slacct.codetables.integration.util.Item;
import hu.erste.slacct.codetables.integration.util.exception.ParserException;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;


public class BicLineParser {

    static final String DELIMITER = "\t";
    static final List<Item<BicInfo>> ITEMS = newArrayList(
            new Bic8(),
            new Bic(),
            new Institution(),
            new City(),
            new IsoCountryCode()
    );

    public static BicInfo parse(String line) throws ParserException {
        ParserException exception = new ParserException();
        BicInfo temp = new BicInfo();

        String[] array = line.split(DELIMITER);
        for (Item<BicInfo> item : ITEMS) {
            try {
                item.setRoutingTableWith(temp, array[item.getGroup()]);
            } catch (ParserException e) {
                exception.addAll(e.getErrors());
            }
        }

        exception.throwException();

        return temp;
    }
}
