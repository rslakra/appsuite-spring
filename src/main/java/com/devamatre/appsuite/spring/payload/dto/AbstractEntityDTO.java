package com.devamatre.appsuite.spring.payload.dto;

import com.devamatre.framework.core.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * @author Rohtash Lakra
 * @created 8/4/21 5:59 PM
 */

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractEntityDTO<ID extends Serializable> extends AuditableDTO {

    private ID id;

    /**
     * Returns the string representation of this object.
     *
     * @return
     */
    @Override
    public String toString() {
        return ToString.of(AbstractEntityDTO.class)
            .add("id", getId())
            .add(super.toString())
            .toString();
    }
}
