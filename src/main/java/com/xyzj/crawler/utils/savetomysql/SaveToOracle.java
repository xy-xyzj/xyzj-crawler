package com.xyzj.crawler.utils.savetomysql;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Slf4j
public class SaveToOracle {
	private static JdbcTemplate jdbcTemplate = null;
	private final static String ORACLE_URL;
	private final static String ORACLE_USERNAME;
	private final static String ORACLE_PASSWORD;

	//加载配置文件
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("conf");

	static {
		ORACLE_URL = resourceBundle.getString("oracle.url");
		ORACLE_USERNAME = resourceBundle.getString("oracle.username");
		ORACLE_PASSWORD = resourceBundle.getString("oracle.password");

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl(ORACLE_URL);
		dataSource.setUsername(ORACLE_USERNAME);
		dataSource.setPassword(ORACLE_PASSWORD);

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public boolean saveToOracle(String tableName,Object object) {
		try {
			save(sqlBuilder(tableName,object), getValues(object));
		} catch (Exception e) {
			log.error("error,exception: {}",e);
		}
		return true;
	}

	public List<Map<String, Object>> queryBySql(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	public void executeBySql(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void batchUpdate(String sql, List<Object[]> param) {
		jdbcTemplate.batchUpdate(sql, param);
	}



	// 取得要执行的sql语句
	private static String sqlBuilder(String tableName,Object object) {
		Class<? extends Object> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();

		StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append("`"+tableName+"`");
        sql.append("(");

        StringBuilder insertValues = new StringBuilder();
        insertValues.append("values(");
		for(int i=1;i<fields.length-1;i++) {
            sql.append("`"+ fields[i].getName()+"`,");
            insertValues.append("?,");
		}
        sql.append("`"+ fields[fields.length-1].getName()+"`)");
        insertValues.append("?)");

		sql.append(insertValues);
		System.out.println(sql.toString());
		return sql.toString();
	}


	private Object[] getValues(Object object) throws Exception {
		Class<? extends Object> clazz =  object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Object[] params = new Object[fields.length - 1];
		for (int i = 1; i < fields.length; i++) {
			Method method = (Method) clazz.getMethod("get" + getMethodName(fields[i].getName()));
			Object value = method.invoke(object);
			params[i - 1] = value;
		}
		return params;
	}

	private static String getMethodName(String fieldName) {
        // 把一个字符串的第一个字母大写、效率是最高的
		byte[] items = fieldName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	private void save(String sql, Object[] params) {
		// 根据模板和sql语句执行数据库操作
		try {
			int count = jdbcTemplate.update(sql, params);
			log.info("插入数据成功......");
		} catch (Exception e) {
			log.error("插入异常 exception: {}",e);
		}
	}

}
