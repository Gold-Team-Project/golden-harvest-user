package com.teamgold.goldenharvestuser.domain.user.command.infrastructure.repository;

import com.teamgold.goldenharvestuser.domain.user.command.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
