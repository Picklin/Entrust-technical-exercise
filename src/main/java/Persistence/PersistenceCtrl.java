package Persistence;


import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.AreaBreakType;

import java.io.*;
import java.util.List;

public class PersistenceCtrl {
    private static PersistenceCtrl instance;
    private PersistenceCtrl() {}
    public static PersistenceCtrl getInstance() {
        if (instance == null) {
            instance = new PersistenceCtrl();
        }
        return instance;
    }
    public boolean isCorrectFileType(String fileName, String expectedExtension) {
        return fileName.endsWith(expectedExtension);
    }
    public String readDoc(String pathInput) throws Exception {
        String content;
        if (isCorrectFileType(pathInput, ".txt")) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(pathInput));
                content = bufferedReader.readLine();
            } catch (IOException e) {
                content = e.getMessage();
            }
        }
        else throw new Exception("Wrong file extension");
        return content;
    }
    public void createPDF(List<List<String>> pages, String pathDirSave, String nameDoc) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(pathDirSave + '/' + nameDoc + ".pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new FooterLineEventHandler(doc));
        int pageNum = 0;
        for (List<String> page : pages) {
            for (String line : page) {
                Paragraph paragraph = new Paragraph(line);
                paragraph.setFirstLineIndent(35f)
                        .setMarginTop(0f)
                        .setMarginBottom(0f);
                doc.add(paragraph);
            }
            if (pageNum < pages.size()-1) doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            ++pageNum;
        }
        doc.close();
    }
    public void createTXT(List<List<String>> pages, String pathDirSave, String nameDoc) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathDirSave + '/' + nameDoc + ".txt"))) {
            int pageNum = 1;
            for (List<String> page : pages) {
                for (String line : page) {
                    writer.write(line);
                    writer.newLine();
                }
                writer.newLine();
                writer.write("------------------------------ " + pageNum + " ------------------------------\n");
                writer.newLine();
                ++pageNum;
            }
        } catch (IOException e) {
            throw e;
        }

    }
}
