/********************************************************************
 *  AnimatedDiag.java 
 *  A dialog with animation capabilities
 * 
 *  Jonathan B. Lazar
 *  jonathanlazar17@gmail.com
 *  Started: August 07 2018
 *  Updated: December 9 2019
 *  
 *
 * Copyright (c) 2018 [Jonathan B. Lazar]
 *
 *******************************************************************/

package jbl.javaDev.swingSupport;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


/**
 *
 * @author Nathan
 */
public class AnimatedDiag extends JDialog{
    
    private Point finalPoint;
    private Timer slideTimer,showEffectTimer,fadeEffectTimer,growTimer,shrinkTimer;
    private int finalWidth,finalHeight;
    private boolean isAnimationRunning=false;
    private BufferedImage img;
    private Container container;
    
    private int gWidth=0,gHeight=0;
    
    public AnimatedDiag(Point finalPoint){
        
        this.setUndecorated(true);
        this.finalPoint=finalPoint;
        
    }
    
    public void activate(){
       
        if(!isAnimationRunning()){
            
            setAnimationRunning(true);
            
            if(!this.isVisible()){
                
                this.setOpacity(0.0f);
                this.setVisible(true);
                
                if(this.img==null){
                    
                    this.snapShot(this.getContentPane());
                    
                }
                
                if(growTimer==null)
                    growTimer=new Timer(10,new GrowActionListener());
                
                if(slideTimer==null)
                    slideTimer=new Timer(10,new SlideActionListener());
                
                if(showEffectTimer==null)
                    showEffectTimer=new Timer(10,new ShowEffectActionListener());
                
                growTimer.start();
                slideTimer.start();
                showEffectTimer.start();      
                
            }
        
            else{
                
                this.snapShot(this.getContentPane());
                
                if(shrinkTimer==null)
                    shrinkTimer=new Timer(10,new ShrinkActionListener());
                
                if(slideTimer==null)
                    slideTimer=new Timer(10,new SlideActionListener());
                
                if(fadeEffectTimer==null)
                    fadeEffectTimer=new Timer(1,new FadeEffectActionListener());
                
                shrinkTimer.start();
                slideTimer.start();
                fadeEffectTimer.start();  
            
            }
            
        }
        
    }
    
    @Override
    public void setSize(int width,int height){
        
        this.finalWidth=width;
        this.finalHeight=height;
        super.setSize(width,height);
    
    }
    
