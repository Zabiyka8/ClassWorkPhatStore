package org.klozevitz.phat_store_mvc_java_311.dao.services;


import org.klozevitz.phat_store_mvc_java_311.model.secuirty.ApplicationUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ApplicationUserService extends DAO<ApplicationUser> {
    ApplicationUser loadUserByUsername(String email) throws UsernameNotFoundException;
}
