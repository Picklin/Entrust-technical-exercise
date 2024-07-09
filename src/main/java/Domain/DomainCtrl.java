package Domain;

import Persistence.PersistenceCtrl;

import java.util.ArrayList;
import java.util.List;

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
    public void paginate(String pathInputFile, String pathDirSave, String nameDoc, String extension) throws Exception {
        String content = persistenceCtrl.readDoc(pathInputFile);
        List<List<String>> pages = sliceText(content,80,25);
        if (extension.equals(".pdf")) persistenceCtrl.createPDF(pages, pathDirSave, nameDoc);
        else persistenceCtrl.createTXT(pages, pathDirSave, nameDoc);
    }

}
