package connectionDb;

import io.qameta.allure.Step;

public class Hooks extends ConnectForDB {

    /**
     * Закрытие соединения с БД.
     */
    @Step("Закрытие соединения с БД")
    public void closeDbConnect() {
        ConnectForDB.closeDbConnection();
    }
}
