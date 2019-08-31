/********************************************************************
 *  CarouselItems.java 
 *  A sliding items
 * 
 *  Jonathan B. Lazar
 *  jonathanlazar17@gmail.com
 *  Started: December 12 2016
 *  
 *  
 * 
 * Copyright (c) 2016 [Jonathan B. Lazar]
 *
 *******************************************************************/




package jbl.javaDev.swingSupport;

import javax.swing.*;

public class CarouselItems {
    
	public JComponent clickableEntities=null;
	public JPanel panel=null;
	
	public CarouselItems(JComponent clickableEntities,JPanel panel){
            
		this.clickableEntities=clickableEntities;
		this.panel=panel;
	
        }
	
}
