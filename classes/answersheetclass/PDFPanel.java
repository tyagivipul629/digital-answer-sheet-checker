package classes.answersheetclass;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import javax.swing.filechooser.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.common.MyAnnotationCallback;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 
import java.io.File.*;



class PDFPanel{

	JPanel viewerComponentPanel;
    SwingController controller;
    SwingViewBuilder factory;
    String filePath="";
    File fileToSave;
    java.util.List<marksClass> mrkcls;
    JTextField rollnoprompt;
    JSONArray reportCard;
    answerSheetWindow1 obj;

    PDFPanel(answerSheetWindow1 obj){
    	mrkcls=new ArrayList<marksClass>();
    	this.obj=obj;
    }

	public JPanel returnPanel() throws IOException{
		JPanel controls = new JPanel(new BorderLayout(10,10));
        JButton submit=new JButton("submit");

        JButton newAnswerSheet =new JButton("New Answer Sheet");
        JButton openQuestionPaper=new JButton("Open Question Paper");

        try{ openQuestionPaperClass oqpc=new openQuestionPaperClass(obj);
        	openQuestionPaper.addActionListener(e->oqpc.openQuestionPaperPDF());
        }
        catch(Exception e){}
        try{submit.addActionListener(e->actionDone());}
        catch(Exception e){}

        try{newAnswerSheet.addActionListener(e->loadNewAnswerSheet());}
        catch(Exception e){}

        JPanel buttonsPanel=new JPanel(new BorderLayout(10,10));
        buttonsPanel.add(submit, BorderLayout.EAST);
        buttonsPanel.add(newAnswerSheet, BorderLayout.WEST);
        JPanel centerPanel=new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(Color.GREEN);
        centerPanel.add(openQuestionPaper);
        buttonsPanel.add(centerPanel, BorderLayout.CENTER);
        
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonsPanel.setBackground(Color.GREEN);

        controls.add(buttonsPanel,BorderLayout.SOUTH);
        loadAnswerSheet();
        controls.add(viewerComponentPanel,BorderLayout.CENTER);

        controls.setVisible(true);
        return controls;

	}
	public void loadAnswerSheet(){
		controller = new SwingController();

        factory = new SwingViewBuilder(controller);

        viewerComponentPanel = factory.buildViewerPanel();
        controller.openDocument(filePath);
	}
	public void loadNewAnswerSheet(){
		controller.closeDocument();
        JFileChooser fileChooser=new JFileChooser(obj.uncheckedFileDirectory);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF files","pdf");
        fileChooser.setFileFilter(filter);

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Specify only a .pdf file to open");

        try{int userSelection=fileChooser.showOpenDialog(obj);
        if(userSelection==JFileChooser.APPROVE_OPTION){

            fileToSave=fileChooser.getSelectedFile();
            filePath=fileToSave.getAbsolutePath();
            controller.openDocument(filePath);
        }
    }
    catch(Exception e){}
	}
	public void actionDone(){
		//System.out.println(mrkcls);
        try{
            fileToSave.renameTo(new File(obj.checkedFileDirectory+File.separator+fileToSave.getName()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
		if(rollnoprompt.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Roll no field cannot be empty");
			return;
		}
		else{
			try{
				int i=Integer.parseInt(rollnoprompt.getText());
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null,"Roll no can only be an integer");
				return;
			}
		}

        try{
            JSONObject job=new JSONObject();
            try{job.put("roll-no",Integer.parseInt(rollnoprompt.getText()));}
            catch(Exception e){}
            JSONArray Marks=new JSONArray();

            
            for(int i=0;i<mrkcls.size();++i){
                
            JSONObject job1=new JSONObject();
            job1.put("Question-no",i+1);
            job1.put("total-marks",Float.parseFloat(mrkcls.get(i).questionMarks.getText()));
            
            JSONArray Parts=new JSONArray();
            char ch=97;
            java.util.List<JTextField> partsMarksList=mrkcls.get(i).partsMarks;

            for(int j=0;j<partsMarksList.size();++j){
                JSONObject job2=new JSONObject();
                job2.put("part",Character.toString(ch));
                job2.put("marks",Float.parseFloat(partsMarksList.get(j).getText()));

                ++ch;
                Parts.add(job2);
            }

            job1.put("Parts",Parts);
            Marks.add(job1);
        }   
        job.put("Marks",Marks);
        reportCard.add(job);
        //System.out.println(job);
        
        FileWriter file=new FileWriter(obj.resultFilePath);
        file.write(reportCard.toJSONString());

        file.flush();
        file.close();
        JOptionPane.showMessageDialog(null,"Result submitted of rollno "+rollnoprompt.getText());
        }

        catch(Exception e){
        	e.printStackTrace();
        }
    }

    }

