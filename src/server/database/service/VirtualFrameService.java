package server.database.service;

import server.database.dao.AbstractDao;
import server.database.dao.TUserDao;
import server.database.dao.TVirtualFrameDao;
import server.models.VirtualFrame;

public class VirtualFrameService extends AbstractService<VirtualFrame, Long> {

	private TVirtualFrameDao dao;
	
	public VirtualFrameService(AbstractDao<VirtualFrame, Long> dao) {
		super(dao);
	}
	
    public VirtualFrameService()
    {
        super(new TVirtualFrameDao());
        this.dao = (TVirtualFrameDao) this.getDao();
    }

}
