package dp.mvc.bank;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dp.strategy.account.Account;

public class SystemView implements UserObserver, ActionListener {
	private BankController controller;
	private BankDatabase model;
	
	// controls
	private JFrame frame;
	private JPanel bankPanel;
	private JPanel usersPanel;
	private JPanel noticePanel;
	private JButton btnAddUser;
	private JButton btnMessage;
	private JButton btnRefresh;

	public SystemView(BankController controller, BankDatabase model) {
		this.controller = controller;
		this.model = model;
		
		model.registUserObserver(this);
		
		initialize();
	}
	
	@Override
	public void updateUser() {
		usersPanel.removeAll();
		int i = 0;
		for(User u : model.getCustomers()){
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setAlignmentY(Component.TOP_ALIGNMENT);
			panel.add(new JLabel("                                                    "));
			// �̸�
			JLabel nameLabel = new JLabel(String.format("[%d] %s", ++i, u.getName()));
			panel.add(nameLabel);
			// ���ڳ���
			JLabel msgLabel = new JLabel("<no message>");
			if(u.getMessages().size() > 0)
				msgLabel.setText(u.getMessages().get(u.getMessages().size()-1));
			panel.add(msgLabel);
			// ���
			JButton btnAccount = new JButton(String.format("%s's ���°���", u.getName()));
			btnAccount.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					newAccount(u);
				}
			});
			panel.add(btnAccount);
			// ����
			int j = 0;
			for(Account a : u.getAccounts()){
				panel.add(new JLabel(String.format("[%d] %s", ++j, a.getOpeningDay())));
				panel.add(new JLabel("-     �ܾ�: " + a.getBalance()));
				panel.add(new JLabel(String.format("- ������: %5.3f", a.getInterest())));
				// ���� ���
				JButton btnDeposit = new JButton("�Ա�");
				btnDeposit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						deposit(a);
					}
				});
				panel.add(btnDeposit);
				JButton btnWithdraw = new JButton("���");
				btnWithdraw.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						withdraw(a);
					}
				});
				panel.add(btnWithdraw);
			}
			usersPanel.add(panel);
		}
		SwingUtilities.updateComponentTreeUI(frame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAddUser)
			addUser();
		else if(e.getSource() == btnMessage)
			sendMessage();
		else if(e.getSource() == btnRefresh)
			updateUser();
	}

	/**
	 * ��Ʈ�� ���� �� �ʱ�ȭ
	 */
	private void initialize() {
		
		frame = new JFrame("Bank System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(700, 500));
	
		// ���� �г� ����
		bankPanel = new JPanel();
		btnAddUser = new JButton("�� ���");
		btnAddUser.addActionListener(this);
		bankPanel.add(btnAddUser);
		btnMessage = new JButton("��� ������ ���� ����");
		btnMessage.addActionListener(this);
		bankPanel.add(btnMessage);
		btnRefresh = new JButton("����� ����");
		btnRefresh.addActionListener(this);
		bankPanel.add(btnRefresh);
		frame.getContentPane().add(bankPanel, BorderLayout.NORTH);
		
		// �� �г� ����
		usersPanel = new JPanel();
		usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.X_AXIS));
		usersPanel.add(new JLabel("<no customers>"));
		frame.add(usersPanel, BorderLayout.CENTER);
		
		// �ȳ� �г� ����
		JLabel notice1 = new JLabel("** 1�δ� ���´� 3�� ���� �����մϴ�.");
		JLabel notice2 = new JLabel("** ������ ���´� '�Ϲݰ���'�Դϴ�.");
		noticePanel = new JPanel();
		noticePanel.setLayout(new BoxLayout(noticePanel, BoxLayout.Y_AXIS));;
		noticePanel.add(notice1);
		noticePanel.add(notice2);
		frame.add(noticePanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
	
	/**
	 * ����ڸ� ����Ѵ�.
	 */
	private void addUser() {
		String name = JOptionPane.showInputDialog(frame, "����?");
		if(name != null && name.length() > 0)
			controller.registCustomer(name);
	}
	
	/**
	 * ��� ����ڿ� ���ڸ� �����Ѵ�.
	 */
	private void sendMessage() {
		String text = JOptionPane.showInputDialog(frame, "���� ����?");
		if(text != null && text.length() > 0)
			controller.sendMessage("From Bank : " + text);
	}
	
	/**
	 * �Ա�
	 * @param a
	 */
	private void deposit(Account a) {
		String amount = JOptionPane.showInputDialog(frame, "�Աݾ�?");
		if(amount != null && amount.length() > 0){
			controller.deposit(a, Integer.parseInt(amount));
		}
	}
	
	/**
	 * ���
	 * @param a
	 */
	private void withdraw(Account a) {
		String amount = JOptionPane.showInputDialog(frame, "��ݾ�?");
		if(amount != null && amount.length() > 0){
			controller.withdraw(a, Integer.parseInt(amount));
		}
	}
	
	/**
	 * ���¸� �����Ѵ�.
	 * @param u
	 */
	private void newAccount(User u) {
		controller.newAccount(u);
	}
}
