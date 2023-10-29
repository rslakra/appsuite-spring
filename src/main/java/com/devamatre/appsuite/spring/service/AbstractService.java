package com.devamatre.appsuite.spring.service;

import com.devamatre.appsuite.spring.filter.Filter;
import com.devamatre.appsuite.spring.persistence.Operation;
import com.devamatre.framework.core.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 10/9/21 7:54 PM
 */
public interface AbstractService<T, ID extends Serializable> {

    String[] IGNORED_PROPERTIES = {
        "id", "createdOn", "createdAt", "createdBy", "updatedOn", "updatedAt", "updatedBy"
    };

    /**
     * Returns the new <code>HttpHeaders</code> object.
     *
     * @return
     */
    static HttpHeaders newHttpHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.set(BeanUtils.REQUEST_TRACER, BeanUtils.nextUuid());
        return httpHeaders;
    }

    /**
     * Validates the <code>T</code> object.
     *
     * @param t
     * @return
     */
    public T validate(Operation operation, T t);

    /**
     * Creates the <code>T</code> object.
     *
     * @param t
     * @return
     */
    public T create(T t);

    /**
     * Creates the <code>List<T></code> objects.
     *
     * @param ts
     * @return
     */
    public List<T> create(List<T> ts);

    /**
     * Returns the list of all <code>T</code> objects.
     *
     * @return
     */
    List<T> getAll();

    /**
     * Returns the <code>T</code> object by <code>id</code>.
     *
     * @param id
     * @return
     */
    T getById(ID id);

    /**
     * Returns the pageable <code>T</code> object by <code>pageable</code> filter.
     *
     * @param filter
     * @return
     */
    public List<T> getByFilter(Filter filter);

    /**
     * Returns the pageable <code>T</code> object by <code>pageable</code> filter.
     *
     * @param pageable
     * @return
     */
    public Page<T> getByFilter(Filter filter, Pageable pageable);

    /**
     * Updates the <code>T</code> object.
     *
     * @param t
     * @return
     */
    public T update(T t);

    /**
     * Updates the <code>List<T></code> objects.
     *
     * @param ts
     * @return
     */
    public List<T> update(List<T> ts);

    /**
     * Deletes the object by <code>id</code>.
     *
     * @param id
     * @return
     */
    public T delete(ID id);

}
