import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.*;
import java.util.concurrent.TimeUnit;
import classes.answersheetclass.*;
import classes.questiondetailclass.*;
import classes.resultclass.*;

public class HomeWindow extends JFrame{
    static int height=400,width=500;
	public HomeWindow(){
		super("Answer sheet checker");
		createAndShowGUI();
	}
	public void createAndShowGUI(){
		this.setLayout(new GridBagLayout());
		JPanel buttonsPanel=new JPanel();
		BoxLayout bl1=new BoxLayout(buttonsPanel,BoxLayout.Y_AXIS);
		buttonsPanel.setLayout(bl1);
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 0));
		JButton createNewPaper=new JButton("Create new Paper");
		buttonsPanel.add(createNewPaper);
		try{
			createNewPaper.addActionListener(e->createPaper());
		}
		catch(Exception e){}
		JButton useExistingPaper=new JButton("Use Existing Paper");
		buttonsPanel.add(Box.createRigidArea(new Dimension(0,10)));
		buttonsPanel.add(useExistingPaper);
		try{
			useExistingPaper.addActionListener(e->usePaper());
		}
		catch(Exception e){}
		buttonsPanel.add(Box.createRigidArea(new Dimension(0,10)));
		JButton seeResult=new JButton("See Result");
		buttonsPanel.add(seeResult);
		try{
			seeResult.addActionListener(e->seeStudentResult());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		JPanel messagePanel=new JPanel();
		BoxLayout bl2=new BoxLayout(messagePanel,BoxLayout.Y_AXIS);
		messagePanel.setLayout(bl2);
		messagePanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
		JLabel message=new JLabel("<html>If you select create new paper, the paper<br/>"+
			" will be saved into a JSON file. Otherwise, on selecting<br/>"+
			"use existing paper, you will choose the esisting json paper<br/>"+
			" format of the question paper. </html>");
		message.setForeground(Color.RED);
		messagePanel.add(message);
		this.add(buttonsPanel);
		this.add(messagePanel);
		//this.setSize(height,width);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	public void createPaper(){
		questionDetails obj=new questionDetails();
		obj.fillQuestionDetail();
	}
	public void usePaper(){
		String paperFilePath = "",resultFilePath="",uncheckedFileDirectory="",checkedFileDirectory="";
        
    	JFileChooser fileChooser=new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files","json");
        fileChooser.setFileFilter(filter);

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Choose Question Paper(.json only)");

        try{int userSelection=fileChooser.showOpenDialog(this);
        if(userSelection==JFileChooser.APPROVE_OPTION){

            paperFilePath=fileChooser.getSelectedFile().getAbsolutePath();
        }
        else if(userSelection==JFileChooser.CANCEL_OPTION){
        	return;
        }
    }
        catch(Exception e){}

        try{TimeUnit.SECONDS.sleep(1);}
        catch(Exception e){}

        fileChooser=new JFileChooser();

        fileChooser.setFileFilter(filter);

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Specify Result File (.json only)");

        try{int userSelection=fileChooser.showOpenDialog(this);
        if(userSelection==JFileChooser.APPROVE_OPTION){

            resultFilePath=fileChooser.getSelectedFile().getAbsolutePath();
        }
        else if(userSelection==JFileChooser.CANCEL_OPTION){
        	return;
    }
}
        catch(Exception e){}
        try{TimeUnit.SECONDS.sleep(1);}
        catch(Exception e){}

        fileChooser=new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Specify directory for unchecked files");

        try{int userSelection=fileChooser.showOpenDialog(this);
        if(userSelection==JFileChooser.APPROVE_OPTION){

            uncheckedFileDirectory=fileChooser.getSelectedFile().getAbsolutePath();
        }
        else if(userSelection==JFileChooser.CANCEL_OPTION){
        	return;
    }
}
        catch(Exception e){}
        try{TimeUnit.SECONDS.sleep(1);}
        catch(Exception e){}

        fileChooser=new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Specify directory for checked files");

        try{int userSelection=fileChooser.showOpenDialog(this);
        if(userSelection==JFileChooser.APPROVE_OPTION){

            checkedFileDirectory=fileChooser.getSelectedFile().getAbsolutePath();
        }
        else if(userSelection==JFileChooser.CANCEL_OPTION){
        	return;
    }
}
        catch(Exception e){}

        try{ 
        	answerSheetWindow1 sendObj=new answerSheetWindow1(paperFilePath,resultFilePath,checkedFileDirectory,uncheckedFileDirectory);
        	sendObj.showMainPanel();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
	}
	public void seeStudentResult(){
		String resultFilePath="";
		JFileChooser fileChooser=new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files","json");
        fileChooser.setFileFilter(filter);

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Choose Result file(.json only)");

        try{int userSelection=fileChooser.showOpenDialog(this);
        if(userSelection==JFileChooser.APPROVE_OPTION){

            resultFilePath=fileChooser.getSelectedFile().getAbsolutePath();
        }
        else if(userSelection==JFileChooser.CANCEL_OPTION){
        	return;
        }
    }
        catch(Exception e){}
        try{
        	Result rslt=new Result(resultFilePath);
        rslt.showResult();}
        catch(Exception e){}

	}
	public static void main(String[] args) {
		HomeWindow hw=new HomeWindow();
	}
}