package jbl.javaDev.swingSupport;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/********************************************************************
 *  MenuDialog.java 
 *  A dialog width animation abilities
 * 
 *  Jonathan B. Lazar
 *  jonathanlazar17@gmail.com
 *  Started: December 10 2016
 *  Updated: June 22 2019
 *  
 *
 * Copyright (c) 2016 [Jonathan B. Lazar]
 *
 *
 *******************************************************************/
public class MenuDialog extends JDialog{
	
    private int x,y;
    public final static int DIALOG_APPEARANCE_RIGHT_SIDE=0;
    public final static int DIALOG_APPEARANCE_LEFT_SIDE=1;
    public final static int DIALOG_APPEARANCE_DOWN_SIDE_LEFT_ALIGN=2;
    public final static int DIALOG_APPEARANCE_DOWN_SIDE_RIGHT_ALIGN=3;
    private javax.swing.Timer showEffect;
    private javax.swing.Timer slideShowEffect;
    private javax.swing.Timer fadeEffect;
    private javax.swing.Timer slideAwayEffect;
    private int dialogAppearance=0;	
    private boolean isAnimationRunning=false;
    private MenuHover menuHover;
       

    /**
     *  MenuDialog constructor 
     *  -set dialog as undecorated and opacity to transparent
     *  -added Global MouseListener within all components in Event Dispatched Thread due to following circumstances
     *  *   when mouse is exited in dialog it doesn't dissappear because of the component above the dialog which is
     *  *   completely covered by the component 
     */
    public MenuDialog(){
        
        this.setUndecorated(true);
        this.setOpacity(0.0f);
        
		/*	
                 *      Counter measure if MenuDialog behaves improperly
		 *	minsan kasi pag itong dialog na ito ay natakpan ng iba pang components
		 *	ay hindi madedetect ng mouse pointer ang mouseExited ng MenuDialog
		 *	kaya ang ginawa ko gumawa ako ng Global MouseListener within all components 
                 *      na nasa Event Dispatched Thread and then listens for all mouseExited components, 
                 *      once na ang Global MouseListener ay nakadetect ng mouseExited sa kahit na kaninong
                 *      component i-che-check ng Global MouseListener if yung MenuDialog is opaque then if
                 *      the animation of MenuDialog still runs
                 *
		 */
                
           
        Toolkit.getDefaultToolkit().addAWTEventListener(
                
            new AWTEventListener(){
                
                public void eventDispatched(AWTEvent e){
                    
                    if(e instanceof MouseEvent){
                        
                        MouseEvent event=(MouseEvent)e;
                        if(!(event.getSource()==MenuDialog.this.menuHover)){
                            
                            if(event.getID()==MouseEvent.MOUSE_EXITED){
                               
                                try{
                                    
                                    if(MenuDialog.this.getOpacity()==1.0f){
                                         
                                        if(!isAnimationRunning()){
                                            
                                            if(getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_LEFT_ALIGN||getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_RIGHT_ALIGN){
                                                
                                                if(!(MouseInfo.getPointerInfo().getLocation().x>MenuDialog.this.getLocation().x
                                                    &&MouseInfo.getPointerInfo().getLocation().x<MenuDialog.this.getLocation().x+MenuDialog.this.getWidth()
                                                    &&MouseInfo.getPointerInfo().getLocation().y>MenuDialog.this.getLocation().y
                                                    &&MouseInfo.getPointerInfo().getLocation().y<MenuDialog.this.getLocation().y+MenuDialog.this.getHeight())){
                                                
                                                    animateExit();
                                                
                                                }
                                                
                                            }
                                            else if(getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_LEFT_SIDE){
                                                
                                                if(MouseInfo.getPointerInfo().getLocation().x<MenuDialog.this.getLocation().x
                                                    ||MouseInfo.getPointerInfo().getLocation().y<MenuDialog.this.getLocation().y
                                                    ||(MouseInfo.getPointerInfo().getLocation().x>MenuDialog.this.getLocation().x+MenuDialog.this.getWidth()
                                                        &&MouseInfo.getPointerInfo().getLocation().y>MenuDialog.this.menuHover.getLocationOnScreen().y+MenuDialog.this.menuHover.getHeight())
                                                    ||MouseInfo.getPointerInfo().getLocation().y>MenuDialog.this.getLocation().y+MenuDialog.this.getHeight()){
                                                    
                                                    animateExit();
                                                    
                                                }
                                                
                                            }
                                            
                                        
                                        }
                                    
                                    }
                                
                                }
                                
                                catch(IllegalComponentStateException ie){
                                
                                    ;
                                
                                }
                            
                            }
                        
                        }
                    
                    }
                
                }
            
            }
        ,AWTEvent.MOUSE_MOTION_EVENT_MASK+AWTEvent.MOUSE_EVENT_MASK);
    
    }
    
