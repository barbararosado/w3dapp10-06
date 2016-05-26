/*
 * Copyright (C) 2015 barbara.
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
package codelets.perception;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import ws3dproxy.model.Creature;
import memory.CreatureInnerSense;


/**
 *Detect low energy.
 *      This class detects if the creature is low fuel.
 * 
 * @author barbara
 */

public class LowEnergyDetector extends Codelet {      
    
        private MemoryObject innerSenseMO;
        private MemoryObject hungerMO;
        private Creature c;
        CreatureInnerSense cis;
        //private Double hunger;
    
        public LowEnergyDetector (Creature cc){
            c = cc;
        }
        
        @Override
        public void accessMemoryObjects() {
            innerSenseMO=getInput("INNER");
            hungerMO = this.getOutput("HUNGER");
        }
        
        @Override
        public void proc(){
            double hunger = 0;
            double fuel;
            
            cis = (CreatureInnerSense) innerSenseMO.getI();
            cis.fuel = c.getFuel();
            fuel = cis.fuel;
            hunger = 1 - (fuel/1000.0);          
            
            
            synchronized(hungerMO){         
                hungerMO.setI(new Double(hunger));
            }
        
        } //end proc
        @Override
        public void calculateActivation() {
        }        
}
