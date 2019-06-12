package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoDB extends DB_connection implements SupplierDao {


    private static SupplierDaoDB instance = null;

    private SupplierDaoDB() {
    }

    public static SupplierDaoDB getInstance() {
        if (instance == null) {
            instance = new SupplierDaoDB();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        String statement = "INSERT INTO suppliers (id, name, description) " +
                "VALUES (DEFAULT, '" + supplier.getName() +
                "', '" + supplier.getDescription() +
                "');";
        super.executeStatement(statement);

        //TODO setID
    }

    @Override
    public Supplier find(int id) {
        String query = String.format("SELECT * FROM suppliers WHERE id = %d;", id);

        Supplier returnedSupplier = null;

        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(query);
             ResultSet resultSet = pStatement.executeQuery()) {

            while (resultSet.next()) {

                returnedSupplier = getSupplierFromDatabase(resultSet);

                int returnedId = resultSet.getInt("id");
                returnedSupplier.setId(returnedId);

                System.out.println("The searching based on ID was successful. \n" +
                        "The following data retrieved from Database: " +
                        "Supplier: [" +
                        returnedSupplier.toString() +
                        "]");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnedSupplier;
    }



    @Override
    public void remove(int id) {
        String statement = String.format("DELETE FROM suppliers WHERE id = %d", id);
        super.executeStatement(statement);
    }

    @Override
    public List<Supplier> getAll() {

        String query = "SELECT * FROM suppliers";

        List<Supplier> result = new ArrayList<>();


        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(query);
             ResultSet resultSet = pStatement.executeQuery()){

            while(resultSet.next()){

                Supplier returnedSupplier = getSupplierFromDatabase(resultSet);

                int returnedId = resultSet.getInt("id");
                returnedSupplier.setId(returnedId);

                result.add(returnedSupplier);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Supplier getSupplierFromDatabase(ResultSet resultSet) throws SQLException {
        String returnedName = resultSet.getString("name");
        String returnedDescription = resultSet.getString("description");

        return new Supplier(
                returnedName,
                returnedDescription
        );
    }
}