    /**
     * The isAnimationRunning method to check if animation still runs
     * @return boolean
     */
    protected boolean isAnimationRunning(){
    
        return this.isAnimationRunning;
    
    }
        
    /**
     * The setAnimationRunning method to set if animation is running
     * @param isAnimationRunning 
     */
    protected void setAnimationRunning(boolean isAnimationRunning){
    
        this.isAnimationRunning=isAnimationRunning;
    
    }
      
    /**
     * The setDialogAppearance method to set dialog appearances
     * @param value constant value
     */
    public void setDialogAppearance(int value){

        this.dialogAppearance=value;
    
    }
	
    /**
     * The getDialogAppearance method to get the appearance of the dialog
     * @return int constant value
     */
    public int getDialogAppearance(){

        return this.dialogAppearance;
    
    }
	
    protected void animateExit(){
        
        this.setAnimationRunning(true);
        
        if(fadeEffect==null)
            fadeEffect=new javax.swing.Timer(10,new FadeEffectListenerHandler());
        
        if(this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_LEFT_ALIGN||this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_RIGHT_ALIGN){
            
            if(slideAwayEffect==null)
                slideAwayEffect=new javax.swing.Timer(10,new SlideAwayEffectListenerHandler());
            
            slideAwayEffect.start();
        
        }

        fadeEffect.start();
        
    
    }
        
    /**
     * The animate method to animate the dialog according to it's appearance
     * @param x initial x location
     * @param y initial y location
     */
    protected void animate(int x,int y,MenuHover menuHover){
        
        this.menuHover=menuHover;
        this.setLocation(x,y);
        this.setOpacity(0.0f);
        this.setVisible(true);
        this.setAnimationRunning(true);
        
        if(showEffect==null)
            showEffect=new javax.swing.Timer(10,new ShowEffectListenerHandler());
        
        if(this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_LEFT_ALIGN||this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_RIGHT_ALIGN){
            
            if(slideShowEffect==null)
                slideShowEffect=new javax.swing.Timer(10,new SlideShowEffectListenerHandler());
            
            slideShowEffect.start();
        
        }
        
        showEffect.start();
        
    }
        
    
    
    private class ShowEffectListenerHandler implements ActionListener{
        
        private float opacity=0.0f;
        
        public void actionPerformed(ActionEvent e){
            
            if(this.opacity!=1.0000001f){
            
                MenuDialog.this.setOpacity(this.opacity);
                this.opacity+=0.1f;
            
            }
            
            else{
                
                this.opacity=0.0f;
                MenuDialog.this.setOpacity(1.0f);
                
                if(MenuDialog.this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_LEFT_SIDE||MenuDialog.this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_RIGHT_SIDE){
                
                    MenuDialog.this.setAnimationRunning(false);
                
                }
                
                showEffect.stop();
            
            }
        
        }
    
    }
    
    private class SlideShowEffectListenerHandler implements ActionListener{
        
        private int i=0;
        private int initialPosLeft=menuHover.getLocationOnScreen().x-30;
        private int initialPosRight=(menuHover.getLocationOnScreen().x-(MenuDialog.this.getWidth()-menuHover.getWidth()))+30;
        
