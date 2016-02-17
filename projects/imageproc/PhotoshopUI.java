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
	 * ���� ����
	 */
	private void fileOpen(){
		
		boolean success = false;
		
		// fileChooser�� ���� ����
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		chooser.setFileFilter(new FileNameExtensionFilter("�̹�������", "png", "jpg"));
		int result = chooser.showOpenDialog(this);
		if(result == JFileChooser.APPROVE_OPTION){
			success = this.prg.fileOpen(chooser.getSelectedFile());
		}
		
		// ���������� �������� �̹��� ��� �� �������� ����
		if(success){
			curFile = chooser.getSelectedFile();	// ���� ����
			printImage();							// ���
		}
	}
	
	/**
	 * �ǵ�����
	 */
	private void undo(){
		
		prg.undo();
		printImage();
	}
	
	/**
	 * ����
	 */
	private void exit(){
		
		this.dispose();
	}

	/**
	 * ������׷� ����
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
	 * 4-i. ����� ��ȯ
	 */
	private void grayscale(){
		
		prg.RGBtoGray();
		printImage();
	}
	
	/**
	 * 4-ii. �¿� ����
	 */
	private void horizontalReverse(){
		
		prg.horizontalReverse();
		printImage();
	}
	
	/**
	 * 4-iii. ���� ����
	 */
	private void verticalReverse(){
		
		prg.verticalReverse();
		printImage();
	}
	
	/**
	 * 4-iv. ����ȭ
	 */
	private void equalization(){
		
		prg.histogramEqualization();
		printImage();
	}
	
	/**
	 * 4-v. ��Ʈ��Ī
	 */
	private void stretching(){
		
		int[] minMax = prg.getBoundaryAutoStretching();
		JTextField width = new JTextField(""+minMax[0], 5);
		JTextField height = new JTextField(""+minMax[1], 5);
		Object[] messages = {
				"�ּҰ� : ", width,
				"�ִ밪 : ", height
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
	 * 4-vii. Negative ����
	 */
	private void negative(){
		
		prg.negative();
		printImage();
	}
	
	/**
	 * 4-viii. �Ӱ�ġ ���͸�
	 */
	private void thresholding(){
		
		String input = JOptionPane.showInputDialog(null,
				"������ : ",
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
	 * 5-i. ��Ʈ �÷���
	 */
	private void bitplane(){
		
		String input = JOptionPane.showInputDialog(null,
				"��Ʈ �Է�(0~7) : ",
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
	 * 6-i. ���� 3x3
	 */
	private void blurring3x3(){
		
		prg.blurring3x3();
		printImage();
	}
	
	/**
	 * 6-ii. ���� 5x5
	 */
	private void blurring5x5(){
		
		prg.blurring5x5();
		printImage();
	}
	
	/**
	 * 6-iii. ������
	 */
	private void sharpening(){
		
		prg.sharpening();
		printImage();
	}
	
	/**
	 * 6-iv. ��հ� ���͸�
	 */
	private void meanFiltering(){
		
		prg.meanFiltering();
		printImage();
	}
	
	/**
	 * 6-v. �߰��� ���͸�
	 */
	private void medianFiltering(){
		
		prg.medianFiltering();
		printImage();
	}
	
	/**
	 * 6-vi. ����þ� Smoothing
	 */
	private void gaussianSmoothing(){
		
		prg.smoothing();
		printImage();
	}
	
	/**
	 * 7-i. �Һ� ����ŷ
	 */
	private void sobelMasking(){
		
		prg.sobelmasking();
		printImage();
	}
	
	/**
	 * 7-ii. ���ö�þ�
	 */
	private void laplacian(){
		
		prg.laplacian();
		printImage();
	}
	
	/**
	 * 8-i. �������� - ��������
	 */
	private void morphologyOpening(){
		
		prg.morphologyOpening();
		printImage();
	}
	
	/**
	 * 8-ii. �������� - ��������
	 */
	private void morphologyClosing(){
		
		prg.morphologyClosing();
		printImage();
	}
	
	/**
	 * 9-i. Ȯ��
	 */
	private void expansion(){
		
		JTextField width = new JTextField(5);
		JTextField height = new JTextField(5);
		Object[] messages = {
				"�ʺ� ���� : ", width,
				"���� ���� : ", height
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
	 * 9-ii. ���
	 */
	private void reduction(){
		
		JTextField width = new JTextField(5);
		JTextField height = new JTextField(5);
		Object[] messages = {
				"�ʺ� ���� : ", width,
				"���� ���� : ", height
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
	 * 9-iii. Ȯ�� - ��������
	 */
	private void expensionLinearInterpolation(){
		
		JTextField width = new JTextField(5);
		JTextField height = new JTextField(5);
		Object[] messages = {
				"�ʺ� ���� : ", width,
				"���� ���� : ", height
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
	 * 9-iv. ��� - ��������
	 */
	private void redurctionLinearInterpolation(){
		
		JTextField width = new JTextField(5);
		JTextField height = new JTextField(5);
		Object[] messages = {
				"�ʺ� ���� : ", width,
				"���� ���� : ", height
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
	 * 9-v. ȸ��
	 */
	private void rotation(){
		
		String input = JOptionPane.showInputDialog(null,
				"rotation",
				"ȸ�� ���� : ",
				JOptionPane.YES_NO_OPTION);
		if(input != null && input.length() > 0){
			double value = Double.parseDouble(input);
			prg.rotation(value);
			printImage();
		}
	}
	
	/**
	 * ���� �۾����� �̹����� ���/������
	 */
	private void printImage(){
		
		// ����ó��
		BufferedImage img = this.prg.getBufferedImage();
		if(img == null) return;
		
		// ���
		ImageIcon icon = new ImageIcon(img);
		this.lblImageScreen.setIcon(icon);
		
		// ������Ʈ ����
		revalidate();
		repaint();
	}
	
	/**
	 * ����
	 */
	private void save(){
		
		boolean success = fileSave(curFile);
		if(!success){
			JOptionPane.showMessageDialog(this, "���� ����!");
		}
	}
	
	/**
	 * �ٸ� �̸����� ����
	 */
	private void saveAs(){
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("�ٸ� �̸����� ����...");
		
		// ���� Ȯ�ν�
		int userSelection = chooser.showSaveDialog(this);
		if(userSelection == JFileChooser.APPROVE_OPTION){
			File f = chooser.getSelectedFile();
			chooser.setFileFilter(new FileNameExtensionFilter("�̹�������", "png", "jpg", "gif"));
			
			boolean success = fileSave(f);
			if(!success){
				JOptionPane.showConfirmDialog(this, "���� ����!");
			}
		}
	}
	
	/**
	 * ���� ����
	 * @param f	����
	 * @return ���� ��������
	 */
	private boolean fileSave(File f){
		
		BufferedImage img = prg.getBufferedImage();
		
		if(img != null){
			try {
				ImageIO.write(img, "png", f);	// ���Ͽ� �̹��� ��
				return true;					// ����
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;	// ����
	}
}
