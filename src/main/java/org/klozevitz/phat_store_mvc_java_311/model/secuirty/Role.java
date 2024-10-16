package org.klozevitz.phat_store_mvc_java_311.model.secuirty;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_EMPLOYEE,
    ROLE_USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
