package com.besafx.app.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.transaction.Transactional;
import java.util.Optional;

class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public Optional<String> getCurrentAuditor() {
        PersonAwareUserDetails userDetails = (PersonAwareUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.ofNullable(userDetails.getPerson().getEmail());
    }
}