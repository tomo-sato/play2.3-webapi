package jp.dcworks.test.main;
import static play.test.Helpers.*;

import java.io.File;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import jp.dcworks.webapi.core.components.WSClient;
import play.Configuration;
import play.libs.ws.WSResponse;

/**
 * テスト用基底クラス。
 * @author tomo-sato
 */
public class AppTest {
	
	/**
	 * テスト初期化処理。
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		// playアプリケーション起動処理
		Config additionalConfig = ConfigFactory.parseFile(new File("conf/application.conf"));
		Configuration additionalConfiguration = new Configuration(additionalConfig);
		start(fakeApplication(additionalConfiguration.asMap()));
	}
	
	/**
	 * jsonレスポンス取得処理。
	 * @param url URL
	 * @param params パラメータ
	 * @return レスポンス
	 */
	public static String getJsonResponse(String url, Map<String, String> params) {
		WSClient client = new WSClient();
		WSResponse response = client.get(url, params);
		String strJson = "";
		try {
			JSONObject json = new JSONObject(response.getBody());
			strJson = json.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return strJson;
	}
}