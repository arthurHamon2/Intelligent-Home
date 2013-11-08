package server.database.dao;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.Main;
import server.models.*;

public class DaosTest
{

    private TOperatorDao operatorDao;
    private TRuleDao ruleDao;
    private TSensorDao sensorDao;
    private TUserDao userDao;
    private TMeasureDao measureDao;
    private TMeasureTypeDao typemeasureDao;
    private TSensorTypeDao sensorTypeDao;
    private TActionDao actionDao;
    private TOperatorTypeDao operatorTypeDao;
    private THouseDao houseDao;
    private TRoomDao roomDao;
    private TVirtualFrameDao virtualFrameDao;

    @Before
    public void initBeforeTest() throws Exception
    {
        this.operatorDao = new TOperatorDao();
        this.ruleDao = new TRuleDao();
        this.sensorDao = new TSensorDao();
        this.userDao = new TUserDao();
        this.measureDao = new TMeasureDao();
        this.typemeasureDao = new TMeasureTypeDao();
        this.sensorTypeDao = new TSensorTypeDao();
        this.actionDao = new TActionDao();
        this.operatorTypeDao = new TOperatorTypeDao();
        this.houseDao = new THouseDao(); 
        this.roomDao = new TRoomDao();
        this.virtualFrameDao = new TVirtualFrameDao();

    }

    @Test
    public void testOperatorPersistence()
    {
        // Check the persistance

        Long i = new Float(Math.random() * 10000.0 + 100.0).longValue();
        Operator o = new PlugOperator(i, operatorTypeDao.find(2L), "T", "D");
        operatorDao.create(o); // Create
        assertTrue(o.getOperatorRef() > 0);
        o = operatorDao.find(i);
        assertTrue(o.getOperatorRef() == i.longValue());
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getDescription(), "D");

