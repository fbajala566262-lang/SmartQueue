package View;

import javax.swing.*;
import java.awt.*;

class LoginBulletinScreen extends JPanel {
    private boolean registerMode = false;
    private JLabel headingLbl, subLbl, alertLbl;
    private JPanel nameGroup;
    private JTextField nameField, emailField;
    private JPasswordField passField;
    private JButton submitBtn, switchBtn;

    public LoginBulletinScreen(SmartQueueUI app, java.util.function.Consumer<String> onSuccess) {
        setLayout(new GridLayout(1, 2));
        setBackground(SmartQueueUI.BG_CREAM);
        add(buildLoginColumn(onSuccess));
        add(buildBulletinColumn());
    }

    private JPanel buildLoginColumn(java.util.function.Consumer<String> onSuccess) {
        JPanel col = new JPanel(new BorderLayout());
        col.setBackground(SmartQueueUI.BG_WHITE);
        col.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, SmartQueueUI.BORDER_STONE),
                BorderFactory.createEmptyBorder(28, 48, 28, 48)));

        // Top Bar
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JPanel leftBrand = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftBrand.setOpaque(false);
        JLabel brand = new JLabel("SMARTQUEUE");
        brand.setFont(SmartQueueUI.FONT_BRAND);
        brand.setForeground(SmartQueueUI.INK_DARK);
        leftBrand.add(brand);
        leftBrand.add(SmartQueueUI.pill("UNIVERSITY CANTEEN", SmartQueueUI.BG_SAND, SmartQueueUI.INK_TAUPE));

        JLabel tag = new JLabel("Good food. Less waiting.");
        tag.setFont(SmartQueueUI.FONT_SMALL);
        tag.setForeground(SmartQueueUI.INK_TAUPE);
        top.add(leftBrand, BorderLayout.WEST);
        top.add(tag, BorderLayout.EAST);
        col.add(top, BorderLayout.NORTH);

        // Center Form
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);
        form.setPreferredSize(new Dimension(400, 490));

        JLabel eye = new JLabel("YOUR CAMPUS. YOUR LUNCH BREAK.");
        eye.setFont(SmartQueueUI.FONT_EYEBROW);
        eye.setForeground(SmartQueueUI.TERRACOTTA);
        eye.setAlignmentX(Component.LEFT_ALIGNMENT);

        headingLbl = new JLabel("<html>Welcome back!<br>Ready to skip the queue?</html>");
        headingLbl.setFont(SmartQueueUI.FONT_HERO);
        headingLbl.setForeground(SmartQueueUI.INK_DARK);
        headingLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        subLbl = new JLabel("<html>Your next campus favourite is a few clicks away.<br>Log in with your school account to get started.</html>");
        subLbl.setFont(SmartQueueUI.FONT_BODY);
        subLbl.setForeground(SmartQueueUI.INK_TAUPE);
        subLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        alertLbl = new JLabel(" ");
        alertLbl.setFont(SmartQueueUI.FONT_SMALL_BOLD);
        alertLbl.setOpaque(true);
        alertLbl.setBackground(SmartQueueUI.AMBER_BG);
        alertLbl.setForeground(SmartQueueUI.AMBER_TEXT);
        alertLbl.setBorder(BorderFactory.createEmptyBorder(7, 10, 7, 10));
        alertLbl.setVisible(false);
        alertLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        nameGroup = new JPanel();
        nameGroup.setLayout(new BoxLayout(nameGroup, BoxLayout.Y_AXIS));
        nameGroup.setOpaque(false);
        nameGroup.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel nameLbl = new JLabel("Full name");
        nameLbl.setFont(SmartQueueUI.FONT_BODY_BOLD);
        nameField = styledField("");
        nameGroup.add(nameLbl);
        nameGroup.add(Box.createVerticalStrut(4));
        nameGroup.add(nameField);
        nameGroup.add(Box.createVerticalStrut(10));
        nameGroup.setVisible(false);

        JLabel emailLbl = new JLabel("School email");
        emailLbl.setFont(SmartQueueUI.FONT_BODY_BOLD);
        emailLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField = styledField("you@school.edu");
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel emailHelp = new JLabel("Use the email address provided by your school.");
        emailHelp.setFont(SmartQueueUI.FONT_SMALL);
        emailHelp.setForeground(SmartQueueUI.INK_MUTED);
        emailHelp.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel passTop = new JPanel(new BorderLayout());
        passTop.setOpaque(false);
        passTop.setMaximumSize(new Dimension(410, 22));
        passTop.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel passLbl = new JLabel("Password");
        passLbl.setFont(SmartQueueUI.FONT_BODY_BOLD);
        JButton forgot = new JButton("Forgot password?");
        forgot.setFont(SmartQueueUI.FONT_SMALL);
        forgot.setForeground(SmartQueueUI.TERRACOTTA);
        forgot.setBorderPainted(false);
        forgot.setContentAreaFilled(false);
        forgot.addActionListener(e -> showAlert("Enter any school email & password (4+ chars) to continue."));
        passTop.add(passLbl, BorderLayout.WEST);
        passTop.add(forgot, BorderLayout.EAST);

        JPanel passRow = new JPanel(new BorderLayout(6, 0));
        passRow.setOpaque(false);
        passRow.setMaximumSize(new Dimension(410, 40));
        passRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        passField = new JPasswordField("pass1234");
        passField.setFont(SmartQueueUI.FONT_BODY);
        passField.setBackground(SmartQueueUI.BG_CREAM);
        passField.setBorder(SmartQueueUI.paddedBox(8, 12, 8, 12));
        char echo = passField.getEchoChar();

        JButton showBtn = SmartQueueUI.outlineButton("Show");
        showBtn.addActionListener(e -> {
            boolean hidden = passField.getEchoChar() != 0;
            passField.setEchoChar(hidden ? (char) 0 : echo);
            showBtn.setText(hidden ? "Hide" : "Show");
        });
        passRow.add(passField, BorderLayout.CENTER);
        passRow.add(showBtn, BorderLayout.EAST);

        submitBtn = SmartQueueUI.darkButton("Log in");
        submitBtn.setMaximumSize(new Dimension(410, 42));
        submitBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitBtn.addActionListener(e -> {
            String em = emailField.getText().trim();
            String pw = new String(passField.getPassword());
            if (!em.contains("@") || pw.length() < 4) {
                showAlert("Please enter a valid school email and 4+ character password.");
                return;
            }
            alertLbl.setVisible(false);
            onSuccess.accept(em);
        });

        switchBtn = new JButton("New to SmartQueue? Create an account");
        switchBtn.setFont(SmartQueueUI.FONT_SMALL_BOLD);
        switchBtn.setForeground(SmartQueueUI.INK_DARK);
        switchBtn.setBorderPainted(false);
        switchBtn.setContentAreaFilled(false);
        switchBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        switchBtn.addActionListener(e -> {
            registerMode = !registerMode;
            alertLbl.setVisible(false);
            nameGroup.setVisible(registerMode);
            headingLbl.setText(registerMode
                    ? "<html>Create your<br>SmartQueue account.</html>"
                    : "<html>Welcome back!<br>Ready to skip the queue?</html>");
            submitBtn.setText(registerMode ? "Create account" : "Log in");
            switchBtn.setText(registerMode ? "Already have an account? Log in" : "New to SmartQueue? Create an account");
        });

        form.add(eye);
        form.add(Box.createVerticalStrut(8));
        form.add(headingLbl);
        form.add(Box.createVerticalStrut(8));
        form.add(subLbl);
        form.add(Box.createVerticalStrut(12));
        form.add(alertLbl);
        form.add(Box.createVerticalStrut(8));
        form.add(nameGroup);
        form.add(emailLbl);
        form.add(Box.createVerticalStrut(4));
        form.add(emailField);
        form.add(Box.createVerticalStrut(4));
        form.add(emailHelp);
        form.add(Box.createVerticalStrut(12));
        form.add(passTop);
        form.add(Box.createVerticalStrut(4));
        form.add(passRow);
        form.add(Box.createVerticalStrut(18));
        form.add(submitBtn);
        form.add(Box.createVerticalStrut(10));
        form.add(switchBtn);

        center.add(form);
        col.add(center, BorderLayout.CENTER);

        JLabel foot = new JLabel("SMARTQUEUE / MADE FOR YOUR CAMPUS");
        foot.setFont(SmartQueueUI.FONT_EYEBROW);
        foot.setForeground(SmartQueueUI.INK_TAUPE);
        col.add(foot, BorderLayout.SOUTH);
        return col;
    }

    private JPanel buildBulletinColumn() {
        JPanel col = new JPanel(new BorderLayout(0, 16));
        col.setBackground(SmartQueueUI.BG_SAND);
        col.setBorder(BorderFactory.createEmptyBorder(28, 44, 28, 44));

        JPanel hdr = new JPanel();
        hdr.setLayout(new BoxLayout(hdr, BoxLayout.Y_AXIS));
        hdr.setOpaque(false);
        JLabel eye = new JLabel("THE CAMPUS BULLETIN");
        eye.setFont(SmartQueueUI.FONT_EYEBROW);
        eye.setForeground(SmartQueueUI.TERRACOTTA);
        JLabel h1 = new JLabel("Fresh from the canteen.");
        h1.setFont(SmartQueueUI.FONT_H1);
        h1.setForeground(SmartQueueUI.INK_DARK);
        JLabel sub = new JLabel("New stalls, fresh flavours and updates worth knowing.");
        sub.setFont(SmartQueueUI.FONT_BODY);
        sub.setForeground(SmartQueueUI.INK_TAUPE);
        hdr.add(eye); hdr.add(Box.createVerticalStrut(4)); hdr.add(h1); hdr.add(Box.createVerticalStrut(4)); hdr.add(sub);
        col.add(hdr, BorderLayout.NORTH);

        JPanel stack = new JPanel();
        stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));
        stack.setOpaque(false);

        JPanel featured = new JPanel();
        featured.setLayout(new BoxLayout(featured, BoxLayout.Y_AXIS));
        featured.setBackground(SmartQueueUI.BG_WHITE);
        featured.setBorder(SmartQueueUI.paddedBox(22, 24, 22, 24));
        featured.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pills = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pills.setOpaque(false);
        pills.setAlignmentX(Component.LEFT_ALIGNMENT);
        pills.add(SmartQueueUI.pill("NEW ON CAMPUS", SmartQueueUI.TERRACOTTA, Color.WHITE));
        pills.add(SmartQueueUI.pill("OPENING SOON", SmartQueueUI.SAGE_BG, SmartQueueUI.SAGE_TEXT));
        pills.add(SmartQueueUI.pill("MAIN CANTEEN", SmartQueueUI.BG_SAND, SmartQueueUI.INK_TAUPE));

        JLabel featTitle = new JLabel("<html>Meet your new<br>lunch-break favourite.</html>");
        featTitle.setFont(SmartQueueUI.FONT_H1);
        featTitle.setForeground(SmartQueueUI.INK_DARK);
        featTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel featBody = new JLabel("<html>Leaf &amp; Ladle is joining the line-up with fresh salad bowls and hearty soups.</html>");
        featBody.setFont(SmartQueueUI.FONT_BODY);
        featBody.setForeground(SmartQueueUI.INK_TAUPE);
        featBody.setAlignmentX(Component.LEFT_ALIGNMENT);

        featured.add(pills);
        featured.add(Box.createVerticalStrut(14));
        featured.add(featTitle);
        featured.add(Box.createVerticalStrut(8));
        featured.add(featBody);

        JPanel row = new JPanel(new GridLayout(1, 2, 16, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(900, 180));
        row.add(bulletinCard("01", "A little refresh for your canteen",
                "Seating-area renovations are on the way. Your favourite stalls will stay open as usual."));
        row.add(bulletinCard("02", "More time for your favourites",
                "Sip Happens is extending its afternoon hours. Drop by for a post-class pick-me-up."));

        stack.add(featured);
        stack.add(Box.createVerticalStrut(16));
        stack.add(row);
        col.add(stack, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, SmartQueueUI.BORDER_STONE));
        JLabel left = new JLabel("A little campus news. A better lunch break.");
        left.setFont(SmartQueueUI.FONT_SMALL);
        left.setForeground(SmartQueueUI.INK_TAUPE);
        left.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JLabel right = new JLabel("Need help? Contact canteen support");
        right.setFont(SmartQueueUI.FONT_SMALL_BOLD);
        right.setForeground(SmartQueueUI.TERRACOTTA);
        right.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        col.add(footer, BorderLayout.SOUTH);
        return col;
    }

    private JPanel bulletinCard(String num, String title, String body) {
        JPanel c = new JPanel();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setBackground(SmartQueueUI.BG_WHITE);
        c.setBorder(SmartQueueUI.paddedBox(16, 18, 16, 18));

        JLabel badge = SmartQueueUI.pill(num, SmartQueueUI.BG_SAND, SmartQueueUI.INK_DARK);
        badge.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel h3 = new JLabel("<html>" + title + "</html>");
        h3.setFont(SmartQueueUI.FONT_H3);
        h3.setForeground(SmartQueueUI.INK_DARK);
        h3.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel p = new JLabel("<html>" + body + "</html>");
        p.setFont(SmartQueueUI.FONT_BODY);
        p.setForeground(SmartQueueUI.INK_TAUPE);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        c.add(badge); c.add(Box.createVerticalStrut(10)); c.add(h3); c.add(Box.createVerticalStrut(6)); c.add(p);
        return c;
    }

    private JTextField styledField(String val) {
        JTextField f = new JTextField(val);
        f.setFont(SmartQueueUI.FONT_BODY);
        f.setBackground(SmartQueueUI.BG_CREAM);
        f.setMaximumSize(new Dimension(410, 40));
        f.setBorder(SmartQueueUI.paddedBox(8, 12, 8, 12));
        return f;
    }

    private void showAlert(String msg) {
        alertLbl.setText(msg);
        alertLbl.setVisible(true);
        revalidate();
    }
}