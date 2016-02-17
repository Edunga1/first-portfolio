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
			// 이름
			JLabel nameLabel = new JLabel(String.format("[%d] %s", ++i, u.getName()));
			panel.add(nameLabel);
			// 문자내용
			JLabel msgLabel = new JLabel("<no message>");
			if(u.getMessages().size() > 0)
				msgLabel.setText(u.getMessages().get(u.getMessages().size()-1));
			panel.add(msgLabel);
			// 기능
			JButton btnAccount = new JButton(String.format("%s's 계좌개설", u.getName()));
			btnAccount.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					newAccount(u);
				}
			});
			panel.add(btnAccount);
			// 계좌
			int j = 0;
			for(Account a : u.getAccounts()){
				panel.add(new JLabel(String.format("[%d] %s", ++j, a.getOpeningDay())));
				panel.add(new JLabel("-     잔액: " + a.getBalance()));
				panel.add(new JLabel(String.format("- 이자율: %5.3f", a.getInterest())));
				// 계좌 기능
				JButton btnDeposit = new JButton("입금");
				btnDeposit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						deposit(a);
					}
				});
				panel.add(btnDeposit);
				JButton btnWithdraw = new JButton("출금");
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
	 * 컨트롤 생성 및 초기화
	 */
	private void initialize() {
		
		frame = new JFrame("Bank System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(700, 500));
	
		// 은행 패널 구성
		bankPanel = new JPanel();
		btnAddUser = new JButton("고객 등록");
		btnAddUser.addActionListener(this);
		bankPanel.add(btnAddUser);
		btnMessage = new JButton("모든 고객에게 문자 전송");
		btnMessage.addActionListener(this);
		bankPanel.add(btnMessage);
		btnRefresh = new JButton("고객목록 갱신");
		btnRefresh.addActionListener(this);
		bankPanel.add(btnRefresh);
		frame.getContentPane().add(bankPanel, BorderLayout.NORTH);
		
		// 고객 패널 구성
		usersPanel = new JPanel();
		usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.X_AXIS));
		usersPanel.add(new JLabel("<no customers>"));
		frame.add(usersPanel, BorderLayout.CENTER);
		
		// 안내 패널 구성
		JLabel notice1 = new JLabel("** 1인당 계좌는 3개 생성 가능합니다.");
		JLabel notice2 = new JLabel("** 생성된 계좌는 '일반계좌'입니다.");
		noticePanel = new JPanel();
		noticePanel.setLayout(new BoxLayout(noticePanel, BoxLayout.Y_AXIS));;
		noticePanel.add(notice1);
		noticePanel.add(notice2);
		frame.add(noticePanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
	
	/**
	 * 사용자를 등록한다.
	 */
	private void addUser() {
		String name = JOptionPane.showInputDialog(frame, "고객명?");
		if(name != null && name.length() > 0)
			controller.registCustomer(name);
	}
	
	/**
	 * 모든 사용자에 문자를 전송한다.
	 */
	private void sendMessage() {
		String text = JOptionPane.showInputDialog(frame, "문자 내용?");
		if(text != null && text.length() > 0)
			controller.sendMessage("From Bank : " + text);
	}
	
	/**
	 * 입금
	 * @param a
	 */
	private void deposit(Account a) {
		String amount = JOptionPane.showInputDialog(frame, "입금액?");
		if(amount != null && amount.length() > 0){
			controller.deposit(a, Integer.parseInt(amount));
		}
	}
	
	/**
	 * 출금
	 * @param a
	 */
	private void withdraw(Account a) {
		String amount = JOptionPane.showInputDialog(frame, "출금액?");
		if(amount != null && amount.length() > 0){
			controller.withdraw(a, Integer.parseInt(amount));
		}
	}
	
	/**
	 * 계좌를 개설한다.
	 * @param u
	 */
	private void newAccount(User u) {
		controller.newAccount(u);
	}
}
