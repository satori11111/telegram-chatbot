package com.smarttek.telegramchatbot.repository;

import com.smarttek.telegramchatbot.enums.RoleName;
import com.smarttek.telegramchatbot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role getRoleByRoleName(RoleName roleName);
}
