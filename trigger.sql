CREATE TRIGGER my_test_rule AFTER UPDATE ON T_MEASURE
WHEN new.IDMEASURE = 1 AND new.VALUE > 42
BEGIN
       DELETE FROM T_ACTIONS_TODO WHERE OPERATORREF = 42 AND IDACTION = 1;
       INSERT INTO T_ACTIONS_TODO(IDACTION, OPERATORREF, ADDEDAT) VALUES(1, 42, datetime('now'));
END;

/* TO TESR :
echo "SELECT * FROM t_measure;" | sqlite3 db.sqlite
echo "SELECT * FROM t_actions_todo;" | sqlite3 db.sqlite
echo "UPDATE FROM t_measure SET VALUE = 50 WHERE IDMEASURE = 1;" | sqlite3 db.sqlite
echo "SELECT * FROM t_actions_todo;" | sqlite3 db.sqlite
*/
