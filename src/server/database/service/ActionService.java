package server.database.service;

import java.util.List;

import server.database.dao.TActionDao;
import server.models.Action;

public class ActionService extends AbstractService<Action, Long>
{
    private TActionDao dao;

    public ActionService(TActionDao dao)
    {
        super(dao);
        this.dao = dao;
    }
    
    public ActionService()
    {
        super(new TActionDao());
        this.dao = (TActionDao) this.getDao();
    }
    
    public List<Action> findByType(Long id)
    {
        return this.dao.findByType(id);
    }

}
