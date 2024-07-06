package com.example.demo;

import org.apache.poi.xwpf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
public class WordOperationTest {

    public static void mergeCellHorizontally(XWPFTable table, int row, int fromCol, int toCol) {
        XWPFTableCell cell = table.getRow(row).getCell(fromCol);
        // Try getting the TcPr. Not simply setting an new one every time.
        CTTcPr tcPr = cell.getCTTc().getTcPr();
        if (tcPr == null) tcPr = cell.getCTTc().addNewTcPr();
        // The first merged cell has grid span property set
        if (tcPr.isSetGridSpan()) {
            tcPr.getGridSpan().setVal(BigInteger.valueOf(toCol - fromCol + 1));
        } else {
            tcPr.addNewGridSpan().setVal(BigInteger.valueOf(toCol - fromCol + 1));
        }
        // The merged cells need to be removed
//        for (int colIndex = toCol; colIndex > fromCol; colIndex--) {
//            table.getRow(row).removeCell(colIndex); // use only this for apache poi versions greater than 4.1.1
//            // table.getRow(row).getCtRow().removeTc(colIndex); // use this for apache poi versions up to 4.1.1
//        }
    }

    public static void cloneParagraph(XWPFParagraph clone, XWPFParagraph source) {
        CTPPr pPr = clone.getCTP().isSetPPr() ? clone.getCTP().getPPr() : clone.getCTP().addNewPPr();
        pPr.set(source.getCTP().getPPr());
        for (XWPFRun r : source.getRuns()) {
            XWPFRun nr = clone.createRun();
            cloneRun(nr, r);
        }
    }

    public static void cloneRun(XWPFRun clone, XWPFRun source) {
        CTRPr rPr = clone.getCTR().isSetRPr() ? clone.getCTR().getRPr() : clone.getCTR().addNewRPr();
        rPr.set(source.getCTR().getRPr());
        clone.setText(source.getText(0));
    }

    @Test
    public void docParagraphTest() {
        try (
                XWPFDocument src = new XWPFDocument(Files.newInputStream(Paths.get("D:/tmp/src_doc.docx")));
                XWPFDocument src1 = new XWPFDocument(Files.newInputStream(Paths.get("D:/tmp/src_doc_1.docx")));
                XWPFDocument src2 = new XWPFDocument(Files.newInputStream(Paths.get("D:/tmp/src_doc_2.docx")));
                XWPFDocument target = new XWPFDocument()
        ) {
            copyElement(src2.getBodyElements(), target);
            copyElement(src.getBodyElements(), target);
            copyElement(src1.getBodyElements(), target);

            try (FileOutputStream out = new FileOutputStream("d:/tmp/hello.docx")) {
                target.write(out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void docTableTest() {
        try (XWPFDocument doc = new XWPFDocument()) {

            XWPFTable table = doc.createTable();

            //Creating first Row
            XWPFTableRow row1 = table.getRow(0);
            row1.getCell(0).setText("First Row, First Column");
            row1.addNewTableCell().setText("First Row, Second Column");
            row1.addNewTableCell().setText("First Row, Third Column");

            //Creating second Row
            XWPFTableRow row2 = table.createRow();
            row2.getCell(0).setText("Second Row, First Column");
            row2.getCell(1).setText("Second Row, Second Column");
            row2.getCell(2).setText("Second Row, Third Column");

            //create third row
            XWPFTableRow row3 = table.createRow();
            row3.getCell(0).setText("Third Row, First Column");
            row3.getCell(1).setText("Third Row, Second Column");
            row3.getCell(2).setText("Third Row, Third Column");

            mergeCellHorizontally(table, 0, 0, 2);

            // save to .docx file
            try (FileOutputStream out = new FileOutputStream("d:/tmp/table.docx")) {
                doc.write(out);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void copyParagraph(XWPFParagraph source, XWPFParagraph target) {
        target.getCTP().setPPr(source.getCTP().getPPr());
        for (int i = 0; i < source.getRuns().size(); i++) {
            XWPFRun run = source.getRuns().get(i);
            XWPFRun targetRun = target.createRun();
            //copy formatting
            targetRun.getCTR().setRPr(run.getCTR().getRPr());
            //no images just copy text
            targetRun.setText(run.getText(0));
        }
    }

    private void copyElement(List<IBodyElement> elements, XWPFDocument target) {
        for (IBodyElement element : elements) {
            if (element.getElementType() == BodyElementType.PARAGRAPH) {
                XWPFParagraph p = target.createParagraph();

                p.getCTP().set(((XWPFParagraph) element).getCTP());
            } else if (element.getElementType() == BodyElementType.TABLE) {
                XWPFTable table = target.createTable();

                table.getCTTbl().set(((XWPFTable) element).getCTTbl());
            }
        }
    }
}
