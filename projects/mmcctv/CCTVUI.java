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
		// 컴포넌트 초기화
		initComponents();
		
		// 클라이언트 초기화
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
		
		// 아이피 획득
		getMyIP();
		
		// UI 초기화
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
					int itemIdx = listCam.locationToIndex(e.getPoint());		// 아이템 인덱스
					if(itemIdx >= 0){
						String itemVal = listCam.getModel().getElementAt(itemIdx);	// 아이템 값
						int camIdx = getIntegerFromString(itemVal);					// CAM 구분자 획득
						addCamLabel(camIdx);	// CAM 레이블 추가
					}
				}
			}
		});
		
		pCamListWrapper.add(listCam);
	}
	
	/**
	 * CamLabel 제거
	 * @param cil	Cam 레이블
	 */
	public void removeCamLabel(CamImageLabel cil){
		
		camLables.remove(cil);
		pImageInner.remove(cil);
		validate();
		repaint();
	}
	
	/**
	 * CAM 레이블 추가
	 * @param camIdx
	 */
	private void addCamLabel(int camIdx){
		
		CamImageLabel lbl = new CamImageLabel(this, camIdx);
		camLables.add(lbl);
		pImageInner.add(lbl);
	}
	
	/**
	 * CAM 목록을 업데이트 합니다
	 * @param datas	CAM 데이터 배열
	 */
	private void updateList(ArrayList<CamData> datas){
		
		// 이전의 목록 지움
		listCam.removeAll();
		
		// 새 목록 구성
		for(int i=0; i<datas.size(); i++){
			model.add(i, "CAM - "+datas.get(i).camIdx);
		}
	}
	
	/**
	 * 영상 업데이트
	 * @param d	CAM 데이터
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
	 * 서버 열기
	 */
	private void hostServer(){
		
		server = new CCTVServer();
		server.addCam(0);
		getMyIP();
		connectToServer();
	}

	/**
	 * 서버에 접속
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
	 * 내 아이피를 받아서 텍스트필드에 저장합니다
	 */
	private void getMyIP(){
		
		String ip = "127.0.0.1";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException ignored) { }
		tfServerIP.setText(ip);
	}
	
	/**
	 * 문자열에서 숫자를 추출합니다
	 * @param str	문자열
	 * @return		숫자
	 */
	private int getIntegerFromString(String str){
		
		Pattern intsOnly = Pattern.compile("\\d+");			// 숫자를 추출할 정규식
		Matcher makeMatch = intsOnly.matcher(str);			// 문자열 설정
		makeMatch.find();									// 찾기
		int integer = Integer.parseInt(makeMatch.group());	// 숫자 획득
		return integer;
	}
}
