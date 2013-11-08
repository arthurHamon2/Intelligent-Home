package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.models.*;

public class TRuleDao extends AbstractDao<Rule,Integer>{
	
	private final String TABLE_NAME = "T_RULE";

	public TRuleDao(){

	}

	@Override
	public void create(Rule obj) {
		try {	
			// The NULL value will auto increment the id value 
			PreparedStatement stat = this.dal.prepare("INSERT INTO "
						+ TABLE_NAME
						+ " (REF, TITLE , CONTENTS, ENABLE) "
						+ "VALUES( ? , ? , ?, ? )");
	
			stat.setString(1, obj.getRef());
			stat.setString(2, obj.getTitle());
			stat.setString(3, obj.getContents());
			stat.setBoolean(4, obj.isEnable());
			// Execute the query
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
	public void delete(Rule obj) {
		try {
			PreparedStatement stat = 
					this.dal.prepare("DELETE FROM " + TABLE_NAME + " WHERE IDRULE = "+ obj.getId());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Rule find(Integer id) {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE IDRULE = "
				+ id.intValue();
		Rule obj = null;

		try {
			ResultSet res = dal.prepare(query).executeQuery();
			if (res.next()) {
				obj = new Rule(res.getInt(1), res.getString(2), res.getString(3), res
						.getString(4), res.getBoolean(5));
				res.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public List<Rule> findAll() {
		String query = "SELECT * FROM " + TABLE_NAME;
		List<Rule> result = new ArrayList<Rule>();
		try {
			ResultSet res = dal.prepare(query).executeQuery();
			while(res.next()) {
				Rule rule = new Rule(res.getInt(1), res.getString(2), 
						res.getString(3), res.getString(4), res.getBoolean(5));
				result.add(rule);			
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public void update(Rule obj) {
	    try {
            PreparedStatement stat = this.dal.prepare("UPDATE " + TABLE_NAME
                        + " SET REF = ?, TITLE = ?, CONTENTS = ?, ENABLE = ?"
                        + " WHERE IDRULE = ?");
    
            stat.setString(1, obj.getRef());
            stat.setString(2, obj.getTitle());
            stat.setString(3, obj.getContents());
            stat.setBoolean(4, obj.isEnable());
            stat.setInt(5, obj.getId());
            
            
            // Execute the query
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}