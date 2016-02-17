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
	
	/** CAM 영상 너비 */
	public static final int CAM_WIDTH = 320;
	/** CAM 영상 높이 */
	public static final int CAM_HEIGHT = 240;
	/** 이전 영상 */
	private IplImage before = null;
	/** CAM 번호 */
	private int camNum;
	/** 부모 객체 */
	private CCTVUI parent;
	/** 녹화 객체 */
	private CCTVRecorder recorder = new CCTVRecorder();
	
	/** CAM 레이블의 컨텍스트 메뉴 */
	CamLabelPopupMenu popup;
	/** 추적 여부 */
	private boolean isTracking = false;
	/** 알람 여부 */
	private boolean isAlarm = false;
	/** 녹화 여부 */
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
	 * 이 CAM 레이블의 CAM 번호가 맞는지 여부를 반환합니다
	 * @param camNum	비교할 CAM 번호
	 * @return			맞는지 여부
	 */
	public boolean isSameCam(int camNum){
		
		return this.camNum == camNum;
	}
	
	/**
	 * CAM 이미지를 설정합니다
	 * @param img	CAM 영상
	 */
	public void setCamImage(IplImage img){
		
		// 편집 영상
		IplImage display = null;
		
		// 감지 및 감지여부 저장
		int []border = detect(img);
		boolean isDetected = border == null ? false : true;
		
		// 알람
		if(isAlarm && isDetected){
			alarm();
		}
		
		// 추적
		if(isTracking && isDetected){
			display = tracking(img, border);
		}
		
		// 녹화
		if(isRecord){
			recorder.record(img, isDetected);
		}
		
		// 출력
		BufferedImage displayImg = null;
		if(display != null)
			displayImg = resizeImage(display.getBufferedImage());
		else
			displayImg = resizeImage(img.getBufferedImage());
		setIcon(new ImageIcon(displayImg));
		
		// 이전 이미지 저장
		before = img.clone();
	}
	
	/**
	 * 팝업 메뉴를 표시합니다
	 * @param e	마우스 이벤트
	 */
	private void popupMenu(MouseEvent e){
		
		// 팝업 메뉴 호출이 아닌 경우
		if(!e.isPopupTrigger())
			return;
		
		// 팝업메뉴 표시
		popup.show(e.getComponent(), e.getX(), e.getY());
	}
	
	/**
	 * 감지
	 * @param cur	현재 영상
	 * @return		감지 범위 int[0]:top, int[1]:bottom, int[2]:left, int[3]:right<br>
	 * 				없으면 null 반환
	 */
	private int[] detect(IplImage cur){
		
		// 이전 영상 없으면 종료
		if(before == null) return null;
		
		// 감지
		int[]border = ImageProc.detect(before, cur);
		return border;
	}
	
	/**
	 * 알람
	 */
	private void alarm(){

		Alarm arm = new Alarm();
		arm.start();
	}
	
	/**
	 * 영상 추적. 감지한 부분 위에 사각형을 그립니다
	 * @param cur	현재 영상
	 * @param border	경계선
	 * @return		추적 적용된 영상
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
	 * 이미지 크기를 변경합니다
	 * @param img	원본 이미지
	 * @return		리사이즈된 이미지
	 */
	private BufferedImage resizeImage(BufferedImage img){

		Image img_r = img.getScaledInstance(CAM_WIDTH, CAM_HEIGHT, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(CAM_WIDTH, CAM_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		resized.getGraphics().drawImage(img_r, 0, 0, null);
		return resized;
	}
}
