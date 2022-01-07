package com.thinktalkbuild.contractreader.ui.controller;
import com.thinktalkbuild.contractreader.ui.model.Analysis;
import com.thinktalkbuild.contractreader.ui.service.AnalyserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kate
 */
@Controller
@Slf4j
public class UploadController {

    @Autowired
    private AnalyserService analyserService;

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
   
        try {

            Analysis a = analyserService.postToAnalysisEngine(file);

            model.addAttribute("filename", a.getFileName());
            model.addAttribute("summary", a.getSummary());
            model.addAttribute("obligationsByParty", a.getObligationsByParty());
            model.addAttribute("durations", a.getDurations());
            model.addAttribute("raw", "");

        } catch (Exception ex) {
            log.error(ex.getMessage());
            model.addAttribute("errormessage", "An error occurred processing the file.");

        }
        return "analysis";

    }
}
