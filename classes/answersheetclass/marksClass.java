package classes.answersheetclass;

import javax.swing.*;
import java.util.*;


class marksClass{
    JTextField questionMarks;
    java.util.List<JTextField> partsMarks;
    marksClass(){
        questionMarks=new JTextField();
        partsMarks=new ArrayList<JTextField>();
    }
}