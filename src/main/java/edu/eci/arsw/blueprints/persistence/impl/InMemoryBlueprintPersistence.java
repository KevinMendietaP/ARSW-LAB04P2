/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints = new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115), new Point(115, 115)};
        Blueprint bp=new Blueprint("juan", "INC",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        bp=new Blueprint("juan", "SAS",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        bp=new Blueprint("juan", "ARS",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        bp=new Blueprint("carl", "SOS",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }
    
    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        Set<Blueprint> result = new LinkedHashSet<>();
        for(Tuple<String, String> tuple : blueprints.keySet()){
            if (tuple.getElem1().equals(author)) result.add(blueprints.get(tuple));
        }
        return result;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        HashSet<Blueprint> result = new HashSet<>();
        for(Blueprint values : blueprints.values()){
            result.add(values);
        }
        return result;
    }

}