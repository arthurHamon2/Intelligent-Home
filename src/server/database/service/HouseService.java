package server.database.service;

import java.util.List;

import server.database.dao.AbstractDao;
import server.database.dao.THouseDao;
import server.models.*;

public class HouseService extends AbstractService<House, Long> {

    private THouseDao dao;
	
	public HouseService(AbstractDao<House, Long> dao) {
		super(dao);
	}
	
    public HouseService()
    {
        super(new THouseDao());
        this.dao = (THouseDao) super.getDao();
    }
	
	public House findByTitle(String title)
	{
		return this.dao.findByTitle(title);
	}
	
	public List<Room> findRooms(Long idHouse){
		return this.dao.findRooms(idHouse);
	}

}
