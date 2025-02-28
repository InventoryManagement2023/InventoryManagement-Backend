package net.inventorymanagement.usermanagementwebservice.repository;

import net.inventorymanagement.usermanagementwebservice.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

    @Query(value = "SELECT * FROM configuration c WHERE c.id = 1", nativeQuery = true)
    Configuration findActiveConfiguration();

}
