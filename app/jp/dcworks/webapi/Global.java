package jp.dcworks.webapi;


import jp.dcworks.webapi.results.ApiResult;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;

/**
 * アプリケーショングローバル設定クラス。
 * <p>起動、停止、エラー関連のイベントリスナーを実装。
 *
 * @author tomo-sato
 */
public class Global extends GlobalSettings {

	/**
	 * アプリケーション起動イベント。
	 * @see play.GlobalSettings#onStart(play.Application)
	 */
	@Override
	public void onStart(Application app) {
		super.onStart(app);
		Logger.info("=== play2.3-webapi start ===");
	}

	/**
	 * アプリケーション停止イベント。
	 * @see play.GlobalSettings#onStop(play.Application)
	 */
	@Override
	public void onStop(Application app) {
		Logger.info("=== play2.3-webapi stop ===");
		super.onStop(app);
	}

	/**
	 * 404：アクションが見つからない場合。
	 * @see play.GlobalSettings#onHandlerNotFound(play.mvc.Http.RequestHeader)
	 */
	@Override
	public Promise<Result> onHandlerNotFound(RequestHeader request) {
		Logger.warn(new StringBuffer("Request 404 Error：request=").append(request).toString());

		// API結果：1.失敗
		ApiResult ret = new ApiResult(ApiResult.API_RES_FAILURE);
		return Promise.<Result>pure(Results.notFound(ret.render()));
	}

	/**
	 * 400：リクエストエラー。
	 * @see play.GlobalSettings#onBadRequest(play.mvc.Http.RequestHeader, java.lang.String)
	 */
	@Override
	public Promise<Result> onBadRequest(RequestHeader request, String error) {
		Logger.warn(new StringBuffer("Request 400 Error：request=").append(request).append(", error=").append(error).toString());

		// API結果：1.失敗
		ApiResult ret = new ApiResult(ApiResult.API_RES_FAILURE);
		return Promise.<Result>pure(Results.badRequest(ret.render()));
	}

	/**
	 * 500：エラー共通。
	 * @see play.GlobalSettings#onError(play.mvc.Http.RequestHeader, java.lang.Throwable)
	 */
	@Override
	public Promise<Result> onError(RequestHeader request, Throwable t) {
		Logger.error(new StringBuffer("Request 500 Error：request=").append(request).toString(), t);

		// 999.システムエラー
		ApiResult ret = new ApiResult(ApiResult.API_RES_SYS_ERROR);
		return Promise.<Result>pure(Results.internalServerError(ret.render()));
	}
}
