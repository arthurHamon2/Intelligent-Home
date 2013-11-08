package server.database.service;

import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import server.database.dao.TMeasureDao;
import server.database.dao.TSensorDao;
import server.models.Measure;
import server.models.Sensor;


public class SensorService extends AbstractService<Sensor, Long>
{

    private TSensorDao dao;
    private TMeasureDao measureDao;

    public SensorService(TSensorDao dao, TMeasureDao measureDao)
    {
        super(dao);
        this.dao = dao;
        this.measureDao = measureDao;
    }

    public SensorService()
    {
        super(new TSensorDao());
        this.dao = (TSensorDao) super.getDao();
        this.measureDao = new TMeasureDao();
    }
    
    @Override
    public void create(Sensor obj)
    {
        super.create(obj);
        this.persistChildren(obj);
    }
    
    @Override
    public void update(Sensor obj)
    {
        this.cleanChildren(obj);
        super.update(obj);
        this.persistChildren(obj);
    }
    
    public void persist(Sensor obj)
    {
    	if (obj.getSensorRef()!=0)
    	{
	        if (dao.find(obj.getSensorRef()) == null)
	        {
	            this.create(obj);
	        }
	        else
	            this.update(obj);
    	}
    }
    
    @Override
    public void delete(Sensor obj)
    {
       // Iterator<Measure> i = obj.getMeasures().iterator();
    	Iterator<Measure> i = measureDao.findByRef(obj.getSensorRef()).iterator();
        while (i.hasNext()){
        	Measure m = i.next();
            this.measureDao.delete(m);
        }
        
        super.delete(obj);
    }
    
    @Override
    public Sensor find(Long id)
    {
        Sensor r = super.find(id);
        Iterator<Measure> cs = this.measureDao.findByRef(id).iterator();
        int i = 0;
        while (cs.hasNext()){
            Measure c = cs.next();
            r.getMeasures().get(i).setSensorRef(c.getSensorRef());
            r.getMeasures().get(i).setIdMeasure(c.getIdMeasure());
            r.getMeasures().get(i).setValue(c.getValue());
            i++;
        }
        
        return r;
    }
    
    
    @Override
    public List<Sensor> findAll()
    {
        Map<Long, Sensor> r = this.mapSensor(super.findAll());
        Iterator<Measure> ms = this.measureDao.findAll().iterator();
        Measure m = null;
        Map<Long, Integer> i = new HashMap<Long, Integer>();
       
        Iterator<Long> ids = r.keySet().iterator();
        while (ids.hasNext()){
        	Long temp = ids.next();
            i.put(temp, 0);
        }
        
        while (ms.hasNext())
        {
            m = ms.next();
            List<Measure> tempList = r.get(m.getSensorRef()).getMeasures();
            tempList.get(i.get(m.getSensorRef())).setValue(m.getValue());
            // ARTHUR
            tempList.get(i.get(m.getSensorRef())).setMeasureType(m.getMeasureType());
            tempList.get(i.get(m.getSensorRef())).setIdMeasure(m.getIdMeasure());
            // ARTHUR
            i.put(m.getSensorRef(), i.get(m.getSensorRef()) + 1);
        }
        
        return new ArrayList<Sensor>(r.values());
    }
    

    
    private void persistChildren(Sensor s)
    {
        
        Iterator<Measure> i = s.getMeasures().iterator();
        Measure m;
        while (i.hasNext())
        {
            m = i.next();
            m.setSensorRef(s.getSensorRef());
            
            if (m.getIdMeasure() == 0)
            {    this.measureDao.create(m);
            }
            else{
                this.measureDao.update(m);
            }
        }
    }
    
    private void cleanChildren(Sensor s)
    {
        Map<Long, Measure> olds = this.mapMeasure(this.dao.find(s.getSensorRef()).getMeasures());
        Set<Long> olds_id = olds.keySet();
        
        Map<Long, Measure> news = this.mapMeasure(s.getMeasures());
        Set<Long> news_id = news.keySet();
        
        olds_id.removeAll(news_id);
        Iterator<Long> id = olds_id.iterator();
        while (id.hasNext())
            this.measureDao.delete(olds.get(id.next())); // TODO : delete rule
    }
    
