package org.example.bcm.core.seeder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bcm.core.model.entity.*;
import org.example.bcm.core.repository.*;
import org.example.bcm.shared.Enum.TableType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalTime;
import java.util.*;

@Configuration
@RequiredArgsConstructor
public class AppSeeder implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final ServiceRepository serviceRepository;
    private final ClubRepository clubRepository;
    private final TableRepository tableRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Map<String, Permission> permissions = Map.of();
    private Map<String, Role> roles = Map.of();

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        saveCities();
        savePermissions();
        saveServices();
        saveClubs();
        saveTables();
    }

    private void savePermissions() {
        if (permissionRepository.count() == 0) {
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
    }

    private void saveRoles() {
        if (roleRepository.count() == 0) {
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
    }

    private void saveUser() {
        if (userRepository.count() == 0) {
            User client = User.builder()
                    .id(1L)
                    .email("client@gmail.com")
                    .firstName("client")
                    .lastName("client")
                    .password(passwordEncoder.encode("password"))
                    .numberOfToken(0)
                    .role(roles.get("CLIENT"))
                    .telephone("130772107")
                    .city(cityRepository.findById(1L).orElseThrow(() -> new NoSuchElementException("City not found with ID: 1")))
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
                    .city(cityRepository.findById(1L).orElseThrow(() -> new NoSuchElementException("City not found with ID: 1")))
                    .build();

            userRepository.saveAll(List.of(client, manager));
        }
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

    private void saveServices() {
        if (serviceRepository.count() == 0) {
            Service service1 = Service.builder().name("Service1").imageUrl("url1").build();
            Service service2 = Service.builder().name("Service2").imageUrl("url2").build();

            serviceRepository.saveAll(List.of(service1, service2));
        }
    }

    private void saveClubs() {
        if (clubRepository.count() == 0) {
            // Retrieve services (assuming services are already seeded)
            List<Service> services = serviceRepository.findAll();

            Club club1 = Club.builder()
                    .name("Club1")
                    .description("Description1")
                    .services(new HashSet<>(services))
                    .address("address")
                    .openingHour(LocalTime.of(8, 0))
                    .closeHour(LocalTime.of(22, 0))
                    .numberOfToken(0)
                    .imageUrl("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/07/e3/70/28/b-o-binh-billiards-club.jpg?w=1200&h=-1&s=1")
                    .city(cityRepository.findById(1L).orElseThrow(() -> new NoSuchElementException("City not found with ID: 1")))
                    .build();
            Club club2 = Club.builder()
                    .name("Club2")
                    .description("Description2")
                    .services(new HashSet<>(services))
                    .address("address")
                    .openingHour(LocalTime.of(8, 0))
                    .closeHour(LocalTime.of(22, 0))
                    .numberOfToken(0)
                    .city(cityRepository.findById(2L).orElseThrow(() -> new NoSuchElementException("City not found with ID: 2")))
                    .imageUrl("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/07/e3/70/28/b-o-binh-billiards-club.jpg?w=1200&h=-1&s=1")
                    .build();

            clubRepository.saveAll(List.of(club1, club2));
        }
    }

    private void saveTables() {
        if (tableRepository.count() == 0) {
            // Retrieve clubs (assuming clubs are already seeded)
            List<Club> clubs = clubRepository.findAll();

            Table table1 = Table.builder().tableType(TableType.RUSSIAN_BILLIARDS).tokensNeeded(5).club(clubs.get(0)).build();
            Table table2 = Table.builder().tableType(TableType.POOL).tokensNeeded(3).club(clubs.get(1)).build();

            tableRepository.saveAll(List.of(table1, table2));
        }
    }
}
