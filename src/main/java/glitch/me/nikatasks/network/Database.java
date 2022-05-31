package glitch.me.nikatasks.network;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        Dotenv dotenv = Dotenv.configure().directory(System.getProperty("user.home")).load();

        config.setJdbcUrl(dotenv.get("DB_JDBCURL")); // jdbc:postgresql://localhost:5432/postgres
        config.setUsername(dotenv.get("DB_USR"));
        config.setPassword(dotenv.get("DB_PWD"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
