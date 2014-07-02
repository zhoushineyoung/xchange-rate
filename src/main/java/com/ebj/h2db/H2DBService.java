package com.ebj.h2db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;

import com.ebj.conf.ConfigManager;
import com.ebj.conf.ConfigurationSchema;
import com.google.common.base.Strings;

public class H2DBService {
    Logger logger = Logger.getLogger(H2DBService.class);

    private static JdbcConnectionPool h2ConnPool = null;
    private final String __H2_JDBC_HEADER = "jdbc:h2:";
    
	private Server server;
	private String port = "3366";
	private String dbdir = "/h2db/sample/dbs";//d:/data/yeahmobi-datasystem/finance
	private String table = "currency_xchang_rate";
	private String user = "sa";
	private String password = "martinwin@2014";
	
	
	private static H2DBService h2dbService = null;
	public static H2DBService getInstance() {
        if (null == h2dbService) {
            h2dbService = new H2DBService();
            h2dbService.startServer();
        }
        return h2dbService;
    }

	private H2DBService() {
	    ConfigurationSchema config = ConfigManager.getInstance().getConfig();

        user = config.getH2DBUser();
        password = config.getH2DBPass();
        port = config.getH2ServPort();
        dbdir = config.getH2DBDir();
        try {
            server = Server.createTcpServer("-tcpPort", "3366");
//            server = Server.createTcpServer("-tcpAllowOthers", "-web");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

    private void startServer() {
        try {
            if (null != server) {
                this.server.start();
                logger.info("H2-Database Server Conn URL : [" + server.getURL() + "].");
            }
        } catch (SQLException e) {
            logger.error("H2-Database Server Start Ercror", e);
        }
    }

	public void stopServer() {
		if (null != server) {
			this.server.stop();
		}
	}

	private JdbcConnectionPool creatH2ConnPool() {
        try {
            Class.forName("org.h2.Driver");
            h2ConnPool = JdbcConnectionPool.create(this.getH2DBURL(), user, password);
            if (logger.isDebugEnabled()) {
                logger.debug("H2-DB ConnPool Created, MaxConnections [" + h2ConnPool.getMaxConnections() + "].");
            }
        } catch (ClassNotFoundException e) {
            logger.error("Load H2-Database Driver Error", e);
        }
        
        return h2ConnPool;
    }
	
	public Connection getH2Conn() {
        Connection connection = null;
        try {
            // connection = DriverManager.getConnection(this.getH2DBURL(), user, password); // conn1: url=jdbc:h2:./h2db/sample/dbs user=SA
            connection = creatH2ConnPool().getConnection();
        }  catch (SQLException e) {
            logger.error("Connect To H2-Database Server Error", e);
        }
        return connection;
    }
	
	public JdbcConnectionPool getH2ConnPool() {
        return creatH2ConnPool();
    }
	
	public void useH2() {
		try {
			Connection conn = getH2Conn();
			Statement stat = conn.createStatement();
			// insert data
			stat.execute("DROP TABLE IF EXISTS TEST");
			stat.execute("CREATE TABLE TEST(NAME VARCHAR)");
			stat.execute("INSERT INTO TEST VALUES('Hello World')");

			// use data
			ResultSet result = stat.executeQuery("select name from test ");
			int i = 1;
			while (result.next()) {
				System.out.println(i++ + ":" + result.getString("name"));
			}
			result.close();
			stat.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getH2DBURL() {
        String conn_str = "";
        if (!Strings.isNullOrEmpty(dbdir) && !Strings.isNullOrEmpty(__H2_JDBC_HEADER)) {
            conn_str = __H2_JDBC_HEADER + dbdir;
        }
        return conn_str;
    }
}
