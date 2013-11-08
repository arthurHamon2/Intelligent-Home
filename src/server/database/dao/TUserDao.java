package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.database.service.HouseService;
import server.models.Sensor;
import server.models.User;

public class TUserDao extends AbstractDao<User,Integer> {

	private final String TABLE_NAME = "T_USER";
	private HouseService s;
	
	public TUserDao() {
		s = new HouseService();
	}

	@Override
	public void create(User obj) {
		try {
			// The NULL value will auto increment the id value 
			PreparedStatement stat = this.dal.prepare("INSERT INTO "
					+ TABLE_NAME + " (IDUSER, HOUSE, LOGIN, PWD, MAIL,IMAGE) "
					+ "VALUES( NULL , ? , ? , ? , ? , ?)");

			
			stat.setLong(1, obj.getHouse().getId());
			stat.setString(2, obj.getLogin());
			stat.setString(3, obj.getPwd());
			stat.setString(4, obj.getMail());
			stat.setString(5, obj.getAvatar());

			stat.executeUpdate();
			// Get the last id inserted
			stat = this.dal.prepare("SELECT MAX(last_insert_rowid()) FROM "+ TABLE_NAME);
			ResultSet lastId = stat.executeQuery();
			if(lastId.next()){
				// Update the object with the right id
				obj.setId(lastId.getInt(1));	
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(User obj) {
		try {
			PreparedStatement stat = this.dal.prepare(
					"DELETE FROM " + TABLE_NAME + " WHERE IDUSER = "
							+ obj.getId());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User find(Integer id) {
		String query = "SELECT IDUSER, LOGIN, PWD, MAIL , HOUSE, IMAGE" +
				" FROM " + TABLE_NAME + " WHERE IDUSER = ?";
		User obj = null;
		try {
			PreparedStatement stat = dal.prepare(query);
			stat.setInt(1, id);
			ResultSet res = stat.executeQuery();
			if (res.next()) {
				obj = new User(res.getInt(1), res.getString(2), res.getString(3) , res.getString(4), res.getString(6));
				obj.setHouse(s.find(res.getLong(5)));
				res.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public User findByUser(String login) {
		String query = "SELECT IDUSER, LOGIN, PWD, MAIL , HOUSE, IMAGE" +
				" FROM " + TABLE_NAME + " WHERE LOGIN = ?";
		User obj = null;
		try {
			PreparedStatement stat = dal.prepare(query);
			stat.setString(1, login);
			ResultSet res = stat.executeQuery();
			if (res.next()) {
				obj = new User(res.getInt(1), res.getString(2), res.getString(3) , res.getString(4), res.getString(6));
				obj.setHouse(s.find(res.getLong(5)));
				res.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public List<User> findAll() {
		String query = "SELECT IDUSER, LOGIN, PWD, MAIL , HOUSE, IMAGE" +
				" FROM " + TABLE_NAME;
		List<User> result = new ArrayList<User>();
		try {
			ResultSet res = dal.prepare(query).executeQuery();
			while(res.next()) {
				User user = new User(res.getInt(1), res.getString(2), res.getString(3) , res.getString(4), res.getString(6));
				user.setHouse(s.find(res.getLong(5)));
				result.add(user);			
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void update(User obj) {
	    try { 
            PreparedStatement stat = this.dal.prepare("UPDATE " + TABLE_NAME
                    + " SET LOGIN = ?, PWD = ?, MAIL = ?, HOUSE = ? , IMAGE = ?"
                    + " WHERE IDUSER = ?");

            stat.setString(1, obj.getLogin());
            stat.setString(2, obj.getPwd());
            stat.setString(3, obj.getMail());
            stat.setLong(4, obj.getHouse().getId());
            stat.setString(5, obj.getAvatar());
            stat.setInt(6, obj.getId());

            stat.executeUpdate();
            // Get the last id inserted
            stat = this.dal.prepare("SELECT MAX(last_insert_rowid()) FROM "+ TABLE_NAME);
            ResultSet lastId = stat.executeQuery();
            if(lastId.next()){
                // Update the object with the right id
                obj.setId(lastId.getInt(1));    
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}