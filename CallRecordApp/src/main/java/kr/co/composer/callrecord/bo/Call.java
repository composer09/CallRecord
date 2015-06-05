package kr.co.composer.callrecord.bo;

public class Call {
	
	private int rowId;
	
	private int callerID;
	
	// 전화한 사람이름
	private String callerName;
	
	// 착.발신여부 이미지
	private String sendReceive;
	
	// 전화 시작시간
	private String startTime;
	
	// 전화 종료시간
	private String endTime;
	
	// 총 전화한 시간
	private String callTime;
	
	// 전화통화한 번호
	private String phoneNumber;
	
	// 발신시간
	private String outgoingCallDate;
	
	// 녹음파일 이름
	private String fileName;
	
	public int getRowId() {
		return rowId;
	}
	
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	
	public int getCallerID() {
		return callerID;
	}

	public void setCallerID(int callerID){
		this.callerID = callerID;
	}

	public String getFileName(){
		return fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getCallerName() {
		return callerName;
	}

	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}

	public String getsendReceive() {
		return sendReceive;
	}

	public void setSendReceive(String sendReceive) {
		this.sendReceive = sendReceive;
	}

	public String getStartTime(){
		return startTime;
	}
	
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	
	public String getEndTime(){
		return endTime;
	}
	
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	
	public String getCallTime() {
		return callTime;
	}
	
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOutgoingCallDate() {
		return outgoingCallDate;
	}

	public void setOutgoingCallDate(String outgoingCallDate) {
		this.outgoingCallDate = outgoingCallDate;
	}


	
}
