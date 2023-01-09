package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PetAdministratorTest {
    private static final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private PetAdministrator administrator;

    private Owner owner1 = new Owner("John Johnson", "Budapest", 4);
    private Owner owner2 = new Owner("John Doe", "Budapest", 2);
    private Owner owner3 = new Owner("Clark Kent", "Boston", 2);
    private Owner owner4 = new Owner("Mark Rover", "New York", 5);
    private Owner owner5 = new Owner("Anne Small", "Békásmegyer", 1);
    private Owner owner6 = new Owner("Paula Shaman", "Budapest", 1);

    private Pet pet1 = new Pet("Cica", "cat", 1, "John Johnson");
    private Pet pet2 = new Pet("Bogyo", "dog", 6, "John Doe");
    private Pet pet3 = new Pet("Katty", "cat", 2, "Anne Small");
    private Pet pet4 = new Pet("Pamacs", "dog", 4, "Mark Rover");
    private Pet pet5 = new Pet("Smile", "axolotl", 10, "Mark Rover");
    private Pet pet6 = new Pet("Eric", "tiger", 11, "John Johnson");
    private Pet pet7 = new Pet("Mr Mancs", "owl", 8, "John Johnson");
    private Pet pet8 = new Pet("Pocok", "mouse", 5, "John Johnson");
    private Pet pet9 = new Pet("Bryan", "dog", 3, "Paula Shaman");

    @BeforeEach
    void init() throws SQLException {
        administrator = new PetAdministrator(DB_URL, DB_USER, DB_PASSWORD);
        createTable();
        insertDataIntoTables();
    }

    @AfterEach
    void destruct() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String dropTableOwners = "DROP TABLE IF EXISTS owners";
            Statement statement = connection.createStatement();
            statement.execute(dropTableOwners);
        }
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String dropTablePets = "DROP TABLE IF EXISTS pets";
            Statement statement = connection.createStatement();
            statement.execute(dropTablePets);
        }
    }

    private void createTable() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String createTableOwners = "CREATE TABLE IF NOT EXISTS owners (name VARCHAR(255), city VARCHAR(255), max_number_of_pets INT);";
            Statement statement = connection.createStatement();
            statement.execute(createTableOwners);
        }
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String createTablePets = "CREATE TABLE IF NOT EXISTS pets (name VARCHAR(255), species VARCHAR(255), age INT, current_owner VARCHAR(255));";
            Statement statement = connection.createStatement();
            statement.execute(createTablePets);
        }
    }

    private void insertDataIntoTables() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertOwners = "INSERT INTO owners (name, city, max_number_of_pets) VALUES " +
                    "('John Johnson', 'Budapest', 4), "+
                    "('John Doe', 'Budapest', 2), " +
                    "('Clark Kent', 'Boston', 2), " +
                    "('Mark Rover', 'New York', 5), " +
                    "('Anne Small', 'Békásmegyer', 1), " +
                    "('Paula Shaman', 'Budapest', 1);";
            Statement statement = connection.createStatement();
            statement.execute(insertOwners);
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertPets = "INSERT INTO pets (name, species, age, current_owner) VALUES " +
                    "('Cica', 'cat', 1, 'John Johnson'), " +
                    "('Bogyo', 'dog', 6, 'John Doe'), " +
                    "('Katty', 'cat', 2, 'Anne Small'), " +
                    "('Pamacs', 'dog', 4, 'Mark Rover'), " +
                    "('Smile', 'axolotl', 10, 'Mark Rover'), " +
                    "('Eric', 'tiger', 11, 'John Johnson'), " +
                    "('Mr Mancs', 'owl', 8, 'John Johnson'), " +
                    "('Pocok', 'mouse', 5, 'John Johnson'), " +
                    "('Bryan', 'dog', 3, 'Paula Shaman');";
            Statement statement = connection.createStatement();
            statement.execute(insertPets);
        }
    }

    @Test
    void findOwnersWhoCanAdoptMorePetsTest(){
        assertThat(administrator.getOwnersWhoCanAdoptPets())
                .isNotEmpty()
                .hasSize(3)
                .containsExactlyInAnyOrder(owner2, owner3, owner4);
    }

    @Test
    void findOwnerWithMostPetsTest(){
        assertEquals(owner1, administrator.getOwnerWithMostPets());
    }

    @Test
    void getPetsOrderedByAgeDescTest(){
        assertThat(administrator.getPetsOrderedByAgeDesc())
                .isNotEmpty()
                .hasSize(9)
                .containsExactly(pet6, pet5, pet7, pet2, pet8, pet4, pet9, pet3, pet1);
    }

    @Test
    void getPetsOfOwnerTest(){
        assertThat(administrator.getPetsOfOwner(owner4))
                .isNotEmpty()
                .hasSize(2)
                .containsExactlyInAnyOrder(pet4, pet5);
    }
}