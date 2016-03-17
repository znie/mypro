package com.znie.mypro.jndi.util;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class DbutilsTemplate {

	private static final Logger LOG = LoggerFactory
			.getLogger(DbutilsTemplate.class);

	private QueryRunner queryRunner;

	@Resource(name="dataSource")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public QueryRunner getQueryRunner() {
		return this.queryRunner;
	}

	public DataSource getDataSource() {
		return this.dataSource;
	}
	
	/** 
     * 执行sql语句 
     *  
     * @param sql 
     *            sql语句 
     * @param params 
     *            参数数组 
     * @return 受影响的行数 
     */  
    public int update(String sql, Object[] params) {  
        queryRunner = new QueryRunner(dataSource);  
        int affectedRows = 0;  
        try {  
            if (params == null) {  
                affectedRows = queryRunner.update(sql);  
            } else {  
                affectedRows = queryRunner.update(sql, params);  
            }  
        } catch (SQLException e) {  
            LOG.error("Error occured while attempting to update data", e);  
        }  
        return affectedRows;  
    }  
}
