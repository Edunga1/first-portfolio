import static org.bytedeco.javacpp.opencv_core.CV_FILLED;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvGet2D;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_core.cvRectangle;
import static org.bytedeco.javacpp.opencv_core.cvScalar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;

public class Photoshop {
	
	private static final int GRAPH_ADDITIONAL_WIDTH = 1;// 히스토그램 막대 폭
	private static final int GRAPH_AREA_HEIGHT = 100;	// 히스토그램 그래프 높이
	private static final int GRAYSCALE_AREA_HEIGHT = 10;// 히스토그램 grayscale 높이
	private static final int TOTAL_AREA_HEIGHT = 115;	// 히스토그램 이미지 높이
	private static final int[][] MASK_BLUR_3X3 = {{1,1,1},
												{1,1,1},
												{1,1,1}};	// 블러링 마스크 3x3
	private static final int[][] MASK_BLUR_5X5 = {{1,1,1,1,1},
												{1,1,1,1,1},
												{1,1,1,1,1},
												{1,1,1,1,1},
												{1,1,1,1,1}};// 블러링 마스크 5x5
	private static final int[][] MASK_SHARPEN = {{0,-1,0},
												{-1,5,-1},
												{0,-1,0}};	// 샤프닝 마스크
	private static final int[][] MASK_SMOOTHING = {{1,2,1},
												{2,4,2},
												{1,2,1}};	// 가우시안 smoothing 마스크
	private static final int[][] MASK_SOBEL = {{0,2,2},
											{-2,0,2},
											{-2,-2,0}};		// 소벨 마스크 가로+세로
	private static final int[][] MASK_LAPLACIAN = {{0,-1,0},
												{-1,4,-1},
												{0,-1,0}};	// 라플라시안 4방향 마스크
	private static final int[][] MASK_EROSION = {{255,255,255},
												{255,255,255},
												{255,255,255}};	// 모폴로지 침식 연산 마스크
	private static final int[][] MASK_DILATION = {{0,0,0},
												{0,0,0},
												{0,0,0}};	// 모폴로지 팽창 연산 마스크
	private IplImage image;			// 작업 이미지
	private IplImage previousImage;	// 한 단계 전의 이미지
	
	/**
	 * 작업 이미지를 반환한다.
	 * @return	작업 이미지, 없으면 null
	 */
	public BufferedImage getBufferedImage(){
		
		if(image == null)
			return null;
		
		return image.getBufferedImage();
	}
	
	/**
	 * 작업 이미지의 히스토그램 이미지 반환
	 * @return	히스토그램 이미지
	 */
	public BufferedImage getHistogramImage(){
		
		// 예외처리
		if(image == null)
			return null;
		
		// 히스토그램 명암 배열
		int[] hData = getHistogramData();
		
		// 최대 명암 값
		int max = getMaximum(hData);
		
		// 이미지화
		IplImage hImg = IplImage.create(256*GRAPH_ADDITIONAL_WIDTH, TOTAL_AREA_HEIGHT, IPL_DEPTH_8U, 1);
		cvRectangle(hImg,
				cvPoint(0,0),
				cvPoint(hImg.width(), hImg.height()),
				CvScalar.WHITE,
				CV_FILLED, 8, 0);
		for(int i=0; i<256; i++){
			
			// 막대 좌표
			int x = i*GRAPH_ADDITIONAL_WIDTH;
			int y = TOTAL_AREA_HEIGHT-GRAYSCALE_AREA_HEIGHT;
			int x2 = x + GRAPH_ADDITIONAL_WIDTH;
			
			// 회색조 차트
			cvRectangle(hImg,
					cvPoint(x, y),
					cvPoint(x2, TOTAL_AREA_HEIGHT),
					cvScalar(i),
					CV_FILLED, 8, 0);
			
			// 그래프
			int gh = GRAPH_AREA_HEIGHT - (int)Math.round((double)hData[i] / max * GRAPH_AREA_HEIGHT);	// 막대 크기
			cvRectangle(hImg,
					cvPoint(x, gh),
					cvPoint(x2, GRAPH_AREA_HEIGHT),
					CvScalar.BLACK,
					CV_FILLED, 8, 0);
		}
		
		// 히스토그램 이미지 반환
		return hImg.getBufferedImage();
	}
	
