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
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import ws3dproxy.model.Thing;

/**
 *
 * @author barbara
 */
public class Escape extends Codelet {
        private MemoryObject knownMO;
        private List<Thing> known;
        private MemoryObject legsMO4;


	public Escape(){
		
	}

	@Override
	public void proc() {
            //legsMO4.setEvaluation(0.0);
            known = (List<Thing>) knownMO.getI();
            if (known.size() == 0) {
		JSONObject message=new JSONObject();
			try {
				message.put("ACTION", "ESCAPE");
				legsMO4.updateI(message.toString());
                                legsMO4.setEvaluation(0.0);
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }            
		
	}

	@Override
	public void accessMemoryObjects() {
            knownMO = this.getInput("KNOWN_APPLES");
            legsMO4=this.getOutput("LEGS4");

		// TODO Auto-generated method stub
		
	}
        
        @Override
        public void calculateActivation() {
            
        }
}
