import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.IplImage;


public class CCTVServer {
	
	/** ���� �⺻ ��Ʈ��ȣ */
	static public final int DEFAULT_PORT = 9007;
	
	/** ���� ���� */
	private ServerSocket serverSocket;
	/** Ŭ���̾�Ʈ ���� accepter */
	private CCTVServerAccepter accepter;
	/** Cam���� ���� ���� �迭 */
	private List<CamData> images;
	/** CAM ������ ��� */
	private ArrayList<CCTVCam> cams;
	
	public CCTVServer() {
		
		initialize();
	}
	
	/**
	 * ���� �迭 ��ȯ
	 * @return	���� �迭
	 */
	public List<CamData> getImages(){
		return images;
	}
	
	/**
	 * �� CAM�� �߰��մϴ�
	 * @param camNum	CAM ��ȣ
	 */
	public void addCam(int camNum){
		
		// CAM ������ ����
		CCTVCam cam = new CCTVCam(camNum) {
			
			@Override
			void receiveFrame(IplImage frame, int camIdx) {
				// CAM ������κ��� ���� ȹ���ϸ� �̹��� ����
				setCamImage(frame, camIdx);
			}
		};
		
		// CAM �迭�� CAM ���
		cams.add(cam);
		
		// ���� �迭�� CAM ���
		CamData d = new CamData(cam.getCamIdx());
		images.add(d);
		
		// CAM ����
		cam.start();
	}
	
	/**
	 * ���� ���α׷� �ʱ�ȭ
	 */
	private void initialize(){
		
		// ���� �迭 �ʱ�ȭ
		images = Collections.synchronizedList(new ArrayList<CamData>());
		
		// CAM �迭 �ʱ�ȭ
		cams = new ArrayList<CCTVCam>();
		
		try {
			// ���� ����
			serverSocket = new ServerSocket(DEFAULT_PORT);
			
			// accepter ����
			accepter = new CCTVServerAccepter(serverSocket) {
				@Override
				void clientConnected(Socket clientSocket) {
					// receiver ����
					CCTVReceiver recv = new CCTVReceiver(clientSocket) {
						@Override
						void receiveMessage(Message msg) {
							messageFromClient(msg);
						}
					};
					
					// sender ����
					CCTVServerSender send = new CCTVServerSender(CCTVServer.this, clientSocket);
					
					// receiver�� sender ����
					recv.start();
					send.start();
				}
			};
			
			// accepter ����
			accepter.start();
		} catch (IOException e) {
			// ���� ���� ����
			e.printStackTrace();
		}
	}
	
	/**
	 * CAM���κ��� ���� ������ �迭�� �����մϴ�
	 * @param frame		����
	 * @param camIdx	CAM ������
	 */
	private void setCamImage(IplImage frame, int camIdx){
		
		// CAM �����ڷ� �迭������ �ε��� ����
		int idx = -1;
		for(int i=0; i<images.size(); i++){
			if(camIdx == images.get(i).camIdx)
				idx = i;
		}
		
		// �迭�� ��ϵ��� ���� CAM�̸� ����
		if(idx == -1) return;
		
		// �̹��� ����
		CamData d = images.get(idx);
		d.setImg(frame);
		d.updateTime = System.currentTimeMillis();
	}
	
	/**
	 * Ŭ���̾�Ʈ�κ��� �޽����� ������ �� ��
	 * @param msg	�޽���
	 */
	private void messageFromClient(Message msg){
		// TODO
	}
}
