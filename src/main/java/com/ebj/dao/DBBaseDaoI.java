package com.ebj.dao;

import java.sql.ResultSet;

public interface DBBaseDaoI {
    public boolean doInsert();
    public boolean doDelete();
    public boolean doUpdate();
    public ResultSet doQuery(String sql);
}
