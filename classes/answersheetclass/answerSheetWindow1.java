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
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.common.MyAnnotationCallback;
import java.net.URL;



 
public class answerSheetWindow1 extends JFrame {

    private int width = 700;
    private int height = 800;
    String resultFilePath,paperFilePath,checkedFileDirectory,uncheckedFileDirectory;

    public answerSheetWindow1(String paperFilePath, String resultFilePath, String checkedFileDirectory, String uncheckedFileDirectory){
        this.resultFilePath=resultFilePath;
        this.paperFilePath=paperFilePath;
        this.checkedFileDirectory=checkedFileDirectory;
        this.uncheckedFileDirectory=uncheckedFileDirectory;
    }

    public void showMainPanel()throws IOException {

        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setSize(width, height);

        splitPane.setResizeWeight(0.7);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        PDFPanel pdfpanel=new PDFPanel(this);
        MarksPanel markspanel=new MarksPanel(this);

        JPanel controls=pdfpanel.returnPanel();
        JScrollPane scrollPane=markspanel.showMarksPanel();

        pdfpanel.mrkcls=markspanel.mrkcls;
        pdfpanel.rollnoprompt=markspanel.rollnoprompt;
        pdfpanel.reportCard=markspanel.reportCard;

        splitPane.setLeftComponent(controls);
        splitPane.setRightComponent(scrollPane);

        this.add(splitPane);
    }

    public JFrame returnFrame(){
        return this;
    } 
}
        
    