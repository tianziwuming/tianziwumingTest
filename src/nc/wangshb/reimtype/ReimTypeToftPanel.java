package nc.wangshb.reimtype;

import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class ReimTypeToftPanel extends JPanel {
	
	private JPanel contentPanel;
	private JTable table;
	private TableModel tablemodel;
	private static TableColumn[] tablecolumns;
	public ReimTypeToftPanel(){
		super();
		initialize();
		registerButtons();
	}
	
	private void initialize(){
		this.setLayout(new BorderLayout());
		this.add(getTable(),BorderLayout.CENTER);
	}
	
	private void registerButtons(){
		
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return "报销类型";
	}

//	@Override
//	public void onButtonClicked(ButtonObject arg0) {
//		// TODO Auto-generated method stub
//
//	}

//	public UIPanel getContentPanel() {
//		if(contentPanel==null){
//			contentPanel=new UIPanel();
//			
//			contentPanel.add(getTable());
//		}
//		return contentPanel;
//	}

	private JTable getTable(){
		if(table==null){
			table=new JTable();
			table.setSize(200, 300);
			table.setLocation(10,50);
			//table.setModel(getTableModel());
			TableColumn[] columns=this.getTableColumn();
			for(int i=0;i<columns.length;i++){
				table.addColumn(columns[i]);
			}
		}
		return table;
	}
	
	private TableModel getTableModel(){
		if(tablemodel==null){
			tablemodel=new DefaultTableModel();
			//未完成
		}
		return tablemodel;
	}
	
	public static TableColumn[] getTableColumn(){
		if(tablecolumns==null){
			tablecolumns=new TableColumn[4];
			
			tablecolumns[0]=new TableColumn();
			tablecolumns[0].setHeaderValue("编码");
			tablecolumns[0].setCellRenderer(new DefaultTableCellRenderer());
			tablecolumns[0].setCellEditor(new DefaultCellEditor(new JTextField()));
			tablecolumns[0].setResizable(true);
			
			tablecolumns[1]=new TableColumn();
			tablecolumns[1].setHeaderValue("名称");
			tablecolumns[1].setCellRenderer(new DefaultTableCellRenderer());
			tablecolumns[1].setCellEditor(new DefaultCellEditor(new JTextField()));
			tablecolumns[1].setResizable(true);
			
			tablecolumns[2]=new TableColumn();
			tablecolumns[2].setHeaderValue("备注");
			tablecolumns[2].setCellRenderer(new DefaultTableCellRenderer());
			tablecolumns[2].setCellEditor(new DefaultCellEditor(new JTextField()));
			tablecolumns[2].setResizable(true);
			
			tablecolumns[3]=new TableColumn();
			tablecolumns[3].setHeaderValue("封存");
			tablecolumns[3].setCellRenderer(new DefaultTableCellRenderer());
			tablecolumns[3].setCellEditor(new DefaultCellEditor(new JCheckBox()));
			tablecolumns[3].setResizable(true);
		}
		return tablecolumns;
	}
	
	
	public static void main(String[] args){
		JFrame frame=new JFrame();
		frame.setBounds(100, 200, 300, 500);
		frame.setLayout(new BorderLayout());
		//frame.setContentPane(new ReimTypeToftPanel());
		//frame.add(new ReimTypeToftPanel());
		
		String[] columns=new String[]{"编码","名称","备注","封存"};
		Object[][] rows=new Object[][]{{"1","2","3",false}};
		JTable table=new JTable(rows,columns);
		
		table.setDefaultRenderer(boolean.class, new CheckBoxCellRender());
		
		
		//table.setColumnModel(new DefaultTableColumnModel());
		//JTableHeader head=table.getTableHeader();
		//TableColumnModel colModel=new DefaultTableColumnModel();
//		Enumeration colums=colModel.getColumns();
//		for(TableColumn column:ReimTypeToftPanel.getTableColumn()){
//			colModel.addColumn(column);
//		}
		
		
		JScrollPane tableScroll=new JScrollPane(table);
		frame.add(tableScroll,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	

}
