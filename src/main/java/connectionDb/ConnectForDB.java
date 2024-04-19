package connectionDb;

import config.DbProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

public class ConnectForDB {

    protected static DbProperties dbProperties = org.aeonbits.owner.ConfigFactory.create(DbProperties.class);
    private static Connection connection;

    /**
     * Получает соединение с базой данных.
     *
     * @return соединение с базой данных
     */
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(getEnvDb(), getUsernameDb(), getPasswordDb());
        } catch (SQLException se) {
            fail("БД недоступна" + se);
        }
        return connection;
    }

    /**
     * Возвращает путь к базе данных в зависимости от текущей среды.
     *
     * @return путь к базе данных.
     */
    private static String getEnvDb() {
        String env = System.getProperty("env");
        if ("demo".equals(env)) {
            return dbProperties.dbPath();
        } else {
            throw new IllegalStateException("Unexpected value: " + env);
        }
    }

    /**
     * Получает имя пользователя из базы данных в зависимости от окружения.
     *
     * @return имя пользователя из базы данных
     */
    private static String getUsernameDb() {
        String env = System.getProperty("env");
        if ("dev".equals(env)) {
            return dbProperties.user();
        } else {
            throw new IllegalStateException("Unexpected value: " + env);
        }
    }

    /**
     * Получает пароль для базы данных.
     *
     * @return Пароль для базы данных.
     */
    private static String getPasswordDb() {
        String env = System.getProperty("env");
        if ("dev".equals(env)) {
            return dbProperties.password();
        } else {
            throw new IllegalStateException("Unexpected value: " + env);
        }
    }

    /**
     * Закрывает соединение с базой данных.
     */
    public static void closeDbConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            fail("БД недоступна");
        }
    }

    /**
     * Откатывает текущую транзакцию.
     */
    private static void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
