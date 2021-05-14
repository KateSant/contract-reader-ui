package com.thinktalkbuild.contractreader.service;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kate
 */
@Service
public class WordDocReader {

    public String extractTextFromFile(MultipartFile file) throws IOException, InvalidFormatException {
        XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(file.getInputStream()));
        XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
        return extractor.getText();

    }

}
