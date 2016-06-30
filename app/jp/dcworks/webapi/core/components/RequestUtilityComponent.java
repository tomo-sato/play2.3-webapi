package jp.dcworks.webapi.core.components;

import java.util.Map;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.Request;

/**
 * リクエスト操作に関するユーティリティクラス。
 * @author tomo-sato
 */
public class RequestUtilityComponent {
	
	/** Logger定義 */
	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(RequestUtilityComponent.class.getName());
	
	/** ブランク文字列 */
	public static final String BLANK = "";

	/**
	 * リクエストからkeyのクエリ文字列を取得する。
	 * @param request リクエスト
	 * @param key キー
	 * @return クエリ文字列
	 */
	public static String getQueryString(Request request, String key) {
		if (request == null
				|| key == null
				|| key.length() <= 0) {

			LOGGER.warn("Parameter error：request=" + request + ", key=" + key);
			return null;
		}
		return request.getQueryString(key);
	}

	/**
	 * リクエストからクエリ文字列Mapを取得する。
	 * @param request リクエスト
	 * @return クエリ文字列Map
	 */
	public static Map<String, String[]> getQueryString(Request request) {
		if (request == null) {

			LOGGER.warn("Parameter error：request=" + request);
			return null;
		}
		return request.queryString();
	}

	/**
	 * リクエストからkeyのFrom文字列を取得する。
	 * @param request リクエスト
	 * @param key キー
	 * @return Post文字列
	 */
	public static String getFromPostString(Request request, String key) {
		if (request == null
				|| request.body() == null
				|| request.body().asMultipartFormData() == null
				|| key == null
				|| key.length() <= 0) {

			LOGGER.warn("Parameter error：request=" + request + ", key=" + key);
			return null;
		}

		MultipartFormData form = request.body().asMultipartFormData();
		if (form == null
				|| form.asFormUrlEncoded() == null
				|| form.asFormUrlEncoded().get(key) == null
				|| form.asFormUrlEncoded().get(key).length <= 0) {

			LOGGER.warn("Form error：form=" + form + ", key=" + key);
			return null;
		}

		String str = form.asFormUrlEncoded().get(key)[0];
		return str;
	}

	/**
	 * リクエストからkeyのFrom文字列を取得する。
	 * @param request リクエスト
	 * @param key キー
	 * @return Post文字列
	 */
	public static String[] getFromPostStrings(Request request, String key) {
		if (request == null
				|| request.body() == null
				|| request.body().asMultipartFormData() == null
				|| key == null
				|| key.length() <= 0) {

			LOGGER.warn("Parameter error：request=" + request + ", key=" + key);
			return null;
		}

		MultipartFormData form = request.body().asMultipartFormData();
		if (form == null
				|| form.asFormUrlEncoded() == null
				|| form.asFormUrlEncoded().get(key) == null) {

			LOGGER.warn("Form error：form=" + form + ", key=" + key);
			return null;
		}

		String[] str = form.asFormUrlEncoded().get(key);
		return str;
	}

	/**
	 * リクエストからkeyのFrom文字列を取得する。
	 * @param request リクエスト
	 * @param key キー
	 * @return Post文字列
	 */
	public static String getFromPostStringNullToBlank(Request request, String key) {
		if (request == null
				|| request.body() == null
				|| request.body().asMultipartFormData() == null
				|| key == null
				|| key.length() <= 0) {

			LOGGER.warn("Parameter error：request=" + request + ", key=" + key);
			return BLANK;
		}

		MultipartFormData form = request.body().asMultipartFormData();
		if (form == null
				|| form.asFormUrlEncoded() == null
				|| form.asFormUrlEncoded().get(key) == null
				|| form.asFormUrlEncoded().get(key).length <= 0) {

			LOGGER.warn("Form error：form=" + form + ", key=" + key);
			return BLANK;
		}

		String str = form.asFormUrlEncoded().get(key)[0];
		return (str == null) ? BLANK : str;
	}
	
	/**
	 * リクエストからFrom文字列Mapを取得する。
	 * @param request リクエスト
	 * @return Post文字列Map
	 */
	public static Map<String, String[]> getFromPostStringMap(Request request) {
		if (request == null
				|| request.body() == null
				|| request.body().asMultipartFormData() == null) {

			LOGGER.warn("Parameter error：request=" + request);
			return null;
		}

		MultipartFormData form = request.body().asMultipartFormData();
		if (form == null
				|| form.asFormUrlEncoded() == null) {

			LOGGER.warn("Form error：form=" + form);
			return null;
		}

		return form.asFormUrlEncoded();
	}
}
