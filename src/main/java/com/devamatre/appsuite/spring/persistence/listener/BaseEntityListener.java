package com.devamatre.appsuite.spring.persistence.listener;

import com.devamatre.appsuite.spring.persistence.ServiceOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

/**
 * @author Rohtash Lakra
 * @created 8/4/21 6:20 PM
 */
public class BaseEntityListener<T> {

    // LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityListener.class);

    /**
     * @param entity
     */
    @PrePersist
    public void prePersist(Object entity) {
        performOperation(ServiceOperation.CREATE, entity);
    }

    /**
     * @param entity
     */
    @PreUpdate
    public void preUpdate(Object entity) {
        performOperation(ServiceOperation.UPDATE, entity);
    }

    /**
     * @param entity
     */
    @PreRemove
    public void preRemove(Object entity) {
        performOperation(ServiceOperation.DELETE, entity);
    }

    /**
     * @param serviceOperation
     * @param entity
     */
    @Transactional(Transactional.TxType.MANDATORY)
    public void performOperation(ServiceOperation serviceOperation, Object entity) {
        LOGGER.debug("performOperation({}, {})", serviceOperation, entity);
// EntityManager entityManager = AppContextAware.getBean(EntityManager.class);
// entityManager.persist(new FileHistory(file, serviceOperation));
    }

}
