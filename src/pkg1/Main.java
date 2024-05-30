package pkg1;


import com.sun.security.jgss.GSSUtil;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

interface WaterlevelObserver {
    void update(int waterlevel);
}
class Displaywindow extends JFrame implements WaterlevelObserver{
    private JLabel displaylable;
    Displaywindow(){
        setSize(350,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFont(new Font("",1,15));
        setTitle("Display");
        setLayout(new FlowLayout());

        displaylable=new JLabel("50");
        displaylable.setFont(new Font("",1,15));
        add(displaylable);

        setVisible(true);
       // i try my best
        //this is feature 3 commit
    }


    public void update(int waterlevel){
        this.displaylable.setText(waterlevel+" ");
    }

}


class Alarmwindow extends JFrame implements WaterlevelObserver{
    private JLabel alarmlable;

    Alarmwindow(){
        setSize(350,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFont(new Font("",1,15));
        setTitle("Alarm");
        setLayout(new FlowLayout());

        alarmlable=new JLabel("Off");
        alarmlable.setFont(new Font("",1,15));
        add(alarmlable);
        setVisible(true);
    }

    public void update(int waterlevel){
      this.alarmlable.setText(waterlevel >= 51 ? "On":"Off");
    }
}

class Splitterwindow extends JFrame implements WaterlevelObserver{
    private JLabel splitterlable;

    Splitterwindow(){
        setSize(350,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFont(new Font("",1,15));
        setTitle("Splitter");
        setLayout(new FlowLayout());

        splitterlable=new JLabel("Off");
        splitterlable.setFont(new Font("",1,15));
        add(splitterlable);

        setVisible(true);
    }

    public void update(int waterlevel){
        this.splitterlable.setText(waterlevel>=75 ? "On":"Off");
    }
}

class SMSsender extends JFrame implements WaterlevelObserver{
    private JLabel smslable;
    SMSsender(){
        setSize(350,400);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setFont(new Font("",1,15));
        setTitle("SMS Sender");

        smslable = new JLabel("Working ......");
        smslable.setFont(new Font("",1,15));
        add(smslable);

        setVisible(true);

    }

    public void update(int waterlevel){
        smslable.setText("SMS sending : "+waterlevel);
    }
}

class WatertankController{
    private WaterlevelObserver[] observers = new WaterlevelObserver[0];
   private int waterlevel;
   public void addObserver(WaterlevelObserver waterlevelObserver){
       WaterlevelObserver[] temp =new WaterlevelObserver[observers.length+1];
       for(int i=0 ; i<observers.length;i++){
           temp[i]=observers[i];
       }
       temp[temp.length-1]=waterlevelObserver;
       observers=temp;
   }

   public void setWaterlevel(int waterlevel){
       if(this.waterlevel!=waterlevel){
           this.waterlevel=waterlevel;
       }
        notifyobject();
   }

   public void notifyobject(){
       for(WaterlevelObserver waterlevelObserver:observers){
           waterlevelObserver.update(waterlevel);
       }
   }
}


class Watertankwindow extends JFrame{
    private JSlider slider;
    private WatertankController watertankController;
    Watertankwindow(WatertankController watertankController){
        setSize(350,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFont(new Font("",1,15));
        setTitle("Water Tank");
        setLayout(new FlowLayout());

         this.watertankController=watertankController;
        slider=new JSlider(JSlider.VERTICAL,0,100,50);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int waterlevel = slider.getValue();
                watertankController.setWaterlevel(waterlevel);
            }
        });

        add(slider);
        setVisible(true);
    }

}

class Example {
    public static void main(String[] args) {
        WatertankController watertankController = new WatertankController();
       watertankController.addObserver(new Displaywindow());
       watertankController.addObserver(new Alarmwindow());
       watertankController.addObserver(new Splitterwindow());
       watertankController.addObserver(new SMSsender());

        new Watertankwindow(watertankController);
    }
}

