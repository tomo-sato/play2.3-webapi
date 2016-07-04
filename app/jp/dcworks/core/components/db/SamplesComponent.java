package jp.dcworks.core.components.db;

import java.util.List;

import com.avaje.ebean.Ebean;

import jp.dcworks.core.components.db.dao.SamplesDao;
import jp.dcworks.core.models.Samples;

/**
 * サンプル（samples）モデルの操作を行うコンポーネントクラス。
 * @author tomo-sato
 */
public class SamplesComponent extends SamplesDao {

	/**
	 * 未削除のサンプル（samples）情報一覧を返す。
	 * @return 未削除のサンプル（samples）情報一覧を返す。
	 */
	protected static List<Samples> getSamplesList() {
		List<Samples> retList =
				Ebean.find(Samples.class)
					.where()
					.eq("delete_flg", false)
					.orderBy("id ASC")
					.findList();

		return retList;
	}
}
