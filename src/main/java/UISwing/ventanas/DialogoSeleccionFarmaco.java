package UISwing.ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import model.Farmaco;
import DB.FarmacoDAO;
import UISwing.recursos.RoundedPanel;

public class DialogoSeleccionFarmaco extends JDialog {
    private JComboBox<Farmaco> comboBoxFarmacos;
    private JTextField textFieldDosis;
    private JTextField textFieldFrecuencia;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private boolean seleccionado = false;
    private FarmacoDAO farmacoDAO;

    public DialogoSeleccionFarmaco(Frame owner, boolean modal, FarmacoDAO farmacoDAO) {
        super(owner, modal);
        this.farmacoDAO = farmacoDAO;
        setTitle("Seleccionar Fármaco");
        setUndecorated(true);
        setSize(new Dimension(320, 270));
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        
        RoundedPanel roundedBackground = new RoundedPanel(20);
        roundedBackground.setLayout(null);
        roundedBackground.setBackground(new Color(91, 123, 213));
        roundedBackground.setOpaque(false);
        setContentPane(roundedBackground);

        comboBoxFarmacos = new JComboBox<>();
        comboBoxFarmacos.setForeground(Color.decode("#0057FF"));
        comboBoxFarmacos.setEditable(true);
        comboBoxFarmacos.setBounds(34, 55, 248, 24);
        comboBoxFarmacos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBoxFarmacos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Farmaco) {
                    setText(((Farmaco) value).getNombre());
                }
                return this;
            }
        });

        JTextField textfield = (JTextField) comboBoxFarmacos.getEditor().getEditorComponent();
        textfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String search = textfield.getText();
                actualizarListaFarmacos(search);
            }
        });

        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.decode("#A6B1D8"));
        getContentPane().add(comboBoxFarmacos);

        textFieldDosis = new JTextField(10);
        textFieldDosis.setForeground(Color.decode("#0057FF"));
        textFieldDosis.setBounds(34, 110, 116, 22);
        textFieldDosis.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        getContentPane().add(textFieldDosis);

        textFieldFrecuencia = new JTextField(10);
        textFieldFrecuencia.setForeground(Color.decode("#0057FF"));
        textFieldFrecuencia.setBounds(34, 168, 248, 22);
        textFieldFrecuencia.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        getContentPane().add(textFieldFrecuencia);

        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(190, 214, 92, 23);
        btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAceptar.setBackground(Color.WHITE);
        btnAceptar.setForeground(Color.decode("#0057FF"));
        btnAceptar.addActionListener(e -> {
            seleccionado = true;
            setVisible(false);
        });
        getContentPane().add(btnAceptar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(34, 214, 95, 23);
        btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(Color.decode("#0057FF"));
        btnCancelar.addActionListener(e -> {
            seleccionado = false;
            setVisible(false);
        });
        getContentPane().add(btnCancelar);
        
        JLabel lblFarmaco = new JLabel("Farmaco:");
        lblFarmaco.setForeground(Color.decode("#1B2582")); 
        lblFarmaco.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFarmaco.setBounds(34, 30, 69, 14);
        getContentPane().add(lblFarmaco);
        
        JLabel lblDosis = new JLabel("Dosis:");
        lblDosis.setForeground(Color.decode("#1B2582"));
        lblDosis.setFont(new Font("Segoe UI", Font.BOLD, 12));
     
        lblDosis.setBounds(34, 90, 46, 14);
        getContentPane().add(lblDosis);        
        JLabel lblFrecuencia = new JLabel("Frecuencia:");
        lblFrecuencia.setForeground(Color.decode("#1B2582"));
        lblFrecuencia.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFrecuencia.setBounds(34, 143, 95, 14);
        getContentPane().add(lblFrecuencia);
        
        
        JPanel centerPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.SrcOver.derive(0.5f));
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose();
            super.paintComponent(g);
	        }
	    };
	    centerPanel.setBackground(new Color(255, 255, 255, 70)); 
	    centerPanel.setOpaque(false);
	    centerPanel.setBounds(21, 21, 275, 227);

	    getContentPane().add(centerPanel);
    }
    
    private void actualizarListaFarmacos(String texto) {
        List<Farmaco> farmacos = farmacoDAO.buscarFarmacos(texto);
        comboBoxFarmacos.removeAllItems();
        farmacos.forEach(comboBoxFarmacos::addItem);
        comboBoxFarmacos.setPopupVisible(true);
    }

  

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public Farmaco getFarmacoSeleccionado() {
        return (Farmaco) comboBoxFarmacos.getSelectedItem();
    }

    public String getDosis() {
        return textFieldDosis.getText();
    }

    public String getFrecuencia() {
        return textFieldFrecuencia.getText();
    }
    
    public static void main(String[] args) {
        FarmacoDAO farmacoDAO = new FarmacoDAO();
        DialogoSeleccionFarmaco dialogo = new DialogoSeleccionFarmaco(null, true, farmacoDAO);
        dialogo.setVisible(true);
    }
}
