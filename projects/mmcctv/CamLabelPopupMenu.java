import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public abstract class CamLabelPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 3226759089765769103L;
	
	JCheckBoxMenuItem cmiTracking;
	JCheckBoxMenuItem cmiAlarm;
	JCheckBoxMenuItem cmiRecord;
	JMenuItem miRemove;
	
	public CamLabelPopupMenu() {
		
		// 추적
		cmiTracking = new JCheckBoxMenuItem("물체 추적");
		cmiTracking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickedTracking();
			}
		});
		add(cmiTracking);
		
		// 알람
		cmiAlarm = new JCheckBoxMenuItem("감지시 알람");
		cmiAlarm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickedAlarm();
			}
		});
		add(cmiAlarm);
		
		// 녹화
		cmiRecord = new JCheckBoxMenuItem("감지시 녹화");
		cmiRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clickedRecord();
			}
		});
		add(cmiRecord);
		
		// 제거
		miRemove = new JMenuItem("제거");
		miRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clickedRemove();
			}
		});
		add(miRemove);
	}
	
	/**
	 * 추적 메뉴를 선택하면 이 함수를 호출합니다
	 */
	public abstract void clickedTracking();
	
	/**
	 * 알람 메뉴를 선택하면 이 함수를 호출합니다
	 */
	public abstract void clickedAlarm();
	
	/**
	 * 녹화 메뉴를 선택하면 이 함수를 호출합니다
	 */
	public abstract void clickedRecord();
	
	/**
	 * 제거 메뉴를 선택하면 이 함수를 호출합니다
	 */
	public abstract void clickedRemove();
}
