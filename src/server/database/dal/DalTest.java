package server.database.dal;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import server.database.dao.TUserDao;
import server.models.User;

public class DalTest
{

	@Test
	public void test()
	{
		boolean res = false;
		assertTrue(new File("schema.sql").isFile());
		try
		{
			Dal.instance();
		}
		catch (ConnectionException e)
		{
			res = true;
		}
		assertFalse(res);
		assertTrue(new File("db.sqlite").isFile());
		
		// Table existence test
		assertTrue(tryTable("T_OPERATOR"));
		assertTrue(tryTable("T_OPERATOR_TYPE"));
		assertTrue(tryTable("T_ACTION"));
		assertTrue(tryTable("T_ACTIONS_TODO"));
		assertTrue(tryTable("TH_ACTION"));
        assertTrue(tryTable("T_SENSOR"));
        assertTrue(tryTable("T_SENSOR_TYPE"));
        assertTrue(tryTable("TH_SENSOR"));
        assertTrue(tryTable("T_MEASURE"));
        assertTrue(tryTable("T_MEASURE_TYPE"));
		assertTrue(tryTable("T_RULE"));
		assertTrue(tryTable("T_USER"));
		assertFalse(tryTable("T_UNKNOWN"));
	}
	
	public boolean tryTable(String tableName)
	{
		try
		{
			Dal.instance().prepare("SELECT * FROM " + tableName + ";");
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

    @Test
    public void testTransactionCommit()
    {
        try
        {
            TUserDao dao = new TUserDao();
            User u = new User(0, "AMPLFDKMLGKFO;LF", "MMM","xxx@gmail.com","I");
            int b = dao.findAll().size();
            Dal.instance().begin();
            dao.create(u);
            Dal.instance().commit();
            int a = dao.findAll().size();
            assertEquals(b+1, a);
            b = dao.findAll().size();
            Dal.instance().begin();
            dao.delete(u);
            Dal.instance().commit();
            a = dao.findAll().size();
            assertEquals(b-1, a);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
        assertTrue(true);
    }

    @Test
    public void testTransactionRollback()
    {
        try
        {
            TUserDao dao = new TUserDao();
            User u = new User(0, "AMPLFLGKFO;LF", "MMM","xxx@gmail.com","I");
            int b = dao.findAll().size();
            Dal.instance().begin();
            dao.create(u);
            Dal.instance().rollback();
            int a = dao.findAll().size();
            //assertEquals(b, a);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
        assertTrue(true);
    }
}
