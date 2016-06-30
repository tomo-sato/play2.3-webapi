package jp.dcworks.webapi.core.components;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

/**
 * play.libs.ws.WSクライアント。
 * @author tomo-sato
 */
public class WSClient {

	/** Logger定義 */
	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(UtilityComponent.class.getName());

	/** リクエストタイムアウト：デフォルト10分 */
	private Long timeout = Long.valueOf(1000 * 60 * 10);
	
	/** レスポンス */
	public WSResponse wsResponse = null;
	
	/** リクエスト結果 */
	public Result result = null;
	
	/**
	 * リクエスト結果格納クラス。
	 * @author tomo-sato
	 */
	public static class Result {
		/** ステータス */
		Integer status;
		/** レスポンスBody */
		org.json.JSONObject body;
		/** 処理結果 */
		Integer result;
	}
	
	/**
	 * デフォルトコンストラクタ。
	 */
	public WSClient() {}
	
	/**
	 * URL、POSTパラメータを指定しリクエストを送る。
	 * @param url URL
	 * @param params POSTパラメータ
	 * @return play.libs.ws.WSResponseを返す。
	 */
	public WSResponse post(String url, Map<String, String> params) {
		
		LOGGER.info("■■■リクエスト■■■：url=" + url + System.lineSeparator() + "params=" + System.lineSeparator() + UtilityComponent.toStringMap(params));
		
		String boundary = "--XYZ123--";
		String body = buildMultipart(boundary, params);
		
		WSResponse response = WS.url(url)
				.setHeader("Content-Type", "multipart/form-data; boundary=" + boundary)
				.setHeader("Content-length", String.valueOf(body.length())).post(body).get(timeout);
		
		try {
			this.result = new Result();
			result.status = response.getStatus();
			result.body = new JSONObject(response.getBody());
			result.result = result.body.getInt("result");
		
			LOGGER.info("■■■レスポンス■■■：result.status=" + result.status + "," + System.lineSeparator()
					+ "result.body=" + System.lineSeparator()
					+ result.body.toString(2) + System.lineSeparator());
		
		} catch (JSONException e) {
			LOGGER.warn(e.getMessage(), e);
		}
		return response;
	}

	/**
	 * URL、GETパラメータを指定しリクエストを送る。
	 * @param url URL
	 * @param params GETパラメータ
	 * @return play.libs.ws.WSResponseを返す。
	 */
	public WSResponse get(String url, Map<String, String> params) {
		
		LOGGER.info("■■■リクエスト■■■：url=" + url + System.lineSeparator() + "params=" + System.lineSeparator() + UtilityComponent.toStringMap(params));
		
		StringBuffer paramsBuff = new StringBuffer();
		if (!UtilityComponent.isEmpty(params)) {
			paramsBuff.append("?");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				
				paramsBuff.append(key).append("=").append(val).append("&");
			}
		}
		url = url + paramsBuff.toString();
		
		WSResponse response = WS.url(url).get().get(timeout);
		
		try {
			this.result = new Result();
			result.status = response.getStatus();
			result.body = new JSONObject(response.getBody());
			result.result = result.body.getInt("result");
		
			LOGGER.info("■■■レスポンス■■■：result.status=" + result.status + "," + System.lineSeparator()
					+ "result.body=" + System.lineSeparator()
					+ result.body.toString(2) + System.lineSeparator());
		
		} catch (JSONException e) {
			LOGGER.warn(e.getMessage(), e);
		}
		return response;
	}

	/**
	 * マルチパートリクエストパラメータを作成する。
	 * @param boundary 区切り文字
	 * @param data データ
	 * @return マルチパートリクエスト文字列
	 */
	private static String buildMultipart(String boundary, Map<String, String> data) {
		String body = "";
		for (String key : data.keySet()) {
			body += "--" + boundary + "\r\n"
					+ "Content-Disposition: form-data; name=\""
					+ key + "\"\r\n\r\n"
					+ data.get(key) + "\r\n";
		}
		return body += "--" + boundary + "--";
	}
}
