package server.database.service;

import java.sql.SQLException;
import java.util.List;

import server.database.dal.ConnectionException;
import server.database.dal.Dal;
import server.database.dao.AbstractDao;
import server.database.dao.TRuleDao;
import server.dsl.Builder;
import server.dsl.ParserException;
import server.models.Rule;


public class RuleService extends AbstractService<Rule, Integer>
{

    public RuleService(AbstractDao<Rule, Integer> dao)
    {
        super(dao);
    }

    public RuleService()
    {
        super(new TRuleDao());
    }
    
    @Override
    public void create(Rule obj)
    {
        super.create(obj);
        if (obj.isEnable())
        {
            Builder b = new Builder();
            String r;
            try
            {
                r = b.generate("MyR" + obj.getId(), obj.getContents());
                try
                {
                    Dal.instance().prepare(r).execute();
                }
                catch (Exception e)
                {
                    this.getDao().delete(obj);
                    e.printStackTrace();
                }
            }
            catch (ParserException e1)
            {
                e1.printStackTrace();
            }
        }
    }
    
    @Override
    public void update(Rule obj)
    {
        Rule old = getDao().find(obj.getId());
        this.deleteTrigger(old.getId());

        super.update(obj);
        if (obj.isEnable())
        {
            Builder b = new Builder();
            try
            {
                String r = b.generate("MyR" + obj.getId(), obj.getContents());
                try
                {
                    Dal.instance().prepare(r).execute();
                }
                catch (Exception e)
                {
                    this.getDao().update(old);
                    this.deleteTrigger(obj.getId());
                    if (old.isEnable())
                    {
                        r = b.generate("MyR" + old.getId(), old.getContents());
                        try
                        {
                            Dal.instance().prepare(r).execute();
                        }
                        catch (Exception e2)
                        {
                            e2.printStackTrace();
                        }
                    }
                    e.printStackTrace();
                }
            }
            catch (ParserException e1)
            {
                e1.printStackTrace();
            }
        }
    }
    
    public void persist(List<Rule> rules){
    	for(Rule r : rules){
    		if(this.find(r.getId()) != null){
    			this.update(r);
    		}
    		else{
    			this.create(r);
    		}
    	}
    }
    
    @Override
    public void delete(Rule obj)
    {
        super.delete(obj);
        this.deleteTrigger(obj.getId());
    }
    
    protected void deleteTrigger(Integer id)
    {
        try
        {
            if (id != null)
                Dal.instance().prepare("DROP TRIGGER MyR" + id + ";").execute();
        }
        catch (Exception e)
        {
        }
    }

}
