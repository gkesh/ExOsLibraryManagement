package com.synergy.exos.uxinterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import com.synergy.exos.entity.Author;
import com.synergy.exos.entity.Books;
import com.synergy.exos.entity.Users;
import com.synergy.exos.hdbc.BooksDB;
import com.synergy.exos.hdbc.UsersDB;
import com.synergy.exos.hdbc.extrafetch.BooksFetch;
import com.synergy.exos.hdbc.extrafetch.UsersFetch;
import com.synergy.exos.uxinterface.filters.RoundImageFilter;
import com.synergy.exos.uxinterface.listeners.CursorListener;
import com.synergy.exos.uxinterface.listeners.DragListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class HomePanel extends JFrame {

	/**
	 * @author G-Kesh
	 */
	private static final long serialVersionUID = 1L;
	private Users selectedUser;
	private static JPanel contentPane;
	private static boolean isExpanded = false, isProfileEditable = false;
	private JPanel panel_title, panel_username, panel_left, panel_home, panel_profile, panel_about, panel_counter,
			panel_book, panel_people, panel_right, panel_logout, panellayer_home, panellayer_profile, panellayer_people,
			panel_title_people, panel_title_profile, panel_title_home, panellayer_about, panel_title_about,
			panellayer_counter, panel_title_counter, panellayer_book, panel_title_book, panel_contents_profile,
			selection;
	private final JLabel label_bg_img, lblX, lblSaveBtn, lblCancelBtn, lblNameL, labelEmailIcon, labelPhoneIcon,
			labelDobIcon, labelAcTypeIcon, labelJoinedIcon, lblDOB, lblPhone, lblJoined, lblEmail, lblAcType,
			lblUsernameIcon, labelUsername, labelCounterUser, lblcounterpicture;
	private final JLayeredPane layeredPane_contents;
	private final JTextField lblName, txtDOB, txtPhone, txtEmail, txtUsername;
	private final JSeparator editorSep, editNameSep, editDOBSep, editPhoneSep, editEmailSep, editUsernameSep;
	private JLabel labelbigabout;
	private JPanel[] menuPanels;
	private UsersDB uD = new UsersDB();
	private BooksDB bd = new BooksDB();
	private JTable table, btable, ctable;
	private JScrollPane scrollPane, bscrollPane, cscrollPane;
	private List<Vector<Object>> selectedBooks = new ArrayList<>();
	private DefaultTableCellRenderer dtr = new DefaultTableCellRenderer() {
		private static final long serialVersionUID = 1L;
		Border padding = BorderFactory.createEmptyBorder(0, 10, 0, 10);

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setHorizontalAlignment(JLabel.LEFT);
			setBorder(BorderFactory.createCompoundBorder(getBorder(), padding));
			return this;
		}
	};

	/**
	 * Launch the application.
	 * @param User
	 */
	public static void homePanel(final Users usr) {
		EventQueue.invokeLater(() -> {
			try {
				HomePanel frame = new HomePanel(usr);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 * @param User
	 */
	@SuppressWarnings("serial")
	public HomePanel(final Users usr) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
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

		JPanel panel_bg = new JPanel();
		panel_bg.setBounds(0, 0, 1500, 800);
		contentPane.add(panel_bg);
		panel_bg.setLayout(null);

		panel_left = new JPanel();
		panel_left.setBackground(new Color(0, 102, 153, 200));
		panel_left.setBounds(0, 0, 450, 800);
		panel_bg.add(panel_left);
		panel_left.setLayout(null);

		// Home Panel

		panel_home = new JPanel();
		panel_home.setBackground(SystemColor.activeCaption);
		panel_home.setBounds(0, 200, 450, 75);
		panel_left.add(panel_home);
		panel_home.setLayout(null);
		selection = panel_home;

		JLabel label_home_icon = new JLabel("");
		label_home_icon.setBounds(20, 0, 50, 75);
		label_home_icon.setHorizontalAlignment(SwingConstants.CENTER);
		label_home_icon.setIcon(new ImageIcon(
				HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/home_icon_active.png")));
		panel_home.add(label_home_icon);

		JLabel lblHome = new JLabel("Home");
		lblHome.setForeground(SystemColor.inactiveCaptionBorder);
		lblHome.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblHome.setBounds(120, 0, 100, 75);
		panel_home.add(lblHome);

		// Home Panel End
		// Profile Panel

		panel_profile = new JPanel();
		panel_profile.setOpaque(false);
		panel_profile.setBounds(0, 275, 450, 75);
		panel_left.add(panel_profile);
		panel_profile.setLayout(null);

		JLabel label_profile_icon = new JLabel("");
		label_profile_icon.setBounds(20, 0, 50, 75);
		label_profile_icon.setHorizontalAlignment(SwingConstants.CENTER);
		label_profile_icon.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/profile_icon.png")));
		panel_profile.add(label_profile_icon);

		JLabel lblProfile = new JLabel("Profile");
		lblProfile.setForeground(SystemColor.inactiveCaption);
		lblProfile.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblProfile.setBounds(120, 0, 100, 75);
		panel_profile.add(lblProfile);
		// Profile Panel End

		// People Panel

		panel_people = new JPanel();
		panel_people.setOpaque(false);
		panel_people.setBounds(0, 350, 450, 75);
		panel_left.add(panel_people);
		panel_people.setLayout(null);

		JLabel label_people_icon = new JLabel("");
		label_people_icon.setBounds(20, 0, 50, 75);
		label_people_icon.setHorizontalAlignment(SwingConstants.CENTER);
		label_people_icon.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/people_icon.png")));
		panel_people.add(label_people_icon);

		JLabel lblPeople = new JLabel("People");
		lblPeople.setForeground(SystemColor.inactiveCaption);
		lblPeople.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblPeople.setBounds(120, 0, 161, 75);
		panel_people.add(lblPeople);
		// People Panel End

		// Books Panel

		panel_book = new JPanel();
		panel_book.setOpaque(false);
		panel_book.setBounds(0, 425, 450, 75);
		panel_left.add(panel_book);
		panel_book.setLayout(null);

		JLabel label_book_icon = new JLabel("");
		label_book_icon.setBounds(20, 0, 50, 75);
		label_book_icon.setHorizontalAlignment(SwingConstants.CENTER);
		label_book_icon.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/book_icon.png")));
		panel_book.add(label_book_icon);

		JLabel lblBook = new JLabel("Books");
		lblBook.setForeground(SystemColor.inactiveCaption);
		lblBook.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblBook.setBounds(120, 0, 100, 75);
		panel_book.add(lblBook);
		// Books Panel End

		// Counter Panel

		panel_counter = new JPanel();
		panel_counter.setBounds(0, 575, 450, 75);
		panel_counter.setOpaque(false);
		if (usr.isAdmin()) {
			panel_left.add(panel_counter);
		}
		panel_counter.setLayout(null);

		JLabel label_counter_icon = new JLabel("");
		label_counter_icon.setBounds(20, 0, 50, 75);
		label_counter_icon.setHorizontalAlignment(SwingConstants.CENTER);
		label_counter_icon.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/counter_icon.png")));
		panel_counter.add(label_counter_icon);

		JLabel lblCounter = new JLabel("Counter");
		lblCounter.setForeground(SystemColor.inactiveCaption);
		lblCounter.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblCounter.setBounds(120, 0, 116, 75);
		panel_counter.add(lblCounter);
		// Counter Panel

		// About Panel

		panel_about = new JPanel();
		panel_about.setOpaque(false);
		panel_about.setBounds(0, 500, 450, 75);
		panel_left.add(panel_about);
		panel_about.setLayout(null);

		JLabel label_about_icon = new JLabel("");
		label_about_icon.setBounds(20, 0, 50, 75);
		label_about_icon.setHorizontalAlignment(SwingConstants.CENTER);
		label_about_icon.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/about_icon.png")));
		panel_about.add(label_about_icon);

		JLabel lblAbout = new JLabel("About");
		lblAbout.setForeground(SystemColor.inactiveCaption);
		lblAbout.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblAbout.setBounds(120, 0, 100, 75);
		panel_about.add(lblAbout);

		// About Panel End

		panel_username = new JPanel();
		panel_username.setBounds(0, 100, 450, 75);
		panel_left.add(panel_username);
		panel_username.setOpaque(false);
		panel_username.setLayout(null);

		JLabel lblUsername = new JLabel(usr.getUsername());
		lblUsername.setForeground(SystemColor.inactiveCaption);
		lblUsername.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblUsername.setBounds(120, 0, 250, 74);
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		panel_username.add(lblUsername);

		JLabel labelUserImgSmall = RoundImageFilter.getRoundLabel(
				"src\\main\\java\\com\\synergy\\exos\\uxinterface\\images\\user_img\\" + usr.getUsername() + ".png",
				50);
		if (labelUserImgSmall == null) {
			labelUserImgSmall = RoundImageFilter.getRoundLabel(
					"src\\main\\java\\com\\synergy\\exos\\uxinterface\\images\\user_img\\default.png", 50);
		}
		labelUserImgSmall.setBounds(20, 12, 50, 50);
		panel_username.add(labelUserImgSmall);

		// Panel Logout
		panel_logout = new JPanel();
		panel_logout.setBounds(0, 725, 450, 75);
		panel_logout.setBackground(new Color(0, 0, 0, 0));
		panel_left.add(panel_logout);
		panel_logout.setLayout(null);

		JLabel label_logout_icon = new JLabel("");
		label_logout_icon.setBounds(20, 0, 50, 75);
		label_logout_icon.setHorizontalAlignment(SwingConstants.CENTER);
		label_logout_icon.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/logout_icon.png")));
		panel_logout.add(label_logout_icon);

		JLabel lblLogout = new JLabel("Log out");
		lblLogout.setForeground(SystemColor.inactiveCaption);
		lblLogout.setFont(new Font("Century Gothic", Font.BOLD, 30));
		lblLogout.setBounds(120, 0, 155, 75);
		panel_logout.add(lblLogout);

		panel_title = new JPanel();
		panel_title.setBackground(new Color(0, 0, 0, 0));
		panel_title.setBounds(0, 0, 450, 75);
		panel_left.add(panel_title);
		panel_title.setLayout(null);

		JLabel label_menu_btn = new JLabel("");
		label_menu_btn.setHorizontalAlignment(SwingConstants.CENTER);
		label_menu_btn.setBounds(20, 0, 50, 75);
		panel_title.add(label_menu_btn);
		label_menu_btn.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/menu_icon.png")));

		JLabel lblExos = new JLabel("exOs");
		lblExos.setBounds(120, 0, 86, 75);
		panel_title.add(lblExos);
		lblExos.setForeground(SystemColor.inactiveCaption);
		lblExos.setFont(new Font("Century Gothic", Font.BOLD, 35));
		lblExos.setIcon(null);

		// Panel Logout End
		// Background Label

		label_bg_img = new JLabel("");
		label_bg_img.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/background4.jpg")));
		label_bg_img.setBounds(0, 0, 450, 800);
		panel_bg.add(label_bg_img);

		panel_right = new JPanel();
		panel_right.setBounds(450, 0, 1050, 800);
		panel_bg.add(panel_right);
		panel_right.setLayout(null);

		layeredPane_contents = new JLayeredPane();
		layeredPane_contents.setBounds(0, 0, 1050, 800);
		panel_right.add(layeredPane_contents);
		layeredPane_contents.setLayout(null);

		// People Layered Panel END
		// Home Layered Panel

		panellayer_home = new JPanel();
		panellayer_home.setBackground(SystemColor.activeCaption);
		panellayer_home.setBounds(0, 0, 1050, 800);
		layeredPane_contents.add(panellayer_home);
		panellayer_home.setLayout(null);

		panel_title_home = new JPanel();
		panel_title_home.setBackground(new Color(0, 102, 153));
		panel_title_home.setBounds(0, 0, 1410, 167);
		panellayer_home.add(panel_title_home);
		panel_title_home.setLayout(null);

		JLabel lblHome_1 = new JLabel("Home");
		lblHome_1.setBounds(40, 34, 207, 87);
		panel_title_home.add(lblHome_1);
		lblHome_1.setForeground(SystemColor.inactiveCaptionBorder);
		lblHome_1.setFont(new Font("Century Gothic", Font.PLAIN, 70));

		JPanel panel_contents_home = new JPanel();
		panel_contents_home.setBounds(40, 180, 1310, 600);
		panellayer_home.add(panel_contents_home);
		panel_contents_home.setOpaque(false);
		panel_contents_home.setLayout(null);

		// Profile logo inside home panel

		JLabel lbl_profileicon_home = new JLabel("");
		lbl_profileicon_home.setIcon(new ImageIcon(
				HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/logo_profile_home.png")));
		lbl_profileicon_home.setHorizontalAlignment(SwingConstants.TRAILING);
		lbl_profileicon_home.setBounds(120, 50, 200, 200);
		panel_contents_home.add(lbl_profileicon_home);
		lbl_profileicon_home.addMouseListener(new CursorListener(lbl_profileicon_home) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				select(panel_profile, panellayer_profile);
			}
		});

		// People logo inside home panel

		JLabel lbl_peopleicon_home = new JLabel("");
		lbl_peopleicon_home.setIcon(new ImageIcon(
				HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/logo_people_home.png")));
		lbl_peopleicon_home.setHorizontalAlignment(SwingConstants.TRAILING);
		lbl_peopleicon_home.setBounds(120, 320, 200, 200);
		panel_contents_home.add(lbl_peopleicon_home);
		lbl_peopleicon_home.addMouseListener(new CursorListener(lbl_peopleicon_home) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				select(panel_people, panellayer_people);
			}
		});

		// books logo inside home panel

		JLabel lbl_booksicon_home = new JLabel("");
		lbl_booksicon_home.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/logo_books_home.png")));
		lbl_booksicon_home.setHorizontalAlignment(SwingConstants.TRAILING);
		lbl_booksicon_home.setBounds(600, 50, 200, 200);
		panel_contents_home.add(lbl_booksicon_home);
		lbl_booksicon_home.addMouseListener(new CursorListener(lbl_booksicon_home) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				select(panel_book, panellayer_book);
			}
		});

		// about logo inside home panel

		JLabel lbl_abouticon_home = new JLabel("");
		lbl_abouticon_home.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/logo_about_home.png")));
		lbl_abouticon_home.setHorizontalAlignment(SwingConstants.TRAILING);
		lbl_abouticon_home.setBounds(600, 320, 200, 200);
		panel_contents_home.add(lbl_abouticon_home);
		lbl_abouticon_home.addMouseListener(new CursorListener(lbl_abouticon_home) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				select(panel_about, panellayer_about);
			}
		});

		// logout logo inside home panel

		JLabel lbl_logouticon_home = new JLabel("");
		lbl_logouticon_home.setIcon(new ImageIcon(
				HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/logo_logout_home.png")));
		lbl_logouticon_home.setHorizontalAlignment(SwingConstants.TRAILING);
		lbl_logouticon_home.setBounds(1050, 50, 200, 200);
		panel_contents_home.add(lbl_logouticon_home);
		lbl_logouticon_home.addMouseListener(new CursorListener(lbl_logouticon_home) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				new LoginPanel().setUser(usr).setVisible(true);;
				dispose();
			}
		});

		// About Layered Panel END

		lblX = new JLabel("");
		lblX.setBounds(1011, 13, 27, 20);
		panel_title_home.add(lblX);
		lblX.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/close_button.png")));
		lblX.setForeground(SystemColor.inactiveCaptionBorder);
		lblX.setHorizontalAlignment(SwingConstants.RIGHT);
		lblX.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));

		lblX.addMouseListener(new CursorListener(lblX) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				System.exit(0);
			}
		});
		disableOthers(panellayer_home);

		// Profile Layered Panel END

		// People Layered Panel

		panellayer_people = new JPanel();
		panellayer_people.setBackground(SystemColor.activeCaption);
		panellayer_people.setBounds(0, 0, 1050, 800);
		layeredPane_contents.add(panellayer_people);
		panellayer_people.setLayout(null);

		panel_title_people = new JPanel();
		panel_title_people.setBackground(new Color(0, 102, 153));
		panel_title_people.setBounds(0, 0, 1410, 167);
		panellayer_people.add(panel_title_people);
		panel_title_people.setLayout(null);

		JLabel lblpeople_1 = new JLabel("People");
		lblpeople_1.setBounds(40, 34, 300, 87);
		panel_title_people.add(lblpeople_1);
		lblpeople_1.setForeground(SystemColor.inactiveCaptionBorder);
		lblpeople_1.setFont(new Font("Century Gothic", Font.PLAIN, 70));

		scrollPane = new JScrollPane();
		panellayer_people.add(scrollPane);

		table = new JTable();
		table.setForeground(SystemColor.inactiveCaptionBorder);
		scrollPane.setViewportView(table);
		scrollPane.getViewport().setBackground(SystemColor.activeCaption);
		table.setBackground(SystemColor.activeCaption);
		table.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 22));
		table.getTableHeader().setBackground(SystemColor.activeCaption);
		table.setFont(new Font("Century Gothic", Font.BOLD, 18));

		// Small Modal
		DefaultTableModel dfs = new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Name", "Username", "Email", "Ac. Type" }) {
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};

		// Large Modal
		DefaultTableModel dfl = new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Name", "Username", "Email", "Ac. Type", "Phone No.", "D.O.B", "Date Joined" }) {
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};

		table.setModel(dfs);
		scrollPane.setBounds(40, 180, 980, 600);

		// Adding Data to Table
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		removeRowsFromTable(dtm.getRowCount(), dtm);

		for (Users aUser : uD.fetch(UsersFetch.FETCH_ALL, null)) {
			dtm.addRow(new Object[] { aUser.getFirst_name() + " " + aUser.getLast_name(), aUser.getUsername(),
					aUser.getEmail_add(), aUser.getType() });
		}

		table.setRowHeight(table.getRowHeight() + 25);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(dtr);
		}

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(190);
		table.getColumnModel().getColumn(1).setPreferredWidth(190);
		table.getColumnModel().getColumn(2).setPreferredWidth(450);
		table.getColumnModel().getColumn(3).setPreferredWidth(147);

		// People Panel End

		JLabel lbl_countericon_home = new JLabel("");
		lbl_countericon_home.setIcon(new ImageIcon(
				HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/logo_counter_home.png")));
		lbl_countericon_home.setHorizontalAlignment(SwingConstants.TRAILING);
		lbl_countericon_home.setBounds(1050, 320, 200, 200);
		if (usr.isAdmin())
			panel_contents_home.add(lbl_countericon_home);
		lbl_countericon_home.addMouseListener(new CursorListener(lbl_countericon_home) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				select(panel_counter, panellayer_counter);
			}
		});

		// Profile Layered Panel
		panellayer_profile = new JPanel();
		panellayer_profile.setBackground(SystemColor.activeCaption);
		panellayer_profile.setBounds(0, 0, 1050, 800);
		layeredPane_contents.add(panellayer_profile);
		panellayer_profile.setLayout(null);

		panel_title_profile = new JPanel();
		panel_title_profile.setBackground(new Color(0, 102, 153));
		panel_title_profile.setBounds(0, 0, 1410, 167);
		panellayer_profile.add(panel_title_profile);
		panel_title_profile.setLayout(null);

		JLabel lblProfile_1 = new JLabel("Profile");
		lblProfile_1.setBounds(40, 34, 207, 87);
		panel_title_profile.add(lblProfile_1);
		lblProfile_1.setForeground(SystemColor.inactiveCaptionBorder);
		lblProfile_1.setFont(new Font("Century Gothic", Font.PLAIN, 70));

		// Book Layered Panel END
		// Counter Layered Panel
		panellayer_counter = new JPanel();
		panellayer_counter.setBackground(SystemColor.activeCaption);
		panellayer_counter.setBounds(0, 0, 1050, 800);
		layeredPane_contents.add(panellayer_counter);
		panellayer_counter.setLayout(null);

		panel_title_counter = new JPanel();
		panel_title_counter.setBackground(new Color(0, 102, 153));
		panel_title_counter.setBounds(0, 0, 1410, 167);
		panellayer_counter.add(panel_title_counter);
		panel_title_counter.setLayout(null);

		JLabel lblcounter_1 = new JLabel("Counter");
		lblcounter_1.setBounds(40, 34, 300, 87);
		panel_title_counter.add(lblcounter_1);
		lblcounter_1.setForeground(SystemColor.inactiveCaptionBorder);
		lblcounter_1.setFont(new Font("Century Gothic", Font.PLAIN, 70));

		lblcounterpicture = new JLabel("");
		labelCounterUser = new JLabel("No User Selected");
		labelCounterUser.setBounds(40, 180, 300, 40);
		labelCounterUser.setBackground(SystemColor.activeCaption);
		labelCounterUser.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		labelCounterUser.setForeground(SystemColor.inactiveCaptionBorder);
		labelCounterUser.setFont(new Font("Century Gothic", Font.BOLD, 35));
		panellayer_counter.add(labelCounterUser);

		// Books Table End

		// Home Layered Panel END
		// Book Layered Panel
		panellayer_book = new JPanel();
		panellayer_book.setBackground(SystemColor.activeCaption);
		panellayer_book.setBounds(0, 0, 1050, 800);
		layeredPane_contents.add(panellayer_book);
		panellayer_book.setLayout(null);

		panel_title_book = new JPanel();
		panel_title_book.setBackground(new Color(0, 102, 153));
		panel_title_book.setBounds(0, 0, 1410, 167);
		panellayer_book.add(panel_title_book);
		panel_title_book.setLayout(null);

		JLabel lblbook_1 = new JLabel("Books");
		lblbook_1.setBounds(40, 34, 300, 87);
		panel_title_book.add(lblbook_1);
		lblbook_1.setForeground(SystemColor.inactiveCaptionBorder);
		lblbook_1.setFont(new Font("Century Gothic", Font.PLAIN, 70));

		bscrollPane = new JScrollPane();
		panellayer_book.add(bscrollPane);

		btable = new JTable();
		btable.setForeground(SystemColor.inactiveCaptionBorder);
		bscrollPane.setViewportView(btable);
		bscrollPane.getViewport().setBackground(SystemColor.activeCaption);
		btable.setBackground(SystemColor.activeCaption);
		btable.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 22));
		btable.getTableHeader().setBackground(SystemColor.activeCaption);
		btable.setFont(new Font("Century Gothic", Font.BOLD, 18));

		// Small Modal
		DefaultTableModel bdfs = new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "ISBN", "Title", "Author(s)", "Copies" }) {
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.Integer.class };
			boolean[] canEdit = new boolean[] { false, false, false, false };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};

		// Large Modal
		DefaultTableModel bdfl = new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "ISBN", "Title", "Author(s)", "Copies", "Genre", "Publisher", "Edition" }) {
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};

		btable.setModel(bdfs);
		bscrollPane.setBounds(40, 180, 980, 600);

		DefaultTableModel bdtm = (DefaultTableModel) btable.getModel();
		removeRowsFromTable(bdtm.getRowCount(), bdtm);

		btable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		btable.getColumnModel().getColumn(0).setPreferredWidth(170);
		btable.getColumnModel().getColumn(1).setPreferredWidth(230);
		btable.getColumnModel().getColumn(2).setPreferredWidth(460);
		btable.getColumnModel().getColumn(3).setPreferredWidth(117);

		for (Books aBook : bd.fetch(BooksFetch.FETCH_ALL, null)) {
			StringBuilder authors = new StringBuilder();
			List<Author> authorList = aBook.getAuthor();
			for (Author a : authorList) {
				if (authorList.indexOf(a) != authorList.size() - 1) {
					authors.append(a.getFirst_name() + " " + a.getLast_name() + ", ");
				} else {
					authors.append(a.getFirst_name() + " " + a.getLast_name());
				}
			}
			bdtm.addRow(new Object[] { aBook.getIsbn(), aBook.getTitle(), authors.toString(),
					aBook.getCopies_available() });
		}

		btable.setRowHeight(btable.getRowHeight() + 25);
		for (int i = 0; i < btable.getColumnCount(); i++) {
			btable.getColumnModel().getColumn(i).setCellRenderer(dtr);
		}

		// Ctable model Clone

		// Small Modal
		DefaultTableModel cdfs = new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "ISBN", "Title", "Author(s)", "Copies" }) {
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.Integer.class };
			boolean[] canEdit = new boolean[] { false, false, false, false };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};

		// Large Modal
		DefaultTableModel cdfl = new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "ISBN", "Title", "Author(s)", "Copies", "Genre", "Publisher", "Edition" }) {
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};
		cscrollPane = new JScrollPane();
		cscrollPane.setSize(980, 400);
		cscrollPane.setLocation(40, 240);
		panellayer_counter.add(cscrollPane);

		ctable = new JTable();
		ctable.setModel(cdfs);
		ctable.setForeground(SystemColor.inactiveCaptionBorder);
		cscrollPane.setViewportView(ctable);
		cscrollPane.getViewport().setBackground(SystemColor.activeCaption);
		ctable.setBackground(SystemColor.activeCaption);
		ctable.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 22));
		ctable.getTableHeader().setBackground(SystemColor.activeCaption);
		ctable.setFont(new Font("Century Gothic", Font.BOLD, 18));

		ctable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ctable.getColumnModel().getColumn(0).setPreferredWidth(170);
		ctable.getColumnModel().getColumn(1).setPreferredWidth(230);
		ctable.getColumnModel().getColumn(2).setPreferredWidth(460);
		ctable.getColumnModel().getColumn(3).setPreferredWidth(117);

		ctable.setRowHeight(ctable.getRowHeight() + 25);

		for (int i = 0; i < ctable.getColumnCount(); i++) {
			ctable.getColumnModel().getColumn(i).setCellRenderer(dtr);
		}

		JButton btnCheckOut = new JButton("Check Out");
		btnCheckOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCheckOut.setBackground(SystemColor.inactiveCaptionBorder);
		btnCheckOut.setForeground(new Color(0, 102, 153));
		btnCheckOut.setBounds(40, 690, 150, 40);
		btnCheckOut.setEnabled(false);
		panellayer_counter.add(btnCheckOut);
		btnCheckOut.addMouseListener(new CursorListener(btnCheckOut) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				for (Vector<Object> temp : selectedBooks) {
					Books tempBook = bd.fetch(BooksFetch.FETCH_BY_ISBN, temp.get(0).toString()).get(0);
					tempBook.setCopies_available(tempBook.getCopies_available() - 1);
					bd.update(tempBook, null, null);
					/*
					 * if (!selectedUser.getBooks().contains(tempBook)) {
					 * selectedUser.setBook(tempBook); }
					 */
					if (Collections.disjoint(selectedUser.getBook(), tempBook.getUsers())) {
						selectedUser.addBook(tempBook);
					}
				}
				uD.update(selectedUser);
				new NotificationDialog().createNotification(
						"<html>" + selectedUser.getFirst_name() + " has check-out succesfully</html>");
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCancel.setBackground(SystemColor.inactiveCaptionBorder);
		btnCancel.setForeground(new Color(0, 102, 153));
		btnCancel.setBounds(230, 690, 150, 40);
		btnCancel.setEnabled(false);
		panellayer_counter.add(btnCancel);
		btnCancel.addMouseListener(new CursorListener(btnCancel) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				selectedUser = null;
				labelCounterUser.setText("No User Selected");
				lblcounterpicture.setIcon(new ImageIcon(RoundImageFilter.getRoundImage(
						"src\\main\\java\\com\\synergy\\exos\\uxinterface\\images\\user_img\\default.png", 50)));
				lblcounterpicture.revalidate();
				lblcounterpicture.repaint();
				selectedBooks.clear();
				if (isExpanded) {
					setBooksCounterTableBig(true, cdfl);
				} else {
					setBooksCounterTableBig(false, cdfs);
				}
			}
		});

		// Table Listeners

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (usr.isAdmin()) {
					if (!arg0.getValueIsAdjusting()) {
						if (table.getSelectedRow() > -1) {
							selectedUser = uD.fetch(UsersFetch.FETCH_BY_USERNAME,
									((String) table.getModel().getValueAt(table.getSelectedRow(), 1))).get(0);

							new NotificationDialog()
									.createNotification(selectedUser.getFirst_name() + " is now at counter.");
							selectedBooks.clear();
							try {
								lblcounterpicture.setIcon(new ImageIcon(RoundImageFilter.getRoundImage(
										"src\\main\\java\\com\\synergy\\exos\\uxinterface\\images\\user_img\\"
												+ selectedUser.getUsername() + ".png",
										50)));
							} catch (NullPointerException exp) {
								System.err.println(exp);
								lblcounterpicture.setIcon(new ImageIcon(RoundImageFilter.getRoundImage(
										"src\\main\\java\\com\\synergy\\exos\\uxinterface\\images\\user_img\\default.png",
										50)));
							}
							for (Books usrBooks : selectedUser.getWithdrawls()) {
								Vector<Object> temp = new Vector<>();
								temp.add(usrBooks.getIsbn());
								temp.add(usrBooks.getTitle());
								StringBuilder authors = new StringBuilder();
								List<Author> authorList = usrBooks.getAuthor();
								for (Author a : authorList) {
									if (authorList.indexOf(a) != authorList.size() - 1) {
										authors.append(a.getFirst_name() + " " + a.getLast_name() + ", ");
									} else {
										authors.append(a.getFirst_name() + " " + a.getLast_name());
									}
								}
								temp.add(authors.toString());
								temp.add(usrBooks.getCopies_available() + "");
								selectedBooks.add(temp);
							}

							if (isExpanded) {
								setBooksCounterTableBig(true, cdfl);
							} else {
								setBooksCounterTableBig(false, cdfs);
							}

							lblcounterpicture.setBounds(40, 170, 60, 60);
							labelCounterUser.setBounds(120, 180, 450, 40);
							labelCounterUser.setText(selectedUser.getFirst_name() + " " + selectedUser.getLast_name());
							panellayer_counter.add(lblcounterpicture);
							if (panel_counter != selection) {
								if (selectedBooks != null) {
									btnCancel.setEnabled(true);
									btnCheckOut.setEnabled(true);
								}
								select(panel_counter, panellayer_counter);
							}
							lblcounterpicture.revalidate();
							lblcounterpicture.repaint();
						}
					}
				}

			}
		});
		btable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (usr.isAdmin()) {
					if (!arg0.getValueIsAdjusting()) {
						boolean valueExists = false;
						int selectionRow = btable.getSelectedRow();
						if (selectionRow > -1) {
							for (int i = 0; i < ctable.getRowCount(); i++) {
								if (ctable.getModel().getValueAt(i, 0)
										.equals(btable.getModel().getValueAt(btable.getSelectedRow(), 1))) {
									valueExists = true;
								}
							}
							if (!valueExists) {
								boolean isredundent = false;
								Vector<Object> temp = (Vector<Object>) bdtm.getDataVector().elementAt(selectionRow);
								for (Vector<Object> tempVector : selectedBooks) {
									if (tempVector.get(0).toString().equals(temp.get(0).toString())) {
										isredundent = true;
										break;
									} else {
										isredundent = false;
									}
								}
								if (!isredundent) {
									selectedBooks.add(temp);
									new NotificationDialog().createNotification("Book added to counter.");
								} else {
									new NotificationDialog().createNotification("Book already in list.");
								}
								if (isExpanded) {
									setBooksCounterTableBig(true, cdfl);
									repaint();
								} else {
									setBooksCounterTableBig(false, cdfs);
									repaint();
								}
								if (panel_counter != selection) {
									if (selectedUser != null) {
										btnCancel.setEnabled(true);
										btnCheckOut.setEnabled(true);
									}
									select(panel_counter, panellayer_counter);
								}
							}
						}
					}

				}
			}
		});

		ctable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Removing the row from selection
				if (!e.getValueIsAdjusting()) {
					int selectedRow = ctable.getSelectedRow();
					if (selectedRow > -1) {
						new MessageDialog().createDialog("<html>Are you sure you returnn this Selection? </html>>",
								selectedRow, ctable, selectedBooks);
					}
				}
			}
		});
		// Counter Table End

		panel_contents_profile = new JPanel();
		panel_contents_profile.setBounds(40, 180, 1310, 600);
		panel_contents_profile.setBackground(SystemColor.activeCaption);
		panellayer_profile.add(panel_contents_profile);
		panel_contents_profile.setLayout(null);

		JLabel labelUserIconBig = RoundImageFilter.getRoundLabel(
				"src\\main\\java\\com\\synergy\\exos\\uxinterface\\images\\user_img\\" + usr.getUsername() + ".png",
				420);
		if (labelUserIconBig == null) {
			labelUserIconBig = RoundImageFilter.getRoundLabel(
					"src\\main\\java\\com\\synergy\\exos\\uxinterface\\images\\user_img\\default.png", 420);
		}
		labelUserIconBig.setBounds(550, 100, 420, 420);
		panel_contents_profile.add(labelUserIconBig);

		// Profile Name section

		lblName = new JTextField(usr.getFirst_name() + " " + usr.getLast_name());
		lblName.setBackground(SystemColor.activeCaption);
		lblName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		lblName.setForeground(SystemColor.inactiveCaptionBorder);
		lblName.setFont(new Font("Century Gothic", Font.BOLD, 58));
		lblName.setBounds(12, 45, 550, 80);
		((AbstractDocument) lblName.getDocument())
				.setDocumentFilter(new com.synergy.exos.uxinterface.filters.CharacterFilter(true));

		lblNameL = new JLabel(usr.getFirst_name() + " " + usr.getLast_name());
		lblNameL.setBackground(SystemColor.activeCaption);
		lblNameL.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		lblNameL.setForeground(SystemColor.inactiveCaptionBorder);
		lblNameL.setFont(new Font("Century Gothic", Font.BOLD, 58));
		lblNameL.setBounds(12, 45, 550, 80);
		panel_contents_profile.add(lblNameL);

		// Profile Name section end
		// Profile DOB section

		labelDobIcon = new JLabel();
		labelDobIcon.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/dob_icon.png")));
		labelDobIcon.setBounds(12, 225, 40, 55);
		panel_contents_profile.add(labelDobIcon);

		lblDOB = new JLabel(usr.getDob().toString());
		lblDOB.setForeground(SystemColor.inactiveCaptionBorder);
		lblDOB.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		lblDOB.setBackground(SystemColor.activeCaption);
		lblDOB.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		lblDOB.setBounds(100, 225, 400, 55);
		panel_contents_profile.add(lblDOB);

		txtDOB = new JTextField(usr.getDob().toString());
		txtDOB.setForeground(SystemColor.inactiveCaptionBorder);
		txtDOB.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		txtDOB.setBackground(SystemColor.activeCaption);
		txtDOB.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtDOB.setBounds(100, 225, 400, 55);

		editDOBSep = new JSeparator();
		editDOBSep.setBounds(100, 285, 400, 5);
		editDOBSep.setBackground(SystemColor.inactiveCaptionBorder);
		editDOBSep.setForeground(SystemColor.inactiveCaptionBorder);

		// Profile DOB Section End
		// Proile Phone Section

		labelPhoneIcon = new JLabel();
		labelPhoneIcon.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/phone_icon.png")));
		labelPhoneIcon.setBounds(12, 160, 40, 55);
		panel_contents_profile.add(labelPhoneIcon);

		lblPhone = new JLabel(usr.getPhone_no().toString());
		lblPhone.setForeground(SystemColor.inactiveCaptionBorder);
		lblPhone.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		lblPhone.setBackground(SystemColor.activeCaption);
		lblPhone.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		lblPhone.setBounds(100, 160, 400, 55);
		panel_contents_profile.add(lblPhone);

		txtPhone = new JTextField(usr.getPhone_no().toString());
		txtPhone.setForeground(SystemColor.inactiveCaptionBorder);
		txtPhone.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		txtPhone.setBackground(SystemColor.activeCaption);
		txtPhone.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtPhone.setBounds(100, 160, 400, 55);
		((AbstractDocument) txtPhone.getDocument())
				.setDocumentFilter(new com.synergy.exos.uxinterface.filters.NumberFilter());

		editPhoneSep = new JSeparator();
		editPhoneSep.setBounds(100, 220, 400, 5);
		editPhoneSep.setBackground(SystemColor.inactiveCaptionBorder);
		editPhoneSep.setForeground(SystemColor.inactiveCaptionBorder);

		// Profile Phone section End
		// Profile Email Section

		labelEmailIcon = new JLabel();
		labelEmailIcon.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/email_icon.png")));
		labelEmailIcon.setBounds(12, 290, 40, 55);
		panel_contents_profile.add(labelEmailIcon);

		lblEmail = new JLabel(usr.getEmail_add());
		lblEmail.setForeground(SystemColor.inactiveCaptionBorder);
		lblEmail.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		lblEmail.setBackground(SystemColor.activeCaption);
		lblEmail.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		lblEmail.setBounds(100, 290, 400, 55);
		panel_contents_profile.add(lblEmail);

		txtEmail = new JTextField(usr.getEmail_add());
		txtEmail.setForeground(SystemColor.inactiveCaptionBorder);
		txtEmail.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		txtEmail.setBackground(SystemColor.activeCaption);
		txtEmail.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtEmail.setBounds(100, 290, 400, 55);

		editEmailSep = new JSeparator();
		editEmailSep.setBounds(100, 350, 400, 5);
		editEmailSep.setBackground(SystemColor.inactiveCaptionBorder);
		editEmailSep.setForeground(SystemColor.inactiveCaptionBorder);

		// Profile Email Section End
		// Profile Account type Section
		labelAcTypeIcon = new JLabel();
		labelAcTypeIcon.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/actype_icon.png")));
		labelAcTypeIcon.setBounds(12, 355, 40, 55);
		panel_contents_profile.add(labelAcTypeIcon);

		lblAcType = new JLabel(usr.getType());
		lblAcType.setForeground(SystemColor.inactiveCaptionBorder);
		lblAcType.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		lblAcType.setBackground(SystemColor.activeCaption);
		lblAcType.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		lblAcType.setBounds(100, 355, 400, 55);
		panel_contents_profile.add(lblAcType);

		// Profile Account Type Section End
		// Profile Joined Section

		labelJoinedIcon = new JLabel();
		labelJoinedIcon.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/joined_icon.png")));
		labelJoinedIcon.setBounds(12, 420, 40, 55);
		panel_contents_profile.add(labelJoinedIcon);

		lblJoined = new JLabel(usr.getAdded_date().toString());
		lblJoined.setForeground(SystemColor.inactiveCaptionBorder);
		lblJoined.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		lblJoined.setBackground(SystemColor.activeCaption);
		lblJoined.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		lblJoined.setBounds(100, 420, 400, 55);
		panel_contents_profile.add(lblJoined);

		// Profile Joined section end
		// Profile User name Section

		lblUsernameIcon = new JLabel();
		lblUsernameIcon.setIcon(new ImageIcon(
				HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/profile_icon_active.png")));
		lblUsernameIcon.setBounds(12, 485, 40, 55);
		panel_contents_profile.add(lblUsernameIcon);

		labelUsername = new JLabel(usr.getUsername());
		labelUsername.setForeground(SystemColor.inactiveCaptionBorder);
		labelUsername.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		labelUsername.setBackground(SystemColor.activeCaption);
		labelUsername.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		labelUsername.setBounds(100, 485, 400, 55);
		panel_contents_profile.add(labelUsername);

		txtUsername = new JTextField(usr.getUsername());
		txtUsername.setForeground(SystemColor.inactiveCaptionBorder);
		txtUsername.setFont(new Font("Century Gothic", Font.PLAIN, 30));
		txtUsername.setBackground(SystemColor.activeCaption);
		txtUsername.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtUsername.setBounds(100, 485, 400, 55);

		editUsernameSep = new JSeparator();
		editUsernameSep.setBounds(100, 545, 400, 5);
		editUsernameSep.setBackground(SystemColor.inactiveCaptionBorder);
		editUsernameSep.setForeground(SystemColor.inactiveCaptionBorder);

		// Profile User name Section end

		JLabel label_edit_profile = new JLabel(" MODIFY");
		label_edit_profile.setBounds(1105, 120, 200, 55);
		panel_contents_profile.add(label_edit_profile);
		label_edit_profile.setForeground(SystemColor.inactiveCaptionBorder);
		label_edit_profile.setFont(new Font("Century Gothic", Font.BOLD, 25));
		label_edit_profile.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/edit_icon.png")));

		JLabel label_delete = new JLabel(" REMOVE");
		label_delete.setBounds(1100, 220, 200, 71);

		label_delete.setForeground(SystemColor.inactiveCaptionBorder);
		label_delete.setFont(new Font("Century Gothic", Font.BOLD, 25));
		panel_contents_profile.add(label_delete);
		label_delete.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/delete_icon.png")));
		label_delete.addMouseListener(new CursorListener(label_delete) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				// Delete the account
				uD.terminate(usr);
				LoginPanel.loginPanel();
				dispose();
			}
		});

		editorSep = new JSeparator();
		editorSep.setBounds(1105, 320, 150, 5);
		editorSep.setBackground(SystemColor.inactiveCaptionBorder);
		editorSep.setForeground(SystemColor.inactiveCaptionBorder);

		editNameSep = new JSeparator();
		editNameSep.setBounds(12, 130, getStringWidth(lblName, lblName.getText()), 20);
		editNameSep.setBackground(SystemColor.inactiveCaptionBorder);
		editNameSep.setForeground(SystemColor.inactiveCaptionBorder);

		// Name TextField Underline dynamic size update
		lblName.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				editNameSep.setBounds(12, 130, getStringWidth(lblName, lblName.getText()), 20);
			}
		});

		lblSaveBtn = new JLabel("");
		lblSaveBtn.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/save_icon.png")));
		lblSaveBtn.setBounds(1110, 360, 40, 40);
		lblSaveBtn.addMouseListener(new CursorListener(lblSaveBtn) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				try {
					usr.setDob(new java.sql.Date(
							((java.util.Date) (new SimpleDateFormat("yyyy-MM-dd")).parse(txtDOB.getText())).getTime()));
					String[] name = lblName.getText().split(" ");
					usr.setFirst_name(name[0]);
					usr.setLast_name(name[1]);
				} catch (ParseException exp) {
					exp.printStackTrace();
				} catch (NullPointerException exp) {
					exp.printStackTrace();
				}
				usr.setPhone_no(txtPhone.getText());
				usr.setEmail_add(txtEmail.getText());
				usr.setUsername(txtUsername.getText());
				// Update The Data
				uD.update(usr);

				// Set Value to the interface
				lblDOB.setText(txtDOB.getText());
				lblNameL.setText(lblName.getText());
				lblPhone.setText(txtPhone.getText());
				lblEmail.setText(txtEmail.getText());
				labelUsername.setText(txtUsername.getText());
				setProfileUneditable();
			}
		});

		lblCancelBtn = new JLabel("");
		lblCancelBtn.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/cancel_icon.png")));
		lblCancelBtn.setBounds(1210, 360, 40, 40);
		lblCancelBtn.addMouseListener(new CursorListener(lblCancelBtn) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (isProfileEditable) {
					{
						setProfileUneditable();
					}
				}
			}
		});

		label_edit_profile.addMouseListener(new CursorListener(label_edit_profile) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				// Write Editor code here
				if (!isProfileEditable) {
					panel_contents_profile.remove(lblNameL);
					panel_contents_profile.add(lblName);
					panel_contents_profile.remove(lblDOB);
					panel_contents_profile.add(txtDOB);
					panel_contents_profile.remove(lblPhone);
					panel_contents_profile.add(txtPhone);
					panel_contents_profile.remove(lblEmail);
					panel_contents_profile.add(txtUsername);
					panel_contents_profile.remove(labelUsername);
					panel_contents_profile.add(txtEmail);
					panel_contents_profile.add(lblSaveBtn);
					panel_contents_profile.add(lblCancelBtn);
					panel_contents_profile.add(editorSep);
					panel_contents_profile.add(editNameSep);
					panel_contents_profile.add(editDOBSep);
					panel_contents_profile.add(editEmailSep);
					panel_contents_profile.add(editPhoneSep);
					panel_contents_profile.add(editUsernameSep);
					lblName.requestFocus();
					isProfileEditable = true;
					repaint();
				}
			}
		});
		// Counter Layered Panel END
		// About Layered Panel
		panellayer_about = new JPanel();
		panellayer_about.setBackground(SystemColor.activeCaption);
		panellayer_about.setBounds(0, 0, 1050, 800);
		layeredPane_contents.add(panellayer_about);
		panellayer_about.setLayout(null);

		panel_title_about = new JPanel();
		panel_title_about.setBounds(0, 0, 1410, 167);
		panel_title_about.setBackground(new Color(0, 102, 153));
		panellayer_about.add(panel_title_about);
		panel_title_about.setLayout(null);

		JLabel lblabout_1 = new JLabel("About");
		lblabout_1.setBounds(40, 34, 300, 87);
		panel_title_about.add(lblabout_1);
		lblabout_1.setForeground(SystemColor.inactiveCaptionBorder);
		lblabout_1.setFont(new Font("Century Gothic", Font.PLAIN, 70));

		JTextArea txtrSThePerfect = new JTextArea();
		txtrSThePerfect.setForeground(SystemColor.inactiveCaptionBorder);
		txtrSThePerfect.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtrSThePerfect.setText(
				"Exos is the perfect solution to your eResource, learning, and discovery requirements.\nCustomisation options, ongoing product development and eXos ensures the library continues\nto effectively support the curriculum and the long-term strategy of your institution. \n\nYour library users will love the convenience and Web2.0 user experience, staff will love\nthe streamlined, workflow-centric experience, management will value the cost savings,\nproductivity and patronage statistics.\n\nWe understand your Special library service is unique. eXos is one of the most flexible\nand easy-to use solutions available to build an information service that is 'fit for purpose' for\nyou and your organisation.\n\neXos provides a stand-alone discovery layer that provides a single ‘Google-like’ search\nfor all your content, in all catalogues no matter the format or even the location.");
		txtrSThePerfect.setEditable(false);
		txtrSThePerfect.setBackground(SystemColor.activeCaption);
		txtrSThePerfect.setBounds(60, 240, 930, 414);
		panellayer_about.add(txtrSThePerfect);

		labelbigabout = new JLabel("");
		labelbigabout.setIcon(
				new ImageIcon(HomePanel.class.getResource("/com/synergy/exos/uxinterface/images/about_icon_big.png")));
		labelbigabout.setBounds(1080, 300, 270, 270);
		panellayer_about.add(labelbigabout);

		// Cursor pointer listener and implementation
		panel_home.addMouseListener(new CursorListener(panel_home) {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (panel_home != selection)
					select(panel_home, panellayer_home);
			}
		});
		panel_about.addMouseListener(new CursorListener(panel_about) {
			@Override
			public void mousePressed(MouseEvent e) {
				if (panel_about != selection)
					select(panel_about, panellayer_about);
			}
		});
		panel_book.addMouseListener(new CursorListener(panel_book) {
			@Override
			public void mousePressed(MouseEvent e) {
				if (panel_book != selection)
					select(panel_book, panellayer_book);
			}
		});
		panel_counter.addMouseListener(new CursorListener(panel_counter) {
			@Override
			public void mousePressed(MouseEvent e) {
				if (panel_counter != selection)
					select(panel_counter, panellayer_counter);
			}
		});
		panel_people.addMouseListener(new CursorListener(panel_people) {
			@Override
			public void mousePressed(MouseEvent e) {
				if (panel_people != selection)
					select(panel_people, panellayer_people);
			}
		});
		panel_profile.addMouseListener(new CursorListener(panel_profile) {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (panel_profile != selection)
					select(panel_profile, panellayer_profile);
			}
		});

		label_menu_btn.addMouseListener(new CursorListener(label_menu_btn));
		panel_logout.addMouseListener(new CursorListener(panel_logout) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				new LoginPanel().setUser(usr).setVisible(true);
				dispose();
			}
		});

		// Menu Toggle Section

		label_menu_btn.addMouseListener(new CursorListener(label_menu_btn) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (isExpanded) {
					panel_left.setBounds(0, 0, 90, 800);
					label_bg_img.setBounds(0, 0, 90, 800);
					panel_right.setBounds(90, 0, 1410, 800);
					layeredPane_contents.setBounds(0, 0, 1410, 800);
					lblX.setBounds(1371, 13, 27, 20);
					setUserTableBig(true, dfl);
					setBooksTableBig(true, bdfl);
					if (usr.isAdmin()) {
						setBooksCounterTableBig(true, cdfl);
					}
					Arrays.asList(layeredPane_contents.getComponents()).parallelStream().forEach((comp) -> {
						comp.setBounds(0, 0, 1410, 800);
					});
					isExpanded = false;
					repaint();
				} else {
					panel_left.setBounds(0, 0, 450, 800);
					label_bg_img.setBounds(0, 0, 450, 800);
					panel_right.setBounds(450, 0, 1050, 800);
					layeredPane_contents.setBounds(0, 0, 1050, 800);
					lblX.setBounds(1011, 13, 27, 20);
					setUserTableBig(false, dfs);
					setBooksTableBig(false, bdfs);
					if (usr.isAdmin()) {
						setBooksCounterTableBig(false, cdfs);
					}
					Arrays.asList(layeredPane_contents.getComponents()).parallelStream().forEach((comp) -> {
						comp.setBounds(0, 0, 1050, 800);
					});
					isExpanded = true;
					repaint();
				}
			}
		});

		// Menu Toggle Section
		// Cursor pointer listener and implementation END
		menuPanels = new JPanel[] { panel_home, panel_profile, panel_about, panel_book, panel_counter, panel_people };
	}

	/**
	 * Selection of panels
	 * @param panel
	 * @param panellayer
	 */
	private void select(JPanel panel, JPanel panellayer) {
		layeredPane_contents.moveToFront(panellayer);
		disableOthers(panellayer);
		Arrays.asList(menuPanels).parallelStream().forEach((temp) -> {
			if (temp != panel)
				resetPanelColor(temp);
		});
		setPanelColor(panel);
		lblX.getParent().remove(lblX);
		((JPanel) panellayer.getComponent(0)).add(lblX);
		selection = panel;
	}

	/**
	 * Menu Selection indicator and painter
	 * @param p
	 */
	private void setPanelColor(JPanel p) {
		p.setOpaque(true);
		StringBuilder sb = new StringBuilder();
		Matcher matcher = Pattern.compile("(.*)(com)(.*)(.png)")
				.matcher(((JLabel) p.getComponent(0)).getIcon().toString());
		if (matcher.find())
			sb.append("/").append(matcher.group(2)).append(matcher.group(3)).append("_active").append(matcher.group(4));
		((JLabel) p.getComponent(0)).setIcon(new ImageIcon(HomePanel.class.getResource(sb.toString())));
		p.getComponent(1).setForeground(SystemColor.inactiveCaptionBorder);
		p.setBackground(SystemColor.activeCaption);
		repaint();
	}

	/**
	 * Focus Repulsion initiator and painter
	 * @param p
	 */
	private void resetPanelColor(JPanel p) {
		Matcher matcher = Pattern.compile("(.*)(com)(.*)(_active.png)")
				.matcher(((JLabel) p.getComponent(0)).getIcon().toString());
		if (matcher.find())
			((JLabel) p.getComponent(0)).setIcon(new ImageIcon(HomePanel.class.getResource(new StringBuilder()
					.append("/").append(matcher.group(2)).append(matcher.group(3)).append(".png").toString())));
		p.getComponent(1).setForeground(SystemColor.inactiveCaption);
		p.setOpaque(false);
		repaint();
	}

	/**
	 * Background panel layers disabling function
	 * @param layer
	 */
	private void disableOthers(JPanel layer) {
		Arrays.asList(layeredPane_contents.getComponents()).parallelStream().forEach((temp) -> {
			Arrays.asList(((JPanel) temp).getComponents()).parallelStream().forEach((inner_panel) -> {
				if ((JPanel) temp != layer)
					inner_panel.setVisible(false);
				else
					inner_panel.setVisible(true);
			});
		});
	}

	/**
	 * Get the Screen width of the string
	 * @param jtxtf
	 * @param txt
	 * @return
	 */
	private static int getStringWidth(JTextField jtxtf, String txt) {
		return (int) (jtxtf.getFont().getStringBounds(txt, new FontRenderContext(new AffineTransform(), true, true))
				.getWidth()) + 10;
	}

	/**
	 * Turn off profile edit
	 */
	private void setProfileUneditable() {
		panel_contents_profile.remove(lblName);
		panel_contents_profile.add(lblNameL);
		panel_contents_profile.remove(txtDOB);
		panel_contents_profile.add(lblDOB);
		panel_contents_profile.remove(txtPhone);
		panel_contents_profile.add(lblPhone);
		panel_contents_profile.remove(txtEmail);
		panel_contents_profile.add(lblEmail);
		panel_contents_profile.remove(txtUsername);
		panel_contents_profile.add(labelUsername);
		panel_contents_profile.remove(lblSaveBtn);
		panel_contents_profile.remove(lblCancelBtn);
		panel_contents_profile.remove(editorSep);
		panel_contents_profile.remove(editNameSep);
		panel_contents_profile.remove(editDOBSep);
		panel_contents_profile.remove(editEmailSep);
		panel_contents_profile.remove(editPhoneSep);
		panel_contents_profile.remove(editUsernameSep);
		isProfileEditable = false;
		repaint();
	}

	/**
	 * 
	 * @param rowCount
	 * @param dtm
	 */
	private void removeRowsFromTable(int rowCount, DefaultTableModel dtm) {
		for (int row = rowCount - 1; row >= 0; row--) {
			dtm.removeRow(row);
		}
	}

	/**
	 * 
	 * @param flag
	 * @param df
	 */
	private void setUserTableBig(boolean flag, DefaultTableModel df) {
		if (flag) {
			table.setModel(df);
			scrollPane.setBounds(40, 180, 1330, 600);

			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			removeRowsFromTable(dtm.getRowCount(), dtm);

			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(190);
			table.getColumnModel().getColumn(1).setPreferredWidth(190);
			table.getColumnModel().getColumn(2).setPreferredWidth(330);
			table.getColumnModel().getColumn(3).setPreferredWidth(147);
			table.getColumnModel().getColumn(4).setPreferredWidth(125);
			table.getColumnModel().getColumn(5).setPreferredWidth(125);
			table.getColumnModel().getColumn(6).setPreferredWidth(220);

			(uD.fetch(UsersFetch.FETCH_ALL, null)).forEach((aUser) -> {
				dtm.addRow(new Object[] { aUser.getFirst_name() + " " + aUser.getLast_name(), aUser.getUsername(),
						aUser.getEmail_add(), aUser.getType(), aUser.getPhone_no(), aUser.getDob().toString(),
						aUser.getAdded_date().toString() });
			});

			(Collections.list(table.getColumnModel().getColumns())).parallelStream().forEach((column) -> {
				column.setCellRenderer(dtr);
			});
		} else {
			table.setModel(df);
			scrollPane.setBounds(40, 180, 980, 600);

			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			removeRowsFromTable(dtm.getRowCount(), dtm);

			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(190);
			table.getColumnModel().getColumn(1).setPreferredWidth(190);
			table.getColumnModel().getColumn(2).setPreferredWidth(450);
			table.getColumnModel().getColumn(3).setPreferredWidth(147);

			(uD.fetch(UsersFetch.FETCH_ALL, null)).forEach((aUser) -> {
				dtm.addRow(new Object[] { aUser.getFirst_name() + " " + aUser.getLast_name(), aUser.getUsername(),
						aUser.getEmail_add(), aUser.getType() });
			});

			(Collections.list(table.getColumnModel().getColumns())).parallelStream().forEach((column) -> {
				column.setCellRenderer(dtr);
			});

		}
	}
	
	/**
	 * 
	 * @param flag
	 * @param df
	 */
	private void setBooksTableBig(boolean flag, DefaultTableModel df) {

		if (flag) {
			btable.setModel(df);
			bscrollPane.setBounds(40, 180, 1330, 600);

			DefaultTableModel dtm = (DefaultTableModel) btable.getModel();
			removeRowsFromTable(dtm.getRowCount(), dtm);

			btable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			btable.getColumnModel().getColumn(0).setPreferredWidth(170);
			btable.getColumnModel().getColumn(1).setPreferredWidth(220);
			btable.getColumnModel().getColumn(2).setPreferredWidth(387);
			btable.getColumnModel().getColumn(3).setPreferredWidth(90);
			btable.getColumnModel().getColumn(4).setPreferredWidth(170);
			btable.getColumnModel().getColumn(5).setPreferredWidth(190);
			btable.getColumnModel().getColumn(6).setPreferredWidth(100);

			for (Books aBook : bd.fetch(BooksFetch.FETCH_ALL, null)) {
				StringBuilder authors = new StringBuilder();
				List<Author> authorList = aBook.getAuthor();
				for (Author a : authorList) {
					if (authorList.indexOf(a) != authorList.size() - 1) {
						authors.append(a.getFirst_name() + " " + a.getLast_name() + ", ");
					} else {
						authors.append(a.getFirst_name() + " " + a.getLast_name());
					}
				}
				dtm.addRow(new Object[] { aBook.getIsbn(), aBook.getTitle(), authors.toString(),
						aBook.getCopies_available(), aBook.getGenre(), aBook.getPublisher().getName(),
						aBook.getEdition() });
			}

			for (int i = 0; i < btable.getColumnCount(); i++) {
				btable.getColumnModel().getColumn(i).setCellRenderer(dtr);
			}
		} else {
			btable.setModel(df);
			bscrollPane.setBounds(40, 180, 980, 600);

			DefaultTableModel dtm = (DefaultTableModel) btable.getModel();
			removeRowsFromTable(dtm.getRowCount(), dtm);

			btable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			btable.getColumnModel().getColumn(0).setPreferredWidth(170);
			btable.getColumnModel().getColumn(1).setPreferredWidth(230);
			btable.getColumnModel().getColumn(2).setPreferredWidth(460);
			btable.getColumnModel().getColumn(3).setPreferredWidth(117);

			for (Books aBook : bd.fetch(BooksFetch.FETCH_ALL, null)) {
				StringBuilder authors = new StringBuilder();
				List<Author> authorList = aBook.getAuthor();
				for (Author a : authorList) {
					if (authorList.indexOf(a) != authorList.size() - 1) {
						authors.append(a.getFirst_name() + " " + a.getLast_name() + ", ");
					} else {
						authors.append(a.getFirst_name() + " " + a.getLast_name());
					}
				}
				dtm.addRow(new Object[] { aBook.getIsbn(), aBook.getTitle(), authors.toString(),
						aBook.getCopies_available() });
			}

			for (int i = 0; i < btable.getColumnCount(); i++) {
				btable.getColumnModel().getColumn(i).setCellRenderer(dtr);
			}

		}
	}

	/**
	 * 
	 * @param flag
	 * @param df
	 */
	private void setBooksCounterTableBig(boolean flag, DefaultTableModel df) {

		if (flag) {
			ctable.setModel(df);
			cscrollPane.setBounds(40, 240, 1330, 400);

			DefaultTableModel dtm = (DefaultTableModel) ctable.getModel();
			removeRowsFromTable(dtm.getRowCount(), dtm);

			ctable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			ctable.getColumnModel().getColumn(0).setPreferredWidth(170);
			ctable.getColumnModel().getColumn(1).setPreferredWidth(220);
			ctable.getColumnModel().getColumn(2).setPreferredWidth(387);
			ctable.getColumnModel().getColumn(3).setPreferredWidth(90);
			ctable.getColumnModel().getColumn(4).setPreferredWidth(170);
			ctable.getColumnModel().getColumn(5).setPreferredWidth(190);
			ctable.getColumnModel().getColumn(6).setPreferredWidth(100);

			for (Vector<Object> row : selectedBooks) {
				for (Books aBook : bd.fetch(BooksFetch.FETCH_BY_ISBN, row.get(0).toString())) {
					StringBuilder authors = new StringBuilder();
					List<Author> authorList = aBook.getAuthor();
					for (Author a : authorList) {
						if (authorList.indexOf(a) != authorList.size() - 1) {
							authors.append(a.getFirst_name() + " " + a.getLast_name() + ", ");
						} else {
							authors.append(a.getFirst_name() + " " + a.getLast_name());
						}
					}
					dtm.addRow(new Object[] { aBook.getIsbn(), aBook.getTitle(), authors.toString(),
							aBook.getCopies_available(), aBook.getGenre(), aBook.getPublisher().getName(),
							aBook.getEdition() });
				}
			}

			for (int i = 0; i < btable.getColumnCount(); i++) {
				ctable.getColumnModel().getColumn(i).setCellRenderer(dtr);
			}
		} else {
			ctable.setModel(df);
			cscrollPane.setBounds(40, 240, 980, 400);

			DefaultTableModel dtm = (DefaultTableModel) ctable.getModel();
			removeRowsFromTable(dtm.getRowCount(), dtm);

			ctable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			ctable.getColumnModel().getColumn(0).setPreferredWidth(170);
			ctable.getColumnModel().getColumn(1).setPreferredWidth(230);
			ctable.getColumnModel().getColumn(2).setPreferredWidth(460);
			ctable.getColumnModel().getColumn(3).setPreferredWidth(117);

			for (Vector<Object> row : selectedBooks) {
				dtm.addRow(row);
			}
			for (int i = 0; i < btable.getColumnCount(); i++) {
				btable.getColumnModel().getColumn(i).setCellRenderer(dtr);
			}

		}
	}

	/**
	 * 
	 * @author G-Kesh
	 */
	private static class MessageDialog extends JDialog {
		private static final long serialVersionUID = 1L;
		private JPanel messageContents, panelTitle, panelBody;
		private Dimension rootSize = new Dimension(500, 300);
		private JTable dtable;
		private List<Vector<Object>> blist;
		
		/**
		 * 
		 * @param message
		 * @param selectedRow
		 * @param dtable
		 * @param blist
		 */
		private void createDialog(String message, int selectedRow, JTable dtable, List<Vector<Object>> blist) {
			this.dtable = dtable;
			this.blist = blist;
			messageContents = new JPanel();
			messageContents.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.setContentPane(messageContents);
			messageContents.setLayout(null);
			setUndecorated(true);
			DragListener dragListener = new DragListener(this);
			this.addMouseListener(dragListener);
			this.addMouseMotionListener(dragListener);
			this.setVisible(true);
			this.setLayout(null);
			this.setSize(rootSize);
			this.setLocation(500, 300);
			this.setLocationRelativeTo(contentPane);
			messageContents.setSize(rootSize);

			panelTitle = new JPanel();
			panelTitle.setLayout(null);
			panelTitle.setBounds(0, 0, 500, 50);
			panelTitle.setBackground(SystemColor.inactiveCaptionBorder);
			panelTitle.setForeground(new Color(0, 102, 153));
			messageContents.add(panelTitle);

			JLabel lblX = new JLabel("X");
			lblX.setForeground(new Color(0, 102, 153));
			lblX.setBounds(450, 12, 25, 25);
			lblX.setHorizontalAlignment(SwingConstants.RIGHT);
			lblX.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
			panelTitle.add(lblX);
			lblX.addMouseListener(new CursorListener(lblX) {
				@Override
				public void mouseClicked(MouseEvent evt) {
					dispose();
				}
			});

			JLabel lblTitle = new JLabel("Confirmation");
			lblTitle.setForeground(new Color(0, 102, 153));
			lblTitle.setBounds(20, 12, 250, 25);
			lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
			panelTitle.add(lblTitle);

			panelBody = new JPanel();
			panelBody.setLayout(null);
			panelBody.setBounds(0, 0, 500, 300);
			panelBody.setBackground(new Color(0, 102, 153));
			panelBody.setForeground(SystemColor.inactiveCaptionBorder);
			messageContents.add(panelBody);

			JLabel lblMessage = new JLabel(message);
			lblMessage.setForeground(SystemColor.inactiveCaptionBorder);
			lblMessage.setBounds(20, 0, 460, 250);
			lblMessage.setHorizontalAlignment(SwingConstants.LEFT);
			lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 30));
			panelBody.add(lblMessage);

			JButton btnYes = new JButton("Yes");
			btnYes.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnYes.setBackground(SystemColor.inactiveCaptionBorder);
			btnYes.setForeground(new Color(0, 102, 153));
			btnYes.setBounds(125, 200, 100, 50);
			panelBody.add(btnYes);
			btnYes.addMouseListener(new CursorListener(btnYes) {
				@Override
				public void mouseClicked(MouseEvent evt) {
					removeBookFromCounter(selectedRow);
					dispose();
				}
			});

			JButton btnNo = new JButton("No");
			btnNo.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnNo.setBackground(SystemColor.inactiveCaptionBorder);
			btnNo.setForeground(new Color(0, 102, 153));
			btnNo.setBounds(275, 200, 100, 50);
			panelBody.add(btnNo);
			btnNo.addMouseListener(new CursorListener(btnNo) {
				@Override
				public void mouseClicked(MouseEvent evt) {
					dispose();
				}
			});

		}

		/**
		 * 
		 * @param selectedRow
		 */
		private void removeBookFromCounter(int selectedRow) {
			((DefaultTableModel) dtable.getModel()).removeRow(selectedRow);
			BooksDB bd = new BooksDB();
			Books tempBook = bd.fetch(BooksFetch.FETCH_BY_ISBN, blist.get(selectedRow).get(0).toString()).get(0);
			tempBook.setCopies_available(tempBook.getCopies_available() + 1);
			bd.update(tempBook, null, null);
			blist.remove(selectedRow);
			new NotificationDialog().createNotification("Book returned to the library!");
		}
	}


}
