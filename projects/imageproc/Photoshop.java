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
	
	private static final int GRAPH_ADDITIONAL_WIDTH = 1;// ������׷� ���� ��
	private static final int GRAPH_AREA_HEIGHT = 100;	// ������׷� �׷��� ����
	private static final int GRAYSCALE_AREA_HEIGHT = 10;// ������׷� grayscale ����
	private static final int TOTAL_AREA_HEIGHT = 115;	// ������׷� �̹��� ����
	private static final int[][] MASK_BLUR_3X3 = {{1,1,1},
												{1,1,1},
												{1,1,1}};	// ���� ����ũ 3x3
	private static final int[][] MASK_BLUR_5X5 = {{1,1,1,1,1},
												{1,1,1,1,1},
												{1,1,1,1,1},
												{1,1,1,1,1},
												{1,1,1,1,1}};// ���� ����ũ 5x5
	private static final int[][] MASK_SHARPEN = {{0,-1,0},
												{-1,5,-1},
												{0,-1,0}};	// ������ ����ũ
	private static final int[][] MASK_SMOOTHING = {{1,2,1},
												{2,4,2},
												{1,2,1}};	// ����þ� smoothing ����ũ
	private static final int[][] MASK_SOBEL = {{0,2,2},
											{-2,0,2},
											{-2,-2,0}};		// �Һ� ����ũ ����+����
	private static final int[][] MASK_LAPLACIAN = {{0,-1,0},
												{-1,4,-1},
												{0,-1,0}};	// ���ö�þ� 4���� ����ũ
	private static final int[][] MASK_EROSION = {{255,255,255},
												{255,255,255},
												{255,255,255}};	// �������� ħ�� ���� ����ũ
	private static final int[][] MASK_DILATION = {{0,0,0},
												{0,0,0},
												{0,0,0}};	// �������� ��â ���� ����ũ
	private IplImage image;			// �۾� �̹���
	private IplImage previousImage;	// �� �ܰ� ���� �̹���
	
	/**
	 * �۾� �̹����� ��ȯ�Ѵ�.
	 * @return	�۾� �̹���, ������ null
	 */
	public BufferedImage getBufferedImage(){
		
		if(image == null)
			return null;
		
		return image.getBufferedImage();
	}
	
	/**
	 * �۾� �̹����� ������׷� �̹��� ��ȯ
	 * @return	������׷� �̹���
	 */
	public BufferedImage getHistogramImage(){
		
		// ����ó��
		if(image == null)
			return null;
		
		// ������׷� ��� �迭
		int[] hData = getHistogramData();
		
		// �ִ� ��� ��
		int max = getMaximum(hData);
		
		// �̹���ȭ
		IplImage hImg = IplImage.create(256*GRAPH_ADDITIONAL_WIDTH, TOTAL_AREA_HEIGHT, IPL_DEPTH_8U, 1);
		cvRectangle(hImg,
				cvPoint(0,0),
				cvPoint(hImg.width(), hImg.height()),
				CvScalar.WHITE,
				CV_FILLED, 8, 0);
		for(int i=0; i<256; i++){
			
			// ���� ��ǥ
			int x = i*GRAPH_ADDITIONAL_WIDTH;
			int y = TOTAL_AREA_HEIGHT-GRAYSCALE_AREA_HEIGHT;
			int x2 = x + GRAPH_ADDITIONAL_WIDTH;
			
			// ȸ���� ��Ʈ
			cvRectangle(hImg,
					cvPoint(x, y),
					cvPoint(x2, TOTAL_AREA_HEIGHT),
					cvScalar(i),
					CV_FILLED, 8, 0);
			
			// �׷���
			int gh = GRAPH_AREA_HEIGHT - (int)Math.round((double)hData[i] / max * GRAPH_AREA_HEIGHT);	// ���� ũ��
			cvRectangle(hImg,
					cvPoint(x, gh),
					cvPoint(x2, GRAPH_AREA_HEIGHT),
					CvScalar.BLACK,
					CV_FILLED, 8, 0);
		}
		
		// ������׷� �̹��� ��ȯ
		return hImg.getBufferedImage();
	}
	
	/**
	 * ���Ϸκ��� �۾��� �̹����� ��´�.
	 * @param file	����
	 * @return		���Ͽ��� ���� ����
	 */
	public boolean fileOpen(File file){
		
		// ������ �����ϸ� �۾��̹����� ����
		if(file.exists()){
			
			BufferedImage bimg = getBimg(file);		// ���Ϸκ��� bufferedImage ����
			this.image = IplImage.createFrom(bimg);	// �۾��̹����� ����
			return true;// ����
		}
		
		return false;	// ����
	}
	
	/**
	 * �ǵ�����<br />
	 * 
	 * @return	���� ����
	 */
	public boolean undo(){
		
		// ���� �̹��� ������ ����
		if(this.previousImage == null)
			return false;
		
		// �ǵ�����
		this.image = this.previousImage;	// �۾� �̹����� ���� �̹����� �ٲٰ�
		this.previousImage = null;			// ���� �̹����� null
		
		return true;	// ����
	}
	
	/**
	 * ��鿵������ ��ȯ
	 */
	public void RGBtoGray(){
		
		// ����ó��
		if(image == null) return;				// �۾� �̹��� ����
//		else if(image.nChannels() != 3) return;	// RGB ä���� �ƴ�
		// ���� �̹��� ����
		previousImageSave();
		
		// ó��
		IplImage grayImage = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int gIndex = getPixelIndex(grayImage, x, y);		// ȸ���� ������ �ȼ� ��ġ
				CvScalar s = cvGet2D(image, y, x);					// �۾� �̹����� ���� ����
				double gray = (s.red() + s.blue() + s.green()) / 3;	// ȸ���� �� ����
				grayImage.getByteBuffer().put(gIndex, (byte)gray);	// ��ġ�� �� ����
			}
		}
		
		// �ݿ�
		image = grayImage;
	}
	
	/**
	 * �¿� ����
	 */
	public void horizontalReverse(){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ó��
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int srcIdx = getPixelIndex(image, image.width()-x-1, y);	// �̹��� x �ݴ� ��ǥ�� �ȼ�
				int destIdx = getPixelIndex(dest, x, y);					// ������ �̹����� �ȼ�
				byte val = image.getByteBuffer().get(srcIdx);				// �̹����� �ȼ� ��
				dest.getByteBuffer().put(destIdx, val);						// ���� �̹����� �� ����
			}
		}
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * ���� ����
	 */
	public void verticalReverse(){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ó��
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int srcIdx = getPixelIndex(image, x, image.height()-y-1);	// �̹��� y �ݴ� ��ǥ�� �ȼ�
				int destIdx = getPixelIndex(dest, x, y);					// ������ �̹����� �ȼ�
				byte val = image.getByteBuffer().get(srcIdx);				// �̹����� �ȼ� ��
				dest.getByteBuffer().put(destIdx, val);						// ���� �̹����� �� ����
			}
		}
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * ������׷� ��Ȱȭ
	 */
	public void histogramEqualization(){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ����ȭ �� ���� �� ���
		int[] hData = getHistogramData();	// ������׷� �迭
		int max = 255;						// �ִ� ��ϰ�
		int[] normalizedSum = new int[256];	// ����ȭ ���� ��
		int sum = 0;						// ��
		int pixels = image.width()*image.height();	// ��ü ȭ�� ��
		for(int i=0; i<256; i++){
			sum += hData[i];						// ������� ��ϵ� ���
			normalizedSum[i] = (int)Math.round((double)sum / pixels * max);	// ����ȭ�� ���� �� ���
		}
		
		// ������ ���� ��Ȱȭ �ݿ�
		image = mapping(normalizedSum);
	}
	
	/**
	 * ������׷� ��Ʈ��Ī
	 * @param low	�ּҰ�
	 * @param high	�ִ밪
	 */
	public void histogramStretching(int low, int high){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ��Ʈ��Ī ����ũ ���
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int idx = getPixelIndex(image, x, y);		// ���� �ε���
				int X = image.getByteBuffer().get(idx) & 0xff;	// ���� �ȼ� ��
				int newX = (255 * (X-low)/(high-low));		// �� �ȼ� �� ���
				if(newX < 0) newX = 0;						// �ּ� 0
				else if(newX > 255) newX = 255;				// �ִ� 255
				dest.getByteBuffer().put(idx, (byte)newX);	// �̹����� �ݿ�
			}
		}
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * ��Ʈ��Ī - �ڵ� ��Ʈ��Ī�� ����� �ּҰ��� �ִ밪
	 * @return	[0] : �ּҰ�, [1] : �ִ밪
	 */
	public int[] getBoundaryAutoStretching(){
		
		int[] hData = getHistogramData();			// ������׷� ������
		boolean[] b = new boolean[hData.length];	// ���� ������ �̻����� ����
		int cut = getMaximum(hData)*1/10;			// ������ ���
		int maxIdx = getMaximumIndex(hData);		// �ִ� ���� ����Ű�� �ε���
		
		// ������ �̻� ���� ���
		for(int i=0; i<hData.length; i++){
			if(hData[i] >= cut)	b[i] = true;
			else				b[i] = false;
		}
		
		// �ּҰ� ����
		int i = maxIdx;
		int min = maxIdx;
		while(i>=0){
			if(b[i] == true) min = i--;
			else break;
		}
		
		// �ִ밪 ����
		i = maxIdx;
		int max = maxIdx;
		while(i<=255){
			if(b[i] == true) max = i++;
			else break;
		}
		
		// ��ȯ
		int []n = {min, max};
		return n;
	}
	
	/**
	 * ������
	 */
	public void negative(){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ��ȭ���� ���
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int idx = getPixelIndex(image, x, y);		// ���� �ε���
				int val = image.getByteBuffer().get(idx) & 0xff;	// ���� �ȼ� ��
				int iVal = 255 - val;						// �ݴ� �� ���
				dest.getByteBuffer().put(idx, (byte)iVal);	// �� ����
			}
		}
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * ���� ����ȭ
	 * @param value	���ذ� 0~255
	 */
	public void thresholding(int value){

		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ��ȭ���� ���
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int idx = getPixelIndex(image, x, y);		// ���� �ε���
				int val = image.getByteBuffer().get(idx) & 0xff;	// ���� �ȼ� ��
				int binary = val > value ? 255 : 0;			// ������ ���� ũ�� 255, ������ 0
				dest.getByteBuffer().put(idx, (byte)binary);// ����
			}
		}
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * ��Ʈ �÷��� ó��
	 * @param bit	��Ʈ�� �ڸ��� 0~7
	 */
	public void bitplane(int bit){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ��Ʈ �÷��� ó��
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		int a = (int)Math.pow(2.0, (double)bit);	// bit��° �ڸ��� 1���� �����
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int idx = getPixelIndex(image, x, y);		// ���� �ε���
				int val = image.getByteBuffer().get(idx) & 0xff;	// ���� �ȼ� ��
				int newVal = ((val & a)>>bit) * 255;		// �ش� ��Ʈ�� 1�̸� 255 �ƴϸ� 0
				dest.getByteBuffer().put(idx, (byte)newVal);// ����
			}
		}
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * ���� 3x3
	 */
	public void blurring3x3(){
		
		blurringConvolution(MASK_BLUR_3X3);
	}
	
	/**
	 * ���� 5x5
	 */
	public void blurring5x5(){
		
		blurringConvolution(MASK_BLUR_5X5);
	}

	/**
	 * ������
	 */
	public void sharpening(){
		
		convolution(MASK_SHARPEN);
	}
	
	/**
	 * ��հ� ���͸�
	 */
	public void meanFiltering(){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ���͸�
		int maskSize = 3;
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int sum = 0;
				for(int i=0; i<maskSize; i++){
					for(int j=0; j<maskSize; j++){
						int xx = x+(maskSize/2+j+1-maskSize);	// �̹��� �������� ����ũ x
						int yy = y+(maskSize/2+i+1-maskSize);	// �̹��� �������� ����ũ y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// �̹��� ��踦 ����� ���� ��� ����
						int idx = getPixelIndex(image, xx, yy);			// �ȼ� �ε���
						int val = image.getByteBuffer().get(idx) & 0xff;// �ȼ� ��
						sum += val / (maskSize*maskSize);				// ��հ� ����
					}
				}
				int idx = getPixelIndex(dest, x, y);		// �߰��� ����� �ȼ�
				dest.getByteBuffer().put(idx, (byte)sum);	// ����
			}
		}
		
		// �ݿ�
		image = dest;
	}

	/**
	 * �߰��� ���͸�
	 */
	public void medianFiltering(){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ���͸�
		int maskSize = 3;
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				ArrayList<Integer> arr = new ArrayList<Integer>();
				for(int i=0; i<maskSize; i++){
					for(int j=0; j<maskSize; j++){
						int xx = x+(maskSize/2+j+1-maskSize);	// �̹��� �������� ����ũ x
						int yy = y+(maskSize/2+i+1-maskSize);	// �̹��� �������� ����ũ y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// �̹��� ��踦 ����� ���� ��� ����
						int idx = getPixelIndex(image, xx, yy);			// �ȼ� �ε���
						int val = image.getByteBuffer().get(idx) & 0xff;// �ȼ� ��
						arr.add(val);	// �� ����
					}
				}// end mask
				Collections.sort(arr);						// ����
				int median = arr.get(arr.size()/2);			// �߰���
				int idx = getPixelIndex(dest, x, y);		// �ε���
				dest.getByteBuffer().put(idx, (byte)median);// ����
			}
		}
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * ����þ� ������
	 */
	public void smoothing(){
		
		blurringConvolution(MASK_SMOOTHING);
	}
	
	/**
	 * �Һ� ����ŷ
	 */
	public void sobelmasking(){
		
		convolution(MASK_SOBEL);
	}
	
	/**
	 * ���ö�þ�
	 */
	public void laplacian(){
		
		convolution(MASK_LAPLACIAN);
	}
	
	/**
	 * �������� - ����
	 */
	public void morphologyOpening(){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ���
		IplImage erosion = morphology(image, MASK_EROSION, 255, 0);	// ħ�� ��
		IplImage dest = morphology(erosion, MASK_DILATION, 0, 255);	// ��â

		// �ݿ�
		image = dest;
	}
	
	/**
	 * �������� - ����
	 */
	public void morphologyClosing(){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ���
		IplImage dilation = morphology(image, MASK_DILATION, 0, 255);	// ��â ��
		IplImage dest = morphology(dilation, MASK_EROSION, 255, 0);		// ħ��

		// �ݿ�
		image = dest;
	}
	
	/**
	 * Ȯ��
	 * @param wd	�ʺ� ����
	 * @param hd	���� ����
	 */
	public void expansion(double wd, double hd){
		
		scaling(wd, hd);
	}
	
	/**
	 * ���
	 * @param wd	�ʺ� ����
	 * @param hd	���� ����
	 */
	public void reduction(double wd, double hd){
		
		scaling(1/wd, 1/hd);
	}
	
	/**
	 * Ȯ�� ��������
	 * @param wd	�ʺ� ����
	 * @param hd	���� ����
	 */
	public void expansionLinearInterpolation(double wd, double hd){
		
		scalingLinearInterpolation(wd, hd);
	}
	
	/**
	 * ��� ��������
	 * @param wd	�ʺ� ����
	 * @param hd	���� ����
	 */
	public void reductionLinearInterpolation(double wd, double hd){
		
		scalingLinearInterpolation(1/wd, 1/hd);
	}
	
	/**
	 * ȸ��
	 * @param angle	����
	 */
	public void rotation(double degree){

		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ó��
		double seta = Math.PI / (180.0 / degree);
		int centerX = image.width() / 2;
		int centerY = image.height() / 2;
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<dest.height(); y++){
			for(int x=0; x<dest.width(); x++){
				// ȸ�� �� ��ǥ ����
				int destX = (int)((x-centerX) * Math.cos(seta) - (y-centerY) * Math.sin(seta)) + centerX;
				int destY = (int)((x-centerX) * Math.sin(seta) + (y-centerY) * Math.cos(seta)) + centerY;
				// ��� Ȯ��
				if(destX < 0)				continue;
				if(destY < 0)				continue;
				if(destX >= image.width())	continue;
				if(destY >= image.height())	continue;
				// �� ����
				int srcIdx = getPixelIndex(image, x, y);
				int destIdx = getPixelIndex(dest, destX, destY);
				byte value = image.getByteBuffer().get(srcIdx);
				dest.getByteBuffer().put(destIdx, value);
			}
		}
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * Ȯ�� �� ��� ��������
	 * @param wd	�ʺ����
	 * @param hd	���̺���
	 */
	private void scalingLinearInterpolation(double wd, double hd){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ó��
		int width = (int)Math.round(image.width()*wd);	// ũ�� ����� �̹��� �ʺ�
		int height = (int)Math.round(image.height()*hd);// ũ�� ����� �̹��� ����
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
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * Ȯ�� �� ���
	 * @param wd	�ʺ� ����
	 * @param hd	���� ����
	 */
	private void scaling(double wd, double hd){

		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// ó��
		int width = (int)Math.round(image.width()*wd);	// ũ�� ����� �̹��� �ʺ�
		int height = (int)Math.round(image.height()*hd);	// ũ�� ����� �̹��� ����
		IplImage dest = IplImage.create(width, height, IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int destX = (int)Math.round(wd * x);				// ����� x
				int destY = (int)Math.round(hd * y);				// ����� y
				// ��� Ȯ��
				if(destX >= width) destX = width-1;
				if(destY >= height) destY = height-1;
				int srcIdx = getPixelIndex(image, x, y);		// �� �̹����� �ε���
				int destIdx = getPixelIndex(dest, destX, destY);// ����� �̹����� �ε���
				byte value = image.getByteBuffer().get(srcIdx);
				dest.getByteBuffer().put(destIdx, value);
			}
		}
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * �������� ����
	 * @param mask		����ũ
	 * @param src		���� �̹���
	 * @param trueVal	�ùٸ� ��� ������ ��
	 * @param falseVal	�ٸ� ��� ������ ��
	 * @return			�������� ���� �� �̹���
	 */
	private IplImage morphology(IplImage src, int[][] mask, int trueVal, int falseVal){
		
		// ����ó��
		if(src == null) return null;
		
		// ���
		IplImage dest = IplImage.create(src.width(), src.height(), IPL_DEPTH_8U, 1);
		int mh = mask.length;	// ����ũ ����
		for(int y=0; y<dest.height(); y++){
			for(int x=0; x<dest.width(); x++){
				boolean isAllSame = true;	// ��� �ȼ��� ����ũ�� �������� ����
				// ����ũ Ž��
				for(int my=0; my<mh; my++){
					int mw = mask[my].length;	// ����ũ �ʺ�
					for(int mx=0; mx<mw; mx++){
						int xx = x+(mw/2+mx+1-mw);	// �̹��� �������� ����ũ x
						int yy = y+(mh/2+my+1-mh);	// �̹��� �������� ����ũ y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// �̹��� ��踦 ����� ���� ��� ����
						int idx = getPixelIndex(src, xx, yy);		// �ȼ� �ε���
						int val = src.getByteBuffer().get(idx) & 0xff;// �ȼ� ��
						if(val != mask[my][mx]){					// �ٸ��� false �����ϰ� Ż��
							isAllSame = false;
							break;
						}
					}
					if(!isAllSame) break;	// false ���������� �ٷ� Ż��
				}// end of mask
				int newVal = isAllSame ? trueVal : falseVal;	// ������ �� ȹ��
				int idx = getPixelIndex(dest, x, y);			// ������ ��ġ
				dest.getByteBuffer().put(idx, (byte)newVal);	// ����
			}
		}
		
		// ��ȯ
		return dest;
	}
	
	/**
	 * ������ ȸ��
	 * @param mask	����ũ
	 */
	private void blurringConvolution(int[][] mask){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// �̹��� ����
		IplImage dest = image.clone();
		
		// ���
		int mh = mask.length;	// ����ũ ����
		for(int y=0; y<dest.height(); y++){
			for(int x=0; x<dest.width(); x++){
				double newVal = 0;	// ����ũ ���� �� ��
				int coef = 0;		// ���
				// ��� ���
				// ����� ����ũ�� width*height�� �����Ǿ������� �̹��� ��踦 ������ �ùٸ� ���� ������ ����
				// ���� �̹��� ��� �ȿ� ���ԵǴ� �κи� �����
				for(int my=0; my<mh; my++){
					int mw = mask[my].length;		// ����ũ �ʺ�
					for(int mx=0; mx<mw; mx++){
						int xx = x+(mw/2+mx+1-mw);	// �̹��� �������� ����ũ x
						int yy = y+(mh/2+my+1-mh);	// �̹��� �������� ����ũ y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// �̹��� ��踦 ����� ���� ��� ����
						coef+=mask[my][mx];			// ��� ����
					}
				}
				// ����ũ �� ���
				for(int my=0; my<mh; my++){
					int mw = mask[my].length;		// ����ũ �ʺ�
					for(int mx=0; mx<mw; mx++){
						int xx = x+(mw/2+mx+1-mw);	// �̹��� �������� ����ũ x
						int yy = y+(mh/2+my+1-mh);	// �̹��� �������� ����ũ y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// �̹��� ��踦 ����� ���� ��� ����
						int idx = getPixelIndex(dest, xx, yy);				// ����ũ �� �ȼ� �ε���
						int val = image.getByteBuffer().get(idx) & 0xff;	// ����ũ �� �ȼ� ��
						double rate = (double)mask[my][mx] / (double)coef;	// ����ũ ����
						newVal += val * rate;								// ����ũ �� ����
					}
				}// end of mask
				// ���� ���� Ȯ�� �� ����
				if(newVal < 0) newVal = 0;				// ȸ���� ��� 0 �ʰ��� 0
				else if(newVal > 255) newVal = 255;		// ȸ���� ��� 255 �̸��� 255
				int idx = getPixelIndex(image, x, y);	// ���� �ȼ� �ε���
				dest.getByteBuffer().put(idx, (byte)newVal);	// ����
			}
		}
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * ȸ��
	 * @param mask	����ũ
	 */
	private void convolution(int[][] mask){
		
		// ����ó��
		if(image == null) return;
		
		// ���
		previousImageSave();
		
		// �̹��� ����
		IplImage dest = image.clone();
		
		// ���
		int mh = mask.length;	// ����ũ ����
		for(int y=0; y<dest.height(); y++){
			for(int x=0; x<dest.width(); x++){
				double newVal = 0;	// ����ũ ���� �� ��
				// ����ũ �� ���
				for(int my=0; my<mh; my++){
					int mw = mask[my].length;		// ����ũ �ʺ�
					for(int mx=0; mx<mw; mx++){
						int xx = x+(mw/2+mx+1-mw);	// �̹��� �������� ����ũ x
						int yy = y+(mh/2+my+1-mh);	// �̹��� �������� ����ũ y
						if(xx < 0 || xx >= dest.width() || yy < 0 || yy >= dest.height())
							continue;				// �̹��� ��踦 ����� ���� ��� ����
						int idx = getPixelIndex(dest, xx, yy);				// ����ũ �� �ȼ� �ε���
						int val = image.getByteBuffer().get(idx) & 0xff;	// ����ũ �� �ȼ� ��
						newVal += val * mask[my][mx];
					}
				}// end of mask
				// ���� ���� Ȯ�� �� ����
				if(newVal < 0) newVal = 0;				// ȸ���� ��� 0 �ʰ��� 0
				else if(newVal > 255) newVal = 255;		// ȸ���� ��� 255 �̸��� 255
				int idx = getPixelIndex(image, x, y);	// ���� �ȼ� �ε���
				dest.getByteBuffer().put(idx, (byte)newVal);	// ����
			}
		}
		
		// �ݿ�
		image = dest;
	}
	
	/**
	 * �۾� �̹����� ������ �̹��� ����
	 * @param map	����ũ
	 * @return		���� ���� �̹���
	 */
	private IplImage mapping(int[] map){

		// ����
		IplImage dest = IplImage.create(image.width(), image.height(), IPL_DEPTH_8U, 1);
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				int idx = getPixelIndex(image, x, y);				// ���� �ȼ� �ε���
				int mapIdx = image.getByteBuffer().get(idx) & 0xff;	// ��ϰ����κ��� ����ũ�� �ε��� ���ϱ�
				dest.getByteBuffer().put(idx, (byte)map[mapIdx]);	// ����
			}
		}
		
		return dest;
	}

	/**
	 * �̹����� ���� �ε����� ���Ѵ�.
	 * @param img	�̹���
	 * @param x		���� x ��ǥ
	 * @param y		���� y ��ǥ
	 * @return		���� �ε���
	 */
	private int getPixelIndex(IplImage img, int x, int y){
		
		return y*img.widthStep()+x*img.nChannels();
	}
	
	/**
	 * ���Ϸκ��� BufferedImage ����
	 * @param f	����
	 * @return	bufferedImage. ������ null
	 */
	private BufferedImage getBimg(File f){
		
		BufferedImage bimg;
		try {
			bimg = ImageIO.read(f);		// ���Ϸκ��� �̹��� ����
		} catch (IOException ignored) {	// ���н� null ��ȯ
			return null;
		}
		
		return bimg;	// ������ BufferedImage ��ȯ
	}
	
	/**
	 * �����̹��� ����<br />
	 * ���� �۾� �̹����� ������
	 */
	private void previousImageSave(){
		
		if(this.image != null)
			this.previousImage = this.image;
	}

	/**
	 * �۾��̹����κ��� ������׷� �迭 ����
	 * @return	������׷� �迭, ������ null
	 */
	private int[] getHistogramData(){
		
		if(image == null)
			return null;
		
		int[] hData = new int[256];
		for(int y=0; y<image.height(); y++){
			for(int x=0; x<image.width(); x++){
				// ī����
				int index = getPixelIndex(image, x, y);				// �ȼ� ��ġ
				int value = image.getByteBuffer().get(index) & 0xff;// �ȼ� ��
				hData[value]++;	// �ش� ���� ��Ī�Ǵ� ī���� ����
			}
		}
		
		return hData;
	}

	/**
	 * �迭���� �ִ� �� ����
	 * @param arr	�迭
	 * @return		�ִ밪
	 */
	private int getMaximum(int[] arr){
		
		// �ʱⰪ
		int max = arr[0];
		
		// �迭 ũ�Ⱑ 2�̻�
		if(arr.length > 1){
			for(int i=1; i<arr.length; i++)
				if(max < arr[i])
					max = arr[i];
		}// end if length > 1
		
		return max;
	}
	
	/**
	 * �迭���� �ִ� ���� �ε��� ����
	 * @param arr	�迭
	 * @return		�ִ밪�� �ε���
	 */
	private int getMaximumIndex(int[] arr){
		
		// �ʱⰪ
		int max = arr[0];
		int maxIdx = 0;
		
		// �迭 ũ�Ⱑ 2�̻�
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
