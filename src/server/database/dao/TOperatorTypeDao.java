package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.models.Operator;
import server.models.OperatorType;

public class TOperatorTypeDao extends AbstractDao<OperatorType, Long> {

	private final String TABLE_NAME = "T_OPERATOR_TYPE";
	
	public TOperatorTypeDao(){

	}
	
	@Override
	public void create(OperatorType obj) {
		try {
			PreparedStatement stat = this.dal.prepare("INSERT INTO "
					+ TABLE_NAME + " (IDTYPE, TITLETYPE, DESCRIPTIONTYPE, IMAGE, CLASSNAME ) "
					+ "VALUES( ? , ? , ? , ? , ? )");
			stat.setLong(1, obj.getIdType());
			stat.setString(2, obj.getTitle());
			stat.setString(3, obj.getDescription());
			stat.setString(4, obj.getImage());
			stat.setString(5, obj.getClassName());
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(OperatorType obj) {
        try { 
            PreparedStatement stat = this.dal.prepare("UPDATE " + TABLE_NAME
                    + " SET TITLETYPE = ?, DESCRIPTIONTYPE = ?, IMAGE = ?, CLASSNAME = ?"
                    + " WHERE IDTYPE = ?");
            stat.setString(1, obj.getTitle());
            stat.setString(2, obj.getDescription());
            stat.setString(3, obj.getImage());
            stat.setLong(5, obj.getIdType());
            stat.setString(4, obj.getClassName());
            // Execute the query
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void delete(OperatorType obj) {
		try {
			PreparedStatement stat = this.dal.prepare("DELETE FROM " + TABLE_NAME + " WHERE IDTYPE = "
					+ obj.getIdType());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public OperatorType find(Long id) {
		String query = "SELECT IDTYPE, TITLETYPE, DESCRIPTIONTYPE, IMAGE, CLASSNAME" +
				" FROM " + TABLE_NAME + " WHERE IDTYPE = "+ id.longValue();
		OperatorType obj = null;
		try {
			ResultSet res = dal.prepare(query).executeQuery();
			if(res.next()) {
				obj = new OperatorType(res.getLong(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5));
				res.close();			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public List<OperatorType> findAll() {
		String query = "SELECT IDTYPE, TITLETYPE, DESCRIPTIONTYPE, IMAGE, CLASSNAME" +
				" FROM " + TABLE_NAME;
		List<OperatorType> result = new ArrayList<OperatorType>();
		OperatorType obj = null;
		try {
			ResultSet res = dal.prepare(query).executeQuery();
			while(res.next()) {
				obj = new OperatorType(res.getLong(1), res.getString(2), res.getString(3), res.getString(4),res.getString(5));
				result.add(obj);			
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
