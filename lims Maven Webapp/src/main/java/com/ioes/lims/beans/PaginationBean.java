package com.ioes.lims.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.sias.util.DatabaseBonecpUtil;
import com.sias.util.HtmlUtil;


/**
 * 分页Bean：用于Web前台分页显示数据
 * 
 * @author zxp(Zhou XiPing 周喜平）
 * 
 */
public class PaginationBean {
	private static org.apache.log4j.Logger log = Logger
			.getLogger(PaginationBean.class);
	
	/** 当前页码*/
	private int currPage = 1; 
	/** 总页数*/
	private int pageCount = 0;
	/** 总记录数*/
	private int rowCount = 0; 
	/** 每页记录数*/
	private int pageSize = 20;
	/** 当前页开始的行数*/
	private int startRow = 0;
	/** 当前页结束的行数*/
	private int endRow = 0; 
	/** 查询的SQL语句*/
	private String sql = null; 
	/** 数据库类型 mysql or oracle*/
	private String dbtype ="mysql"; 
	
	
	/**
	 * 获取数据库类型
	 * @return the dbtype
	 */
	public String getDbtype() {
		return dbtype;
	}

	/**
	 * @param dbtype the dbtype to set
	 */
	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	private DatabaseBonecpUtil databaseBoneUtil;

	private ResultSetMetaData rsmd = null;
	private ResultSet rs = null;

	public DatabaseBonecpUtil getDatabaseBoneUtil() {
		return databaseBoneUtil;
	}

