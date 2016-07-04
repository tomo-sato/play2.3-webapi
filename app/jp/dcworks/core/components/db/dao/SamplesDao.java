package jp.dcworks.core.components.db.dao;

import java.util.Date;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;

import jp.dcworks.core.components.db.SamplesComponent;


/**
 * サンプル（samples）モデルのネイティブクエリによる操作クラス。
 * <p>アプリケーションとは祖結合としたい為、直接の参照は行わない。
 * <br>利用する場合、{@link SamplesComponent}を経由しアクセスする。
 * @author tomo-sato
 * @see jp.dcworks.core.components.db.SamplesComponent
 */
public class SamplesDao {

	/**
	 * サンプル（samples）の削除フラグ無効（{@code false}）のレコードを全件削除（{@code true}）する。
	 * @return UPDATE結果件数を返す。
	 */
	protected static int deleteAll() {
		String sql = "UPDATE samples SET delete_flg = true, modified = :modified WHERE delete_flg = false";
		SqlUpdate sqlUpdate = Ebean.createSqlUpdate(sql);
		sqlUpdate.setParameter("modified", new Date());
		return sqlUpdate.execute();
	}
}
