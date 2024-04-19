import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    static Map<String, String> parameters = new HashMap() {
        {
            put("warehouseCode", "value");
            put("warehouseName", "value");
            put("active", "true");  //Взял true в кавычки, так как булевые значения на приеме не поддерживаются
            put("updatedBy", null);
            put("createdBy", "null");
            put("legalEntityCode", "value");
        }
    };
    //TODO необходимо реализовать метод, который будет возвращать подготовленный sql запрос.
    // Необходимо прочитать файл с sql запросом и подставить параметры.
    // При реализации метода учесть, что null и "null" должны корректно отображаться в результирующем запросе.

    @Step("Подготовим sql запрос")
    private static String prepareSqlQuery(Map<String, String> parameters, String pathSqlQuery) {

        //Получение подготовленного sql запроса
        String sqlTemplate = null; // Переменная для хранения подготовленного sql запроса
        try {
            sqlTemplate = new String(Files.readAllBytes(Paths.get(pathSqlQuery)));
        } catch (IOException e) {
            throw new RuntimeException(e);  // Если не удалось получить sql запрос, выбрасываем исключение
        }
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");  // Регулярное выражение для поиска параметров в sql запросе
        Matcher matcher = pattern.matcher(sqlTemplate); // Поиск параметров в sql запросе

        // Подстановка параметров в sql запрос
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1).split(":-")[0]; // Извлекаем ключ параметра
            String defaultValue = matcher.group(1).contains(":-") ? matcher.group(1).split(":-")[1] : null;  // Извлекаем значение параметра
            String value = parameters.getOrDefault(key, defaultValue); // Извлекаем значение параметра

            // Подставляем параметр в sql запрос
            if ("null".equals(value)) {
                matcher.appendReplacement(sb, "NULL");
            } else if (value == null) {
                matcher.appendReplacement(sb, "NULL");
            } else {
                matcher.appendReplacement(sb, value.replace("'", "''"));
            }
        }
        matcher.appendTail(sb); // Добавляем последний параметр в sql запрос
        return sb.toString(); // Возвращаем подготовленный sql запрос
    }


    /**
     * Выполним sql запрос из файла с подстановкой параметров
     *
     * @param parametersSqlQuery мапа с параметрами для подстановки в sql запрос
     * @param sqlQueryPath       путь к файлу с sql запросом
     * @throws java.sql.SQLException исключение, возникающее при ошибке выполнения sql запроса
     * @throws java.io.IOException   исключение, возникающее при ошибке чтения файла с sql запросом
     */
    @Attachment(value = "query", fileExtension = ".sql", type = "text/plain")
    @Step("Выполним sql запрос из файла с подстановкой параметров")
    public static void executeSqlQuery(java.util.Map<String, String> parametersSqlQuery, String sqlQueryPath) throws IOException {
        //В оригинальной реализации здесь находится запрос,
        //который выполняется для подготовки данных к бд, его реализовывать не обязательно
        System.out.println(prepareSqlQuery(parametersSqlQuery, sqlQueryPath));
    }

    public static void main(String[] args) throws IOException {
        executeSqlQuery(parameters, "src/main/resources/CreateWarehouse.sql");
    }
}