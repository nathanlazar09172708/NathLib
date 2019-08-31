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




package jbl.javaDev.swingSupport;


import javax.swing.*;
import java.awt.event.*;

public class MenuHover extends JLabel{
	
    private MenuDialog diag;
	
    public MenuHover(String string){
        
        super(string);
        this.addMouseListener(
                
            new MouseAdapter(){
                
                public void mouseEntered(MouseEvent e){
                    
                    if(!MenuHover.this.diag.isAnimationRunning()){
                        
                        fireDialogAppearance();
                    
                    }
                
                }
                public void mouseExited(MouseEvent e){
                    
                    //must be relative to the bounds of Label Menu
                    if(!MenuHover.this.diag.isAnimationRunning()){
                        
                        if(MenuHover.this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_RIGHT_SIDE){
                            
                            if(!(e.getX()>0&&e.getY()>0&&e.getY()<MenuHover.this.getBounds().height)){
                                
                                MenuHover.this.diag.setVisible(false);
                            
                            }
                        
                        }
                        if(MenuHover.this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_LEFT_SIDE){
                            
                            if(!(e.getX()<0&&e.getY()>0&&e.getY()<MenuHover.this.getBounds().height)){
                            
                                MenuHover.this.diag.setVisible(false);
                            
                            }
                        
                        }
                        else if(MenuHover.this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_LEFT_ALIGN||MenuHover.this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_RIGHT_ALIGN){
                            
                            if(!(e.getX()<MenuHover.this.getBounds().width&&e.getX()>0&&e.getY()>0)){
                                
                                MenuHover.this.diag.setVisible(false);
                            
                            }
                        
                        }
                    
                    }	
                
                }
            
            }
        
        );
    
    }
	
    public void setMenuDialog(MenuDialog diag){
        
        this.diag=diag;
        this.diag.regMenuHover(this);
    
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
        
        this.diag.animate(this.getLocationOnScreen().x-this.diag.getWidth(),this.getLocationOnScreen().y);
        
    }
    
    private void dialogAppearanceRightSide(){
        
        this.diag.animate(this.getLocationOnScreen().x+this.getWidth()+5,this.getLocationOnScreen().y);
        
    }
	
    private void dialogAppearanceDownSide(){
        
        if(this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_LEFT_ALIGN){
        
            this.diag.animate(this.getLocationOnScreen().x-this.getWidth(),this.getLocationOnScreen().y+this.getHeight()+5);
        
        }
        
        else if(this.diag.getDialogAppearance()==MenuDialog.DIALOG_APPEARANCE_DOWN_SIDE_RIGHT_ALIGN){
        
            this.diag.animate(this.getLocationOnScreen().x-this.diag.getWidth()+(this.getWidth()*2),this.getLocationOnScreen().y+this.getHeight()+5);
        
        }
    
    }
    
}

