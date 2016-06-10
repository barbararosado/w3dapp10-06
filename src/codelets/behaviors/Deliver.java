/*
 * Copyright (C) 2016 ra145393.
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
import java.util.logging.Level;
import java.util.logging.Logger;
import memory.CreatureInnerSense;
import ws3dproxy.CommandExecException;
import ws3dproxy.model.Creature;
import ws3dproxy.model.Leaflet;

/**
 *
 * @author ra145393
 */
public class Deliver extends Codelet {
    
    private MemoryObject leafletToDeliverMO;
    private MemoryObject innerSenseMO;
    private int reachDistance;
    private Creature c;
    CreatureInnerSense cis;
    Leaflet l;
    
    public Deliver(Creature cc, int reachDistance){
        this.reachDistance=reachDistance;
        c=cc;
    }
    
    @Override
    public void accessMemoryObjects(){
        leafletToDeliverMO=this.getInput("LEAFLET");
        innerSenseMO=this.getInput("INNER");
    }
    
    @Override
    public void proc(){

        l = (Leaflet) leafletToDeliverMO.getI();
        if (l != null) {
            Long ID = l.getID();
            String id = String.valueOf(ID);
            
            //testar se está próximo e se ele deve realmente ir trocar os pontos
            double selfX=cis.position.getX();
            double selfY=cis.position.getY();

            double D = Math.sqrt(Math.pow(selfX, 2)+Math.pow(selfY, 2));
//            Point2D pBank = new Point();
//            pBank.setLocation(0, 0);
//
//            Point2D pSelf = new Point();
//            pSelf.setLocation(selfX, selfY);
//
//            double distance = pSelf.distance(pBank);
            
            if(D<reachDistance){ 
                try {
                //deliver this leaflet
                c.deliverLeaflet(id);
                } catch (CommandExecException ex) {
                    Logger.getLogger(Deliver.class.getName()).log(Level.SEVERE, null, ex);
                }
	    }
   
        }  
        
    }
    
    @Override
    public void calculateActivation() {
        
    }
    
}
