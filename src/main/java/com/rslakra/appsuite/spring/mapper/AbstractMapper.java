package com.rslakra.appsuite.spring.mapper;

/**
 * @author Rohtash Lakra
 * @version 1.0.0
 * @since 04/19/2025 11:39 AM
 */
public interface AbstractMapper<Model, Entity> {

    /***
     * Converts an <code>Model</code> to <code>Entity</code>
     * @param model
     * @return
     */
    Entity toEntity(Model model);

    /**
     * Converts the <code>Entity</code> to <code>Model</code>
     *
     * @param entity
     * @return
     */
    Model toModel(Entity entity);
}
