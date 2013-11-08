package server.database.dal;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Dal
{

	private static Dal instance = null;
	
	private	Connection connection = null;

	private Dal (String src, String schema) throws ConnectionException
	{
		try
		{
			// Is db created ?
			File db = new File(src);
			boolean exist = db.exists();
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + src);
			connection.setAutoCommit(true);
			
			if (!exist)
			{
				File sch = new File(schema);
				byte[] query = new byte[(int) sch.length()];
				FileInputStream fis = new FileInputStream(sch);
				fis.read(query);
				connection.createStatement().executeUpdate(new String(query));
				fis.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ConnectionException();
		}
	}
	
	public static Dal instance() throws ConnectionException
	{
		if (null == instance)
			instance = new Dal("db.sqlite", "schema.sql");
		
		return instance;
	}
	
	public PreparedStatement prepare(String query) throws SQLException
	{
		return this.connection.prepareStatement(query);
	}

    public void begin() throws SQLException
    {
        /*// this.prepare("BEGIN TRANSACTION;").execute();
        this.connection.setAutoCommit(false);*/
    }

    public void commit() throws SQLException
    {
        /*//this.prepare("COMMIT TRANSACTION; END;").execute();
        this.connection.commit();
        this.connection.setAutoCommit(true);
        //this.connection.*/
    }

    public void rollback() throws SQLException
    {
        /*//this.prepare("ROLLBACK TRANSACTION; END;").execute();
        this.connection.rollback();
        this.connection.setAutoCommit(true);*/
    }
	
}