    private Map<Long, Measure> mapMeasure(List<Measure> f)
    {
        HashMap<Long, Measure> r = new HashMap<Long, Measure>();
        Measure m;
        Iterator<Measure> i = f.iterator();
        while (i.hasNext())
        {
            m = i.next();
            r.put(m.getIdMeasure(), m);
        }
        
        r.remove(null);
        r.remove(0);
        
        return r;
    }
    
    private Map<Long, Sensor> mapSensor(List<Sensor> f)
    {
        HashMap<Long, Sensor> r = new HashMap<Long, Sensor>();
        Sensor m;
        Iterator<Sensor> i = f.iterator();
        while (i.hasNext())
        {
            m = i.next();
            r.put(m.getSensorRef(), m);
        }
        
        r.remove(null);
        r.remove(0);
        
        return r;
    }

    public List<Measure> historicOf(Long id)
    {
    	return this.dao.historicOf(id);
    }
    
    public List<Sensor> findSensorInstalledByRoom(Long idRoom){
        Map<Long, Sensor> r = this.mapSensor(this.dao.findSensorInstalledByRoom(idRoom));
        if(!r.isEmpty()){
	        Iterator<Measure> ms = this.measureDao.findAll().iterator();
	        Measure m = null;
	        Map<Long, Integer> i = new HashMap<Long, Integer>();
	       
	        Iterator<Long> ids = r.keySet().iterator();
	        while (ids.hasNext()){
	        	Long temp = ids.next();
	            i.put(temp, 0);
	        }
	        
	        while (ms.hasNext())
	        {
	            m = ms.next();
	            Long sensorRef = m.getSensorRef();
	            Sensor s = r.get(sensorRef);
	            //Le capteur n'est pas installé, mais possède une mesure
	            if(s != null){
		            List<Measure> tempList = s.getMeasures();
		            tempList.get(i.get(m.getSensorRef())).setValue(m.getValue());
		            // ARTHUR
		            tempList.get(i.get(m.getSensorRef())).setMeasureType(m.getMeasureType());
		            tempList.get(i.get(m.getSensorRef())).setIdMeasure(m.getIdMeasure());
		            // ARTHUR
		            i.put(m.getSensorRef(), i.get(m.getSensorRef()) + 1);
	            }
	        }
	        return new ArrayList<Sensor>(r.values());
        }
        return new ArrayList<Sensor>();
    }
    
    public List<Sensor> findSensorInstalled(){
        Map<Long, Sensor> r = this.mapSensor(this.dao.findAllByInstalled(true));
        if(!r.isEmpty()){
	        Iterator<Measure> ms = this.measureDao.findAll().iterator();
	        Measure m = null;
	        Map<Long, Integer> i = new HashMap<Long, Integer>();
	       
	        Iterator<Long> ids = r.keySet().iterator();
	        while (ids.hasNext()){
	        	Long temp = ids.next();
	            i.put(temp, 0);
	        }
	        
	        while (ms.hasNext())
	        {
	            m = ms.next();
	            Long sensorRef = m.getSensorRef();
	            Sensor s = r.get(sensorRef);
	            //Le capteur n'est pas installé, mais possède une mesure
	            if(s != null){
		            List<Measure> tempList = s.getMeasures();
		            tempList.get(i.get(m.getSensorRef())).setValue(m.getValue());
		            // ARTHUR
		            tempList.get(i.get(m.getSensorRef())).setMeasureType(m.getMeasureType());
		            tempList.get(i.get(m.getSensorRef())).setIdMeasure(m.getIdMeasure());
		            // ARTHUR
		            i.put(m.getSensorRef(), i.get(m.getSensorRef()) + 1);
	            }
	        }
	        return new ArrayList<Sensor>(r.values());
        }
        return new ArrayList<Sensor>();
    }
}
