import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class CCTVServerSender extends Thread {

	/** ���� ���α׷� */
	private CCTVServer server;
	/** Ŭ���̾�Ʈ ���� */
	private Socket clientSocket;
	/** Ŭ���̾�Ʈ�� �Է� ��Ʈ�� */
	private ObjectOutputStream os;
	/** ������ ���� �ð� */
	private long latestUpdateTime = 0;
	
	public CCTVServerSender(CCTVServer server, Socket clientSocket) {
		
		this.server = server;
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		
		try{
			// ��� ��Ʈ�� ����
			os = new ObjectOutputStream(clientSocket.getOutputStream());
			
			// ����
			while(true){
				
				for(CamData d : server.getImages()){
					if(d.updateTime > latestUpdateTime){
						sendCamData(d);				// ���� ����
						updateTime(d.updateTime);	// �ð� ������Ʈ
					}
				}
			}
		}catch(IOException ignored){
			// Ŭ���̾�Ʈ�� ���� ������
		}
		
		// ���ϰ� ��Ʈ�� ���� ����
		try {
			os.close();
			clientSocket.close();
		} catch (IOException ignored) { }
	}
	
	/**
	 * ���� ����
	 * @param d	������
	 * @throws IOException 
	 */
	private void sendCamData(CamData d) throws IOException{
		
		Message msg = new Message(Message.TYPE_IMAGES, d);
		os.reset();				// reference reset. ���ϸ� ó�� ������ ��ü�� ����
		os.writeObject(msg);
	}
	
	/**
	 * ������ ���ε� �ð��� ������Ʈ ��
	 * @param time	�ð�
	 */
	private void updateTime(long time){
		
		latestUpdateTime = time;
	}
}