    private void snapShot(Container contentPane){
        
        this.container=contentPane;
        this.img=new BufferedImage(contentPane.getWidth(),contentPane.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        contentPane.paint(g2);
        
    }
    
    private boolean isAnimationRunning(){
        
        return this.isAnimationRunning;
        
    }
    
    private void setAnimationRunning(boolean isAnimationRunning){
        
        this.isAnimationRunning=isAnimationRunning;
        
    }
    
    public void imageDraw(){
    
        this.remove(this.getContentPane());
        this.setContentPane(new Container(){
                    
            @Override
            public void paint(Graphics g){
                        
                Graphics2D g2d=(Graphics2D)g;
                g2d.drawImage(AnimatedDiag.this.img,0,0,AnimatedDiag.this.gWidth,AnimatedDiag.this.gHeight,null);
                           
            }
                    
        });
        
        AnimatedDiag.super.setSize(AnimatedDiag.this.gWidth,AnimatedDiag.this.gHeight);
        this.revalidate();
        
    }
    
    public void setFinalPoint(Point finalPoint){
        
        this.finalPoint=finalPoint;
        
    }
    
    public Point getFinalPoint(){
        
        return this.finalPoint;
        
    }
    
    private class SlideActionListener implements ActionListener{
        
        private boolean b=true;
        private int ix=(int)((AnimatedDiag.this.finalPoint.x-AnimatedDiag.this.getLocationOnScreen().x)*0.20),
                iy=(int)((AnimatedDiag.this.finalPoint.y-AnimatedDiag.this.getLocationOnScreen().y)*0.20);
        private Point finalPoint=new Point(AnimatedDiag.this.getLocationOnScreen().x,AnimatedDiag.this.getLocationOnScreen().y);
        private int diffX,diffY,rate,mod,movement;
        private final static int UPPER_LEFT=0,UPPER_RIGHT=1,LOWER_LEFT=2,LOWER_RIGHT=3;
        
        @Override
        public void actionPerformed(ActionEvent e){
                    
            if(this.b){
                        
                AnimatedDiag.this.setLocation(AnimatedDiag.this.getLocationOnScreen().x+ix,AnimatedDiag.this.getLocationOnScreen().y+iy);
                this.b=false;
                this.ix=(int)(AnimatedDiag.this.finalPoint.x-AnimatedDiag.this.getLocationOnScreen().x);
                    this.iy=(int)(AnimatedDiag.this.finalPoint.y-AnimatedDiag.this.getLocationOnScreen().y);
                        
            }
                    
            else if(((int)(this.ix*0.20))!=0&&((int)(this.iy*0.20))!=0){
                
                this.ix=(int)(AnimatedDiag.this.finalPoint.x-AnimatedDiag.this.getLocationOnScreen().x);
                    this.iy=(int)(AnimatedDiag.this.finalPoint.y-AnimatedDiag.this.getLocationOnScreen().y);
                AnimatedDiag.this.setLocation(AnimatedDiag.this.getLocationOnScreen().x+((int)(this.ix*0.20)),
                    AnimatedDiag.this.getLocationOnScreen().y+((int)(this.iy*0.20)));
                
            }
            
            else if((AnimatedDiag.this.getLocation().x!=AnimatedDiag.this.finalPoint.x)
                    &&(AnimatedDiag.this.getLocation().y!=AnimatedDiag.this.finalPoint.y)){
                
                if(AnimatedDiag.this.getLocation().x>AnimatedDiag.this.finalPoint.x){
                    
                    this.diffX=AnimatedDiag.this.getLocation().x-AnimatedDiag.this.finalPoint.x;
                    
                    if(AnimatedDiag.this.getLocation().y>AnimatedDiag.this.finalPoint.y){
                     
                        this.diffY=AnimatedDiag.this.getLocation().y-AnimatedDiag.this.finalPoint.y;
                        this.movement=UPPER_LEFT;
                        
                    }
                    
                    else{
                        
                        this.diffY=AnimatedDiag.this.finalPoint.y-AnimatedDiag.this.getLocation().y;
                        this.movement=UPPER_RIGHT;
                        
                    }
                    
                    if(this.diffX>this.diffY){
                        
                        this.rate=(int)(this.diffX/this.diffY);
                        this.mod=this.diffX%this.diffY;
                        
                    }
                    
                    else{
                        
                        this.rate=(int)(this.diffY/this.diffX);
                        this.mod=this.diffY%this.diffX;
                        
                    }
                    
                }
                
                else{
                    
                    this.diffX=AnimatedDiag.this.finalPoint.x-AnimatedDiag.this.getLocation().x;
                     
                    if(AnimatedDiag.this.getLocation().y>AnimatedDiag.this.finalPoint.y){
                     
                        this.diffY=AnimatedDiag.this.getLocation().y-AnimatedDiag.this.finalPoint.y;
                        this.movement=LOWER_LEFT;
                    }
                    
                    else{
                        
                        this.diffY=AnimatedDiag.this.finalPoint.y-AnimatedDiag.this.getLocation().y;
                        this.movement=LOWER_RIGHT;
                        
                    }
                    
                    if(this.diffX>this.diffY){
                        
                        this.rate=(int)(this.diffX/this.diffY);
                        this.mod=this.diffX%this.diffY;
                        
                    }
                    
                    else{
                        
                        this.rate=(int)(this.diffY/this.diffX);
                        this.mod=this.diffY%this.diffX;
                        
                    }
                    
                }
                
               
                    
                switch(this.movement){
                        
                    case UPPER_LEFT:
                        
                        if(this.mod!=0)
                            AnimatedDiag.this.setLocation(AnimatedDiag.this.getLocation().x-rate,AnimatedDiag.this.getLocation().y-1);
                        
                        else
                            AnimatedDiag.this.setLocation(AnimatedDiag.this.getLocation().x-(rate+mod),AnimatedDiag.this.getLocation().y-1);
                        
                        break;
                        
                    case UPPER_RIGHT:
                        
                        if(this.mod!=0)
                            AnimatedDiag.this.setLocation(AnimatedDiag.this.getLocation().x-rate,AnimatedDiag.this.getLocation().y+1);
                        
                        else
                            AnimatedDiag.this.setLocation(AnimatedDiag.this.getLocation().x-(rate+mod),AnimatedDiag.this.getLocation().y+1);
                            
                        break;
                            
                    case LOWER_RIGHT:
                        
                        if(this.mod!=0)
                            AnimatedDiag.this.setLocation(AnimatedDiag.this.getLocation().x+rate,AnimatedDiag.this.getLocation().y+1);
                        
                        else
                            AnimatedDiag.this.setLocation(AnimatedDiag.this.getLocation().x+(rate+mod),AnimatedDiag.this.getLocation().y+1);
                            
                        break;
                        
                    case LOWER_LEFT:
                        
                        if(this.mod!=0)
                            AnimatedDiag.this.setLocation(AnimatedDiag.this.getLocation().x+rate,AnimatedDiag.this.getLocation().y-1);
                        
                        else
                            AnimatedDiag.this.setLocation(AnimatedDiag.this.getLocation().x+(rate+mod),AnimatedDiag.this.getLocation().y-1);
                            
                        break;
                            
                    }
                
            }
            
            else{
                
                this.b=true;
                this.ix=(int)((AnimatedDiag.this.finalPoint.x-AnimatedDiag.this.getLocationOnScreen().x)*0.20);
                this.iy=(int)((AnimatedDiag.this.finalPoint.y-AnimatedDiag.this.getLocationOnScreen().y)*0.20);
                AnimatedDiag.this.finalPoint=this.finalPoint;
                this.finalPoint=new Point(AnimatedDiag.this.getLocationOnScreen().x,AnimatedDiag.this.getLocationOnScreen().y);
                AnimatedDiag.this.setAnimationRunning(false);
                AnimatedDiag.this.slideTimer.stop();
                
            }
                
        }
        
    }
    private class GrowActionListener implements ActionListener{
        
        private boolean b=true;
        private int iwidth=(int)(AnimatedDiag.this.finalWidth/2),iheight=(int)(AnimatedDiag.this.finalHeight/2);
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            if(this.b){
                
                AnimatedDiag.this.gWidth=iwidth;
                AnimatedDiag.this.gHeight=iheight;
                AnimatedDiag.this.imageDraw();
                this.b=false;
                this.iwidth=(int)(AnimatedDiag.this.finalWidth-AnimatedDiag.this.gWidth);
                this.iheight=(int)(AnimatedDiag.this.finalHeight-AnimatedDiag.this.gHeight);
                
            }
            
            else if((((int)(this.iwidth*0.20))!=0)||(((int)(this.iheight*0.20))!=0)){
                
                this.iwidth=(int)(AnimatedDiag.this.finalWidth-AnimatedDiag.this.gWidth);
                this.iheight=(int)(AnimatedDiag.this.finalHeight-AnimatedDiag.this.gHeight);
                AnimatedDiag.this.gWidth=AnimatedDiag.this.gWidth+((int)(this.iwidth*0.20));
                AnimatedDiag.this.gHeight=AnimatedDiag.this.gHeight+((int)(this.iheight*0.20));
                AnimatedDiag.this.imageDraw();
                
            }
            
            else if(AnimatedDiag.this.finalWidth!=AnimatedDiag.this.gWidth&&AnimatedDiag.this.finalHeight!=AnimatedDiag.this.gHeight){
                
                AnimatedDiag.this.gWidth+=1;
                AnimatedDiag.this.gHeight+=1;
                AnimatedDiag.this.imageDraw();
               
            }
            
            else{
                
                this.b=true;
                this.iwidth=(int)(AnimatedDiag.this.finalWidth/2);
                this.iheight=(int)(AnimatedDiag.this.finalHeight/2);
                AnimatedDiag.this.remove(AnimatedDiag.this.getContentPane());
                AnimatedDiag.this.setContentPane(AnimatedDiag.this.container);
                AnimatedDiag.this.revalidate();
                AnimatedDiag.this.growTimer.stop();
                
            }
            
        }
        
    }
    
