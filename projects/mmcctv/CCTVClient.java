import java.net.Socket;
import java.util.ArrayList;


public abstract class CCTVClient {

	/** 서버 포트번호 */
	public static final int SERVER_PORT = 9007;
	
	/** 서버 소켓 */
	private Socket serverSocket;
	/** CAM 데이터들 */
	private ArrayList<CamData> camDatas = new ArrayList<CamData>();
	
	public CCTVClient() {
	}
	
	/**
	 * 서버에 접속
	 * @param	서버 아이피
	 * @return	접속 성공 여부
	 */
	public boolean connect(String ip){
		
		// 접속 시도
		try {
			// 소켓
			serverSocket = new Socket(ip, SERVER_PORT);
			
			// receiver 생성
			CCTVReceiver recv = new CCTVReceiver(serverSocket) {
				@Override
				void receiveMessage(Message msg) {
					messageFromServer(msg);
				}
			};
			
			// receiver 시작
			recv.start();
		} catch (Exception ignored) {
			// 서버 접속 실패시
			return false;
		}
		
		// 접속 성공
		return true;
	}
	
	/**
	 * 서버로부터 메시지 받으면 할 일
	 * @param msg	메시지
	 */
	private void messageFromServer(Message msg){
		
		switch(msg.type){
		case Message.TYPE_IMAGES:
			CamData d = (CamData) msg.object;	// CamData로 변환
			receiveCamData(d);					// CamData 처리
			break;
		}
	}
	
	/**
	 * 새 영상데이터 받음
	 * @param d	새로운 영상 데이터
	 */
	private void receiveCamData(CamData d){
		
		// 이미 배열에 존재하는 CAM이면 업데이트
		boolean isExist = false;
		for(int i=0; i<camDatas.size(); i++){
			if(camDatas.get(i).camIdx == d.camIdx){
				isExist = true;
				camDatas.set(i, d);
			}
		}
		
		// 새로운 CAM이라면 배열에 추가
		if(!isExist){
			camDatas.add(d);
			receiveNewCam(camDatas);
		}
		
		// 핸들러 호출
		receiveCamImage(d);
	}
	
	/**
	 * 새로운 CAM의 데이터를 얻었을 경우 호출합니다
	 * @param datas	모든 CAM 데이터
	 */
	public abstract void receiveNewCam(ArrayList<CamData> datas);
	
	/**
	 * 얻은 영상의 처리가 끝났으면 이 함수를 호출합니다
	 * @param d	CAM 데이터
	 */
	public abstract void receiveCamImage(CamData d);
}
