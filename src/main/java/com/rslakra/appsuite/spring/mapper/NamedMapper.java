package com.rslakra.appsuite.spring.mapper;

import com.rslakra.appsuite.spring.payload.model.NamedModel;
import com.rslakra.appsuite.spring.persistence.entity.NamedEntity;

/**
 * @author Rohtash Lakra
 * @version 1.0.0
 * @since 04/19/2025 12:03 PM
 */
public class NamedMapper implements AbstractMapper<NamedModel, NamedEntity> {

    /***
     * Converts an <code>Model</code> to <code>Entity</code>
     * @param namedModel
     * @return
     */
    @Override
    public NamedEntity toEntity(NamedModel namedModel) {
        NamedEntity namedEntity = new NamedEntity<>();

        namedEntity.setId(namedModel.getId());
        namedEntity.setName(namedModel.getName());
        namedEntity.setCreatedAt(namedModel.getCreatedAt());
        namedEntity.setCreatedOn(namedModel.getCreatedOn());
        namedEntity.setCreatedBy(namedModel.getCreatedBy());
        namedEntity.setUpdatedAt(namedModel.getUpdatedAt());
        namedEntity.setUpdatedOn(namedModel.getUpdatedOn());
        namedEntity.setUpdatedBy(namedModel.getUpdatedBy());

        return namedEntity;
    }

    /**
     * Converts the <code>Entity</code> to <code>Model</code>
     *
     * @param namedEntity
     * @return
     */
    @Override
    public NamedModel toModel(NamedEntity namedEntity) {
        NamedModel namedModel = new NamedModel<>();

        namedModel.setId(namedEntity.getId());
        namedModel.setName(namedEntity.getName());
        namedModel.setCreatedAt(namedEntity.getCreatedAt());
        namedModel.setCreatedOn(namedEntity.getCreatedOn());
        namedModel.setCreatedBy(namedEntity.getCreatedBy());
        namedModel.setUpdatedAt(namedEntity.getUpdatedAt());
        namedModel.setUpdatedOn(namedEntity.getUpdatedOn());
        namedModel.setUpdatedBy(namedEntity.getUpdatedBy());

        return namedModel;
    }

}
