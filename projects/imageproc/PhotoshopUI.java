import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;


public class PhotoshopUI extends JFrame {
	private static final long serialVersionUID = 5544579270068030609L;
	private Photoshop prg = new Photoshop();
	
	private JScrollPane spImageWrapper;
	private JPanel jpTopmenu;
	private JButton btnOpen;
	private JLabel lblImageScreen;
	private JButton btnGrayscale;
	private JButton btnUndo;
	
	private File curFile;
	private JButton btnSave;
	private JButton btnSaveas;
	private JButton btnHorizontalReverse;
	private JButton btnVerticalReverse;
	private JPanel jpMenu2;
	private JPanel jpMenu1;
	private JLabel lblMenu2Title;
	private JButton btnHistogram;
	private JButton btnEqualization;
	private JButton btnStretching;
	private JButton btnNegative;
	private JButton btnThreshold;
	private JPanel jpMenu3;
	private JPanel jpMenu4;
	private JButton btnBitplane;
	private JLabel lblMenu3Title;
	private JLabel lblMenu4Title;
	private JButton btnBlurring3x3;
	private JButton btnBlurring5x5;
	private JButton btnSharpening;
	private JButton btnMeanFiltering;
	private JButton btnMedianfiltering;
	private JButton btnGaussiansmoothing;
	private JPanel jpMenu5;
	private JLabel lblMenu5Title;
	private JButton btnSobelmasking;
	private JButton btnLaplacian;
	private JPanel jpMenu6;
	private JLabel lblMenu6Title;
	private JButton btnOpening;
	private JButton btnClosing;
	private JPanel jpMenu7;
	private JLabel lblMeny7Title;
	private JButton btnExpansion;
	private JButton btnReduction;
	private JButton btnExpansionLI;
	private JButton btnReductionLI;
	private JButton btnRotation;
	private JButton btnExit;
	
	public PhotoshopUI() {
		initComponents();
		setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
	}
	
	private void initComponents() {
		setPreferredSize(new Dimension(1000, 500));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Photoshop");
		pack();
		
		jpTopmenu = new JPanel();
		getContentPane().add(jpTopmenu, BorderLayout.NORTH);
		jpTopmenu.setLayout(new BoxLayout(jpTopmenu, BoxLayout.Y_AXIS));
		
		jpMenu1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) jpMenu1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		jpTopmenu.add(jpMenu1);
		
		btnOpen = new JButton("Open");
		jpMenu1.add(btnOpen);
		
		btnSave = new JButton("Save");
		jpMenu1.add(btnSave);
		
		btnSaveas = new JButton("SaveAs");
		jpMenu1.add(btnSaveas);
		
		btnUndo = new JButton("Undo");
		jpMenu1.add(btnUndo);
		