    private class ShrinkActionListener implements ActionListener{
        
        private boolean b=true;
        private int iwidth=(int)(AnimatedDiag.this.finalWidth/2),iheight=(int)(AnimatedDiag.this.finalHeight/2);
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            if(this.b){
                
                AnimatedDiag.this.gWidth=iwidth;
                AnimatedDiag.this.gHeight=iheight;
                AnimatedDiag.this.imageDraw();
                this.b=false;
                this.iwidth=(int)AnimatedDiag.this.gWidth;
                this.iheight=(int)AnimatedDiag.this.gHeight;
                
            }
            else if((((int)(this.iwidth*0.20))!=0)||(((int)(this.iheight*0.20))!=0)){
                
                this.iwidth=(int)AnimatedDiag.this.gWidth;
                this.iheight=(int)AnimatedDiag.this.gHeight;
                AnimatedDiag.this.gWidth=AnimatedDiag.this.gWidth-((int)(this.iwidth*0.20));
                AnimatedDiag.this.gHeight=AnimatedDiag.this.gHeight-((int)(this.iheight*0.20));
                AnimatedDiag.this.imageDraw();
                
            }
            else if(AnimatedDiag.this.gWidth>0&&AnimatedDiag.this.gHeight>0){
                
                AnimatedDiag.this.gWidth-=1;
                AnimatedDiag.this.gHeight-=1;
                AnimatedDiag.this.imageDraw();
                
            }
            else{
                
                this.b=true;
                this.iwidth=(int)(AnimatedDiag.this.finalWidth/2);
                this.iheight=(int)(AnimatedDiag.this.finalHeight/2);
                AnimatedDiag.this.shrinkTimer.stop();
                
            }
            
        }
        
    }
    
    private class ShowEffectActionListener implements ActionListener{
        
        private float opacity=0.0f;
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            if(opacity!=1.0000001f){
                
		AnimatedDiag.this.setOpacity(opacity);
                this.opacity+=0.1f;	
                
            }
            else{
                
                if(!AnimatedDiag.this.slideTimer.isRunning()){
                    
                    this.opacity=0.0f;
                    AnimatedDiag.this.setOpacity(1.0f);
                    AnimatedDiag.this.showEffectTimer.stop();
                
                }
                
            }
            
        }
        
    }
    
    private class FadeEffectActionListener implements ActionListener{
        
        private float opacity=1.0f;
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            if(opacity!=0.09999993f){
                
		AnimatedDiag.this.setOpacity(opacity);
                this.opacity-=0.1f;	
            
            }
            else{
                
                if(!AnimatedDiag.this.slideTimer.isRunning()){
                    
                    this.opacity=1.0f;
                    AnimatedDiag.this.setOpacity(0.0f);
                    AnimatedDiag.this.setVisible(false);
                    AnimatedDiag.this.fadeEffectTimer.stop();
                    
                }
                
            }
            
        }
        
    }
}
