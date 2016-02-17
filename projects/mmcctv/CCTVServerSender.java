import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class CCTVServerSender extends Thread {

	/** 서버 프로그램 */
	private CCTVServer server;
	/** 클라이언트 소켓 */
	private Socket clientSocket;
	/** 클라이언트의 입력 스트림 */
	private ObjectOutputStream os;
	/** 마지막 전송 시간 */
	private long latestUpdateTime = 0;
	
	public CCTVServerSender(CCTVServer server, Socket clientSocket) {
		
		this.server = server;
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		
		try{
			// 출력 스트림 얻음
			os = new ObjectOutputStream(clientSocket.getOutputStream());
			
			// 전송
			while(true){
				
				for(CamData d : server.getImages()){
					if(d.updateTime > latestUpdateTime){
						sendCamData(d);				// 영상 전송
						updateTime(d.updateTime);	// 시간 업데이트
					}
				}
			}
		}catch(IOException ignored){
			// 클라이언트와 연결 끊어짐
		}
		
		// 소켓과 스트림 연결 해제
		try {
			os.close();
			clientSocket.close();
		} catch (IOException ignored) { }
	}
	
	/**
	 * 영상 전송
	 * @param d	데이터
	 * @throws IOException 
	 */
	private void sendCamData(CamData d) throws IOException{
		
		Message msg = new Message(Message.TYPE_IMAGES, d);
		os.reset();				// reference reset. 안하면 처음 전송한 객체만 나감
		os.writeObject(msg);
	}
	
	/**
	 * 마지막 업로드 시간을 업데이트 함
	 * @param time	시간
	 */
	private void updateTime(long time){
		
		latestUpdateTime = time;
	}
}
