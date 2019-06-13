package com.besafx.app.controller;

import com.besafx.app.utils.ExportType;
import com.besafx.app.utils.IOUtils;
import com.google.common.collect.Lists;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class ReportController {

    private final Logger log = LoggerFactory.getLogger(ReportController.class);

    public void export(
            final ExportType exportType,
            final HttpServletResponse response,
            final JasperPrint jasperPrint) {
        export("Report", exportType, response, Lists.newArrayList(jasperPrint));
    }

    public void export(
            final ExportType exportType,
            final HttpServletResponse response,
            final List<JasperPrint> jasperPrints) {
        export("Report", exportType, response, jasperPrints);
    }

    public void export(
            final String fileName,
            final ExportType exportType,
            final HttpServletResponse response,
            final JasperPrint jasperPrint) {
        export(fileName, exportType, response, Lists.newArrayList(jasperPrint));
    }

    public void export(
            final String fileName,
            final ExportType exportType,
            final HttpServletResponse response,
            final List<JasperPrint> jasperPrints) {
        ServletOutputStream servletOutputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            servletOutputStream = response.getOutputStream();
            baos = new ByteArrayOutputStream();
            Exporter exporter = null;
            switch (exportType) {
                case PDF:
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline; filename=\"" +
                            IOUtils.getEncodedFileName(fileName) + ".pdf\"".replace("+", " "));
                    exporter = new JRPdfExporter();
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    SimplePdfExporterConfiguration configurationPdf = new SimplePdfExporterConfiguration();
                    configurationPdf.setCreatingBatchModeBookmarks(true);
                    exporter.setConfiguration(configurationPdf);
                    break;
                case RTF:
                    response.setContentType("application/rtf");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            IOUtils.getEncodedFileName(fileName) + ".rtf\"".replace("+", " "));
                    exporter = new JRRtfExporter();
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
                    exporter.setExporterOutput(new SimpleWriterExporterOutput(servletOutputStream));
                    break;
                case HTML:
                    response.setContentType("application/html");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            IOUtils.getEncodedFileName(fileName) + ".html\"".replace("+", " "));
                    exporter = new HtmlExporter();
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
                    exporter.setExporterOutput(new SimpleHtmlExporterOutput(servletOutputStream));
                    SimpleHtmlReportConfiguration reportExportConfiguration = new SimpleHtmlReportConfiguration();
                    reportExportConfiguration.setWhitePageBackground(false);
                    reportExportConfiguration.setRemoveEmptySpaceBetweenRows(true);
                    exporter.setConfiguration(reportExportConfiguration);
                    break;
                case XHTML:
                    response.setContentType("application/xhtml");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            IOUtils.getEncodedFileName(fileName) + ".xhtml\"".replace("+", " "));
                    exporter = new HtmlExporter();
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
                    exporter.setExporterOutput(new SimpleHtmlExporterOutput(servletOutputStream));
                    break;
                case XLSX:
                    response.setContentType("application/xlsx");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            IOUtils.getEncodedFileName(fileName) + ".xlsx\"".replace("+", " "));
                    exporter = new JRXlsxExporter();
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    SimpleXlsxReportConfiguration configurationXlsx = new SimpleXlsxReportConfiguration();
                    configurationXlsx.setOnePagePerSheet(false);
                    exporter.setConfiguration(configurationXlsx);
                    break;
                case CSV:
                    response.setContentType("application/csv");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            IOUtils.getEncodedFileName(fileName) + ".csv\"".replace("+", " "));
                    exporter = new JRCsvExporter();
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
                    exporter.setExporterOutput(new SimpleWriterExporterOutput(servletOutputStream));
                    break;
                case PPTX:
                    response.setContentType("application/pptx");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            IOUtils.getEncodedFileName(fileName) + ".pptx\"".replace("+", " "));
                    exporter = new JRPptxExporter();
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    break;
                case DOCX:
                    response.setContentType("application/docx");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            IOUtils.getEncodedFileName(fileName) + ".docx\"".replace("+", " "));
                    exporter = new JRDocxExporter();
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    break;
                case ODS:
                    response.setContentType("application/ods");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            IOUtils.getEncodedFileName(fileName) + ".ods\"".replace("+", " "));
                    exporter = new JROdsExporter();
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    SimpleOdsReportConfiguration configurationOds = new SimpleOdsReportConfiguration();
                    configurationOds.setOnePagePerSheet(true);
                    exporter.setConfiguration(configurationOds);
                    break;
                case ODT:
                    response.setContentType("application/odt");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            IOUtils.getEncodedFileName(fileName) + ".odt\"".replace("+", " "));
                    exporter = new JROdtExporter();
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    break;
            }
            exporter.exportReport();
            response.setContentLength(baos.size());
            baos.writeTo(servletOutputStream);

            servletOutputStream.flush();
            servletOutputStream.close();
            baos.close();

        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }
}
