/********************************************************************
 *  ToggleButton.java 
 *  ToggleButton that shows and hides component
 * 
 *  Jonathan B. Lazar
 *  jonathanlazar17@gmail.com
 *  Started: April 29 2018
 *  Updated: June 22 2019
 *  
 *  
 *
 * Copyright (c) 2018 [Jonathan B. Lazar]
 *
 *
 *******************************************************************/
package jbl.javaDev.swingSupport;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author nathan
 */
public class ToggleButton{
    
    private Timer slideAwayEffectTimer,slideShowEffectTimer;
    private JFrame window;
    private JLabel toggleLabel;
    private JDialog tail;
    
    private Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
    private Rectangle windowSize=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    
    private boolean animationRunning=false;
    
    private ImageIcon hideIcon,showIcon;
    
    
    public ToggleButton(JFrame window,int width,int height){
        
        this.window=window;
        try{
            
            this.checkToggleButtonException();
            this.window.setVisible(true);
            this.window.setLocation(this.screenSize.width-this.window.getWidth(),
                (this.screenSize.height-this.window.getHeight())-(this.screenSize.height-this.windowSize.height));
        
            tail=new javax.swing.JDialog(this.window);
            tail.setUndecorated(true);
            tail.setSize(width,height);
            tail.setBackground(new Color(0,0,0,0));
            tail.setLocation(window.getLocationOnScreen().x-width,window.getLocationOnScreen().y);
        
            toggleLabel=new JLabel();
            toggleLabel.setPreferredSize(new Dimension(width,height));
        
            tail.getContentPane().add(toggleLabel);
        
            tail.setVisible(true);
            tail.addMouseListener(new TailListenerHandler());
            
        }
        catch(ToggleButtonException e){
            
            e.printStackTrace();
            
        }
        
        
    }
    
    
    public void setToggleIcon(ImageIcon hideIcon,ImageIcon showIcon){
        
        this.hideIcon=hideIcon;
        this.showIcon=showIcon;
        
        this.setHideIcon();
        
    }
    
    private void setHideIcon(){
        
        Image hideImage=this.hideIcon.getImage().getScaledInstance(this.tail.getWidth(),this.tail.getHeight(),java.awt.Image.SCALE_SMOOTH);
        this.hideIcon.setImage(hideImage);
        this.toggleLabel.setIcon(this.hideIcon);
        
    }
    
    private void setShowIcon(){
        
        Image showImage=this.showIcon.getImage().getScaledInstance(this.tail.getWidth(),this.tail.getHeight(),java.awt.Image.SCALE_SMOOTH);
        this.showIcon.setImage(showImage);
        this.toggleLabel.setIcon(this.showIcon);
        
    }
    
    
    private void toggle(){
        
        if(this.window.getLocationOnScreen().x!=this.screenSize.width){
            
            if(this.slideAwayEffectTimer==null)
                this.slideAwayEffectTimer=new javax.swing.Timer(10,new SlideAwayEffectTimerHandler());
            
            this.slideAwayEffectTimer.start();
            
        }
        else{
                
             if(this.slideShowEffectTimer==null)
                this.slideShowEffectTimer=new javax.swing.Timer(10,new SlideShowEffectTimerHandler());
             
             this.slideShowEffectTimer.start();
             
        }

    }
    
    
    private void checkToggleButtonException()throws ToggleButtonException{
        
        if(!this.window.isUndecorated()){
            
            throw new ToggleButtonException("Frame must be undecorated");
            
        }
        
    }
    
    private class TailListenerHandler extends MouseAdapter{
        
        @Override
        public void mouseClicked(MouseEvent e){
            
            if(!ToggleButton.this.animationRunning){
                
                ToggleButton.this.toggle();
                ToggleButton.this.animationRunning=true;
                
            }
            
        }
        
    }
    
    private class SlideAwayEffectTimerHandler implements java.awt.event.ActionListener{
        
