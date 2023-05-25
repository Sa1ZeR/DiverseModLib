package su.sa1zer.diversemodlib.manager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SQLiteManager implements ISQLManager {
    private final ExecutorService executorService;

    private final String fileName;
    private final String absolutePath;

    private Connection connection;

    public SQLiteManager(String absolutePath, String fileName) {
        this.executorService = Executors.newSingleThreadExecutor();

        this.fileName = fileName;
        this.absolutePath = absolutePath;

        connect();
    }

    public boolean isConnected() throws SQLException {
        if(connection == null) {
            return false;
        } else return connection.isValid(5);
    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"
                    + absolutePath + "/" + fileName + ".db");
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(isConnected())
            connect();
        return connection;
    }

    @Override
    public ExecutorService getExecutorService() {
        return executorService;
    }
}
