package com.electronic.store.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public abstract class BaseEntityAuditorAware implements AuditorAware<String> {
    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor.
     */
    public static Logger logger= LoggerFactory.getLogger(BaseEntityAuditorAware.class);
    @Override
    public Optional<String> getCurrentAuditor() {
        logger.info("Initiating request to getCurrentAuditor" );

        return Optional.ofNullable("Deepali Kamble").filter(s->s.isEmpty());
    }
}
