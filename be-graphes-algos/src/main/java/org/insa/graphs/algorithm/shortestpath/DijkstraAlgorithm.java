package org.insa.graphs.algorithm.shortestpath;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
		Label currentLabel;
		Graph graph = data.getGraph();
        List<Node> nodes = graph.getNodes();
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        ArrayList<Label> labels = new ArrayList<Label>();
        boolean notfini = true;
		
		for (Node node : nodes) {
        	labels.add(new Label(node, null, Double.MAX_VALUE));
        }
        
        Node origin = LabelOrigin(data).getCurrentNode();
        notifyOriginProcessed(origin);
        labels.get(origin.getId()).setCost(0);
        tas.insert(labels.get(origin.getId()));
				
        while (tas.isEmpty() != true && notfini) 
        {
        	currentLabel = tas.findMin();
        	tas.remove(currentLabel);
        	Node currentNode = currentLabel.getCurrentNode();
        	if (currentNode == data.getDestination()){
                notifyDestinationReached(data.getDestination());
        		notfini = false;
        	}
        	labels.get(currentNode.getId()).setMark(true);
        	for (Arc newArc : currentNode.getSuccessors()){
        		if (data.isAllowed(newArc)){
        			Label destLabel = labels.get(newArc.getDestination().getId());                    
                    if (destLabel == null)
        			{                        
                        destLabel = this.CreateLabel(data, newArc, currentLabel);
                        labels.set(destLabel.getCurrentNode().getId(),destLabel);
                        tas.insert(destLabel);
                        Node destNode = destLabel.getCurrentNode();
                        notifyNodeReached(destNode);
                    }
        			if (!destLabel.getMark())
        			{        				
                        Node destNode = destLabel.getCurrentNode();
        				notifyNodeReached(destNode);                                          
        				Double currentCost = currentLabel.getCost();
        				Double destCost = destLabel.getCost();
        				Double arcCost = data.getCost(newArc);
        				if (destCost > currentCost + arcCost) {        					
        					if (destCost != Double.MAX_VALUE) {
        						tas.remove(destLabel);
        					}
        					destLabel.setCost(currentCost+arcCost);
        					destLabel.setFatherArc(newArc);
        					tas.insert(destLabel);
        					labels.set(destNode.getId(),destLabel);        					
        				}
        			}
        		}
        	}
        	notifyNodeMarked(currentNode);
        }
        
        Arc currentArc=labels.get(data.getDestination().getId()).getFatherArc();
        if (currentArc == null) {
            solution = new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE);
        }
        else {
            notifyDestinationReached(data.getDestination());            
            ArrayList<Arc> arcs = new ArrayList<>();
            while (currentArc != null) {
                arcs.add(currentArc);
                Arc newArc=labels.get(currentArc.getOrigin().getId()).getFatherArc();
                currentArc = newArc;
            }
            Collections.reverse(arcs);
            solution = new ShortestPathSolution(data, AbstractSolution.Status.OPTIMAL, new Path(graph, arcs));
        }
		
        return solution;
    }

    protected Label LabelOrigin(ShortestPathData data){
        return new Label(data.getOrigin(),null,Double.MAX_VALUE);
    }
    
    protected Label CreateLabel(ShortestPathData data, Arc fatherArc, Label lastLabel){  
        return new Label(data.getDestination(),fatherArc,lastLabel.getCost() + data.getCost(fatherArc));
    }
    
}