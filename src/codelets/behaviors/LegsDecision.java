/*
 * Copyright (C) 2016 barbara.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package codelets.behaviors;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;

/**
 *
 * @author barbara
 */
public class LegsDecision extends Codelet {
    private MemoryObject legsMO5;
    private MemoryObject legsMO4;
    private MemoryObject legsMO3;
    private MemoryObject legsMO2;
    private MemoryObject legsMO1;
    private MemoryObject legsMO;
    
    public LegsDecision(){}
    
    @Override
    public void accessMemoryObjects() {
        legsMO1=this.getInput("LEGS1");
        legsMO2=this.getInput("LEGS2");
        legsMO3=this.getInput("LEGS3");
        legsMO4=this.getInput("LEGS4");
        legsMO5=this.getInput("LEGS5");
        legsMO=this.getOutput("LEGS");
    }
        
    @Override
    public void proc() {
        
        Double maior = legsMO1.getEvaluation();;
        int i = 1;
        
        if(maior<legsMO2.getEvaluation()){
            maior = legsMO2.getEvaluation();
            i = 2;
        }
        if(maior<legsMO3.getEvaluation()){
            maior = legsMO3.getEvaluation();
            i = 3;
        }
        if(maior<legsMO4.getEvaluation()){
            maior = legsMO4.getEvaluation();      
            i = 4;
        }
        if(maior<legsMO5.getEvaluation()){
            maior = legsMO5.getEvaluation();
            i = 5;
        }
        
        if(i==1){
            legsMO.setI(legsMO1.getI());
        }
        else if(i==2){
            legsMO.setI(legsMO2.getI());
        }
        else if(i==3){
            legsMO.setI(legsMO3.getI());
        }
        else if(i==4){
            legsMO.setI(legsMO4.getI());
        }
        else if(i==5){
            legsMO.setI(legsMO5.getI());
        }
//        System.out.println("Decision 1: "+legsMO1.getEvaluation());
//        System.out.println("Decision 2: "+legsMO2.getEvaluation());
//        System.out.println("Decision 3: "+legsMO3.getEvaluation());
//        System.out.println("Decision 4: "+legsMO4.getEvaluation());
//        System.out.println("Decision 5: "+legsMO5.getEvaluation());
//        System.out.println("chosen: "+i+" value: "+maior);
        
               
        
    }
        
    @Override
    public void calculateActivation() {
        
    }
    
}
