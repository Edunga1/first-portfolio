import static org.bytedeco.javacpp.opencv_highgui.cvCreateCameraCapture;
import static org.bytedeco.javacpp.opencv_highgui.cvQueryFrame;
import static org.bytedeco.javacpp.opencv_highgui.cvReleaseCapture;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;

public abstract class CCTVCam extends Thread {

	/** 카메라 생성 수. 객체 구분자로 사용 */
	static private int count = 0;
	/** CAM 번호 */
	private int camNum;
	/** 현재 프레임 */
	private IplImage frame;
	/** Cam 객체 */
    private CvCapture capture;
    /** Cam 객체 구분자 */
    private int index;
	
	/**
	 * 기본 Cam 번호인 0으로 생성합니다
	 */
	public CCTVCam(){
		
		this(0);
	}
	
	/**
	 * @param camNum	하드웨어 Cam 번호
	 */
	public CCTVCam(int camNum) {
		
		this.camNum = camNum;
		// 구분자 결정 후 전역변수인 counter 증가
		this.index = CCTVCam.count++;
	}
	
	/**
	 * CAM 구분자를 반환합니다
	 * @return
	 */
	public int getCamIdx(){
		
		return index;
	}
	
	@Override
	public void run() {
		
		// CAM 캡쳐 객체 생성
		capture = cvCreateCameraCapture(camNum);
		
		// 객체 생성 여부 확인
		if(capture.isNull()){
			return;
		}
		
		// 화면 받아오는 부분
		while(true){
			//카메라로부터 한 프레임을 요청
            if((frame = cvQueryFrame(capture))==null) break;
            
            // 영상 획득 핸들러 호출
            receiveFrame(frame, index);
		}
		
        //자원 해제
        cvReleaseCapture(capture);
	}
	
	/**
	 * CAM으로부터 영상을 획득했을 때 발생합니다
	 * @param frame		영상
	 * @param camIdx	CAM 구분자
	 */
	abstract void receiveFrame(IplImage frame, int camIdx);
}
