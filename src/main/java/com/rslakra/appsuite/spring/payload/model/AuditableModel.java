package com.rslakra.appsuite.spring.payload.model;

import com.rslakra.appsuite.core.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Rohtash Lakra
 * @created 8/4/21 5:40 PM
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AuditableModel {

    private Long createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String createdBy;

    private Long updatedOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private String updatedBy;

    /**
     * ToString
     *
     * @return
     */
    @Override
    public String toString() {
        return ToString.of()
                .add("createdOn", getCreatedOn())
                .add("createdAt", getCreatedAt())
                .add("createdBy", getCreatedBy())
                .add("updatedOn", getUpdatedOn())
                .add("updatedAt", getUpdatedAt())
                .add("updatedBy", getUpdatedBy())
                .toString();
    }

}
