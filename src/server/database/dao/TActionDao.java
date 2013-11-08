package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import server.models.Action;


public class TActionDao extends AbstractDao<Action, Long> {

	private final String TABLE_NAME = "T_ACTION";

	
	public TActionDao(){

	}
	
	@Override
	public void create(Action obj) {
		try {
            // The NULL value will auto increment the id value
			PreparedStatement stat = this.dal.prepare("INSERT INTO "
					+ TABLE_NAME + " (IDACTION, OPERATORTYPE, FRAME , TITLEACTION, DESCRIPTIONACTION, IMAGE ) "
					+ "VALUES( NULL , ? , ? , ? , ? , ?  )");
			stat.setLong(1, obj.getOperatorType().getIdType());
			stat.setString(2, obj.getFrame());
			stat.setString(3, obj.getTitle());
			stat.setString(4, obj.getDescription());
			stat.setString(5, obj.getImage());
			// Execute the query
			stat.executeUpdate();
            // Get the last id inserted
            stat = this.dal.prepare("SELECT MAX(last_insert_rowid()) FROM "
                    + TABLE_NAME);
            ResultSet lastId = stat.executeQuery();
            if (lastId.next())
            {
                // Update the object with the right id
                obj.setId(lastId.getLong(1));
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Action obj) {
        try { 
            PreparedStatement stat = this.dal.prepare("UPDATE " + TABLE_NAME
                    + " SET OPERATORTYPE = ?, FRAME = ?, TITLEACTION = ?, DESCRIPTIONACTION = ?, IMAGE = ? "
                    + " WHERE IDACTION = ?");
			stat.setLong(1, obj.getOperatorType().getIdType());
			stat.setString(2, obj.getFrame());
			stat.setString(3, obj.getTitle());
			stat.setString(4, obj.getDescription());
			stat.setString(5, obj.getImage());
			stat.setLong(6, obj.getId());
            // Execute the query
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void delete(Action obj) {
		try {
			PreparedStatement stat = this.dal.prepare("DELETE FROM " + TABLE_NAME + " WHERE IDACTION = "
					+ obj.getId());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//TODO: Arthur: jointure pour initialiser l'objet operatorType de la table action
	@Override
	public Action find(Long id) {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE IDACTION = "+ id.longValue();
		Action obj = null;
		try {
			ResultSet res = dal.prepare(query).executeQuery();
			if(res.next()) {
				obj = new Action(res.getLong(1), null , res.getString(3), res.getString(4), res.getString(5),res.getString(6));
				res.close();			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	//TODO: Arthur: jointure pour initialiser l'objet operatorType de la table action
	public List<Action> findByType(Long type) {
		String query = "SELECT * " +
					   "FROM " + TABLE_NAME +
					   " WHERE OPERATORTYPE = "+ type.longValue();
		List<Action> objs = new ArrayList<Action>();
		try {
			ResultSet res = dal.prepare(query).executeQuery();
			while(res.next()) {
				Action obj = new Action(res.getLong(1), null , res.getString(3), res.getString(4), res.getString(5),res.getString(6));
				objs.add(obj);			
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objs;
	}

	//TODO: Arthur: jointure pour initialiser l'objet operatorType de la table action
	@Override
	public List<Action> findAll() {
		String query = "SELECT * FROM " + TABLE_NAME;
		List<Action> result = new ArrayList<Action>();
		Action obj = null;

		try {
			ResultSet res = dal.prepare(query).executeQuery();
			while(res.next()) {
				obj = new Action(res.getLong(1), null , res.getString(3), res.getString(4), res.getString(5),res.getString(6));
				result.add(obj);			
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
