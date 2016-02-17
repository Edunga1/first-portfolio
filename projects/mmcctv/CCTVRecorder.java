import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_highgui.CV_FOURCC;
import static org.bytedeco.javacpp.opencv_highgui.cvCreateVideoWriter;
import static org.bytedeco.javacpp.opencv_highgui.cvReleaseVideoWriter;
import static org.bytedeco.javacpp.opencv_highgui.cvWriteFrame;

import java.text.SimpleDateFormat;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui.CvVideoWriter;

class CCTVRecorder extends Thread {
	
	/** ��ȭ ���� ���� */
	static private final String VIDEO_DIRECTORY = "record/";
	/** ��ȭ ���� Ȯ���� */
	static private final String VIDEO_EXT = ".avi";
	/** ��¥ ��� */
	static private final String DATE_FORMAT = "yyyy.MM.dd.hh.mm.ss";
	/** ���� ���� ĿƮ���� */
	static private final int PLAIN_IMAGE_CUTLINE = 100;
	
	/** ���� ��ü. ������ �� ������ ������ �ʿ��� */
	private CvVideoWriter rec = null;
	/** ���� ī��Ʈ */
	private int count = 0;
	
	/**
	 * ������ ����(isDetected�� true)�� ������ ��ȭ�� �����մϴ�<br>
	 * ���� �̹� ��ȭ���̶�� ������ �߰��մϴ�<br>
	 * ������ ������ �ƴ� �Ϲ� ����(isDetected�� false)�� �������� PLAIN_IMAGE_CUTLINE �̻� ������<br>
	 * ��ȭ�� ��Ĩ�ϴ�
	 * @param frame			����
	 * @param isDetected	������ �������� ����
	 */
	public void record(IplImage frame, boolean isDetected){
		
		// ��ȭ ���� �ƴϰ� ������ ������ ������ ��ȭ ����
		if(rec == null && isDetected)
			saveStart(frame);
		// �̹� ��ȭ���̶�� ���� �߰��ϰ� ������ �������� Ȯ�� �� ī��Ʈ
		else if(rec != null && count <= PLAIN_IMAGE_CUTLINE){
			inputFrame(frame);
			if(!isDetected) count++;
			else count = 0;
		}
		// ��ȭ���̰� ���� ������ PLAIN_IMAGE_CUTLINE�� ������ ��ȭ����
		else if(rec != null){
			saveEnd();
		}
	}
	
	/**
	 * ��ȭ ����
	 * 
	 * @param startFrame
	 *            ���� ������
	 */
	private void saveStart(IplImage startFrame) {
		System.out.println("RECORD START");
		// ���ϸ�
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String date = sdf.format(System.currentTimeMillis());
		String filename = VIDEO_DIRECTORY + date + VIDEO_EXT;
		
		// recorder ����
		rec = cvCreateVideoWriter(filename,
				CV_FOURCC((byte) 'P', (byte) 'I', (byte) 'M', (byte) '1'), 30,
				cvGetSize(startFrame));
	}

	/**
	 * ��� �߰�
	 * 
	 * @param frame
	 *            ����
	 */
	private void inputFrame(IplImage frame) {
		if (rec != null)
			cvWriteFrame(rec, frame);
	}

	/**
	 * ��ȭ ����<br>
	 * ������ �����˴ϴ�
	 */
	private void saveEnd() {
		System.out.println("RECORD END");
		if (rec != null) {
			cvReleaseVideoWriter(rec);
			rec = null;
		}
	}

}