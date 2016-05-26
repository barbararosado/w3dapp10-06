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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import ws3dproxy.model.Thing;

/**
 *Detect crystals in the vision field.
 * 	This class detects a number of things related to crystal, such as if there are any within reach,
 * any on sight, if they are rotten, and so on.
 * 
 * @author barbara
 */
public class CrystalDetector extends Codelet {
    //debug variables
	private boolean printMap=false;
	//private RawMemory rm=RawMemory.getInstance();

//	private double minDist=50; //minimum distance to be considered within reach
//	private double xs=-100,ys=-100;
//	private double[] closest_apple={-1.0,-1.0,-1.0}; //{[x,y,d}
//	private boolean closest_apple_is_rotten=false;
//	private boolean apple_at_reach=false;
//	private boolean knows_apple=false;
        private MemoryObject visionMO;
        private MemoryObject knownCrystalsMO;
        

	public CrystalDetector(){
		
	}

	@Override
	public void accessMemoryObjects() {
                synchronized(this) {
		this.visionMO=this.getInput("VISION");
                }
		this.knownCrystalsMO=this.getOutput("KNOWN_CRYSTALS");
	}

	@Override
	public void proc() {
            CopyOnWriteArrayList<Thing> vision;
            List<Thing> known;
            synchronized (visionMO) {
            //vision = Collections.synchronizedList((List<Thing>) visionMO.getI());
            vision = new CopyOnWriteArrayList((List<Thing>) visionMO.getI());    
            known = Collections.synchronizedList((List<Thing>) knownCrystalsMO.getI());
            //known = new CopyOnWriteArrayList((List<Thing>) knownApplesMO.getI());    
            synchronized(vision) {
            for (Thing t : vision) {
               boolean found = false;
               synchronized(known) {
                  CopyOnWriteArrayList<Thing> myknown = new CopyOnWriteArrayList<>(known);
                  for (Thing e : myknown)
                    if (t.getName().equals(e.getName())) {
                      found = true;
                      break;
                    }
                  //String colorName = t.getAttributes().getColor();
                  if (found == false && t.getName().contains("Jewel") /*&& colorName.equals("Green")*/) known.add(t);/* && crystal is green */ 
               }
               
            }
            }
            }
		
	}// end proc
        
        @Override
        public void calculateActivation() {
        
        }
}
