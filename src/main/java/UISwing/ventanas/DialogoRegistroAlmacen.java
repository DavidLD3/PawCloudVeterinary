package UISwing.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DialogoRegistroAlmacen extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfRnombre;
	private JTextField tfRcategoria;
	private JTextField tfRfechaUltCompra;
	private JTextField tfRN_Lote;
	private JTextField tfRcantidad;
	private JTextField tfrfecha_Caducidad;
	private JTextField tfRproveedor;
	private JTextField tfRCodigo_Barras;
	private JTextField tfRprecio_Bruto;
	private JTextField tfRobservaciones;
	private JTextField tfRdescripcion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogoRegistroAlmacen dialog = new DialogoRegistroAlmacen();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogoRegistroAlmacen() {
		setBounds(100, 100, 1200, 900);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(44, 35, 49, 14);
		contentPanel.add(lblNombre);
		
		tfRnombre = new JTextField();
		tfRnombre.setBounds(44, 60, 96, 20);
		contentPanel.add(tfRnombre);
		tfRnombre.setColumns(10);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(248, 35, 49, 14);
		contentPanel.add(lblCategoria);
		
		tfRcategoria = new JTextField();
		tfRcategoria.setBounds(248, 60, 96, 20);
		contentPanel.add(tfRcategoria);
		tfRcategoria.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(433, 35, 49, 14);
		contentPanel.add(lblDescripcion);
		
		JLabel lblFecha_Ultima_Compra = new JLabel("Fecha de Compra");
		lblFecha_Ultima_Compra.setBounds(44, 115, 49, 14);
		contentPanel.add(lblFecha_Ultima_Compra);
		
		tfRfechaUltCompra = new JTextField();
		tfRfechaUltCompra.setBounds(44, 140, 96, 20);
		contentPanel.add(tfRfechaUltCompra);
		tfRfechaUltCompra.setColumns(10);
		
		JLabel lblN_Lote = new JLabel("Numero Lote");
		lblN_Lote.setBounds(248, 115, 49, 14);
		contentPanel.add(lblN_Lote);
		
		tfRN_Lote = new JTextField();
		tfRN_Lote.setBounds(248, 140, 96, 20);
		contentPanel.add(tfRN_Lote);
		tfRN_Lote.setColumns(10);
		
		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setBounds(433, 115, 49, 14);
		contentPanel.add(lblCantidad);
		
		tfRcantidad = new JTextField();
		tfRcantidad.setBounds(433, 140, 96, 20);
		contentPanel.add(tfRcantidad);
		tfRcantidad.setColumns(10);
		
		
		JLabel lblFecha_Caducidad = new JLabel("Fecha Caducidad");
		lblFecha_Caducidad.setBounds(44, 196, 49, 14);
		contentPanel.add(lblFecha_Caducidad);
		
		tfrfecha_Caducidad = new JTextField();
		tfrfecha_Caducidad.setBounds(44, 221, 96, 20);
		contentPanel.add(tfrfecha_Caducidad);
		tfrfecha_Caducidad.setColumns(10);
		
		JLabel lblProveedor = new JLabel("Proveedor");
		lblProveedor.setBounds(248, 196, 49, 14);
		contentPanel.add(lblProveedor);
		
		tfRproveedor = new JTextField();
		tfRproveedor.setBounds(248, 221, 96, 20);
		contentPanel.add(tfRproveedor);
		tfRproveedor.setColumns(10);
		
		JLabel lblCodigo_Barras = new JLabel("Codigo de barras");
		lblCodigo_Barras.setBounds(433, 196, 49, 14);
		contentPanel.add(lblCodigo_Barras);
		
		tfRCodigo_Barras = new JTextField();
		tfRCodigo_Barras.setBounds(433, 221, 96, 20);
		contentPanel.add(tfRCodigo_Barras);
		tfRCodigo_Barras.setColumns(10);
		
		JLabel lblPrecio_Bruto = new JLabel("Precio bruto");
		lblPrecio_Bruto.setBounds(44, 293, 49, 14);
		contentPanel.add(lblPrecio_Bruto);
		
		tfRprecio_Bruto = new JTextField();
		tfRprecio_Bruto.setBounds(44, 327, 96, 20);
		contentPanel.add(tfRprecio_Bruto);
		tfRprecio_Bruto.setColumns(10);
		
		JLabel lblObservaciones = new JLabel("Observaciones");
		lblObservaciones.setBounds(248, 293, 49, 14);
		contentPanel.add(lblObservaciones);
		
		tfRobservaciones = new JTextField();
		tfRobservaciones.setBounds(248, 327, 96, 20);
		contentPanel.add(tfRobservaciones);
		tfRobservaciones.setColumns(10);
		
		tfRdescripcion = new JTextField();
		tfRdescripcion.setBounds(433, 60, 96, 20);
		contentPanel.add(tfRdescripcion);
		tfRdescripcion.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
