package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//データベースに接続するためのクラス。
public class ConnectionManager {
	
	//Javaとsuedbデータベースの接続のためのAPIのURL。
	private static final String URL = "jdbc:mysql://localhost:3306/suedb?characterEncoding=UTF-8";
	
	//suedbデータベースを使うユーザー。
	private static final String USER = "root";
	
	//suedbデータベースを使うパスワード。
	private static final String PW = "root";
	
	 //唯一のインスタンスを生成する。
	private static ConnectionManager instance = new ConnectionManager();
	
	//privateのため新規のインスタンスをつくらせない。
	private ConnectionManager() {}
	
	//唯一のインスタンスを取得する。
	public static ConnectionManager getInstance(){
		return instance;
	}
	
	//データベースに接続する。
	public Connection connect() throws SQLException{
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return DriverManager.getConnection(URL, USER, PW);
	}
}
