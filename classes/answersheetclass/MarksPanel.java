package classes.answersheetclass;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import javax.swing.filechooser.*;
import java.io.*;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 
import java.awt.*;
import java.util.*;


class MarksPanel{
	JTextField rollnoprompt;
    JSONArray reportCard;
    static int noofques=0;
    answerSheetWindow1 obj1;

    java.util.List<marksClass> mrkcls;
    MarksPanel(answerSheetWindow1 obj1){
    	mrkcls=new ArrayList<marksClass>();
    	rollnoprompt=new JTextField("",3);
    	this.obj1=obj1;
    }
	public JScrollPane showMarksPanel() throws IOException{
		try{
            Object obj = new JSONParser().parse(new FileReader(obj1.paperFilePath));
        try{
            reportCard=(JSONArray)new JSONParser().parse(new FileReader(obj1.resultFilePath));
        }
        catch(Exception e){
            FileWriter fw=new FileWriter("Result.json");
            fw.append(((JSONArray)(new JSONParser()).parse("[]")).toJSONString());
            fw.flush();
            fw.close();
            reportCard=((JSONArray)(new JSONParser()).parse("[]"));
        }
        
        
        JSONArray jo = (JSONArray) obj;
        noofques=jo.size();

        JPanel glass = new JPanel();
        BoxLayout bl=new BoxLayout(glass,BoxLayout.Y_AXIS);
        glass.setLayout(bl);
        JPanel[] questions=new JPanel[noofques];
       
        JLabel header=new JLabel("Questions");
        Font font=new Font("Verdana",Font.BOLD,25);

        header.setFont(font);
        header.setForeground(Color.BLUE);
        glass.add(header);

        for(int i=0;i<noofques;++i){
            JSONObject jb=(JSONObject)jo.get(i);
            long quesno=(Long)jb.get("Question-no");
            
            Integer jparts=new Integer(((Long)jb.get("no-of-parts")).intValue());
            questions[i]=new JPanel();
            BoxLayout bl1=new BoxLayout(questions[i],BoxLayout.Y_AXIS);
            questions[i].setLayout(bl1);

            questions[i].setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 0));
            JLabel lb=new JLabel("<html>"+quesno+". "+"</html>",SwingConstants.CENTER);
            questions[i].add(lb);
            questions[i].add(Box.createRigidArea(new Dimension(0,10)));
            
            JPanel marksPanel[]=new JPanel[jparts];
            marksClass obj1=new marksClass();
            char ch=97;

            for(int j=0;j<marksPanel.length;++j){
                marksPanel[j]=new JPanel();
                lb=new JLabel("<html>"+ch+". "+"</html>" ,SwingConstants.CENTER);
                questions[i].add(lb);
                marksPanel[j].setLayout(new FlowLayout(FlowLayout.LEFT));

                marksPanel[j].setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                JTextField partsMarksField=new JTextField("0",3);
                marksPanel[j].add(partsMarksField);

                obj1.partsMarks.add(partsMarksField);
                questions[i].add(marksPanel[j]);
                questions[i].add(Box.createRigidArea(new Dimension(0,10)));
                ++ch;
                
            }
            JTextField jtf=new JTextField("0",3);
           
            obj1.questionMarks=jtf;
            JPanel marksfield=new JPanel();

            marksfield.setLayout(new FlowLayout(FlowLayout.LEFT));
            marksfield.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            marksfield.add(jtf);
            questions[i].add(marksfield);

            glass.add(questions[i]);
            mrkcls.add(obj1);
        }
        
        JPanel rollnopane=new JPanel();
        rollnopane.setLayout(new FlowLayout(FlowLayout.CENTER));

        rollnopane.add(new JLabel("Enter rollno:"));

        rollnopane.add(rollnoprompt);
        glass.add(rollnopane);

        glass.setVisible(true);
        //System.out.println(mrkcls);

        JScrollPane scrollPane=new JScrollPane(glass, 
   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }
    catch(Exception e){}
    return null;
	}
}