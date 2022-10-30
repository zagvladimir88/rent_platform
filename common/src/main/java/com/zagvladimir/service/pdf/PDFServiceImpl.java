package com.zagvladimir.service.pdf;

import com.zagvladimir.domain.ItemLeased;
import com.zagvladimir.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PDFServiceImpl implements PDFService {
  private static final String DEST = "common/target/orders/billing/";
  private static final String PATH_LOGO = "common/src/main/resources/logo.jpg";
  private final ItemService itemService;

  @Override
  public String generateAnInvoice(Set<ItemLeased> itemLeased, Long renterId) {
    String pathToBilling = String.format("%s%d%s", DEST, renterId, ".pdf");

    File file = new File(pathToBilling);
    file.getParentFile().mkdirs();

    PdfDocument pdfDoc = null;
    try {
      pdfDoc = new PdfDocument(new PdfWriter(pathToBilling));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    Document doc = new Document(pdfDoc);

    String[] columns = {
      "Item", "Price", "Amount", "Date of receiving", "Return date", "Total Price"
    };

    Table table = generateTable(itemLeased, columns);
    Image image = new Image(ImageDataFactory.create(imageDataBytes(PATH_LOGO)));
    image.setWidth(200);
    image.setHeight(150);

    doc.add(image);
    doc.add(table);
    doc.add(
        new Paragraph(
            String.format(
                "Total: %f", itemLeased.stream().mapToDouble(ItemLeased::getPriceTotal).sum())));
    doc.add(
        new Paragraph(
            "This calculation is preliminary, the exact calculation will be made by the manager in the office."));
    doc.close();

    return pathToBilling;
  }

  private byte[] imageDataBytes(String pathToImageFile) {
    BufferedImage bImage = null;
    try {
      bImage = ImageIO.read(new File(pathToImageFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      ImageIO.write(bImage, "jpg", bos);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return bos.toByteArray();
  }

  private Table generateTable(Set<ItemLeased> itemLeased, String[] columns) {
    Table table = new Table(UnitValue.createPercentArray(columns.length)).useAllAvailableWidth();

    for (String column : columns) {
      table.addCell(column);
    }

    for (ItemLeased leased : itemLeased) {
      table.addCell(itemService.findById(leased.getItemId()).getItemName());
      table.addCell(String.format("%8.2f", leased.getPricePerDay()));
      table.addCell(
          String.format(
              "%d",
              ChronoUnit.DAYS.between(
                  leased.getTimeFrom().toLocalDateTime().toLocalDate(),
                  leased.getTimeTo().toLocalDateTime().toLocalDate())));
      table.addCell(leased.getTimeFrom().toString());
      table.addCell(leased.getTimeTo().toString());
      table.addCell(String.format("%8.2f", leased.getPriceTotal()));
    }
    return table;
  }
}
