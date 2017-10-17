package main;

import graphs.Graph;
import graphs.GraphVisuals;
import graphs.Pti;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import simpleFrame.Colour;
import simpleFrame.SimpleFrame;
import terms.Abstraction;
import terms.Application;
import terms.Reduction;
import terms.Term;
import terms.Variable;

public class LambdaBuilder implements KeyListener, MouseListener{
	Pti topleft = new Pti(50,50);
	SimpleFrame sf;
	Graph<Term> g;
	GraphVisuals<Term> viz = new GraphVisuals<>(null, Colour.GRAY, 0);
	Term from;
	Map<Term, Pti> spring;

	public LambdaBuilder() {
		sf = new SimpleFrame(800, 600, 1, "", Colour.BLACK);
		g = new Graph<>(true, false);
		sf.registerKeyListener(this);
		sf.registerMouseListener(this);
	}
	
	public static void main(String[] args){
		new LambdaBuilder();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		if(Character.isAlphabetic(c)){
			if(from == null){
				g.addVertex(new Variable(c + ""));
				spring();
			}else{
				g.removeVertex(from);
				Variable v = new Variable(c + ""); 
				g.addVertex(new Abstraction(v, from));
				from = null;
				spring();
			}
		}
		e.consume();
		draw(spring);
	}
	
	private void draw(Map<Term, Pti> map) {
		sf.clear(Colour.BLACK);
		g.draw(sf, map, viz, from==null?Colour.WHITE:Colour.RED, null);
		sf.render();
	}

	private void spring() {
		spring = g.springEmbed(topleft, 700, 500, 1);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Pti click = new Pti(e.getX(), e.getY());
		Term t = vertexAt(click, spring);
		System.out.println(t);
		if(t != null){
			
			if(e.getButton() == 1){ //lclick build
				if(from == null){
					from = t;
				}else if(t != from){
					g.removeVertex(from);
					g.removeVertex(t);
					g.addVertex(new Application(t, from));
					from  = null;
					spring();
				}else{
					from = null;
				}
			}else if(e.getButton() == 3){ //rclick reduce
				Reduction r = t.lmomReduce();
				if(r.reducedFlag){
					g.removeVertex(t);
					g.addVertex(r.term);
					spring();
				}
				from  = null;
			}
		}
		draw(spring);
	}

	private Term vertexAt(Pti click, Map<Term, Pti> spring) {
		if(spring == null) return null;
		for(Term t : spring.keySet()){
			if(spring.get(t).euclideanDistance(click) < 30){
				return t;
			}
		}
		return null;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
