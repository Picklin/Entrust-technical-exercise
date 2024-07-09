package Presentation;

import Domain.DomainCtrl;

import java.awt.*;
/**
 * Layer dedicated to the interface management and communication with the logic of the application
 * @author Martin Viteri
 * @version 1.0, 9/07/24
 */
public class PresentationCtrl {
    private static PresentationCtrl instance;
    private final DomainCtrl domainCtrl;
    private PresentationCtrl() {
        domainCtrl = DomainCtrl.getInstance();
    }
    public static PresentationCtrl getInstance() {
        if (instance == null) {
            instance = new PresentationCtrl();
        }
        return instance;
    }
    public void ini(Rectangle window) {
        new Window(window);
    }
    public void paginate(String pathInputFile, String pathDirSave, String nameDoc, String extension) throws Exception {
        domainCtrl.paginate(pathInputFile, pathDirSave, nameDoc, extension);
    }
}
