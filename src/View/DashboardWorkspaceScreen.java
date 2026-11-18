package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

class DashboardWorkspaceScreen extends JPanel {
    private final SmartQueueUI app;
    private final CardLayout cards = new CardLayout();
    private final JPanel deck = new JPanel(cards);

    private String selectedStall = "All stalls";
    private String selectedCategory = "All";
    private int usualOffset = 0;

    private JTextField searchField;
    private JPanel stallsRow, usualRow, categoriesRow, menuGrid;
    private JPanel ordersCardsPanel, mapCardsPanel;

    public DashboardWorkspaceScreen(SmartQueueUI app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(SmartQueueUI.BG_CREAM);

        deck.add(wrapScroll(buildMenuDashboard()), "MENU");
        deck.add(wrapScroll(buildOrdersScreen()), "ORDERS");
        deck.add(wrapScroll(buildMapScreen()), "MAP");
        add(deck, BorderLayout.CENTER);
    }

    public void showCard(String name) { cards.show(deck, name); }

    public void refreshAll() {
        rebuildStallPills();
        rebuildCategoryTabs();
        refreshMenu();
        refreshOrders();
    }

    private JScrollPane wrapScroll(JPanel content) {
        JScrollPane sp = new JScrollPane(content);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(18);
        return sp;
    }

    // --- 1. MENU DASHBOARD (Matches Wireframe Page 1 & Page 4) ---
    private JPanel buildMenuDashboard() {
        JPanel page = new JPanel();
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setBackground(SmartQueueUI.BG_CREAM);
        page.setBorder(BorderFactory.createEmptyBorder(24, 34, 38, 34));

        // Hero Banner
        JPanel hero = new JPanel(new BorderLayout(24, 0));
        hero.setBackground(SmartQueueUI.BG_WHITE);
        hero.setBorder(SmartQueueUI.paddedBox(24, 26, 24, 26));
        hero.setMaximumSize(new Dimension(1400, 245));
        hero.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        JLabel eye = new JLabel("UNIVERSITY CANTEEN OPEN UNTIL 6 PM");
        eye.setFont(SmartQueueUI.FONT_EYEBROW);
        eye.setForeground(SmartQueueUI.TERRACOTTA);
        eye.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel h1 = new JLabel("Lunch, without the line.");
        h1.setFont(SmartQueueUI.FONT_HERO);
        h1.setForeground(SmartQueueUI.INK_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("<html>Pre-order from the campus kitchens you know. One checkout, precise pickup times, no waiting around.</html>");
        sub.setFont(SmartQueueUI.FONT_BODY);
        sub.setForeground(SmartQueueUI.INK_TAUPE);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel searchBox = new JPanel(new BorderLayout(8, 0));
        searchBox.setBackground(SmartQueueUI.BG_CREAM);
        searchBox.setBorder(SmartQueueUI.paddedBox(6, 12, 6, 12));
        searchBox.setMaximumSize(new Dimension(430, 40));
        searchBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel sLbl = new JLabel("Search today's menu:");
        sLbl.setFont(SmartQueueUI.FONT_SMALL_BOLD);
        sLbl.setForeground(SmartQueueUI.INK_TAUPE);
        searchField = new JTextField();
        searchField.setBackground(SmartQueueUI.BG_CREAM);
        searchField.setBorder(null);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { refreshMenu(); }
            @Override public void removeUpdate(DocumentEvent e) { refreshMenu(); }
            @Override public void changedUpdate(DocumentEvent e) { refreshMenu(); }
        });
        searchBox.add(sLbl, BorderLayout.WEST);
        searchBox.add(searchField, BorderLayout.CENTER);

        left.add(eye); left.add(Box.createVerticalStrut(6));
        left.add(h1); left.add(Box.createVerticalStrut(8));
        left.add(sub); left.add(Box.createVerticalStrut(14));
        left.add(searchBox);

        // Right Hero Wireframe Box (No Image — Dark Espresso + Sage LIVE)
        JPanel heroRight = new JPanel(new BorderLayout());
        heroRight.setBackground(SmartQueueUI.BG_ESPRESSO);
        heroRight.setBorder(SmartQueueUI.paddedBox(16, 18, 16, 18));
        heroRight.setPreferredSize(new Dimension(350, 190));

