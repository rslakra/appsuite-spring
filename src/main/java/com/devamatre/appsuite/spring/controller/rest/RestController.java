package com.devamatre.appsuite.spring.controller.rest;

import com.devamatre.appsuite.spring.exception.InvalidRequestException;
import com.devamatre.appsuite.spring.filter.Filter;
import com.devamatre.appsuite.core.BeanUtils;
import com.devamatre.appsuite.spring.persistence.ServiceOperation;
import com.devamatre.appsuite.core.Payload;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 1:33 PM
 */
public interface RestController<T, ID extends Serializable> {

    /**
     * @param id
     */
    public default void validate(Optional<ID> id) {
        if (BeanUtils.isNull(id) || !id.isPresent()) {
            throw new InvalidRequestException("The ID should provide!");
        }
    }

    /**
     * @param serviceOperation
     * @param t
     * @return
     */
    public default T validate(ServiceOperation serviceOperation, T t) {
        return t;
    }

    /**
     * Returns the list of all <code>T</code> object.
     *
     * @return
     */
    public List<T> getAll();

    /**
     * Filters the list of <code>T</code> objects.
     *
     * @param filter
     * @return
     */
    public List<T> getByFilter(Filter filter);

    /**
     * @param filter
     * @param pageable
     * @return
     */
    public Page<T> getByFilter(Filter filter, Pageable pageable);

    /**
     * Returns the list of <code>T</code> filters objects.
     *
     * @param allParams
     * @return
     */
    public List<T> getByFilter(Map<String, Object> allParams);

    /**
     * Returns the <code>Page<T></code> list of objects filtered with <code>allParams</code>.
     *
     * @param allParams@return
     */
    public Page<T> getByFilter(Map<String, Object> allParams, Pageable pageable);

    /**
     * Creates the <code>T</code> type object.
     *
     * @param t
     * @return
     */
    public ResponseEntity<T> create(T t);

    /**
     * Creates the list of <code>T</code> type objects.
     *
     * @param ts
     * @return
     */
    public ResponseEntity<List<T>> create(List<T> ts);

    /**
     * Updates the <code>T</code> type object.
     *
     * @param t
     * @return
     */
    public ResponseEntity<T> update(T t);

    /**
     * Updates the list of <code>T</code> type objects.
     *
     * @param ts
     * @return
     */
    public ResponseEntity<List<T>> update(List<T> ts);


    /**
     * Deletes the <code>T</code> type object by <code>id</code>.
     *
     * @param idOptional
     * @return
     */
    public ResponseEntity<Payload> delete(Optional<ID> idOptional);

    /**
     * Uploads the <code>file</code>
     *
     * @param file
     * @return
     */
    public ResponseEntity<Payload> upload(MultipartFile file);

    /**
     * Downloads the object of <code>T</code> as <code>fileType</code> file.
     *
     * @param fileType
     * @return
     */
    public ResponseEntity<Resource> download(String fileType);

}
