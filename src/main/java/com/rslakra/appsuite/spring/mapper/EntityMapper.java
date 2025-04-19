package com.rslakra.appsuite.spring.mapper;

/**
 * @author Rohtash Lakra
 * @version 1.0.0
 * @since 04/19/2025 11:39 AM
 */
public interface EntityMapper<Entity, Dto> {

    /***
     * Converts an <code>Entity</code> to <code>Dto</code>
     * @param entity
     * @return
     */
    Dto toDto(Entity entity);

    /**
     * Converts the <code>Dto</code> to <code>Entity</code>
     *
     * @param dto
     * @return
     */
    Entity toEntity(Dto dto);
}
