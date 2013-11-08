package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.models.Room;

public class TRoomDao extends AbstractDao<Room, Long> {

	private final String TABLE_NAME = "T_ROOM";
	private THouseDao houseDao;    
	
	public TRoomDao(){
		this.houseDao = new THouseDao();
	}
	
	@Override
	public void create(Room obj) {
		 try
	        {
	            // The NULL value will auto increment the id value
	            PreparedStatement stat = this.dal.prepare("INSERT INTO "
	                    + TABLE_NAME
	                    + " (IDROOM, IDHOUSE, ROOMTITLE , Xleft , Yleft , Xright , Yright )"
	                    + "VALUES( NULL , ? , ? , ? , ? , ? , ? )");
	            stat.setLong(1, obj.getHouse().getId());
	            stat.setString(2, obj.getTitle());
	            stat.setInt(3, obj.getCoordinates().get(0).x);
	            stat.setInt(4, obj.getCoordinates().get(0).y);
	            stat.setInt(5, obj.getCoordinates().get(1).x);
	            stat.setInt(6, obj.getCoordinates().get(1).y);

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
	public void update(Room obj) {
		try
        {
            PreparedStatement stat = this.dal.prepare("UPDATE " + TABLE_NAME
                    + " SET IDHOUSE = ?, ROOMTITLE = ? , Xleft = ?, Yleft = ?, Xright = ?, Yright = ?"
                    + " WHERE IDROOM = ?");
            stat.setLong(1, obj.getHouse().getId());
            stat.setString(2, obj.getTitle());
            stat.setInt(3, obj.getCoordinates().get(0).x);
            stat.setInt(4, obj.getCoordinates().get(0).y);
            stat.setInt(5, obj.getCoordinates().get(1).x);
            stat.setInt(6, obj.getCoordinates().get(1).y);
            stat.setLong(7, obj.getId());
            // Execute the query
            stat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
	}
	
	@Override
	public Room find(Long id) {
		 String query = "SELECT *"
                 + " FROM " + TABLE_NAME 
                 + " WHERE IDROOM = ?";
		Room obj = null;
	    try
	    {
	        PreparedStatement stat = dal.prepare(query);
	        stat.setLong(1, id);
	        ResultSet res = stat.executeQuery();
	        if (res.next())
	        {
	            obj = new Room(id,houseDao.find(res.getLong(2)),res.getString(3),
	            res.getInt(4),
	            res.getInt(5),
	            res.getInt(6),
	            res.getInt(7));
	            res.close();
	        }
	    }
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    return obj;
	}

	public Room findByTitle(String title){
		String query = "SELECT *"
                + " FROM " + TABLE_NAME 
                + " WHERE ROOMTITLE = ?";
		 Room obj = null;
	    try
	    {
	        PreparedStatement stat = dal.prepare(query);
	        stat.setString(1, title);
	        ResultSet res = stat.executeQuery();
	        if (res.next())
	        {
	        	obj = new Room(res.getLong(1),houseDao.find(res.getLong(2)),res.getString(3),
	    	            res.getInt(4),
	    	            res.getInt(5),
	    	            res.getInt(6),
	    	            res.getInt(7));
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
	public List<Room> findAll() {
		String query = "SELECT *"
                + " FROM " + TABLE_NAME;
		List<Room> objs = new ArrayList<Room>();
	    try
	    {
	        PreparedStatement stat = dal.prepare(query);
	        ResultSet res = stat.executeQuery();
	        while (res.next())
	        {
	            objs.add(new Room(res.getLong(1),houseDao.find(res.getLong(2)),res.getString(3),
	    	            res.getInt(4),
	    	            res.getInt(5),
	    	            res.getInt(6),
	    	            res.getInt(7)));
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
	public void delete(Room obj) {
        try
        {
            PreparedStatement stat = this.dal.prepare("DELETE FROM "
                    + TABLE_NAME + " WHERE IDROOM = " + obj.getId());
            stat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }	
	}

}
