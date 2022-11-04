package model.entity;

import java.util.Date;

//従業員一覧画面表示モデルクラス。
public class ViewListDisplay {

	//従業員コード。
	private String employeeCode;
	//氏名。
	private String employeeName;
	//氏名かな。
	private String employeeKanaName;
	//性別。
	private String gender;
	//生年月日。
	private Date birthDay;
	//部署名。
	private String sectionName;
	//入社日。
	private Date hireDate;
	//ViewListDisplayのコンストラクタ。
	public ViewListDisplay() {
	}

	//従業員コードを返す。
	public String getEmployeeCode() {
		return employeeCode;
	}

	//指定したemployeeCodeをセットする。
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	//氏名を返す。
	public String getEmployeeName() {
		return employeeName;
	}

	//指定したemployeeNameをセットする。
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	//氏名かなを返す。
	public String getEmployeeKanaName() {
		return employeeKanaName;
	}

	//指定したemployeeKanaNameをセットする。
	public void setEmployeeKanaName(String employeeKanaName) {
		this.employeeKanaName = employeeKanaName;
	}

	//性別を返す。
	public String getGender() {
		return gender;
	}

	//数値を受け取って対応する文字列をgenderフィールドにセットする。
	public void setGender(int gender) {
		if(gender == 0) {
			this.gender = "男性";
		} else {
			this.gender = "女性";
		}
	}

	//生年月日を返す。
	public Date getBirthDay() {
		return birthDay;
	}

	//指定したbirthDayをセットする。
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	//部署名を返す。
	public String getSectionName() {
		return sectionName;
	}

	//指定したsectionNameをセットする。
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	//入社日を返す。
	public Date getHireDate() {
		return hireDate;
	}

	//指定したhireDateをセットする。
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
}
