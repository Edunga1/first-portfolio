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
		
		// ����
		cmiTracking = new JCheckBoxMenuItem("��ü ����");
		cmiTracking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickedTracking();
			}
		});
		add(cmiTracking);
		
		// �˶�
		cmiAlarm = new JCheckBoxMenuItem("������ �˶�");
		cmiAlarm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickedAlarm();
			}
		});
		add(cmiAlarm);
		
		// ��ȭ
		cmiRecord = new JCheckBoxMenuItem("������ ��ȭ");
		cmiRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clickedRecord();
			}
		});
		add(cmiRecord);
		
		// ����
		miRemove = new JMenuItem("����");
		miRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clickedRemove();
			}
		});
		add(miRemove);
	}
	
	/**
	 * ���� �޴��� �����ϸ� �� �Լ��� ȣ���մϴ�
	 */
	public abstract void clickedTracking();
	
	/**
	 * �˶� �޴��� �����ϸ� �� �Լ��� ȣ���մϴ�
	 */
	public abstract void clickedAlarm();
	
	/**
	 * ��ȭ �޴��� �����ϸ� �� �Լ��� ȣ���մϴ�
	 */
	public abstract void clickedRecord();
	
	/**
	 * ���� �޴��� �����ϸ� �� �Լ��� ȣ���մϴ�
	 */
	public abstract void clickedRemove();
}
