package jp.dcworks.webapi.results;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;

/**
 * API結果格納クラス。
 * @author tomo-sato
 */
public class APIResult {

	/** API結果：0.成功 */
	public static final int API_RES_SUCCESS = 0;

	/** API結果：1.失敗 */
	public static final int API_RES_FAILURE = 1;

	/** API結果：999.システムエラー */
	public static final int API_RES_SYS_ERROR = 999;


	/** 処理結果 **/
	private Integer result;

	/** コンテンツ **/
	private Map<String, Object> content;


	/**
	 * コンストラクタ。
	 * @param result 処理結果
	 */
	public APIResult(Integer result) {
		super();
		this.result = result;
		this.content = new HashMap<String, Object>();

	}

	/**
	 * コンストラクタ。
	 * @param result 処理結果
	 * @param content コンテンツ
	 */
	public APIResult(Integer result, Map<String, Object> content) {
		super();
		this.result = result;
		this.content = content;
	}

	/**
	 * 処理結果セット。
	 * @param result 処理結果
	 */
	public void setResult(Integer result) {
		this.result = result;
	}

	/**
	 * コンテンツセット。
	 * @param content コンテンツ
	 */
	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

	/**
	 * コンテンツレンダリング。
	 * @return JSON jsonオブジェクト
	 */
	public JsonNode render() {
		content.put("result", result);
		return Json.toJson(content);
	}
}
