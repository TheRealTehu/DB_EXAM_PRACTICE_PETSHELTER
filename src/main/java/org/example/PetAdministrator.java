package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetAdministrator {
    private final String DbUrl;
    private final String DbUser;
    private final String DbPassword;

    public PetAdministrator(String DbUrl, String DbUser, String DbPassword) {
        this.DbUrl = DbUrl;
        this.DbUser = DbUser;
        this.DbPassword = DbPassword;
    }

    public List<Owner> getOwnersWhoCanAdoptPets(){
        List<Owner> ownersWithFreeSpace = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DbUrl, DbUser, DbPassword)){
            final String SQL = "SELECT owners.name, owners.city, owners.max_number_of_pets, COUNT(*) as number_of_pets " +
                    "FROM owners LEFT JOIN pets ON owners.name = pets.current_owner " +
                    "GROUP BY owners.name " +
                    "HAVING max_number_of_pets - number_of_pets > 0;";

            PreparedStatement statement = conn.prepareStatement(SQL);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Owner owner = new Owner(rs.getString("name"), rs.getString("city"),
                        rs.getInt("max_number_of_pets"));
                ownersWithFreeSpace.add(owner);
            }

            return ownersWithFreeSpace;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Owner getOwnerWithMostPets(){
        try(Connection connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword)){
            final String SQL = "SELECT owners.name, owners.city, owners.max_number_of_pets, COUNT(*) as number_of_pets " +
                    "FROM owners LEFT JOIN pets ON owners.name = pets.current_owner " +
                    "GROUP BY owners.name " +
                    "ORDER BY number_of_pets DESC " +
                    "LIMIT 1;";

            PreparedStatement statement = connection.prepareStatement(SQL);

            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                return new Owner(rs.getString("name"), rs.getString("city"),
                        rs.getInt("max_number_of_pets"));
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pet> getPetsOrderedByAgeDesc(){
        List<Pet> petsInOrder = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword)){
            final String SQL = "SELECT name, species, age, current_owner FROM pets ORDER BY age DESC;";

            PreparedStatement statement = connection.prepareStatement(SQL);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Pet pet = new Pet(rs.getString("name"), rs.getString("species"),
                        rs.getInt("age"), rs.getString("current_owner"));

                petsInOrder.add(pet);
            }

            return petsInOrder;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pet> getPetsOfOwner(Owner owner){
        List<Pet> petsOfOwner = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword)) {
            final String SQL = "SELECT name, species, age FROM pets WHERE current_owner = ?;";

            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setString(1, owner.getName());

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Pet pet = new Pet(rs.getString("name"), rs.getString("species"),
                        rs.getInt("age"), owner.getName());

                petsOfOwner.add(pet);
            }

            return petsOfOwner;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
