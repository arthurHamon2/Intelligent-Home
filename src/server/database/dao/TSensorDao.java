package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.models.Measure;
import server.models.MeasureType;
import server.models.Room;
import server.models.Sensor;
import server.models.SensorType;

public class TSensorDao extends AbstractDao<Sensor, Long> {

	private final String TABLE_NAME = "T_SENSOR";
	private final String TABLE_HISTORIC_NAME = "TH_SENSOR";
	private final String TABLE_TYPE_NAME = "T_SENSOR_TYPE";
	private final String TABLE_MEASURE_TYPE_NAME = "T_MEASURE_TYPE";
	private final String TABLE_MEASURE_NAME = "T_MEASURE";
	private final String TABLE_ROOM = "T_ROOM";

	public TSensorDao() {

	}

	@Override
	public void create(Sensor obj) {
		try {
			PreparedStatement stat = this.dal.prepare("INSERT INTO "
					+ TABLE_NAME
					+ " (SENSORREF, IDTYPE, SENSORTITLE, INSTALLED, IDROOM) "
					+ "VALUES( ? ," + " (SELECT IDTYPE FROM " + TABLE_TYPE_NAME
					+ "  WHERE SENSORCLASS = ?) , ? , ?, ? )");

			stat.setLong(1, obj.getSensorRef());
			stat.setString(2, obj.getClass().getName());
			stat.setString(3, obj.getTitle());
			stat.setBoolean(4, obj.isInstalled());
			stat.setLong(5, obj.getRoom().getId());
			// Execute the query
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Sensor obj) {
		try {
			PreparedStatement stat = this.dal.prepare("DELETE FROM "
					+ TABLE_NAME + " WHERE SENSORREF = ?");
			stat.setLong(1, obj.getSensorRef());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Sensor find(Long ref) {
		String query = "SELECT C.SENSORREF, C.SENSORTITLE, T.SENSORCLASS, C.INSTALLED, "
				+ "T.IDTYPE, T.EED, T.FRAMECLASS, T.TITLETYPE, T.IMAGE, C.IDROOM, R.ROOMTITLE," +
						" Xleft , Yleft , Xright , Yright"
				+ " FROM "
				+ TABLE_NAME
				+ " C, "
				+ TABLE_TYPE_NAME
				+ " T, "
				+ TABLE_ROOM
				+ " R "
				+ " WHERE C.SENSORREF = ? AND T.IDTYPE = C.IDTYPE AND R.IDROOM = C.IDROOM";
		Sensor obj = null;
		try {
			PreparedStatement stat = dal.prepare(query);
			stat.setLong(1, ref.longValue());
			ResultSet res = stat.executeQuery();
			if (res.next()) {
				obj = (Sensor) Class.forName(res.getString(3))
						.getConstructor(Long.class, String.class)
						.newInstance(ref.longValue(), res.getString(2));
				obj.setInstalled(res.getBoolean(4));
				obj.setSensorType(new SensorType(res.getLong(5), res
						.getString(6), res.getString(3), res.getString(7), res
						.getString(8), res.getString(9)));
				obj.setRoom(new Room(res.getLong(10), null, res.getString(11),res.getInt(12),res.getInt(13),res.getInt(14),res.getInt(15)));
				res.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	public List<Sensor> findSensorInstalledByRoom(Long id) {
		String query = "SELECT C.SENSORREF, C.SENSORTITLE, T.SENSORCLASS, C.INSTALLED, "
				+ "T.IDTYPE, T.EED, T.FRAMECLASS, T.TITLETYPE, T.IMAGE, C.IDROOM, R.ROOMTITLE," +
				" Xleft , Yleft , Xright , Yright"
				+ " FROM "
				+ TABLE_NAME
				+ " C, "
				+ TABLE_TYPE_NAME
				+ " T, "
				+ TABLE_ROOM
				+ " R "
				+ " WHERE C.IDROOM = ? AND C.INSTALLED = ? AND T.IDTYPE = C.IDTYPE AND R.IDROOM = C.IDROOM";
		Sensor obj = null;
		List<Sensor> result = new ArrayList<Sensor>();
		try {
			PreparedStatement stat = dal.prepare(query);
			stat.setLong(1, id.longValue());
			stat.setBoolean(2, true);
			ResultSet res = stat.executeQuery();
			while (res.next()) {
				obj = (Sensor) Class.forName(res.getString(3))
						.getConstructor(Long.class, String.class)
						.newInstance(res.getLong(1), res.getString(2));
				obj.setInstalled(res.getBoolean(4));
				obj.setSensorType(new SensorType(res.getLong(5), res
						.getString(6), res.getString(3), res.getString(7), res
						.getString(8), res.getString(9)));
				obj.setRoom(new Room(res.getLong(10), null, res.getString(11),res.getInt(12),res.getInt(13),res.getInt(14),res.getInt(15)));
				result.add(obj);
			}
			res.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public void update(Sensor obj) {
		try {
			PreparedStatement stat = this.dal.prepare("UPDATE " + TABLE_NAME
					+ " SET SENSORTITLE = ?, INSTALLED = ?, IDROOM = ?, "
					//+ "IDTYPE=(SELECT IDTYPE FROM " + TABLE_TYPE_NAME +" WHERE SENSORCLASS = ?)"
					+ " IDTYPE = ? "
					+ " WHERE SENSORREF = ?");

			stat.setString(1, obj.getTitle());
			stat.setBoolean(2, obj.isInstalled());
			stat.setLong(3, obj.getRoom().getId());
			stat.setLong(4, obj.getSensorType().getIdType());
			stat.setLong(5, obj.getSensorRef());

			// Execute the query
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Sensor> findByType(Long idType) {
		String query = "SELECT C.SENSORREF, C.SENSORTITLE, T.SENSORCLASS, C.INSTALLED, "
				+ "T.IDTYPE, T.EED, T.FRAMECLASS, T.TITLETYPE, T.IMAGE, C.IDROOM, R.ROOMTITLE," +
				" Xleft , Yleft , Xright , Yright"
				+ " FROM "
				+ TABLE_NAME
				+ " C, "
				+ TABLE_TYPE_NAME
				+ " T, "
				+ TABLE_ROOM
				+ " R "
				+ " WHERE C.IDTYPE = ? AND T.IDTYPE = C.IDTYPE AND R.IDROOM = C.IDROOM";
		List<Sensor> result = new ArrayList<Sensor>();
		try {
			PreparedStatement stat = dal.prepare(query);
			stat.setLong(1, idType);
			ResultSet res = stat.executeQuery();
			while (res.next()) {
				Sensor obj = (Sensor) Class.forName(res.getString(3))
						.getConstructor(Long.class, String.class)
						.newInstance(res.getLong(1), res.getString(2));
				obj.setInstalled(res.getBoolean(4));
				obj.setSensorType(new SensorType(res.getLong(5), res
						.getString(6), res.getString(3), res.getString(7), res
						.getString(8), res.getString(9)));
				obj.setRoom(new Room(res.getLong(10), null, res.getString(11),res.getInt(12),res.getInt(13),res.getInt(14),res.getInt(15)));
				result.add(obj);
			}
			res.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Sensor> findAll() {
		String query = "SELECT C.SENSORREF, C.SENSORTITLE, T.SENSORCLASS, C.INSTALLED, "
				+ "T.IDTYPE, T.EED, T.FRAMECLASS, T.TITLETYPE, T.IMAGE , C.IDROOM, R.ROOMTITLE, " +
				" Xleft , Yleft , Xright , Yright"
				+ " FROM "
				+ TABLE_NAME
				+ " C, "
				+ TABLE_TYPE_NAME
				+ " T, "
				+ TABLE_ROOM
				+ " R "
				+ " WHERE T.IDTYPE = C.IDTYPE AND R.IDROOM = C.IDROOM";
		List<Sensor> result = new ArrayList<Sensor>();
		try {
			ResultSet res = dal.prepare(query).executeQuery();
			while (res.next()) {
				Sensor obj = (Sensor) Class.forName(res.getString(3))
						.getConstructor(Long.class, String.class)
						.newInstance(res.getLong(1), res.getString(2));
				obj.setInstalled(res.getBoolean(4));
				obj.setSensorType(new SensorType(res.getLong(5), res
						.getString(6), res.getString(3), res.getString(7), res
						.getString(8), res.getString(9)));
				obj.setRoom(new Room(res.getLong(10), null, res.getString(11),res.getInt(12),res.getInt(13),res.getInt(14),res.getInt(15)));
				result.add(obj);
			}
			res.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Sensor> findAllByInstalled(boolean installed) {
		String query = "SELECT C.SENSORREF, C.SENSORTITLE, T.SENSORCLASS, C.INSTALLED, "
				+ "T.IDTYPE, T.EED, T.FRAMECLASS, T.TITLETYPE, T.IMAGE , C.IDROOM, R.ROOMTITLE," +
				" Xleft , Yleft , Xright , Yright"
				+ " FROM "
				+ TABLE_NAME
				+ " C, "
				+ TABLE_TYPE_NAME
				+ " T, "
				+ TABLE_ROOM
				+ " R "
				+ " WHERE C.INSTALLED = ? AND T.IDTYPE = C.IDTYPE AND R.IDROOM = C.IDROOM";
		List<Sensor> result = new ArrayList<Sensor>();
		try {
			PreparedStatement stat = dal.prepare(query);
			stat.setBoolean(1, installed);
			ResultSet res = stat.executeQuery();

			while (res.next()) {
				Sensor obj = (Sensor) Class.forName(res.getString(3))
						.getConstructor(Long.class, String.class)
						.newInstance(res.getLong(1), res.getString(2));
				obj.setInstalled(res.getBoolean(4));
				obj.setSensorType(new SensorType(res.getLong(5), res
						.getString(6), res.getString(3), res.getString(7), res
						.getString(8), res.getString(9)));
				obj.setRoom(new Room(res.getLong(10), null, res.getString(11),res.getInt(12),res.getInt(13),res.getInt(14),res.getInt(15)));
				result.add(obj);
			}
			res.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Measure> historicOf(Long idMeasure) { 
    	TMeasureTypeDao daoType = new TMeasureTypeDao();  
		String query =
    			  "SELECT t.SENSORREF, th.IDMEASURETYPE, th.VALUE, th.MODIFIEDAT FROM " +
    					  TABLE_NAME
    					  +" t JOIN "+TABLE_HISTORIC_NAME+" th ON (t.SENSORREF = th.SENSORREF)"
    					  +" JOIN "+TABLE_MEASURE_NAME+" measure ON (th.IDMEASURE = measure.IDMEASURE)"
    			/*		  +" JOIN "+TABLE_TYPE_NAME+" type ON (t.IDTYPE = type.IDTYPE)"
    					  +" JOIN "+TABLE_MEASURE_TYPE_NAME+" tm ON (tm.IDMEASURETYPE = th.IDMEASURETYPE)" */
    					  +" WHERE measure.IDMEASURE = "+idMeasure; 
    	  List<Measure> result = new ArrayList<Measure>();
    	  try { 
    		  ResultSet res = dal.prepare(query).executeQuery();
    		  while(res.next()){ 
	    		  Measure m = new Measure(idMeasure, res.getLong(1), 
	    				  daoType.find(res.getLong(2))
	    				  /*new MeasureType(res.getLong(4), res.getString(7), res.getString(8))*/
	    		  		, res.getDouble(3));
	    		  m.setModifiedat(res.getString(4));
	              result.add(m); 
              }
    		  res.close(); 
		  } catch(Exception e) 
		  { 
			  e.printStackTrace(); 
		  } 
    	  return result; 
    }

}