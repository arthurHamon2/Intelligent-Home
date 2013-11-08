package server.database.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.Sensors.Frame.OceanAbstractFrame;
import server.Sensors.Frame.RockerFrame;
import server.models.Action;
import server.models.ActionOperator;
import server.models.House;
import server.models.Measure;
import server.models.MeasureType;
import server.models.Operator;
import server.models.OperatorType;
import server.models.PlugOperator;
import server.models.Rocker2Sensor;
import server.models.Room;
import server.models.Rule;
import server.models.Sensor;
import server.models.SensorType;
import server.models.SwitchWindowSensor;
import server.models.TemperatureSensor;
import server.models.User;
import server.models.VirtualCalendarSensor;
import server.models.VirtualMeteoSensor;

public class ServicesTest {

	private OperatorService operatorService;
	private RuleService ruleService;
	private SensorService sensorService;
	private UserService userService;
	private MeasureTypeService typemeasureService;
	private SensorTypeService typeSensorService;
	private ActionService actionService;
	private OperatorTypeService operatorTypeService;
	private RoomService roomService;
	private HouseService houseService;

	@Before
	public void initBeforeTest() throws Exception {
		this.operatorService = new OperatorService();
		this.ruleService = new RuleService();
		this.sensorService = new SensorService();
		this.userService = new UserService();
		this.typemeasureService = new MeasureTypeService();
		this.typeSensorService = new SensorTypeService();
		this.actionService = new ActionService();
		this.operatorTypeService = new OperatorTypeService();
		this.houseService = new HouseService();
		this.roomService = new RoomService();
	}

	@Test
	public void testOperatorPersistence() {
        // Check the persistance

        Long i = new Float(Math.random() * 1000.0 + 1000.0).longValue();
        Operator o = new PlugOperator(i, operatorTypeService.find(99L), "T", "D");
        operatorService.create(o); // Create
        assertTrue(o.getOperatorRef() > 0);
        o = operatorService.find(i);
        assertTrue(o.getOperatorRef() == i.longValue());
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getDescription(), "D");

