package server.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import server.database.service.OperatorService;

public class ModelsTest
{

    @Test
    public void testOperatorChainning()
    {
        Operator o = new PlugOperator(0l,null,"","");
        assertEquals(o.getOperatorRef().intValue(), 0);
        assertNull(o.getOperatorType());
        assertEquals(o.getTitle(),"");
        assertEquals(o.getDescription(),"");
        o.setOperatorRef(42L);
        o.setTitle("T");
        o.setDescription("D");
        assertEquals(o.getOperatorRef().intValue(), 42L);
        assertNull(o.getOperatorType());
        assertEquals(o.getTitle(),"T");
        assertEquals(o.getDescription(),"D");
    }
    
    @Test
    public void testOperatorConstruct()
    {
        Operator o = new PlugOperator(52L,null,"T","D");
        assertEquals(o.getOperatorRef().intValue(), 52L);
        assertEquals(o.getOperatorType(), null);
        assertEquals(o.getTitle(),"T");
        assertEquals(o.getDescription(),"D");
    }

    @Test
    public void testOperatorTypeConstruct()
    {
        OperatorType o = new OperatorType(52L,"T","D","I",PlugOperator.class.getName());
        assertEquals(o.getIdType().longValue(), 52L);
        assertEquals(o.getTitle(),"T");
        assertEquals(o.getDescription(),"D");
        assertEquals(o.getImage(),"I");
    }
    
    @Test
    public void testOperatorTypeChainning()
    {
        OperatorType o = new OperatorType();
        assertEquals(o.getIdType().longValue(), 0L);
        assertEquals(o.getTitle(),"");
        assertEquals(o.getDescription(),"");
        o.setIdType(42L);
        o.setTitle("T");
        o.setDescription("D");
        assertEquals(o.getIdType().longValue(), 42L);
        assertEquals(o.getTitle(),"T");
        assertEquals(o.getDescription(),"D");
    }
    
    @Test
    public void testActionConstruct()
    {
        Action o = new Action(52L,null,"F","T","D","I");
        assertEquals(o.getId().longValue(), 52L);
        assertNull(o.getOperatorType());
        assertEquals(o.getFrame(),"F");
        assertEquals(o.getTitle(),"T");
        assertEquals(o.getDescription(),"D");
        assertEquals(o.getImage(),"I");
    }
    
    @Test
    public void testActionChainning()
    {
        Action o = new Action();
        assertEquals(o.getId().longValue(), 0L);
        assertNull(o.getOperatorType());
        assertEquals(o.getFrame(),"");
        assertEquals(o.getTitle(),"");
        assertEquals(o.getDescription(),"");
        o.setId(42L);
        o.setFrame("F");
        o.setTitle("T");
        o.setDescription("D");
        assertEquals(o.getId().longValue(), 42L);
        assertNull(o.getOperatorType());
        assertEquals(o.getFrame(),"F");
        assertEquals(o.getTitle(),"T");
        assertEquals(o.getDescription(),"D");
    }
    