        private int i=0,width=ToggleButton.this.window.getWidth();
        private boolean b=true;
        private java.awt.Point initLocation=new java.awt.Point(ToggleButton.this.window.getLocationOnScreen().x,ToggleButton.this.window.getLocationOnScreen().y);
        
        public void actionPerformed(java.awt.event.ActionEvent e){
            
            if(this.b){
                
                ToggleButton.this.window.setLocation((this.initLocation.x+((int)this.width/2)),this.initLocation.y);
                ToggleButton.this.tail.setLocation((this.initLocation.x+((int)this.width/2))-ToggleButton.this.tail.getWidth(),this.initLocation.y);
                this.i=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width-ToggleButton.this.window.getLocationOnScreen().x;
                this.b=false;
                
            }
            else if(((int)(this.i*0.20))!=0){
                
                this.i=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width-ToggleButton.this.window.getLocationOnScreen().x;
                ToggleButton.this.window.setLocation(ToggleButton.this.window.getLocationOnScreen().x+((int)(i*0.20)),this.initLocation.y);
                ToggleButton.this.tail.setLocation(ToggleButton.this.window.getLocationOnScreen().x-ToggleButton.this.tail.getWidth(),this.initLocation.y);
                
            }
            else if(ToggleButton.this.window.getLocationOnScreen().x!=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width){
                
                ToggleButton.this.window.setLocation(ToggleButton.this.window.getLocationOnScreen().x+1,this.initLocation.y);
                ToggleButton.this.tail.setLocation(ToggleButton.this.window.getLocationOnScreen().x-ToggleButton.this.tail.getWidth(),this.initLocation.y);
                
            }
            else{
                
                this.i=0;
                this.b=true;
                ToggleButton.this.setShowIcon();
                ToggleButton.this.animationRunning=false;
                ToggleButton.this.slideAwayEffectTimer.stop();
                
            }
            
            
        }
    }
    
    private class SlideShowEffectTimerHandler implements ActionListener{
        
        private int i=0,width=ToggleButton.this.window.getWidth();
        private boolean b=true;
        private java.awt.Point initLocation=new java.awt.Point(ToggleButton.this.window.getLocationOnScreen().x,ToggleButton.this.window.getLocationOnScreen().y);
        
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e){
            
            if(this.b){
                
                ToggleButton.this.window.setLocation((this.initLocation.x-((int)this.width/2)),this.initLocation.y);
                ToggleButton.this.tail.setLocation((this.initLocation.x-((int)this.width/2))-ToggleButton.this.tail.getWidth(),this.initLocation.y);
                this.i=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width-ToggleButton.this.window.getLocationOnScreen().x;
                this.b=false;
                
            }
            else if(((int)(this.i*0.20))!=0){
                
                this.i=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width-(ToggleButton.this.window.getLocationOnScreen().x+ToggleButton.this.window.getWidth());
                ToggleButton.this.window.setLocation(ToggleButton.this.window.getLocationOnScreen().x+((int)(i*0.20)),this.initLocation.y);
                ToggleButton.this.tail.setLocation(ToggleButton.this.window.getLocationOnScreen().x-ToggleButton.this.tail.getWidth(),this.initLocation.y);
            
            }
            else if((java.awt.Toolkit.getDefaultToolkit().getScreenSize().width-ToggleButton.this.window.getWidth())!=ToggleButton.this.window.getLocationOnScreen().x){
                
                ToggleButton.this.window.setLocation(ToggleButton.this.window.getLocationOnScreen().x-1,this.initLocation.y);
                ToggleButton.this.tail.setLocation(ToggleButton.this.window.getLocationOnScreen().x-ToggleButton.this.tail.getWidth(),this.initLocation.y);
            
            }
            else{
                
                this.i=0;
                this.b=true;
                ToggleButton.this.setHideIcon();
                ToggleButton.this.animationRunning=false;
                ToggleButton.this.slideShowEffectTimer.stop();
            
            }
            
        }
        
    }
    
    
    private class ToggleButtonException extends Exception{
        
        public ToggleButtonException(String message){
            
            super(message);
        
        }
        
    }
    
}
