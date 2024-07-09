package Persistence;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;

/**
 * Class dedicated to handle the things we want at the end of each page in a pdf file
 * @author Martin Viteri
 * @version 1.0, 9/07/24
 */
public class FooterLineEventHandler implements IEventHandler {
    final private Document doc;
    public FooterLineEventHandler(Document doc) {
        this.doc = doc;
    }
    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = page.getPageSize();
        PdfCanvas pdfCanvas = new PdfCanvas(page);

        // Draw a line at the bottom of the page
        float xStart = pageSize.getLeft() + 36;
        float xEnd = pageSize.getRight() - 36;
        float y = pageSize.getBottom() + 40;

        pdfCanvas.setStrokeColor(Color.GRAY)
                .setLineWidth(1.5f)
                .moveTo(xStart, y)
                .lineTo(xEnd, y)
                .stroke();

        PdfDocument pdfDoc = docEvent.getDocument();
        int pageNum = pdfDoc.getPageNumber(page);
        String strPageNum = "" + pageNum;
        float textwidth = doc.getPdfDocument().getDefaultFont().getWidth(strPageNum, 12);
        float textX = ((xStart + xEnd)/2 - textwidth);
        float textY = y - 20;
        pdfCanvas.beginText()
                .setFontAndSize(doc.getPdfDocument()
                        .getDefaultFont(), 12)
                .moveText(textX, textY)
                .showText(strPageNum)
                .endText();
    }
}
