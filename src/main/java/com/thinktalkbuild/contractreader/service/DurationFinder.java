package com.thinktalkbuild.contractreader.service;

import com.thinktalkbuild.contractreader.model.Obligation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class DurationFinder {

    public static String regex = "a period of (\\d+) (month|months|year|years)";
    private Pattern pattern;
    public DurationFinder(){
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }


    public List<Period> findDurationsInParagraph(String para) {

        List<Period> durations = new ArrayList<>();
        Matcher matcher = pattern.matcher(para);
        while (matcher.find()) {


            int howMany = Integer.parseInt(matcher.group(1));
            String monthsOrYears = matcher.group(2).toLowerCase();

            log.info("howmany= {}, monthsoryears= {}", howMany, monthsOrYears);
            if(monthsOrYears.contains("month")){
                durations.add( Period.ofMonths(howMany) );
            }else if(monthsOrYears.contains("year")){
                durations.add( Period.ofYears(howMany) );
            }
        }
        return durations;
    }
}