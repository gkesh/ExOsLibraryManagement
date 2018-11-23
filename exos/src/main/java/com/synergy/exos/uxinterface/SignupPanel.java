package com.synergy.exos.uxinterface;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.AbstractDocument;

import com.synergy.exos.entity.Users;
import com.synergy.exos.hdbc.UsersDB;
import com.synergy.exos.hdbc.extrafetch.UsersFetch;
import com.synergy.exos.uxinterface.listeners.CursorListener;
import com.synergy.exos.uxinterface.listeners.DragListener;
import com.synergy.exos.uxinterface.listeners.SeperatorListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import javax.swing.JButton;

public class SignupPanel extends JFrame {

	/**
	 * @author G-Kesh
	 */
	private Color sci = SystemColor.inactiveCaption, sciB = SystemColor.inactiveCaptionBorder;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_fname, txt_lname, txt_username, txt_phoneno, txt_dob, txt_email;
	private JComboBox<String> txt_actype;
	private JPasswordField txt_password;
	private final JSeparator separator, separator_1, separator_2, separator_3, separator_4, separator_5, separator_6,
			separator_7;
	final JLabel lblFirstName, lblPassword, lblUsername, lblDob, lblEmail, lblLastName, lblAcType, lblPhoneno;

	/**
	 * Launch the application.
	 */
	public static void signupPanel() {
		EventQueue.invokeLater(() -> {
			try {
				SignupPanel frame = new SignupPanel();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignupPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);

		DragListener dragListener = new DragListener(this);
		addMouseListener(dragListener);
		addMouseMotionListener(dragListener);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JPanel panel_overlay = new JPanel();
		panel_overlay.setBounds(0, 0, 600, 241);
		getContentPane().add(panel_overlay);
		panel_overlay.setBackground(new Color(0, 102, 153, 50));
		panel_overlay.setLayout(null);

		JLabel label_logo = new JLabel("");
		label_logo.setBounds(12, 38, 150, 150);
		label_logo.setIcon(
				new ImageIcon(SignupPanel.class.getResource("/com/synergy/exos/uxinterface/images/logo_150.png")));
		panel_overlay.add(label_logo);

		JLabel lblExos = new JLabel("exOs");
		lblExos.setForeground(sci);
		lblExos.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		lblExos.setBounds(153, 54, 102, 47);
		panel_overlay.add(lblExos);

		JLabel lblLibraryManagementSystem = new JLabel("Library Management System");
		lblLibraryManagementSystem.setForeground(sci);
		lblLibraryManagementSystem.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		lblLibraryManagementSystem.setBounds(153, 89, 253, 30);
		panel_overlay.add(lblLibraryManagementSystem);

		JLabel lblX = new JLabel("");
		lblX.setBounds(572, 0, 23, 40);
		lblX.setIcon(
				new ImageIcon(LoginPanel.class.getResource("/com/synergy/exos/uxinterface/images/close_button.png")));
		panel_overlay.add(lblX);

		lblX.addMouseListener(new CursorListener(lblX) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				System.exit(0);
			}
		});

		JLabel label_bg = new JLabel("");
		label_bg.setBounds(-125, 0, 807, 241);
		getContentPane().add(label_bg);
		label_bg.setIcon(
				new ImageIcon(SignupPanel.class.getResource("/com/synergy/exos/uxinterface/images/background3.jpg")));

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 236, 600, 664);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblAlreadyAMember = new JLabel("Already a Member? Login");
		lblAlreadyAMember.setForeground(sciB);
		lblAlreadyAMember.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblAlreadyAMember.setBounds(199, 632, 191, 19);
		Font font = lblAlreadyAMember.getFont();
		Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblAlreadyAMember.setFont(font.deriveFont(attributes));
		panel.add(lblAlreadyAMember);
		lblAlreadyAMember.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				new LoginPanel().setVisible(true);
			}
		});
		lblAlreadyAMember.addMouseListener(new CursorListener(lblAlreadyAMember));

		lblFirstName = new JLabel("First Name");
		lblFirstName.setForeground(sci);
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFirstName.setBounds(31, 24, 87, 16);
		panel.add(lblFirstName);

		txt_fname = new JTextField();
		txt_fname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lblFirstName.setForeground(sciB);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				lblFirstName.setForeground(sci);
			}
		});
		txt_fname.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txt_fname.setBackground(SystemColor.activeCaption);
		txt_fname.setForeground(sciB);
		txt_fname.setBounds(31, 48, 229, 34);
		panel.add(txt_fname);
		txt_fname.setColumns(10);
		txt_fname.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		((AbstractDocument) txt_fname.getDocument())
				.setDocumentFilter(new com.synergy.exos.uxinterface.filters.CharacterFilter());

		separator = new JSeparator();
		separator.setForeground(sciB);
		separator.setBackground(sciB);
		separator.setBounds(31, 95, 229, 3);
		panel.add(separator);

		lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(sci);
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLastName.setBounds(331, 24, 87, 16);
		panel.add(lblLastName);

		txt_lname = new JTextField();
		txt_lname.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txt_lname.setBackground(SystemColor.activeCaption);
		txt_lname.setForeground(sciB);
		txt_lname.setBounds(331, 48, 229, 34);
		panel.add(txt_lname);
		txt_lname.setColumns(10);
		txt_lname.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		((AbstractDocument) txt_lname.getDocument())
				.setDocumentFilter(new com.synergy.exos.uxinterface.filters.CharacterFilter());

		txt_lname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lblLastName.setForeground(sciB);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				lblLastName.setForeground(sci);
			}
		});

		separator_1 = new JSeparator();
		separator_1.setForeground(sciB);
		separator_1.setBackground(sciB);
		separator_1.setBounds(331, 95, 229, 3);
		panel.add(separator_1);

		lblUsername = new JLabel("Username");
		lblUsername.setForeground(sci);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsername.setBounds(31, 144, 87, 16);
		panel.add(lblUsername);

		txt_username = new JTextField();
		txt_username.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txt_username.setBackground(SystemColor.activeCaption);
		txt_username.setForeground(sciB);
		txt_username.setBounds(31, 168, 229, 34);
		panel.add(txt_username);
		txt_username.setColumns(10);
		txt_username.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		txt_username.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lblUsername.setForeground(sciB);
			}

			@Override
			public void focusLost(FocusEvent evt) {
				lblUsername.setForeground(sci);
				if (new UsersDB().userExists(txt_username.getText(), UsersFetch.FETCH_BY_USERNAME)
						|| txt_username.getText().equals("")) {
					SeperatorListener.seperatorState(separator_2, false);
				} else {
					SeperatorListener.seperatorState(separator_2, true);
				}
			}
		});

		separator_2 = new JSeparator();
		separator_2.setForeground(sciB);
		separator_2.setBackground(sciB);
		separator_2.setBounds(31, 215, 229, 3);
		panel.add(separator_2);

		lblPassword = new JLabel("Password");
		lblPassword.setForeground(sci);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(331, 144, 87, 16);
		panel.add(lblPassword);

		txt_password = new JPasswordField();
		txt_password.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txt_password.setBackground(SystemColor.activeCaption);
		txt_password.setForeground(sciB);
		txt_password.setBounds(331, 168, 229, 34);
		panel.add(txt_password);
		txt_password.setColumns(10);
		txt_password.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		txt_password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lblPassword.setForeground(sciB);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				lblPassword.setForeground(sci);
			}
		});

		separator_3 = new JSeparator();
		separator_3.setForeground(sciB);
		separator_3.setBackground(sciB);
		separator_3.setBounds(331, 215, 229, 3);
		panel.add(separator_3);

		lblDob = new JLabel("Date of Birth");
		lblDob.setForeground(sci);
		lblDob.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDob.setBounds(31, 264, 101, 16);
		panel.add(lblDob);

		txt_dob = new JTextField();
		txt_dob.setText("YYYY/MM/DD");
		txt_dob.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txt_dob.setBackground(SystemColor.activeCaption);
		txt_dob.setForeground(sciB);
		txt_dob.setBounds(31, 288, 229, 34);
		panel.add(txt_dob);
		txt_dob.setColumns(10);
		txt_dob.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		txt_dob.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent evt) {
				txt_dob.setText("");
				lblDob.setForeground(sciB);
			}

			@Override
			public void focusLost(FocusEvent evt) {
				lblDob.setForeground(sci);
			}
		});

		txt_dob.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				if (Character.isDigit(evt.getKeyChar())) {
					if (txt_dob.getText().length() == 4 || txt_dob.getText().length() == 7) {
						txt_dob.setText(txt_dob.getText() + "/");
					}
				} else {
					evt.consume();
				}
			}
		});

		separator_4 = new JSeparator();
		separator_4.setForeground(sciB);
		separator_4.setBackground(sciB);
		separator_4.setBounds(31, 335, 229, 3);
		panel.add(separator_4);

		lblPhoneno = new JLabel("Phone No.");
		lblPhoneno.setForeground(sci);
		lblPhoneno.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPhoneno.setBounds(331, 264, 87, 16);
		panel.add(lblPhoneno);

		txt_phoneno = new JTextField();
		txt_phoneno.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txt_phoneno.setBackground(SystemColor.activeCaption);
		txt_phoneno.setForeground(sciB);
		txt_phoneno.setBounds(331, 288, 229, 34);
		panel.add(txt_phoneno);
		txt_phoneno.setColumns(10);
		txt_phoneno.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		((AbstractDocument) txt_phoneno.getDocument())
				.setDocumentFilter(new com.synergy.exos.uxinterface.filters.NumberFilter());

		txt_phoneno.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lblPhoneno.setForeground(sciB);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				lblPhoneno.setForeground(sci);
			}
		});

		separator_5 = new JSeparator();
		separator_5.setForeground(sciB);
		separator_5.setBackground(sciB);
		separator_5.setBounds(331, 335, 229, 3);
		panel.add(separator_5);

		lblEmail = new JLabel("Email");
		lblEmail.setForeground(sci);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEmail.setBounds(31, 384, 87, 16);
		panel.add(lblEmail);

		txt_email = new JTextField();
		txt_email.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txt_email.setBackground(SystemColor.activeCaption);
		txt_email.setForeground(sciB);
		txt_email.setBounds(31, 408, 229, 34);
		panel.add(txt_email);
		txt_email.setColumns(10);
		txt_email.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		txt_email.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lblEmail.setForeground(sciB);
			}

			@Override
			public void focusLost(FocusEvent evt) {
				lblEmail.setForeground(sci);
				if (new UsersDB().userExists(txt_email.getText(), UsersFetch.FETCH_BY_EMAIL)
						|| txt_email.getText().equals("")) {
					SeperatorListener.seperatorState(separator_6, false);
				} else {
					SeperatorListener.seperatorState(separator_6, true);
				}
			}
		});

		separator_6 = new JSeparator();
		separator_6.setForeground(sciB);
		separator_6.setBackground(sciB);
		separator_6.setBounds(31, 455, 229, 3);
		panel.add(separator_6);

		UIManager.put("ComboBox.background", new ColorUIResource(new Color(153, 180, 209)));
		UIManager.put("ComboBox.foreground", new ColorUIResource(sciB));
		UIManager.put("ComboBox.selectionBackground", new ColorUIResource(new Color(153, 180, 209)));
		UIManager.put("ComboBox.selectionForeground", new ColorUIResource(sciB));

		lblAcType = new JLabel("Account Type");
		lblAcType.setForeground(sci);
		lblAcType.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAcType.setBounds(331, 384, 116, 16);
		panel.add(lblAcType);

		txt_actype = new JComboBox<String>();
		txt_actype.setForeground(sciB);
		txt_actype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Student", "Intern", "Employee" }));
		txt_actype.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txt_actype.setBounds(331, 408, 229, 34);
		panel.add(txt_actype);

		txt_actype.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lblAcType.setForeground(sciB);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				lblAcType.setForeground(sci);
			}
		});
		separator_7 = new JSeparator();
		separator_7.setForeground(sciB);
		separator_7.setBackground(sciB);
		separator_7.setBounds(331, 455, 229, 3);
		panel.add(separator_7);

		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setForeground(sciB);
		btnSignUp.setBackground(new Color(0, 102, 153));
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSignUp.setBounds(158, 544, 271, 47);
		panel.add(btnSignUp);

		btnSignUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					signUp();
				} catch (ParseException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error while parsing date. \nData not saved");
				}
			}
		});
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				System.exit(0);
			}
		});
		btnSignUp.addMouseListener(new CursorListener(btnSignUp));
	}

	/**
	 * Add user to the application
	 * @throws ParseException
	 */
	public void signUp() throws ParseException {
		Users newUser = new Users();
		newUser.setAdded_date(new Timestamp(System.currentTimeMillis()));
		newUser.setAdmin(false);
		newUser.setEmail_add(txt_email.getText());
		newUser.setFirst_name(txt_fname.getText());
		newUser.setLast_name(txt_lname.getText());
		newUser.setPassword(new String(txt_password.getPassword()));
		newUser.setPhone_no(txt_phoneno.getText());
		newUser.setType(txt_actype.getSelectedItem().toString());
		newUser.setUsername(txt_username.getText());
		newUser.setDob(new java.sql.Date(new SimpleDateFormat("yyyy/MM/dd").parse(txt_dob.getText()).getTime()));
		UsersDB ud = new UsersDB();
		ud.save(newUser);
		new LoginPanel().setUser(newUser).setVisible(true);
		dispose();
	}
}
