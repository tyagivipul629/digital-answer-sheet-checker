package classes.resultclass;

import javax.swing.*;
import java.awt.*;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;
import java.io.*;
import java.util.*;
import javax.swing.table.*;

public class Result extends JFrame{
	String resultFilePath;
	JTextField jtf;
	DefaultTableModel dtm;
	JSONArray resultArray;
	JTable table;
	GridBagConstraints gbc;
	int flagFound=1;
	public Result(String resultFilePath){
		this.resultFilePath=resultFilePath;
		dtm=new DefaultTableModel();
		table=new JTable(dtm);
		char ch=97;
		dtm.addColumn("Question-no");
		for(int i=0;i<=11;++i){
			dtm.addColumn(Character.toString(ch));
			++ch;
		}
		dtm.addColumn("Total");

	}
	public void showResult(){
		try{
			resultArray=(JSONArray)(new JSONParser()).parse(new FileReader(this.resultFilePath));
		}
		catch(Exception e){}
		this.setLayout(new GridBagLayout());
		JPanel searchPanel=new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		gbc=new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridwidth=2;
		JLabel lbl=new JLabel("Enter student's roll no:");
		jtf=new JTextField("",3);
		JButton submit=new JButton("See Result");
		try{
			submit.addActionListener(e->seeTable());
		}
		catch(Exception e){}
		searchPanel.add(lbl);
		searchPanel.add(jtf);
		searchPanel.add(submit);
		JScrollPane jsp=new JScrollPane(table,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(searchPanel,gbc);
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=2;
		this.add(jsp,gbc);
		this.pack();
		this.setVisible(true);
	}
	public void seeTable(){
		Object ob[];
		long rollno=Long.parseLong(jtf.getText());int i;
		for(i=0;i<resultArray.size();++i){
			JSONObject obj=(JSONObject)resultArray.get(i);
			//System.out.println(obj.get("roll-no")+" "+rollno);
			dtm.setRowCount(0);
			double sum=0.0f;
			if(rollno==(Long)obj.get("roll-no")){
				dtm.setRowCount(0);
				JSONArray marksArray=(JSONArray)obj.get("Marks");
				for(int j=0;j<marksArray.size();++j){
					ob=new Object[14];
					Arrays.fill(ob,"");
					ob[0]=((JSONObject)marksArray.get(j)).get("Question-no");
					ob[13]=((JSONObject)marksArray.get(j)).get("total-marks");
					sum+=(double)ob[13];
					JSONArray parts=(JSONArray)((JSONObject)marksArray.get(j)).get("Parts");
					int partno=1;
					for(int k=0;k<parts.size();++k){
						ob[partno]=((JSONObject)parts.get(k)).get("marks");
						++partno;
					}
					dtm.addRow(ob);
					ob=null;
				}
				ob=new Object[14];
				Arrays.fill(ob,"");
				ob[0]="total";
				ob[13]=sum;
				dtm.addRow(ob);
				refresh();
				break;
			}
			/*if(i==resultArray.size()){
				dtm.setRowCount(0);
				System.out.println("Roll no doesn't exists");
				JOptionPane.showMessageDialog(null,"Roll no not found");
				refresh();
			}*/
			
		}
		if(i==resultArray.size()){
				dtm.setRowCount(0);
				//System.out.println("Roll no doesn't exists");
				JOptionPane.showMessageDialog(null,"Roll no not found");
				refresh();
			}

	}
	public void refresh(){
		this.pack();
	}
	
}