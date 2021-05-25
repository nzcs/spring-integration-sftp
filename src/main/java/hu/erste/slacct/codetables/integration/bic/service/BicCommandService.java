package hu.erste.slacct.codetables.integration.bic.service;

import hu.erste.slacct.codetables.integration.bic.entity.BicInfo;
import hu.erste.slacct.codetables.integration.bic.entity.BicInfoLoad;
import hu.erste.slacct.codetables.integration.bic.repository.BicInfoLoadRepository;
import hu.erste.slacct.codetables.integration.bic.repository.BicInfoRepository;
import hu.erste.slacct.codetables.integration.bic.service.line.BicLineParser;
import hu.erste.slacct.codetables.integration.util.exception.ParserException;
import hu.erste.slacct.codetables.integration.util.exception.RowException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


@Service
@AllArgsConstructor
@Slf4j
class BicCommandService {

    static final int N = 5000;

    EntityManager em;
    BicInfoLoadRepository loadRepository;
    BicInfoRepository repository;


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RowException.class)
    public void saveLines(int version, LocalDate date, Stream<String> lines) throws RowException {
        BicInfoLoad bicInfoLoad = loadRepository.save(
                new BicInfoLoad().setVersion(0).setLoadTime(date)
        );

        List<BicInfo> rows = new ArrayList<>();
        RowException rowException = new RowException();
        AtomicInteger i = new AtomicInteger(0);

        lines.forEach(line -> {
            if (i.get() > 0) {
                try {
                    rows.add(
                            BicLineParser.parse(line)
                                    .setBicInfoLoad(bicInfoLoad)
                                    .setMonth(date.withDayOfMonth(1))
                    );
                    if (rows.size() == N) {
                        if (rowException.isEmpty()) {
                            repository.saveAll(rows);
                            em.flush();
                            em.clear();
                        }
                        rows.clear();
                        System.out.println(i.get());
                    }
                } catch (ParserException e) {
                    rowException.add(i.get(), e);
                }
            }
            i.incrementAndGet();
        });

        repository.saveAll(rows);

        rowException.throwException();

        bicInfoLoad.setVersion(version);
    }
}
