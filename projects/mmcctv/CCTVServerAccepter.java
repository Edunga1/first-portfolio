import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ���� �������� �����Ǹ�, Ŭ���̾�Ʈ�� ������ ��ٸ��ϴ�<br />
 * Ŭ���̾�Ʈ�� �����ϸ� <b>clientConnected</b> �Լ��� ȣ���մϴ�
 * @author pArk
 *
 */
public abstract class CCTVServerAccepter extends Thread {
	
	/** ���� ���� */
	private ServerSocket server;
	
	/**
	 * @param server	���� ����
	 */
	public CCTVServerAccepter(ServerSocket server) {
		
		this.server = server;
	}
	
	@Override
	public void run() {
		
		// Ŭ���̾�Ʈ�� ������ �����ϰ� �ڵ鷯 ȣ��
		while(true){
			Socket client = acceptClient();				// Ŭ���̾�Ʈ ������ ��ٸ���
			if(client != null) clientConnected(client);	// ����Ǹ� �ڵ鷯�� ȣ���մϴ�
		}
	}
	
	/**
	 * Ŭ���̾�Ʈ�� ����Ǹ� �߻��մϴ�.
	 * @param clientSocket	Ŭ���̾�Ʈ ����
	 */
	abstract void clientConnected(Socket clientSocket);
	
	/**
	 * Ŭ���̾�Ʈ�� ������ �����ϰ� ���� ��ȯ�ճ���.
	 * @return	������ ��ȯ�ϰų� ������ null�� ��ȯ�մϴ�.
	 */
	private Socket acceptClient(){
		
		// ���� �� ���� ��ȯ
		try {
			Socket sock = server.accept();
			return sock;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// ���� �� ���� ���
		return null;
	}
}