        JPanel phCenter = new JPanel();
        phCenter.setLayout(new BoxLayout(phCenter, BoxLayout.Y_AXIS));
        phCenter.setOpaque(false);
        JLabel ph1 = new JLabel("IMAGE PLACEHOLDER");
        ph1.setFont(SmartQueueUI.FONT_EYEBROW);
        ph1.setForeground(new Color(175, 168, 158));
        ph1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel ph2 = new JLabel("Featured canteen food photo");
        ph2.setFont(SmartQueueUI.FONT_SMALL);
        ph2.setForeground(new Color(140, 134, 125));
        ph2.setAlignmentX(Component.CENTER_ALIGNMENT);
        phCenter.add(Box.createVerticalGlue()); phCenter.add(ph1); phCenter.add(ph2); phCenter.add(Box.createVerticalGlue());

        JPanel heroBot = new JPanel(new BorderLayout());
        heroBot.setOpaque(false);
        JLabel avg = new JLabel("<html><span style='font-size:9px;color:#C8C2B8;'>CURRENT AVERAGE</span><br><b style='font-size:18px;color:#FFFFFF;'>8–12 min</b></html>");
        JLabel live = SmartQueueUI.pill("•  LIVE", new Color(36, 58, 48), new Color(110, 231, 183));
        heroBot.add(avg, BorderLayout.WEST);
        heroBot.add(live, BorderLayout.EAST);

        heroRight.add(phCenter, BorderLayout.CENTER);
        heroRight.add(heroBot, BorderLayout.SOUTH);

        hero.add(left, BorderLayout.CENTER);
        hero.add(heroRight, BorderLayout.EAST);

        // Kitchens Strip
        JPanel kitchenHdr = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        kitchenHdr.setOpaque(false);
        kitchenHdr.setAlignmentX(Component.LEFT_ALIGNMENT);
        kitchenHdr.add(SmartQueueUI.pill("•  LIVE", SmartQueueUI.SAGE_BG, SmartQueueUI.SAGE_TEXT));
        JLabel kTitle = new JLabel("KITCHENS");
        kTitle.setFont(SmartQueueUI.FONT_H3);
        JLabel kSub = new JLabel("7 kitchens available");
        kSub.setFont(SmartQueueUI.FONT_SMALL);
        kSub.setForeground(SmartQueueUI.INK_TAUPE);
        kitchenHdr.add(kTitle); kitchenHdr.add(kSub);

        stallsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        stallsRow.setOpaque(false);
        stallsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        rebuildStallPills();

        // Your Usual Picks
        JPanel usualHdr = new JPanel(new BorderLayout());
        usualHdr.setOpaque(false);
        usualHdr.setAlignmentX(Component.LEFT_ALIGNMENT);
        usualHdr.setMaximumSize(new Dimension(1400, 30));
        JLabel uTitle = new JLabel("YOUR USUAL PICKS  •  Recently ordered");
        uTitle.setFont(SmartQueueUI.FONT_H3);
        JPanel arrows = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        arrows.setOpaque(false);
        JButton prev = SmartQueueUI.outlineButton("<");
        JButton next = SmartQueueUI.outlineButton(">");
        prev.addActionListener(e -> { usualOffset = (usualOffset + 3) % 4; rebuildUsualCards(); });
        next.addActionListener(e -> { usualOffset = (usualOffset + 1) % 4; rebuildUsualCards(); });
        arrows.add(prev); arrows.add(next);
        usualHdr.add(uTitle, BorderLayout.WEST);
        usualHdr.add(arrows, BorderLayout.EAST);

        usualRow = new JPanel(new GridLayout(1, 3, 14, 0));
        usualRow.setOpaque(false);
        usualRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        usualRow.setMaximumSize(new Dimension(1400, 84));
        rebuildUsualCards();

