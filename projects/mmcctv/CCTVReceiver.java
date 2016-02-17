import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * ��� ������� ���� �����͸� �޴� Ŭ�����Դϴ�.
 * @author pArk
 *
 */
public abstract class CCTVReceiver extends Thread {

	/** ��� ��� ���� */
	private Socket socket;
	/** ��� ��� �Է� ��Ʈ�� */
	private ObjectInputStream is;
	
	/**
	 * 
	 * @param socket	����
	 */
	public CCTVReceiver(Socket socket) {
		
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		try {
			// �Է� ��Ʈ�� ����
			is = new ObjectInputStream(socket.getInputStream());
			
			// �޽��� ����
			while(true){
				Object msgObj = is.readObject();	// ������Ʈ ������
				receiveMessage((Message)msgObj);	// �޽��� �߻� �ڵ鷯 ȣ��
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// ������ ������
			e.printStackTrace();
		}
		
		// ���ϰ� ��Ʈ�� ���� ����
		try {
			is.close();
			socket.close();
		} catch (IOException ignored) { }
		catch (NullPointerException ignored) { }
		
	}
	
	/**
	 * ��Ŵ������ ���� �޽����� ������ �� �Լ��� ȣ��˴ϴ�.
	 * @param msg	�޽���
	 */
	abstract void receiveMessage(Message msg);
}
