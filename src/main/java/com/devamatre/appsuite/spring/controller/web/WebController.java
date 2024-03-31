package com.devamatre.appsuite.spring.controller.web;

import com.devamatre.appsuite.spring.filter.Filter;
import com.devamatre.appsuite.core.Payload;
import com.devamatre.appsuite.spring.persistence.ServiceOperation;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 10/15/21 6:11 PM
 */
public interface WebController<T, ID extends Serializable> {

    /**
     * Saves the <code>t</code> object.
     *
     * @param t
     * @return
     */
    public String save(T t);

    /**
     * Returns the list of <code>T</code> objects.
     *
     * @param model
     * @return
     */
    public String getAll(Model model);

    /**
     * Filters the list of <code>T</code> objects.
     *
     * @param model
     * @param filter
     * @return
     */
    public String filter(Model model, Filter filter);

    /**
     * @param model
     * @param allParams
     * @return
     */
    public String filter(Model model, Map<String, Object> allParams);

    /**
     * @param serviceOperation
     * @param t
     * @return
     */
    public default T validate(ServiceOperation serviceOperation, T t) {
        return t;
    }

    /**
     * Create the new object or Updates the object with <code>id</code>.
     *
     * @param model
     * @param id
     * @return
     */
    public String editObject(Model model, ID id);

    /**
     * Deletes the object with <code>id</code>.
     *
     * @param model
     * @param id
     * @return
     */
    public String delete(Model model, ID id);

    /**
     * Displays the upload UI of <code>T</code> objects.
     *
     * @return
     */
    public String showUploadPage();

    /**
     * Uploads the file of <code>Roles</code>.
     *
     * @param file
     * @return
     */
    public ResponseEntity<Payload> upload(MultipartFile file);

    /**
     * Displays the download UI for <code>T</code> objects.
     *
     * @return
     */
    public String showDownloadPage();

    /**
     * Downloads the object of <code>T</code> as <code>fileType</code> file.
     *
     * @param fileType
     * @return
     */
    public ResponseEntity<Resource> download(String fileType);

}
