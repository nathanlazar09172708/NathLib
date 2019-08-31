/********************************************************************
 *  Carousel.java 
 *  A container that holds sliders
 * 
 *  Jonathan B. Lazar
 *  jonathanlazar17@gmail.com
 *  Started: December 12 2016
 *  Updated: June 22 2019
 *  
 *
 * Copyright (c) 2016 [Jonathan B. Lazar]
 *
 *******************************************************************/




package jbl.javaDev.swingSupport;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;


public class Carousel extends JPanel{
	
    private ArrayList<CarouselItems> carouselItems=new ArrayList<CarouselItems>();
    private int count=0;
    private int xBounds=0;
    private int carouselItemCentered=0;
    private javax.swing.Timer slidingEffectTimer;
    private SlidingEffectListenerHandler listenerHandler;
    private boolean isAnimationRunning=false;
	
    public Carousel(){
            
        this.setLayout(null);
        this.setOpaque(false);
	
    }
	
    public void add(JComponent clickableEntities,JPanel panel){
	
        // Do not use instance count as parameter inside MouseListener, pass the value through local variable
	int localCount=count;
	panel.setBounds(xBounds,0,this.getBounds().width,this.getBounds().height);
	xBounds+=this.getBounds().width;
	carouselItems.add(new CarouselItems(clickableEntities,panel));
                
	clickableEntities.addMouseListener(
		
            new MouseAdapter(){
			
                public void mouseClicked(MouseEvent e){
			
                    activateCarousel(localCount);
				
                }
			
            }
		
        );
		
        count++;
        super.add(panel);
	
    }
	
    private void activateCarousel(int c){
            
	if(isRunning()==false){
                    
            isAnimationRunning=true;
                    
            if(carouselItemCentered<c){
                        
                if(listenerHandler==null)
                    listenerHandler=new SlidingEffectListenerHandler(c,SlidingEffectListenerHandler.SLIDE_LEFT);
                else
                    listenerHandler.setPosSlideDir(c,SlidingEffectListenerHandler.SLIDE_LEFT);
                
            }
                    
            else if(carouselItemCentered>c){
                
                if(listenerHandler==null)
                    listenerHandler=new SlidingEffectListenerHandler(c,SlidingEffectListenerHandler.SLIDE_RIGHT);
                else
                    listenerHandler.setPosSlideDir(c,SlidingEffectListenerHandler.SLIDE_RIGHT);
    
            }
            
            if(slidingEffectTimer==null)
                slidingEffectTimer=new javax.swing.Timer(10,listenerHandler);	
                
            slidingEffectTimer.start();
            
        }
		
        else{
                
            isAnimationRunning=false;
		
        }
	
    }
	
    @Override
    public void setOpaque(boolean val){
	
        super.setOpaque(false);
	
    }
	
    public boolean isRunning(){
		
        return isAnimationRunning;
	
    }
	
    private class SlidingEffectListenerHandler implements ActionListener{
		
        private int c=0;
        public final static int SLIDE_LEFT=0,SLIDE_RIGHT=1;
        private int slideDirection=0;
        private int rateChange=0;
		
        public SlidingEffectListenerHandler(int c,int slideDirection){
		
            this.c=c;
            this.slideDirection=slideDirection;
            this.rateChange=(int)Carousel.this.getWidth()/2;
		
        }
		
        public void actionPerformed(ActionEvent e){
                    
            switch(this.slideDirection){
		
                case SLIDE_LEFT:
                            
                    if(carouselItems.get(c).panel.getBounds().x>=Carousel.this.getWidth()){
                                
                        for(int i=0;i<carouselItems.size();i++){
                                
                            carouselItems.get(i).panel.setLocation(carouselItems.get(i).panel.getBounds().x-this.rateChange
                            ,carouselItems.get(i).panel.getBounds().y);
				
                        }
                            
                    }
                            
                    else if(((int)((Carousel.this.getWidth()-(Carousel.this.getWidth()-carouselItems.get(c).panel.getBounds().x))*(0.20)))!=0){
                            
                        this.rateChange=((int)((Carousel.this.getWidth()
                        -(Carousel.this.getWidth()-carouselItems.get(c).panel.getBounds().x))*(0.20)));
                                
                        for(int i=0;i<carouselItems.size();i++){
                                
                            carouselItems.get(i).panel.setLocation(carouselItems.get(i).panel.getBounds().x
                            -this.rateChange,carouselItems.get(i).panel.getBounds().y);
                               
                        }
                                
                    }
                            
                    else{
                            
                        this.rateChange=Carousel.this.getWidth()-(Carousel.this.getWidth()-carouselItems.get(c).panel.getBounds().x);
                                
                        for(int i=0;i<carouselItems.size();i++){
                                
                            carouselItems.get(i).panel.setLocation((carouselItems.get(i).panel.getBounds().x-this.rateChange)
                            ,carouselItems.get(i).panel.getBounds().y);
                                    
                        }
                                
                        isAnimationRunning=false;
                        this.rateChange=(int)Carousel.this.getWidth()/2;
                        slidingEffectTimer.stop();
                            
                    }
                            
                    carouselItemCentered=this.c;
			
                break;
			
                case SLIDE_RIGHT:
                            
                    if(0>=(carouselItems.get(c).panel.getBounds().x+Carousel.this.getWidth())){
                            
                        for(int i=carouselItems.size()-1;i>=0;i--){
                                
                            carouselItems.get(i).panel.setLocation(carouselItems.get(i).panel.getBounds().x+this.rateChange
                            ,carouselItems.get(i).panel.getBounds().y);
				
                        }
                            
                    }
                            
                    else if(((int)((Carousel.this.getWidth()-(carouselItems.get(c).panel.getBounds().x+Carousel.this.getWidth()))*(0.20)))!=0){
                            
                        this.rateChange=((int)((Carousel.this.getWidth()
                        -(carouselItems.get(c).panel.getBounds().x+Carousel.this.getWidth()))*(0.20)));
                                
                        for(int i=carouselItems.size()-1;i>=0;i--){
                                
                            carouselItems.get(i).panel.setLocation(carouselItems.get(i).panel.getBounds().x
                            +this.rateChange,carouselItems.get(i).panel.getBounds().y);
                                
                        }
                                
                    }
                            
                    else{
                            
                        this.rateChange=Carousel.this.getWidth()-(carouselItems.get(c).panel.getBounds().x+Carousel.this.getWidth());
                                
                        for(int i=carouselItems.size()-1;i>=0;i--){
                                
                            carouselItems.get(i).panel.setLocation(carouselItems.get(i).panel.getBounds().x
                            +this.rateChange,carouselItems.get(i).panel.getBounds().y);
                                
                        }
                                
                        isAnimationRunning=false;
                        this.rateChange=(int)Carousel.this.getWidth()/2;
                        slidingEffectTimer.stop();
                            
                    }
                            
                    carouselItemCentered=this.c;
                        
                break;
                    
            }	
		
        }
        
        public void setPosSlideDir(int c,int slideDirection){
            
            this.c=c;
            this.slideDirection=slideDirection;
            
        }
	
    }
	
}