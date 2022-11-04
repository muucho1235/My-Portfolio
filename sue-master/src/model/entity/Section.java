package model.entity;

//部署モデルクラス。
public class Section {

	//部署コード。
	private String sectionCode;
	//部署名。
	private String sectionName;
	//Sectionのコンストラクタ。
	public Section() {
	}

	//部署コードを返す。
	public String getSectionCode() {
		return sectionCode;
	}

	//指定したsectionCodeをセットする。
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	//部署名を返す。
	public String getSectionName() {
		return sectionName;
	}

	//指定したsectionNameをセットする。
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
}
