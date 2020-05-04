package classes.questiondetailclass;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.util.*;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 
import javax.swing.filechooser.*;
import javax.swing.JFileChooser;
import java.io.*;
public class questionDetails extends JFrame{
	static int questionNo=1;
	List<Integer> noOfParts;
	JLabel questionNoLabel;
	JTextField jtf;
	File questionFile;
	public void fillQuestionDetail(){
		this.setLayout(new GridBagLayout());
		noOfParts=new ArrayList<Integer>();
		JPanel detailspanel=new JPanel();
		detailspanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		detailspanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 0));
		questionNoLabel=new JLabel("Enter no of parts in question "+questionNo+" (even if it is 0)");
		jtf=new JTextField("",3);
		detailspanel.add(questionNoLabel);
		detailspanel.add(jtf);
		JPanel buttonsPanel=new JPanel();
		BoxLayout bl=new BoxLayout(buttonsPanel,BoxLayout.Y_AXIS);
		JButton addMoreDetail=new JButton("Add More Detail");
		addMoreDetail.addActionListener(e->addDetail());
		JButton submitDetail=new JButton("Submit");
		submitDetail.addActionListener(e->submitDetail());
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 0));
		buttonsPanel.add(addMoreDetail);
		buttonsPanel.add(Box.createRigidArea(new Dimension(0,10)));
		buttonsPanel.add(submitDetail);
		this.add(detailspanel);
		this.add(buttonsPanel);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	void addDetail(){
		++questionNo;
		questionNoLabel.setText("Enter no of parts in question "+questionNo+" (even if it is 0)");
		noOfParts.add(Integer.parseInt(jtf.getText()));
		jtf.setText("");
	}
	void submitDetail(){
		noOfParts.add(Integer.parseInt(jtf.getText()));
		JSONArray jarr=null;
		try{jarr=(JSONArray)(new JSONParser()).parse("[]");}
		catch(Exception e){}

		for(int i=0;i<noOfParts.size();++i){
			JSONObject job=new JSONObject();
			job.put("Question-no",i+1);
			job.put("no-of-parts",(int)noOfParts.get(i));
			jarr.add(job);
		}

		JFileChooser fileChooser=new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files","json");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Specify only a .json file to save");
        int userSelection=fileChooser.showSaveDialog(new JFrame());
        if(userSelection==JFileChooser.APPROVE_OPTION){
            try{ questionFile=fileChooser.getSelectedFile();
                FileWriter fw=new FileWriter(questionFile);
                fw.write(jarr.toJSONString());
                fw.flush();
                fw.close();
                }
            catch(Exception e){}
    }
    this.dispose();
	}
	public static void main(String[] args) {
		new questionDetails().fillQuestionDetail();
	}
}