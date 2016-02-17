import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CCTVUI extends JFrame {
	private static final long serialVersionUID = -6970302563071986867L;
	
	private CCTVClient client;
	private CCTVServer server;
	private List<CamImageLabel> camLables = Collections.synchronizedList(new ArrayList<CamImageLabel>());
	private final JPanel pTopMenu = new JPanel();
	private final JPanel pTopSub1 = new JPanel();
	private final JTextField tfServerIP = new JTextField();
	private final JLabel lblServerip = new JLabel("\uC11C\uBC84 \uC544\uC774\uD53C : ");
	private final JButton btnConnect = new JButton("\uC811\uC18D");
	private final JPanel pImageInner = new JPanel();
	private final JPanel pCamListWrapper = new JPanel();
	private final DefaultListModel<String> model = new DefaultListModel<String>();
	private final JList<String> listCam = new JList<String>(model);
	private final JButton btnHost = new JButton("\uC11C\uBC84\uC5F4\uAE30");
	
	public CCTVUI() {
		// ������Ʈ �ʱ�ȭ
		initComponents();
		
		// Ŭ���̾�Ʈ �ʱ�ȭ
		client = new CCTVClient() {
			
			@Override
			public void receiveCamImage(CamData d) {
				updateCam(d);
			}
			
			@Override
			public void receiveNewCam(ArrayList<CamData> d) {
				updateList(d);
			}
		};
		
		// ������ ȹ��
		getMyIP();
		
		// UI �ʱ�ȭ
		setSize(new Dimension(820, 570));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void initComponents() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		getContentPane().add(pTopMenu, BorderLayout.NORTH);
		pTopMenu.setLayout(new BoxLayout(pTopMenu, BoxLayout.Y_AXIS));
		FlowLayout flowLayout = (FlowLayout) pTopSub1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		pTopSub1.setBackground(Color.GRAY);
		
		pTopMenu.add(pTopSub1);
		lblServerip.setForeground(Color.WHITE);
		
		pTopSub1.add(lblServerip);

		tfServerIP.setColumns(10);
		pTopSub1.add(tfServerIP);
		btnConnect.setBackground(Color.WHITE);
		btnConnect.setForeground(Color.BLACK);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connectToServer();
			}
		});
		btnHost.setBackground(Color.WHITE);
		btnHost.setForeground(Color.BLACK);
		btnHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hostServer();
			}
		});
		
		pTopSub1.add(btnHost);
		
		pTopSub1.add(btnConnect);
		getContentPane().add(pImageInner, BorderLayout.CENTER);
		pImageInner.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		pCamListWrapper.setPreferredSize(new Dimension(150, 10));
		
		getContentPane().add(pCamListWrapper, BorderLayout.WEST);
		pCamListWrapper.setLayout(new BorderLayout(0, 0));
		listCam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					int itemIdx = listCam.locationToIndex(e.getPoint());		// ������ �ε���
					if(itemIdx >= 0){
						String itemVal = listCam.getModel().getElementAt(itemIdx);	// ������ ��
						int camIdx = getIntegerFromString(itemVal);					// CAM ������ ȹ��
						addCamLabel(camIdx);	// CAM ���̺� �߰�
					}
				}
			}
		});
		
		pCamListWrapper.add(listCam);
	}
	
	/**
	 * CamLabel ����
	 * @param cil	Cam ���̺�
	 */
	public void removeCamLabel(CamImageLabel cil){
		
		camLables.remove(cil);
		pImageInner.remove(cil);
		validate();
		repaint();
	}
	
	/**
	 * CAM ���̺� �߰�
	 * @param camIdx
	 */
	private void addCamLabel(int camIdx){
		
		CamImageLabel lbl = new CamImageLabel(this, camIdx);
		camLables.add(lbl);
		pImageInner.add(lbl);
	}
	
	/**
	 * CAM ����� ������Ʈ �մϴ�
	 * @param datas	CAM ������ �迭
	 */
	private void updateList(ArrayList<CamData> datas){
		
		// ������ ��� ����
		listCam.removeAll();
		
		// �� ��� ����
		for(int i=0; i<datas.size(); i++){
			model.add(i, "CAM - "+datas.get(i).camIdx);
		}
	}
	
	/**
	 * ���� ������Ʈ
	 * @param d	CAM ������
	 */
	private void updateCam(CamData d){
		
		synchronized (camLables) {
			ListIterator<CamImageLabel> it = camLables.listIterator();
			while(it.hasNext()){
				CamImageLabel lbl = it.next();
				if(lbl.isSameCam(d.camIdx)){
					lbl.setCamImage(d.getImg());
				}
			}
		}
		
	}
	
	/**
	 * ���� ����
	 */
	private void hostServer(){
		
		server = new CCTVServer();
		server.addCam(0);
		getMyIP();
		connectToServer();
	}

	/**
	 * ������ ����
	 */
	private void connectToServer(){
		
		for(int i=0; i<pTopSub1.getComponentCount(); i++){
			Component c = pTopSub1.getComponent(i);
			c.setEnabled(false);
		}
		String ip = tfServerIP.getText();
		client.connect(ip);
	}
	
	/**
	 * �� �����Ǹ� �޾Ƽ� �ؽ�Ʈ�ʵ忡 �����մϴ�
	 */
	private void getMyIP(){
		
		String ip = "127.0.0.1";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException ignored) { }
		tfServerIP.setText(ip);
	}
	
	/**
	 * ���ڿ����� ���ڸ� �����մϴ�
	 * @param str	���ڿ�
	 * @return		����
	 */
	private int getIntegerFromString(String str){
		
		Pattern intsOnly = Pattern.compile("\\d+");			// ���ڸ� ������ ���Խ�
		Matcher makeMatch = intsOnly.matcher(str);			// ���ڿ� ����
		makeMatch.find();									// ã��
		int integer = Integer.parseInt(makeMatch.group());	// ���� ȹ��
		return integer;
	}
}
