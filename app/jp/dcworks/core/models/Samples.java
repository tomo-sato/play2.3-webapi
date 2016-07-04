package jp.dcworks.core.models;

import java.util.Date;

import javax.persistence.Entity;

/**
 * サンプル（samples）モデル。
 * @author tomo-sato
 */
@SuppressWarnings("serial")
@Entity
public class Samples extends AppModel {

	/** 名前 */
	public String name;

	/** 年齢 */
	public Integer age;

	/** 誕生日 */
	public Date birthday;
}
