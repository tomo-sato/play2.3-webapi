package jp.dcworks.webapi.controllers;

import java.util.HashMap;
import java.util.Map;

import jp.dcworks.webapi.params.SampleParams;
import jp.dcworks.webapi.results.ApiResult;
import play.Logger;
import play.data.Form;
import play.mvc.Result;

/**
 * WEB API コントローラークラス。
 * <p>WEB API エンドポイントアクションを定義する。
 * @author tomo-sato
 */
public class WebApi extends AppWebApiController {

	/**
	 * サンプルAPIエンドポイント。
	 * <p>[GET] /wapi/sample
	 * @return JSON文字列
	 */
	public static Result sample() {
		// 返却値初期化
		ApiResult ret = new ApiResult(ApiResult.API_RES_SUCCESS);
		
		// パラメータマッピングバリデーションチェック
		Form<SampleParams> requestParams = Form.form(SampleParams.class).bindFromRequest();

		// バリデーションチェック結果
		if (requestParams.hasErrors()) {
			ret.setErrors(requestParams.errorsAsJson());
			ret.setResult(ApiResult.API_RES_FAILURE);

			return badRequest(ret.render());
		}
		
		// パラメータ取得
		SampleParams params = requestParams.get();
		
		// ※登録処理等
		Logger.info(params.toString());
		
		// レスポンスセット
		Map<String, Object> retMap = new HashMap<>();
		retMap.put("token", "hogehoge");
		ret.setContent(retMap);
		return ok(ret.render());
	}
}