	/**
	 * 파일로부터 작업할 이미지를 얻는다.
	 * @param file	파일
	 * @return		파일열기 성공 여부
	 */
	public boolean fileOpen(File file){
		
		// 파일이 존재하면 작업이미지를 얻음
		if(file.exists()){
			
			BufferedImage bimg = getBimg(file);		// 파일로부터 bufferedImage 구함
			this.image = IplImage.createFrom(bimg);	// 작업이미지로 설정
			return true;// 성공
		}
		
		return false;	// 실패
	}
	
	/**
	 * 되돌리기<br />
	 * 
	 * @return	성공 여부
	 */
	public boolean undo(){
		
		// 이전 이미지 없으면 종료
		if(this.previousImage == null)
			return false;
		
		// 되돌리기
		this.image = this.previousImage;	// 작업 이미지를 이전 이미지로 바꾸고
		this.previousImage = null;			// 이전 이미지는 null
		
		return true;	// 성공
	}
	
	/**
	 * 흑백영상으로 변환
	 */
	public void RGBtoGray(){
		
		// 예외처리
		if(image == null) return;				// 작업 이미지 없음
//		else if(image.nChannels() != 3) return;	// RGB 채널이 아님
		// 이전 이미지 저장
		previousImageSave();
		
		// 처리
		IplImage grayImage = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int gIndex = getPixelIndex(grayImage, x, y);		// 회색조 영상의 픽셀 위치
				CvScalar s = cvGet2D(image, y, x);					// 작업 이미지의 색상 구함
				double gray = (s.red() + s.blue() + s.green()) / 3;	// 회색조 값 구함
				grayImage.getByteBuffer().put(gIndex, (byte)gray);	// 위치에 값 대입
			}
		}
		
		// 반영
		image = grayImage;
	}
	
	/**
	 * 좌우 반전
	 */
	public void horizontalReverse(){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 처리
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int srcIdx = getPixelIndex(image, image.width()-x-1, y);	// 이미지 x 반대 좌표의 픽셀
				int destIdx = getPixelIndex(dest, x, y);					// 반전될 이미지의 픽셀
				byte val = image.getByteBuffer().get(srcIdx);				// 이미지의 픽셀 값
				dest.getByteBuffer().put(destIdx, val);						// 반전 이미지에 값 대입
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 상하 반전
	 */
	public void verticalReverse(){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 처리
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int srcIdx = getPixelIndex(image, x, image.height()-y-1);	// 이미지 y 반대 좌표의 픽셀
				int destIdx = getPixelIndex(dest, x, y);					// 반전될 이미지의 픽셀
				byte val = image.getByteBuffer().get(srcIdx);				// 이미지의 픽셀 값
				dest.getByteBuffer().put(destIdx, val);						// 반전 이미지에 값 대입
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 히스토그램 평활화
	 */
	public void histogramEqualization(){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 정규화 된 누적 값 얻기
		int[] hData = getHistogramData();	// 히스토그램 배열
		int max = 255;						// 최대 명암값
		int[] normalizedSum = new int[256];	// 정규화 누적 합
		int sum = 0;						// 합
		int pixels = image.width()*image.height();	// 전체 화소 수
		for(int i=0; i<256; i++){
			sum += hData[i];						// 현재까지 명암도 계산
			normalizedSum[i] = (int)Math.round((double)sum / pixels * max);	// 정규화된 누적 합 계산
		}
		
		// 매핑을 통한 평활화 반영
		image = mapping(normalizedSum);
	}
	
	/**
	 * 히스토그램 스트레칭
	 * @param low	최소값
	 * @param high	최대값
	 */
	public void histogramStretching(int low, int high){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 스트레칭 마스크 계산
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int idx = getPixelIndex(image, x, y);		// 현재 인덱스
				int X = image.getByteBuffer().get(idx) & 0xff;	// 현재 픽셀 값
				int newX = (255 * (X-low)/(high-low));		// 새 픽셀 값 계산
				if(newX < 0) newX = 0;						// 최소 0
				else if(newX > 255) newX = 255;				// 최대 255
				dest.getByteBuffer().put(idx, (byte)newX);	// 이미지에 반영
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 스트레칭 - 자동 스트레칭에 사용할 최소값과 최대값
	 * @return	[0] : 최소값, [1] : 최대값
	 */
	public int[] getBoundaryAutoStretching(){
		
		int[] hData = getHistogramData();			// 히스토그램 데이터
		boolean[] b = new boolean[hData.length];	// 값이 기준점 이상인지 여부
		int cut = getMaximum(hData)*1/10;			// 기준점 계산
		int maxIdx = getMaximumIndex(hData);		// 최대 값을 가르키는 인덱스
		
		// 기준점 이상 여부 계산
		for(int i=0; i<hData.length; i++){
			if(hData[i] >= cut)	b[i] = true;
			else				b[i] = false;
		}
		
		// 최소값 구함
		int i = maxIdx;
		int min = maxIdx;
		while(i>=0){
			if(b[i] == true) min = i--;
			else break;
		}
		
		// 최대값 구함
		i = maxIdx;
		int max = maxIdx;
		while(i<=255){
			if(b[i] == true) max = i++;
			else break;
		}
		
		// 반환
		int []n = {min, max};
		return n;
	}
	
	/**
	 * 음영상
	 */
	public void negative(){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 음화영상 계산
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int idx = getPixelIndex(image, x, y);		// 현재 인덱스
				int val = image.getByteBuffer().get(idx) & 0xff;	// 현재 픽셀 값
				int iVal = 255 - val;						// 반대 값 계산
				dest.getByteBuffer().put(idx, (byte)iVal);	// 값 대입
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 영상 이진화
	 * @param value	기준값 0~255
	 */
	public void thresholding(int value){

		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 음화영상 계산
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int idx = getPixelIndex(image, x, y);		// 현재 인덱스
				int val = image.getByteBuffer().get(idx) & 0xff;	// 현재 픽셀 값
				int binary = val > value ? 255 : 0;			// 기준점 보다 크면 255, 작으면 0
				dest.getByteBuffer().put(idx, (byte)binary);// 대입
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 비트 플레인 처리
	 * @param bit	비트의 자리수 0~7
	 */
	public void bitplane(int bit){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 비트 플레인 처리
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		int a = (int)Math.pow(2.0, (double)bit);	// bit번째 자리가 1인지 검출용
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int idx = getPixelIndex(image, x, y);		// 현재 인덱스
				int val = image.getByteBuffer().get(idx) & 0xff;	// 현재 픽셀 값
				int newVal = ((val & a)>>bit) * 255;		// 해당 비트가 1이면 255 아니면 0
				dest.getByteBuffer().put(idx, (byte)newVal);// 대입
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 블러링 3x3
	 */
	public void blurring3x3(){
		
		blurringConvolution(MASK_BLUR_3X3);
	}
	
	/**
	 * 블러링 5x5
	 */
	public void blurring5x5(){
		
		blurringConvolution(MASK_BLUR_5X5);
	}

	/**
	 * 샤프닝
	 */
	public void sharpening(){
		
		convolution(MASK_SHARPEN);
	}
	
	/**
	 * 평균값 필터링
	 */
	public void meanFiltering(){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 필터링
		int maskSize = 3;
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int sum = 0;
				for(int i=0; i<maskSize; i++){
					for(int j=0; j<maskSize; j++){
						int xx = x+(maskSize/2+j+1-maskSize);	// 이미지 위에서의 마스크 x
						int yy = y+(maskSize/2+i+1-maskSize);	// 이미지 위에서의 마스크 y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// 이미지 경계를 벗어나면 다음 계산 수행
						int idx = getPixelIndex(image, xx, yy);			// 픽셀 인덱스
						int val = image.getByteBuffer().get(idx) & 0xff;// 픽셀 값
						sum += val / (maskSize*maskSize);				// 평균값 저장
					}
				}
				int idx = getPixelIndex(dest, x, y);		// 중간값 적용될 픽셀
				dest.getByteBuffer().put(idx, (byte)sum);	// 적용
			}
		}
		
		// 반영
		image = dest;
	}

	/**
	 * 중간값 필터링
	 */
	public void medianFiltering(){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 필터링
		int maskSize = 3;
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				ArrayList<Integer> arr = new ArrayList<Integer>();
				for(int i=0; i<maskSize; i++){
					for(int j=0; j<maskSize; j++){
						int xx = x+(maskSize/2+j+1-maskSize);	// 이미지 위에서의 마스크 x
						int yy = y+(maskSize/2+i+1-maskSize);	// 이미지 위에서의 마스크 y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// 이미지 경계를 벗어나면 다음 계산 수행
						int idx = getPixelIndex(image, xx, yy);			// 픽셀 인덱스
						int val = image.getByteBuffer().get(idx) & 0xff;// 픽셀 값
						arr.add(val);	// 값 저장
					}
				}// end mask
				Collections.sort(arr);						// 정렬
				int median = arr.get(arr.size()/2);			// 중간값
				int idx = getPixelIndex(dest, x, y);		// 인덱스
				dest.getByteBuffer().put(idx, (byte)median);// 적용
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 가우시안 스무싱
	 */
	public void smoothing(){
		
		blurringConvolution(MASK_SMOOTHING);
	}
	
	/**
	 * 소벨 마스킹
	 */
	public void sobelmasking(){
		
		convolution(MASK_SOBEL);
	}
	
	/**
	 * 라플라시안
	 */
	public void laplacian(){
		
		convolution(MASK_LAPLACIAN);
	}
	
	/**
	 * 모폴로지 - 열림
	 */
	public void morphologyOpening(){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 계산
		IplImage erosion = morphology(image, MASK_EROSION, 255, 0);	// 침식 후
		IplImage dest = morphology(erosion, MASK_DILATION, 0, 255);	// 팽창

		// 반영
		image = dest;
	}
	
	/**
	 * 모폴로지 - 닫힘
	 */
	public void morphologyClosing(){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 계산
		IplImage dilation = morphology(image, MASK_DILATION, 0, 255);	// 팽창 후
		IplImage dest = morphology(dilation, MASK_EROSION, 255, 0);		// 침식

		// 반영
		image = dest;
	}
	
	/**
	 * 확대
	 * @param wd	너비 비율
	 * @param hd	높이 비율
	 */
	public void expansion(double wd, double hd){
		
		scaling(wd, hd);
	}
	
	/**
	 * 축소
	 * @param wd	너비 비율
	 * @param hd	높이 비율
	 */
	public void reduction(double wd, double hd){
		
		scaling(1/wd, 1/hd);
	}
	
	/**
	 * 확대 선형보간
	 * @param wd	너비 비율
	 * @param hd	높이 비율
	 */
	public void expansionLinearInterpolation(double wd, double hd){
		
		scalingLinearInterpolation(wd, hd);
	}
	
	/**
	 * 축소 선형보건
	 * @param wd	너비 비율
	 * @param hd	높이 비율
	 */
	public void reductionLinearInterpolation(double wd, double hd){
		
		scalingLinearInterpolation(1/wd, 1/hd);
	}
	
	/**
	 * 회전
	 * @param angle	각도
	 */
	public void rotation(double degree){

		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 처리
		double seta = Math.PI / (180.0 / degree);
		int centerX = image.width() / 2;
		int centerY = image.height() / 2;
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<dest.height(); y++){
			for(int x=0; x<dest.width(); x++){
				// 회전 후 좌표 구함
				int destX = (int)((x-centerX) * Math.cos(seta) - (y-centerY) * Math.sin(seta)) + centerX;
				int destY = (int)((x-centerX) * Math.sin(seta) + (y-centerY) * Math.cos(seta)) + centerY;
				// 경계 확인
				if(destX < 0)				continue;
				if(destY < 0)				continue;
				if(destX >= image.width())	continue;
				if(destY >= image.height())	continue;
				// 값 넣음
				int srcIdx = getPixelIndex(image, x, y);
				int destIdx = getPixelIndex(dest, destX, destY);
				byte value = image.getByteBuffer().get(srcIdx);
				dest.getByteBuffer().put(destIdx, value);
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 확대 및 축소 선형보간
	 * @param wd	너비비율
	 * @param hd	높이비율
	 */
	private void scalingLinearInterpolation(double wd, double hd){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 처리
		int width = (int)Math.round(image.width()*wd);	// 크기 변경된 이미지 너비
		int height = (int)Math.round(image.height()*hd);// 크기 변경된 이미지 높이
		IplImage dest = IplImage.create(width, height, IPL_DEPTH_8U, 1);
		for(int y=0; y<dest.height(); y++){
			for(int x=0; x<dest.width(); x++){
				int srcX = (int)(x/wd);
				int srcY = (int)(y/hd);
				int srcIdx = getPixelIndex(image, srcX, srcY);
				int destIdx = getPixelIndex(dest, x, y);
				byte value = image.getByteBuffer().get(srcIdx);
				dest.getByteBuffer().put(destIdx, value);
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 확대 및 축소
	 * @param wd	너비 비율
	 * @param hd	높이 비율
	 */
	private void scaling(double wd, double hd){

		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 처리
		int width = (int)Math.round(image.width()*wd);	// 크기 변경된 이미지 너비
		int height = (int)Math.round(image.height()*hd);	// 크기 변경된 이미지 높이
		IplImage dest = IplImage.create(width, height, IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int destX = (int)Math.round(wd * x);				// 변경된 x
				int destY = (int)Math.round(hd * y);				// 변경된 y
				// 경계 확인
				if(destX >= width) destX = width-1;
				if(destY >= height) destY = height-1;
				int srcIdx = getPixelIndex(image, x, y);		// 원 이미지의 인덱스
				int destIdx = getPixelIndex(dest, destX, destY);// 변경된 이미지의 인덱스
				byte value = image.getByteBuffer().get(srcIdx);
				dest.getByteBuffer().put(destIdx, value);
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 모폴로지 연산
	 * @param mask		마스크
	 * @param src		원본 이미지
	 * @param trueVal	올바른 경우 저장할 값
	 * @param falseVal	다른 경우 저장할 값
	 * @return			모포롤지 적용 된 이미지
	 */
	private IplImage morphology(IplImage src, int[][] mask, int trueVal, int falseVal){
		
		// 예외처리
		if(src == null) return null;
		
		// 계산
		IplImage dest = IplImage.create(src.width(), src.height(), IPL_DEPTH_8U, 1);
		int mh = mask.length;	// 마스크 높이
		for(int y=0; y<dest.height(); y++){
			for(int x=0; x<dest.width(); x++){
				boolean isAllSame = true;	// 모든 픽셀이 마스크와 동일한지 여부
				// 마스크 탐색
				for(int my=0; my<mh; my++){
					int mw = mask[my].length;	// 마스크 너비
					for(int mx=0; mx<mw; mx++){
						int xx = x+(mw/2+mx+1-mw);	// 이미지 위에서의 마스크 x
						int yy = y+(mh/2+my+1-mh);	// 이미지 위에서의 마스크 y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// 이미지 경계를 벗어나면 다음 계산 수행
						int idx = getPixelIndex(src, xx, yy);		// 픽셀 인덱스
						int val = src.getByteBuffer().get(idx) & 0xff;// 픽셀 값
						if(val != mask[my][mx]){					// 다르면 false 저장하고 탈출
							isAllSame = false;
							break;
						}
					}
					if(!isAllSame) break;	// false 저장했으면 바로 탈출
				}// end of mask
				int newVal = isAllSame ? trueVal : falseVal;	// 적용할 값 획득
				int idx = getPixelIndex(dest, x, y);			// 적용할 위치
				dest.getByteBuffer().put(idx, (byte)newVal);	// 적용
			}
		}
		
		// 반환
		return dest;
	}
	
	/**
	 * 블러링용 회선
	 * @param mask	마스크
	 */
	private void blurringConvolution(int[][] mask){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 이미지 복사
		IplImage dest = image.clone();
		
		// 계산
		int mh = mask.length;	// 마스크 높이
		for(int y=0; y<dest.height(); y++){
			for(int x=0; x<dest.width(); x++){
				double newVal = 0;	// 마스크 적용 된 값
				int coef = 0;		// 계수
				// 계수 계산
				// 계수가 마스크의 width*height로 고정되어있으면 이미지 경계를 만나면 올바른 값이 나오지 않음
				// 따라서 이미지 경계 안에 포함되는 부분만 계산함
				for(int my=0; my<mh; my++){
					int mw = mask[my].length;		// 마스크 너비
					for(int mx=0; mx<mw; mx++){
						int xx = x+(mw/2+mx+1-mw);	// 이미지 위에서의 마스크 x
						int yy = y+(mh/2+my+1-mh);	// 이미지 위에서의 마스크 y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// 이미지 경계를 벗어나면 다음 계산 수행
						coef+=mask[my][mx];			// 계수 증가
					}
				}
				// 마스크 값 계산
				for(int my=0; my<mh; my++){
					int mw = mask[my].length;		// 마스크 너비
					for(int mx=0; mx<mw; mx++){
						int xx = x+(mw/2+mx+1-mw);	// 이미지 위에서의 마스크 x
						int yy = y+(mh/2+my+1-mh);	// 이미지 위에서의 마스크 y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// 이미지 경계를 벗어나면 다음 계산 수행
						int idx = getPixelIndex(dest, xx, yy);				// 마스크 위 픽셀 인덱스
						int val = image.getByteBuffer().get(idx) & 0xff;	// 마스크 위 픽셀 값
						double rate = (double)mask[my][mx] / (double)coef;	// 마스크 배율
						newVal += val * rate;								// 마스크 값 더함
					}
				}// end of mask
				// 색상 범위 확인 후 대입
				if(newVal < 0) newVal = 0;				// 회색조 경계 0 초과는 0
				else if(newVal > 255) newVal = 255;		// 회색조 경계 255 미만은 255
				int idx = getPixelIndex(image, x, y);	// 현재 픽셀 인덱스
				dest.getByteBuffer().put(idx, (byte)newVal);	// 대입
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 회선
	 * @param mask	마스크
	 */
	private void convolution(int[][] mask){
		
		// 예외처리
		if(image == null) return;
		
		// 백업
		previousImageSave();
		
		// 이미지 복사
		IplImage dest = image.clone();
		
		// 계산
		int mh = mask.length;	// 마스크 높이
		for(int y=0; y<dest.height(); y++){
			for(int x=0; x<dest.width(); x++){
				double newVal = 0;	// 마스크 적용 된 값
				// 마스크 값 계산
				for(int my=0; my<mh; my++){
					int mw = mask[my].length;		// 마스크 너비
					for(int mx=0; mx<mw; mx++){
						int xx = x+(mw/2+mx+1-mw);	// 이미지 위에서의 마스크 x
						int yy = y+(mh/2+my+1-mh);	// 이미지 위에서의 마스크 y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// 이미지 경계를 벗어나면 다음 계산 수행
						int idx = getPixelIndex(dest, xx, yy);				// 마스크 위 픽셀 인덱스
						int val = image.getByteBuffer().get(idx) & 0xff;	// 마스크 위 픽셀 값
						newVal += val * mask[my][mx];
					}
				}// end of mask
				// 색상 범위 확인 후 대입
				if(newVal < 0) newVal = 0;				// 회색조 경계 0 초과는 0
				else if(newVal > 255) newVal = 255;		// 회색조 경계 255 미만은 255
				int idx = getPixelIndex(image, x, y);	// 현재 픽셀 인덱스
				dest.getByteBuffer().put(idx, (byte)newVal);	// 대입
			}
		}
		
		// 반영
		image = dest;
	}
	
	/**
	 * 작업 이미지를 매핑한 이미지 생성
	 * @param map	마스크
	 * @return		매핑 적용 이미지
	 */
	private IplImage mapping(int[] map){

		// 매핑
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int idx = getPixelIndex(image, x, y);				// 현재 픽셀 인덱스
				int mapIdx = image.getByteBuffer().get(idx) & 0xff;	// 명암값으로부터 마스크의 인덱스 구하기
				dest.getByteBuffer().put(idx, (byte)map[mapIdx]);	// 적용
			}
		}
		
		return dest;
	}

	/**
	 * 이미지의 현재 인덱스를 구한다.
	 * @param img	이미지
	 * @param x		현재 x 좌표
	 * @param y		현재 y 좌표
	 * @return		현재 인덱스
	 */
	private int getPixelIndex(IplImage img, int x, int y){
		
		return y*img.widthStep()+x*img.nChannels();
	}
	
	/**
	 * 파일로부터 BufferedImage 구함
	 * @param f	파일
	 * @return	bufferedImage. 오류시 null
	 */
	private BufferedImage getBimg(File f){
		
		BufferedImage bimg;
		try {
			bimg = ImageIO.read(f);		// 파일로부터 이미지 읽음
		} catch (IOException ignored) {	// 실패시 null 반환
			return null;
		}
		
		return bimg;	// 성공시 BufferedImage 반환
	}
	
	/**
	 * 이전이미지 저장<br />
	 * 현재 작업 이미지를 참조함
	 */
	private void previousImageSave(){
		
		if(this.image != null)
			this.previousImage = this.image;
	}

	/**
	 * 작업이미지로부터 히스토그램 배열 얻음
	 * @return	히스토그램 배열, 없으면 null
	 */
	private int[] getHistogramData(){
		
		if(image == null)
			return null;
		
		int[] hData = new int[256];
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				// 카운팅
				int index = getPixelIndex(image, x, y);				// 픽셀 위치
				int value = image.getByteBuffer().get(index) & 0xff;// 픽셀 값
				hData[value]++;	// 해당 값에 매칭되는 카운터 증가
			}
		}
		
		return hData;
	}

	/**
	 * 배열에서 최대 값 구함
	 * @param arr	배열
	 * @return		최대값
	 */
	private int getMaximum(int[] arr){
		
		// 초기값
		int max = arr[0];
		
		// 배열 크기가 2이상
		if(arr.length > 1){
			for(int i=1; i<arr.length; i++)
				if(max < arr[i])
					max = arr[i];
		}// end if length > 1
		
		return max;
	}
	
	/**
	 * 배열에서 최대 값의 인덱스 구함
	 * @param arr	배열
	 * @return		최대값의 인덱스
	 */
	private int getMaximumIndex(int[] arr){
		
		// 초기값
		int max = arr[0];
		int maxIdx = 0;
		
		// 배열 크기가 2이상
		if(arr.length > 1){
			for(int i=1; i<arr.length; i++){
				if(max < arr[i]){
					max = arr[i];
					maxIdx = i;
				}
			}
		}// end if length > 1
		
		return maxIdx;
	}
}
