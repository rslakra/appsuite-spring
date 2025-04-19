package com.rslakra.appsuite.spring.payload.model;

import com.rslakra.appsuite.core.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Rohtash Lakra
 * @created 8/4/21 5:59 PM
 */

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractModel<ID extends Serializable> extends AuditableModel {

    private ID id;

    /**
     * Returns the string representation of this object.
     *
     * @return
     */
    @Override
    public String toString() {
        return ToString.of(AbstractModel.class)
                .add("id", getId())
                .add(super.toString())
                .toString();
    }
}
