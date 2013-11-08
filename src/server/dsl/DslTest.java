package server.dsl;

import static org.junit.Assert.*;

import org.junit.Test;

public class DslTest
{

    @Test
    public void testGood() throws Exception
    {
        String expected = "CREATE TRIGGER MyRule AFTER UPDATE ON T_MEASURE\n"
                        + "WHEN (SELECT VALUE FROM T_MEASURE WHERE IDMEASURE = 1) = 2 AND (SELECT VALUE FROM T_MEASURE WHERE IDMEASURE = 3) = 5 AND ((SELECT VALUE FROM T_MEASURE WHERE IDMEASURE = 2)>1 OR (SELECT VALUE FROM T_MEASURE WHERE IDMEASURE = 5)<=5.0)\n"
                        + "BEGIN\n"
                        + "  DELETE FROM T_ACTIONS_TODO WHERE OPERATORREF = 4 AND IDACTION = 10;\n"
                        + "  INSERT INTO T_ACTIONS_TODO(IDACTION, OPERATORREF, ADDEDAT) VALUES(10, 4, datetime('now'));\n"
                        + "  DELETE FROM T_ACTIONS_TODO WHERE OPERATORREF = 5 AND IDACTION = 42;\n"
                        + "  INSERT INTO T_ACTIONS_TODO(IDACTION, OPERATORREF, ADDEDAT) VALUES(42, 5, datetime('now'));\n"
                        + "END;\n";

        Builder b = new Builder();

        assertEquals(expected, b.generate("MyRule", "ON M1==2 && M3 == 5 && (M2 > 1 || M5 <= 5.0) "
                                                  + "DO P5:42 P4:10"));
    }
    
    @Test(expected=Exception.class)
    public void testBad() throws Exception
    {

        Builder b = new Builder();
        b.generate("MyRule", "C");

    }

    @Test
    public void testGoodBoth() throws Exception
    {
        String expected = "CREATE TRIGGER MyRule AFTER UPDATE ON T_MEASURE\n"
                        + "WHEN 2 = (SELECT VALUE FROM T_MEASURE WHERE IDMEASURE = 1) AND (SELECT VALUE FROM T_MEASURE WHERE IDMEASURE = 3) = (SELECT VALUE FROM T_MEASURE WHERE IDMEASURE = 4) AND ((SELECT VALUE FROM T_MEASURE WHERE IDMEASURE = 2)>1 OR (SELECT VALUE FROM T_MEASURE WHERE IDMEASURE = 5)<=5.0)\n"
                        + "BEGIN\n"
                        + "  DELETE FROM T_ACTIONS_TODO WHERE OPERATORREF = 4 AND IDACTION = 10;\n"
                        + "  INSERT INTO T_ACTIONS_TODO(IDACTION, OPERATORREF, ADDEDAT) VALUES(10, 4, datetime('now'));\n"
                        + "  DELETE FROM T_ACTIONS_TODO WHERE OPERATORREF = 5 AND IDACTION = 42;\n"
                        + "  INSERT INTO T_ACTIONS_TODO(IDACTION, OPERATORREF, ADDEDAT) VALUES(42, 5, datetime('now'));\n"
                        + "END;\n";

        Builder b = new Builder();

        assertEquals(expected, b.generate("MyRule", "ON 2==M1 && M3 == M4 && (M2 > 1 || M5 <= 5.0) "
                                                  + "DO P5:42 P4:10"));
    }

}
