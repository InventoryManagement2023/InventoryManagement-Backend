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
@Table(name = "remember_me_cookie_config")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RememberMeCookieConfig {

    @Id
    private int id;

    @Column(name = "days_until_expiration")
    private int daysUntilExpiration;
}
