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
import ws3dproxy.model.Thing;

/**
 *
 * @author barbara
 */
public class GoToCrystal extends Codelet {
        private MemoryObject crystalToGetMO;
	private MemoryObject selfInfoMO;
        private MemoryObject greedMO;
        private MemoryObject bagMO;
	private MemoryObject legsMO5;
	private int creatureBasicSpeed;
	private double reachDistance;

	public GoToCrystal(int creatureBasicSpeed, int reachDistance) {
		this.creatureBasicSpeed=creatureBasicSpeed;
		this.reachDistance=reachDistance;
	}

	@Override
	public void accessMemoryObjects() {
		crystalToGetMO=this.getInput("CRYSTAL");
		selfInfoMO=this.getInput("INNER");
                greedMO=this.getInput("GREED");
		legsMO5=this.getOutput("LEGS5");
	}

	@Override
	public void proc() {
		// Find distance between creature and closest green crystal
		//If far, go towards it
		//If close, stops

		//String appleInfo = closestAppleMO.getInfo();
                Thing crystal = (Thing) crystalToGetMO.getI();
		//String selfInfo= (String) selfInfoMO.getI();
                CreatureInnerSense cis = (CreatureInnerSense) selfInfoMO.getI();
                //System.out.println("GoToClosestApple: "+appleInfo+" "+selfInfo);
                //legsMO5.setEvaluation(0.0);

		if(crystal != null)
		{
			JSONObject jsonGCrystalInfo=null;
			String crystalName="";
			double crystalX=0;
			double crystalY=0;
			try {
				//jsonAppleInfo = new JSONObject(appleInfo);
				//appleName=jsonAppleInfo.getString("NAME");
				//appleX=jsonAppleInfo.getDouble("X");
				//appleY=jsonAppleInfo.getDouble("Y");
                                crystalName = crystal.getName();
                                crystalX = crystal.getX1();
                                crystalY = crystal.getY1();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//String[] selfInfoArray=selfInfo.split(" ");

			//String selfName=selfInfoArray[0];
			double selfX=cis.position.getX();
			double selfY=cis.position.getY();

			Point2D pCrystal = new Point();
			pCrystal.setLocation(crystalX, crystalY);

			Point2D pSelf = new Point();
			pSelf.setLocation(selfX, selfY);

			double distance = pSelf.distance(pCrystal);
			JSONObject message=new JSONObject();
			try {
				if(distance>reachDistance){ //Go to it
                                        message.put("ACTION", "GOTO");
					message.put("X", (int)crystalX);
					message.put("Y", (int)crystalY);
                                        message.put("SPEED", creatureBasicSpeed);	

				}else{//Stop
					message.put("ACTION", "GOTO");
					message.put("X", (int)crystalX);
					message.put("Y", (int)crystalY);
                                        message.put("SPEED", 0.0);	
				}
				legsMO5.updateI(message.toString());
                                legsMO5.setEvaluation((Double)greedMO.getI());
//				System.out.println(message);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
                        legsMO5.setEvaluation(0.0);
//			
			
		}
         //System.out.println("Command: "+legsMO.getInfo());

	}//end proc
        
        @Override
        public void calculateActivation() {
        
        }
}
