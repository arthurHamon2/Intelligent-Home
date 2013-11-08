package server.database.service;

import server.database.dao.AbstractDao;
import server.database.dao.TOperatorTypeDao;
import server.models.OperatorType;

public class OperatorTypeService extends AbstractService<OperatorType, Long>
{

    public OperatorTypeService(AbstractDao<OperatorType, Long> dao)
    {
        super(dao);
    }
    
    public OperatorTypeService()
    {
        super(new TOperatorTypeDao());
    }

}
