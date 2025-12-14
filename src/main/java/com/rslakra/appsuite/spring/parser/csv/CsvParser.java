package com.rslakra.appsuite.spring.parser.csv;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.appsuite.spring.parser.Parser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 4/5/23 4:29 PM
 */
public interface CsvParser<T> extends Parser<T> {
    
    String CSV_CONTENT_TYPE = "text/csv";
    String CSV_FILE_TYPE = "csv";
    String CSV_MEDIA_TYPE = "application/csv";
    
    CSVFormat CSV_WRITER_FORMAT = CSVFormat.DEFAULT.builder()
            .setQuoteMode(QuoteMode.MINIMAL)
            .build();
    
    
    /**
     * @param file
     * @return
     */
    static boolean isCSVFile(MultipartFile file) {
        return Parser.isTypeOf(file, CSV_CONTENT_TYPE);
    }
    
    /**
     * @param fileType
     * @return
     */
    static boolean isCSVFileType(final String fileType) {
        return CSV_FILE_TYPE.equalsIgnoreCase(fileType);
    }
    
    /**
     * @param headers
     * @return
     */
    default CSVFormat getReaderFormat(final String... headers) {
        return CSVFormat.DEFAULT.builder()
                .setHeader(headers)
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .setQuoteMode(QuoteMode.ALL)
                .build();
    }
    
    /**
     * Writes the list of <code>T</code> objects to the <code>OutputStream</code>.
     *
     * @param tList
     * @return
     */
    @Override
    default OutputStream writeStream(List<T> tList) {
        return null;
    }
    
    /**
     * @param csvRecord
     * @return
     */
    T readCSVRecord(CSVRecord csvRecord);
    
    /**
     * @param inputStream
     * @return
     */
    default List<T> readCSVStream(InputStream inputStream) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, getReaderFormat(getReadHeaders()));) {
            final List<T> tList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                tList.add(readCSVRecord(csvRecord));
            }
            
            return tList;
        } catch (IOException ex) {
            throw new IOException("Failed to parse CSV file! Error=" + ex.getMessage(), ex);
        }
    }
    
    /**
     * @param tList
     * @return
     */
    @SuppressWarnings("varargs")
    default InputStreamResource buildCSVResourceStream(final List<T> tList) throws IOException {
        try (ByteArrayOutputStream csvByteStream = new ByteArrayOutputStream();
             final CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(csvByteStream), CSV_WRITER_FORMAT);) {
            // add headers row
            String[] writeHeaders = getWriteHeaders();
            if (BeanUtils.isNotEmpty(writeHeaders)) {
                csvPrinter.printRecord((Object[]) writeHeaders);
            }
            
            // add contents
            if (BeanUtils.isNotEmpty(tList)) {
                tList.forEach(item -> {
                    try {
                        csvPrinter.printRecord(buildRowCells(item).toArray());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
            
            csvPrinter.flush();
            return Parser.newInputStreamResource(csvByteStream.toByteArray());
        } catch (IOException ex) {
            throw new IOException("Failed to import data from CSV file! Error=" + ex.getMessage(), ex);
        }
    }
    
}
