package jbl.javaDev.swingSupport;

import javax.swing.*;

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
public class CarouselItems {
    
	public JComponent clickableEntities;
	public JPanel panel;
	
        /**
         * CarouselItems constructor to encapsulate clickableEntities and panel for binding
         * @param clickableEntities to bind with panel
         * @param panel to bind with clickableEntities
         */
	public CarouselItems(JComponent clickableEntities,JPanel panel){
            
		this.clickableEntities=clickableEntities;
		this.panel=panel;
	
        }
	
}
