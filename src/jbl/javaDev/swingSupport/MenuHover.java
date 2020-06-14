package jbl.javaDev.swingSupport;

import javax.swing.*;
import java.awt.event.*;

/********************************************************************
 *  MenuHover.java 
 *  A hoverable label to show menu dialog
 * 
 *  Jonathan B. Lazar
 *  jonathanlazar17@gmail.com
 *  Started: December 10 2016
 *  
 *  
 * 
 * Copyright (c) 2016 [Jonathan B. Lazar]
 *
 *
 *******************************************************************/
public class MenuHover extends JLabel{
	
    private MenuDialog diag;
	  
    /**
     * MenuHover Constructor to register mouseListeners
     * @param string default param
     */
    public MenuHover(String string){
        
        super(string);
        this.addMouseMotionListener(
                
                new MouseMotionAdapter(){
                    
                    @Override
                    public void mouseMoved(MouseEvent e){
                        
                        if(!MenuHover.this.diag.isAnimationRunning()&&!MenuHover.this.diag.isVisible()){
                            
                            fireDialogAppearance();
                        
                        }
                    }
                    
                }
                
        );
        this.addMouseListener(
                
            new MouseAdapter(){
                
                public void mouseExited(MouseEvent e){
                    
                    //must be relative to the bounds of Label Menu
                    if(!MenuHover.this.diag.isAnimationRunning()){
                        
                        if(MenuHover.this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_RIGHT_SIDE){
                            
                            if(!(e.getX()>0&&e.getY()>0&&e.getY()<MenuHover.this.getBounds().height)){
                                
                                MenuHover.this.diag.animateExit();
                            
                            }
                        
                        }
                        if(MenuHover.this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_LEFT_SIDE){
                            
                            if(!(e.getX()<0&&e.getY()>0&&e.getY()<MenuHover.this.getBounds().height)){
                                
                                MenuHover.this.diag.animateExit();
                                
                            }
                        
                        }
                        else if(MenuHover.this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_LEFT_ALIGN||MenuHover.this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_RIGHT_ALIGN){
                            
                            if(!(e.getX()<MenuHover.this.getBounds().width&&e.getX()>0&&e.getY()>0)){
                                
                                MenuHover.this.diag.animateExit();
                            
                            }
                        
                        }
                    
                    }
                
                }
            
            }
        
        );
    
    }
	
    /**
     * The setMenuDialog to bind dialog in menuHover
     * @param diag to bind with menuHover
     */
    public void setMenuDialog(MenuDialog diag){
        
        this.diag=diag;
    
    }
        
    private void fireDialogAppearance(){
        
        if(this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_RIGHT_SIDE){
        
            this.dialogAppearanceRightSide();
        
        }
        
        if(this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_LEFT_SIDE){
        
            this.dialogAppearanceLeftSide();
        
        }
        
        else if(this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_LEFT_ALIGN||this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_RIGHT_ALIGN){
        
            this.dialogAppearanceDownSide();
        
        }
        
    }
    
    private void dialogAppearanceLeftSide(){
        
        this.diag.animate(this.getLocationOnScreen().x-this.diag.getWidth(),this.getLocationOnScreen().y,this);
        
    }
    
    private void dialogAppearanceRightSide(){
        
        this.diag.animate(this.getLocationOnScreen().x+this.getWidth()+5,this.getLocationOnScreen().y,this);
        
    }
	
    private void dialogAppearanceDownSide(){
        
        if(this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_LEFT_ALIGN){
        
            this.diag.animate(this.getLocationOnScreen().x-this.getWidth(),this.getLocationOnScreen().y+this.getHeight()+5,this);
        
        }
        
        else if(this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_RIGHT_ALIGN){
        
            this.diag.animate(this.getLocationOnScreen().x-this.diag.getWidth()+(this.getWidth()*2),this.getLocationOnScreen().y+this.getHeight()+5,this);
        
        }
    
    }
    
}

