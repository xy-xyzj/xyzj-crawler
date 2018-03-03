package com.xyzj.crawler.framework.savetomysql;

public class ServiceImpl implements IService {
	private DAOImpl DAOImpl;

	public void setMedicineDAOImpl(DAOImpl DAOImpl) {
		this.DAOImpl = DAOImpl;
	}

	public boolean add(String tableName,Object PO) {
		DAOImpl = new DAOImpl();
		return DAOImpl.add(tableName,PO);
	}



}
