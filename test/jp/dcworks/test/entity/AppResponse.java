package jp.dcworks.test.entity;

import java.util.List;
import java.util.Map;

/**
 * テスト用レスポンスパラメータ基底クラス。
 * @author tomo-sato
 */
public class AppResponse {

	/** レスポンス：結果 */
	public Integer result;
	/** レスポンス：エラーメッセージ */
	public Map<String, List<String>> errors;
}
