package com.besafx.app.auditing;


import com.besafx.app.model.History;
import com.besafx.app.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class EntityHistoryListener {

    private static final Logger LOG = LoggerFactory.getLogger(EntityHistoryListener.class);

    public void perform(String note) {
        try {
            EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
            History history = new History();
            history.setNote(note);
            entityManager.persist(history);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }
}
