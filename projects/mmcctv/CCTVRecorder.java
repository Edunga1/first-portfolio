import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_highgui.CV_FOURCC;
import static org.bytedeco.javacpp.opencv_highgui.cvCreateVideoWriter;
import static org.bytedeco.javacpp.opencv_highgui.cvReleaseVideoWriter;
import static org.bytedeco.javacpp.opencv_highgui.cvWriteFrame;

import java.text.SimpleDateFormat;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui.CvVideoWriter;

class CCTVRecorder extends Thread {
	
	/** 녹화 영상 폴더 */
	static private final String VIDEO_DIRECTORY = "record/";
	/** 녹화 영상 확장자 */
	static private final String VIDEO_EXT = ".avi";
	/** 날짜 양식 */
	static private final String DATE_FORMAT = "yyyy.MM.dd.hh.mm.ss";
	/** 비감지 영상 커트라인 */
	static private final int PLAIN_IMAGE_CUTLINE = 100;
	
	/** 저장 객체. 생성될 때 프레임 정보가 필요함 */
	private CvVideoWriter rec = null;
	/** 비감지 카운트 */
	private int count = 0;
	
	/**
	 * 감지된 영상(isDetected가 true)이 들어오면 녹화를 시작합니다<br>
	 * 만약 이미 녹화중이라면 영상을 추가합니다<br>
	 * 감지된 영상이 아닌 일반 영상(isDetected가 false)이 연속으로 PLAIN_IMAGE_CUTLINE 이상 들어오면<br>
	 * 녹화를 마칩니다
	 * @param frame			영상
	 * @param isDetected	감지된 영상인지 여부
	 */
	public void record(IplImage frame, boolean isDetected){
		
		// 녹화 중이 아니고 감지된 영상이 들어오면 녹화 시작
		if(rec == null && isDetected)
			saveStart(frame);
		// 이미 녹화중이라면 영상 추가하고 감지된 영상인지 확인 후 카운트
		else if(rec != null && count <= PLAIN_IMAGE_CUTLINE){
			inputFrame(frame);
			if(!isDetected) count++;
			else count = 0;
		}
		// 녹화중이고 비감지 영상이 PLAIN_IMAGE_CUTLINE개 들어오면 녹화종료
		else if(rec != null){
			saveEnd();
		}
	}
	
	/**
	 * 녹화 시작
	 * 
	 * @param startFrame
	 *            시작 프레임
	 */
	private void saveStart(IplImage startFrame) {
		System.out.println("RECORD START");
		// 파일명
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String date = sdf.format(System.currentTimeMillis());
		String filename = VIDEO_DIRECTORY + date + VIDEO_EXT;
		
		// recorder 생성
		rec = cvCreateVideoWriter(filename,
				CV_FOURCC((byte) 'P', (byte) 'I', (byte) 'M', (byte) '1'), 30,
				cvGetSize(startFrame));
	}

	/**
	 * 장면 추가
	 * 
	 * @param frame
	 *            영상
	 */
	private void inputFrame(IplImage frame) {
		if (rec != null)
			cvWriteFrame(rec, frame);
	}

	/**
	 * 녹화 종료<br>
	 * 파일이 생성됩니다
	 */
	private void saveEnd() {
		System.out.println("RECORD END");
		if (rec != null) {
			cvReleaseVideoWriter(rec);
			rec = null;
		}
	}

}