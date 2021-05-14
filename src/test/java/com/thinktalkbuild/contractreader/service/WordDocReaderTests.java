package com.thinktalkbuild.contractreader.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;


/**
 *
 * @author kate
 */
public class WordDocReaderTests {
    
    
   private WordDocReader reader = new WordDocReader();

   @Test
   void testReaderParsesWordDoc() throws IOException, InvalidFormatException{
       
       Logger.getAnonymousLogger().info("reader="+reader);
       MockMultipartFile mockFile = dummyWordDoc("WordDocWithLinesAndParagraphs.docx");
       String text = reader.extractTextFromFile(mockFile);
       Logger.getLogger(this.getClass().getName()).info("Text="+text);
       assertFalse(text.isEmpty());
       
   }
   

   private MockMultipartFile dummyWordDoc(String fileName) throws FileNotFoundException, IOException {

        InputStream dataStream = this.getClass().getClassLoader().getResourceAsStream(fileName);  
        return new MockMultipartFile("file", fileName, "multipart/form-data", dataStream); 
        
    }
    
}
