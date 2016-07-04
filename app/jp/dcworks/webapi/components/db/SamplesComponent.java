package jp.dcworks.webapi.components.db;

import java.util.List;

import jp.dcworks.core.components.db.dao.SamplesDao;
import jp.dcworks.core.models.Samples;

/**
 * サンプル（samples）モデルの操作を行うコンポーネントクラス。
 * <p>アプリケーション固有のモデル操作または、共通コンポーネントをラップし機能を提供する。
 * @author tomo-sato
 * @see jp.dcworks.core.components.db.SamplesComponent
 */
public class SamplesComponent extends jp.dcworks.core.components.db.SamplesComponent {

	/**
	 * サンプル（samples）の削除フラグ無効（{@code false}）のレコードを全件削除（{@code true}）する。
	 * @return UPDATE結果件数を返す。
	 * @see jp.dcworks.core.components.db.dao.SamplesDao#deleteAll()
	 */
	public static int deleteAll() {
		return SamplesDao.deleteAll();
	}

	/**
	 * 未削除のサンプル（samples）情報一覧を返す。
	 * @return 未削除のサンプル（samples）情報一覧を返す。
	 * @see jp.dcworks.core.components.db.SamplesComponent#getSamplesList()
	 */
	public static List<Samples> getSamplesList() {
		return jp.dcworks.core.components.db.SamplesComponent.getSamplesList();
	}
}
