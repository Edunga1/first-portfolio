import java.io.Serializable;
import java.nio.ByteBuffer;

import org.bytedeco.javacpp.opencv_core.IplImage;


public class CamData implements Serializable {
	private static final long serialVersionUID = 7875155242123462330L;
	
	/** Cam 구분자 */
	int camIdx;
	/** Cam에서 얻은 영상 */
	private byte[] imgByteArray = null;
	private int width;
	private int height;
	private int depth;
	private int channels;
	/** 영상을 얻은 시간 */
	long updateTime = -1;
	
	public CamData(int camIdx) {
		
		this.camIdx = camIdx;
	}
	
	/**
	 * 영상을 반환합니다
	 * @return	영상
	 */
	public IplImage getImg(){
		
		IplImage img = IplImage.create(width, height, depth, channels);
		img.getByteBuffer().put(imgByteArray);
		return img;
	}
	
	/**
	 * 영상을 설정합니다
	 * @param img	영상
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
