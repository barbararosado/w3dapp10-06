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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import memory.CreatureInnerSense;
import org.json.JSONException;
import org.json.JSONObject;
import ws3dproxy.model.Bag;
import ws3dproxy.model.Creature;
import ws3dproxy.model.Leaflet;
import ws3dproxy.model.Thing;

/**
 *
 * @author barbara
 */
public class DecideCrystalToGet extends Codelet {
        private MemoryObject crystalToGetMO;
	private MemoryObject knownCrystalsMO;
        private MemoryObject bagMO;
        private MemoryObject innerSenseMO;
        private Creature c;

        CreatureInnerSense cis;
        List<Thing> known;
    
    public DecideCrystalToGet(Creature cc){
        c=cc;
    }
    
    @Override
    public void accessMemoryObjects(){
        crystalToGetMO=this.getOutput("CRYSTAL");
        knownCrystalsMO=this.getInput("KNOWN_CRYSTALS");
        bagMO=this.getInput("BAG");
        innerSenseMO=this.getInput("INNER");
    }
	
    @Override
    public void proc(){
        Bag bag = c.getBag();
        Thing crystalToGet = null;
        known = Collections.synchronizedList((List<Thing>) knownCrystalsMO.getI());
        String crystalToGetName = null;
        //double crystalToGetX=0;
	//double crystalToGetY=0;
        double utilidade=0;
        
        CreatureInnerSense cis = (CreatureInnerSense) innerSenseMO.getI();
        
         synchronized(known) {
		if(known.size() != 0){

			CopyOnWriteArrayList<Thing> myknown = new CopyOnWriteArrayList<>(known);

                        for (Thing t : myknown) {
                            if(t.getName().contains("Jewel")){ 
                                if(calcutil(t,bag,c,cis)>utilidade){
                                    crystalToGet = t;
                                    crystalToGetName=t.getName();
                                    String colorName = t.getAttributes().getColor();
                                }		
                            }
                        }

			if(crystalToGetName!=null){
//				JSONObject jsonInfo=new JSONObject();	
//				try {
//					jsonInfo.put("NAME", crystalToGetName);
//					jsonInfo.put("X", crystalToGet.getX1());
//					jsonInfo.put("Y", crystalToGet.getY1());
//					if(!crystalToGetMO.getI().equals(jsonInfo.toString())){
//						//crystalToGetMO.updateI(jsonInfo.toString());
//                                                
//						
//					}
//                                      
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
                                crystalToGetMO.setI(crystalToGet);
			}else{
				//crystalToGetMO.updateI(""); //couldn't find any crystal
                                crystalToGet = null;
                                crystalToGetMO.setI(crystalToGet);
                             
			}
						
                
		}else{
			//crystalToGetMO.updateI("");
                        crystalToGet = null;
                        crystalToGetMO.setI(crystalToGet);
                        
		}
                }
//		
	}//end proc
         
         
    @Override
    public void calculateActivation(){

    }
    

    double calcutil(Thing t, Bag bag, Creature c, CreatureInnerSense cis){
        double utilidade;
        double crystalX = t.getX1();
        double crystalY = t.getY1();
        double selfX = cis.position.getX();
	double selfY = cis.position.getY();
        int n=-1;
        double P = 0;
        double p;
        
        double D = Math.sqrt(Math.pow(crystalX-selfX, 2)+Math.pow(crystalY-selfY, 2));
        String colorName = t.getAttributes().getColor();
        
        for(Leaflet l : c.getLeaflets()){
            if(l.ifInLeaflet(colorName)){
            n = l.getMissingNumberOfType(colorName);
            //System.out.println(""+n);
                if(n != -1){
                    p = l.getPayment()/n;
                    if(p>P){
                        P=p;
                    }
                   
                }
            }
        }
        utilidade = P/D;
        
        return utilidade;
    }
     
}
