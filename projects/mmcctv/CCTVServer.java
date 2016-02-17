import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.IplImage;


public class CCTVServer {
	
	/** 서버 기본 포트번호 */
	static public final int DEFAULT_PORT = 9007;
	
	/** 서버 소켓 */
	private ServerSocket serverSocket;
	/** 클라이언트 연결 accepter */
	private CCTVServerAccepter accepter;
	/** Cam에서 받은 영상 배열 */
	private List<CamData> images;
	/** CAM 스레드 목록 */
	private ArrayList<CCTVCam> cams;
	
	public CCTVServer() {
		
		initialize();
	}
	
	/**
	 * 영상 배열 반환
	 * @return	영상 배열
	 */
	public List<CamData> getImages(){
		return images;
	}
	
	/**
	 * 새 CAM을 추가합니다
	 * @param camNum	CAM 번호
	 */
	public void addCam(int camNum){
		
		// CAM 스레드 생성
		CCTVCam cam = new CCTVCam(camNum) {
			
			@Override
			void receiveFrame(IplImage frame, int camIdx) {
				// CAM 스레드로부터 영상 획득하면 이미지 설정
				setCamImage(frame, camIdx);
			}
		};
		
		// CAM 배열에 CAM 등록
		cams.add(cam);
		
		// 영상 배열에 CAM 등록
		CamData d = new CamData(cam.getCamIdx());
		images.add(d);
		
		// CAM 시작
		cam.start();
	}
	
	/**
	 * 서버 프로그램 초기화
	 */
	private void initialize(){
		
		// 영상 배열 초기화
		images = Collections.synchronizedList(new ArrayList<CamData>());
		
		// CAM 배열 초기화
		cams = new ArrayList<CCTVCam>();
		
		try {
			// 소켓 생성
			serverSocket = new ServerSocket(DEFAULT_PORT);
			
			// accepter 생성
			accepter = new CCTVServerAccepter(serverSocket) {
				@Override
				void clientConnected(Socket clientSocket) {
					// receiver 생성
					CCTVReceiver recv = new CCTVReceiver(clientSocket) {
						@Override
						void receiveMessage(Message msg) {
							messageFromClient(msg);
						}
					};
					
					// sender 생성
					CCTVServerSender send = new CCTVServerSender(CCTVServer.this, clientSocket);
					
					// receiver와 sender 시작
					recv.start();
					send.start();
				}
			};
			
			// accepter 시작
			accepter.start();
		} catch (IOException e) {
			// 소켓 생성 실패
			e.printStackTrace();
		}
	}
	
	/**
	 * CAM으로부터 얻은 영상을 배열에 저장합니다
	 * @param frame		영상
	 * @param camIdx	CAM 구분자
	 */
	private void setCamImage(IplImage frame, int camIdx){
		
		// CAM 구분자로 배열에서의 인덱스 구함
		int idx = -1;
		for(int i=0; i<images.size(); i++){
			if(camIdx == images.get(i).camIdx)
				idx = i;
		}
		
		// 배열에 등록되지 않은 CAM이면 종료
		if(idx == -1) return;
		
		// 이미지 저장
		CamData d = images.get(idx);
		d.setImg(frame);
		d.updateTime = System.currentTimeMillis();
	}
	
	/**
	 * 클라이언트로부터 메시지를 받으면 할 일
	 * @param msg	메시지
	 */
	private void messageFromClient(Message msg){
		// TODO
	}
}
