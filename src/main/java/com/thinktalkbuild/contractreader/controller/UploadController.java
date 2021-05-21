package com.thinktalkbuild.contractreader.controller;

import com.thinktalkbuild.contractreader.model.ContractSummary;
import com.thinktalkbuild.contractreader.model.Obligation;
import com.thinktalkbuild.contractreader.model.ObligationsByParty;
import com.thinktalkbuild.contractreader.service.ContractSummariser;
import com.thinktalkbuild.contractreader.service.ObligationsFinder;
import com.thinktalkbuild.contractreader.service.WordDocReader;
import java.util.List;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kate
 */
@Controller
public class UploadController {

    private WordDocReader reader;

    private ContractSummariser summariser;

    private ObligationsFinder obligationsFinder;

    @Autowired
    public UploadController(WordDocReader reader, ContractSummariser summariser, ObligationsFinder obligationsFinder){
        this.reader=reader;
        this.summariser=summariser;
        this.obligationsFinder=obligationsFinder;
    }
    

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
   
        try {
            String text = reader.extractTextFromFile(file);
            List<String> paragraphs = reader.parseParagraphs(text);
            model.addAttribute("raw", text);
            model.addAttribute("filename", file.getOriginalFilename());
            ContractSummary summary = summariser.summarise(paragraphs);
            model.addAttribute("summary", summary);
            ObligationsByParty obligationsByParty = obligationsFinder.findObligations(paragraphs);
            model.addAttribute("obligationsByParty", obligationsByParty);

        } catch (Exception ex) {
            model.addAttribute("errormessage", "An error occurred processing the file.");

        }
        return "analysis";

    }
}
