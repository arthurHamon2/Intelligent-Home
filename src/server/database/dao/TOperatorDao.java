package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.models.*;

public class TOperatorDao extends AbstractDao<Operator,Long> {

	private final String TABLE_NAME = "T_OPERATOR";
	private final String TABLE_TYPE_NAME ="T_OPERATOR_TYPE";
	private final String TABLE_ACTION_NAME = "T_ACTION";
	private final String TABLE_ACTION_TODO = "T_ACTIONS_TODO";
	private final String TABLE_HISTORIC_NAME = "TH_ACTION";

	
	private TActionDao actionDao;
	private TOperatorTypeDao typeDao;
	
	public TOperatorDao(){
		this.typeDao = new TOperatorTypeDao();
		this.actionDao = new TActionDao();
	}

	@Override
	public void create(Operator obj) {
		try {
			PreparedStatement stat = this.dal.prepare("INSERT INTO "
					+ TABLE_NAME + " (OPERATORREF, IDROOM, IDOPERATORTYPE, TITLE , DESCRIPTION ) "
					+ "VALUES( ? ,? , ? , ?, ?  )");
			stat.setLong(1, obj.getOperatorRef());
			stat.setLong(2, obj.getRoom().getId());
			stat.setLong(3, obj.getOperatorType().getIdType());
			stat.setString(4, obj.getTitle());
			stat.setString(5, obj.getDescription());
			// Execute the query
			stat.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Operator obj) {
		try {
			PreparedStatement stat = this.dal.prepare("DELETE FROM " + TABLE_NAME + " WHERE OPERATORREF = "
					+ obj.getOperatorRef());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Operator obj) {
        try { 
            PreparedStatement stat = this.dal.prepare("UPDATE " + TABLE_NAME
                    + " SET TITLE = ?, DESCRIPTION = ?, IDROOM = ?"
                    + " WHERE OPERATORREF = ?");
            stat.setString(1, obj.getTitle());
            stat.setString(2, obj.getDescription());
            stat.setLong(3, obj.getRoom().getId());
            stat.setLong(4, obj.getOperatorRef());
            // Execute the query
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public Operator find(Long ref) {
		String query = "SELECT OPERATORREF, IDOPERATORTYPE, TITLE , DESCRIPTION, IDROOM, CLASSNAME" +
				" FROM " + TABLE_NAME + " JOIN "+TABLE_TYPE_NAME + " ON (IDOPERATORTYPE = IDTYPE) "+
				" WHERE OPERATORREF = ?";
		Operator obj = null;
		TOperatorTypeDao dao = new TOperatorTypeDao();
		TRoomDao daoRoom = new TRoomDao();
		try {
			PreparedStatement stat = dal.prepare(query);
			stat.setLong(1, ref.longValue());
			ResultSet res = stat.executeQuery();
			if(res.next()) {
				 obj = (Operator) Class.forName(res.getString(6))
				.getConstructor(Long.class, OperatorType.class ,String.class, String.class)
				.newInstance(res.getLong(1), dao.find(res.getLong(2)), res.getString(3), res.getString(4));
				obj.setRoom(daoRoom.find(res.getLong(5)));
				res.close();			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
		
	public List<Operator> findAll() {
		String query = "SELECT OPERATORREF, IDOPERATORTYPE, TITLE , DESCRIPTION, IDROOM, CLASSNAME FROM " 
			+ TABLE_NAME + " T JOIN "+TABLE_TYPE_NAME + " TY ON( T.IDOPERATORTYPE = TY.IDTYPE) ";
		List<Operator> result = new ArrayList<Operator>();
		Operator obj = null;
		TRoomDao daoRoom = new TRoomDao();
		TOperatorTypeDao dao = new TOperatorTypeDao();
		try {
			ResultSet res = dal.prepare(query).executeQuery();
			while(res.next()) {
				obj = (Operator) Class.forName(res.getString(6))
				.getConstructor(Long.class, OperatorType.class ,String.class, String.class)
				.newInstance(res.getLong(1), dao.find(res.getLong(2)), res.getString(3), res.getString(4));
				obj.setRoom(daoRoom.find(res.getLong(5)));
				result.add(obj);			
			}
			res.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Operator> findByInstalled(boolean installed) {
		String query = "SELECT  OPERATORREF, IDOPERATORTYPE, TITLE , DESCRIPTION, IDROOM, CLASSNAME " +
				" FROM " + TABLE_NAME + " T JOIN "+TABLE_TYPE_NAME + " TY ON( T.IDOPERATORTYPE = TY.IDTYPE) "+
				" WHERE INSTALLED = "+ installed;;
		List<Operator> result = new ArrayList<Operator>();
		Operator obj = null;
		TRoomDao daoRoom = new TRoomDao();
		TOperatorTypeDao dao = new TOperatorTypeDao();
		try {
			ResultSet res = dal.prepare(query).executeQuery();
			while(res.next()) {
				obj = (Operator) Class.forName(res.getString(6))
				.getConstructor(Long.class, OperatorType.class ,String.class, String.class)
				.newInstance(res.getLong(1), dao.find(res.getLong(2)), res.getString(3), res.getString(4));
				obj.setRoom(daoRoom.find(res.getLong(5)));
				result.add(obj);			
			}
			res.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void addActionTodo(Action a, Operator o)
	{
		try {
			PreparedStatement stat = this.dal.prepare("INSERT INTO "
					+ TABLE_ACTION_TODO + " (IDACTION, OPERATORREF, ADDEDAT ) "
					+ "VALUES( ? , ? , datetime('now')  )");
			stat.setLong(1, a.getId());
			stat.setLong(2, o.getOperatorRef());
			// Execute the query
			stat.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<ActionOperator> findAllActionTodo()
	{
		String query = "SELECT IDACTION, OPERATORREF, ADDEDAT FROM "
				+ TABLE_ACTION_TODO;
		List<ActionOperator> result = new ArrayList<ActionOperator>();
		ActionOperator obj = null;
		TActionDao actionDao = new TActionDao(); 
		try {
			ResultSet res = dal.prepare(query).executeQuery();
			while(res.next()) {
				obj = new ActionOperator(actionDao.find(res.getLong(1)), find(res.getLong(2)));	
				result.add(obj);	
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void deleteAllActionsTodo() {
		try {
			PreparedStatement stat = this.dal.prepare("DELETE FROM " + TABLE_ACTION_TODO);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Action> historicOf(Long idOperator) { 
	  	  String query =
	  			  "SELECT th.IDACTION, th.OPERATORREF , th.ADDEDAT, th.EXECUTEDAT"+
	  			  /*, t.FRAME, t.TITLEACTION, t.DESCRIPTIONACTION, op.TITLE, op.DESCRIPTION" +*/
	  			  " FROM " +
	  			  			TABLE_ACTION_NAME
	  					  +" t JOIN "+TABLE_HISTORIC_NAME+" th ON (t.IDACTION = th.IDACTION)"
	  					  +" JOIN "+TABLE_NAME+" op ON (op.OPERATORREF = th.OPERATORREF)"
	  					  +" WHERE th.OPERATORREF = "+idOperator.longValue(); 
	  	List<Action> actions = new ArrayList<Action>();
	  	  try { 
	  		  ResultSet res = dal.prepare(query).executeQuery();
	 
	  		  while(res.next()){ 
	  			  Operator op = this.find(res.getLong(2));
	  			  //new Operator(res.getLong(2), null , res.getString(8), res.getString(9));
	  			  Action act = actionDao.find(res.getLong(1));
	  				//  new Action(res.getLong(1), null , res.getString(5), res.getString(6), res.getString(7));
	  			  act.setAddedAt(res.getString(3));
	  			  act.setExecutedAt(res.getString(4));
	  			  actions.add(act);
	          }
	  		  res.close(); 
			  } catch(Exception e) 
			  { 
				  e.printStackTrace(); 
			  } 
	  	  return actions; 
	  }
}