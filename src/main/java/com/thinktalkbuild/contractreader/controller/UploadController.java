package com.thinktalkbuild.contractreader.controller;

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
    
    @Autowired
    private WordDocReader reader;
    

    @GetMapping("/")
    public String greeting(Model model) {
        return "upload";
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
   
        try {
            String text = reader.extractTextFromFile(file);
            List<String> paragraphs = reader.parseParagraphs(text);
            model.addAttribute("raw", text); 
            model.addAttribute("paragraphs", paragraphs);

        } catch (Exception ex) {
            model.addAttribute("errormessage", "An error occurred processing the file.");

        }
        return "analysis";

    }
}
