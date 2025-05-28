package com.qu3dena.aquaengine.backend.iam.domain.model.entities;

import com.qu3dena.aquaengine.backend.iam.domain.model.valueobjects.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private Roles name;

    public Role(Roles name) {
        this.name = name;
    }

    public static Role getDefaultRole() {
        return new Role(Roles.ROLE_USER);
    }

    public static Role toRoleFromName(String name) {
        return new Role(Roles.valueOf(name));
    }

    public static Set<Role> validateRoles(Set<Role> roles) {
        return roles == null || roles.isEmpty() ? Set.of(getDefaultRole()) : roles;
    }

    public String getStringName() {
        return this.name.name();
    }

    public static Role create(String name) {
        return new Role(Roles.valueOf(name));
    }
}