        o.setTitle("T2");
        o.setDescription("D2");
        operatorDao.update(o); // Update
        o = operatorDao.find(o.getOperatorRef());
        assertTrue(o.getOperatorRef() == i.longValue());
        assertEquals(o.getTitle(), "T2");
        assertEquals(o.getDescription(), "D2");
        operatorDao.delete(o); // Delete
        assertNull(operatorDao.find(o.getOperatorRef()));
    }

    @Test
    public void testOperatorTypePersistence()
    {
        // Check the persistance

        Long i = new Float(Math.random() * 1000.0 + 1000.0).longValue();
        OperatorType o = new OperatorType(i, "T", "D","I", PlugOperator.class.getName());
        operatorTypeDao.create(o); // Create
        assertTrue(o.getIdType() > 0);
        Long oldId = o.getIdType();
        o = operatorTypeDao.find(o.getIdType());
        assertTrue(o.getIdType().longValue() == oldId.longValue());
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getDescription(), "D");
        assertEquals(o.getImage(), "I");

        o.setTitle("T2");
        o.setDescription("D2");
        o.setImage("I2");
        operatorTypeDao.update(o); // Update
        o = operatorTypeDao.find(o.getIdType());
        assertTrue(o.getIdType().longValue() == oldId.longValue());
        assertEquals(o.getTitle(), "T2");
        assertEquals(o.getDescription(), "D2");
        assertEquals(o.getImage(), "I2");
        operatorTypeDao.delete(o); // Delete
        assertNull(operatorTypeDao.find(o.getIdType()));
    }

    @Test
    public void testActionPersistence()
    {
        // Check the persistance
        Long i = new Float(Math.random() * 1000.0 + 1000.0).longValue();
        Action o = new Action(i, null, "F", "T", "D","I");
        o.setOperatorType(new OperatorType(1L, "test", "ceci est test","I",PlugOperator.class.getName()));
        actionDao.create(o); // Create
        assertTrue(o.getId() > 0);
        o = actionDao.find(o.getId());
        Long oldId = o.getId();
        assertTrue(o.getId().longValue() == oldId.longValue());
        assertNull(o.getOperatorType());
        assertEquals(o.getFrame(), "F");
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getDescription(), "D");

        o.setFrame("F2");
        o.setTitle("T2");
        o.setDescription("D2");
        o.setOperatorType(new OperatorType(1L, "test", "ceci est test","I",PlugOperator.class.getName()));

        actionDao.update(o); // Update

        o = actionDao.find(o.getId());
        assertTrue(o.getId().longValue() == oldId.longValue());
        assertNull(o.getOperatorType());
        assertEquals(o.getFrame(), "F2");
        assertEquals(o.getTitle(), "T2");
        assertEquals(o.getDescription(), "D2");

        actionDao.delete(o); // Delete
        assertNull(actionDao.find(o.getId()));
    }

    @Test
    public void testRulePersistence()
    {
        // Check the persistance
        Rule o = new Rule(42, "R", "T", "C", false);
        ruleDao.create(o); // Create
        assertTrue(o.getId() > 0);
        Integer oldId = o.getId();
        o = ruleDao.find(o.getId());
        assertEquals(oldId, o.getId());
        assertEquals(o.getRef(), "R");
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getContents(), "C");
        assertEquals(o.isEnable(), false);
        assertTrue(o.getId() == oldId);
        o.setRef("A").setTitle("B").setContents("D").setEnable(true);
        ruleDao.update(o); // Update
        o = ruleDao.find(o.getId());
        assertEquals(oldId, o.getId());
        assertEquals(o.getRef(), "A");
        assertEquals(o.getTitle(), "B");
        assertEquals(o.getContents(), "D");
        assertEquals(o.isEnable(), true);
        assertTrue(o.getId() == oldId);
        ruleDao.delete(o); // Delete
        assertNull(ruleDao.find(o.getId()));
    }

    @Test
    public void testSensorPersistence()
    {
        Long i = new Float(Math.random() * 1000.0 + 1000.0).longValue();
        Sensor o = new TemperatureSensor(i, "T");
        sensorDao.create(o); // Create
        assertTrue(o.getSensorRef() > 0);
        TemperatureSensor s = (TemperatureSensor) sensorDao.find(o
                .getSensorRef());
        assertTrue(s.getSensorRef().longValue() == i.longValue());
        assertEquals(s.getTitle(), "T");
        assertEquals(s.isInstalled(), false);
        assertEquals(s.getRoom().getId().longValue(), 1L);
        assertTrue(s instanceof TemperatureSensor);
        s.setTitle("B");
        s.setInstalled(true);
        sensorDao.update(s); // Update
        Sensor e = sensorDao.find(s.getSensorRef());
        assertTrue(s.getSensorRef() == i.longValue());
        assertEquals(e.getTitle(), "B");
        assertEquals(e.isInstalled(), true);
        assertEquals(s.getRoom().getId().longValue(), 1L);
        sensorDao.delete(e); // Delete
		assertEquals(sensorDao.find(e.getSensorRef()),null);
    }

    @Test
    public void testRoomPersistence(){
    	List<Point> points = new ArrayList<Point>();
    	Point p1= new Point(1, 1);
    	Point p2= new Point(10, 10);
    	points.add(p1);
    	points.add(p2);
    	Room o = new Room(1L, new House(1L,"empty house","empty"), "title",0,0,0,0);
    	roomDao.create(o);
    	assertTrue(o.getId() > 0);
        Long oldId = o.getId();
        o = roomDao.find(o.getId());
        // Room and house settings
        assertEquals(o.getId().longValue(), oldId.longValue());
        assertEquals(o.getTitle(), "title");
        assertEquals(o.getHouse().getId().longValue() , 1L);
        assertEquals(o.getHouse().getName() , "empty house");
        assertEquals(o.getHouse().getImage() , "empty");
        /////
        o.setHouse(new House(1L, "empty house","empty"));
        o.setTitle("title2");
        roomDao.update(o);
        o = roomDao.find(o.getId());
        // Room and house settings
        assertEquals(o.getId().longValue(), oldId.longValue());
        assertEquals(o.getTitle(), "title2");
        assertEquals(o.getHouse().getId().longValue() , 1L);
        assertEquals(o.getHouse().getName() , "empty house");
        assertEquals(o.getHouse().getImage() , "empty");
        /////
        roomDao.delete(o);
        assertNull(roomDao.find(o.getId()));
    }
    
    @Test
    public void testHousePersistence(){
    	House o = new House(1L, "test","image");
    	houseDao.create(o);
    	assertTrue(o.getId() > 0);
        Long oldId = o.getId();
        o = houseDao.find(o.getId());
        // house settings
        assertEquals(o.getId().longValue(), oldId.longValue());
        assertEquals(o.getName(), "test");
        assertEquals(o.getImage(), "image");
        /////
        o.setName("test2");
        o.setImage("img");
        houseDao.update(o);
        // house settings
        assertEquals(o.getId().longValue(),  oldId.longValue());
        assertEquals(o.getName(), "test2");
        assertEquals(o.getImage(), "img");
        /////
        houseDao.delete(o);
        assertNull(houseDao.find(o.getId()));
    }
    
    @Test
    public void testUserPersistence()
    {
        // Check the persistance
        User o = new User(42, "L", "M", "mail@gmail.com","I");
        userDao.create(o); // Create
        assertTrue(o.getId() > 0);
        Integer oldId = o.getId();
        o = userDao.find(o.getId());
        assertTrue(o.getId() == oldId);
        assertEquals(o.getLogin(), "L");
        assertEquals(o.getPwd(), "M");
        assertEquals(o.getMail(), "mail@gmail.com");
        assertEquals(o.getAvatar(), "I");
        o.setLogin("A").setPwd("B").setAvatar("I2");
        userDao.update(o); // Update
        o = userDao.find(o.getId());
        assertTrue(o.getId() == oldId);
        assertEquals(o.getLogin(), "A");
        assertEquals(o.getPwd(), User.crypt("B"));
        assertEquals(o.getAvatar(), "I2");
        userDao.delete(o); // Delete
        assertNull(userDao.find(o.getId()));
    }

    @Test
    public void testMeasurePersistence()
    {
        // Check the persistance
        Measure o = new Measure(100L, 32L, new MeasureType(1L, "A", "B",""), 11.0);
        measureDao.create(o); // Create
        assertTrue(o.getIdMeasure() > 0);
        Long oldId = o.getIdMeasure();
        o = measureDao.find(o.getIdMeasure());
        assertTrue(o.getIdMeasure().longValue() == oldId.longValue());
        assertEquals(o.getSensorRef().longValue(), 32L);
        assertEquals(o.getMeasureType().getIdMeasureType().longValue(), 1L);
        assertTrue(o.getValue() == 11.0);
        o.setSensorRef(132L);
        o.getMeasureType().setIdMeasureType(2L);
        o.setValue(111.0);
        measureDao.update(o); // Update
        o = measureDao.find(o.getIdMeasure());
        assertTrue(o.getIdMeasure().longValue() == oldId.longValue());
        assertEquals(o.getSensorRef().longValue(), 132L);
        assertEquals(o.getMeasureType().getIdMeasureType().longValue(), 2L);
        assertTrue(o.getValue() == 111.0);
        measureDao.delete(o); // Delete
        assertNull(measureDao.find(o.getIdMeasure()));
    }

    @Test
    public void testMeasureTypePersistence()
    {
        // Check the persistance
        MeasureType o = new MeasureType(50L, "TEMP", "C","");
        typemeasureDao.create(o); // Create
        assertTrue(o.getIdMeasureType() > 0);
        Long oldId = o.getIdMeasureType();
        o = typemeasureDao.find(o.getIdMeasureType());
        assertTrue(o.getIdMeasureType() == oldId);
        assertEquals(o.getTitle(), "TEMP");
        assertEquals(o.getUnity(), "C");
        o.setTitle("TEMP2");
        o.setUnity("C2");
        typemeasureDao.update(o); // Update
        o = typemeasureDao.find(o.getIdMeasureType());
        assertTrue(o.getIdMeasureType() == oldId);
        assertEquals(o.getTitle(), "TEMP2");
        assertEquals(o.getUnity(), "C2");
        typemeasureDao.delete(o); // Delete
        assertNull(typemeasureDao.find(o.getIdMeasureType()));
    }

    @Test
    public void testSensorTypePersistence()
    {
        // Check the persistance
        SensorType o = new SensorType(50L, "EED", "S", "F", "T",
                "thermometre.png");
        sensorTypeDao.create(o); // Create
        assertTrue(o.getIdType() > 0);
        Long oldId = o.getIdType();
        o = sensorTypeDao.find(o.getIdType());
        assertTrue(o.getIdType() == oldId);
        assertEquals(o.getEed(), "EED");
        assertEquals(o.getSensorClass(), "S");
        assertEquals(o.getFrameClass(), "F");
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getImage(), "thermometre.png");

        o.setEed("EED2");
        o.setSensorClass("S2");
        o.setFrameClass("F2");
        o.setTitle("T2");
        o.setImage("image2.png");

        sensorTypeDao.update(o); // Update
        o = sensorTypeDao.find(o.getIdType());
        assertTrue(o.getIdType() == oldId);
        assertEquals(o.getEed(), "EED2");
        assertEquals(o.getSensorClass(), "S2");
        assertEquals(o.getFrameClass(), "F2");
        assertEquals(o.getTitle(), "T2");
        assertEquals(o.getImage(), "image2.png");

        sensorTypeDao.delete(o); // Delete
        assertNull(sensorTypeDao.find(o.getIdType()));
    }

    @Test
    public void testFindByEED()
    {
        // Check the persistance
        SensorType o = new SensorType(50L, "EED", "S", "F", "T", "img.png");
        sensorTypeDao.create(o); // Create
        assertTrue(o.getIdType() > 0);
        Long oldId = o.getIdType();
        o = sensorTypeDao.findByEED("EED");
        assertTrue(o.getIdType() == oldId);
        assertEquals(o.getEed(), "EED");
        assertEquals(o.getSensorClass(), "S");
        assertEquals(o.getFrameClass(), "F");
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getImage(), "img.png");

        o.setEed("EED2");
        o.setSensorClass("S2");
        o.setFrameClass("F2");
        o.setTitle("T2");
        o.setImage("img2.png");

        sensorTypeDao.update(o); // Update
        o = sensorTypeDao.findByEED("EED2");
        assertTrue(o.getIdType() == oldId);
        assertEquals(o.getEed(), "EED2");
        assertEquals(o.getSensorClass(), "S2");
        assertEquals(o.getFrameClass(), "F2");
        assertEquals(o.getTitle(), "T2");
        assertEquals(o.getImage(), "img2.png");

        sensorTypeDao.delete(o); // Delete
        assertNull(sensorTypeDao.find(o.getIdType()));
    }

    @Test
    public void testFindBySensorRef()
    {
        // Check the persistance
        TemperatureSensor s = new TemperatureSensor(42L, "ST");
        sensorDao.create(s);
        SensorType o = sensorTypeDao.findBySensorRef(42L);
        assertTrue(o.getIdType().longValue() == 3);
        assertEquals(o.getEed(), "7-2-5");
        assertEquals(o.getSensorClass(), "server.models.TemperatureSensor");
        assertEquals(o.getFrameClass(),
                "server.Sensors.Frame.TemperatureFrame_7_2_5");
        assertEquals(o.getTitle(), "Temperature entre 0° et 40°");

        sensorDao.delete(s); // Delete
        assertEquals(sensorDao.find(s.getSensorRef()),null);
    }

    @Test
    public void testFindByTitle()
    {
        // Check the persistance
        MeasureType o = new MeasureType(50L, "TEMP", "C","");
        typemeasureDao.create(o); // Create
        assertTrue(o.getIdMeasureType() > 0);
        Long oldId = o.getIdMeasureType();
        o = typemeasureDao.findByTitle("TEMP");
        assertTrue(o.getIdMeasureType() == oldId);
        assertEquals(o.getTitle(), "TEMP");
        assertEquals(o.getUnity(), "C");
        o.setTitle("TEMP2");
        o.setUnity("C2");
        typemeasureDao.update(o); // Update
        o = typemeasureDao.findByTitle("TEMP2");
        assertTrue(o.getIdMeasureType() == oldId);
        assertEquals(o.getTitle(), "TEMP2");
        assertEquals(o.getUnity(), "C2");
        typemeasureDao.delete(o); // Delete
        assertNull(typemeasureDao.find(o.getIdMeasureType()));
        // ROOM
        Room o1 = new Room(1L, new House(1L, "test", "image"), "title",0,0,0,0);
    	roomDao.create(o1);
    	assertTrue(o1.getId() > 0);
        Long oldId1 = o1.getId();
        o1 = roomDao.findByTitle("title");
        // Room and house settings
        assertEquals(o1.getId().longValue(), oldId1.longValue());
        assertEquals(o1.getTitle(), "title");
        assertEquals(o1.getHouse().getId().longValue() , 1L);
        assertEquals(o1.getHouse().getName() , "empty house");
        assertEquals(o1.getHouse().getImage() , "empty");
        ////
        roomDao.delete(o1);
        assertNull(roomDao.find(o1.getId()));
        
        // HOUSE
        House o11 = new House(1L, "test","image");
    	houseDao.create(o11);
    	assertTrue(o11.getId() > 0);
        Long oldId11 = o11.getId();
        o11 = houseDao.find(o11.getId());
        // house settings
        assertEquals(o11.getId().longValue(), oldId11.longValue());
        assertEquals(o11.getName(), "test");
        assertEquals(o11.getImage(), "image");
        /////
        o11.setName("test2");
        o11.setImage("img");
        houseDao.update(o11);
        // house settings
        assertEquals(o11.getId().longValue(),  oldId11.longValue());
        assertEquals(o11.getName(), "test2");
        assertEquals(o11.getImage(), "img");
        /////
        houseDao.delete(o11);
        assertNull(houseDao.find(o11.getId()));
    }

    @Test
    public void testFindByRef()
    {
        Measure m1 = new Measure(0L, 42L, new MeasureType(1L, "C", "D",""), 5);
        Measure m2 = new Measure(0L, 42L, new MeasureType(2L, "A", "B",""), 5);
        List<Measure> l = new ArrayList<Measure>();
        l.add(m1);
        l.add(m2);
        Sensor se = new TemperatureSensor(42L, "T");
        sensorDao.create(se);
        measureDao.create(m1);

        List<Sensor> resultSe = sensorDao.findByType(3L);
        assertTrue(resultSe.size() > 0);
        List<Sensor> resultSeFalse = sensorDao.findByType(100L);
        assertFalse(resultSeFalse.size() > 0);

        List<Measure> m = measureDao.findByRef(42L);
        assertTrue(m.size() > 0);
        List<Measure> resultMeFalse = measureDao.findByRef(100L);
        assertFalse(resultMeFalse.size() > 0);

        sensorDao.delete(se);
        for (Measure me : m)
        {
            measureDao.delete(me);
        }

    }

    @Test
    public void testFindByType()
    {
        Long i = 0L;
        Action o = null;
        List<Action> l = new ArrayList<Action>();
        for (int j = 0; j < 5; j++)
        {
            i = new Float(Math.random() * 1000.0 + 1000.0).longValue();
            o = new Action(i, new OperatorType(201L, "Test", " ","I",PlugOperator.class.getName()),
                    "F" + j, "T" + j, "D" + j,"I");
            actionDao.create(o); // Create
            l.add(o);
        }
        List<Action> actions = actionDao.findByType(201L);
        assertTrue(actions.size() > 0);
        for (Action a1 : actions)
        {
            assertTrue(l.contains(a1));
            actionDao.delete(a1); // Delete
            assertNull(actionDao.find(a1.getId()));
        }

    }

    @Test
    public void testFindAllOperator()
    {
        int nbOperator = 5;
        List<Operator> olds = operatorDao.findAll();
        for (long i = 0; i < nbOperator; i++)
        {
            Operator op = new PlugOperator(0L,operatorTypeDao.find(99L),"","");
            Long j = new Float(Math.random() * 1000.0 + 1000.0).longValue();
            op.setOperatorRef(j);
            operatorDao.create(op);
        }
        List<Operator> resultList = operatorDao.findAll();
        assertEquals(nbOperator + olds.size(), resultList.size());

        for (Operator op : resultList)
        {
            if (!olds.contains(op))
                operatorDao.delete(op);
        }
        assertEquals(olds.size(), operatorDao.findAll().size());
    }

    @Test
    public void testFindAllRule()
    {
        int nbRule = 5;
        List<Rule> olds = ruleDao.findAll();
        for (int i = 0; i < nbRule; i++)
        {
            ruleDao.create(new Rule());
        }
        List<Rule> resultList = ruleDao.findAll();
        assertEquals(olds.size() + nbRule, resultList.size());
        for (Rule r : resultList)
        {
            if (!olds.contains(r))
                ruleDao.delete(r);
        }
        assertEquals(olds.size(), ruleDao.findAll().size());
    }

    @Test
    public void testFindAllSensor()
    {
        int nbSensor = 5;
        List<Sensor> olds = sensorDao.findAll();
        for (long i = 0; i < nbSensor; i++)
        {
            Sensor s = new TemperatureSensor(new Float(
                    Math.random() * 10000.0 + 100.0).longValue(), "T");
            sensorDao.create(s);
        }
        List<Sensor> resultList = sensorDao.findAll();
        assertEquals(olds.size() + nbSensor, resultList.size());
        for (Sensor s : resultList)
        {
            if (!olds.contains(s))
                sensorDao.delete(s);
        }
        assertEquals(olds.size(), sensorDao.findAll().size());
    }
    
    @Test
    public void testFindAllSensorInstalled()
    {
        int nbSensor = 5;
        List<Sensor> olds = sensorDao.findAll();
        List<Sensor> sensorInstalled = new ArrayList<Sensor>();
        List<Sensor> sensorNotInstalled = new ArrayList<Sensor>();
        for (long i = 0; i < nbSensor; i++)
        {
            Sensor s = new TemperatureSensor(new Float(
                    Math.random() * 10000.0 + 100.0).longValue(), "T");
            s.setInstalled(false);
            sensorDao.create(s);
            sensorNotInstalled.add(s);
            Sensor s1 = new TemperatureSensor(new Float(
                    Math.random() * 10000.0 + 100.0).longValue(), "T");
            s1.setInstalled(true);
            sensorDao.create(s1);
            sensorInstalled.add(s1);
        }
        List<Sensor> resultListFalse = sensorDao.findAllByInstalled(false);
        List<Sensor> resultListTrue = sensorDao.findAllByInstalled(true);
        assertEquals(olds.size() + (nbSensor*2), resultListFalse.size()+resultListTrue.size());
        for (Sensor s : resultListFalse)
        {
        	if(sensorNotInstalled.contains(s)){
            	assertFalse(s.isInstalled());
                sensorDao.delete(s);
        	}

        }
        for (Sensor s : resultListTrue)
        {
        	if(sensorInstalled.contains(s)){
	        	assertTrue(s.isInstalled());
	            sensorDao.delete(s);
        	}
        }
        assertEquals(olds.size(), sensorDao.findAll().size());
    }

    
    
    @Test
    public void testFindAllRoom(){
    	int nbRoom = 5;
    	List<Point> points = new ArrayList<Point>();
    	Point p1= new Point(1, 1);
    	Point p2= new Point(10, 10);
    	points.add(p1);
    	points.add(p2);
        List<Room> olds = roomDao.findAll();
        for (long i = 0; i < nbRoom; i++)
        {
            Room r = new Room(null, null, "title",0,0,0,0);
            r.setHouse(new House(1L, "name", "image"));
            roomDao.create(r);
        }
        List<Room> resultList = roomDao.findAll();
        assertEquals(olds.size() + nbRoom, resultList.size());
        for (Room r : resultList)
        {
            if (!olds.contains(r))
            	roomDao.delete(r);
        }
        assertEquals(olds.size(), roomDao.findAll().size());
    }
    
    @Test
    public void testFindSensorByRoom()
    {
        Room r = new Room(null, null, "title",0,0,0,0);
    	List<Point> points = new ArrayList<Point>();
    	Point p1= new Point(1, 1);
    	Point p2= new Point(10, 10);
    	points.add(p1);
    	points.add(p2);
        r.setHouse(new House(1L, "name", "image"));
        roomDao.create(r);
    	int nbSensor = 5;
    	List<Sensor> checkTest = new ArrayList<Sensor>();
        for (long i = 0; i < nbSensor; i++)
        {
            Sensor s = new TemperatureSensor(new Float(
                    Math.random() * 10000.0 + 100.0).longValue(), "T");
            s.setRoom(r);
            sensorDao.create(s);
            checkTest.add(s);
        }
        List<Sensor> list = sensorDao.findSensorInstalledByRoom(r.getId());
        for(Sensor s:list){
        	assertTrue(checkTest.contains(s));
        	sensorDao.delete(s);
        }
        roomDao.delete(r);
        
    }
    
    
    @Test
    public void testFindAllHouse(){
    	int nbHouse = 5;
    	List<Point> points = new ArrayList<Point>();
    	Point p1= new Point(1, 1);
    	Point p2= new Point(10, 10);
    	points.add(p1);
    	points.add(p2);
        List<House> olds = houseDao.findAll();
        assertFalse(olds.contains(houseDao.findByTitle("empty house")));
        for (long i = 0; i < nbHouse; i++)
        {
            House h = new House(1L, "name", "image");
            houseDao.create(h);
        }
        List<House> resultList = houseDao.findAll();
        assertEquals(olds.size() + nbHouse, resultList.size());
        for (House h : resultList)
        {
            if (!olds.contains(h))
            	houseDao.delete(h);
        }
        assertEquals(olds.size(), houseDao.findAll().size());
    }
    
    @Test
    public void testFindAllUser()
    {
        int nbUser = 5;
        List<User> olds = userDao.findAll();
        for (int i = 0; i < nbUser; i++)
        {
            userDao.create(new User());
        }
        List<User> resultList = userDao.findAll();
        assertEquals(olds.size() + nbUser, resultList.size());
        for (User r : resultList)
        {
            if (!olds.contains(r))
                userDao.delete(r);
        }
        assertEquals(olds.size(), userDao.findAll().size());
    }

    @Test
    public void testFindAllMeasure()
    {
        int nbMeasure = 5;
        List<Measure> olds = this.measureDao.findAll();
        List<Measure> objectAdded = new ArrayList<Measure>();
        for (int i = 0; i < nbMeasure; i++)
        {
        	Measure m = new Measure(null, 2L,
                    new MeasureType(1L, "A", "B",""), 4);
            measureDao.create(m);
            objectAdded.add(m);
        }
        List<Measure> resultList = measureDao.findAll();
        assertEquals(olds.size() + nbMeasure, resultList.size());
        for (Measure m : objectAdded)
        {
            if (!olds.contains(m))
                measureDao.delete(m);
        }
        assertEquals(olds.size(), measureDao.findAll().size());
    }

    @Test
    public void testFindAllMeasureType()
    {
        int nbMeasureType = 5;
        List<MeasureType> olds = this.typemeasureDao.findAll();
        for (int i = 0; i < nbMeasureType; i++)
        {
            typemeasureDao.create(new MeasureType());
        }
        List<MeasureType> resultList = typemeasureDao.findAll();
        assertEquals(olds.size() + nbMeasureType, resultList.size());
        for (MeasureType m : resultList)
        {
            if (!olds.contains(m))
                typemeasureDao.delete(m);
        }
        assertEquals(olds.size(), typemeasureDao.findAll().size());
    }

    @Test
    public void testFindAllSensorType()
    {
        int nbSensorType = 5;
        List<SensorType> olds = this.sensorTypeDao.findAll();
        for (int i = 0; i < nbSensorType; i++)
        {
            sensorTypeDao.create(new SensorType());
        }
        List<SensorType> resultList = sensorTypeDao.findAll();
        assertEquals(olds.size() + nbSensorType, resultList.size());
        for (SensorType s : resultList)
        {
            if (!olds.contains(s))
                sensorTypeDao.delete(s);
        }
        assertEquals(olds.size(), sensorTypeDao.findAll().size());
    }

    @Test
    public void testFindAllOperatorType()
    {
        int nbOperatorType = 5;
        List<OperatorType> olds = this.operatorTypeDao.findAll();
        for (Long i = 0L; i < nbOperatorType; i++)
        {
			OperatorType o = new OperatorType();
			o.setIdType(i+1000);
			operatorTypeDao.create(o);
        }
        List<OperatorType> resultList = operatorTypeDao.findAll();
        assertEquals(olds.size() + nbOperatorType, resultList.size());
        for (OperatorType s : resultList)
        {
            if (!olds.contains(s))
                operatorTypeDao.delete(s);
        }
        assertEquals(olds.size(), operatorTypeDao.findAll().size());
    }

    @Test
    public void testFindAllAction()
    {
        int nbAction = 5;
        List<Action> olds = this.actionDao.findAll();
        for (int i = 0; i < nbAction; i++)
        {
            Action a = new Action();
            a.setOperatorType(new OperatorType(1L, "test", "ceci est test","I",PlugOperator.class.getName()));
            actionDao.create(a);
        }
        List<Action> resultList = actionDao.findAll();
        assertEquals(olds.size() + nbAction, resultList.size());
        for (Action s : resultList)
        {
            if (!olds.contains(s))
                actionDao.delete(s);
        }
        assertEquals(olds.size(), actionDao.findAll().size());
    }
    
    @Test
    public void testAddActionToDo()
    {
        // Check the persistance
        Long i = new Float(Math.random() * 10000.0 + 100.0).longValue();
        Action a = new Action(i, null, "F", "T", "D","I");
        a.setOperatorType(new OperatorType(1L, "test", "ceci est test","I",PlugOperator.class.getName()));
        actionDao.create(a); // Create
        Long i2 = new Float(Math.random() * 10000.0 + 100.0).longValue();
        Operator o = new PlugOperator(i2, operatorTypeDao.find(99L), "T", "D");
        operatorDao.create(o); // Create
        operatorDao.addActionTodo(a, o);
        List<ActionOperator> l = operatorDao.findAllActionTodo();
        ActionOperator test = l.get(0);
        
        a = test.getAction();
        assertNull(a.getOperatorType());
        assertEquals(a.getFrame(), "F");
        assertEquals(a.getTitle(), "T");
        assertEquals(a.getDescription(), "D");
        
        o = test.getOperator();
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getDescription(), "D");
        
        operatorDao.deleteAllActionsTodo();
        assertTrue(operatorDao.findAllActionTodo().size() == 0);

    }
    
    @Test
    public void testFindRooms(){
    	int nbRoom = 5;
        List<Room> olds = roomDao.findAll();
        House h = new House(1000L, "houseTest", "test");
        houseDao.create(h);
        for (long i = 0; i < nbRoom; i++)
        {
            Room r = new Room(null, null, "title",0,0,0,0);
            r.setHouse(h);
            roomDao.create(r);
        }
        List<Room> resultList = houseDao.findRooms(h.getId());
        List<Room> resultRoom = roomDao.findAll();
        assertEquals(olds.size() + nbRoom, resultRoom.size());
        for (Room r : resultList)
        {
            if (!olds.contains(r))
            	roomDao.delete(r);
        }
        assertEquals(olds.size(), roomDao.findAll().size());
        houseDao.delete(h);
    }
    
    @Test
    public void testFindAllVirtualFrame()
    {
    	List<VirtualFrame> result = virtualFrameDao.findAll();
    	for(VirtualFrame v : result){

    		if(v instanceof VirtualMeteoFrame){
    			assertEquals(v.getId().longValue(), 2L);
    			assertEquals(v.getSensorId().longValue(), 2L);
    			assertEquals(v.getFrameType(), "server.models.VirtualMeteoFrame");
    		}
    		/*else if(v instanceof VirtualCalendarFrame){
    			assertEquals(v.getId().longValue(), 1L);
    			assertEquals(v.getIdsensor().longValue(), 1L);
    			assertEquals(v.getFrameType(), "server.models.VirtualCalendarFrame");
    		}*/
    	}
    	
    }
  
   
}
