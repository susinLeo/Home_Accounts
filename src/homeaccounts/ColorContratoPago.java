package homeaccounts;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorContratoPago extends DefaultTableCellRenderer{
	private static final long   serialVersionUID    = 1L;
	public static final float R = 0.9f;
	public static final float G = 0.5f; 
	public static final float B = 0.8f;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	 super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	   
	 	Color c = Color.GREEN;
	    Object text = table.getValueAt(row, 4);
	    Date d = new Date();
	    Date dateVencto = new Date();
	    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	    java.util.Date acdate = null;
	
	    setBackground(c);   
	    return this;
	    
	}



}
