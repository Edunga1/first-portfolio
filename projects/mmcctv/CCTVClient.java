import java.net.Socket;
import java.util.ArrayList;


public abstract class CCTVClient {

	/** ���� ��Ʈ��ȣ */
	public static final int SERVER_PORT = 9007;
	
	/** ���� ���� */
	private Socket serverSocket;
	/** CAM �����͵� */
	private ArrayList<CamData> camDatas = new ArrayList<CamData>();
	
	public CCTVClient() {
	}
	
	/**
	 * ������ ����
	 * @param	���� ������
	 * @return	���� ���� ����
	 */
	public boolean connect(String ip){
		
		// ���� �õ�
		try {
			// ����
			serverSocket = new Socket(ip, SERVER_PORT);
			
			// receiver ����
			CCTVReceiver recv = new CCTVReceiver(serverSocket) {
				@Override
				void receiveMessage(Message msg) {
					messageFromServer(msg);
				}
			};
			
			// receiver ����
			recv.start();
		} catch (Exception ignored) {
			// ���� ���� ���н�
			return false;
		}
		
		// ���� ����
		return true;
	}
	
	/**
	 * �����κ��� �޽��� ������ �� ��
	 * @param msg	�޽���
	 */
	private void messageFromServer(Message msg){
		
		switch(msg.type){
		case Message.TYPE_IMAGES:
			CamData d = (CamData) msg.object;	// CamData�� ��ȯ
			receiveCamData(d);					// CamData ó��
			break;
		}
	}
	
	/**
	 * �� �������� ����
	 * @param d	���ο� ���� ������
	 */
	private void receiveCamData(CamData d){
		
		// �̹� �迭�� �����ϴ� CAM�̸� ������Ʈ
		boolean isExist = false;
		for(int i=0; i<camDatas.size(); i++){
			if(camDatas.get(i).camIdx == d.camIdx){
				isExist = true;
				camDatas.set(i, d);
			}
		}
		
		// ���ο� CAM�̶�� �迭�� �߰�
		if(!isExist){
			camDatas.add(d);
			receiveNewCam(camDatas);
		}
		
		// �ڵ鷯 ȣ��
		receiveCamImage(d);
	}
	
	/**
	 * ���ο� CAM�� �����͸� ����� ��� ȣ���մϴ�
	 * @param datas	��� CAM ������
	 */
	public abstract void receiveNewCam(ArrayList<CamData> datas);
	
	/**
	 * ���� ������ ó���� �������� �� �Լ��� ȣ���մϴ�
	 * @param d	CAM ������
	 */
	public abstract void receiveCamImage(CamData d);
}
