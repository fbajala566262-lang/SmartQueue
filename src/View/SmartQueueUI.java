package View;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

class SmartQueueUI extends JFrame {

    // --- Color Palette & Typography (from colored mockup) ---
    public static final Color BG_CREAM       = new Color(248, 246, 241); // #F8F6F1 warm page background
    public static final Color BG_WHITE       = new Color(255, 255, 255); // #FFFFFF crisp card surface
    public static final Color BG_SAND        = new Color(239, 236, 229); // #EFECE5 warm sand / placeholder fill
    public static final Color BG_ESPRESSO    = new Color(36, 33, 30);    // #24211E dark hero box
    public static final Color BORDER_STONE   = new Color(224, 219, 210); // #E0DBD2 1px hairline border
    public static final Color INK_DARK       = new Color(30, 28, 26);    // #1E1C1A primary text & CTA
    public static final Color INK_TAUPE      = new Color(110, 104, 95);  // #6E685F secondary text
    public static final Color INK_MUTED      = new Color(150, 143, 133); // #968F85 muted caption
    public static final Color TERRACOTTA     = new Color(200, 75, 49);   // #C84B31 BESTSELLER / NEW / POPULAR
    public static final Color SAGE_TEXT      = new Color(36, 106, 73);   // #246A49 LIVE / READY status
    public static final Color SAGE_BG        = new Color(232, 245, 238); // #E8F5EE soft sage pill fill
    public static final Color AMBER_TEXT     = new Color(175, 98, 20);   // #AF6214 PREPARING status
    public static final Color AMBER_BG       = new Color(254, 243, 226); // #FEF3E2 soft amber pill fill

    public static final Font FONT_BRAND      = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_HERO       = new Font("SansSerif", Font.BOLD, 34);
    public static final Font FONT_H1         = new Font("SansSerif", Font.BOLD, 25);
    public static final Font FONT_H2         = new Font("SansSerif", Font.BOLD, 18);
    public static final Font FONT_H3         = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONT_BODY       = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BODY_BOLD  = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_SMALL      = new Font("SansSerif", Font.PLAIN, 11);
    public static final Font FONT_SMALL_BOLD = new Font("SansSerif", Font.BOLD, 11);
    public static final Font FONT_EYEBROW    = new Font("SansSerif", Font.BOLD, 10);

    // --- Simple Wireframe Data Tables (No model classes needed) ---
    // Columns: {name, waitMins, specialty, location}
    public static final String[][] STALLS = {
        {"Bun Intended", "10", "Burgers & Melts", "North Wing • Stall 01"},
        {"Lola Nena's Kitchen", "8", "Filipino Rice Meals", "North Wing • Stall 02"},
        {"Slurp Society", "6", "Hot Noodle Bowls", "East Wing • Stall 03"},
        {"The Crunch Club", "4", "Loaded Fries & Snacks", "East Wing • Stall 04"},
        {"Leaf & Ladle", "5", "Fresh Salads & Soups", "Central Atrium • Stall 05"},
        {"Sip Happens", "3", "Milk Tea & Iced Coffee", "South Entrance • Stall 06"},
        {"Sweet Spot", "3", "Campus Desserts", "South Entrance • Stall 07"}
    };

    // Columns: {num, title, stall, category, description, price, prepMins, badge}
    public static final String[][] MENU_ITEMS = {
        {"01", "Classic Chicken Burger", "Bun Intended", "Burgers",
         "Crispy chicken fillet, lettuce, pickles, and house sauce, with a side of fries.", "95.00", "10", "BESTSELLER"},
        {"02", "Double Smash Burger", "Bun Intended", "Burgers",
         "Two smashed-beef patties meet melted cheddar, pickles and sauce.", "135.00", "10", "NEW"},
        {"03", "Mushroom Swiss Melt", "Bun Intended", "Burgers",
         "Beef patty, sautéed mushrooms, melted Swiss and red cabbage slaw.", "125.00", "10", ""},
        {"04", "Spicy Mayo Chicken Burger", "Bun Intended", "Burgers",
         "Crispy spiced chicken fillet drizzled with roasted sriracha mayo and slaw.", "105.00", "10", ""},
        {"05", "Sizzling Pork Sisig", "Lola Nena's Kitchen", "Rice Meals",
         "Chopped crispy pork jowl, calamansi, chili, egg, and steamed garlic rice.", "115.00", "8", "POPULAR"},
        {"06", "Chicken Inasal Plate", "Lola Nena's Kitchen", "Rice Meals",
         "Charcoal-grilled annatto chicken quarter with atchara, sinamak, and rice.", "120.00", "8", ""},
        {"07", "Tonkotsu Chashu Ramen", "Slurp Society", "Noodles",
         "Rich 12-hour pork broth, braised chashu, marinated egg, nori, and noodles.", "145.00", "6", "POPULAR"},
        {"08", "Loaded Cheese Fries", "The Crunch Club", "Snacks",
         "Crisp skin-on fries topped with warm cheddar queso, bacon bits, and chives.", "85.00", "4", "BESTSELLER"},
        {"09", "Roasted Harvest Salad Bowl", "Leaf & Ladle", "Healthy",
         "Baby greens, grilled herb chicken, sweet potato, feta, and lemon vinaigrette.", "110.00", "5", "NEW"},
        {"10", "Brown Sugar Boba Milk Tea", "Sip Happens", "Drinks",
         "Assam black milk tea swirled with warm caramelized muscovado pearls.", "75.00", "3", "POPULAR"},
        {"11", "Mango Graham Parfait Cup", "Sweet Spot", "Desserts",
         "Layers of sweet ripe mangoes, crushed graham crackers, and whipped cream.", "65.00", "3", ""}
    };

