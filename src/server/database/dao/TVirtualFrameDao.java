package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.models.Action;
import server.models.Sensor;
import server.models.VirtualFrame;

public class TVirtualFrameDao extends AbstractDao<VirtualFrame, Long> {

	private final String TABLE_NAME = "T_VIRTUAL_FRAME";
	
	@Override
	public void create(VirtualFrame obj) {
		
	}

	@Override
	public void delete(VirtualFrame obj) {
		
	}


	@Override
	public void update(VirtualFrame obj) {
		
	}
	
	@Override
	public VirtualFrame find(Long id) {
		return null;
	}

	@Override
	public List<VirtualFrame> findAll() {
		String query = "SELECT * FROM " + TABLE_NAME;
		List<VirtualFrame> result = new ArrayList<VirtualFrame>();
		VirtualFrame obj = null;
		try {
			ResultSet res = dal.prepare(query).executeQuery();
			while(res.next()) {
				obj = (VirtualFrame) Class.forName(res.getString(3))
				.getConstructor(Long.class, Long.class, String.class)
				.newInstance(res.getLong(1), res.getLong(2),res.getString(3));
				result.add(obj);			
			}
			res.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
