package com.sbs.textboard;

// 청원경찰 -> 창구직원 -> 과장님 -> 창고관리자
// App -> 컨트롤러 -> 서비스 -> Dao
// 어떤 명령이 들어왔는지 확인(간단하게) -> 명령에 대한 정보가 들어왔는지 확인(자세하게) -> 확인해서 통과, 거부 -> 데이터 저장
public class Main {
	public static void main(String[] args) {
		new App();
	}
}
