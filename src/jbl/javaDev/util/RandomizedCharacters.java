/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbl.javaDev.util;

/********************************************************************
 *  RandomizedCharacters.java
 *  A tool to generate randomized characters
 * 
 *  Jonathan B. Lazar
 *  jonathanlazar17@gmail.com
 *  Started: August 07 2018
 *  
 *
 * Copyright (c) 2018 [Jonathan B. Lazar]
 *
 *******************************************************************/

import java.util.Random;

public class RandomizedCharacters {
    
    private int length;
    private String randomizedCharacters="";
    private String characters="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    public RandomizedCharacters(int length){
        this.length=length;
        this.randomized();
    }
    
    private void randomized(){
        Random rand=new Random();
        for(int i=0;i<this.length;i++){
            randomizedCharacters+=characters.charAt(rand.nextInt(characters.length()));
        }
    }
    
    public String getRandomizedCharacters(){
        return this.randomizedCharacters;
    }
    
}
