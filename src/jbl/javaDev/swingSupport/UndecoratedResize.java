/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbl.javaDev.swingSupport;

/********************************************************************
 *  UndecoratedResize.java 
 *  A resizing tool for undecorated window
 * 
 *  Jonathan B. Lazar
 *  jonathanlazar17@gmail.com
 *  Started: February 18 2019
 *  
 *
 * Copyright (c) 2019 [Jonathan B. Lazar]
 *
 *******************************************************************/

public class UndecoratedResize {
    
    private java.awt.Window window;
    private java.awt.Cursor cursor;
    
    /**
     * UndecoratedResize constructor to check if window is 
     * @param window 
     */
    public UndecoratedResize(javax.swing.JFrame window){
        
        try{
            
            if(!window.isUndecorated()){
                
                throw new UndecoratedWindowException("Window must be undecorated");
                
            }
            else{
                
                this.window=window;
                this.windowResize();
                
            }
            
        }
        catch(UndecoratedWindowException e){
            
            e.printStackTrace();
            
        }
        
    }
    
    public UndecoratedResize(javax.swing.JDialog window){
        
        try{
            
            if(!window.isUndecorated()){
                
                throw new UndecoratedWindowException("Window must be undecorated");
                
            }
            else{
                
                this.window=window;
                this.windowResize();
                
            }
            
        }
        catch(UndecoratedWindowException e){
            
            e.printStackTrace();
            
        }
        
    }
    
    private void windowResize(){
        
        this.window.addMouseMotionListener(new URMouseHandlerListener());
        
    }
    
    private class URMouseHandlerListener extends java.awt.event.MouseMotionAdapter{
        
        @Override
        public void mouseMoved(java.awt.event.MouseEvent e){
            
            if(e.getX()<=2){
                
                if(e.getY()<=2){
                    
                    cursor=new java.awt.Cursor(java.awt.Cursor.SE_RESIZE_CURSOR);
                    UndecoratedResize.this.window.setCursor(cursor);
                    
                }
                
                else if(e.getY()==UndecoratedResize.this.window.getHeight()-2){
                    
                    cursor=new java.awt.Cursor(java.awt.Cursor.NE_RESIZE_CURSOR);
                    UndecoratedResize.this.window.setCursor(cursor);
                    
                }
                
                else {
                    
                    cursor=new java.awt.Cursor(java.awt.Cursor.W_RESIZE_CURSOR);
                    UndecoratedResize.this.window.setCursor(cursor);
                    
                }
                
            }
            
            else if(e.getX()==UndecoratedResize.this.window.getWidth()-2){
                
                if(e.getY()<=2){
                    
                    cursor=new java.awt.Cursor(java.awt.Cursor.SW_RESIZE_CURSOR);
                    UndecoratedResize.this.window.setCursor(cursor);
                    
                }
                
                else if(e.getY()==UndecoratedResize.this.window.getHeight()-2){
                    
                    cursor=new java.awt.Cursor(java.awt.Cursor.NW_RESIZE_CURSOR);
                    UndecoratedResize.this.window.setCursor(cursor);
                    
                }
                
                else{
                    
                    cursor=new java.awt.Cursor(java.awt.Cursor.E_RESIZE_CURSOR);
                    UndecoratedResize.this.window.setCursor(cursor);
                    
                }
                
                
            }
            
            else if(e.getY()<=2){
                
                cursor=new java.awt.Cursor(java.awt.Cursor.N_RESIZE_CURSOR);
                UndecoratedResize.this.window.setCursor(cursor);
                
            }
            
            else if(e.getY()==UndecoratedResize.this.window.getHeight()-2){
                
                cursor=new java.awt.Cursor(java.awt.Cursor.S_RESIZE_CURSOR);
                UndecoratedResize.this.window.setCursor(cursor);
                
            }
            
            else if(e.getX()>=2&&e.getY()>=2){
                
                cursor=new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR);
                UndecoratedResize.this.window.setCursor(cursor);
                
            }
            
        }
        
