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
package codelets.sensors;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import memory.CreatureInnerSense;
import ws3dproxy.model.Bag;
import ws3dproxy.model.Creature;

/**
 * Read what the creature has in the bag.
 * 
 * @author barbara
 */
public class BagInspector extends Codelet {
    
        
        private MemoryObject bagMO;
        private Creature c;
        CreatureInnerSense cis;
        private Double bag;
    
        public BagInspector (Creature cc){
           c = cc;            
        }
        
        @Override
        public void accessMemoryObjects() {
            bagMO = this.getOutput("BAG");
            
            //this.cis = (CreatureInnerSense) innerSenseMO.getI();
    }
        
        @Override
        public void proc(){
            
            Bag current = c.getBag();
            bagMO.setI(current);
//            c.getSackContent()
//            apple = c..getNumberPFood();
//            green_crystal= getNumberCrystalPerType(colorGREEN);
//            
            /*deve ver qual os cristais ele possui a fim de saber qual leaflet pode ser cumprido
            não considerar os leaflets que possuem cristais vermelhos
            
            */
                    
//            synchronized(bagMO){         
//                Creature cc = (Creature) bagMO.getI();
//                Bag bg = cc.updateBag();
//                
//                
//            }
        
        } //end proc
        @Override
        public void calculateActivation() {
        
        }
}//end class
