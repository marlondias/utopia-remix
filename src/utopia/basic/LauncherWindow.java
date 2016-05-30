package utopia.basic;

public class LauncherWindow extends javax.swing.JFrame {
    private javax.swing.JCheckBox checkboxFullscreen;
    private javax.swing.JComboBox<String> comboLanguage;
    private javax.swing.JComboBox<String> comboResolution;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel labelGraphics;
    private javax.swing.JLabel labelLanguage;
    private javax.swing.JLabel topImage;
    

    public LauncherWindow() {
        initComponents();
    }

    private void initComponents() {
        topImage = new javax.swing.JLabel();
        labelLanguage = new javax.swing.JLabel();
        comboLanguage = new javax.swing.JComboBox<>();
        labelGraphics = new javax.swing.JLabel();
        comboResolution = new javax.swing.JComboBox<>();
        checkboxFullscreen = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        topImage.setIcon(new javax.swing.ImageIcon("res/bg-launcher.jpg")); // NOI18N
        topImage.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        topImage.setFocusable(false);
        topImage.setMaximumSize(new java.awt.Dimension(300, 100));
        topImage.setMinimumSize(new java.awt.Dimension(300, 100));
        topImage.setPreferredSize(new java.awt.Dimension(300, 100));

        labelLanguage.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        labelLanguage.setText("Language");

        comboLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelGraphics.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        labelGraphics.setText("Graphics");

        comboResolution.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboResolution.setToolTipText("Choose resolution");

        checkboxFullscreen.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        checkboxFullscreen.setText("Fullscreen");

        jButton1.setText("Controls...");

        jButton2.setText("Play");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topImage, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboLanguage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(comboResolution, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(checkboxFullscreen))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelLanguage)
                            .addComponent(labelGraphics))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(topImage, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelLanguage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(labelGraphics)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkboxFullscreen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void openWindow() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LauncherWindow().setVisible(true);
            }
        });
    }
}
