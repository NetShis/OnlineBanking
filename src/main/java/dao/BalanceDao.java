package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceDao {
    private final String GET_BALANCE_SQL = "select balance from balance where id = ?";
    private final String PUT_MONEY_SQL = "UPDATE balance SET balance = (select balance from balance where id = ?) + ? where id = ?";
    private final String TAKE_MONEY_SQL = "UPDATE balance SET balance = ? where id = ?";

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
            return new BigDecimal(-1);
        }

        return balance;
    }

    public int putMoneу(Long id, BigDecimal amount) {
        try (Connection connection = ConnectionBuilder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(PUT_MONEY_SQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBigDecimal(2, amount);
            preparedStatement.setLong(3, id);
            preparedStatement.execute();
                return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int takeMoney(Long id, BigDecimal amount) {
        BigDecimal balance = getBalance(id);
        if (balance.compareTo(amount) >= 0) {
            try (Connection connection = ConnectionBuilder.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(TAKE_MONEY_SQL);
                preparedStatement.setBigDecimal(1, balance.subtract(amount));
                preparedStatement.setLong(2, id);
                preparedStatement.execute();
                return 1;

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 0;

    }
}