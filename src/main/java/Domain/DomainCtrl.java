package Domain;

import Persistence.PersistenceCtrl;

import java.util.ArrayList;
import java.util.List;
/**
 * Layer dedicated to the logic of the application
 * It has the controller and singleton design pattern to ensure we only have 1 instance of the class
 * @author Martin Viteri
 * @version 1.0, 9/07/24
 */
public class DomainCtrl {
    private static DomainCtrl instance;
    private final PersistenceCtrl persistenceCtrl;
    private DomainCtrl() {
        persistenceCtrl = PersistenceCtrl.getInstance();
    }
    public static DomainCtrl getInstance() {
        if (instance == null) {
            instance = new DomainCtrl();
        }
        return instance;
    }
    /**
     * Divide a very long String into a group of pageSize lines, each of lineSize size
     * Resulting in the content of each page of the output document
     * @param content String to divide into smaller strings
     * @param lineSize number of characters of each line
     * @return a list with the content of the pages of the output document
     * @see Domain.DomainCtrl#sliceText(String, int, int)
     */
    private List<List<String>> sliceText(String content, int lineSize, int pageSize) {
        List<List<String>> pages = new ArrayList<>();
        List<String> currentPage = new ArrayList<>();
        int totalLength = content.length();
        for (int i = 0; i < totalLength; i += lineSize) {
            if (currentPage.size() == pageSize) {
                pages.add(currentPage);
                currentPage = new ArrayList<>();
            }
            String line = content.substring(i, Math.min(totalLength, i + lineSize));
            int lastSpacePos = line.lastIndexOf(" ");
            if ((i + lineSize) < totalLength && content.charAt(i + lineSize - 1) != ' ' && content.charAt(i + lineSize) != ' ') {
                if (lastSpacePos != -1) {
                    line = line.substring(0, lastSpacePos+1);
                    i -= lineSize - lastSpacePos - 1;
                }
            }
            if ((i + lineSize) < totalLength && content.charAt(i+lineSize) == ' ') i++;
            currentPage.add(line.trim());
        }
        pages.add(currentPage);

        return pages;
    }
    /**
     * Method to call when we want to generate either the pdf or txt file
     * @param pathInputFile path to indicate where is the txt file we want to paginate
     * @param pathDirSave path to indicate where we want to save our paginated document
     * @param nameDoc name of the paginated document
     * @param extension if we want a pdf or txt document
     * @see Domain.DomainCtrl#paginate(String, String, String, String) 
     */
    public void paginate(String pathInputFile, String pathDirSave, String nameDoc, String extension) throws Exception {
        String content = persistenceCtrl.readDoc(pathInputFile);
        List<List<String>> pages = sliceText(content,80,25);
        if (extension.equals(".pdf")) persistenceCtrl.createPDF(pages, pathDirSave, nameDoc);
        else persistenceCtrl.createTXT(pages, pathDirSave, nameDoc);
    }

}
