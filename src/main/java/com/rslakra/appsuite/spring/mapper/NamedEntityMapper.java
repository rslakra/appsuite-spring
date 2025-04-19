package com.rslakra.appsuite.spring.mapper;

import com.rslakra.appsuite.spring.payload.dto.NamedEntityDTO;
import com.rslakra.appsuite.spring.persistence.entity.NamedEntity;

/**
 * @author Rohtash Lakra
 * @version 1.0.0
 * @since 04/19/2025 12:03 PM
 */
public class NamedEntityMapper implements EntityMapper<NamedEntity, NamedEntityDTO> {

    /***
     * Converts an <code>Entity</code> to <code>Dto</code>
     * @param namedEntity
     * @return
     */
    @Override
    public NamedEntityDTO toDto(NamedEntity namedEntity) {
        NamedEntityDTO dto = new NamedEntityDTO<>();

        dto.setId(namedEntity.getId());
        dto.setName(namedEntity.getName());
        dto.setCreatedAt(namedEntity.getCreatedAt());
        dto.setCreatedOn(namedEntity.getCreatedOn());
        dto.setCreatedBy(namedEntity.getCreatedBy());
        dto.setUpdatedAt(namedEntity.getUpdatedAt());
        dto.setUpdatedOn(namedEntity.getUpdatedOn());
        dto.setUpdatedBy(namedEntity.getUpdatedBy());

        return dto;
    }

    /**
     * Converts the <code>Dto</code> to <code>Entity</code>
     *
     * @param dto
     * @return
     */
    @Override
    public NamedEntity toEntity(NamedEntityDTO dto) {
        NamedEntity namedEntity = new NamedEntity<>();
        namedEntity.setId(dto.getId());
        namedEntity.setName(dto.getName());
        namedEntity.setCreatedAt(dto.getCreatedAt());
        namedEntity.setCreatedOn(dto.getCreatedOn());
        namedEntity.setCreatedBy(dto.getCreatedBy());
        namedEntity.setUpdatedAt(dto.getUpdatedAt());
        namedEntity.setUpdatedOn(dto.getUpdatedOn());
        namedEntity.setUpdatedBy(dto.getUpdatedBy());

        return namedEntity;
    }
}
