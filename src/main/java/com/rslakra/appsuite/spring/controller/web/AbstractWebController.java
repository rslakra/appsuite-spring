package com.rslakra.appsuite.spring.controller.web;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.appsuite.core.Payload;
import com.rslakra.appsuite.spring.parser.Parser;
import com.rslakra.appsuite.spring.parser.csv.CsvParser;
import com.rslakra.appsuite.spring.parser.excel.ExcelParser;
import com.rslakra.appsuite.spring.service.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Rohtash Lakra
 * @created 10/15/21 6:11 PM
 */
public abstract class AbstractWebController<T, ID extends Serializable> implements WebController<T, ID> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWebController.class);
    
    private String prefix;
    private Class<T> objectType;
    private AbstractService<T, ID> service;
    
    /**
     * Returns the parser of the <code>T</code> object.
     *
     * @return
     */
    public abstract Parser<T> getParser();
    
    /**
     * Displays the upload UI of <code>T</code> objects.
     *
     * @return
     */
    @Override
    public String showUploadPage() {
        return null;
    }
    
    /**
     * Uploads the file of <code>Roles</code>.
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @Override
    public ResponseEntity<Payload> upload(@RequestParam("file") MultipartFile file) {
        BeanUtils.assertNonNull(file, "Upload 'file' must provide!");
        Payload payload = Payload.newBuilder();
        try {
            List<T> listObjects = null;
            Parser parser = getParser();
            if (CsvParser.isCSVFile(file)) {
                listObjects = ((CsvParser) parser).readCSVStream(file.getInputStream());
            } else if (ExcelParser.isExcelFile(file)) {
                listObjects = ((ExcelParser) parser).readStream(file.getInputStream());
            }
            
            // check the task list is available
            if (Objects.nonNull(listObjects)) {
                listObjects = service.create(listObjects);
                LOGGER.debug("listObjects: {}", listObjects);
                payload.withMessage("Uploaded the file '%s' successfully!", file.getOriginalFilename());
                return ResponseEntity.status(HttpStatus.OK).body(payload);
            }
        } catch (Exception ex) {
            LOGGER.error("Could not upload the file:{}!", file.getOriginalFilename(), ex);
            payload.withMessage("Could not upload the file '%s'!", file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(payload);
        }
        
        payload.withMessage("Unsupported file type!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payload);
    }
    
    /**
     * Displays the download UI for <code>T</code> objects.
     *
     * @return
     */
    @Override
    public String showDownloadPage() {
        return null;
    }
    
    /**
     * Downloads the object of <code>T</code> as <code>fileType</code> file.
     *
     * @param fileType
     * @return
     */
    @GetMapping("/download")
    @Override
    public ResponseEntity<Resource> download(@RequestParam("fileType") String fileType) {
        BeanUtils.assertNonNull(fileType, "Download 'fileType' must provide!");
        ResponseEntity responseEntity = null;
        InputStreamResource inputStreamResource = null;
        String contentDisposition;
        MediaType mediaType;
        Parser parser = getParser();
        try {
            if (CsvParser.isCSVFileType(fileType)) {
                contentDisposition = Parser.getContentDisposition(parser.getDownloadFileName());
                mediaType = Parser.getMediaType(CsvParser.CSV_MEDIA_TYPE);
                inputStreamResource = ((CsvParser) parser).buildCSVResourceStream(service.getAll());
            } else if (ExcelParser.isExcelFileType(fileType)) {
                contentDisposition = Parser.getContentDisposition(parser.getDownloadFileName());
                mediaType = Parser.getMediaType(ExcelParser.EXCEL_MEDIA_TYPE);
                inputStreamResource = ((ExcelParser) parser).buildStreamResources(service.getAll());
            } else {
                throw new UnsupportedOperationException("Unsupported fileType:" + fileType);
            }
            
            // check inputStreamResource is not null
            if (Objects.nonNull(inputStreamResource)) {
                responseEntity = Parser.buildOKResponse(contentDisposition, mediaType, inputStreamResource);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        return responseEntity;
    }
}
