package name.zhangmin.gw.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("SQLITE Test Bundle");
		    Connection connection = null;
		    try
		    {
		    	Class.forName("org.sqlite.JDBC");
		      // create a database connection
		      connection = DriverManager.getConnection("jdbc:sqlite:E:/IoTGW/data/sample.db");
		      Statement statement = connection.createStatement();
		      statement.setQueryTimeout(30);  // set timeout to 30 sec.

		      statement.executeUpdate("drop table if exists person");
		      statement.executeUpdate("create table person (id integer, name string)");
		      statement.executeUpdate("insert into person values(1, 'leo')");
		      statement.executeUpdate("insert into person values(2, 'yui')");
		      ResultSet rs = statement.executeQuery("select * from person");
		      while(rs.next())
		      {
		        // read the result set
		        System.out.println("name = " + rs.getString("name"));
		        System.out.println("id = " + rs.getInt("id"));
		      }
		    }
		    catch(SQLException e)
		    {
		      // if the error message is "out of memory", 
		      // it probably means no database file is found
		      System.err.println(e.getMessage());
		    }
		    finally
		    {
		      try
		      {
		        if(connection != null)
		          connection.close();
		      }
		      catch(SQLException e)
		      {
		        // connection close failed.
		        System.err.println(e);
		      }
		    }
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
