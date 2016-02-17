import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;


public class CamImageLabel extends JLabel {
	private static final long serialVersionUID = 2262973282932495311L;
	
	/** CAM ���� �ʺ� */
	public static final int CAM_WIDTH = 320;
	/** CAM ���� ���� */
	public static final int CAM_HEIGHT = 240;
	/** ���� ���� */
	private IplImage before = null;
	/** CAM ��ȣ */
	private int camNum;
	/** �θ� ��ü */
	private CCTVUI parent;
	/** ��ȭ ��ü */
	private CCTVRecorder recorder = new CCTVRecorder();
	
	/** CAM ���̺��� ���ؽ�Ʈ �޴� */
	CamLabelPopupMenu popup;
	/** ���� ���� */
	private boolean isTracking = false;
	/** �˶� ���� */
	private boolean isAlarm = false;
	/** ��ȭ ���� */
	private boolean isRecord = false;
	
	public CamImageLabel(CCTVUI parent, int camNum) {
		super("");
		this.parent = parent;
		this.camNum = camNum;
		initComponents();
		initPopupMenu();
	}
	
	private void initComponents() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				popupMenu(e);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				popupMenu(e);
			}
		});
	}
	
	private void initPopupMenu(){
		
		popup = new CamLabelPopupMenu() {
			private static final long serialVersionUID = 2377412433644217366L;

			@Override
			public void clickedTracking() {
				if(isTracking) isTracking = false;
				else isTracking = true;
			}
			
			@Override
			public void clickedAlarm() {
				if(isAlarm) isAlarm = false;
				else isAlarm = true;
			}
			
			@Override
			public void clickedRecord() {
				if(isRecord) isRecord = false;
				else isRecord = true;
			}
			
			@Override
			public void clickedRemove() {
				parent.removeCamLabel(CamImageLabel.this);
			}
		};
	}
	
	/**
	 * �� CAM ���̺��� CAM ��ȣ�� �´��� ���θ� ��ȯ�մϴ�
	 * @param camNum	���� CAM ��ȣ
	 * @return			�´��� ����
	 */
	public boolean isSameCam(int camNum){
		
		return this.camNum == camNum;
	}
	
	/**
	 * CAM �̹����� �����մϴ�
	 * @param img	CAM ����
	 */
	public void setCamImage(IplImage img){
		
		// ���� ����
		IplImage display = null;
		
		// ���� �� �������� ����
		int []border = detect(img);
		boolean isDetected = border == null ? false : true;
		
		// �˶�
		if(isAlarm && isDetected){
			alarm();
		}
		
		// ����
		if(isTracking && isDetected){
			display = tracking(img, border);
		}
		
		// ��ȭ
		if(isRecord){
			recorder.record(img, isDetected);
		}
		
		// ���
		BufferedImage displayImg = null;
		if(display != null)
			displayImg = resizeImage(display.getBufferedImage());
		else
			displayImg = resizeImage(img.getBufferedImage());
		setIcon(new ImageIcon(displayImg));
		
		// ���� �̹��� ����
		before = img.clone();
	}
	
	/**
	 * �˾� �޴��� ǥ���մϴ�
	 * @param e	���콺 �̺�Ʈ
	 */
	private void popupMenu(MouseEvent e){
		
		// �˾� �޴� ȣ���� �ƴ� ���
		if(!e.isPopupTrigger())
			return;
		
		// �˾��޴� ǥ��
		popup.show(e.getComponent(), e.getX(), e.getY());
	}
	
	/**
	 * ����
	 * @param cur	���� ����
	 * @return		���� ���� int[0]:top, int[1]:bottom, int[2]:left, int[3]:right<br>
	 * 				������ null ��ȯ
	 */
	private int[] detect(IplImage cur){
		
		// ���� ���� ������ ����
		if(before == null) return null;
		
		// ����
		int[]border = ImageProc.detect(before, cur);
		return border;
	}
	
	/**
	 * �˶�
	 */
	private void alarm(){

		Alarm arm = new Alarm();
		arm.start();
	}
	
	/**
	 * ���� ����. ������ �κ� ���� �簢���� �׸��ϴ�
	 * @param cur	���� ����
	 * @param border	��輱
	 * @return		���� ����� ����
	 */
	private IplImage tracking(IplImage cur, int[] border){
		
		IplImage processed = cur.clone();
		if(border != null && border.length == 4){
			opencv_core.cvRectangle(processed,
					opencv_core.cvPoint(border[0], border[2]),
					opencv_core.cvPoint(border[1], border[3]),
					CvScalar.RED,
					3,
					8,
					0);
		}
		return processed;
	}
	
	/**
	 * �̹��� ũ�⸦ �����մϴ�
	 * @param img	���� �̹���
	 * @return		��������� �̹���
	 */
	private BufferedImage resizeImage(BufferedImage img){

		Image img_r = img.getScaledInstance(CAM_WIDTH, CAM_HEIGHT, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(CAM_WIDTH, CAM_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		resized.getGraphics().drawImage(img_r, 0, 0, null);
		return resized;
	}
}
