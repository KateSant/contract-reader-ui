package com.thinktalkbuild.contractreader.service;

import com.thinktalkbuild.contractreader.model.Obligation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Period;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class DurationFinderTests {

    @Autowired
    private DurationFinder durationfinder = new DurationFinder();

    String DURATION_PARA_1 = "This Agreement shall continue for a period of 12 months from the Services Start Date (the “Initial Term”). ";
    Period DURATION_FOUND_1 = Period.ofMonths(12);

    String DURATION_PARA_2 = "for a period of 500 years ";
    Period DURATION_FOUND_2 = Period.ofYears(500);

    String DURATION_PARA_3 = "for a perioD of 2 YeArs ";
    Period DURATION_FOUND_3 = Period.ofYears(2);

    String DURATION_PARA_4 = "there is a period of 1 month and a period of 2 years";
    Period DURATION_FOUND_4_a = Period.ofMonths(1);
    Period DURATION_FOUND_4_b = Period.ofYears(2);


    @Test
    void testFindDuration_findsMonths() {
        List<Period> durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_1);
        assertEquals(durations.get(0), DURATION_FOUND_1);
    }

    @Test
    void testFindDuration_findsYears() {
        List<Period> durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_2);
        assertEquals(durations.get(0), DURATION_FOUND_2);
    }

    @Test
    void testFindDuration_isCaseInsensitive() {
        List<Period> durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_3);
        assertEquals(durations.get(0), DURATION_FOUND_3);
    }

    @Test
    void testFindDuration_findsMultiples() {
        List<Period> durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_4);
        assertEquals(durations.get(0), DURATION_FOUND_4_a);
        assertEquals(durations.get(1), DURATION_FOUND_4_b);
    }


}
