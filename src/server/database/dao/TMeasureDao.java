package server.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.models.Measure;
import server.models.MeasureType;

public class TMeasureDao extends AbstractDao<Measure, Long>
{

    private final String TABLE_NAME = "T_MEASURE";
    private final String TABLE_TYPE_NAME = "T_MEASURE_TYPE";

    private TMeasureTypeDao typeDao = new TMeasureTypeDao();
    
    @Override
    public void create(Measure obj)
    {
        try
        {
            // The NULL value will auto increment the id value
            PreparedStatement stat = this.dal.prepare("INSERT INTO "
                    + TABLE_NAME
                    + " (IDMEASURE, SENSORREF, IDMEASURETYPE , VALUE)"
                    + "VALUES( NULL , ? , ? , ? )");
            stat.setLong(1, obj.getSensorRef());
            stat.setLong(2, obj.getMeasureType().getIdMeasureType());
            stat.setDouble(3, obj.getValue());

            // Execute the query
            stat.executeUpdate();
            // Get the last id inserted
            stat = this.dal.prepare("SELECT MAX(last_insert_rowid()) FROM "
                    + TABLE_NAME + "");
            ResultSet lastId = stat.executeQuery();
            if (lastId.next())
            {
                // Update the object with the right id
                obj.setIdMeasure(lastId.getLong(1));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Measure obj)
    {
        try
        {
            PreparedStatement stat = this.dal.prepare("UPDATE " + TABLE_NAME
                    + " SET SENSORREF = ?, IDMEASURETYPE = ?, VALUE = ?"
                    + " WHERE IDMEASURE = ?");
            stat.setLong(1, obj.getSensorRef());
            stat.setLong(2, obj.getMeasureType().getIdMeasureType());
            stat.setDouble(3, obj.getValue());
            stat.setDouble(4, obj.getIdMeasure());

            // Execute the query
            stat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Measure obj)
    {
        try
        {
            PreparedStatement stat = this.dal.prepare("DELETE FROM "
                    + TABLE_NAME + " WHERE IDMEASURE = " + obj.getIdMeasure());
            stat.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Measure find(Long id)
    {
        String query = "SELECT M.IDMEASURE, M.SENSORREF, M.IDMEASURETYPE, M.VALUE"
                     + " FROM " + TABLE_NAME + " M, " + TABLE_TYPE_NAME + " T"
                     + " WHERE M.IDMEASURE = ? AND T.IDMEASURETYPE = M.IDMEASURETYPE"
                     + " ORDER BY IDMEASURE";
        Measure obj = null;
        
        try
        {
            PreparedStatement stat = dal.prepare(query);
            stat.setLong(1, id);
            ResultSet res = stat.executeQuery();
            if (res.next())
            {
                obj = new Measure(
                        res.getLong(1),
                        res.getLong(2),
                        typeDao.find(res.getLong(3)),
                        res.getDouble(4));
                res.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public List<Measure> findAll()
    {
        String query = "SELECT M.IDMEASURE, M.SENSORREF, M.IDMEASURETYPE, M.VALUE"
                     + " FROM " + TABLE_NAME + " M, " + TABLE_TYPE_NAME + " T"
                     + " WHERE T.IDMEASURETYPE = M.IDMEASURETYPE"
                     + " ORDER BY IDMEASURE";
        List<Measure> result = new ArrayList<Measure>();
        try
        {
            ResultSet res = dal.prepare(query).executeQuery();
            while (res.next())
            {
                Measure m = new Measure(
                                res.getLong(1),
                                res.getLong(2),
                                typeDao.find(res.getLong(3)),
                                res.getDouble(4));
                result.add(m);
            }
            res.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }



    public List<Measure> findByRef(Long ref)
    {
        String query = "SELECT M.IDMEASURE, M.SENSORREF, M.IDMEASURETYPE, M.VALUE"
                     + " FROM " + TABLE_NAME + " M, " + TABLE_TYPE_NAME + " T"
                     + " WHERE M.SENSORREF = ? AND T.IDMEASURETYPE = M.IDMEASURETYPE"
                     + " ORDER BY IDMEASURE";
        List<Measure> result = new ArrayList<Measure>();
        try
        {
            PreparedStatement stat = dal.prepare(query);
            stat.setLong(1, ref);
            ResultSet res = stat.executeQuery();
            while (res.next())
            {
                Measure m = new Measure(
                                res.getLong(1),
                                res.getLong(2),
                                typeDao.find(res.getLong(3)),
                                res.getDouble(4));
                result.add(m);
            }
            res.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
