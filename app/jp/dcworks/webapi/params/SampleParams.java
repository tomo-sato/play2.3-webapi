package jp.dcworks.webapi.params;

import jp.dcworks.webapi.core.components.UtilityComponent;
import jp.dcworks.webapi.params.validator.AppValidator;
import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;

/**
 * サンプルAPI バインドクラス。
 * @author tomo-sato
 */
public class SampleParams {

	/** リクエストパラメータ：名前 */
	@Required(message="名前（name）は必須です。")
	public String name;
	
	/** リクエストパラメータ：年齢 */
	@Required(message="年齢（age）は必須です。")
	@Min(value=0,   message="年齢（age）は0～150で指定してください。")
	@Max(value=150, message="年齢（age）は0～150で指定してください。")
	public Integer age;
	
	/** リクエストパラメータ：誕生日 */
	@Required(message="誕生日（birthday）は必須です。")
	@AppValidator.Date(value=UtilityComponent.DATE_FORMAT_YYYYMMDD, message="誕生日（birthday）はyyyyMMddで指定してください。")
	public String birthday;
	
	/**
	 * toString()のオーバーライド。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return UtilityComponent.toStringField(this);
	}
}
