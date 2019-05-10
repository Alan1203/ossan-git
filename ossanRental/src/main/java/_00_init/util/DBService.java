package _00_init.util;

public class DBService {
	public static final String host = "127.0.0.1";
	public static final String DB_MYSQL = "MYSQL";

	public static final String DB_TYPE = DB_MYSQL;

	private static final String DBURL_MySQL = "jdbc:mysql://" + host
			+ "/jspdb?useUnicode=yes&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Taipei";
	public static final String USERID_MySQL = "root";
	public static final String PSWD_MySQL = "Do!ng123";

	private static final String DROP_MemberOssan_MySQL = "DROP Table IF EXISTS MemberOssan ";
	private static final String DROP_Article_MySQL = "DROP Table IF EXISTS Article ";

	private static final String CREATE_MemberOssan_MySQL = " CREATE TABLE MemberOssan " 
			+ "(seqNo int NOT NULL Auto_Increment, "
			+ " memberId	 		varchar(20), "
			+ " password			varchar(32), " 
			+ " name    			varchar(32), "
			+ " nickname   			varchar(32), "
			+ " uid		   			varchar(32), "
			+ " address 			varchar(64), "
			+ " tel  				varchar(15), "
			+ " email 				varchar(64), " 
			+ " birthday	    	Date, "
			+ " registerTime    	DateTime, "		 
			+ " memberImage  		LongBlob, "
			+ " fileName     		varchar(20), "
			+ " quote	      		TinyText, " 
			+ " intro	      		LongText, " 
			+ " PRIMARY KEY 		(seqNo) "
			+ " ) ENGINE=INNODB CHARACTER SET utf8 COLLATE utf8_general_ci";
	
	private static final String DROP_OssanOrders_MySQL = "DROP Table IF EXISTS OssanOrders ";
	
	private static final String CREATE_OssanOrders_MySQL = " CREATE TABLE OssanOrders " 
			+ "(orderNo int NOT NULL Auto_Increment, "
			+ " invoiceTitle	 	varchar(20), "
			+ " address				varchar(64), " 
			+ " BNO    				varchar(8), "
			+ " tel   				varchar(15), "
			+ " email		   		varchar(64), "
			+ " TotalAmount 		decimal(11,1), "
			+ " Comment  			varchar(255), "
			+ " orderDate 			DateTime, " 
			+ " CancelTag	    	varchar(1), "
			+ " ShippingDate    	DateTime, "		  
			+ " PRIMARY KEY 		(orderNo) "
			+ " ) ENGINE=INNODB CHARACTER SET utf8 COLLATE utf8_general_ci";
	
	private static final String CREATE_Article_MySQL = " CREATE TABLE Article " 
			+ "(artNo int NOT NULL Auto_Increment, "
			+ " title    			varchar(32), "
			+ " UpdateTime    		DateTime, "		 
			+ " articleImage  		LongBlob, " 
			+ " article	      		LongText, " 
			+ " fileName	      	varchar(32), " 
			+ " seqNo	      		int     , " 
			+ " PRIMARY KEY 		(artNo) "
			+ " ) ENGINE=INNODB CHARACTER SET utf8 COLLATE utf8_general_ci";
	
	private static final String DROP_OssanOrderItems_MySQL = "DROP Table IF EXISTS OssanOrderItems ";
	
	private static final String CREATE_OssanOrderItems_MySQL = " CREATE TABLE OssanOrderItems " 
			+ "(seqNo int NOT NULL Auto_Increment, "
			+ " orderNo	 			int(11), "
			+ " ossanNo				int(11), " 
			+ " amount    			int(11), "
			+ " unitPrice   		decimal(18,1), "
			+ " Discount		   	decimal(8,3), "	  
			+ " PRIMARY KEY 		(seqNo) "
			+ " ) ENGINE=INNODB CHARACTER SET utf8 COLLATE utf8_general_ci";
	

	public static String getDropOssanOrders() {
		String drop = null;
		if (DB_TYPE.equalsIgnoreCase(DB_MYSQL)) {
			drop = DROP_OssanOrders_MySQL;
		} 
		return drop;
	}

	public static String getCreateOssanOrders() {
		String create = null;
		if (DB_TYPE.equalsIgnoreCase(DB_MYSQL)) {
			create = CREATE_OssanOrders_MySQL;
		}
		return create;
	}	
	
