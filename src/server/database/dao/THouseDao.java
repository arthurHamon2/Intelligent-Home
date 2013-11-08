package server.database.dao;

import java.awt.Point;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.models.House;
import server.models.Room;

public class THouseDao extends AbstractDao<House, Long> {

	private final String TABLE_NAME = "T_HOUSE";
	private final String TABLE_NAME_ROOM = "T_ROOM";
	    	
	public THouseDao(){
		
	}
	
	@Override
	public void create(House obj) {
		 try
	        {
	            // The NULL value will auto increment the id value
	            PreparedStatement stat = this.dal.prepare("INSERT INTO "
	                    + TABLE_NAME
	                    + " (IDHOUSE, HOUSENAME, IMAGE)"
	                    + "VALUES( NULL , ? , ? )");
	            stat.setString(1, obj.getName());
	            stat.setString(2, obj.getImage());

	            // Execute the query
	            stat.executeUpdate();
	            // Get the last id inserted
	            stat = this.dal.prepare("SELECT MAX(last_insert_rowid()) FROM "
	                    + TABLE_NAME + "");
	            ResultSet lastId = stat.executeQuery();
	            if (lastId.next())
	            {
	                // Update the object with the right id
	                obj.setId(lastId.getLong(1));
	            }
	        }
	        catch (SQLException e)
	        {
	            e.printStackTrace();
	        }		
	} 
	
	@Override
	public void update(House obj) {
		try
        {
            PreparedStatement stat = this.dal.prepare("UPDATE " + TABLE_NAME
                    + " SET HOUSENAME = ?, IMAGE = ?"
                    + " WHERE IDHOUSE = ?");
            stat.setString(1, obj.getName());
            stat.setString(2, obj.getImage());
            stat.setLong(3, obj.getId());
            // Execute the query
            stat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
	}
	
	@Override
	public House find(Long id) {
		 String query = "SELECT *"
                 + " FROM " + TABLE_NAME 
                 + " WHERE IDHOUSE = ?";
		House obj = null;
	    try
	    {
	        PreparedStatement stat = dal.prepare(query);
	        stat.setLong(1, id);
	        ResultSet res = stat.executeQuery();
	        if (res.next())
	        {
	            obj = new House(id,res.getString(2),res.getString(3));
	            res.close();
	        }
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    return obj;
	}

	public House findByTitle(String title){
		String query = "SELECT *"
                + " FROM " + TABLE_NAME 
                + " WHERE HOUSENAME = ?";
		House obj = null;
	    try
	    {
	        PreparedStatement stat = dal.prepare(query);
	        stat.setString(1, title);
	        ResultSet res = stat.executeQuery();
	        if (res.next())
	        {
	            obj = new House(res.getLong(1),res.getString(2),res.getString(3));
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
	public List<House> findAll() {
		String query = "SELECT *"
                + " FROM " + TABLE_NAME + " WHERE HOUSENAME NOT LIKE 'empty house' ";
		List<House> objs = new ArrayList<House>();
	    try
	    {
	        PreparedStatement stat = dal.prepare(query);
	        ResultSet res = stat.executeQuery();
	        while (res.next())
	        {
	            objs.add(new House(res.getLong(1),res.getString(2),res.getString(3)));
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
	public void delete(House obj) {
        try
        {
            PreparedStatement stat = this.dal.prepare("DELETE FROM "
                    + TABLE_NAME + " WHERE IDHOUSE = " + obj.getId());
            stat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }	
	}

	
	public List<Room> findRooms(Long idHouse){
		 String query = "SELECT R.IDROOM, R.IDHOUSE, R.ROOMTITLE, Xleft , Yleft , Xright , Yright " 
             + " FROM " + TABLE_NAME +" H JOIN "+ TABLE_NAME_ROOM +" R ON(H.IDHOUSE = R.IDHOUSE)"
             + " WHERE H.IDHOUSE = ?";
		List<Room> list = new ArrayList<Room>();
	    try
	    {
	        PreparedStatement stat = dal.prepare(query);
	        stat.setLong(1, idHouse.longValue());
	        ResultSet res = stat.executeQuery();
	        while (res.next())
	        {
	            Room obj = new Room(res.getLong(1),this.find(idHouse),res.getString(3), res.getInt(4),
	    	            res.getInt(5),
	    	            res.getInt(6),
	    	            res.getInt(7));
	            list.add(obj);
	        }
	        res.close();
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    return list;
	}
}
