package nc.wangshb.reimtype;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckBoxCellRender implements TableCellRenderer {

	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		if(!(arg1 instanceof Boolean)){
			return new JLabel(arg1.toString());
		}
		Boolean isSelected=(Boolean)arg1;
		JCheckBox box= new JCheckBox();
		box.setSelected(isSelected);
		return box;
		
	}

}
