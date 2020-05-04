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

class openQuestionPaperClass extends JFrame{
	answerSheetWindow1 obj;
	openQuestionPaperClass(answerSheetWindow1 obj){
		this.obj=obj;
	}
	public void openQuestionPaperPDF(){
    	try{String filePath = "";
        
    	JFileChooser fileChooser=new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF files","pdf");
        fileChooser.setFileFilter(filter);

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Specify only a .pdf file to open");

        try{int userSelection=fileChooser.showOpenDialog(obj);
        if(userSelection==JFileChooser.APPROVE_OPTION){

            filePath=fileChooser.getSelectedFile().getAbsolutePath();
        }
    }
        catch(Exception e){}
        
        SwingController controller = new SwingController();

        SwingViewBuilder factory = new SwingViewBuilder(controller);

        JPanel viewerComponentPanel = factory.buildViewerPanel();

        controller.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(
                        controller.getDocumentViewController()));

        JFrame applicationFrame = new JFrame();
        applicationFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JScrollPane scrollPane=new JScrollPane(viewerComponentPanel, 
   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        applicationFrame.add(viewerComponentPanel);

        
        controller.openDocument(filePath);

       
        applicationFrame.pack();
        applicationFrame.setVisible(true);}
        catch(Exception e){}
    }
}