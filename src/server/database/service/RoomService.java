package server.database.service;

import server.database.dao.AbstractDao;
import server.database.dao.TRoomDao;
import server.models.Room;

public class RoomService extends AbstractService<Room, Long> {

    private TRoomDao dao;
	
	public RoomService(AbstractDao<Room, Long> dao) {
		super(dao);
	}
	
    public RoomService()
    {
        super(new TRoomDao());
        this.dao = (TRoomDao) super.getDao();
    }
	
	public Room findByTitle(String title)
	{
		return this.dao.findByTitle(title);
	}

}