        // Curated For Today — The Menu
        JPanel menuHdr = new JPanel(new BorderLayout());
        menuHdr.setOpaque(false);
        menuHdr.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuHdr.setMaximumSize(new Dimension(1400, 52));
        JLabel mTitle = new JLabel("<html><span style='font-size:9px;color:#C84B31;'><b>CURATED FOR TODAY</b></span><br><b style='font-size:20px;'>The menu</b></html>");
        categoriesRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 8));
        categoriesRow.setOpaque(false);
        rebuildCategoryTabs();
        menuHdr.add(mTitle, BorderLayout.WEST);
        menuHdr.add(categoriesRow, BorderLayout.EAST);

        menuGrid = new JPanel(new GridLayout(0, 3, 16, 16));
        menuGrid.setOpaque(false);
        menuGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        refreshMenu();

        page.add(hero); page.add(Box.createVerticalStrut(22));
        page.add(kitchenHdr); page.add(stallsRow); page.add(Box.createVerticalStrut(20));
        page.add(usualHdr); page.add(Box.createVerticalStrut(8)); page.add(usualRow); page.add(Box.createVerticalStrut(24));
        page.add(menuHdr); page.add(Box.createVerticalStrut(10)); page.add(menuGrid);
        return page;
    }

    private void rebuildStallPills() {
        stallsRow.removeAll();
        stallsRow.add(stallButton("All stalls", "All 7 kitchens"));
        for (String[] s : SmartQueueUI.STALLS) {
            stallsRow.add(stallButton(s[0], s[1] + " min wait"));
        }
        stallsRow.revalidate();
        stallsRow.repaint();
    }

    private JButton stallButton(String name, String wait) {
        boolean active = name.equalsIgnoreCase(selectedStall);
        JButton b = new JButton("<html><b>" + name + "</b><br><span style='font-size:10px;color:#6E685F;'>" + wait + "</span></html>");
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setFocusPainted(false);
        b.setBackground(active ? SmartQueueUI.BG_SAND : SmartQueueUI.BG_WHITE);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(active ? SmartQueueUI.TERRACOTTA : SmartQueueUI.BORDER_STONE, active ? 2 : 1),
                BorderFactory.createEmptyBorder(7, 13, 7, 15)));
        b.addActionListener(e -> { selectedStall = name; rebuildStallPills(); refreshMenu(); });
        return b;
    }

    private void rebuildUsualCards() {
        usualRow.removeAll();
        for (int i = 0; i < 3; i++) {
            String[] pick = SmartQueueUI.USUAL_PICKS[(usualOffset + i) % 4];
            JPanel card = new JPanel(new BorderLayout(10, 0));
            card.setBackground(SmartQueueUI.BG_WHITE);
            card.setBorder(SmartQueueUI.paddedBox(10, 12, 10, 12));

            // Wireframe "IMAGE" placeholder square (No image)
            JPanel thumb = new JPanel(new GridBagLayout());
            thumb.setPreferredSize(new Dimension(52, 52));
            thumb.setBackground(SmartQueueUI.BG_SAND);
            thumb.setBorder(BorderFactory.createLineBorder(SmartQueueUI.BORDER_STONE, 1));
            JLabel imgTxt = new JLabel("IMAGE");
            imgTxt.setFont(SmartQueueUI.FONT_EYEBROW);
            imgTxt.setForeground(SmartQueueUI.INK_MUTED);
            thumb.add(imgTxt);

            JLabel info = new JLabel("<html><span style='font-size:9px;color:#C84B31;'><b>" + pick[1] + "</b></span><br>"
                    + "<b>" + pick[2] + "</b><br><span style='font-size:10px;color:#968F85;'>" + pick[3] + "</span></html>");

            JButton plus = SmartQueueUI.outlineButton("+");
            plus.setFont(SmartQueueUI.FONT_H2);
            plus.addActionListener(e -> {
                app.changeBagQty(pick[0], 1);
                refreshMenu();
                app.showToast("Added " + pick[2] + " to your Bag.");
            });

            card.add(thumb, BorderLayout.WEST);
            card.add(info, BorderLayout.CENTER);
            card.add(plus, BorderLayout.EAST);
            usualRow.add(card);
        }
        usualRow.revalidate();
        usualRow.repaint();
    }

    private void rebuildCategoryTabs() {
        categoriesRow.removeAll();
        for (String cat : new String[]{"All", "Burgers", "Rice Meals", "Noodles", "Snacks", "Healthy", "Drinks", "Desserts"}) {
            boolean active = cat.equalsIgnoreCase(selectedCategory);
            JButton b = new JButton(cat);
            b.setFont(active ? SmartQueueUI.FONT_SMALL_BOLD : SmartQueueUI.FONT_SMALL);
            b.setForeground(active ? Color.WHITE : SmartQueueUI.INK_DARK);
            b.setBackground(active ? SmartQueueUI.INK_DARK : SmartQueueUI.BG_WHITE);
            b.setFocusPainted(false);
            b.setBorder(SmartQueueUI.paddedBox(5, 10, 5, 10));
            b.addActionListener(e -> { selectedCategory = cat; rebuildCategoryTabs(); refreshMenu(); });
            categoriesRow.add(b);
        }
        categoriesRow.revalidate();
        categoriesRow.repaint();
    }

    public void refreshMenu() {
        if (menuGrid == null) return;
        menuGrid.removeAll();
        String q = searchField == null ? "" : searchField.getText().trim().toLowerCase();

        for (String[] item : SmartQueueUI.MENU_ITEMS) {
            boolean okStall = "All stalls".equalsIgnoreCase(selectedStall) || item[2].equalsIgnoreCase(selectedStall);
            boolean okCat = "All".equalsIgnoreCase(selectedCategory) || item[3].equalsIgnoreCase(selectedCategory);
            boolean okQuery = q.isEmpty() || item[1].toLowerCase().contains(q) || item[2].toLowerCase().contains(q);
            if (okStall && okCat && okQuery) {
                menuGrid.add(buildMenuCard(item));
            }
        }
        menuGrid.revalidate();
        menuGrid.repaint();
    }

    private JPanel buildMenuCard(String[] item) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(SmartQueueUI.BG_WHITE);
        card.setBorder(BorderFactory.createLineBorder(SmartQueueUI.BORDER_STONE, 1));

        // Top Wireframe Placeholder Box (No image — Number chip + IMAGE PLACEHOLDER + Terracotta Badge)
        JPanel topBox = new JPanel(new BorderLayout());
        topBox.setPreferredSize(new Dimension(280, 148));
        topBox.setBackground(SmartQueueUI.BG_SAND);
        topBox.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, SmartQueueUI.BORDER_STONE));

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topRow.setOpaque(false);
        JLabel numChip = new JLabel(item[0]);
        numChip.setFont(SmartQueueUI.FONT_SMALL_BOLD);
        numChip.setOpaque(true);
        numChip.setBackground(SmartQueueUI.BG_WHITE);
        numChip.setBorder(SmartQueueUI.paddedBox(3, 7, 3, 7));
        topRow.add(numChip);
        topBox.add(topRow, BorderLayout.NORTH);

        JPanel centerPh = new JPanel();
        centerPh.setLayout(new BoxLayout(centerPh, BoxLayout.Y_AXIS));
        centerPh.setOpaque(false);
        JLabel ph1 = new JLabel("IMAGE PLACEHOLDER");
        ph1.setFont(SmartQueueUI.FONT_EYEBROW);
        ph1.setForeground(SmartQueueUI.INK_TAUPE);
        ph1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel ph2 = new JLabel(item[1] + " photo");
        ph2.setFont(SmartQueueUI.FONT_SMALL);
        ph2.setForeground(SmartQueueUI.INK_MUTED);
        ph2.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPh.add(Box.createVerticalGlue()); centerPh.add(ph1); centerPh.add(ph2); centerPh.add(Box.createVerticalGlue());
        topBox.add(centerPh, BorderLayout.CENTER);

        JPanel botRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        botRow.setOpaque(false);
        if (!item[7].isEmpty()) {
            botRow.add(SmartQueueUI.pill(item[7], SmartQueueUI.TERRACOTTA, Color.WHITE));
        }
        topBox.add(botRow, BorderLayout.SOUTH);
        card.add(topBox, BorderLayout.NORTH);

        // Middle Info
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(SmartQueueUI.BG_WHITE);
        body.setBorder(BorderFactory.createEmptyBorder(12, 14, 10, 14));

        JPanel meta = new JPanel(new BorderLayout());
        meta.setOpaque(false);
        meta.setAlignmentX(Component.LEFT_ALIGNMENT);
        meta.setMaximumSize(new Dimension(600, 18));
        JLabel st = new JLabel(item[2].toUpperCase());
        st.setFont(SmartQueueUI.FONT_EYEBROW);
        st.setForeground(SmartQueueUI.INK_TAUPE);
        JLabel pr = new JLabel(item[6] + " MIN");
        pr.setFont(SmartQueueUI.FONT_EYEBROW);
        pr.setForeground(SmartQueueUI.INK_TAUPE);
        meta.add(st, BorderLayout.WEST);
        meta.add(pr, BorderLayout.EAST);

        JLabel title = new JLabel(item[1]);
        title.setFont(SmartQueueUI.FONT_H2);
        title.setForeground(SmartQueueUI.INK_DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = new JLabel("<html><div style='width:240px;'>" + item[4] + "</div></html>");
        desc.setFont(SmartQueueUI.FONT_BODY);
        desc.setForeground(SmartQueueUI.INK_TAUPE);
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton details = new JButton("View item details");
        details.setFont(SmartQueueUI.FONT_SMALL_BOLD);
        details.setForeground(SmartQueueUI.TERRACOTTA);
        details.setBorderPainted(false);
        details.setContentAreaFilled(false);
        details.setHorizontalAlignment(SwingConstants.LEFT);
        details.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        details.setAlignmentX(Component.LEFT_ALIGNMENT);
        details.addActionListener(e -> app.showToast(item[1] + " (" + item[2] + ") — ₱" + item[5] + " • Est. prep: " + item[6] + " mins."));

        body.add(meta); body.add(Box.createVerticalStrut(4));
        body.add(title); body.add(Box.createVerticalStrut(4));
        body.add(desc); body.add(Box.createVerticalStrut(4));
        body.add(details);
        card.add(body, BorderLayout.CENTER);

        // Footer Price + Stepper / ADD TO BAG +
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(SmartQueueUI.BG_WHITE);
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, SmartQueueUI.BORDER_STONE),
                BorderFactory.createEmptyBorder(9, 14, 10, 14)));
        JLabel price = new JLabel("₱" + item[5]);
        price.setFont(SmartQueueUI.FONT_H2);
        price.setForeground(SmartQueueUI.INK_DARK);
        footer.add(price, BorderLayout.WEST);

        int qty = app.bagQuantities.getOrDefault(item[0], 0);
        if (qty > 0) {
            JPanel step = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
            step.setOpaque(false);
            JButton minus = SmartQueueUI.outlineButton("−");
            JLabel qLbl = new JLabel(String.valueOf(qty));
            qLbl.setFont(SmartQueueUI.FONT_BODY_BOLD);
            qLbl.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            JButton plus = SmartQueueUI.outlineButton("+");
            plus.setBackground(SmartQueueUI.BG_SAND);
            minus.addActionListener(e -> { app.changeBagQty(item[0], -1); refreshMenu(); });
            plus.addActionListener(e -> { app.changeBagQty(item[0], 1); refreshMenu(); });
            step.add(minus); step.add(qLbl); step.add(plus);
            footer.add(step, BorderLayout.EAST);
        } else {
            JButton add = SmartQueueUI.outlineButton("ADD TO BAG  +");
            add.addActionListener(e -> { app.changeBagQty(item[0], 1); refreshMenu(); app.showToast("Added " + item[1] + " to Bag."); });
            footer.add(add, BorderLayout.EAST);
        }

        card.add(footer, BorderLayout.SOUTH);
        return card;
    }

    // --- 2. ORDERS TRACKER SCREEN ---
    private JPanel buildOrdersScreen() {
        JPanel page = new JPanel();
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setBackground(SmartQueueUI.BG_CREAM);
        page.setBorder(BorderFactory.createEmptyBorder(26, 34, 38, 34));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.setMaximumSize(new Dimension(1400, 48));
        JLabel h1 = new JLabel("Orders & Live Pickup Status");
        h1.setFont(SmartQueueUI.FONT_H1);
        JButton nextStep = SmartQueueUI.darkButton("Advance Next Order Status →");
        nextStep.addActionListener(e -> {
            for (String[] t : app.orderTickets) {
                if ("QUEUED".equals(t[7])) { t[7] = "PREPARING"; break; }
                if ("PREPARING".equals(t[7])) { t[7] = "READY FOR PICKUP"; break; }
                if ("READY FOR PICKUP".equals(t[7])) { t[7] = "PICKED UP"; break; }
            }
            refreshOrders();
        });
        top.add(h1, BorderLayout.WEST);
        top.add(nextStep, BorderLayout.EAST);

        ordersCardsPanel = new JPanel(new GridLayout(0, 3, 16, 16));
        ordersCardsPanel.setOpaque(false);
        ordersCardsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        refreshOrders();

        page.add(top);
        page.add(Box.createVerticalStrut(18));
        page.add(ordersCardsPanel);
        return page;
    }

    public void refreshOrders() {
        if (ordersCardsPanel == null) return;
        ordersCardsPanel.removeAll();
        for (int i = app.orderTickets.size() - 1; i >= 0; i--) {
            String[] t = app.orderTickets.get(i);
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(SmartQueueUI.BG_WHITE);
            card.setBorder(SmartQueueUI.paddedBox(16, 18, 16, 18));

            JPanel top = new JPanel(new BorderLayout());
            top.setOpaque(false);
            top.setAlignmentX(Component.LEFT_ALIGNMENT);
            top.setMaximumSize(new Dimension(500, 24));
            JLabel id = new JLabel(t[0]);
            id.setFont(SmartQueueUI.FONT_BODY_BOLD);
            boolean ready = "READY FOR PICKUP".equals(t[7]) || "PICKED UP".equals(t[7]);
            JLabel badge = SmartQueueUI.pill(t[7],
                    ready ? SmartQueueUI.SAGE_BG : SmartQueueUI.AMBER_BG,
                    ready ? SmartQueueUI.SAGE_TEXT : SmartQueueUI.AMBER_TEXT);
            top.add(id, BorderLayout.WEST);
            top.add(badge, BorderLayout.EAST);

            JLabel stall = new JLabel(t[2].toUpperCase() + " • " + t[5]);
            stall.setFont(SmartQueueUI.FONT_EYEBROW);
            stall.setForeground(SmartQueueUI.TERRACOTTA);
            stall.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel items = new JLabel("<html>" + t[3] + "</html>");
            items.setFont(SmartQueueUI.FONT_BODY);
            items.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel pay = new JLabel(t[4] + " • " + t[6]);
            pay.setFont(SmartQueueUI.FONT_SMALL);
            pay.setForeground(SmartQueueUI.INK_TAUPE);
            pay.setAlignmentX(Component.LEFT_ALIGNMENT);

            card.add(top); card.add(Box.createVerticalStrut(8));
            card.add(stall); card.add(Box.createVerticalStrut(6));
            card.add(items); card.add(Box.createVerticalStrut(8));
            card.add(pay);
            ordersCardsPanel.add(card);
        }
        ordersCardsPanel.revalidate();
        ordersCardsPanel.repaint();
    }

    // --- 3. CANTEEN MAP SCREEN ---
    private JPanel buildMapScreen() {
        JPanel page = new JPanel();
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setBackground(SmartQueueUI.BG_CREAM);
        page.setBorder(BorderFactory.createEmptyBorder(26, 34, 38, 34));

        JLabel h1 = new JLabel("Canteen Map & Pickup Lanes");
        h1.setFont(SmartQueueUI.FONT_H1);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);

        mapCardsPanel = new JPanel(new GridLayout(0, 3, 16, 16));
        mapCardsPanel.setOpaque(false);
        mapCardsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (String[] s : SmartQueueUI.STALLS) {
            JPanel card = new JPanel(new BorderLayout(0, 8));
            card.setBackground(SmartQueueUI.BG_WHITE);
            card.setBorder(SmartQueueUI.paddedBox(16, 18, 16, 18));

            JPanel top = new JPanel(new BorderLayout());
            top.setOpaque(false);
            JLabel loc = new JLabel(s[3].toUpperCase());
            loc.setFont(SmartQueueUI.FONT_EYEBROW);
            loc.setForeground(SmartQueueUI.INK_TAUPE);
            top.add(loc, BorderLayout.WEST);
            top.add(SmartQueueUI.pill(s[1] + " MIN WAIT", SmartQueueUI.SAGE_BG, SmartQueueUI.SAGE_TEXT), BorderLayout.EAST);

            JLabel mid = new JLabel("<html><b style='font-size:14px;'>" + s[0] + "</b><br><span style='color:#6E685F;'>" + s[2] + "</span></html>");
            JButton browse = SmartQueueUI.outlineButton("View Stall Menu →");
            browse.addActionListener(e -> {
                selectedStall = s[0];
                selectedCategory = "All";
                rebuildStallPills();
                rebuildCategoryTabs();
                refreshMenu();
                app.switchTab("MENU");
            });

            card.add(top, BorderLayout.NORTH);
            card.add(mid, BorderLayout.CENTER);
            card.add(browse, BorderLayout.SOUTH);
            mapCardsPanel.add(card);
        }

        page.add(h1);
        page.add(Box.createVerticalStrut(18));
        page.add(mapCardsPanel);
        return page;
    }
}