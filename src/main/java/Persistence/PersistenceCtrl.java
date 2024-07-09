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
/**
 * Layer dedicated to the creation and saving of documents
 * As the other layers, it has the controller and singleton patterns to ensure cohesiveness and abstraction
 * @author Martin Viteri
 * @version 1.0, 9/07/24
 */
public class PersistenceCtrl {
    private static PersistenceCtrl instance;
    private PersistenceCtrl() {}
    public static PersistenceCtrl getInstance() {
        if (instance == null) {
            instance = new PersistenceCtrl();
        }
        return instance;
    }
    private boolean isCorrectFileType(String fileName, String expectedExtension) {
        return fileName.endsWith(expectedExtension);
    }
    /**
     * Method to read a Document from the path indicated
     * @param pathInput path were the document is located
     * @return a String with the content of the first line of the document
     * @exception Exception throws an exception in case the document has the wrong extension
     * @see Persistence.PersistenceCtrl#readDoc(String) 
     */
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
    /**
     * Creates the pdf file from the content of the parameters
     * @param pages a list of lists of strings with the content
     * @param pathDirSave the path to save the resulting document
     * @param nameDoc the name of the resulting document
     * @exception FileNotFoundException in case the directory in pathDirSave doesn't exist
     * @see Persistence.PersistenceCtrl#createPDF(List, String, String) 
     */
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
    /**
     * Creates the txt file from the content of the parameters
     * @param pages a list of lists of strings with the content
     * @param pathDirSave the path to save the resulting document
     * @param nameDoc the name of the resulting document
     * @exception FileNotFoundException in case the directory in pathDirSave doesn't exist
     * @see Persistence.PersistenceCtrl#createTXT(List, String, String)
     */
    public void createTXT(List<List<String>> pages, String pathDirSave, String nameDoc) throws FileNotFoundException {
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
            throw new FileNotFoundException("Directory doesn't exist");
        }
    }
}
