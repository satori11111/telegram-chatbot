package com.smarttek.telegramchatbot.repository;

import static com.smarttek.telegramchatbot.config.SqlFilesPaths.USER_ROLE_DELETE;
import static com.smarttek.telegramchatbot.config.SqlFilesPaths.USER_ROLE_INSERT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.smarttek.telegramchatbot.enums.RoleName;
import com.smarttek.telegramchatbot.model.Role;
import com.smarttek.telegramchatbot.model.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    private static User expectedUser;
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void beforeAll() {
        expectedUser = new User();
        expectedUser.setId(2L);
        expectedUser.setFirstName("UserName 1");
        expectedUser.setLastName("LastName 1");
        expectedUser.setPassword("Password 1");
        expectedUser.setEmail("email@gmail.com");
        Role role = new Role();
        role.setRoleName(RoleName.ROLE_ADMIN);
        role.setId(1L);
        expectedUser.setRoles(Collections.singleton(role));
    }

    @Sql(scripts = {
            USER_ROLE_INSERT
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            USER_ROLE_DELETE
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void findByEmail_validEmail_ReturnsUser() {
        User user = userRepository.findByEmail("email@gmail.com")
                .orElseThrow();
        assertEquals(expectedUser,user);
    }

    @Sql(scripts = {
            USER_ROLE_INSERT
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            USER_ROLE_DELETE
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void findByEmail_nonValidEmail_ThrowsException() {
        String expectedMessage = "Can't find user with email: non validEmail";
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userRepository.findByEmail("non validEmail")
                        .orElseThrow(() -> new EntityNotFoundException(
                                expectedMessage)));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
