package kadai.kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement statement = null;
		
		//追加する投稿データ
		String[][] postList = {
			{"1003","2023-02-08", "昨日の夜は徹夜でした・・", "13"},
			{"1002","2023-02-08", "お疲れ様です！", "12"},
			{"1003","2023-02-09", "今日も頑張ります！", "18"},
			{"1001","2023-02-09", "無理は禁物ですよ！", "17"},
			{"1002","2023-02-10", "明日から連休ですね！","20"}
		};
		
		try {
			//1.データベース接続
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/challenge_java",
				"root",
				"24purMAple0622"
			);
			
			System.out.println("データベース接続成功");
			
			//2.SQLクエリの準備
			String sql = "INSERT INTO posts(user_id, posted_at, post_content, likes) VALUES( ?, ?, ?, ?);";
			String sql2 = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002";
			statement = con.prepareStatement(sql);
			
			//リストの1行目から順番に読み込む
			int rowCnt = 0;
			for( int i = 0; i < postList.length; i++) {
				// SQLクエリの「?」部分をリストのデータに置き換える
				statement.setString(1,postList[i][0]);
				statement.setString(2,postList[i][1]);
				statement.setString(3,postList[i][2]);
				statement.setString(4,postList[i][3]);
				
				//3.SQLクエリの実行
				System.out.println("レコード追加：" + statement.toString() );
				rowCnt = statement.executeUpdate();
				System.out.println( rowCnt + "件のレコードが追加されました");
				//3-2.SQLクエリ2の実行
				ResultSet result = statement.executeQuery(sql2);
				System.out.println("ユーザーIDが1002のレコードを検索しました");
				
				//4.SQLクエリの実行結果を抽出
				while(result.next()) {
					Date   at      = result.getDate("posted_at");
					String content = result.getString("post_content");
					int    likes   = result.getInt("likes");
					System.out.println(result.getRow() +"件目:投稿日時="+ 
										at + "／投稿内容=" + 
										content + "／いいね数=" +
										likes);
				}
			}
			
		} catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			//5.使用したオブジェクトを解放
			if( statement != null ) {
				try { statement.close(); } catch(SQLException ignore) {}
			}
			if ( con != null ) {
				try { con.close(); } catch(SQLException ignore ) {}
			}
		}
		

	}

}
