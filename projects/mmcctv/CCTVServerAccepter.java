import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 서버 소켓으로 생성되며, 클라이언트의 연결을 기다립니다<br />
 * 클라이언트가 연결하면 <b>clientConnected</b> 함수를 호출합니다
 * @author pArk
 *
 */
public abstract class CCTVServerAccepter extends Thread {
	
	/** 서버 소켓 */
	private ServerSocket server;
	
	/**
	 * @param server	서버 소켓
	 */
	public CCTVServerAccepter(ServerSocket server) {
		
		this.server = server;
	}
	
	@Override
	public void run() {
		
		// 클라이언트의 연결을 수락하고 핸들러 호출
		while(true){
			Socket client = acceptClient();				// 클라이언트 접속을 기다리고
			if(client != null) clientConnected(client);	// 연결되면 핸들러를 호출합니다
		}
	}
	
	/**
	 * 클라이언트가 연결되면 발생합니다.
	 * @param clientSocket	클라이언트 소켓
	 */
	abstract void clientConnected(Socket clientSocket);
	
	/**
	 * 클라이언트의 연결을 수락하고 소켓 반환합나다.
	 * @return	소켓을 반환하거나 없으면 null을 반환합니다.
	 */
	private Socket acceptClient(){
		
		// 수락 및 소켓 반환
		try {
			Socket sock = server.accept();
			return sock;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 오류 및 없는 경우
		return null;
	}
}
