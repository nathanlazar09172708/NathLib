/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbl.javaDev.swingSupport;

/********************************************************************
 *  UndecoratedWindowException.java 
 *  Exception for non undecorated window
 * 
 *  Jonathan B. Lazar
 *  jonathanlazar17@gmail.com
 *  Started: December 25 2019
 *
 *  Copyright (c) 2019 [Jonathan B. Lazar]
 *
 *******************************************************************/
public class UndecoratedWindowException extends Exception{
    
    /**
     * UndecoratedWindowException constructor to handle the exception when window is not undecorated
     * @param message 
     */
    public UndecoratedWindowException(String message){
        
        super(message);
        
    }
    
}
