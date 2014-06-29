import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import com.mysql.jdbc.ReplicationDriver;

public class ReplicationDriverDemo {

	public static void main(String[] args) throws Exception {
		ReplicationDriver driver = new ReplicationDriver();

		Properties props = new Properties();
		props.put("autoReconnect", "true");
		props.put("roundRobinLoadBalance", "true");
		props.put("user", "foo");
		props.put("password", "bar");

		//
		// Looks like a normal MySQL JDBC url, with a comma-separated list
		// of hosts, the first being the 'master', the rest being any number
		// of slaves that the driver will load balance against
		//

		Connection conn =
			driver.connect("jdbc:mysql://master,slave1,slave2,slave3/test",props);

		// Do something on the master

		conn.setReadOnly(false);
		conn.setAutoCommit(false);
		conn.createStatement().executeUpdate("UPDATE some_table ....");
		conn.commit();

		// Now, do a query from a slave, the driver automatically picks one
		// from the list

		conn.setReadOnly(true);

		ResultSet rs = conn.createStatement().executeQuery(
			"SELECT a,b,c FROM some_other_table");
	}
}