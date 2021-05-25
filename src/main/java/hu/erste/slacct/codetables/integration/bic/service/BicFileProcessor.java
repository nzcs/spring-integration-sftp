package hu.erste.slacct.codetables.integration.bic.service;

import com.google.common.base.Stopwatch;
import hu.erste.slacct.codetables.integration.util.FileUtil;
import hu.erste.slacct.codetables.integration.util.exception.RowException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.google.common.base.Stopwatch.createStarted;


@Service
@AllArgsConstructor
@Slf4j
public class BicFileProcessor {

    BicCommandService bicCommandService;


    public File process(File file, Charset charset) {
        Stopwatch sw = createStarted();

        Path path = Paths.get(file.toURI());

        Integer version = getVersion(file.getName());
        LocalDate date = getDate(file.getName());
        if (version == null || date == null) {
            return null;
        }

        try {
            Stream<String> lines = Files.lines(path, charset);

            bicCommandService.saveLines(version, date, lines);

        } catch (RowException e) {

            try {
                return FileUtil.of(path.toFile().getParent(), path.toFile().getName() + ".error")
                        .withContent(e.getErrors());
            } catch (IOException ignored) {
            }

        } catch (Throwable t) {
            log.error("Bic file processing error", t);
//            try {
//                Files.deleteIfExists(path);
//            } catch (IOException ignored) {
//            }
        } finally {
            System.out.println(sw);
        }

        return null;
    }

    private static Integer getVersion(String str) {
        Matcher matcher = Pattern.compile("_V([\\d]+)_").matcher(str);
        if (matcher.find() && matcher.groupCount() == 1) {
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    private static LocalDate getDate(String str) {
        Matcher matcher = Pattern.compile("_([\\d]{8})_").matcher(str);
        if (matcher.find() && matcher.groupCount() == 1) {
            return LocalDate.parse(matcher.group(1), DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        return null;
    }
}
