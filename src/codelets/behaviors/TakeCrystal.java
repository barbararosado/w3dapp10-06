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
package codelets.behaviors;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import memory.CreatureInnerSense;
import org.json.JSONException;
import org.json.JSONObject;
import ws3dproxy.model.Thing;

/**
 *
 * @author barbara
 */
public class TakeCrystal extends Codelet {
        private MemoryObject crystalToGetMO;
	private MemoryObject innerSenseMO;
        private MemoryObject knownCrystalsMO;
	private int reachDistance;
	private MemoryObject handsMO;
        Thing crystal;
        CreatureInnerSense cis;
        List<Thing> known;

	public TakeCrystal(int reachDistance) {
		this.reachDistance=reachDistance;
	}

	@Override
	public void accessMemoryObjects() {
		crystalToGetMO=this.getInput("CRYSTAL");
		innerSenseMO=this.getInput("INNER");
		handsMO=this.getOutput("HANDS");
                knownCrystalsMO = this.getOutput("KNOWN_CRYSTALS");
	}

	@Override
	public void proc() {
                String crystalName="";
                crystal = (Thing) crystalToGetMO.getI();
                cis = (CreatureInnerSense) innerSenseMO.getI();
                known = (List<Thing>) knownCrystalsMO.getI();
		//Find distance between closest green crystal and self
		//If closer than reachDistance, eat the apple
		
		if(crystal != null)
		{
			double crystalX=0;
			double crystalY=0;
			try {
				crystalX=crystal.getX1();
				crystalY=crystal.getY1();
                                crystalName = crystal.getName();
                                

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double selfX=cis.position.getX();
			double selfY=cis.position.getY();

			Point2D pCrystal = new Point();
			pCrystal.setLocation(crystalX, crystalY);

			Point2D pSelf = new Point();
			pSelf.setLocation(selfX, selfY);

			double distance = pSelf.distance(pCrystal);
			JSONObject message=new JSONObject();
			try {
				if(distance<reachDistance){ //take it						
					message.put("OBJECT", crystalName);
					message.put("ACTION", "PICKUP");
					handsMO.updateI(message.toString());
                                        DestroyCrystal();
				}else{
					handsMO.updateI("");	//nothing
				}
				
//				System.out.println(message);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			handsMO.updateI("");	//nothing
		}
	}
        
        @Override
        public void calculateActivation() {
        
        }
        
        public void DestroyCrystal() {
           int r = -1;
           int i = 0;
           synchronized(known) {
             CopyOnWriteArrayList<Thing> myknown = new CopyOnWriteArrayList<>(known);  
             for (Thing t : known) {
              if (crystal != null) 
                 if (t.getName().equals(crystal.getName())) r = i;
              i++;
             }   
             if (r != -1) known.remove(r);
             crystal = null;
           }
        }
}