        o.setTitle("T2");
        o.setDescription("D2");
        operatorService.update(o); // Update
        o = operatorService.find(o.getOperatorRef());
        assertTrue(o.getOperatorRef() == i.longValue());
        assertEquals(o.getTitle(), "T2");
        assertEquals(o.getDescription(), "D2");
        operatorService.delete(o); // Delete
        assertNull(operatorService.find(o.getOperatorRef()));

	}

	@Test
	public void testRulePersistence() {
		// Check the persistance
	   // String rule = "ON M1==2 && M3 == 5 && (M2 > 1 || M5 <= 5.0) DO P5:42 P4:10";
	    String rule = "ON M99999999 > M99999998 DO P4288617990:1";
		Rule o = new Rule(42, "R", "T", rule, true);
		ruleService.create(o); // Create
		assertTrue(o.getId() > 0);
		Integer oldId = o.getId();
		o = ruleService.find(o.getId());
		assertEquals(oldId, o.getId());
		assertEquals("R", o.getRef());
		assertEquals("T", o.getTitle());
		assertEquals(rule, o.getContents());
		assertEquals(true, o.isEnable());
		o.setRef("A").setTitle("B")
		 .setContents(rule)
		 .setEnable(true);
		ruleService.update(o); // Update
		o = ruleService.find(o.getId());
		assertEquals(oldId, o.getId());
		assertEquals("A", o.getRef());
		assertEquals("B", o.getTitle());
		assertEquals(rule, o.getContents());
		assertEquals(true, o.isEnable());
		ruleService.delete(o); // Delete
		assertNull(ruleService.find(o.getId()));
	}

	@Test
	public void testSensorPersistence() {
		Long i = new Float(Math.random() * 1000.0 + 1000.0).longValue();
		// Check the persistance
		Sensor o = new TemperatureSensor(i, "T");
		sensorService.create(o); // Create
		assertTrue(o.getSensorRef() > 0);
		Sensor s = sensorService.find(o.getSensorRef());
		assertEquals(i, s.getSensorRef());
		assertEquals("T", s.getTitle());
		assertEquals(false, s.isInstalled());
		// Test the measure
		assertTrue(s.getMeasures().get(0).getIdMeasure() > 0);
		assertEquals(2L, s.getMeasures().get(0).getMeasureType()
				.getIdMeasureType().longValue());
		assertEquals(i, s.getMeasures().get(0).getSensorRef());
		assertEquals(4.4, s.getMeasures().get(0).getValue(), 5);

		assertTrue(s.getSensorType().getIdType() == 3L);
		assertEquals(s.getSensorType().getEed(), "7-2-5");
		assertEquals(s.getSensorType().getTitle(),
				"Temperature entre 0° et 40°");
		// ///////////////////
		s.setTitle("B");
		s.setInstalled(true);
		sensorService.update(s); // Update
		Sensor e = sensorService.find(s.getSensorRef());
		assertEquals(i, s.getSensorRef());
		assertEquals("B", e.getTitle());
		assertEquals(true, e.isInstalled());
		// Test the measure
		assertEquals(2L, s.getMeasures().get(0).getMeasureType()
				.getIdMeasureType().longValue());
		assertEquals(i, s.getMeasures().get(0).getSensorRef());
		assertEquals(4.4, s.getMeasures().get(0).getValue(), 5);
		// ///////////////////
		sensorService.delete(e); // Delete
		assertEquals(sensorService.find(e.getSensorRef()),null);
	}

    @Test
    public void testRoomPersistence(){
    	Room o = new Room(1L, new House(1L,"empty house","empty"), "title",0,0,0,0);
    	roomService.create(o);
    	assertTrue(o.getId() > 0);
        Long oldId = o.getId();
        o = roomService.find(o.getId());
        // Room and house settings
        assertEquals(o.getId().longValue(), oldId.longValue());
        assertEquals(o.getTitle(), "title");
        assertEquals(o.getHouse().getId().longValue() , 1L);
        assertEquals(o.getHouse().getName() , "empty house");
        assertEquals(o.getHouse().getImage() , "empty");
        /////
        o.setHouse(new House(1L, "empty house","empty"));
        o.setTitle("title2");
        roomService.update(o);
        o = roomService.find(o.getId());
        // Room and house settings
        assertEquals(o.getId().longValue(), oldId.longValue());
        assertEquals(o.getTitle(), "title2");
        assertEquals(o.getHouse().getId().longValue() , 1L);
        assertEquals(o.getHouse().getName() , "empty house");
        assertEquals(o.getHouse().getImage() , "empty");
        /////
        roomService.delete(o);
        assertNull(roomService.find(o.getId()));
    }
    
    @Test
    public void testHousePersistence(){
    	List<Point> points = new ArrayList<Point>();
    	Point p1= new Point(1, 1);
    	Point p2= new Point(10, 10);
    	points.add(p1);
    	points.add(p2);
    	House o = new House(1L, "test","image");
    	houseService.create(o);
    	assertTrue(o.getId() > 0);
        Long oldId = o.getId();
        o = houseService.find(o.getId());
        // house settings
        assertEquals(o.getId().longValue(), oldId.longValue());
        assertEquals(o.getName(), "test");
        assertEquals(o.getImage(), "image");
        /////
        o.setName("test2");
        o.setImage("img");
        houseService.update(o);
        // house settings
        assertEquals(o.getId().longValue(),  oldId.longValue());
        assertEquals(o.getName(), "test2");
        assertEquals(o.getImage(), "img");
        /////
        houseService.delete(o);
        assertNull(houseService.find(o.getId()));
    }
	
	@Test
	public void testUserPersistence() {
		// Check the persistance
		User o = new User(42, "L", "M", "mail","I");
		userService.create(o); // Create
		assertTrue(o.getId() > 0);
		Integer oldId = o.getId();
		o = userService.find(o.getId());
		assertTrue(o.getId() == oldId);
		assertEquals(o.getLogin(), "L");
		assertEquals(o.getPwd(), User.crypt("M"));
		assertEquals(o.getMail(), "mail");
		o.setLogin("A").setPwd("B");
		userService.update(o); // Update
		o = userService.find(o.getId());
		assertTrue(o.getId() == oldId);
		assertEquals(o.getLogin(), "A");
		assertEquals(o.getPwd(), User.crypt("B"));
		userService.delete(o); // Delete
		assertNull(userService.find(o.getId()));
	}

	@Test
	public void testMeasureTypePersistence() {
		// Check the persistance
		MeasureType o = new MeasureType(50L, "TEMP", "C","");
		typemeasureService.create(o); // Create
		assertTrue(o.getIdMeasureType() > 0);
		Long oldId = o.getIdMeasureType();
		o = typemeasureService.find(o.getIdMeasureType());
		assertTrue(o.getIdMeasureType() == oldId);
		assertEquals(o.getTitle(), "TEMP");
		assertEquals(o.getUnity(), "C");
		o.setTitle("TEMP2");
		o.setUnity("C2");
		typemeasureService.update(o); // Update
		o = typemeasureService.find(o.getIdMeasureType());
		assertTrue(o.getIdMeasureType() == oldId);
		assertEquals(o.getTitle(), "TEMP2");
		assertEquals(o.getUnity(), "C2");
		typemeasureService.delete(o); // Delete
		assertNull(typemeasureService.find(o.getIdMeasureType()));
	}

	/*
	 * @Test public void testSensorTypePersistence() { // Check the persistance
	 * SensorType o = new SensorType(50L,"EED","S","F","T");
	 * sensorTypeService.create(o); // Create assertTrue(o.getIdType() > 0);
	 * Long oldId = o.getIdType(); o = sensorTypeService.find(o.getIdType());
	 * assertTrue(o.getIdType() == oldId); assertEquals(o.getEed(), "EED");
	 * assertEquals(o.getSensorClass(), "S"); assertEquals(o.getFrameClass(),
	 * "F"); assertEquals(o.getTitle(), "T");
	 * 
	 * o.setEed("EED2"); o.setSensorClass("S2"); o.setFrameClass("F2");
	 * o.setTitle("T2");
	 * 
	 * sensorTypeService.update(o); // Update o =
	 * sensorTypeService.find(o.getIdType()); assertTrue(o.getIdType() ==
	 * oldId); assertEquals(o.getEed(), "EED2");
	 * assertEquals(o.getSensorClass(), "S2"); assertEquals(o.getFrameClass(),
	 * "F2"); assertEquals(o.getTitle(), "T2");
	 * 
	 * sensorTypeService.delete(o); // Delete
	 * assertNull(sensorTypeService.find(o.getIdType())); }
	 */

	/*
	 * @Test public void testFindByEED() { // Check the persistance SensorType o
	 * = new SensorType(50L,"EED","S","F","T"); sensorTypeService.create(o); //
	 * Create assertTrue(o.getIdType() > 0); Long oldId = o.getIdType(); o =
	 * sensorTypeService.findByEED("EED"); assertTrue(o.getIdType() == oldId);
	 * assertEquals(o.getEed(), "EED"); assertEquals(o.getSensorClass(), "S");
	 * assertEquals(o.getFrameClass(), "F"); assertEquals(o.getTitle(), "T");
	 * 
	 * o.setEed("EED2"); o.setSensorClass("S2"); o.setFrameClass("F2");
	 * o.setTitle("T2");
	 * 
	 * sensorTypeService.update(o); // Update o =
	 * sensorTypeService.findByEED("EED2"); assertTrue(o.getIdType() == oldId);
	 * assertEquals(o.getEed(), "EED2"); assertEquals(o.getSensorClass(), "S2");
	 * assertEquals(o.getFrameClass(), "F2"); assertEquals(o.getTitle(), "T2");
	 * 
	 * sensorTypeService.delete(o); // Delete
	 * assertNull(sensorTypeService.find(o.getIdType())); }
	 */

	@Test
	public void testMakeSensor() {
		Sensor s = typeSensorService.makeSensor("7-2-5");
		assertTrue(s instanceof TemperatureSensor);
	}

	@Test
	public void testMakeFrame() throws Exception {
		OceanAbstractFrame f = typeSensorService.makeFrame(2214883L,
				"A55A0B0708282C8000893382002C");
		assertTrue(f instanceof RockerFrame);
	}

	@Test
	public void testFindByTitle() {
		// Check the persistance
		MeasureType o = new MeasureType(50L, "TEMP", "C","");
		typemeasureService.create(o); // Create
		assertTrue(o.getIdMeasureType() > 0);
		Long oldId = o.getIdMeasureType();
		o = typemeasureService.findByTitle("TEMP");
		assertTrue(o.getIdMeasureType() == oldId);
		assertEquals(o.getTitle(), "TEMP");
		assertEquals(o.getUnity(), "C");
		o.setTitle("TEMP2");
		o.setUnity("C2");
		typemeasureService.update(o); // Update
		o = typemeasureService.findByTitle("TEMP2");
		assertTrue(o.getIdMeasureType() == oldId);
		assertEquals(o.getTitle(), "TEMP2");
		assertEquals(o.getUnity(), "C2");
		typemeasureService.delete(o); // Delete
		assertNull(typemeasureService.find(o.getIdMeasureType()));
        // ROOM
    	List<Point> points = new ArrayList<Point>();
    	Point p1= new Point(1, 1);
    	Point p2= new Point(10, 10);
    	points.add(p1);
    	points.add(p2);
        Room o1 = new Room(1L, new House(1L, "test", "image"), "title",0,0,0,0);
    	roomService.create(o1);
    	assertTrue(o1.getId() > 0);
        Long oldId1 = o1.getId();
        o1 = roomService.findByTitle("title");
        // Room and house settings
        assertEquals(o1.getId().longValue(), oldId1.longValue());
        assertEquals(o1.getTitle(), "title");
        assertEquals(o1.getHouse().getId().longValue() , 1L);
        assertEquals(o1.getHouse().getName() , "empty house");
        assertEquals(o1.getHouse().getImage() , "empty");
        /////
        o1.setHouse(new House(1L, "test2","img2"));
        o1.setTitle("title2");
        roomService.update(o1);
        o1 = roomService.findByTitle("title2");
        // Room and house settings
        assertEquals(o1.getId().longValue(), oldId1.longValue());
        assertEquals(o1.getTitle(), "title2");
        assertEquals(o1.getHouse().getId().longValue() , 1L);
        assertEquals(o1.getHouse().getName() , "empty house");
        assertEquals(o1 .getHouse().getImage() , "empty");
        /////
        roomService.delete(o1);
        assertNull(roomService.find(o1.getId()));
        // HOUSE
        House o11 = new House(1L, "test","image");
    	houseService.create(o11);
    	assertTrue(o11.getId() > 0);
        Long oldId11 = o11.getId();
        o11 = houseService.find(o11.getId());
        // house settings
        assertEquals(o11.getId().longValue(), oldId11.longValue());
        assertEquals(o11.getName(), "test");
        assertEquals(o11.getImage(), "image");
        /////
        o11.setName("test2");
        o11.setImage("img");
        houseService.update(o11);
        // house settings
        assertEquals(o11.getId().longValue(),  oldId11.longValue());
        assertEquals(o11.getName(), "test2");
        assertEquals(o11.getImage(), "img");
        /////
        houseService.delete(o11);
        assertNull(houseService.find(o11.getId()));
	}

	@Test
	public void testFindByRef() {
		Long i = new Float(Math.random() * 1000.0 + 1000.0).longValue();
		// Long j = new Float(Math.random() * 1000.0 + 1000.0).longValue();
		Sensor se = new TemperatureSensor(i, "T");
		// Operator op = new Operator(42, j, "Answer", 42.2, true, new
		// Date(2008, 12, 5, 10, 10, 10));
		// operatorService.create(op);
		sensorService.create(se);

		// TODO: Gautier: ajouter un service findByType pour sensorService
		/*
		 * List<Sensor> resultSe = sensorService.findByType(2L);
		 * assertTrue(resultSe.size() > 0); List<Sensor> resultSeFalse =
		 * sensorService.findByType(100L); assertFalse(resultSeFalse.size() >
		 * 0);
		 */

		// TODO: Gautier: ajouter un service findByRef pour operatorService
		/*
		 * Operator resultOp = operatorService.findByRef(32);
		 * assertNotNull(resultOp); Operator resultOpFalse =
		 * operatorService.findByRef(100); assertNull(resultOpFalse);
		 */

		// operatorService.delete(op);
		sensorService.delete(se);

	}

	@Test
	public void testFindAllOperator() {
		int nbOperator = 5;
		List<Operator> olds = operatorService.findAll();
		for (int i = 0; i < nbOperator; i++) {
			Operator op = new PlugOperator(0L,operatorTypeService.find(99L),"","");
			Long j = new Float(Math.random() * 10000.0 + 100.0).longValue();
			op.setOperatorRef(j);
			operatorService.create(op);
		}
		List<Operator> resultList = operatorService.findAll();
		assertEquals(nbOperator + olds.size(), resultList.size());

		for (Operator op : resultList) {
			if (!olds.contains(op))
				operatorService.delete(op);
		}

		assertEquals(olds.size(), operatorService.findAll().size());
	}

	@Test
	public void testFindAllRule() {
		int nbRule = 5;
		List<Rule> olds = ruleService.findAll();
		for (int i = 0; i < nbRule; i++) {
			ruleService.create(new Rule());
		}
		List<Rule> resultList = ruleService.findAll();
		assertEquals(olds.size() + nbRule, resultList.size());
		for (Rule r : resultList) {
			if (!olds.contains(r))
				ruleService.delete(r);
		}
		assertEquals(olds.size(), ruleService.findAll().size());
	}

	@Test
	public void testFindAllSensor() {
		int nbSensor = 5;
		List<Sensor> olds = sensorService.findAll();
		for (int i = 0; i < nbSensor; i++) {
			Sensor s = new TemperatureSensor(new Float(
					Math.random() * 1000.0 + 1000.0).longValue(), "T");
			sensorService.create(s);
		}
		List<Sensor> resultList = sensorService.findAll();
		assertEquals(olds.size() + nbSensor, resultList.size());
		for (Sensor s : resultList) {
			if (s instanceof Rocker2Sensor) {
				assertTrue(s.getSensorType().getIdType() == 2L);
				assertEquals(s.getSensorType().getEed(), "5-3-1");
				assertEquals(s.getSensorType().getTitle(),
						"Interrupteur deux boutons");
			} else if (s instanceof TemperatureSensor) {
				assertTrue(s.getSensorType().getIdType() == 3L);
				assertEquals(s.getSensorType().getEed(), "7-2-5");
				assertEquals(s.getSensorType().getTitle(),
						"Temperature entre 0° et 40°");
			} 
		    else if (s instanceof SwitchWindowSensor) {
				assertTrue(s.getSensorType().getIdType() == 4L);
				assertEquals(s.getSensorType().getEed(), "6-0-1");
				assertEquals(s.getSensorType().getTitle(),
						"Capteur de l état de la fenêtre");
		    }
		    else if (s instanceof VirtualMeteoSensor) {
				assertTrue(s.getSensorType().getIdType() == 6L);
				assertEquals(s.getSensorType().getEed(), "9-9-9");
				assertEquals(s.getSensorType().getTitle(),
						"Virtual Meteo Sensor");
		    }
		    else if (s instanceof VirtualCalendarSensor) {
				assertTrue(s.getSensorType().getIdType() == 7L);
				assertEquals(s.getSensorType().getEed(), "9-9-9");
				assertEquals(s.getSensorType().getTitle(),
						"Virtual Calendar Sensor");
		    }
		    else {
				fail("Unexpected sensor type  !");
			}
			if (!olds.contains(s))
				sensorService.delete(s);
		}
		assertEquals(olds.size(), sensorService.findAll().size());
	}

	@Test
    public void testFindAllRoom(){
    	int nbRoom = 5;
    	List<Point> points = new ArrayList<Point>();
    	Point p1= new Point(1, 1);
    	Point p2= new Point(10, 10);
    	points.add(p1);
    	points.add(p2);
        List<Room> olds = roomService.findAll();
        for (long i = 0; i < nbRoom; i++)
        {
            Room r = new Room(null, null, "title",0,0,0,0);
            r.setHouse(new House(1L, "name", "image"));
            roomService.create(r);
        }
        List<Room> resultList = roomService.findAll();
        assertEquals(olds.size() + nbRoom, resultList.size());
        for (Room r : resultList)
        {
            if (!olds.contains(r))
            	roomService.delete(r);
        }
        assertEquals(olds.size(), roomService.findAll().size());
    }
    
	@Test
    public void testFindAllHouse(){
    	int nbHouse = 5;
    	List<Point> points = new ArrayList<Point>();
    	Point p1= new Point(1, 1);
    	Point p2= new Point(10, 10);
    	points.add(p1);
    	points.add(p2);
        List<House> olds = houseService.findAll();
        for (long i = 0; i < nbHouse; i++)
        {
            House h = new House(1L, "name", "image");
            houseService.create(h);
        }
        List<House> resultList = houseService.findAll();
        assertEquals(olds.size() + nbHouse, resultList.size());
        for (House h : resultList)
        {
            if (!olds.contains(h))
            	houseService.delete(h);
        }
        assertEquals(olds.size(), houseService.findAll().size());
    }
	
	@Test
	public void testFindAllUser() {
		int nbUser = 5;
		List<User> olds = userService.findAll();
		for (int i = 0; i < nbUser; i++) {
			userService.create(new User());
		}
		List<User> resultList = userService.findAll();
		assertEquals(olds.size() + nbUser, resultList.size());
		for (User r : resultList) {
			if (!olds.contains(r))
				userService.delete(r);
		}
		assertEquals(olds.size(), userService.findAll().size());
	}

	@Test
	public void testFindAllMeasureType() {
		int nbMeasureType = 5;
		List<MeasureType> olds = this.typemeasureService.findAll();
		for (int i = 0; i < nbMeasureType; i++) {
			typemeasureService.create(new MeasureType());
		}
		List<MeasureType> resultList = typemeasureService.findAll();
		assertEquals(olds.size() + nbMeasureType, resultList.size());
		for (MeasureType m : resultList) {
			if (!olds.contains(m))
				typemeasureService.delete(m);
		}
		assertEquals(olds.size(), typemeasureService.findAll().size());
	}

	@Test
	public void testFindAllSensorType() {
		int nbSensorType = 5;
		List<SensorType> olds = this.typeSensorService.findAll();
		for (int i = 0; i < nbSensorType; i++) {
			typeSensorService.create(new SensorType());
		}
		List<SensorType> resultList = typeSensorService.findAll();
		assertEquals(olds.size() + nbSensorType, resultList.size());
		for (SensorType s : resultList) {
			if (!olds.contains(s))
				typeSensorService.delete(s);
		}

		assertEquals(olds.size(), typeSensorService.findAll().size());
	}


	@Test
	public void testFindByType() {
		Long i = 0L;
		Action o = null;
		List<Action> l = new ArrayList<Action>();
		for (int j = 0; j < 5; j++) {
			i = new Float(Math.random() * 1000.0 + 1000.0).longValue();
			o = new Action(i, new OperatorType(100L, "Test2", " ","I",PlugOperator.class.getName()),
					"F" + j, "T" + j, "D" + j,"I");
			actionService.create(o); // Create
			l.add(o);
		}
		List<Action> actions = actionService.findByType(100L);
		assertTrue(actions.size() > 0);
		for (Action a1 : actions) {
			assertTrue(l.contains(a1));
			actionService.delete(a1); // Delete
			assertNull(actionService.find(a1.getId()));
		}
	}

	@Test
	public void testFindAllOperatorType() {
		int nbOperatorType = 5;
		List<OperatorType> olds = this.operatorTypeService.findAll();
		for (Long i = 1L; i < nbOperatorType+1; i++) {
			OperatorType o = new OperatorType();
			o.setIdType(i+1000);
			operatorTypeService.create(o);
		}
		List<OperatorType> resultList = operatorTypeService.findAll();
		assertEquals(olds.size() + nbOperatorType, resultList.size());
		for (OperatorType s : resultList) {
			if (!olds.contains(s))
				operatorTypeService.delete(s);
		}
		assertEquals(olds.size(), operatorTypeService.findAll().size());
	}

	@Test
	public void testFindAllAction() {
		int nbAction = 5;
		List<Action> olds = this.actionService.findAll();
		for (int i = 0; i < nbAction; i++) {
			Action a = new Action();
			a.setOperatorType(new OperatorType(1L, "test", "ceci est test","I",PlugOperator.class.getName()));
			actionService.create(a);
		}
		List<Action> resultList = actionService.findAll();
		assertEquals(olds.size() + nbAction, resultList.size());
		for (Action s : resultList) {
			if (!olds.contains(s))
				actionService.delete(s);
		}
		assertEquals(olds.size(), actionService.findAll().size());
	}
	

    @Test 
    public void testHistoricSensor() 
    { 
    	Long alea = new Float(Math.random() * 1000.0 + 1000.0).longValue();
    	TemperatureSensor obj = new TemperatureSensor(alea, "temp1"); 
    	sensorService.create(obj); 
    	for(double i=0;i<10;i++){
	    	obj.setTemperature(i*10.0);
	    	sensorService.update(obj);
    	}
    	List<Measure> listResult = sensorService.historicOf(sensorService.find(alea).getMeasures().get(0).getIdMeasure()); 
    	double i =0;
        for(Measure m : listResult) {
        	assertTrue(m.getValue() == 10*i); 
        	assertTrue(m.getMeasureType().getIdMeasureType() == 2L);
        	assertTrue(m.getSensorRef().longValue() == alea.longValue());
        	i++;
      	}
        sensorService.delete(obj);
    }
    
    @Test 
    public void testHistoricAction() 
    { 
    	Long opRef = 4288617990L;
    	// Create actions todo
    	operatorService.createActionTodo(actionService.find(1L), operatorService.find(opRef));
    	operatorService.createActionTodo(actionService.find(2L), operatorService.find(opRef));
    	// Delete all actions to do
    	List<ActionOperator> todo = operatorService.getActionTodo();
    	assertTrue(todo.get(0).getAction().equals(actionService.find(1L)));
    	assertTrue(todo.get(1).getAction().equals(actionService.find(2L)));
    	// find them out in the historic
    	List<Action> result = operatorService.historicOf(opRef);
    	assertTrue(result.contains(actionService.find(1L)));
    	assertTrue(result.contains(actionService.find(2L)));
 
    	
    }
    
    @Test
    public void testAddActionToDo()
    {
        // Check the persistance
        Long i = new Float(Math.random() * 1000.0 + 1000.0).longValue();
        Action a = new Action(i, null, "F", "T", "D","I");
        a.setOperatorType(new OperatorType(1L, "test", "ceci est test","I",PlugOperator.class.getName()));
        actionService.create(a); // Create
        Long i2 = new Float(Math.random() * 1000.0 + 1000.0).longValue();
        Operator o = new PlugOperator(i2, operatorTypeService.find(99L), "T", "D");
        operatorService.create(o); // Create
        operatorService.createActionTodo(a, o);
        // Test if we add an existing action todo
        operatorService.createActionTodo(a, o);
        List<ActionOperator> l = operatorService.getActionTodo();   
        assertTrue(operatorService.getActionTodo().size() == 0);
       
        ActionOperator test = l.get(0);
        a = test.getAction();
        assertNull(a.getOperatorType());
        assertEquals(a.getFrame(), "F");
        assertEquals(a.getTitle(), "T");
        assertEquals(a.getDescription(), "D");
        
        o = test.getOperator();
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getDescription(), "D");
    }

    @Test
    public void testFindActionForOperator(){
    	Long i = new Float(Math.random() * 1000.0 + 1000.0).longValue();
        Operator o = new PlugOperator(i, operatorTypeService.find(99L), "T", "D");

        operatorService.create(o); // Create
        List<Action> l = operatorService.findActions(o);
        List<Action> actions = actionService.findByType(99L);
        for(Action a : l){
        	actions.contains(a);
        }
    }
    
	@Test
	public void testFindAllSensorByRoomInstalled() {
		int nbSensor = 5;
		List<Sensor> olds = sensorService.findSensorInstalledByRoom(5L);
		
		Room r = roomService.find(5L);
		for (int i = 0; i < nbSensor; i++) {
			Sensor s = new TemperatureSensor(new Float(
					Math.random() * 1000.0 + 1000.0).longValue(), "T");
			s.setRoom(r);
			s.setInstalled(true);
			sensorService.create(s);
		}
		assertTrue(sensorService.findSensorInstalledByRoom(10L).size() == 0);
		List<Sensor> resultList = sensorService.findSensorInstalledByRoom(r.getId());
		assertEquals(olds.size() + nbSensor, resultList.size());
		for (Sensor s : resultList) {
			if (s instanceof Rocker2Sensor) {
				assertTrue(s.getSensorType().getIdType() == 2L);
				assertEquals(s.getSensorType().getEed(), "5-3-1");
				assertEquals(s.getSensorType().getTitle(),
						"Interrupteur deux boutons");
			} else if (s instanceof TemperatureSensor) {
				assertTrue(s.getSensorType().getIdType() == 3L);
				assertEquals(s.getSensorType().getEed(), "7-2-5");
				assertEquals(s.getSensorType().getTitle(),
						"Temperature entre 0° et 40°");
			} else {
				fail("Unexpected sensor type !");
			}
			if (!olds.contains(s))
				sensorService.delete(s);
		}
		assertEquals(olds.size(), sensorService.findSensorInstalledByRoom(5L).size());
	}
    
    //TODO: Laurent --> pour affichage sensor
    public void testLaurent(){
    	List<Sensor> l = sensorService.findSensorInstalledByRoom(1L);
    	l.get(0).getMeasures();
    	l.get(0).getMeasures().get(0).getMeasureType().getImage();
    	sensorService.historicOf(l.get(0).getMeasures().get(0).getIdMeasure());
    }
}
