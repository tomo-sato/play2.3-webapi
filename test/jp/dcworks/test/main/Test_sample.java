package jp.dcworks.test.main;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import jp.dcworks.test.definition.AppTestConsts;
import jp.dcworks.test.entity.SampleRequest;
import jp.dcworks.test.entity.SampleResponse;

/**
 * サンプルAPI TestSuiteクラス。
 * @author tomo-sato
 */
@RunWith(Suite.class)
@SuiteClasses({
	Test_sample.Test_正常処理_リクエスト.class,
})
public class Test_sample extends AppTest {

	/** Logger定義 */
	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(Test_sample.class.getName());

	/**
	 * 正常系テストクラス。
	 * @author tomo-sato
	 */
	@RunWith(Parameterized.class)
	public static class Test_正常処理_リクエスト {

		/** ケースNo. */
		private final String caseNo;
		/** コメントメッセージ */
		private final String message;
		
		/** テスト実施データ */
		private final SampleRequest requestParams;
		/** 想定結果 */
		private final SampleResponse assertResponse;

		/**
		 * コンストラクタ。
		 * @param caseNo ケースNo.
		 * @param message コメントメッセージ
		 * @param requestParams テスト実施データ
		 * @param assertResponse 想定結果
		 */
		public Test_正常処理_リクエスト(String caseNo,
				String message,
				SampleRequest requestParams,
				SampleResponse assertResponse) {

			this.caseNo = caseNo;
			this.message = message;
			this.requestParams = requestParams;
			this.assertResponse = assertResponse;
		}

		/**
		 * テストパラメータ定義。
		 * @return テストパラメータリスト
		 */
		@Parameters(name="ケースNo.[{0}] {1}")
		public static Iterable<Object[]> getParameters() {
			return Arrays.asList(new Object[][] {

				// 正常系
				{"0001", "正常系"
					, new SampleRequest("太郎", "36", "19800630")
					, new SampleResponse(0),},
				{"0002", "正常系"
					, new SampleRequest("次郎", "35", "19810630")
					, new SampleResponse(0),},

				// 異常系
				{"9001", "異常系 名前 キー値なし"
					, new SampleRequest(null, "36", "19800630")
					, new SampleResponse(1),},
				{"9002", "異常系 年齢 キー値なし"
					, new SampleRequest("次郎", null, "19810630")
					, new SampleResponse(1),},
				{"9003", "異常系 誕生日 キー値なし"
					, new SampleRequest("三郎", "36", null)
					, new SampleResponse(1),},
				{"9004", "異常系 名前 値なし"
					, new SampleRequest("", "36", "19800630")
					, new SampleResponse(1),},
				{"9005", "異常系 年齢 値なし"
					, new SampleRequest("次郎", "", "19810630")
					, new SampleResponse(1),},
				{"9006", "異常系 誕生日 値なし"
					, new SampleRequest("三郎", "36", "")
					, new SampleResponse(1),},
				{"9007", "異常系 全部 キー値なし"
					, new SampleRequest(null, null, null)
					, new SampleResponse(1),},
				{"9008", "異常系 全部 値なし"
					, new SampleRequest("", "", "")
					, new SampleResponse(1),},
				{"9009", "異常系 年齢 値不正"
					, new SampleRequest("次郎", "aa", "19810630")
					, new SampleResponse(1),},
				{"9010", "異常系 誕生日 値不正"
					, new SampleRequest("三郎", "36", "aa")
					, new SampleResponse(1),},
			});
		}

		/**
		 * テスト実施メソッド。
		 */
		@Test
		public void Test_正常処理_リクエスト_001() {
			LOGGER.info("▼▼▼ ----- テスト[" + Test_sample.class.getSimpleName() + "_" + this.caseNo + "] " + this.message + " ----- ▼▼▼");
			
			Map<String, String> params = this.requestParams.getParamsMap();
			String strJson = getJsonResponse(AppTestConsts.BASE_WAPI_URL + "sample", params);
			
			SampleResponse apiResponse = SampleResponse.parseResponse(strJson);
			
			assertThat(apiResponse, is(notNullValue()));
			assertThat(apiResponse.result,  is(this.assertResponse.result));
			if (this.assertResponse.result == 0) {
				assertThat(apiResponse.token, is(notNullValue()));
			
			} else if (this.assertResponse.result == 1) {
				assertThat(apiResponse.errors, is(notNullValue()));
			}
		}
	}
}
