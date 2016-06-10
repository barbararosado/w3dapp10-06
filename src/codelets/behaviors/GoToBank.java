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
import memory.CreatureInnerSense;
import org.json.JSONException;
import org.json.JSONObject;
import ws3dproxy.model.Creature;
import ws3dproxy.model.Leaflet;

/**
 *
 * @author barbara
 */

/*deve ver qual os cristais ele possui a fim de saber qual leaflet pode ser cumprido
            não considerar os leaflets que possuem cristais vermelhos
            
*/
public class GoToBank extends Codelet {
        private MemoryObject bagMO;
	private MemoryObject selfInfoMO;
	private MemoryObject legsMO1;
        private MemoryObject ambitionMO;
        private MemoryObject greedMO;
        private MemoryObject innerSenseMO;
	private int creatureBasicSpeed;
	private double reachDistance;
        private Creature c;
        private Double ambition;
        private Double greed;

	public GoToBank(int creatureBasicSpeed, int reachDistance, Creature cc) {
		this.creatureBasicSpeed=creatureBasicSpeed;
		this.reachDistance=reachDistance;
                c=cc;
	}

	@Override
	public void accessMemoryObjects() {
		bagMO=this.getInput("BAG");
		selfInfoMO=this.getInput("INNER");
		legsMO1=this.getOutput("LEGS1");
                ambitionMO=this.getOutput("AMBITION");
                greedMO=this.getOutput("GREED");
	}

	@Override
	public void proc() {
		// Find distance between creature and bank and go there
	
                //String selfInfo= (String) selfInfoMO.getI();
                CreatureInnerSense cis = (CreatureInnerSense) selfInfoMO.getI();
                Leaflet t = null;
                double complete = 0;
                
                                
                for(Leaflet l : c.getLeaflets()){
                    if((l.getSituation() == 1)){ //if this leaflet is complete and it can provide more points
                        complete ++;
                        if(l.getPayment() > t.getPayment()){
                            t = l; 
                        }
                    }
                }  
                
                ambition = complete/4;
                greed = 0.5 - ambition;        
                
                synchronized(ambitionMO){         
                    ambitionMO.setI(ambition);
                }       
                
                synchronized(greedMO){         
                    greedMO.setI(greed);
                }
                
                
                if(t != null && t.getSituation() == 1){ //if this leaflet is complete

                    double bankX=0;
                    double bankY=0;

                    //String[] selfInfoArray=selfInfo.split(" ");

                    //String selfName=selfInfoArray[0];
                    double selfX=cis.position.getX();
                    double selfY=cis.position.getY();


                    Point2D pBank = new Point();
                    pBank.setLocation(bankX, bankY);

                    Point2D pSelf = new Point();
                    pSelf.setLocation(selfX, selfY);

                    double distance = pSelf.distance(pBank);
                    JSONObject message=new JSONObject();
                    try {

                            if((distance>reachDistance)){ //Go to it
                                    message.put("ACTION", "GOTO");
                                    message.put("X", (int)bankX);
                                    message.put("Y", (int)bankY);
                                    message.put("SPEED", creatureBasicSpeed);	

                            }else{//Stop
                                            message.put("ACTION", "GOTO");
                                            message.put("X", (int)bankX);
                                            message.put("Y", (int)bankY);
                                            message.put("SPEED", 0.0);	
                            }
                            //legsMO1.updateI(message.toString());
                            legsMO1.setI(message.toString());
                            legsMO1.setEvaluation((Double)ambitionMO.getI());
    //			System.out.println(message);
                            } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                            }
                    } else {
                        legsMO1.setEvaluation(0.0);
                    }

	}//end proc
        
        @Override
        public void calculateActivation() {
        
        }
    
}
