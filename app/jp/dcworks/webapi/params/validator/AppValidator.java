package jp.dcworks.webapi.params.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;

import jp.dcworks.webapi.core.components.UtilityComponent;
import play.data.validation.Constraints;
import play.libs.F;
import play.libs.F.Tuple;

/**
 * アプリケーション固有のバリデーション定義クラス。
 * @author tomo-sato
 */
public class AppValidator extends Constraints {

	/**
	 * 日付バリデーションアノテーション定義。
	 * <p>使用方法
	 * <pre>
	 * {@code @AppValidator.Date(value="yyyyMMdd", message="日付が不正です。")}
	 * </pre>
	 * @author tomo-sato
	 * @see jp.dcworks.webapi.params.validator.AppValidator.DateValidator
	 */
	@Target({ java.lang.annotation.ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = { AppValidator.DateValidator.class })
	@play.data.Form.Display(name = "appvalidator.date", attributes = { "value" })
	public static @interface Date {
		String message() default "error.invalid";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};

		String value();
	}

	/**
	 * 日付バリデーションクラス。
	 * @author tomo-sato
	 * @see jp.dcworks.webapi.params.validator.AppValidator.Date
	 */
	public static class DateValidator extends Constraints.Validator<String>
			implements ConstraintValidator<AppValidator.Date, String>{

		private String format;

		/**
		 * 初期化処理。
		 * <p>このクラスの{@code format}を{@code paramFormat.value()}で初期化する。
		 */
		@Override
		public void initialize(AppValidator.Date paramFormat) {
			this.format = paramFormat.value();
		}

		/**
		 * バリデーションチェック。
		 * <p>指定された日付{@code date}が、フォーマット{@code format}に適合する場合、{@code true}。
		 * 適合しない場合、{@code false}を返す。
		 */
		@Override
		public boolean isValid(String date) {
			if (UtilityComponent.isEmpty(date)) {
				return true;
			}
			return UtilityComponent.toDate(date, this.format) != null;
		}

		/**
		 * デフォルトエラーメッセージキー。
		 */
		@Override
		public Tuple<String, Object[]> getErrorMessageKey() {
			return F.Tuple("error.invalid", new Object[0]);
		}
	}
}