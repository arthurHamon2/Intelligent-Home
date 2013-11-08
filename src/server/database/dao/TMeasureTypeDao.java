package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.models.Measure;
import server.models.MeasureType;

public class TMeasureTypeDao extends AbstractDao<MeasureType, Long> {

	private final String TABLE_NAME = "T_MEASURE_TYPE";
	    
	public TMeasureTypeDao(){
		
	}
	
	@Override
	public void create(MeasureType obj) {
		 try
	        {
	            // The NULL value will auto increment the id value
	            PreparedStatement stat = this.dal.prepare("INSERT INTO "
	                    + TABLE_NAME
	                    + " (IDMEASURETYPE, TITLEMEASURE, UNITY, IMAGE)"
	                    + "VALUES( NULL , ? , ? , ? )");
	            stat.setString(1, obj.getTitle());
	            stat.setString(2, obj.getUnity());
	            stat.setString(3, obj.getImage());

	            // Execute the query
	            stat.executeUpdate();
	            // Get the last id inserted
	            stat = this.dal.prepare("SELECT MAX(last_insert_rowid()) FROM "
	                    + TABLE_NAME + "");
	            ResultSet lastId = stat.executeQuery();
	            if (lastId.next())
	            {
	                // Update the object with the right id
	                obj.setIdMeasureType(lastId.getLong(1));
	            }
	        }
	        catch (SQLException e)
	        {
	            e.printStackTrace();
	        }		
	} 
	
	@Override
	public void update(MeasureType obj) {
		try
        {
            PreparedStatement stat = this.dal.prepare("UPDATE " + TABLE_NAME
                    + " SET TITLEMEASURE = ?, UNITY = ? , IMAGE = ?"
                    + " WHERE IDMEASURETYPE = ?");
            stat.setString(1, obj.getTitle());
            stat.setString(2, obj.getUnity());
            stat.setString(3, obj.getImage());
            stat.setLong(4, obj.getIdMeasureType());
            // Execute the query
            stat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
	}
	
	@Override
	public MeasureType find(Long id) {
		 String query = "SELECT *"
                 + " FROM " + TABLE_NAME 
                 + " WHERE IDMEASURETYPE = ?";
		 MeasureType obj = null;
	    try
	    {
	        PreparedStatement stat = dal.prepare(query);
	        stat.setLong(1, id);
	        ResultSet res = stat.executeQuery();
	        if (res.next())
	        {
	            obj = new MeasureType(id,res.getString(2),res.getString(3),res.getString(4));
	            res.close();
	        }
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    return obj;
	}

	public MeasureType findByTitle(String title){
		String query = "SELECT *"
                + " FROM " + TABLE_NAME 
                + " WHERE TITLEMEASURE = ?";
		 MeasureType obj = null;
	    try
	    {
	        PreparedStatement stat = dal.prepare(query);
	        stat.setString(1, title);
	        ResultSet res = stat.executeQuery();
	        if (res.next())
	        {
	            obj = new MeasureType(res.getLong(1),title,res.getString(3),res.getString(4));
	            res.close();
	        }
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    return obj;
	}
	
	@Override
	public List<MeasureType> findAll() {
		String query = "SELECT *"
                + " FROM " + TABLE_NAME;
		List<MeasureType> objs = new ArrayList<MeasureType>();
	    try
	    {
	        PreparedStatement stat = dal.prepare(query);
	        ResultSet res = stat.executeQuery();
	        while (res.next())
	        {
	            objs.add(new MeasureType(res.getLong(1),res.getString(2),res.getString(3),res.getString(4)));
	        }
	        res.close();
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    return objs;
	}

	@Override
	public void delete(MeasureType obj) {
        try
        {
            PreparedStatement stat = this.dal.prepare("DELETE FROM "
                    + TABLE_NAME + " WHERE IDMEASURETYPE = " + obj.getIdMeasureType());
            stat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }	
	}

}
