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

                int returnedId = resultSet.getInt("id");
                String returnedName = resultSet.getString("name");
                String returnedDescription = resultSet.getString("description");

                returnedSupplier = new Supplier(
                        returnedName,
                        returnedDescription
                );

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

        String query = "SELECT * FROM categories";

        List<Supplier> result = new ArrayList<>();


        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(query);
             ResultSet resultSet = pStatement.executeQuery()){

            while(resultSet.next()){

                int returnedId = resultSet.getInt("id");
                String returnedName = resultSet.getString("name");
                String returnedDescription = resultSet.getString("description");

                Supplier returnedSupplier = new Supplier(
                        returnedName,
                        returnedDescription
                );

                returnedSupplier.setId(returnedId);

                result.add(returnedSupplier);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
