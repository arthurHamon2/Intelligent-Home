package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.models.Measure;
import server.models.MeasureType;
import server.models.SensorType;

public class TSensorTypeDao extends AbstractDao<SensorType, Long> {

	private final String TABLE_NAME = "T_SENSOR_TYPE";
	private final String TABLE_NAME_SENSOR = "T_SENSOR";
	
	public TSensorTypeDao(){
		
	}
	
	@Override
	public void create(SensorType obj) {
		 try
	        {
	            // The NULL value will auto increment the id value
	            PreparedStatement stat = this.dal.prepare("INSERT INTO "
	                    + TABLE_NAME
	                    + " (IDTYPE, EED, SENSORCLASS , FRAMECLASS,  TITLETYPE, IMAGE)"
	                    + "VALUES( NULL , ? , ? , ?, ?, ? )");
	            stat.setString(1, obj.getEed());
	            stat.setString(2, obj.getSensorClass());
	            stat.setString(3, obj.getFrameClass());
	            stat.setString(4, obj.getTitle());
	            stat.setString(5, obj.getImage());

	            // Execute the query
	            stat.executeUpdate();
	            // Get the last id inserted
	            stat = this.dal.prepare("SELECT MAX(last_insert_rowid()) FROM "
	                    + TABLE_NAME + "");
	            ResultSet lastId = stat.executeQuery();
	            if (lastId.next())
	            {
	                // Update the object with the right id
	                obj.setIdType(lastId.getLong(1));
	            }
	        }
	        catch (SQLException e)
	        {
	            e.printStackTrace();
	        }
		
	}
	
	@Override
	public void update(SensorType obj) {
        try
        {
            PreparedStatement stat = this.dal.prepare("UPDATE " + TABLE_NAME
                    + " SET EED = ?, SENSORCLASS = ?, FRAMECLASS = ?, TITLETYPE = ?, IMAGE = ?"
                    + " WHERE IDTYPE = ?");
            stat.setString(1, obj.getEed());
            stat.setString(2, obj.getSensorClass());
            stat.setString(3, obj.getFrameClass());
            stat.setString(4, obj.getTitle());
            stat.setString(5, obj.getImage());
            stat.setLong(6, obj.getIdType());

            // Execute the query
            stat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }	
	}
	
	@Override
	public SensorType find(Long id) {
		 String query = "SELECT *"
                 + " FROM " + TABLE_NAME 
                 + " WHERE IDTYPE = ?";
		SensorType obj = null;
		try
		{
		    PreparedStatement stat = dal.prepare(query);
		    stat.setLong(1, id);
		    ResultSet res = stat.executeQuery();
		    if (res.next())
		    {
		        obj = new SensorType(id,res.getString(2),res.getString(3),
		        		res.getString(4),res.getString(5), res.getString(6));
		        res.close();
		    }
		}
		catch (SQLException e)
		{
		    e.printStackTrace();
		}
		return obj;
	}
	
	public SensorType findByClass(String className) {
		 String query = "SELECT *"
                 + " FROM " + TABLE_NAME 
                 + " WHERE SENSORCLASS = ?";
		SensorType obj = null;
		try
		{
		    PreparedStatement stat = dal.prepare(query);
		    stat.setString(1, className);
		    ResultSet res = stat.executeQuery();
		    if (res.next())
		    {
		        obj = new SensorType(res.getLong(1),res.getString(2),res.getString(3),
		        		res.getString(4),res.getString(5), res.getString(6));
		        res.close();
		    }
		}
		catch (SQLException e)
		{
		    e.printStackTrace();
		}
		return obj;
	}
	
	public SensorType findByEED(String eed) {
		 String query = "SELECT *"
                 + " FROM " + TABLE_NAME 
                 + " WHERE EED = ?";
		SensorType obj = null;
		try
		{
		    PreparedStatement stat = dal.prepare(query);
		    stat.setString(1, eed);
		    ResultSet res = stat.executeQuery();
		    if (res.next())
		    {
		        obj = new SensorType(res.getLong(1),eed,res.getString(3)
		        		,res.getString(4),res.getString(5),res.getString(6));
		        res.close();
		    }
		}
		catch (SQLException e)
		{
		    e.printStackTrace();
		}
		return obj;
	}

	public SensorType findBySensorRef(Long sensorRef)
	{
		String query ="SELECT t.IDTYPE, t.EED, t.SENSORCLASS, t.FRAMECLASS, t.TITLETYPE, t.IMAGE" +
				 " FROM "
			     + TABLE_NAME
			     +" t JOIN "+TABLE_NAME_SENSOR+" ts ON t.IDTYPE = ts.IDTYPE"
			     + " WHERE ts.SENSORREF = ?";
		SensorType obj = null;
		try
		{
		    PreparedStatement stat = dal.prepare(query);
		    stat.setLong(1, sensorRef.longValue());
		    ResultSet res = stat.executeQuery();
		    if (res.next())
		    {
		        obj = new SensorType(res.getLong(1),res.getString(2),
		        		res.getString(3),res.getString(4),res.getString(5),res.getString(6));
		        res.close();
		    }
		    else // If we dont find the ref, we return a null type
		    {
		    	obj = this.findByEED("0-0-0");
		    }

		}
		catch (SQLException e)
		{
		    e.printStackTrace();
		}
		return obj;
	}
	
	@Override
	public List<SensorType> findAll() {
		String query = "SELECT *"
                 + " FROM " + TABLE_NAME 
                 + " ORDER BY IDTYPE";
	    List<SensorType> result = new ArrayList<SensorType>();
	    try
	    {
	        ResultSet res = dal.prepare(query).executeQuery();
	        while (res.next())
	        {
	        	SensorType m = new SensorType(
	                            res.getLong(1),
	                            res.getString(2),
	                            res.getString(3),
	                            res.getString(4),
	                            res.getString(5),
	                            res.getString(6));
	            result.add(m);
	        }
	        res.close();
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    return result;
	}

	@Override
	public void delete(SensorType obj) {
        try
        {
            PreparedStatement stat = this.dal.prepare("DELETE FROM "
                    + TABLE_NAME + " WHERE IDTYPE = " + obj.getIdType());
            stat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
		
	}

}
