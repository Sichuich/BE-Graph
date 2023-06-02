package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;

public class LabelStar extends Label {
    private double CostEstime;

    public LabelStar(Node node, Arc fatherArc, double Cost, double CostEstime){
        super(node, fatherArc, Cost);
        this.CostEstime = CostEstime;
    }

    public double getCostEstime(){
        return CostEstime;
    }
    
    @Override
    public double getTotalCost(){
        return this.getCost() + this.getCostEstime();
    }

    @Override
    public int compareTo(Label label) 
     {
         int i = super.compareTo(label);
         LabelStar newone = (LabelStar) label;
         if (i == 0){
             i = Double.compare(this.getCostEstime(), newone.getCostEstime());
         }
         return i;
     }
}
