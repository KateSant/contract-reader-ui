package com.thinktalkbuild.contractreader.service;

import com.thinktalkbuild.contractreader.model.Obligation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kate
 */
@Service
public class ObligationsFinder {

    public static List<String> OBLIGING_VERBS_LIST = new ArrayList<>(List.of("shall", "must"));
    public static String OBLIGING_VERBS = String.join("|", OBLIGING_VERBS_LIST);
    public static String PARTY = "[^\\s]+"; // The party.   A word.  (Any character other than space, 1 or more times.)
    public static String SPACE = " ";

    public static String regex = ".*?" // The beginning of the sentence (zero or more any character, non greedy match)
                                + "("+PARTY+")"
                                + SPACE
                                + "("+OBLIGING_VERBS+")" // (e.g. must|shall)
                                + SPACE
                                +"(.+?)" // The action the party must do.  Possibly.  (zero or more any character, non-greedy match), up to...
                                +"(\\.|(?="+PARTY+SPACE+"("+OBLIGING_VERBS+")))"; // The end of the sentence (full stop)  OR another party+space+obliging verb NB lookahead so don't consume it.


    public static Pattern pattern = Pattern.compile(regex);


    protected List<Obligation> findObligations(List<String> inputParagraphs) {
        List<Obligation> obligations = new ArrayList<>();
        inputParagraphs.stream().forEach(para -> {
            obligations.addAll(findObligations(para));
        });
        return obligations;
    }

    protected List<Obligation> findObligations(String inputParagraph) {
        List<Obligation> obligations = new ArrayList<>();
        Matcher matcher = pattern.matcher(inputParagraph);
        while (matcher.find()) {
            System.out.println("regex="+regex);
            System.out.println("0="+ matcher.group(0)+" 1="+matcher.group(1)+" 2="+matcher.group(2)+" 3="+matcher.group(3));
            Obligation obl = new Obligation();
            obl.setWholeSentence(matcher.group(0));
            obl.setParty(matcher.group(1));
            obl.setObligingVerb(matcher.group(2));
            obl.setAction(matcher.group(3));
            obligations.add(obl);
        }



        return obligations;
    }



}