package com.qu3dena.aquaengine.backend.iam.domain.model.aggregates;

import com.qu3dena.aquaengine.backend.iam.domain.model.entities.Role;
import com.qu3dena.aquaengine.backend.iam.domain.model.events.UserRegisteredEvent;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public UserAggregate() {
        this.roles = new HashSet<>();
    }

    public UserAggregate(String username, String password) {
        this();
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
    }

    public UserAggregate(String username, String password, Set<Role> roles) {
        this();
        this.username = username;
        this.password = password;
        this.addRoles(roles);
    }

    public UserAggregate addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public void addRoles(Set<Role> roles) {
        var validatedRoles = Role.validateRoles(roles);
        this.roles.addAll(validatedRoles);
    }

    public static UserAggregate create(String username, String password, Set<Role> roles) {
        var user = new UserAggregate(username, password, roles);

        user.registerEvent(new UserRegisteredEvent(
                user.getId(),
                user.getUsername()
        ));

        return user;
    }
}
