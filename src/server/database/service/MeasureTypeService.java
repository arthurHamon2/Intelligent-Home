package server.database.service;

import server.database.dao.TMeasureTypeDao;
import server.models.MeasureType;

public class MeasureTypeService extends AbstractService<MeasureType, Long>
{
    private TMeasureTypeDao dao;

    public MeasureTypeService(TMeasureTypeDao dao)
    {
        super(dao);
        this.dao = dao;
    }

    public MeasureTypeService()
    {
        super(new TMeasureTypeDao());
        this.dao = (TMeasureTypeDao)this.getDao();
    }
    
    public MeasureType findByTitle(String title)
    {
        return this.dao.findByTitle(title);
    }

}
