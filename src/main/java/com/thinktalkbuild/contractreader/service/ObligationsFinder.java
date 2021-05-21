package com.thinktalkbuild.contractreader.service;

import com.thinktalkbuild.contractreader.model.Obligation;
import com.thinktalkbuild.contractreader.model.ObligationsByParty;
import com.thinktalkbuild.contractreader.model.config.ObligationsConfig;
import org.springframework.beans.factory.annotation.Autowired;
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


    public static String PARTY = "[^\\s]+"; // The party.   A word.  (Any character other than space, 1 or more times.)
    public static String SPACE = " ";
    public static String OBLIGING_VERBS_PLACEHOLDER="OBLIGING_VERBS_PLACEHOLDER"; // this will be substituted at runtime because it comes from yaml config.
    public static String regex = ".*?" // The beginning of the sentence (zero or more any character, non greedy match)
                                + "("+PARTY+")"
                                + SPACE
                                + "("+OBLIGING_VERBS_PLACEHOLDER+")" // (e.g. must|shall)
                                + SPACE
                                +"(.+?)" // The action the party must do.  Possibly.  (zero or more any character, non-greedy match), up to...
                                +"(\\.|(?="+PARTY+SPACE+"("+OBLIGING_VERBS_PLACEHOLDER+")))"; // The end of the sentence (full stop)  OR another party+space+obliging verb - NB lookahead,don't consume it, since we want it in the next match.




    private ObligationsConfig obligationsConfig;
    private Highlighter highlighter;
    private Pattern pattern;

    @Autowired
    public ObligationsFinder(Highlighter highlighter, ObligationsConfig obligationsConfig){
        this.obligationsConfig=obligationsConfig;
        this.highlighter=highlighter;
        String obligingVerbsJoined = String.join("|", obligationsConfig.getObligingVerbs());
        String regexWithVerbs = regex.replaceAll(OBLIGING_VERBS_PLACEHOLDER,obligingVerbsJoined);
        pattern = Pattern.compile(regexWithVerbs);
    }

    public ObligationsByParty findObligations(List<String> inputParagraphs) {
        List<Obligation> obligations = new ArrayList<>();
        inputParagraphs.stream().forEach(para -> {
            obligations.addAll(findObligations(para));
        });
        return new ObligationsByParty(obligations);
    }

    protected List<Obligation> findObligations(String inputParagraph) {
        List<Obligation> obligations = new ArrayList<>();
        Matcher matcher = pattern.matcher(inputParagraph);
        while (matcher.find()) {
            Obligation obl = new Obligation();
            obl.setWholeSentence(matcher.group(0));
            obl.setParty(matcher.group(1));
            obl.setObligingVerb(matcher.group(2));
            obl.setAction(matcher.group(3));
            String highlighted = highlighter.highlight(obl.getWholeSentence(), List.of(
                    obl.getObligingVerb(),
                    obl.getAction()
                    ));
            obl.setWholeSentenceHighlighted(highlighted);
            obligations.add(obl);
        }



        return obligations;
    }



}