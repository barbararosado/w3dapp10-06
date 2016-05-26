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
import ws3dproxy.model.Creature;
import ws3dproxy.model.Leaflet;

/**
 *
 * @author ra145393
 */
public class Deliver extends Codelet {
    
    private MemoryObject leafletToDeliverMO;
    private Creature c;
    Leaflet l;
    
    public Deliver(Creature cc){
        c=cc;
    }
    
    @Override
    public void accessMemoryObjects(){
        leafletToDeliverMO=this.getInput("LEAFLET");
    }
    
    @Override
    public void proc(){

        l = (Leaflet) leafletToDeliverMO.getI();
        if (l != null) {
          Long ID = l.getID();
          //convert long to string
        }  
        
        if(l==null){
            
            //testar se está próximo e se ele deve realmente ir trocar os pontos
            
            
            //c.deliverLeaflet(ID);
        
        }
        
    }
    
    @Override
    public void calculateActivation() {
        
    }
    
}
