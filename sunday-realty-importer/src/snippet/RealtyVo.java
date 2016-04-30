package snippet;

import java.util.Date;

public class RealtyVo {
	//	시군구
	//	본번
	//	부번
	//	단지명
	//	전용면적(m2)
	//	계약일
	//	거래금액(만원)
	//	층
	//	건축년도
	//	도로명주소
	String Sigungu;	
	int MainNo		;
	int SubNo		;
	String DanjiName	;
	int RealSize	;
	Date ContractDate;
	int ContractMoney;
	int Story;
	int ConstructYear;
	String Address	;
	public String getSigungu() {
		return Sigungu;
	}
	public void setSigungu(String sigungu) {
		Sigungu = sigungu;
	}
	public int getMainNo() {
		return MainNo;
	}
	public void setMainNo(int i) {
		MainNo = i;
	}
	public int getSubNo() {
		return SubNo;
	}
	public void setSubNo(int subNo) {
		SubNo = subNo;
	}
	public String getDanjiName() {
		return DanjiName;
	}
	public void setDanjiName(String danjiName) {
		DanjiName = danjiName;
	}
	public int getRealSize() {
		return RealSize;
	}
	public void setRealSize(int realSize) {
		RealSize = realSize;
	}
	public Date getContractDate() {
		return ContractDate;
	}
	public void setContractDate(Date contractDate) {
		ContractDate = contractDate;
	}
	public int getContractMoney() {
		return ContractMoney;
	}
	public void setContractMoney(int contractMoney) {
		ContractMoney = contractMoney;
	}
	public int getStory() {
		return Story;
	}
	public void setStory(int story) {
		Story = story;
	}
	public int getConstructYear() {
		return ConstructYear;
	}
	public void setConstructYear(int constructYear) {
		ConstructYear = constructYear;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}



}