    // Columns: {itemNum, stallUpper, title, recency}
    public static final String[][] USUAL_PICKS = {
        {"05", "LOLA NENA'S KITCHEN", "Sizzling Pork Sisig", "Bought Yesterday"},
        {"10", "SIP HAPPENS", "Brown Sugar Boba Milk Tea", "Bought Monday"},
        {"08", "THE CRUNCH CLUB", "Loaded Cheese Fries", "Bought Last Friday"},
        {"01", "BUN INTENDED", "Classic Chicken Burger", "Bought Last Week"}
    };

    // --- Shared UI Session State ---
    public String currentUserEmail = "you@school.edu";
    public final Map<String, Integer> bagQuantities = new LinkedHashMap<>();
    // Order row: {id, customer, stall, summary, total, slot, payment, status}
    public final List<String[]> orderTickets = new ArrayList<>();
    public int orderSequence = 1003;

    // --- Navigation Shell Widgets ---
    private final CardLayout rootCards = new CardLayout();
    private final JPanel rootDeck = new JPanel(rootCards);
    private DashboardWorkspaceScreen workspaceScreen;
    private JButton navMenuBtn, navOrdersBtn, navMapBtn, bagButton;
    private JLabel toastLabel;

    public SmartQueueUI() {
        super("SmartQueue Canteen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 840);
        setMinimumSize(new Dimension(1024, 680));
        setLocationRelativeTo(null);

        // Pre-populate Bag 1 and sample orders so wireframe states are live immediately
        bagQuantities.put("01", 1);
        orderTickets.add(new String[]{"TXN-1001", "you@school.edu", "Bun Intended",
                "1x Classic Chicken Burger", "₱95.00", "12:15 PM Pickup", "GCash (09171234567)", "READY FOR PICKUP"});
        orderTickets.add(new String[]{"TXN-1002", "you@school.edu", "Lola Nena's Kitchen",
                "1x Sizzling Pork Sisig", "₱115.00", "12:25 PM Pickup", "Maya (09189876543)", "PREPARING"});

        LoginBulletinScreen loginScreen = new LoginBulletinScreen(this, email -> {
            currentUserEmail = email;
            workspaceScreen.refreshAll();
            updateBagButton();
            switchTab("MENU");
            rootCards.show(rootDeck, "WORKSPACE");
        });

        workspaceScreen = new DashboardWorkspaceScreen(this);

        JPanel workspaceShell = new JPanel(new BorderLayout());
        workspaceShell.setBackground(BG_CREAM);
        workspaceShell.add(buildTopNavbar(), BorderLayout.NORTH);
        workspaceShell.add(workspaceScreen, BorderLayout.CENTER);

        rootDeck.add(loginScreen, "LOGIN");
        rootDeck.add(workspaceShell, "WORKSPACE");
        setContentPane(rootDeck);
        rootCards.show(rootDeck, "LOGIN");
    }

    private JPanel buildTopNavbar() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_STONE));

        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_WHITE);
        bar.setBorder(BorderFactory.createEmptyBorder(12, 32, 12, 32));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 22, 0));
        left.setOpaque(false);
        JLabel brand = new JLabel("SMARTQUEUE");
        brand.setFont(FONT_BRAND);
        brand.setForeground(INK_DARK);

        navMenuBtn = navTab("Menu", true);
        navOrdersBtn = navTab("Orders", false);
        navMapBtn = navTab("Canteen map", false);
        navMenuBtn.addActionListener(e -> switchTab("MENU"));
        navOrdersBtn.addActionListener(e -> { workspaceScreen.refreshOrders(); switchTab("ORDERS"); });
        navMapBtn.addActionListener(e -> switchTab("MAP"));

        left.add(brand);
        left.add(navMenuBtn);
        left.add(navOrdersBtn);
        left.add(navMapBtn);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        bagButton = darkButton("Bag 1");
        bagButton.addActionListener(e -> openBagDialog());

        JButton logoutBtn = outlineButton("Log out");
        logoutBtn.addActionListener(e -> {
            toastLabel.setVisible(false);
            rootCards.show(rootDeck, "LOGIN");
        });
        right.add(bagButton);
        right.add(logoutBtn);

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);

        toastLabel = new JLabel("", SwingConstants.CENTER);
        toastLabel.setFont(FONT_SMALL_BOLD);
        toastLabel.setOpaque(true);
        toastLabel.setBackground(SAGE_BG);
        toastLabel.setForeground(SAGE_TEXT);
        toastLabel.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
        toastLabel.setVisible(false);

        header.add(bar, BorderLayout.CENTER);
        header.add(toastLabel, BorderLayout.SOUTH);
        return header;
    }

    public void switchTab(String tab) {
        workspaceScreen.showCard(tab);
        styleTab(navMenuBtn, "MENU".equals(tab));
        styleTab(navOrdersBtn, "ORDERS".equals(tab));
        styleTab(navMapBtn, "MAP".equals(tab));
    }

    private JButton navTab(String text, boolean active) {
        JButton b = new JButton(text);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        styleTab(b, active);
        return b;
    }

    private void styleTab(JButton b, boolean active) {
        b.setFont(active ? FONT_BODY_BOLD : FONT_BODY);
        b.setForeground(active ? INK_DARK : INK_TAUPE);
        b.setBorder(active
                ? BorderFactory.createMatteBorder(0, 0, 2, 0, TERRACOTTA)
                : BorderFactory.createEmptyBorder(4, 4, 6, 4));
    }

    public void changeBagQty(String itemNum, int delta) {
        int next = bagQuantities.getOrDefault(itemNum, 0) + delta;
        if (next <= 0) bagQuantities.remove(itemNum);
        else bagQuantities.put(itemNum, next);
        updateBagButton();
    }

    public void updateBagButton() {
        int total = 0;
        for (int q : bagQuantities.values()) total += q;
        bagButton.setText("Bag " + total);
    }

    public void showToast(String msg) {
        toastLabel.setText(msg);
        toastLabel.setVisible(true);
        revalidate();
    }

    public String[] findMenuItem(String num) {
        for (String[] row : MENU_ITEMS) {
            if (row[0].equals(num)) return row;
        }
        return null;
    }

    private void openBagDialog() {
        JDialog dlg = new JDialog(this, "Your Bag & Checkout", Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setSize(520, 540);
        dlg.setLocationRelativeTo(this);

        JPanel root = new JPanel(new BorderLayout(0, 12));
        root.setBackground(BG_CREAM);
        root.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel title = new JLabel("Your Pre-Order Bag");
        title.setFont(FONT_H1);
        title.setForeground(INK_DARK);
        root.add(title, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(BG_WHITE);
        listPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel totalLbl = new JLabel();
        totalLbl.setFont(FONT_BODY_BOLD);

        Runnable rebuild = new Runnable() {
            @Override public void run() {
                listPanel.removeAll();
                double sum = 0;
                if (bagQuantities.isEmpty()) {
                    JLabel empty = new JLabel("Your bag is empty. Add items from the menu!");
                    empty.setFont(FONT_BODY);
                    empty.setForeground(INK_TAUPE);
                    empty.setBorder(BorderFactory.createEmptyBorder(20, 8, 20, 8));
                    listPanel.add(empty);
                } else {
                    for (Map.Entry<String, Integer> e : new ArrayList<>(bagQuantities.entrySet())) {
                        String[] item = findMenuItem(e.getKey());
                        if (item == null) continue;
                        double line = Double.parseDouble(item[5]) * e.getValue();
                        sum += line;

                        JPanel row = new JPanel(new BorderLayout(8, 0));
                        row.setBackground(BG_WHITE);
                        row.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_STONE),
                                BorderFactory.createEmptyBorder(8, 4, 8, 4)));
                        row.setMaximumSize(new Dimension(480, 54));

                        JLabel lbl = new JLabel("<html><b>" + item[1] + "</b><br><span style='color:#6E685F;font-size:10px;'>"
                                + item[2] + " • ₱" + item[5] + "</span></html>");
                        JPanel ctrls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 2));
                        ctrls.setOpaque(false);
                        JButton minus = outlineButton("−");
                        JLabel q = new JLabel(String.valueOf(e.getValue()));
                        q.setFont(FONT_BODY_BOLD);
                        JButton plus = outlineButton("+");
                        JLabel lineLbl = new JLabel(String.format("₱%.2f", line));
                        lineLbl.setFont(FONT_BODY_BOLD);

                        minus.addActionListener(ev -> { changeBagQty(item[0], -1); workspaceScreen.refreshMenu(); this.run(); });
                        plus.addActionListener(ev -> { changeBagQty(item[0], 1); workspaceScreen.refreshMenu(); this.run(); });

                        ctrls.add(minus); ctrls.add(q); ctrls.add(plus); ctrls.add(lineLbl);
                        row.add(lbl, BorderLayout.CENTER);
                        row.add(ctrls, BorderLayout.EAST);
                        listPanel.add(row);
                    }
                }
                totalLbl.setText(String.format("Total: ₱%.2f", sum));
                listPanel.revalidate();
                listPanel.repaint();
            }
        };
        rebuild.run();

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_STONE, 1));
        root.add(scroll, BorderLayout.CENTER);

        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        south.setBackground(BG_WHITE);
        south.setBorder(paddedBox(12, 14, 12, 14));

        JComboBox<String> slotBox = new JComboBox<>(new String[]{"ASAP (8–12 min)", "12:00 PM Pickup", "12:30 PM Pickup", "4:30 PM Pickup"});
        JComboBox<String> payBox = new JComboBox<>(new String[]{"GCash E-Wallet", "Maya E-Wallet", "Campus Card"});
        JPanel combos = new JPanel(new GridLayout(1, 2, 8, 0));
        combos.setOpaque(false);
        combos.add(slotBox);
        combos.add(payBox);

        JPanel actions = new JPanel(new BorderLayout());
        actions.setOpaque(false);
        JButton confirmBtn = terracottaButton("Confirm & Pay");
        confirmBtn.addActionListener(e -> {
            if (bagQuantities.isEmpty()) return;
            double total = 0;
            StringBuilder summary = new StringBuilder();
            String stall = "";
            for (Map.Entry<String, Integer> entry : bagQuantities.entrySet()) {
                String[] it = findMenuItem(entry.getKey());
                if (it == null) continue;
                if (summary.length() > 0) summary.append(", ");
                summary.append(entry.getValue()).append("x ").append(it[1]);
                total += Double.parseDouble(it[5]) * entry.getValue();
                stall = stall.isEmpty() ? it[2] : "Multi-Stall";
            }
            String id = "TXN-" + (orderSequence++);
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
            orderTickets.add(new String[]{id, currentUserEmail, stall, summary.toString(),
                    String.format("₱%.2f", total), (String) slotBox.getSelectedItem(),
                    payBox.getSelectedItem() + " (" + time + ")", "QUEUED"});
            bagQuantities.clear();
            updateBagButton();
            workspaceScreen.refreshMenu();
            workspaceScreen.refreshOrders();
            dlg.dispose();
            switchTab("ORDERS");
            showToast("Order " + id + " placed! Track your live queue status below.");
        });
        actions.add(totalLbl, BorderLayout.WEST);
        actions.add(confirmBtn, BorderLayout.EAST);

        south.add(combos);
        south.add(Box.createVerticalStrut(10));
        south.add(actions);
        root.add(south, BorderLayout.SOUTH);

        dlg.setContentPane(root);
        dlg.setVisible(true);
    }

    // --- Shared UI Component Factories ---
    public static Border paddedBox(int t, int l, int b, int r) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_STONE, 1),
                BorderFactory.createEmptyBorder(t, l, b, r));
    }

    public static JButton darkButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BODY_BOLD);
        b.setForeground(Color.WHITE);
        b.setBackground(INK_DARK);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));
        return b;
    }

    public static JButton terracottaButton(String text) {
        JButton b = darkButton(text);
        b.setBackground(TERRACOTTA);
        return b;
    }

    public static JButton outlineButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_SMALL_BOLD);
        b.setForeground(INK_DARK);
        b.setBackground(BG_WHITE);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(paddedBox(6, 12, 6, 12));
        return b;
    }

    public static JLabel pill(String text, Color bg, Color fg) {
        JLabel l = new JLabel(text.toUpperCase());
        l.setFont(FONT_EYEBROW);
        l.setOpaque(true);
        l.setBackground(bg);
        l.setForeground(fg);
        l.setBorder(BorderFactory.createEmptyBorder(4, 9, 4, 9));
        return l;
    }
}