package hu.erste.slacct.codetables.integration.routing_table.service.line;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RTest {


    @Test
    public void test() throws InterruptedException {
        String str = "A\tBD000000DAMC\tFB\tBD000000DAMD\tBD000000DAMD\tB\tBD000000DAMD\tMember\tBD000000DAMD\tBANK\t\t\tBDCCBQBN\tXXX\tBDCCBQBNXXX\t\t\tBDCCBQBNXXX\tBANCO DI CARIBE N.V. BONAIRE\t\t\t22 KAYA GRANDI\t\t\t\tKRALENDIJK\tKRALENDIJK\t\tBONAIRE, SINT EUSTATIUS AND SABA\tBQ\tB\tSUPE\tCNN\t\tFIN\tBD000000DAMC\n";

//        Matcher matcher = Pattern.compile("[^\t]+").matcher(str);
//        if (matcher.find()) {
//            for (int i = 0; i <= matcher.groupCount(); i++) {
//                System.out.println(i + " - " + matcher.group(i));
//            }
//        }
//        int i=0;
//        for (String s : str.split("\t")) {
//            System.out.println(i + " - " + s);
//            i++;
//        }

//        Matcher matcher = Pattern.compile("_V([\\d]+)_").matcher("BANKDIRECTORYPLUS_V2_FULL_20190628_Test.txt");
        Matcher matcher = Pattern.compile("_V([\\d]+)_|_([\\d]{8})_").matcher("BANKDIRECTORYPLUS_V2_FULL_20190628_Test.txt");
        while (matcher.find()) {
            System.out.println("found: " + matcher.group(1) +
                    " " + matcher.group(2));
//            for (int i = 0; i <= matcher.groupCount(); i++) {
//                System.out.println(i + " - " + matcher.group(i));
//            }
        }
//        matcher.find();
//        if (matcher.find()) {
//            matcher.
//            for (int i = 0; i <= matcher.groupCount(); i++) {
//                System.out.println(i + " - " + matcher.group(i));
//            }
//        }
//        while (matcher.find()) {
//            System.out.println(matcher.group(2));
//        }
//        System.out.println(matcher.group(3));


//        Result result = Parser.parse(Stream.of(
//                "10003004IHUSTHUHB   08Magyar Államkincstár Központ            1054 Budapest, Hold utca 7.                       I100320000100           SRF           HUSTHUHB   100"
//        ));
//
//
//        System.out.println(result);


//        FileNameMatcher matcher = FileNameMatcher.of("HT202001.V12");
////        System.out.println(matcher.matches());
//        System.out.println(matcher.getVersion());
//        System.out.println(matcher.getDate());

//        Pattern pattern = Pattern.compile("HT([0-9]{6}).V([0-9]{2})$");
//
//        Matcher matcher = pattern.matcher("HT202001.V12");
//        boolean matches = matcher.matches();
//        System.out.println(matches);
//        System.out.println(matcher.group(1));
//        System.out.println(matcher.group(2));
    }
}
