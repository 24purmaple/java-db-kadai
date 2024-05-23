package kadai.kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {

	public static void main(String[] args) {
		
		Connection con = null;
		Statement statement = null;
		
		try {
			//1.データベース接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"24purMAple0622"
					);
			
			System.out.println("データベース接続成功：" + con);
			
			//2.SQLクエリ(更新)の準備
			statement = con.createStatement();
			String sqlUpdate ="""
					UPDATE scores SET score_math = 95,
									  score_english = 80 WHERE id = 5;
					""";
			
			//2.SQLクエリ(並べ替え)の準備
			String sqlOrder = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC;";

			
			//3.SQLクエリ(更新)の実行
			System.out.println("レコード更新を実行します");
			int rowCnt = statement.executeUpdate(sqlUpdate);
			System.out.println(rowCnt + "件のレコードが更新されました");
			
			//3.SQLクエリ(並べ替え)の実行
			ResultSet result = statement.executeQuery(sqlOrder);
			System.out.println("数学・英語の点数が高い順に並べ替えました");
			
			//4.SQLクエリの実行結果を抽出
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				int math = result.getInt("score_math");
				int english = result.getInt("score_english");
				System.out.println(rowCnt+ "件目：生徒ID=" + id 
										+ "／氏名=" + name
										+ "／数学=" + math
										+ "／英語=" + english);
			}
			
		} catch(SQLException e){
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			//5.オブジェクトを解放
			if( statement != null) {
				try { statement.close();} catch(SQLException ignore) {}
			}
			if( con != null) {
				try { con.close();} catch(SQLException ignore) {}
			}
		}

	}

}
