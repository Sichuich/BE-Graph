package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.*;


public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }  

    @Override
    protected Label CreateLabel(ShortestPathData data, Arc fatherArc, Label lastLabel) {
        double CoutEstime = 0;
        if (data.getMode() == Mode.LENGTH){
            CoutEstime = fatherArc.getDestination().getPoint().distanceTo(data.getDestination().getPoint());
        }else if (data.getMode() == Mode.TIME){
            CoutEstime = fatherArc.getDestination().getPoint().distanceTo(data.getDestination().getPoint()) * 3600.0 / (data.getGraph().getGraphInformation().getMaximumSpeed() * 1000.0);
        }
        return new LabelStar(fatherArc.getDestination(), fatherArc, lastLabel.getCost() + data.getCost(fatherArc), CoutEstime);
    }

    @Override
    protected Label LabelOrigin(ShortestPathData data){
        return new LabelStar(data.getOrigin(), null, Double.MAX_VALUE, Point.distance(data.getOrigin().getPoint(),data.getDestination().getPoint()));
    }

}