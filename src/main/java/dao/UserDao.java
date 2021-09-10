package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final String GET_BALANCE_SQL = "select balance from online_banking_user where id = ?";

    public BigDecimal getBalance(Long id) {
        BigDecimal balance = null;
        try (Connection connection = ConnectionBuilder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BALANCE_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            balance = resultSet.getBigDecimal(1);


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return balance;
    }
}