		btnHistogram = new JButton("Histogram");
		btnHistogram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				histogram();
			}
		});
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		jpMenu1.add(btnExit);
		jpMenu1.add(btnHistogram);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				undo();
			}
		});
		btnSaveas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileOpen();
			}
		});
		
		jpMenu2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) jpMenu2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		jpTopmenu.add(jpMenu2);
		
		lblMenu2Title = new JLabel("\uD654\uC18C \uAC12 \uAE30\uBC18 \uCC98\uB9AC -");
		jpMenu2.add(lblMenu2Title);
		
		btnGrayscale = new JButton("Grayscale");
		jpMenu2.add(btnGrayscale);
		btnGrayscale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				grayscale();
			}
		});
		
		btnVerticalReverse = new JButton("VerticalReverse");
		jpMenu2.add(btnVerticalReverse);
		
		btnHorizontalReverse = new JButton("HorizontalReverse");
		jpMenu2.add(btnHorizontalReverse);
		
		btnEqualization = new JButton("Equalization");
		btnEqualization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				equalization();
			}
		});
		jpMenu2.add(btnEqualization);
		
		btnStretching = new JButton("Stretching");
		btnStretching.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stretching();
			}
		});
		jpMenu2.add(btnStretching);
		
		btnNegative = new JButton("Negative");
		btnNegative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				negative();
			}
		});
		jpMenu2.add(btnNegative);
		
		btnThreshold = new JButton("Threshold");
		btnThreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thresholding();
			}
		});
		jpMenu2.add(btnThreshold);
		
		jpMenu3 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) jpMenu3.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		jpTopmenu.add(jpMenu3);
		
		lblMenu3Title = new JLabel("\uBE44\uD2B8 \uD50C\uB808\uC778 \uAE30\uBC18 \uCC98\uB9AC - ");
		jpMenu3.add(lblMenu3Title);
		
		btnBitplane = new JButton("Bitplane");
		btnBitplane.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bitplane();
			}
		});
		jpMenu3.add(btnBitplane);
		
		jpMenu4 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) jpMenu4.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		jpTopmenu.add(jpMenu4);
		
		lblMenu4Title = new JLabel("\uACF5\uAC04 \uAE30\uBC18 \uCC98\uB9AC - ");
		jpMenu4.add(lblMenu4Title);
		
		btnBlurring3x3 = new JButton("Blurring3x3");
		btnBlurring3x3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				blurring3x3();
			}
		});
		jpMenu4.add(btnBlurring3x3);
		
		btnBlurring5x5 = new JButton("Blurring5x5");
		btnBlurring5x5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blurring5x5();
			}
		});
		jpMenu4.add(btnBlurring5x5);
		
		btnSharpening = new JButton("Sharpening");
		btnSharpening.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sharpening();
			}
		});
		jpMenu4.add(btnSharpening);
		
		btnMeanFiltering = new JButton("MeanFiltering");
		btnMeanFiltering.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meanFiltering();
			}
		});
		jpMenu4.add(btnMeanFiltering);
		
		btnMedianfiltering = new JButton("MedianFiltering");
		btnMedianfiltering.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				medianFiltering();
			}
		});
		jpMenu4.add(btnMedianfiltering);
		
		btnGaussiansmoothing = new JButton("GaussianSmoothing");
		btnGaussiansmoothing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gaussianSmoothing();
			}
		});
		jpMenu4.add(btnGaussiansmoothing);
		
		jpMenu5 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) jpMenu5.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		jpTopmenu.add(jpMenu5);
		
		lblMenu5Title = new JLabel("\uC5D0\uC9C0 \uCD94\uCD9C - ");
		jpMenu5.add(lblMenu5Title);
		
		btnSobelmasking = new JButton("SobelMasking");
		btnSobelmasking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sobelMasking();
			}
		});
		jpMenu5.add(btnSobelmasking);
		
		btnLaplacian = new JButton("Laplacian");
		btnLaplacian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				laplacian();
			}
		});
		jpMenu5.add(btnLaplacian);
		
		jpMenu6 = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) jpMenu6.getLayout();
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		jpTopmenu.add(jpMenu6);
		
		lblMenu6Title = new JLabel("\uBAA8\uD3F4\uB85C\uC9C0 - ");
		jpMenu6.add(lblMenu6Title);
		
		btnOpening = new JButton("Opening");
		btnOpening.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				morphologyOpening();
			}
		});
		jpMenu6.add(btnOpening);
		
		btnClosing = new JButton("Closing");
		btnClosing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				morphologyClosing();
			}
		});
		jpMenu6.add(btnClosing);
		
		jpMenu7 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) jpMenu7.getLayout();
		flowLayout_6.setAlignment(FlowLayout.LEFT);
		jpTopmenu.add(jpMenu7);
		
		lblMeny7Title = new JLabel("\uAE30\uD558\uD559\uC801 \uCC98\uB9AC - ");
		jpMenu7.add(lblMeny7Title);
		
		btnExpansion = new JButton("Expansion");
		btnExpansion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expansion();
			}
		});
		jpMenu7.add(btnExpansion);
		
		btnReduction = new JButton("Reduction");
		btnReduction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reduction();
			}
		});
		jpMenu7.add(btnReduction);
		
		btnExpansionLI = new JButton("Expansion(Interpolation)");
		btnExpansionLI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expensionLinearInterpolation();
			}
		});
		jpMenu7.add(btnExpansionLI);
		
		btnReductionLI = new JButton("Reduction(Interpolation)");
		btnReductionLI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redurctionLinearInterpolation();
			}
		});
		jpMenu7.add(btnReductionLI);
		
		btnRotation = new JButton("Rotation");
		btnRotation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rotation();
			}
		});
		jpMenu7.add(btnRotation);
		btnHorizontalReverse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				horizontalReverse();
			}
		});
		btnVerticalReverse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verticalReverse();
			}
		});
		
		spImageWrapper = new JScrollPane();
		getContentPane().add(spImageWrapper, BorderLayout.CENTER);
		
		lblImageScreen = new JLabel("");
		lblImageScreen.setHorizontalAlignment(SwingConstants.CENTER);
		spImageWrapper.setViewportView(lblImageScreen);
	}

	/**
	 * 파일 열기
	 */
	private void fileOpen(){
		
		boolean success = false;
		
		// fileChooser로 파일 선택
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		chooser.setFileFilter(new FileNameExtensionFilter("이미지파일", "png", "jpg"));
		int result = chooser.showOpenDialog(this);
		if(result == JFileChooser.APPROVE_OPTION){
			success = this.prg.fileOpen(chooser.getSelectedFile());
		}
		
		// 성공적으로 열었으면 이미지 출력 및 파일정보 저장
		if(success){
			curFile = chooser.getSelectedFile();	// 파일 저장
			printImage();							// 출력
		}
	}
	
	/**
	 * 되돌리기
	 */
	private void undo(){
		
		prg.undo();
		printImage();
	}
	
	/**
	 * 종료
	 */
	private void exit(){
		
		this.dispose();
	}

	/**
	 * 히스토그램 보기
	 */
	private void histogram(){
		
		BufferedImage histogramImage = prg.getHistogramImage();
		if(histogramImage != null){
			JLabel histogramImg = new JLabel();
			histogramImg.setIcon(new ImageIcon(histogramImage));
			JOptionPane.showMessageDialog(null, histogramImg, "Histogram", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	/**
	 * 4-i. 흑색조 변환
	 */
	private void grayscale(){
		
		prg.RGBtoGray();
		printImage();
	}
	
	/**
	 * 4-ii. 좌우 반전
	 */
	private void horizontalReverse(){
		
		prg.horizontalReverse();
		printImage();
	}
	
	/**
	 * 4-iii. 상하 반전
	 */
	private void verticalReverse(){
		
		prg.verticalReverse();
		printImage();
	}
	
	/**
	 * 4-iv. 평준화
	 */
	private void equalization(){
		
		prg.histogramEqualization();
		printImage();
	}
	
	/**
	 * 4-v. 스트레칭
	 */
	private void stretching(){
		
		int[] minMax = prg.getBoundaryAutoStretching();
		JTextField width = new JTextField(""+minMax[0], 5);
		JTextField height = new JTextField(""+minMax[1], 5);
		Object[] messages = {
				"최소값 : ", width,
				"최대값 : ", height
		};
		
		int option = JOptionPane.showConfirmDialog(null,
				messages,
				"histogram stretching",
				JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.OK_OPTION){
			int low = Integer.parseInt(width.getText());
			int high = Integer.parseInt(height.getText());
			prg.histogramStretching(low, high);
			printImage();
		}
	}
	
	/**
	 * 4-vii. Negative 영상
	 */
	private void negative(){
		
		prg.negative();
		printImage();
	}
	
	/**
	 * 4-viii. 임계치 필터링
	 */
	private void thresholding(){
		
		String input = JOptionPane.showInputDialog(null,
				"기준점 : ",
				"thresholding",
				JOptionPane.YES_NO_OPTION);
		if(input != null && input.length() > 0){
			int value = Integer.parseInt(input);
			if(value < 0 || value > 255)
				return;
			prg.thresholding(value);
			printImage();
		}
	}
	
	/**
	 * 5-i. 비트 플레인
	 */
	private void bitplane(){
		
		String input = JOptionPane.showInputDialog(null,
				"비트 입력(0~7) : ",
				"bitplane",
				JOptionPane.YES_NO_OPTION);
		if(input != null && input.length() > 0){
			int value = Integer.parseInt(input);
			if(value < 0 || value > 7)
				return;
			prg.bitplane(value);
			printImage();
		}
	}
	
	/**
	 * 6-i. 블러링 3x3
	 */
	private void blurring3x3(){
		
		prg.blurring3x3();
		printImage();
	}
	
	/**
	 * 6-ii. 블러링 5x5
	 */
	private void blurring5x5(){
		
		prg.blurring5x5();
		printImage();
	}
	
	/**
	 * 6-iii. 샤프닝
	 */
	private void sharpening(){
		
		prg.sharpening();
		printImage();
	}
	
	/**
	 * 6-iv. 평균값 필터링
	 */
	private void meanFiltering(){
		
		prg.meanFiltering();
		printImage();
	}
	
	/**
	 * 6-v. 중간값 필터링
	 */
	private void medianFiltering(){
		
		prg.medianFiltering();
		printImage();
	}
	
	/**
	 * 6-vi. 가우시안 Smoothing
	 */
	private void gaussianSmoothing(){
		
		prg.smoothing();
		printImage();
	}
	
	/**
	 * 7-i. 소벨 마스킹
	 */
	private void sobelMasking(){
		
		prg.sobelmasking();
		printImage();
	}
	
	/**
	 * 7-ii. 라플라시안
	 */
	private void laplacian(){
		
		prg.laplacian();
		printImage();
	}
	
	/**
	 * 8-i. 모폴로지 - 열림연산
	 */
	private void morphologyOpening(){
		
		prg.morphologyOpening();
		printImage();
	}
	
	/**
	 * 8-ii. 모폴로지 - 닫힘연산
	 */
	private void morphologyClosing(){
		
		prg.morphologyClosing();
		printImage();
	}
	
	/**
	 * 9-i. 확대
	 */
	private void expansion(){
		
		JTextField width = new JTextField(5);
		JTextField height = new JTextField(5);
		Object[] messages = {
				"너비 비율 : ", width,
				"높이 비율 : ", height
		};
		
		int option = JOptionPane.showConfirmDialog(null,
				messages,
				"expansion",
				JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.OK_OPTION){
			double wd = Double.parseDouble(width.getText());
			double hd = Double.parseDouble(height.getText());
			prg.expansion(wd, hd);
			printImage();
		}
	}
	
	/**
	 * 9-ii. 축소
	 */
	private void reduction(){
		
		JTextField width = new JTextField(5);
		JTextField height = new JTextField(5);
		Object[] messages = {
				"너비 비율 : ", width,
				"높이 비율 : ", height
		};
		
		int option = JOptionPane.showConfirmDialog(null,
				messages,
				"reduction",
				JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.OK_OPTION){
			double wd = Double.parseDouble(width.getText());
			double hd = Double.parseDouble(height.getText());
			prg.reduction(wd, hd);
			printImage();
		}
	}
	
	/**
	 * 9-iii. 확대 - 선형보간
	 */
	private void expensionLinearInterpolation(){
		
		JTextField width = new JTextField(5);
		JTextField height = new JTextField(5);
		Object[] messages = {
				"너비 비율 : ", width,
				"높이 비율 : ", height
		};
		
		int option = JOptionPane.showConfirmDialog(null,
				messages,
				"expension linear interpolation",
				JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.OK_OPTION){
			double wd = Double.parseDouble(width.getText());
			double hd = Double.parseDouble(height.getText());
			prg.expansionLinearInterpolation(wd, hd);
			printImage();
		}
	}
	
	/**
	 * 9-iv. 축소 - 선형보간
	 */
	private void redurctionLinearInterpolation(){
		
		JTextField width = new JTextField(5);
		JTextField height = new JTextField(5);
		Object[] messages = {
				"너비 비율 : ", width,
				"높이 비율 : ", height
		};
		
		int option = JOptionPane.showConfirmDialog(null,
				messages,
				"reduction linear interpolation",
				JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.OK_OPTION){
			double wd = Double.parseDouble(width.getText());
			double hd = Double.parseDouble(height.getText());
			prg.reductionLinearInterpolation(wd, hd);
			printImage();
		}
	}
	
	/**
	 * 9-v. 회전
	 */
	private void rotation(){
		
		String input = JOptionPane.showInputDialog(null,
				"rotation",
				"회전 각도 : ",
				JOptionPane.YES_NO_OPTION);
		if(input != null && input.length() > 0){
			double value = Double.parseDouble(input);
			prg.rotation(value);
			printImage();
		}
	}
	
	/**
	 * 현재 작업중인 이미지를 출력/갱신함
	 */
	private void printImage(){
		
		// 예외처리
		BufferedImage img = this.prg.getBufferedImage();
		if(img == null) return;
		
		// 출력
		ImageIcon icon = new ImageIcon(img);
		this.lblImageScreen.setIcon(icon);
		
		// 컴포넌트 갱신
		revalidate();
		repaint();
	}
	
	/**
	 * 저장
	 */
	private void save(){
		
		boolean success = fileSave(curFile);
		if(!success){
			JOptionPane.showMessageDialog(this, "저장 실패!");
		}
	}
	
	/**
	 * 다른 이름으로 저장
	 */
	private void saveAs(){
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("다른 이름으로 저장...");
		
		// 저장 확인시
		int userSelection = chooser.showSaveDialog(this);
		if(userSelection == JFileChooser.APPROVE_OPTION){
			File f = chooser.getSelectedFile();
			chooser.setFileFilter(new FileNameExtensionFilter("이미지파일", "png", "jpg", "gif"));
			
			boolean success = fileSave(f);
			if(!success){
				JOptionPane.showConfirmDialog(this, "저장 실패!");
			}
		}
	}
	
	/**
	 * 파일 저장
	 * @param f	파일
	 * @return 저장 성공여부
	 */
	private boolean fileSave(File f){
		
		BufferedImage img = prg.getBufferedImage();
		
		if(img != null){
			try {
				ImageIO.write(img, "png", f);	// 파일에 이미지 씀
				return true;					// 성공
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;	// 실패
	}
}
