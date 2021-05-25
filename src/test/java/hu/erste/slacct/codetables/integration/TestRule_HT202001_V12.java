package hu.erste.slacct.codetables.integration;

import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTableLoad;
import hu.erste.slacct.codetables.integration.routing_table.repository.RoutingTableLoadRepository;
import hu.erste.slacct.codetables.integration.routing_table.repository.RoutingTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@Component
public class TestRule_HT202001_V12 {

    public static final String FILE_NAME = "HT202001.V12";
    public static final String FILE_PATH = "src/test/resources/data/";


    @Autowired
    RoutingTableRepository routingTableRepository;
    @Autowired
    RoutingTableLoadRepository routingTableLoadRepository;


    public void check() {
        assertThat(routingTableLoadRepository.findAll())
                .extracting(
                        RoutingTableLoad::getVersion,
                        RoutingTableLoad::getValidFrom
                )
                .containsExactlyInAnyOrder(
                        tuple(12, LocalDate.parse("2020-01-01"))
                );

        assertThat(routingTableRepository.findAllBy(12, LocalDate.parse("2020-01-01")))
                .extracting(
                        RoutingTable::getGiroCode,
                        RoutingTable::getViberSign,
                        RoutingTable::getViberBic,
                        RoutingTable::getGiroAccountLength,
                        RoutingTable::getGiroBank,
                        RoutingTable::getGiroNature,
                        RoutingTable::getGiroDirect,
                        RoutingTable::getViberSend,
                        RoutingTable::getViberReceive
                )
                .containsExactlyInAnyOrder(
                        tuple(10003004, "I", "HUSTHUHB", 8, "Magyar Államkincstár Központ", "I", "10032000", "S", "R"),
                        tuple(10023002, "I", "HUSTHUHB", 8, "Magyar Államkincstár. Budapest", "I", "10032000", null, "R")
                );
    }
}
