package com.noua.loans.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAwareImpl")
public class AuditAware implements AuditorAware<String> {
    /**
     * @return  The current user
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable("DBA");
    }
}
