package com.synergy.exos.uxinterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import com.synergy.exos.entity.Users;
import com.synergy.exos.hdbc.UsersDB;
import com.synergy.exos.hdbc.extrafetch.UsersFetch;
import com.synergy.exos.uxinterface.filters.RoundImageFilter;
import com.synergy.exos.uxinterface.listeners.CursorListener;
import com.synergy.exos.uxinterface.listeners.DragListener;
import com.synergy.exos.uxinterface.listeners.SeperatorListener;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;

import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class LoginPanel extends JFrame {

	/**
	 * @author G-Kesh
	 */
	private Users usr = null;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_username;
	private JPasswordField txt_password;
	private JLabel lblForgotPassword, lblX, lblicon, lblLibrarayManagementSystem, lblNotAlreadyA, lblSignInIcon,
			lblEmailNotification, lbl_login_icon;
	private final JLabel lblSignIn, lblUsername, lblpassword;
	private final JSeparator separator, separator_1;
	private static Thread system = new Thread(new Runnable() {
		@Override
		public void run() {
			// Launch The System
			EventQueue.invokeLater(() -> {
				try {
					LoginPanel frame = new LoginPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}), expiryNotification = new Thread(new Runnable() {

		@Override
		public void run() {
			// Check and send late notification
			UsersDB.notifyReturnDate();
		}
		
	});

	/**
	 * Launch the application.
	 */
	public static void loginPanel() {
		system.start();
		expiryNotification.start();
	}

	/**
	 * Create the frame.
	 */
	public LoginPanel() {
		setBounds(100, 100, 1054, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);

		DragListener dragListener = new DragListener(this);
		addMouseListener(dragListener);
		addMouseMotionListener(dragListener);
		setLocationRelativeTo(null);

		JPanel panel_overlay = new JPanel();
		panel_overlay.setBackground(new Color(0, 102, 153, 100));
		panel_overlay.setBounds(0, 0, 571, 553);
		contentPane.add(panel_overlay);
		panel_overlay.setLayout(null);

		JLabel lblExos = new JLabel("exOs");
		lblExos.setFont(new Font("Century Gothic", Font.PLAIN, 60));
		lblExos.setForeground(SystemColor.inactiveCaption);
		lblExos.setBounds(204, 280, 164, 60);
		panel_overlay.add(lblExos);

		lblicon = new JLabel("");
		lblicon.setIcon(new ImageIcon(LoginPanel.class.getResource("/com/synergy/exos/uxinterface/images/logo.png")));
		lblicon.setBounds(152, 13, 297, 257);
		panel_overlay.add(lblicon);

		lblLibrarayManagementSystem = new JLabel("Libraray Management System");
		lblLibrarayManagementSystem.setForeground(new Color(191, 205, 219));
		lblLibrarayManagementSystem.setFont(new Font("Century Gothic", Font.PLAIN, 27));
		lblLibrarayManagementSystem.setBounds(93, 324, 392, 71);
		panel_overlay.add(lblLibrarayManagementSystem);

		lblNotAlreadyA = new JLabel("Not already a member?");
		lblNotAlreadyA.setForeground(SystemColor.inactiveCaption);
		lblNotAlreadyA.setFont(new Font("Century Gothic", Font.PLAIN, 19));
		lblNotAlreadyA.setBounds(172, 419, 222, 33);
		panel_overlay.add(lblNotAlreadyA);

		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setBackground(new Color(150, 180, 209));
		btnSignUp.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		btnSignUp.setForeground(SystemColor.inactiveCaptionBorder);
		btnSignUp.setBounds(172, 465, 215, 41);
		panel_overlay.add(btnSignUp);

		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				dispose();
				SignupPanel.signupPanel();
			}
		});
		btnSignUp.addMouseListener(new CursorListener(btnSignUp));
		JPanel panel_signin = new JPanel();
		panel_signin.setBackground(new Color(150, 180, 209));
		panel_signin.setBounds(570, 0, 483, 553);
		contentPane.add(panel_signin);
		panel_signin.setLayout(null);

		lblUsername = new JLabel("Username");
		lblUsername.setBackground(SystemColor.activeCaption);
		lblUsername.setForeground(SystemColor.inactiveCaptionBorder);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername.setBounds(71, 135, 73, 27);
		panel_signin.add(lblUsername);

		txt_username = new JTextField();
		txt_username.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txt_username.setForeground(SystemColor.inactiveCaptionBorder);
		txt_username.setBackground(SystemColor.activeCaption);
		txt_username.setBounds(71, 175, 343, 27);
		panel_signin.add(txt_username);
		txt_username.setColumns(10);
		txt_username.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txt_username.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent evt) {
				lblUsername.setForeground(SystemColor.inactiveCaptionBorder);
			}

			public void focusLost(FocusEvent evt) {
				lblUsername.setForeground(SystemColor.inactiveCaption);
			}
		});
		if (usr != null) {
			txt_username.setText(usr.getUsername());
		}
		separator = new JSeparator();
		separator.setForeground(SystemColor.inactiveCaptionBorder);
		separator.setBounds(71, 215, 343, 3);
		panel_signin.add(separator);
		txt_username.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				separator.setForeground(SystemColor.inactiveCaptionBorder);
				separator.setBackground(SystemColor.activeCaption);
			}
		});

		lblpassword = new JLabel("Password");
		lblpassword.setForeground(SystemColor.inactiveCaption);
		lblpassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblpassword.setBackground(SystemColor.activeCaption);
		lblpassword.setBounds(71, 269, 73, 27);
		panel_signin.add(lblpassword);

		txt_password = new JPasswordField();
		txt_password.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txt_password.setForeground(SystemColor.inactiveCaptionBorder);
		txt_password.setBackground(SystemColor.activeCaption);
		txt_password.setBounds(71, 309, 343, 27);
		txt_password.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel_signin.add(txt_password);
		txt_password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent evt) {
				lblpassword.setForeground(SystemColor.inactiveCaptionBorder);
			}

			@Override
			public void focusLost(FocusEvent evt) {
				lblpassword.setForeground(SystemColor.inactiveCaption);
			}
		});
		txt_password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				if (evt.getKeyChar() == KeyEvent.VK_ENTER)
					loginUser();
			}
		});

		separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.inactiveCaptionBorder);
		separator_1.setBounds(71, 349, 343, 3);
		panel_signin.add(separator_1);
		txt_password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				separator_1.setForeground(SystemColor.inactiveCaptionBorder);
				separator_1.setBackground(SystemColor.activeCaption);
			}
		});

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLogin.setBackground(new Color(0, 102, 153));
		btnLogin.setForeground(SystemColor.inactiveCaptionBorder);
		btnLogin.setBounds(71, 400, 343, 48);
		panel_signin.add(btnLogin);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				loginUser();
			}
		});
		btnLogin.addMouseListener(new CursorListener(btnLogin));
		lblForgotPassword = new JLabel("Forgot Password?");
		lblForgotPassword.setForeground(SystemColor.inactiveCaptionBorder);
		lblForgotPassword.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblForgotPassword.setBounds(180, 461, 136, 35);
		Font font = lblForgotPassword.getFont();
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblForgotPassword.setFont(font.deriveFont(attributes));
		panel_signin.add(lblForgotPassword);

		lblForgotPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (!txt_username.getText().equals("")) {
					UsersDB ud = new UsersDB();
					usr = ud.fetch(UsersFetch.FETCH_BY_USERNAME, txt_username.getText()).get(0);
					sendRecoveryMail(usr, usr.getEmail_add());
				}
			}
		});
		lblForgotPassword.addMouseListener(new CursorListener(lblForgotPassword));

		lblSignInIcon = new JLabel();
		lblSignInIcon.setIcon(
				new ImageIcon(LoginPanel.class.getResource("/com/synergy/exos/uxinterface/images/userlogo.png")));
		lblSignInIcon.setBounds(71, 28, 70, 70);
		panel_signin.add(lblSignInIcon);
		lblSignIn = new JLabel("  Sign In");
		lblSignIn.setForeground(new Color(244, 247, 252));
		lblSignIn.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		lblSignIn.setBounds(141, 28, 221, 70);
		panel_signin.add(lblSignIn);

		lblX = new JLabel("");
		lblX.setIcon(
				new ImageIcon(LoginPanel.class.getResource("/com/synergy/exos/uxinterface/images/close_button.png")));
		lblX.setBounds(455, 5, 20, 30);
		panel_signin.add(lblX);

		lbl_login_icon = new JLabel("");
		lbl_login_icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				HomePanel.homePanel(usr);
			}
		});
		lbl_login_icon.setIcon(
				new ImageIcon(LoginPanel.class.getResource("/com/synergy/exos/uxinterface/images/login-icon.png")));
		lbl_login_icon.setBounds(374, 42, 40, 50);
		lbl_login_icon.setVisible(false);
		panel_signin.add(lbl_login_icon);

		lblX.addMouseListener(new CursorListener(lblX) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				System.exit(0);
			}
		});

		lblEmailNotification = new JLabel("Please Check your Email");
		lblEmailNotification.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmailNotification.setBounds(141, 495, 200, 30);
		lblEmailNotification.setVisible(false);
		panel_signin.add(lblEmailNotification);

		JLabel lblbgimage = new JLabel("");
		lblbgimage.setIcon(
				new ImageIcon(LoginPanel.class.getResource("/com/synergy/exos/uxinterface/images/background2.png")));
		lblbgimage.setBounds(-824, -281, 1394, 834);
		contentPane.add(lblbgimage);
	}

	/**
	 * Log user in to their account
	 */
	public void loginUser() {
		String username = txt_username.getText(), password = new String(txt_password.getPassword());
		if (username.equals("") || password.equals("")) {
			if (username.equals("")) {
				SeperatorListener.seperatorState(separator, false);
			}
			if (password.equals("")) {
				SeperatorListener.seperatorState(separator_1, false);
			}
		} else {
			Users user;
			if (usr == null || !usr.getUsername().equals(txt_username.getText())) {
				UsersDB ud = new UsersDB();
				user = ud.login(username, password);
			} else {
				user = usr;
			}
			if (user != null) {
				setUser(user);
				SeperatorListener.seperatorState(separator, true);
				SeperatorListener.seperatorState(separator_1, true);
				lblSignIn.setText("  Hi, " + usr.getFirst_name());
				try {
					lblSignInIcon.setIcon(RoundImageFilter
							.getRoundLabel("src\\main\\java\\com\\synergy\\exos\\uxinterface\\images\\user_img\\"
									+ usr.getUsername() + ".png", 70)
							.getIcon());
				} catch (NullPointerException exp) {
					lblSignInIcon.setIcon(RoundImageFilter.getRoundLabel(
							"src\\main\\java\\com\\synergy\\exos\\uxinterface\\images\\user_img\\default.png", 70)
							.getIcon());
				} finally {
					lbl_login_icon.setVisible(true);
				}
			} else {
				SeperatorListener.seperatorState(separator, false);
				SeperatorListener.seperatorState(separator_1, false);
			}
		}
	}

	/**
	 * Set User to login panel and return the instance
	 * @param user
	 * @return
	 */
	public LoginPanel setUser(Users user) {
		this.usr = user;
		return this;
	}
	
	/**
	 * Send user mail if they forget their password
	 * @param tempUser
	 * @param tempEmail
	 */
	public void sendRecoveryMail(@NotNull final Users tempUser, @NotBlank @Email final String tempEmail) {
		try {
			UsersDB.sendMail(tempUser, tempEmail, "exOs Password Recovery", "Hi, " + tempUser.getFirst_name()
					+ ", \n\nHere is your password for your exOs Library Management System Account.\n\n"
					+ tempUser.getPassword() + "\n\nPlease don't forget it agian.\n\nKindly,\nexOs Management Team.");

			lblEmailNotification.setText("Please Check Your Email");
			lblEmailNotification.setForeground(new Color(144, 238, 144));
		} catch (MessagingException | UnsupportedEncodingException exp) {
			exp.printStackTrace();
			lblEmailNotification.setText("Account Recovery Failed");
			lblEmailNotification.setForeground(new Color(255, 145, 164));
		} finally {
			lblEmailNotification.setVisible(true);
		}
	}

}
