package jp.dcworks.test.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * サンプルAPIテスト用リクエストパラメータクラス。
 * @author tomo-sato
 */
public class SampleRequest extends AppRequest {

	/** リクエストパラメータ：名前 */
	public String name;
	/** リクエストパラメータ：年齢 */
	public String age;
	/** リクエストパラメータ：誕生日 */
	public String birthday;
	
	/**
	 * コンストラクタ。
	 * @param name リクエストパラメータ：名前
	 * @param age リクエストパラメータ：年齢
	 * @param birthday リクエストパラメータ：誕生日
	 */
	public SampleRequest(String name, String age, String birthday) {
		this.name = name;
		this.age = age;
		this.birthday = birthday;
	}
	
	/**
	 * パラメータマッピング処理。
	 * @return パラメータMapを返す。
	 */
	public Map<String, String> getParamsMap() {
		Map<String, String> params = new HashMap<String, String>();
		if (this.name != null) {
			params.put("name",   this.name);
		}
		if (this.age != null) {
			params.put("age",    this.age);
		}
		if (this.birthday != null) {
			params.put("birthday", this.birthday);
		}
		return params;
	}
}
