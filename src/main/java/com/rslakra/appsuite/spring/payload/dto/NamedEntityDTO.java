package com.rslakra.appsuite.spring.payload.dto;

import com.rslakra.appsuite.core.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import jakarta.persistence.MappedSuperclass;

/**
 * @author Rohtash Lakra
 * @created 8/20/21 7:14 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class NamedEntityDTO<ID extends Serializable> extends AbstractEntityDTO<ID> {

    private String name;

    /**
     * Returns the string representation of this object.
     *
     * @return
     */
    @Override
    public String toString() {
        return ToString.of(NamedEntityDTO.class)
            .add("id", getId())
            .add("name", getName())
            .toString();
    }
}
