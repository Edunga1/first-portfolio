import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvAbsDiff;
import static org.bytedeco.javacpp.opencv_core.cvCountNonZero;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvGetSpatialMoment;
import static org.bytedeco.javacpp.opencv_imgproc.cvMoments;
import static org.bytedeco.javacpp.opencv_imgproc.cvThreshold;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_imgproc.CvMoments;

public class ImageProc {

	/** 감지 비율 이 비율 이상 감지해야 추적함 */
//	static private final double TRACKING_CUTLINE = 0.003;
	/** 색상 차이값 기준 */
	static private final int SCALAR_DIFF = 40;

	/**
	 * 영상 감지
	 * 
	 * @param before
	 *            이전 영상
	 * @param cur
	 *            현재 영상
	 * @return 경계 값
	 */
	static public int[] detect(IplImage before, IplImage cur) {

		// 차이를 계산한 이진 영상 구함
		IplImage diff = null;
		IplImage bf = IplImage.create(cur.width(), cur.height(), IPL_DEPTH_8U,1);
		IplImage af = IplImage.create(cur.width(), cur.height(), IPL_DEPTH_8U,1);
		cvCvtColor(before, bf, CV_RGB2GRAY);
		cvCvtColor(cur, af, CV_RGB2GRAY);

		diff = IplImage.create(cur.width(), cur.height(), IPL_DEPTH_8U, 1);
		cvAbsDiff(af, bf, diff);
		cvThreshold(diff, diff, SCALAR_DIFF, 255, CV_THRESH_BINARY);

		// 감지한 픽셀 수 구함
		int numPixels = cvCountNonZero(diff);
		
		// 감지한 픽셀 수가 특정 개수 이상이면
		if(numPixels > 100){
			CvMoments moments = new CvMoments();
			cvMoments(diff, moments, 1);
			double m00 = cvGetSpatialMoment(moments, 0, 0);
			double m10 = cvGetSpatialMoment(moments, 1, 0);
			double m01 = cvGetSpatialMoment(moments, 0, 1);
			
			if (m00 != 0) { // create COG Point
				int xCenter = (int) Math.round(m10 / m00);
				int yCenter = (int) Math.round(m01 / m00);
				
				int []border = new int[4];
				border[0] = xCenter-100;
	            border[1] = yCenter-100;
	            if((xCenter-100)>0 && (yCenter-100)>0 && (xCenter+100)<diff.width() && (yCenter+100)<diff.height())
	            {
	               for(int y=yCenter-100; y<(yCenter+100); y++)
	               {
	                  for(int x=xCenter-100; x<(xCenter+100); x++)
	                  {
	                     int val = diff.getByteBuffer().get(getPixelIndex(diff,x,y)) & 0xff;
	                     if(val==255)
	                     {
	                        if(border[0]>x) border[0] = x;
	                        if(border[1]>y) border[1] = y;
	                        if(border[2]<x) border[2] = x;
	                        if(border[3]<y) border[3] = y;
	                     }
	                  }
	               }
	            }
	            
				return border;
			}
			
		}
		
		return null;
	}
	
	/**
	 * 이미지의 현재 인덱스를 구한다.
	 * @param img	이미지
	 * @param x		현재 x 좌표
	 * @param y		현재 y 좌표
	 * @return		현재 인덱스
	 */
	static private int getPixelIndex(IplImage img, int x, int y){
		
		return y*img.widthStep()+x*img.nChannels();
	}
}