    @Test
    public void testRuleChainning()
    {
        Rule o = new Rule();
        assertEquals(o.getId().intValue(), 0);
        assertEquals(o.getRef(), "");
        assertEquals(o.getTitle(), "");
        assertEquals(o.getContents(), "");
        assertEquals(o.isEnable(), false);
        o.setId(42)
         .setRef("R")
         .setTitle("T")
         .setContents("C")
         .setEnable(true);
        assertEquals(o.getId().intValue(), 42);
        assertEquals(o.getRef(), "R");
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getContents(), "C");
        assertEquals(o.isEnable(), true);
    }

    @Test
    public void testRuleConstruct()
    {
        Rule o = new Rule(42, "R", "T", "C", true);
        assertEquals(o.getId().intValue(), 42);
        assertEquals(o.getRef(), "R");
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getContents(), "C");
        assertEquals(o.isEnable(), true);
    }
    
    @Test
    public void testHouseChaining()
    {
    	House o = new House(1L, "test","image");
        // house settings
        assertEquals(o.getId().longValue(), 1L);
        assertEquals(o.getName(), "test");
        assertEquals(o.getImage(), "image");
        /////
        o.setId(2L);
        o.setName("test2");
        o.setImage("img");
        // house settings
        assertEquals(o.getId().longValue(), 2L);
        assertEquals(o.getName(), "test2");
        assertEquals(o.getImage(), "img");
        /////
    }
    
    
    @Test
    public void testRoomChaining()
    {
    	Room o = new Room(1L, new House(1L, "test", "image"), "title",0,0,0,0);
        // Room and house settings
        assertEquals(o.getId().longValue(), 1L);
        assertEquals(o.getTitle(), "title");
        assertEquals(o.getHouse().getId().longValue(), 1L);
        assertEquals(o.getHouse().getName(), "test");
        assertEquals(o.getHouse().getImage(), "image");
        /////
        o.setId(2L);
        o.setHouse(new House(2L, "test2","img2"));
        o.setTitle("title2");
        // Room and house settings
        assertEquals(o.getId().longValue(), 2L);
        assertEquals(o.getTitle(), "title2");
        assertEquals(o.getHouse().getId().longValue(), 2L);
        assertEquals(o.getHouse().getName(), "test2");
        assertEquals(o.getHouse().getImage(), "img2");
        /////
    }
    
    @Test
    public void testSensorChainning()
    {
        Sensor o = new TemperatureSensor(0L,"");
        assertTrue(o.getSensorRef().longValue() == 0L);
        assertEquals(o.getTitle(), "");
        assertEquals(o.isInstalled(), false);
        assertTrue(o.getMeasures().size() == 1);
        assertNotNull(o.getSensorType());
        assertTrue(o.getSensorType().getIdType() == 3);
        o.setSensorRef(new Long(42));
        o.setTitle("T");
        o.setInstalled(true);
        o.setRoom(new Room(1L, new House(1L, "test","image"), "title",0,0,0,0));
        Measure m1 = new Measure(1L, o.getSensorRef(), new MeasureType(2L, "A", "B",""),4.4);
        Measure m2 = new Measure(2L, o.getSensorRef(), new MeasureType(2L, "A", "B",""), 10.4);
        List<Measure> l1 = new ArrayList<Measure>();
        l1.add(m1);
        l1.add(m2);
        o.setMeasures(l1);
        assertTrue(o.getSensorRef().longValue() == 42L);
        assertEquals(o.getTitle(), "T");
        assertEquals(o.isInstalled(), true);
        // Room and house settings
        assertEquals(o.getRoom().getId().longValue(), 1L);
        assertEquals(o.getRoom().getTitle(), "title");
        assertEquals(o.getRoom().getHouse().getId().longValue(), 1L);
        assertEquals(o.getRoom().getHouse().getName(), "test");
        assertEquals(o.getRoom().getHouse().getImage(), "image");
        /////
        assertTrue(o.getMeasures().get(0).getValue() == 4.4);
        assertTrue(o.getMeasures().get(1).getValue() == 10.4);
        assertNotNull(o.getSensorType());
        assertTrue(o.getSensorType().getIdType() == 3);
    }
    
    @Test
    public void testSensorConstruct()
    {
        Measure m1 = new Measure(1L, 42L, new MeasureType(2L, "A", "B",""), 4.4);
        Measure m2 = new Measure(2L, 42L, new MeasureType(2L, "A", "B",""), 10.4);
        List<Measure> l1 = new ArrayList<Measure>();
        l1.add(m1);
        l1.add(m2);
        Sensor o = new TemperatureSensor(new Long(42), "T");
        o.setMeasures(l1);
        assertTrue(o.getSensorRef().longValue() == 42L);
        assertEquals(o.getTitle(), "T");
        assertTrue(o.getMeasures().get(0).getValue() == 4.4);
        assertTrue(o.getMeasures().get(1).getValue() == 10.4);
    }
    
    @Test
    public void testUserChainning()
    {
        User o = new User();
        assertEquals(o.getId().intValue(), 0);
        assertEquals(o.getLogin(), "");
        assertEquals(o.getPwd(), "");
        o.setId(42)
         .setLogin("L")
         .setPwd("M");
        assertEquals(o.getId().intValue(), 42);
        assertEquals(o.getLogin(), "L");
        assertEquals(o.getPwd(), User.crypt("M"));
    }
    
    @Test
    public void testUserConstruct()
    {
        User o = new User(42, "L", "M","gmail@gmail.com","I");
        assertEquals(o.getId().intValue(), 42);
        assertEquals(o.getLogin(), "L");
        assertEquals(o.getPwd(), "M");
        assertEquals(o.getAvatar(), "I");
    }
    
    @Test
    public void testSensorTypeChainning()
    {
        SensorType o = new SensorType();
        assertEquals(o.getEed(), "");
        assertEquals(o.getSensorClass(), "");
        assertEquals(o.getTitle(), "");
        o.setEed("EED");
        o.setSensorClass("Class");
        o.setTitle("T");
        assertEquals(o.getEed(), "EED");
        assertEquals(o.getSensorClass(), "Class");
        assertEquals(o.getTitle(), "T");
    }
    
    @Test
    public void testSensorTypeConstruct()
    {
    	SensorType o = new SensorType(1L,"ABC","Sensor", "Frame", "Title", "thermometre.png");
        assertEquals(o.getEed(), "ABC");
        assertEquals(o.getSensorClass(), "Sensor");
        assertEquals(o.getFrameClass(), "Frame");
        assertEquals(o.getTitle(), "Title");
        assertEquals(o.getImage(), "thermometre.png");
    }
    
    @Test
    public void testMeasureConstruct()
    {
    	Measure o = new Measure(1L, 2L, new MeasureType(3L, "A", "B",""), 4.4);
        assertEquals(1L, o.getIdMeasure().longValue());
        assertEquals(2L, o.getSensorRef().longValue());
        assertEquals(3L, o.getMeasureType().getIdMeasureType().longValue());
        assertTrue(o.getValue() == 4.4);
    }

    @Test
    public void testMeasureTypeChainning()
    {
        MeasureType o = new MeasureType();
        assertEquals(0L, o.getIdMeasureType().longValue());
        assertEquals(o.getTitle(), "");
        assertEquals(o.getUnity(), "");
        o.setIdMeasureType(42L);
        o.setTitle("T");
        o.setUnity("U");
        assertEquals(42L, o.getIdMeasureType().longValue());
        assertEquals(o.getTitle(), "T");
        assertEquals(o.getUnity(), "U");
    }
    
    @Test
    public void testMeasureTypeConstruct()
    {
    	MeasureType o = new MeasureType(1L,"Title","Unity","");
        assertEquals(1L, o.getIdMeasureType().longValue());
        assertEquals(o.getTitle(), "Title");
        assertEquals(o.getUnity(), "Unity");
    }
    
  /*  @Test
    public void testMailSender()
    {
    	OperatorService s = new OperatorService();
    	Operator o = s.find(1L);
    	MailSender m = new MailSender(o);
    	Action a = new Action(0L, null, "frame", "title", "description");
    	m.execute(a);
    }*/
}
