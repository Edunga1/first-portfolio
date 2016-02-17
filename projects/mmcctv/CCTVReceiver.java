import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 통신 대상으로 부터 데이터를 받는 클래스입니다.
 * @author pArk
 *
 */
public abstract class CCTVReceiver extends Thread {

	/** 통신 대상 소켓 */
	private Socket socket;
	/** 통신 대상 입력 스트림 */
	private ObjectInputStream is;
	
	/**
	 * 
	 * @param socket	소켓
	 */
	public CCTVReceiver(Socket socket) {
		
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		try {
			// 입력 스트림 얻음
			is = new ObjectInputStream(socket.getInputStream());
			
			// 메시지 얻음
			while(true){
				Object msgObj = is.readObject();	// 오브젝트 읽으면
				receiveMessage((Message)msgObj);	// 메시지 발생 핸들러 호출
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// 연결이 끊어짐
			e.printStackTrace();
		}
		
		// 소켓과 스트림 연결 해제
		try {
			is.close();
			socket.close();
		} catch (IOException ignored) { }
		catch (NullPointerException ignored) { }
		
	}
	
	/**
	 * 통신대상으로 부터 메시지를 받으면 이 함수가 호출됩니다.
	 * @param msg	메시지
	 */
	abstract void receiveMessage(Message msg);
}
