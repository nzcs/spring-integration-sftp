package hu.erste.slacct.codetables.integration.util;

import hu.erste.slacct.codetables.integration.util.exception.ParserException;


public interface Item<T> {

    String ALPHA = "A-zÀ-ÿŐőŰű";
    String NUMBER = "0-9";
    String SEPARATOR = ".,:;+&$()'/\\-";
    String SPACE = "\\h ";

    String regex();

    default int getGroup() {
        throw new UnsupportedOperationException();
    }

    Item<T> next();

    void setRoutingTableWith(T table, String value) throws ParserException;

    default String trim(String value) {
        return value.replaceAll("(^\\h*)|(\\h*$)", "");
    }
}
