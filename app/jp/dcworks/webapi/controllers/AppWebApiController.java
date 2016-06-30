package jp.dcworks.webapi.controllers;

import jp.dcworks.webapi.actions.UtilityAction;
import play.mvc.Controller;

/**
 * WEB API 基底クラス。
 * <p>WEB API 共通処理を定義する。
 * @author tomo-sato
 */
@UtilityAction.RequestTrace
public class AppWebApiController extends Controller {
}
