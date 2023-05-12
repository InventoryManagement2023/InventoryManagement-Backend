package net.inventorymanagement.inventorymanagementwebservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "configuration")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {

    @Id
    private int id;

    @Column(name = "remember_me_cookie_days_until_expiration")
    private int rememberMeCookieDaysUntilExpiration;
}