	public void setDatabaseBoneUtil(DatabaseBonecpUtil databaseBoneUtil) {
		this.databaseBoneUtil = databaseBoneUtil;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int p) {
		this.pageCount = p;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowCount() {
		return rowCount;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getStartRow() {
		return startRow;
	}

	/**
	 * 根据传入的当前页码,和每页的大小及sql语句.可以取得该页所有的记录List(每条记录是一个:
	 * HashMap),总行数,总页数,开始行数,结束行数数 在页面取值时,把字段名转为大写,为某些数据库区分大小写
	 * 
	 * @return List
	 */
	public List getCurrPageData() {

		List currPageList = new ArrayList();
		Connection conn1 = null;
		Connection conn2 = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		try {
			conn1 = databaseBoneUtil.getConnection();
			conn2 = databaseBoneUtil.getConnection();
			if (null != this.sql) {

				StringBuffer sb = new StringBuffer();
				sb.append(" select count(*) from ( ").append(this.sql)
						.append(" ) t0 ");
				//System.out.println(sb.toString());
				
				ps1 = databaseBoneUtil.getPrepStatement(conn1, sb.toString());
				this.rs = ps1.executeQuery();
				if (rs.next()) {
					// 获得总记录数
					this.rowCount = rs.getInt(1);
				}
				//this.rs.close();
				if (this.rowCount > 0) {

					this.pageSize = (this.pageSize < 1 ? 1 : this.pageSize);

					// 计算总页数
					this.pageCount = (this.rowCount + this.pageSize - 1)
							/ this.pageSize;

					// 计算当前页开始的行数
					if (this.currPage < 1) {
						this.currPage = 1;
					} else if (this.currPage > this.pageCount) {
						this.currPage = this.pageCount;
					}
					this.startRow = (this.currPage - 1) * this.pageSize + 1;
					
					// 获取当前页的数据
					sb = new StringBuffer();
					
					if (dbtype.equalsIgnoreCase("oracle")){
						// for oracle
						sb.append(" select * from ( ")
						  .append("  	select rownum as rn,t.* from ( ")
						  .append(this.sql).append(" ) t ") .append(" )  t1")
						  .append(" where rn between ").append(this.startRow)
						  .append(" and ").append(this.startRow + this.pageSize -1);
						// end oracle
					}else{
						// for mysql


						sb.append(" select * from ( ")
								.append("  	select @rownum:=@rownum+1 as  rn,t.* from ( ")
								.append(this.sql)
								.append(" ) t,(select @rownum:=0) t1 ")
								.append(" )  t2").append(" where rn between ")
								.append(this.startRow).append(" and ")
								.append(this.startRow + this.pageSize - 1);
	
						// end mysql
					}
					/*
					 * sb.append(" select * from ( ").append(this.sql).append(" )  "
					 * )
					 * .append("  where rownum between ").append(this.startRow)
					 * .append("        and ").append(this.startRow +
					 * this.pageSize -1);
					 */
					this.sql = sb.toString();
					//System.out.println(sql);
					Map map = null;
					int count = 0;
					String key = null;
					Object value = null;

					ps2 = databaseBoneUtil.getPrepStatement(conn2, this.sql);
					this.rs = ps2.executeQuery();

					this.rsmd = this.rs.getMetaData();
					while (rs.next() && count < this.pageSize) {
						map = new LinkedHashMap();
						for (int i = 0; i < this.rsmd.getColumnCount(); i++) {
							key = this.rsmd.getColumnName(i + 1);
							value = databaseBoneUtil.checkNull(this.rs
									.getObject(key));
							// value = this.rs.getObject(key);
							map.put(key.trim().toUpperCase(), value);
						}
						currPageList.add(map);
						count++;
					}

					// 计算当前页结束的行数
					this.endRow = this.startRow + count - 1;

				} else {
					this.currPage = 0;
					this.pageCount = 0;
					this.pageSize = 0;
					this.startRow = 0;
					this.endRow = 0;
				}
			}
		} catch (Exception err) {
			currPageList = null;
			err.printStackTrace();
			log.error(err.getMessage(),err);
		} finally {
			try {
				if (null != this.rs) {
					this.rs.close();
				}
				databaseBoneUtil.closePrepStatement(ps1);				
				databaseBoneUtil.closePrepStatement(ps2);
				databaseBoneUtil.closeConnection(conn1);
				databaseBoneUtil.closeConnection(conn2);
				/*
				 * if(null != this.dbt) { this.dbt.close(); }
				 */
			} catch (Exception e) {
				log.error(this.getClass() + " : 关闭rs时出错!");
			}
		}

		return currPageList;
	}

	/**
	 * 获取树形记录，be applied to department in MP project
	 * 
	 * @return 2015-8-24 by 周喜平
	 */
	public List getTreePageData() {

		List currPageList = new ArrayList();
		Connection conn1 = databaseBoneUtil.getConnection();
		Connection conn2 = databaseBoneUtil.getConnection();
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		try {
			if (null != this.sql) {

				StringBuffer sb = new StringBuffer();
				sb.append(" select count(*) from ( ").append(this.sql)
						.append(" ) t0 ");

				ps1 = databaseBoneUtil.getPrepStatement(conn1, sb.toString());
				this.rs = ps1.executeQuery();
				if (rs.next()) {
					// 获得总记录数
					this.rowCount = rs.getInt(1);
				}
				this.rs.close();

				if (this.rowCount > 0) {

					this.pageSize = (this.pageSize < 1 ? 1 : this.pageSize);

					// 计算总页数
					this.pageCount = (this.rowCount + this.pageSize - 1)
							/ this.pageSize;

					// 计算当前页开始的行数
					if (this.currPage < 1) {
						this.currPage = 1;
					} else if (this.currPage > this.pageCount) {
						this.currPage = this.pageCount;
					}
					this.startRow = (this.currPage - 1) * this.pageSize + 1;

					// 获取当前页的数据
					sb = new StringBuffer();
					if (dbtype.equalsIgnoreCase("oracle")){
						// for oracle					
						  sb.append(" select * from ( ")
						  .append("  	select rownum as rn,t.* from ( ")
						  .append(this.sql).append(" ) t ") .append(" )  t1")
						  .append(" where rn between ").append(this.startRow)
						  .append(" and ").append(this.startRow + this.pageSize -1);
						 
						// end oracle
					}else{
						// for mysql

						sb.append(" select * from ( ")
								.append("  	select @rownum:=@rownum+1 as  rn,t.* from ( ")
								.append(this.sql)
								.append(" ) t,(select @rownum:=0) t1 ")
								.append(" )  t2").append(" where rn between ")
								.append(this.startRow).append(" and ")
								.append(this.startRow + this.pageSize - 1);

						// end mysql

					}
					

					
					/*
					 * sb.append(" select * from ( ").append(this.sql).append(" )  "
					 * )
					 * .append("  where rownum between ").append(this.startRow)
					 * .append("        and ").append(this.startRow +
					 * this.pageSize -1);
					 */
					this.sql = sb.toString();
					Map map = null;
					int count = 0;
					String key = null;
					Object value = null;

					ps2 = databaseBoneUtil.getPrepStatement(conn2, this.sql);
					this.rs = ps2.executeQuery();

					this.rsmd = this.rs.getMetaData();
					LinkedHashMap <Object,String> preStrMap=new LinkedHashMap <Object,String>();//保存最前面的竖线
					while (rs.next() && count < this.pageSize) {
						map = new LinkedHashMap();
						for (int i = 0; i < this.rsmd.getColumnCount(); i++) {
							key = this.rsmd.getColumnName(i + 1);
							value = databaseBoneUtil.checkNull(this.rs
									.getObject(key));
							// value = this.rs.getObject(key);							
							map.put(key.trim().toUpperCase(), value);
						}
						if (map.get("SLEVEL").toString().equalsIgnoreCase("1")){
							if (map.get("ISLAST").toString().equalsIgnoreCase("0")){
								preStrMap.put(preStrMap.size()+1, "<span class='dept_center_pre'></span>");
							}
							map.put("DISDEPTNAME","<span class='root_open'></span><span class='dept_open'></span>"+map.get("DEPTNAME").toString());
							//增加DISDEPTNAME仅用于显示
						}else if (map.get("ISPARENT").toString().equalsIgnoreCase("1")){
							map.put("DISDEPTNAME",HtmlUtil.createHtmlSpace((Integer.parseInt(map.get("SLEVEL").toString())-1)*6)+"<span class='dept_center_open'></span><span class='dept_open'></span>"+map.get("DEPTNAME").toString());
						}else if (map.get("ISLAST").toString().equalsIgnoreCase("1")){
							
							map.put("DISDEPTNAME",""+HtmlUtil.createHtmlSpace((Integer.parseInt(map.get("SLEVEL").toString())-1)*6)+"<span class='dept_center_leaf_last'></span>"+map.get("DEPTNAME").toString());
						}else{
							map.put("DISDEPTNAME",""+HtmlUtil.createHtmlSpace((Integer.parseInt(map.get("SLEVEL").toString())-1)*6)+"<span class='dept_center_leaf_open'></span>"+map.get("DEPTNAME").toString());	
						}
						//增加DISDEPTNAME仅用于显示
						currPageList.add(map);
						count++;
					}

					// 计算当前页结束的行数
					this.endRow = this.startRow + count - 1;

				} else {
					this.currPage = 0;
					this.pageCount = 0;
					this.pageSize = 0;
					this.startRow = 0;
					this.endRow = 0;
				}
			}
		} catch (Exception err) {
			currPageList = null;
			err.printStackTrace();
			log.error(err.getMessage(),err);
			
		} finally {
			try {
				if (null != this.rs) {
					this.rs.close();
				}
				databaseBoneUtil.closePrepStatement(ps1);
				databaseBoneUtil.closeConnection(conn1);
				databaseBoneUtil.closePrepStatement(ps2);
				databaseBoneUtil.closeConnection(conn2);
				/*
				 * if(null != this.dbt) { this.dbt.close(); }
				 */
			} catch (Exception e) {
				log.error(this.getClass() + " : 关闭rs时出错!");
			}
		}

		return currPageList;
	}

	/**
	 * 根据传入的当前页码,和每页的大小及sql语句.可以取得该页所有的记录List(每条记录是一个:
	 * HashMap),总行数,总页数,开始行数,结束行数数 在页面取值时,把字段名转为大写,为某些数据库区分大小写
	 * 
	 * @param sql
	 *            查询的sql语句
	 * @return List
	 */
	public List getCurrPageData(String sql) {
		this.sql = sql;
		return this.getCurrPageData();
	}

	public List getTreePageData(String sql) {
		this.sql = sql;
		return this.getTreePageData();
	}

	/**
	 * 根据传入的当前页码,和每页的大小及sql语句.可以取得该页所有的记录List(每条记录是一个:
	 * HashMap),总行数,总页数,开始行数,结束行数数 在页面取值时,把字段名转为大写,为某些数据库区分大小写
	 * 
	 * @param sql
	 *            查询的sql语句
	 * @param currPage
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @return
	 */
	public List getCurrPageData(String sql, int currPage, int pageSize) {
		this.sql = sql;
		this.currPage = currPage;
		this.pageSize = pageSize;
		return this.getCurrPageData();
	}

	/**
	 * 根据传入的当前页码,和每页的大小及sql语句.可以取得该页所有的记录List(每条记录是一个:
	 * HashMap),总行数,总页数,开始行数,结束行数数 在页面取值时,把字段名转为大写,为某些数据库区分大小写
	 * 
	 * @param sql
	 *            查询的sql语句
	 * @param parameters
	 *            sql语句的参数(对象数组)
	 * @return List
	 */
	public List getCurrPageData(String sql, Object[] parameters) {

		List currPageList = new ArrayList();
		Connection conn1 = null;
		Connection conn2 = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		try {
			conn1 = databaseBoneUtil.getConnection();
			conn2 = databaseBoneUtil.getConnection();
			if (null != sql) {
				this.sql = sql;
				// this.dbt = new DBTools();

				StringBuffer sb = new StringBuffer();
				sb.append(" select count(*) from ( ").append(this.sql)
						.append(" ) t0");

				ps1 = databaseBoneUtil.getPrepStatement(conn1, sb.toString());

				for (int i = 0; i < parameters.length; i++) {
					if (parameters[i] instanceof Timestamp) {
						ps1.setTimestamp(i + 1, (Timestamp) parameters[i]);
					} else {
						ps1.setObject(i + 1, parameters[i]);
					}

				}

				this.rs = ps1.executeQuery();
				if (rs.next()) {
					// 获得总记录数
					this.rowCount = rs.getInt(1);
				}
				this.rs.close();

				if (this.rowCount > 0) {

					// 计算总页数
					this.pageCount = (this.rowCount + this.pageSize - 1)
							/ this.pageSize;

					// 计算当前页开始的行数
					if (this.currPage < 1 || this.currPage > this.pageCount) {
						this.currPage = 1;
					}
					this.startRow = (this.currPage - 1) * this.pageSize + 1;

					// 获取当前页的数据
					sb = new StringBuffer();
					if (dbtype.equalsIgnoreCase("oracle")){
						// for oracle
						
						  sb.append(" select * from ( ")
						  .append("  	select rownum as rn,t.* from ( "
						  ).append(this.sql).append(" ) t ") .append(" ) ")
						  .append(
						  " where rn between ").append(this.startRow).append
						  (" and ").append(this.startRow + this.pageSize -1);
						 
					}else{
						// for mysql test
						sb.append(" select * from ( ")
								.append("  	select @rownum:=@rownum+1 as  rn,t.* from ( ")
								.append(this.sql)
								.append(" ) t,(select @rownum:=0) t1 ")
								.append(" )  t2").append(" where rn between ")
								.append(this.startRow).append(" and ")
								.append(this.startRow + this.pageSize - 1);

					}
					
					
					/*
					 * sb.append(" select * from ( ").append(this.sql).append(" )  "
					 * )
					 * .append("  where rownum between ").append(this.startRow)
					 * .append("        and ").append(this.startRow +
					 * this.pageSize -1);
					 */
					this.sql = sb.toString();

					LinkedHashMap map = null;
					int count = 0;
					String key = null;
					Object value = null;
					log.info(this.sql);
					ps2 = databaseBoneUtil.getPrepStatement(conn2, this.sql);

					for (int i = 0; i < parameters.length; i++) {
						if (parameters[i] instanceof Timestamp) {
							ps2.setTimestamp(i + 1, (Timestamp) parameters[i]);
						} else {
							ps2.setObject(i + 1, parameters[i]);
						}

					}
					this.rs = ps2.executeQuery();
					this.rsmd = this.rs.getMetaData();
					while (rs.next() && count < this.pageSize) {
						map = new LinkedHashMap();
						for (int i = 0; i < this.rsmd.getColumnCount(); i++) {
							key = this.rsmd.getColumnName(i + 1);
							value = databaseBoneUtil.checkNull(this.rs
									.getObject(key));
							// value = this.rs.getObject(key);
							map.put(key.trim().toUpperCase(), value);
						}
						currPageList.add(map);
						count++;
					}

					// 计算当前页结束的行数
					this.endRow = this.startRow + count - 1;

				} else {
					this.currPage = 0;
					this.pageCount = 0;
					this.pageSize = 0;
					this.startRow = 0;
					this.endRow = 0;
				}
			}
		} catch (Exception err) {
			currPageList = null;
			//err.printStackTrace();
			log.error(err.getMessage(),err);
		} finally {
			try {
				if (null != this.rs) {
					this.rs.close();
				}
				databaseBoneUtil.closePrepStatement(ps1);
				databaseBoneUtil.closeConnection(conn1);
				databaseBoneUtil.closePrepStatement(ps2);
				databaseBoneUtil.closeConnection(conn2);
				/*
				 * if(null != this.dbt) { this.dbt.close(); }
				 */
			} catch (Exception e) {
				log.error(this.getClass() + " : 关闭rs时出错!");
			}
		}

		return currPageList;
	}
	String map2Str(LinkedHashMap <Object, String> preStrMap){
		String reString="";
		for (int i = 1; i <= preStrMap.size(); i++) {
			reString+=preStrMap.get(i);
		}
		return reString;
	}
}
