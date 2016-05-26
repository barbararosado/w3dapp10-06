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


import org.json.JSONException;
import org.json.JSONObject;

import br.unicamp.cst.core.entities.MemoryObject;
import java.util.Collections;
import memory.CreatureInnerSense;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import ws3dproxy.model.Thing;

/**
 *
 * @author barbara
 */
public class ClosestGreenCrystalDetector extends Codelet {
        private MemoryObject knownGreenCrystalsMO;
	private MemoryObject closestGreenCrystalMO;
	private MemoryObject innerSenseMO;
	
        private List<Thing> known;

	public ClosestGreenCrystalDetector() {

	}


	@Override
	public void accessMemoryObjects() {
		this.knownGreenCrystalsMO=this.getInput("KNOWN_GCRYSTALS");
		this.innerSenseMO=this.getInput("INNER");
		this.closestGreenCrystalMO=this.getOutput("CLOSEST_GCRYSTAL");	
	}
	@Override
	public void proc() {
                Thing closest_gcrystal=null;
                double selfX;
	        double selfY;
	        String objectName;
	        double closestGCrystalX=0;
	        double closestGCrystalY=0;
	        String closestGCrystalName;
	        double gcrystalX;
	        double gcrystalY;
                //System.out.println("Processing ClosestAppleDetector Codelet");
            
                known = Collections.synchronizedList((List<Thing>) knownGreenCrystalsMO.getI());
                //System.out.println("known: "+known);
                //System.out.println(vision.toString());
                CreatureInnerSense cis = (CreatureInnerSense) innerSenseMO.getI();
                //System.out.println(closestAppleMO);
                //System.out.println("closestAppleMO proc");
                synchronized(known) {
		if(known.size() != 0){
			//Extracting self position
			selfX = cis.position.getX();
			selfY = cis.position.getY();
                        //System.out.println("self: "+selfX+","+selfY);
			//Point self = new Point();

			//Iterate over objects in vision, looking for the closest apple
			closestGCrystalName=null;
                        CopyOnWriteArrayList<Thing> myknown = new CopyOnWriteArrayList<>(known);
                         for (Thing t : myknown) {
				objectName=t.getName();
                                String colorName = t.getAttributes().getColor();
				if(objectName.contains("Jewel") && colorName.equals("Green")){ 
					gcrystalX=t.getX1();
					gcrystalY=t.getY1();
					if(closestGCrystalName==null){
                                                closest_gcrystal = t;
						closestGCrystalName=objectName;
						closestGCrystalX=gcrystalX;
						closestGCrystalY=gcrystalY;
					}else{
						double Dnew=Math.sqrt(Math.pow(gcrystalX-selfX, 2)+Math.pow(gcrystalY-selfY, 2));
						double Dclosest=Math.sqrt(Math.pow(closestGCrystalX-selfX, 2)+Math.pow(closestGCrystalY-selfY, 2));
						if(Dnew<Dclosest){
							closestGCrystalName=objectName;
							closestGCrystalX=gcrystalX;
							closestGCrystalY=gcrystalY;
                                                        closest_gcrystal = t;
						}
					}
				}
			 }
                        
                        //System.out.println("Achou: "+closestAppleName+","+closestAppleX+","+closestAppleY);
			if(closestGCrystalName!=null){
				JSONObject jsonInfo=new JSONObject();	
				try {
					jsonInfo.put("NAME", closestGCrystalName);
					jsonInfo.put("X", closestGCrystalX);
					jsonInfo.put("Y", closestGCrystalY);
					if(!closestGreenCrystalMO.getI().equals(jsonInfo.toString())){
						closestGreenCrystalMO.updateI(jsonInfo.toString());
                                                closestGreenCrystalMO.setI(closest_gcrystal);
						//System.out.println("Achou: "+closestAppleMO.getInfo());
					}
                                        //else System.out.println("closestAppleMO: "+closestAppleX+","+closestAppleY+" self:"+selfX+","+selfY);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else{
				closestGreenCrystalMO.updateI(""); //couldn't find any nearby apples
                                closest_gcrystal = null;
                                closestGreenCrystalMO.setI(closest_gcrystal);
                                //System.out.println("Naoachou: "+ known);
			}

//						System.out.println(closestAppleMO.getInfo());
                
		}else{
			closestGreenCrystalMO.updateI("");
                        closest_gcrystal = null;
                        closestGreenCrystalMO.setI(closest_gcrystal);
                        //System.out.println("Naoachou2: "+closestAppleMO.getInfo()+" known:"+known);
		}
                }
//		System.out.println("closestAppleMO: "+closestAppleMO);
                //System.out.println("Closest Apple: "+closest_apple+" "+closestAppleMO.getInfo());
	}//end proc

@Override
        public void calculateActivation() {
        
        }

}
