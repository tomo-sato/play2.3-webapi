package jp.dcworks.webapi.controllers;

import jp.dcworks.webapi.results.APIResult;
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
		APIResult ret = new APIResult(APIResult.API_RES_SUCCESS);
		return ok(ret.render());
	}
}