        public void actionPerformed(ActionEvent e){
            
            try{
                
                if(MenuDialog.this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_LEFT_ALIGN){
                
                    if(MenuDialog.this.getLocation().x<=MenuDialog.this.menuHover.getLocationOnScreen().x){
                    
                        MenuDialog.this.setLocation(this.initialPosLeft+this.i,MenuDialog.this.getLocation().y);
                        this.i+=3;
                    
                    }
                    
                    else{
                        
                        resetInstance();
                        MenuDialog.this.setAnimationRunning(false);
                        slideShowEffect.stop();
                    
                    }
                
                }
                
                else if(MenuDialog.this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_RIGHT_ALIGN){
                    
                    if((MenuDialog.this.menuHover.getLocationOnScreen().x+MenuDialog.this.menuHover.getWidth())<=(MenuDialog.this.getLocation().x+MenuDialog.this.getWidth())){
                    
                        MenuDialog.this.setLocation(this.initialPosRight-this.i,MenuDialog.this.getLocation().y);
                        this.i+=3;
                    
                    }
                    else{
                        
                        resetInstance();
                        MenuDialog.this.setAnimationRunning(false);
                        slideShowEffect.stop();
                    
                    }
                
                }
            
            }
            
            catch(IllegalComponentStateException iEx){
            
                ;
            
            }
            
        }
        
        private void resetInstance(){
            
            this.i=0;
            this.initialPosLeft=menuHover.getLocationOnScreen().x-30;
            this.initialPosRight=(menuHover.getLocationOnScreen().x-(MenuDialog.this.getWidth()-menuHover.getWidth()))+30;
            
        }
        
    }
    
    private class FadeEffectListenerHandler implements ActionListener{
        
        private float opacity=1.0f;
        
        public void actionPerformed(ActionEvent e){
            
            if(opacity!=0.09999993f){
	
                this.opacity-=0.1f;	
		MenuDialog.this.setOpacity(opacity);
            
            }
            
            else{
                
                this.opacity=1.0f;
                MenuDialog.this.setOpacity(0.0f);
		MenuDialog.this.setVisible(false);
		
                if(MenuDialog.this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_LEFT_SIDE||MenuDialog.this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_RIGHT_SIDE){
                    
                    MenuDialog.this.setAnimationRunning(false);
                       
                    
                }
                
                
                fadeEffect.stop();
                
            }
        
        }
        
    }
    
    private class SlideAwayEffectListenerHandler implements ActionListener{
        
        private int i=0;
        private int initialPos=MenuDialog.this.getLocation().x;
        
        public void actionPerformed(ActionEvent e){
            
            try{
            
                if(MenuDialog.this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_LEFT_ALIGN){
                
                    if(MenuDialog.this.getLocation().x>=(MenuDialog.this.menuHover.getLocationOnScreen().x-MenuDialog.this.menuHover.getWidth())){
                    
                        MenuDialog.this.setLocation(this.initialPos-this.i,MenuDialog.this.getLocation().y);
                        this.i+=3;
                    
                    }
                    
                    else{
                        
                        this.i=0;
                        MenuDialog.this.setAnimationRunning(false);
                        slideAwayEffect.stop();
                    
                    }
                
                }
            
                else if(MenuDialog.this.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_RIGHT_ALIGN){
                
                    if((MenuDialog.this.menuHover.getLocationOnScreen().x+(MenuDialog.this.menuHover.getWidth()*2))>=(MenuDialog.this.getLocation().x+MenuDialog.this.getWidth())){
                    
                        MenuDialog.this.setLocation(this.initialPos+this.i,MenuDialog.this.getLocation().y);
                        this.i+=3;
                    
                    }
                    
                    else{
                        
                        this.i=0;
                        MenuDialog.this.setAnimationRunning(false);
                        slideAwayEffect.stop();
                    
                    }
                
                } 
            
            }
            
            catch(IllegalComponentStateException iEx){
            
                ;
            
            }
        
        }
        
    }
	
}
