import static org.bytedeco.javacpp.opencv_highgui.cvCreateCameraCapture;
import static org.bytedeco.javacpp.opencv_highgui.cvQueryFrame;
import static org.bytedeco.javacpp.opencv_highgui.cvReleaseCapture;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;

public abstract class CCTVCam extends Thread {

	/** ī�޶� ���� ��. ��ü �����ڷ� ��� */
	static private int count = 0;
	/** CAM ��ȣ */
	private int camNum;
	/** ���� ������ */
	private IplImage frame;
	/** Cam ��ü */
    private CvCapture capture;
    /** Cam ��ü ������ */
    private int index;
	
	/**
	 * �⺻ Cam ��ȣ�� 0���� �����մϴ�
	 */
	public CCTVCam(){
		
		this(0);
	}
	
	/**
	 * @param camNum	�ϵ���� Cam ��ȣ
	 */
	public CCTVCam(int camNum) {
		
		this.camNum = camNum;
		// ������ ���� �� ���������� counter ����
		this.index = CCTVCam.count++;
	}
	
	/**
	 * CAM �����ڸ� ��ȯ�մϴ�
	 * @return
	 */
	public int getCamIdx(){
		
		return index;
	}
	
	@Override
	public void run() {
		
		// CAM ĸ�� ��ü ����
		capture = cvCreateCameraCapture(camNum);
		
		// ��ü ���� ���� Ȯ��
		if(capture.isNull()){
			return;
		}
		
		// ȭ�� �޾ƿ��� �κ�
		while(true){
			//ī�޶�κ��� �� �������� ��û
            if((frame = cvQueryFrame(capture))==null) break;
            
            // ���� ȹ�� �ڵ鷯 ȣ��
            receiveFrame(frame, index);
		}
		
        //�ڿ� ����
        cvReleaseCapture(capture);
	}
	
	/**
	 * CAM���κ��� ������ ȹ������ �� �߻��մϴ�
	 * @param frame		����
	 * @param camIdx	CAM ������
	 */
	abstract void receiveFrame(IplImage frame, int camIdx);
}
