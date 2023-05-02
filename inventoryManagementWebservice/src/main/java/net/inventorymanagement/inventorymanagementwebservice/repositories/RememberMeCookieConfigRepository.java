package net.inventorymanagement.inventorymanagementwebservice.repositories;

import net.inventorymanagement.inventorymanagementwebservice.model.RememberMeCookieConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RememberMeCookieConfigRepository extends JpaRepository<RememberMeCookieConfig, Integer> {

    @Query(value = "SELECT * FROM remember_me_cookie_config c WHERE c.id = 1", nativeQuery = true)
    RememberMeCookieConfig findActiveRememberMeCookieConfig();

}
