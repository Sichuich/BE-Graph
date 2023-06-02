    package org.insa.graphs.algorithm.shortestpath;
	import org.insa.graphs.model.*;

    public class Label implements Comparable<Label>
	{

    	private Node currentNode;
    	private boolean mark;
    	private double cost;
    	private Arc fatherArc;
    		
    	public Label(Node currentNode, Arc fatherArc, double Cost) 
		{
    		this.currentNode = currentNode;
    		this.fatherArc = fatherArc;
    		this.cost = Cost;
    		this.mark = false;
    	}
    	
    	public double getCost() 
		{
    		return this.cost;
    	}
  
    	public boolean getMark() 
		{
    		return this.mark;
    	}
    	
    	public Arc getFatherArc() 
		{
    		return this.fatherArc;
    	}
    	
    	public Node getCurrentNode() 
		{
    		return this.currentNode;
    	}
    	
    	public void setCost(double cost) 
		{
    		this.cost = cost;
    	}
    	
    	public void setMark(boolean mark) 
		{
    		this.mark = mark;
    	}
    	
    	public void setFatherArc(Arc fatherArc) 
		{
    		this.fatherArc = fatherArc;
    	}
    	
    	public void setCurrentNode(Node currentNode) 
		{
    		this.currentNode = currentNode;
    	}

		public double getTotalCost() 
		{
    		return this.getCost();
    	}
    	
        public int compareTo(Label label) 
        {
			int i = Double.compare(this.getTotalCost(), label.getTotalCost());
            if (i == 0){
				i = Double.compare(label.getCost(), this.getCost());
			}
			return i;
        }

    }