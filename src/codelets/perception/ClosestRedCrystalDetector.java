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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import memory.CreatureInnerSense;
import ws3dproxy.model.Thing;

/**
 *
 * @author barbara
 */

public class ClosestRedCrystalDetector extends Codelet {

        private boolean printMap=false;
        private MemoryObject visionMO;
        private MemoryObject fearMO;
        private MemoryObject innerSenseMO;
        CreatureInnerSense cis;

	public ClosestRedCrystalDetector(){		
	}
	@Override
	public void accessMemoryObjects() {
                synchronized(this) {
		this.visionMO=this.getInput("VISION");
                }
                this.innerSenseMO=this.getInput("INNER");
                cis = (CreatureInnerSense) innerSenseMO.getI();
                this.fearMO=this.getOutput("FEAR");
	}
	@Override
	public void proc() {
            Thing closest_rcrystal = null;
            double selfX;
	    double selfY;
            double rcrystalX;
	    double rcrystalY;
            CopyOnWriteArrayList<Thing> vision;
            double fear = 0;
            selfX = cis.position.getX();
            selfY = cis.position.getY();
            synchronized (visionMO) {
            vision = new CopyOnWriteArrayList((List<Thing>) visionMO.getI());    

            synchronized(vision) {
                for (Thing t : vision) {
                    String colorName = t.getAttributes().getColor();
                    if (t.getName().equals("Jewel") && colorName.equals("Red")) {
                        rcrystalX=t.getX1();
                        rcrystalY=t.getY1();
                        double Dist=Math.sqrt(Math.pow(rcrystalX-selfX, 2)+Math.pow(rcrystalY-selfY, 2));
                        fear = 1 - Dist;
                        synchronized(fearMO){
                            fearMO.setI(fear);                     
                        }
                    }                           
                }
            }
            }
	}// end proc     
        @Override
        public void calculateActivation() {
        }
}
