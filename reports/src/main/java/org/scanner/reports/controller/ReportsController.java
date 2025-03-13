package org.scanner.reports.controller;

import lombok.AllArgsConstructor;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;
import org.scanner.reports.diagrams.Dataset;
import org.scanner.reports.diagrams.TimeSeriesDay;
import org.scanner.reports.dto.Ticket;
import org.scanner.reports.services.TicketService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
public class ReportsController {
    private final TicketService ticketService;

    @GetMapping("/formReport")
    public ResponseEntity<ByteArrayResource> getReport() {
        List<Ticket> ticketList = ticketService.getTicket();

        byte[] photo;

//        final XYDataset dataset = Dataset.createDataset();
        final XYDataset dataset = Dataset.getDataset(ticketList);
        final JFreeChart chart = TimeSeriesDay.createChart(dataset);
        BufferedImage objBufferedImage = chart.createBufferedImage(1000,500);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        try {
            ImageIO.write(objBufferedImage, "png", bas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        photo=bas.toByteArray();

        ResponseEntity<ByteArrayResource> rsl = ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(photo.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(photo));
        return rsl;
    }

    private byte[] getFileBytesBytes(File file) throws IOException {
        BufferedImage bImage = ImageIO.read(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte [] data = bos.toByteArray();
        return data;
    }

    public static void main(String[] args) throws IOException {
        BufferedImage bImage = ImageIO.read(new File("C:\\projects\\Scanner\\reports\\files\\1.png"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte [] data = bos.toByteArray();

    }
}
