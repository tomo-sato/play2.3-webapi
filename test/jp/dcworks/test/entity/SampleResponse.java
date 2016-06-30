package jp.dcworks.test.entity;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * サンプルAPIテスト用レスポンスパラメータクラス。
 * @author tomo-sato
 */
public class SampleResponse extends AppResponse {
	
	/** レスポンス：トークン */
	public String token;
	
	/**
	 * デフォルトコンストラクタ。
	 */
	public SampleResponse() {}
	
	/**
	 * コンストラクタ。
	 * @param result 結果
	 */
	public SampleResponse(Integer result) {
		this.result = result;
	}
	
	/**
	 * レスポンスマッピング処理。
	 * @param json レスポンスjson
	 * @return レスポンスマッピングオブジェクト
	 */
	public static SampleResponse parseResponse(String json) {
		SampleResponse res = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			res = mapper.readValue(json, SampleResponse.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
