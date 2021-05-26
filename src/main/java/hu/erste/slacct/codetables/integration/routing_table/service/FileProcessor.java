package hu.erste.slacct.codetables.integration.routing_table.service;

import hu.erste.slacct.codetables.integration.filter.FileNameMatcher;
import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTable;
import hu.erste.slacct.codetables.integration.routing_table.entity.RoutingTableLoad;
import hu.erste.slacct.codetables.integration.routing_table.repository.RoutingTableLoadRepository;
import hu.erste.slacct.codetables.integration.routing_table.repository.RoutingTableRepository;
import hu.erste.slacct.codetables.integration.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@AllArgsConstructor
@Slf4j
public class FileProcessor {

    RoutingTableLoadRepository routingTableLoadRepository;
    RoutingTableRepository routingTableRepository;


    public File process(File file, Charset charset) {
        log.debug("process: " + file);

        Path path = Paths.get(file.toURI());

        try {

            FileNameMatcher matcher = FileNameMatcher.of(file.getName());
            RoutingTableLoad routingTableLoad = routingTableLoadRepository.save(
                    new RoutingTableLoad()
                            .setVersion(matcher.getVersion())
                            .setValidFrom(matcher.getDate())
            );


            Stream<String> lines = Files.lines(path, charset);

            Result<RoutingTable> result = Parser.parse(lines);
            routingTableRepository.saveAll(
                    result.getRows().stream()
                            .map(t -> t.setRoutingTableLoad(routingTableLoad))
                            .collect(Collectors.toList())
            );

            return result.getErrors().isEmpty()
                    ? null
                    : FileUtil.of(path.toFile().getParent(), path.toFile().getName() + ".error")
                    .withContent(result.getErrors());

        } catch (Throwable t) {
            log.error("File processing error", t);
            try {
                Files.deleteIfExists(path);
            } catch (IOException ignored) {
            }
        }
        return null;
    }
}