	public static String getDropOssanOrderItems() {
		String drop = null;
		if (DB_TYPE.equalsIgnoreCase(DB_MYSQL)) {
			drop = DROP_OssanOrderItems_MySQL;
		} 
		return drop;
	}

	public static String getCreateOssanOrderItems() {
		String create = null;
		if (DB_TYPE.equalsIgnoreCase(DB_MYSQL)) {
			create = CREATE_OssanOrderItems_MySQL;
		}
		return create;
	}	
	
	public static String getDropMemberOssan() {
		String drop = null;
		if (DB_TYPE.equalsIgnoreCase(DB_MYSQL)) {
			drop = DROP_MemberOssan_MySQL;
		} 
		return drop;
	}

	public static String getCreateMemberOssan() {
		String create = null;
		if (DB_TYPE.equalsIgnoreCase(DB_MYSQL)) {
			create = CREATE_MemberOssan_MySQL;
		}
		return create;
	}	

	public static String getDbUrl() {
		String url = null;
		if (DB_TYPE.equalsIgnoreCase(DB_MYSQL)) {
			url = DBURL_MySQL;
		} 
		return url;
	}

	public static String getUser() {
		String user = null;
		if (DB_TYPE.equalsIgnoreCase(DB_MYSQL)) {
			user = USERID_MySQL;
		}
		return user;
	}

	public static String getPassword() {
		String pswd = null;
		if (DB_TYPE.equalsIgnoreCase(DB_MYSQL)) {
			pswd = PSWD_MySQL;
		}
		return pswd;
	}
	
	public static String addColumnPrivilege() {
		String add = "ALTER TABLE memberOssan ADD COLUMN Privilege VARCHAR(20)";
		return add;
	}
	
	public static String addOneAdmin() {
		String pswd1 = "Do!ng123";
		String pswd2 = GlobalService.getMD5Endocing(GlobalService.encryptString(pswd1));
		String admin = "INSERT INTO MemberOssan (memberID, PASSWORD, NAME, nickname, privilege ) VALUE ( \"admin\",\"" + pswd2 + "\", \"管理員\", \"管理員暱稱\", \"Admin\")";
		System.out.println(admin);
		return admin;
	}
	
	public static String addColumnLocation() {
		String location = "ALTER TABLE memberossan ADD COLUMN( twnorth BOOLEAN DEFAULT TRUE NOT NULL, twmiddle BOOLEAN DEFAULT TRUE NOT NULL,twsouth BOOLEAN DEFAULT TRUE NOT NULL,twother BOOLEAN DEFAULT FALSE NOT NULL)"; 
		return location;
	}
	
	public static String updateRandomColumnLocationNorth() {
		String location =  
				"UPDATE memberossan SET twnorth = (SELECT ROUND(RAND())) WHERE seqNo = (SELECT ROUND((SELECT COUNT(*) FROM (SELECT * FROM memberossan )AS ossanTable)*RAND()));";		
		return location;
	}
	
	public static String updateRandomColumnLocationMiddle() {
		String location =  
				"UPDATE memberossan SET twmiddle = (SELECT ROUND(RAND())) WHERE seqNo = (SELECT ROUND((SELECT COUNT(*) FROM (SELECT * FROM memberossan )AS ossanTable)*RAND()));";		
		return location;
	}
	
	public static String updateRandomColumnLocationSouth() {
		String location =  
				"UPDATE memberossan SET twsouth = (SELECT ROUND(RAND())) WHERE seqNo = (SELECT ROUND((SELECT COUNT(*) FROM (SELECT * FROM memberossan )AS ossanTable)*RAND()));";		
		return location;
	}
	
	public static String updateRandomColumnLocationOther() {
		String location =  
				"UPDATE memberossan SET twother = (SELECT ROUND(RAND())) WHERE seqNo = (SELECT ROUND((SELECT COUNT(*) FROM (SELECT * FROM memberossan )AS ossanTable)*RAND()));";		
		return location;
	}
	
	public static String getDropArticle() {
		String drop = null;
		if (DB_TYPE.equalsIgnoreCase(DB_MYSQL)) {
			drop = DROP_Article_MySQL;
		} 
		return drop;
	}

	public static String getCreateArticle() {
		String create = null;
		if (DB_TYPE.equalsIgnoreCase(DB_MYSQL)) {
			create = CREATE_Article_MySQL;
		}
		return create;
	}
	
	
}