        @Override
        public void mouseDragged(java.awt.event.MouseEvent e){
            
            if(UndecoratedResize.this.cursor.getType()==java.awt.Cursor.W_RESIZE_CURSOR){
                
                UndecoratedResize.this.window.setSize(UndecoratedResize.this.window.getWidth()-e.getX(),UndecoratedResize.this.window.getHeight());
                UndecoratedResize.this.window.setLocation(UndecoratedResize.this.window.getLocation().x+e.getX(),UndecoratedResize.this.window.getLocation().y);
                
                
                
            }
            
            else if(UndecoratedResize.this.cursor.getType()==java.awt.Cursor.E_RESIZE_CURSOR){
                
                UndecoratedResize.this.window.setSize(UndecoratedResize.this.window.getWidth()-(UndecoratedResize.this.window.getWidth()-e.getX()),UndecoratedResize.this.window.getHeight());
                
                
            }
            
            else if(UndecoratedResize.this.cursor.getType()==java.awt.Cursor.N_RESIZE_CURSOR){
                
                UndecoratedResize.this.window.setLocation(UndecoratedResize.this.window.getLocation().x,UndecoratedResize.this.window.getLocation().y+e.getY());
                UndecoratedResize.this.window.setSize(UndecoratedResize.this.window.getWidth(),UndecoratedResize.this.window.getHeight()-e.getY());
                
            }
            
            else if(UndecoratedResize.this.cursor.getType()==java.awt.Cursor.S_RESIZE_CURSOR){
                
      
                UndecoratedResize.this.window.setSize(UndecoratedResize.this.window.getWidth(),UndecoratedResize.this.window.getHeight()-(UndecoratedResize.this.window.getHeight()-e.getY()));
                
            }
            
            else if(UndecoratedResize.this.cursor.getType()==java.awt.Cursor.SE_RESIZE_CURSOR){
                
                UndecoratedResize.this.window.setSize(UndecoratedResize.this.window.getWidth()-e.getX(),UndecoratedResize.this.window.getHeight()-e.getY());
                UndecoratedResize.this.window.setLocation(UndecoratedResize.this.window.getLocation().x+e.getX(),UndecoratedResize.this.window.getLocation().y+e.getY());
                
            }
            
            else if(UndecoratedResize.this.cursor.getType()==java.awt.Cursor.SW_RESIZE_CURSOR){
                
                UndecoratedResize.this.window.setSize(UndecoratedResize.this.window.getWidth()-(UndecoratedResize.this.window.getWidth()-e.getX()),UndecoratedResize.this.window.getHeight()-e.getY());
                UndecoratedResize.this.window.setLocation(UndecoratedResize.this.window.getLocation().x-(UndecoratedResize.this.window.getWidth()-e.getX()),UndecoratedResize.this.window.getLocation().y+e.getY());
                
            }
            
            else if(UndecoratedResize.this.cursor.getType()==java.awt.Cursor.NE_RESIZE_CURSOR){
                
                UndecoratedResize.this.window.setSize(UndecoratedResize.this.window.getWidth()-e.getX(),UndecoratedResize.this.window.getHeight()-(UndecoratedResize.this.window.getHeight()-e.getY()));
                UndecoratedResize.this.window.setLocation(UndecoratedResize.this.window.getLocation().x+e.getX(),(UndecoratedResize.this.window.getLocation().y-UndecoratedResize.this.window.getHeight())+e.getY());
                 
            }
            
            else if(UndecoratedResize.this.cursor.getType()==java.awt.Cursor.NW_RESIZE_CURSOR){
                
                UndecoratedResize.this.window.setSize(UndecoratedResize.this.window.getWidth()-(UndecoratedResize.this.window.getWidth()-e.getX()),UndecoratedResize.this.window.getHeight()-(UndecoratedResize.this.window.getHeight()-e.getY()));
                 
            }
            
        }
    }
    
}
