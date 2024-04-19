package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources("file:src/main/resources/dbProperties.properties")
public interface DbProperties extends Config {


    /**
     * @return линк БД demo
     */
    @Key("dbpath")
    String dbPath();

    /**
     * @return user для подключения к БД demo
     */
    @Key("user")
    String user();


    /**
     * @return password для подключения к БД demo
     */
    @Key("password")
    String password();

}