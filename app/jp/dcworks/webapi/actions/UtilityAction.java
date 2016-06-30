package jp.dcworks.webapi.actions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import jp.dcworks.webapi.core.components.RequestUtilityComponent;
import jp.dcworks.webapi.core.components.UtilityComponent;
import play.core.j.JavaResultExtractor;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.With;

/**
 * ユーティリティ中継処理。
 * @author tomo-sato
 */
public class UtilityAction {
	
	/** Logger定義 */
	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(UtilityAction.class.getName());

	/**
	 * リクエスト処理のトレース中継処理　アノテーション定義。
	 * @see jp.dcworks.webapi.actions.UtilityAction.RequestTraceAction
	 * @author tomo-sato
	 */
	@With(RequestTraceAction.class)
	@Target({ElementType.TYPE, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface RequestTrace {}

	/**
	 * リクエスト処理のトレース中継処理。
	 * <p>アクション実行前後でリクエスト、レスポンスの情報をログ出力する。
	 * @see jp.dcworks.webapi.actions.UtilityAction.RequestTrace
	 * @author tomo-sato
	 */
	public static class RequestTraceAction extends Action<RequestTraceAction> {

		@Override
		public Promise<Result> call(Context ctx) throws Throwable {
			Request request = ctx.request();
			
			final String prefix = new StringBuffer("[" + RandomStringUtils.randomAlphanumeric(20) + "] ")
					.append("method=").append(request.method())
					.append(", url=").append(request.secure() ? "https://" : "http://").append(request.host()).append(request.uri())
					.toString();
			String startPrefix = "■■■start■■■" + prefix;
			
			Map<String, String[]> map = null;
			if ("GET".equals(request.method())) {
				map = RequestUtilityComponent.getQueryString(request);
			} else {
				map = RequestUtilityComponent.getFromPostStringMap(request);
			}
			
			StringBuffer buffRequest = new StringBuffer(startPrefix)
					.append("：").append(System.lineSeparator())
					.append("[REQUEST PARAMS]").append(System.lineSeparator());
			
			StringBuffer buffParams = new StringBuffer();
			if (!UtilityComponent.isEmpty(map)) {
				for (Map.Entry<String, String[]> entry : map.entrySet()) {
					String key = entry.getKey();
					String[] valArr = entry.getValue();
					
					buffParams.append(key).append(":");
					StringBuffer buffValue = new StringBuffer();
					if (valArr != null) {
						for (String val : valArr) {
							buffValue.append(val).append(", ");
						}
					}
					if (buffValue.length() >= 2) {
						buffValue.delete(buffValue.length() - 2, buffValue.length());
					}
					buffParams.append(buffValue);
					buffParams.append(System.lineSeparator());
				}
			}
			if (buffParams.length() >= 2) {
				buffParams.delete(buffParams.length() - 2, buffParams.length());
			}
			
			buffRequest.append(buffParams).append(System.lineSeparator());
			LOGGER.info(buffRequest.toString());

			try {
				// メイン処理開始
				Promise<Result> promise = delegate.call(ctx);
				
				if (promise == null) {
					LOGGER.info(prefix + " レスポンスデータ取得：promise=null");
				} else {
					promise.map((result) -> {
						byte[] body = JavaResultExtractor.getBody(result, 0L);
						
						if (body == null) {
							LOGGER.info(prefix + " レスポンスデータ取得：body=null");
						} else {
							String strBody = new String(body, "UTF-8");
							
							try {
								JSONObject json = new JSONObject(strBody);
								if (json != null) {
									strBody = json.toString(2);
								}
							} catch (Exception e) {}
							
							String endPrefix = "■■■ end ■■■" + prefix;
							StringBuffer buffResponse = new StringBuffer(endPrefix)
									.append("：").append(System.lineSeparator())
									.append("[RESPONSE PARAMS]").append(System.lineSeparator())
									.append(strBody).append(System.lineSeparator());
							
							LOGGER.info(buffResponse.toString());
						}
						return result;
					});
				}
				
				return promise;
			} catch (Exception e) {
				throw e;
			}
		}
	}
}
