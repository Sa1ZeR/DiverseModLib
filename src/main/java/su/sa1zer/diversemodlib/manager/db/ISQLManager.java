package su.sa1zer.diversemodlib.manager.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

public interface ISQLManager {

    Connection getConnection() throws SQLException;

    ExecutorService getExecutorService();
}
