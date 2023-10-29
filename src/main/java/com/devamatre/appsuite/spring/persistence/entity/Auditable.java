package com.devamatre.appsuite.spring.persistence.entity;

import com.devamatre.framework.core.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Rohtash Lakra
 * @created 8/4/21 5:40 PM
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    private Long createdOn;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_on", nullable = false)
    @LastModifiedDate
    private Long updatedOn;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "updated_by", nullable = false)
    @LastModifiedBy
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
