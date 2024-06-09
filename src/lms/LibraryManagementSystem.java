package lms;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LibraryManagementSystem {
    private JFrame frame;
    private JPanel cardPanel, homePanel, bookPanel, memberPanel, transactionPanel;
    private CardLayout cardLayout;
    private JTable bookTable, memberTable, transactionTable;
    private JTextField titleField, authorField, publisherField, yearField;
    private JTextField nameField, emailField, phoneField;
    private JButton addBookButton, loadBooksButton, addMemberButton, loadMembersButton, issueBookButton, returnBookButton, loadTransactionsButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    LibraryManagementSystem window = new LibraryManagementSystem();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LibraryManagementSystem() {
        initialize();
    }

    private void initialize() {
                frame = new JFrame("Library Management System");
        frame.setBounds(100, 100, 1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Create CardLayout for main content area
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Login Panel
        JPanel loginPanel = createLoginPanel();
        cardPanel.add(loginPanel, "LoginPanel");

        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        // Show login panel on startup
        cardLayout.show(cardPanel, "LoginPanel");
    }

    private JButton createNavButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(new Color(70, 130, 180)); // Steel blue background
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.addActionListener(actionListener);
        return button;
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Books"));
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 248, 255)); // Alice blue background

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBackground(new Color(240, 248, 255));

        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        inputPanel.add(authorField);

        inputPanel.add(new JLabel("Publisher:"));
        publisherField = new JTextField();
        inputPanel.add(publisherField);

        inputPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        inputPanel.add(yearField);

        addBookButton = createStyledButton("Add Book", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addBook();
                } catch (SQLException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        inputPanel.add(addBookButton);

        loadBooksButton = createStyledButton("Load Books", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadBooks();
                } catch (SQLException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        inputPanel.add(loadBooksButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        JScrollPane bookScrollPane = new JScrollPane();
        bookTable = new JTable();
        bookScrollPane.setViewportView(bookTable);
        panel.add(bookScrollPane, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Back to Home", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel("HomePanel");
            }
        });
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createMemberPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Members"));
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 220)); // Beige background

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(new Color(245, 245, 220));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
            inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        addMemberButton = createStyledButton("Add Member", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addMember();
                } catch (SQLException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        inputPanel.add(addMemberButton);

        loadMembersButton = createStyledButton("Load Members", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadMembers();
                } catch (SQLException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        inputPanel.add(loadMembersButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        JScrollPane memberScrollPane = new JScrollPane();
        memberTable = new JTable();
        memberScrollPane.setViewportView(memberTable);
        panel.add(memberScrollPane, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Back to Home", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel("HomePanel");
            }
        });
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Transactions"));
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 255, 240)); // Honeydew background

        JPanel inputPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        inputPanel.setBackground(new Color(240, 255, 240));

        issueBookButton = createStyledButton("Issue Book", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    issueBook();
                } catch (SQLException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        inputPanel.add(issueBookButton);

        returnBookButton = createStyledButton("Return Book", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    returnBook();
                } catch (SQLException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        inputPanel.add(returnBookButton);

        loadTransactionsButton = createStyledButton("Load Transactions", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadTransactions();
                } catch (SQLException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LibraryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        inputPanel.add(loadTransactionsButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        JScrollPane transactionScrollPane = new JScrollPane();
        transactionTable = new JTable();
        transactionScrollPane.setViewportView(transactionTable);
        panel.add(transactionScrollPane, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Back to Home", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel("HomePanel");
            }
        });
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStyledButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(new Color(60, 179, 113)); // Medium sea green background
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(actionListener);
        return button;
    }

    private void switchPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }
    private void addBook() throws SQLException, ClassNotFoundException {
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        int year = Integer.parseInt(yearField.getText());

        String query = "INSERT INTO books (title, author, publisher, year, available) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, publisher);
            stmt.setInt(4, year);
            stmt.setBoolean(5, true);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error adding book.");
        }
    }

    private void loadBooks() throws SQLException, ClassNotFoundException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setYear(rs.getInt("year"));
                book.setAvailable(rs.getBoolean("available"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] columnNames = {"ID", "Title", "Author", "Publisher", "Year", "Available"};
        Object[][] data = new Object[books.size()][6];
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            data[i][0] = book.getId();
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthor();
            data[i][3] = book.getPublisher();
            data[i][4] = book.getYear();
            data[i][5] = book.isAvailable();
        }
        bookTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void addMember() throws SQLException, ClassNotFoundException {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        String query = "INSERT INTO members (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();  
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Member added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error adding member.");
        }
    }

    private void loadMembers() throws SQLException, ClassNotFoundException {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM members";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPhone(rs.getString("phone"));
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] columnNames = {"ID", "Name", "Email", "Phone"};
        Object[][] data = new Object[members.size()][4];
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            data[i][0] = member.getId();
            data[i][1] = member.getName();
            data[i][2] = member.getEmail();
            data[i][3] = member.getPhone();
        }
        memberTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void issueBook() throws SQLException, ClassNotFoundException {
        int bookId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Book ID:"));
        int memberId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Member ID:"));
        java.sql.Date issueDate = new java.sql.Date(System.currentTimeMillis());

        String query = "INSERT INTO transactions (book_id, member_id, issue_date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, memberId);
            stmt.setDate(3, issueDate);
            stmt.setString(4, "Issued");
            stmt.executeUpdate();

            String updateQuery = "UPDATE books SET available = false WHERE id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(frame, "Book issued successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error issuing book.");
        }
    }

    private void returnBook() throws SQLException, ClassNotFoundException {
        int transactionId = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Transaction ID:"));
        java.sql.Date returnDate = new java.sql.Date(System.currentTimeMillis());

        String query = "UPDATE transactions SET return_date = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, returnDate);
            stmt.setString(2, "Returned");
            stmt.setInt(3, transactionId);
            stmt.executeUpdate();

            String selectQuery = "SELECT book_id FROM transactions WHERE id = ?";
            int bookId = -1;
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setInt(1, transactionId);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        bookId = rs.getInt("book_id");
                    }
                }
            }

            if (bookId != -1) {
                String updateQuery = "UPDATE books SET available = true WHERE id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, bookId);
                    updateStmt.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(frame, "Book returned successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error returning book.");
        }
    }

    private void loadTransactions() throws SQLException, ClassNotFoundException {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setBookId(rs.getInt("book_id"));
                transaction.setMemberId(rs.getInt("member_id"));
                transaction.setIssueDate(rs.getDate("issue_date"));
                transaction.setReturnDate(rs.getDate("return_date"));
                transaction.setStatus(rs.getString("status"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] columnNames = {"ID", "Book ID", "Member ID", "Issue Date", "Return Date", "Status"};
        Object[][] data = new Object[transactions.size()][6];
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            data[i][0] = transaction.getId();
            data[i][1] = transaction.getBookId();
            data[i][2] = transaction.getMemberId();
            data[i][3] = transaction.getIssueDate();
            data[i][4] = transaction.getReturnDate();
            data[i][5] = transaction.getStatus();
        }
        transactionTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
    private void initializeDashboard() {
    // Create panels for the main dashboard
    homePanel = createHomePanel();
    bookPanel = createBookPanel();
    memberPanel = createMemberPanel();
    transactionPanel = createTransactionPanel();

    // Add panels to cardPanel and set card layout
    cardPanel.add(homePanel, "HomePanel");
    cardPanel.add(bookPanel, "BookPanel");
    cardPanel.add(memberPanel, "MemberPanel");
    cardPanel.add(transactionPanel, "TransactionPanel");

    // Switch to homePanel or the desired initial panel
    cardLayout.show(cardPanel, "HomePanel");
}
private JPanel createLoginPanel() {
    JPanel loginPanel = new JPanel(new GridBagLayout());
    loginPanel.setBackground(new Color(240, 248, 255)); // Light blue background
    loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(10, 10, 10, 10);

    // Username Label
    JLabel usernameLabel = new JLabel("Username:");
    usernameLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
    loginPanel.add(usernameLabel, gbc);

    // Username Field
    gbc.gridy++;
    usernameField = new JTextField(20);
    usernameField.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
    loginPanel.add(usernameField, gbc);

    // Password Label
    gbc.gridy++;
    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
    loginPanel.add(passwordLabel, gbc);

    // Password Field
    gbc.gridy++;
    passwordField = new JPasswordField(20);
    passwordField.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
    loginPanel.add(passwordField, gbc);

    // Login Button
    gbc.gridy++;
    JButton loginButton = new JButton("Login");
    loginButton.setBackground(new Color(70, 130, 180)); // Steel blue background
    loginButton.setForeground(Color.WHITE);
    loginButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
    loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Validate username and password
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            // Your authentication logic here
            if (authenticate(username, String.valueOf(password))) {
                // Login successful, proceed to main dashboard
                initializeDashboard();
            } else {
                // Invalid credentials, show error message
                JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
            // Clear password field for security
            passwordField.setText("");
        }
    });
    loginPanel.add(loginButton, gbc);

    // Add padding at the bottom
    gbc.gridy++;
    gbc.weighty = 1; // Add weight to push components upwards
    loginPanel.add(Box.createGlue(), gbc);

    return loginPanel;
}

    private boolean authenticate(String username, String password) {

        return username.equals("admin") && password.equals("admin");
    }
private JPanel createHomePanel() {
    JPanel home_Panel = new JPanel();
    home_Panel.setLayout(new GridBagLayout());   
    home_Panel.setBackground(new Color(173, 216, 230)); // Light blue background

    // Create navigation buttons
    JButton bookButton = createNavButton("Manage Books", new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            switchPanel("BookPanel");
        }
    });
    JButton memberButton = createNavButton("Manage Members", new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            switchPanel("MemberPanel");
        }
    });
    JButton transactionButton = createNavButton("Manage Transactions", new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            switchPanel("TransactionPanel");
        }
    });

    // Add navigation buttons to the home panel
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    gbc.gridx = 0;
    gbc.gridy = 0;
    home_Panel.add(bookButton, gbc);

    gbc.gridy = 1;
    home_Panel.add(memberButton, gbc);

    gbc.gridy = 2;
    home_Panel.add(transactionButton, gbc);

    return home_Panel;
}


}
