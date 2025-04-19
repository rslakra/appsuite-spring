package com.rslakra.appsuite.spring.mapper;

/**
 * @author Rohtash Lakra
 * @version 1.0.0
 * @since 04/19/2025 11:39 AM
 */
public interface ModelMapper<Model, Dto> {

    /***
     * Converts an <code>Model</code> to <code>Dto</code>
     * @param entity
     * @return
     */
    Dto toDto(Model entity);

    /**
     * Converts the <code>Dto</code> to <code>Model</code>
     *
     * @param dto
     * @return
     */
    Model toModel(Dto dto);
}
