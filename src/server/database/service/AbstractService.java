package server.database.service;

import java.util.List;

import server.database.dao.AbstractDao;
import server.dsl.ParserException;

public abstract class AbstractService<T, ID>
{
    private AbstractDao<T, ID> dao;
    
    public AbstractService(AbstractDao<T, ID> dao)
    {
        this.dao = dao;
    }
    
    protected AbstractDao<T, ID> getDao()
    {
        return this.dao;
    }
        
    public T find(ID id)
    {
        return this.getDao().find(id);
    }
    
    public List<T> findAll()
    {
        return this.getDao().findAll();
    }
    
    public void create(T obj)
    {
        this.getDao().create(obj);
    }
    
    public void update(T obj)
    {
        this.getDao().update(obj);
    }
    
    public void delete(T obj)
    {
        this.getDao().delete(obj);
    }

}
