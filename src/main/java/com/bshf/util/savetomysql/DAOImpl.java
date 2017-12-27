package com.bshf.util.savetomysql;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DAOImpl implements IDAO {
	static JdbcTemplate jdbcTemplate = null;
	static {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.73.21:3306/crawler?characterEncoding=utf8");
		dataSource.setUsername("root");
		dataSource.setPassword("mysql_bshf..0");
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public boolean add(String tableName,Object PO) {
		try {
			save(getSQL(tableName,PO), getValues(PO));
		} catch (Exception e) {
		}
		return true;
	}
	// 取得要执行的sql语句
	public static String getSQL(String tableName,Object PO) {
		Class<? extends Object> clazz = PO.getClass();
		Field[] fields = clazz.getDeclaredFields();
		StringBuffer key = new StringBuffer();
		key.append("INSERT INTO ");
		key.append("`"+tableName+"`");
		key.append("(");
		StringBuffer zwf = new StringBuffer();
		zwf.append("values(");
		for(int i=1;i<fields.length-1;i++) {
			key.append("`"+ fields[i].getName()+"`,");
			zwf.append("?,");
		}
		key.append("`"+ fields[fields.length-1].getName()+"`)");
		zwf.append("?)");
		StringBuffer sql = new StringBuffer();
		sql.append(key);
		sql.append(zwf);
		System.out.println(sql.toString());
		return sql.toString();
	}

	public Object[] getValues(Object PO) throws Exception {
		Class<? extends Object> clazz =  PO.getClass();

		Field[] fields = clazz.getDeclaredFields();
		Object[] params = new Object[fields.length - 1];
		for (int i = 1; i < fields.length; i++) {
			Method method = (Method) clazz.getMethod("get" + getMethodName(fields[i].getName()));
			Object value = method.invoke(PO);
			params[i - 1] = value;
		}
		return params;
	}
	

	// 把一个字符串的第一个字母大写、效率是最高的
	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	private void save(String sql, Object[] params) {
		// 根据模板和sql语句执行数据库操作
		try {
			int count = jdbcTemplate.update(sql, params);
			System.out.println(count);
		} catch (Exception e) {
		}
	}

}
