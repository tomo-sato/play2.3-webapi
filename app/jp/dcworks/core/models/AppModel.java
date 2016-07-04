package jp.dcworks.core.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;

import play.db.ebean.Model;

/**
 * モデルの基底クラス。
 * <p>各モデルはこのクラスを継承する。
 * @author tomo-sato
 */
@MappedSuperclass
@SuppressWarnings("serial")
public class AppModel extends Model {

	/** プライマリーキー */
	@Id
	@GeneratedValue
	public Long id;

	/** 削除フラグ */
	@Column(name = "delete_flg")
	public Boolean deleteFlg = false;

	/** 作成日時 */
	@Column(name = "created")
	@CreatedTimestamp
	public Date created;

	/** 更新日時 */
	@Column(name = "modified")
	@Version
	public Date modified;

	/**
	 * 保存。
	 * @see play.db.ebean.Model#save()
	 */
	@Override
	public void save() {
		super.save();
	}

	/**
	 * 更新。
	 * @see play.db.ebean.Model#update()
	 */
	@Override
	public void update() {
		super.update();
	}

	/**
	 * 削除フラグを立てて更新。
	 * @see play.db.ebean.Model#delete()
	 */
	public void delete() {
		this.deleteFlg = true;
		super.update();
	}

	/**
	 * 削除フラグを立てる。
	 */
	public void setDeleteFlg() {
		this.deleteFlg = true;
	}
}