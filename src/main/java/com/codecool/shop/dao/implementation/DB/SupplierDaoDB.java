package com.codecool.shop.dao.implementation.DB;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.DB_connection;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoDB extends DB_connection implements SupplierDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoDB.class);
    private static SupplierDaoDB instance = null;

    private SupplierDaoDB() {
    }

    public static SupplierDaoDB getInstance() {
        if (instance == null) {
            instance = new SupplierDaoDB();
            logger.debug("Singleton instance of {} created", instance);
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        String statement = "INSERT INTO suppliers (id, name, description) " +
                "VALUES (DEFAULT, '" + supplier.getName() +
                "', '" + supplier.getDescription() +
                "');";

        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)){

            pStatement.executeUpdate();
            ResultSet generatedKey = pStatement.getGeneratedKeys();
            if(generatedKey.next()){
                supplier.setId(generatedKey.getInt(1));
            }
            logger.debug("Supplier [{}] was added to database successfully", supplier);
        } catch (Exception e) {
            logger.error("Error during adding Supplier [{}] to database. \n Stack: ", supplier, e.getStackTrace());
        }
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

                logger.debug(
                    "The searching based on {} was successful. \n" +
                    "   The following data retrieved from Database: Supplier: [{}]",
                    returnedId,
                    returnedSupplier
                );
            }
        } catch (SQLException e) {
            logger.error("Error during finding Supplier with id {}. \n Stack: {}", id, e.getStackTrace());
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

                logger.debug("Getting Supplier [{}] from database was successful.", returnedSupplier);
            }

        } catch (Exception e) {
            logger.error("Error during getting all Supplier from database. \n Stack: {}", e.getStackTrace());
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
