import Presentation.PresentationCtrl;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Point UpRightCorner = new Point(600, 300);
        Dimension windowDimensions = new Dimension(600,400);
        Rectangle window = new Rectangle(UpRightCorner, windowDimensions);
        PresentationCtrl presentationCtrl = PresentationCtrl.getInstance();
        presentationCtrl.ini(window);
    }
}
