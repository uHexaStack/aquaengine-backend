package com.qu3dena.aquaengine.backend.iam.domain.model.aggregates;

import com.qu3dena.aquaengine.backend.iam.domain.model.entities.Role;
import com.qu3dena.aquaengine.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.HashSet;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class UserAggregate extends AuditableAbstractAggregateRoot<UserAggregate> {

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public UserAggregate() {
        this.role = new Role();
    }

    public UserAggregate(String username, String password, Role role) {
        this();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static UserAggregate create(String username, String password, Role role) {
        return new UserAggregate(username, password, role);
    }

    public String getRoleName() {
        return role.getStringName();
    }
}
