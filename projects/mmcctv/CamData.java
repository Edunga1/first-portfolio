import java.io.Serializable;
import java.nio.ByteBuffer;

import org.bytedeco.javacpp.opencv_core.IplImage;


public class CamData implements Serializable {
	private static final long serialVersionUID = 7875155242123462330L;
	
	/** Cam ������ */
	int camIdx;
	/** Cam���� ���� ���� */
	private byte[] imgByteArray = null;
	private int width;
	private int height;
	private int depth;
	private int channels;
	/** ������ ���� �ð� */
	long updateTime = -1;
	
	public CamData(int camIdx) {
		
		this.camIdx = camIdx;
	}
	
	/**
	 * ������ ��ȯ�մϴ�
	 * @return	����
	 */
	public IplImage getImg(){
		
		IplImage img = IplImage.create(width, height, depth, channels);
		img.getByteBuffer().put(imgByteArray);
		return img;
	}
	
	/**
	 * ������ �����մϴ�
	 * @param img	����
	 */
	public void setImg(IplImage img){
		
		ByteBuffer bb = img.getByteBuffer();
		imgByteArray = new byte[bb.remaining()];
		bb.get(imgByteArray);
		width = img.width();
		height = img.height();
		depth = img.depth();
		channels = img.nChannels();
	}
}
