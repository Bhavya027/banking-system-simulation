import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BankingApp extends JFrame {

    // ── Colors ──────────────────────────────────────────────────────────────
    private static final Color BG        = new Color(10, 14, 26);
    private static final Color PANEL_BG  = new Color(18, 24, 42);
    private static final Color ACCENT    = new Color(79, 172, 254);
    private static final Color ACCENT2   = new Color(0, 242, 174);
    private static final Color TEXT      = new Color(220, 230, 255);
    private static final Color MUTED     = new Color(100, 120, 160);
    private static final Color SUCCESS   = new Color(0, 230, 130);
    private static final Color ERROR     = new Color(255, 80, 100);
    private static final Color CARD_BG   = new Color(25, 35, 60);

    private final BankService service = new BankService();

    // Input fields
    private JTextField tfAccNumber, tfHolder, tfAmount;
    private JComboBox<String> cbType;
    private JTextArea taOutput;

    public BankingApp() {
        setTitle("NexaBank — Banking System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(860, 640);
        setMinimumSize(new Dimension(700, 520));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 0));

        add(buildHeader(),  BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    // ── Header ───────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(PANEL_BG);
        p.setBorder(new MatteBorder(0, 0, 1, 0, new Color(40, 60, 100)));
        p.setPreferredSize(new Dimension(0, 58));

        JLabel logo = new JLabel("  ◈  NEXABANK");
        logo.setFont(new Font("Monospaced", Font.BOLD, 18));
        logo.setForeground(ACCENT);

        JLabel tagline = new JLabel("Simulation Console  ");
        tagline.setFont(new Font("Monospaced", Font.PLAIN, 12));
        tagline.setForeground(MUTED);

        p.add(logo, BorderLayout.WEST);
        p.add(tagline, BorderLayout.EAST);
        return p;
    }

    // ── Center (inputs + output) ─────────────────────────────────────────────
    private JSplitPane buildCenter() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildInputPanel(), buildOutputPanel());
        split.setDividerLocation(340);
        split.setDividerSize(4);
        split.setBackground(BG);
        split.setBorder(null);
        return split;
    }

    private JPanel buildInputPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);
        outer.setBorder(new EmptyBorder(20, 16, 20, 8));

        JPanel card = new JPanel();
        card.setBackground(CARD_BG);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(40, 65, 110), 1, true),
                new EmptyBorder(20, 20, 20, 20)));

        card.add(sectionLabel("ACCOUNT DETAILS"));
        card.add(Box.createVerticalStrut(10));

        tfAccNumber = styledField("e.g. ACC001");
        tfHolder    = styledField("e.g. Priya Sharma");
        tfAmount    = styledField("e.g. 5000");

        cbType = new JComboBox<>(new String[]{"SAVINGS", "CURRENT"});
        styleCombo(cbType);

        card.add(fieldRow("Account No",  tfAccNumber));
        card.add(Box.createVerticalStrut(10));
        card.add(fieldRow("Holder Name", tfHolder));
        card.add(Box.createVerticalStrut(10));
        card.add(fieldRow("Type",        cbType));
        card.add(Box.createVerticalStrut(10));
        card.add(fieldRow("Amount (₹)",  tfAmount));
        card.add(Box.createVerticalStrut(22));

        card.add(sectionLabel("ACTIONS"));
        card.add(Box.createVerticalStrut(12));

        card.add(actionBtn("⊕  Create Account", ACCENT,   e -> doCreate()));
        card.add(Box.createVerticalStrut(8));
        card.add(actionBtn("↑  Deposit",         ACCENT2,  e -> doDeposit()));
        card.add(Box.createVerticalStrut(8));
        card.add(actionBtn("↓  Withdraw",        new Color(255,140,80), e -> doWithdraw()));
        card.add(Box.createVerticalStrut(8));
        card.add(actionBtn("≡  Display Account", new Color(180,130,255), e -> doDisplay()));
        card.add(Box.createVerticalStrut(8));
        card.add(actionBtn("❖  List All",        MUTED,    e -> doListAll()));

        outer.add(card, BorderLayout.CENTER);
        return outer;
    }

    private JPanel buildOutputPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);
        outer.setBorder(new EmptyBorder(20, 8, 20, 16));

        // header row
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(BG);
        JLabel lbl = new JLabel("OUTPUT CONSOLE");
        lbl.setFont(new Font("Monospaced", Font.BOLD, 11));
        lbl.setForeground(MUTED);
        lbl.setBorder(new EmptyBorder(0, 4, 6, 0));

        JButton clear = new JButton("CLEAR");
        clear.setFont(new Font("Monospaced", Font.PLAIN, 10));
        clear.setForeground(MUTED);
        clear.setBackground(CARD_BG);
        clear.setBorder(new EmptyBorder(2, 8, 2, 8));
        clear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clear.addActionListener(e -> taOutput.setText(""));
        row.add(lbl, BorderLayout.WEST);
        row.add(clear, BorderLayout.EAST);

        taOutput = new JTextArea();
        taOutput.setEditable(false);
        taOutput.setBackground(new Color(10, 14, 26));
        taOutput.setForeground(new Color(140, 210, 255));
        taOutput.setFont(new Font("Monospaced", Font.PLAIN, 13));
        taOutput.setMargin(new Insets(12, 12, 12, 12));
        taOutput.setLineWrap(true);
        taOutput.setText("» NexaBank console ready.\n» Use the panel on the left to begin.\n");

        JScrollPane scroll = new JScrollPane(taOutput);
        scroll.setBorder(new LineBorder(new Color(30, 50, 90), 1, true));
        scroll.setBackground(BG);

        outer.add(row,    BorderLayout.NORTH);
        outer.add(scroll, BorderLayout.CENTER);
        return outer;
    }

    private JPanel buildFooter() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(PANEL_BG);
        p.setBorder(new MatteBorder(1, 0, 0, 0, new Color(40, 60, 100)));
        JLabel l = new JLabel("Savings minimum ₹500  |  Current overdraft ₹10,000  |  Data persisted to accounts.dat");
        l.setFont(new Font("Monospaced", Font.PLAIN, 11));
        l.setForeground(MUTED);
        p.add(l);
        return p;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Monospaced", Font.BOLD, 10));
        l.setForeground(MUTED);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JTextField styledField(String placeholder) {
        JTextField f = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(MUTED);
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    g2.drawString(placeholder, 8, getHeight() / 2 + 5);
                }
            }
        };
        f.setBackground(new Color(15, 20, 38));
        f.setForeground(TEXT);
        f.setCaretColor(ACCENT);
        f.setFont(new Font("Monospaced", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(50, 80, 130), 1, true),
                new EmptyBorder(6, 8, 6, 8)));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        return f;
    }

    private void styleCombo(JComboBox<String> cb) {
        cb.setBackground(new Color(15, 20, 38));
        cb.setForeground(TEXT);
        cb.setFont(new Font("Monospaced", Font.PLAIN, 13));
        cb.setBorder(new LineBorder(new Color(50, 80, 130), 1, true));
        cb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
    }

    private JPanel fieldRow(String label, JComponent field) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(CARD_BG);
        p.setAlignmentX(LEFT_ALIGNMENT);
        JLabel l = new JLabel(label);
        l.setFont(new Font("Monospaced", Font.PLAIN, 11));
        l.setForeground(MUTED);
        l.setAlignmentX(LEFT_ALIGNMENT);
        field.setAlignmentX(LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(4));
        p.add(field);
        return p;
    }

    private JButton actionBtn(String text, Color fg, ActionListener al) {
        JButton b = new JButton(text);
        b.setFont(new Font("Monospaced", Font.BOLD, 13));
        b.setForeground(fg);
        b.setBackground(new Color(20, 30, 55));
        b.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(fg.darker(), 1, true),
                new EmptyBorder(8, 14, 8, 14)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFocusPainted(false);
        b.setAlignmentX(LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        b.addActionListener(al);
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(30, 45, 80)); }
            public void mouseExited(MouseEvent e)  { b.setBackground(new Color(20, 30, 55)); }
        });
        return b;
    }

    private void log(String msg, Color color) {
        // append styled text via simple prefix markers
        taOutput.append(msg + "\n");
        taOutput.setCaretPosition(taOutput.getDocument().getLength());
    }

    private void ok(String msg)  { log("✔  " + msg, SUCCESS); }
    private void err(String msg) { log("✘  " + msg, ERROR);   }
    private void info(String msg){ log("»  " + msg, ACCENT);  }

    // ── Actions ───────────────────────────────────────────────────────────────
    private void doCreate() {
        try {
            String num    = tfAccNumber.getText().trim();
            String holder = tfHolder.getText().trim();
            String type   = (String) cbType.getSelectedItem();
            double bal    = Double.parseDouble(tfAmount.getText().trim());
            if (num.isEmpty() || holder.isEmpty()) { err("Account number and holder name are required."); return; }
            BankAccount acc = service.createAccount(type, num, holder, bal);
            ok("Account created: " + acc);
        } catch (Exception ex) { err(ex.getMessage()); }
    }

    private void doDeposit() {
        try {
            service.deposit(tfAccNumber.getText().trim(), Double.parseDouble(tfAmount.getText().trim()));
            ok("Deposit successful. " + service.getAccount(tfAccNumber.getText().trim()));
        } catch (Exception ex) { err(ex.getMessage()); }
    }

    private void doWithdraw() {
        try {
            service.withdraw(tfAccNumber.getText().trim(), Double.parseDouble(tfAmount.getText().trim()));
            ok("Withdrawal successful. " + service.getAccount(tfAccNumber.getText().trim()));
        } catch (Exception ex) { err(ex.getMessage()); }
    }

    private void doDisplay() {
        try {
            info(service.getAccount(tfAccNumber.getText().trim()).toString());
        } catch (Exception ex) { err(ex.getMessage()); }
    }

    private void doListAll() {
        Collection<BankAccount> all = service.getAllAccounts();
        if (all.isEmpty()) { info("No accounts found."); return; }
        info("── All Accounts (" + all.size() + ") ──────────────────");
        all.forEach(a -> log("   " + a, TEXT));
        log("", TEXT);
    }

    // ── Entry point ───────────────────────────────────────────────────────────
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new BankingApp().setVisible(true));
    }
}
