package com.devamatre.appsuite.http;

public interface RestClient {

    /**
     * Reads an entity.
     *
     * @param path
     * @param responseType
     * @param <T>
     * @return
     * @throws RestException
     */
    <T> T getEntity(String path, Class<T> responseType) throws RestException;

    /**
     * Creates an entity.
     *
     * @param path
     * @param payload
     * @param responseType
     * @param <T>
     * @return
     * @throws RestException
     */
    <T> T postEntity(String path, Object payload, Class<T> responseType) throws RestException;

    /**
     * Updates an entity.
     *
     * @param path
     * @param payload
     * @param responseType
     * @param <T>
     * @return
     * @throws RestException
     */
    <T> T putEntity(String path, Object payload, Class<T> responseType) throws RestException;


    /**
     * Deletes an entity.
     *
     * @param path
     * @param payload
     * @param responseType
     * @param <T>
     * @return
     * @throws RestException
     */
    <T> T deleteEntity(String path, Object payload, Class<T> responseType) throws RestException;


}
