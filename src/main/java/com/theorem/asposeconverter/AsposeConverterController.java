package com.theorem.asposeconverter;

import com.aspose.words.Document;
import com.aspose.words.HtmlSaveOptions;
import com.aspose.words.SaveFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class AsposeConverterController {
    @RequestMapping("/")
    public String index() throws Exception {
        Document doc = new Document("/Users/bruno/Documents/theorem/docx-files/body-1.docx");
        doc.save("/Users/bruno/Documents/theorem/docx-files/body-1.html", SaveFormat.HTML);
        return "Greetings from Spring Boot!";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        // original file
        var bytes = file.getBytes();
        var inputStream = new ByteArrayInputStream(bytes);

        // response stream
        var outputStream = new ByteArrayOutputStream();

        // using Aspose to convert from docx to html
        Document doc = new Document(inputStream);
        HtmlSaveOptions saveOptions = new HtmlSaveOptions();
        saveOptions.setSaveFormat(SaveFormat.HTML);
        saveOptions.setEncoding(StandardCharsets.UTF_8);
        saveOptions.setImagesFolder("/private/tmp");
        doc.save(outputStream, saveOptions);

        // sending response back to client
        return outputStream.toString(StandardCharsets.UTF_8);
    }
}
