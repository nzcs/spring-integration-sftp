package hu.erste.slacct.codetables.integration;

import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTableLoad;
import hu.erste.slacct.codetables.integration.routing_table.repository.RoutingTableLoadRepository;
import hu.erste.slacct.codetables.integration.routing_table.repository.RoutingTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@Component
public class TestRule_HT202001_V13_Error {

    public static final String FILE_NAME = "HT202001.V13";
    public static final String ERROR_FILE_NAME = "HT202001.V13.error";
    public static final String FILE_PATH = "src/test/resources/data/";


    @Autowired
    RoutingTableRepository routingTableRepository;
    @Autowired
    RoutingTableLoadRepository routingTableLoadRepository;


    public void checkDb() {
        assertThat(routingTableLoadRepository.findAll())
                .extracting(
                        RoutingTableLoad::getVersion,
                        RoutingTableLoad::getValidFrom
                )
                .containsExactlyInAnyOrder(
                        tuple(13, LocalDate.parse("2020-01-01"))
                );

        assertThat(routingTableRepository.findAllBy(13, LocalDate.parse("2020-01-01")))
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
                        tuple(10401938, "I", "OKHBHUHB", 24, "K&H Bank Zrt. 193 Budapest Promontor", "I", "10402142", "S", "R")
                );
    }

    public void checkContent(byte[] content) throws IOException {
        assertThat(new String(content, UTF_8).trim())
                .isEqualTo(
                        new String(Files.readAllBytes(Paths.get(FILE_PATH + ERROR_FILE_NAME)), UTF_8).trim()
                );
    }
}
