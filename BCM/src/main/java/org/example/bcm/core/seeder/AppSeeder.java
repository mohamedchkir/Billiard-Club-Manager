package org.example.bcm.core.seeder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bcm.core.model.entity.Permission;
import org.example.bcm.core.model.entity.Role;
import org.example.bcm.core.model.entity.User;
import org.example.bcm.core.repository.CityRepository;
import org.example.bcm.core.repository.PermissionRepository;
import org.example.bcm.core.repository.RoleRepository;
import org.example.bcm.core.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AppSeeder implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    @PersistenceContext
    private EntityManager entityManager;


    private Map<String, Permission> permissions = Map.of();
    private Map<String, Role> roles = Map.of();


    @Transactional
    @Override
    public void run(String... args) throws Exception {
        saveCities();
        savePermissions();
    }

    private void savePermissions() {
        Permission readClub = Permission.builder().name("READ_CLUB").build();
        Permission writeClub = Permission.builder().name("WRITE_CLUB").build();
        Permission updateClub = Permission.builder().name("UPDATE_CLUB").build();
        Permission deleteClub = Permission.builder().name("DELETE_CLUB").build();

        Permission manageCity = Permission.builder().name("MANAGE_CITY").build();

        permissionRepository.saveAll(List.of(readClub, writeClub, updateClub, deleteClub, manageCity));
        permissions = Map.of(
                "READ_CLUB", readClub,
                "WRITE_CLUB", writeClub,
                "UPDATE_CLUB", updateClub,
                "DELETE_CLUB", deleteClub,
                "MANAGE_CITY", manageCity
        );

        saveRoles();
    }

    private void saveRoles() {
        Role manager = Role.builder().name("MANAGER").permissions(List.of(
                permissions.get("READ_CLUB"),
                permissions.get("WRITE_CLUB"),
                permissions.get("UPDATE_CLUB"),
                permissions.get("DELETE_CLUB"),
                permissions.get("MANAGE_CITY")
        )).build();

        Role client = Role.builder().name("CLIENT").permissions(List.of(
                permissions.get("READ_CLUB"),
                permissions.get("WRITE_CLUB"),
                permissions.get("UPDATE_CLUB"),
                permissions.get("UPDATE_CLUB")
        )).build();

        roleRepository.saveAll(List.of(manager, client));

        roles = Map.of(
                "CLIENT", client,
                "MANAGER", manager
        );

        saveUser();

    }

    private void saveUser() {
        User client = User.builder()
                .id(1L)
                .email("client@gmail.com")
                .firstName("client")
                .lastName("client")
                .password(passwordEncoder.encode("password"))
                .numberOfToken(0)
                .role(roles.get("CLIENT"))
                .telephone("130772107")
                .build();
        User manager = User.builder()
                .id(2L)
                .email("manager@gmail.com")
                .firstName("manager")
                .lastName("manager")
                .password(passwordEncoder.encode("password"))
                .numberOfToken(0)
                .role(roles.get("MANAGER"))
                .telephone("130772107")
                .build();

        userRepository.saveAll(List.of(client, manager));
    }


    private void saveCities() {
        if (cityRepository.count() == 0) {
            try {
                ClassPathResource resource = new ClassPathResource("City.sql");
                byte[] fileContent = FileCopyUtils.copyToByteArray(resource.getInputStream());
                String sqlQueries = new String(fileContent, StandardCharsets.UTF_8);

                String[] queries = sqlQueries.split(";");
                for (String query : queries) {
                    if (!query.trim().isEmpty()) {
                        try {
                            entityManager.createNativeQuery(query.trim()).executeUpdate();
                        } catch (PersistenceException e) {
                            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                                System.err.println("Error: Unique constraint violation. Skipping the query.");
                            } else {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
