package server.database.service;

import java.util.List;

import server.database.dao.TActionDao;
import server.database.dao.TOperatorDao;
import server.models.Action;
import server.models.ActionOperator;
import server.models.Operator;

public class OperatorService extends AbstractService<Operator, Long>
{

    private TOperatorDao dao;
    private TActionDao actionDao;
    
    public OperatorService(TOperatorDao dao)
    {
        super(dao); 
    }
    
    public OperatorService()
    {
        super(new TOperatorDao());
        this.dao = (TOperatorDao) super.getDao();
        this.actionDao = new TActionDao();
    }

    @Override
    public void create(Operator obj)
    {
        super.create(obj);
    }
    
    @Override
    public void update(Operator obj)
    {
        super.update(obj);
    }
    
    public void createActionTodo(Action a,Operator o){
    	List<ActionOperator> list = this.dao.findAllActionTodo();
    	ActionOperator test = new ActionOperator(a, o);
    	//if(!list.isEmpty())
    	//{
    		if(!list.contains(test)){
    			this.dao.addActionTodo(a, o);
	    	}
	    	else{
	    		System.out.println("Action todo already exist !");
	    	} 	
    	//}
    }
    
    public List<ActionOperator> getActionTodo()
    {
    	List<ActionOperator> l = this.dao.findAllActionTodo();
    	// Drop all the actions to do after being catched 
    	this.dao.deleteAllActionsTodo();
    	return l;
    }
    
    //TODO: arthur: � tester !!
    public List<Action> findActions(Operator obj){
    	Long idType = obj.getOperatorType().getIdType();
    	actionDao.findByType(idType);
    	return actionDao.findByType(idType);
    }
    
    //TODO: arthur: � tester !!
    public List<Operator> getInstalled()
    {
    	return this.dao.findByInstalled(false);
    }
    
    /**
     * Get the action's historic by the operator ref.
     * The action has two dates, an added date and an execution date to show the delay between the order and the execution.
     * @param ref the operator ref
     * @return An action list, the historic of the actions did by the operator
     */
    public List<Action> historicOf(Long ref)
    {
    	return this.dao.historicOf(ref);
    }
    
}
