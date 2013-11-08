package server.database.service;

import server.database.dao.AbstractDao;
import server.database.dao.TSensorTypeDao;
import server.database.dao.TUserDao;
import server.models.User;


public class UserService extends AbstractService<User, Integer>
{

	private TUserDao dao;
	
    public UserService(AbstractDao<User, Integer> dao)
    {
        super(dao);
    }

    public UserService()
    {
        super(new TUserDao());
        this.dao = (TUserDao) this.getDao();
    }
    
    
    @Override
    public void create(User obj)
    {
    	obj.cryptPassword();
        super.create(obj);
    }
    
    @Override
    public void update(User obj)
    {
        super.update(obj);
    }
    
    public User findByUser(String login)
    {
    	return this.dao.findByUser(login);
    }
    
    public User authentificate(String login, String pwd)
    {
    	User retour =null;
        TUserDao d = new TUserDao();
        User u = d.findByUser(login);
        if (u != null)
        {
	        if (u.getPwd().equals(User.crypt(pwd)))
	        {
	        	retour = u;
	        }
        }
        
        return retour;
    }

}
