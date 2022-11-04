package model.entity;

//従業員モデルクラス。
public class Employee {

	// 従業員コード。
	private String employeeCode;
	//氏。
	private String lastName;
	//名。
	private String firstName;
	//氏かな。
	private String lastKanaName;
	//名かな。
	private String firstKanaName;
	//性別。
	private int gender;
	//生年月日。
	private String birthDay;
	//部署コード。
	private String sectionCode;
	//入社日。
	private String hireDate;
	//パスワード。
	private String password;
	//Employeeのコンストラクタ。
	public Employee(){
	}

	// 従業員コードを返す。
	public String getEmployeeCode() {
		return employeeCode;
	}

	//指定したemployeeCodeをセットする。
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	//氏を返す。
	public String getLastName() {
		return lastName;
	}

	//指定したlastNameをセットする。
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	//名を返す。
	public String getFirstName() {
		return firstName;
	}

	//指定したfirstNameをセットする。
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	//氏かなを返す。
	public String getLastKanaName() {
		return lastKanaName;
	}

	//指定したlastKanaNameをセットする。
	public void setLastKanaName(String lastKanaName) {
		this.lastKanaName = lastKanaName;
	}

	//氏かなを返す。
	public String getFirstKanaName() {
		return firstKanaName;
	}

	//指定したfirstKanaNameをセットする。
	public void setFirstKanaName(String firstKanaName) {
		this.firstKanaName = firstKanaName;
	}

	//性別を返す。
	public int getGender() {
		return gender;
	}

	//指定したgenderをセットする。
	public void setGender(int gender) {
		this.gender = gender;
	}

	//文字列を受け取って対応する数値をgenderフィールドにセットする。
	public void setGender(String gender) {
		if(gender.equals("男性")) {
			this.gender = 0;
		} else if(gender.equals("女性")){
			this.gender = 1;
		}
	}

	//生年月日を返す。
	public String getBirthDay() {
		return birthDay;
	}

	//指定したbirthDayをセットする。
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	//部署コードを返す。
	public String getSectionCode() {
		return sectionCode;
	}

	//指定したsectionCodeをセットする。
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	//入社日を返す。
	public String getHireDate() {
		return hireDate;
	}

	//指定したhireDateをセットする。
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	//パスワードを返す。
	public String getPassword() {
		return password;
	}

	//指定したpasswordをセットする。
	public void setPassword(String password) {
		this.password = password;
	}
}
