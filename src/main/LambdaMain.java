package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import simpleFrame.Fun;
import terms.Abstraction;
import terms.Application;
import terms.Term;
import terms.Variable;

public class LambdaMain {
	Map<Variable, String> display;

	public LambdaMain() {
		
		List<String> accepted = new ArrayList<>();
		List<String> rejected = new ArrayList<>();
		for(int i = 0; i < 100; i++){
			String s = fuzz();
			try{
				Term t = parse(s);
				accepted.add(s);
			}catch (Error e){
				rejected.add(s);
			}
		}
		
		System.out.println("\n\nAccepted:");
		for(String o : accepted){
			System.out.println(o);
		}
		
		System.out.println("\n\nRejected:");
		for(String o : rejected){
			System.out.println(o);
		}
	}

	private String fuzz() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 20; i++){
			int rand = Fun.rng(60);
			if(rand < 20) sb.append((char)(rand + 'a'));
			else if(rand < 24) sb.append('(');
			else if(rand < 28) sb.append(')');
			else if(rand < 32) sb.append('\\');
			else if(rand < 35) sb.append('.');
			else if(rand < 45) sb.append(' ');
		}
		return sb.toString();
	}

	public static void main(String[] args){
		new LambdaMain();
	}
	
	public static Term peelBrackets(String s){
		s = s.trim();
		if(s.charAt(0) != '(' || s.charAt(s.length()-1) != ')')
			return null;
		int h = 0;
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if(c == '(') h++;
			else if (c == ')') h--;
			if(h == 0){
				if(i == s.length()-1){
					System.out.println("PEELED");
					return parse(s.substring(1, s.length()-1));
				}else{
					return null;
				}
			}
		}
		return null;
	}
	
	public static Term parse(String s){
		
		char type = 'x';
		int ind1 = -1;
		int ind2 = -2;
		
		s = s.trim();
		int h = 0;
		boolean inabs = false;
		
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if(c == '(') h++;
			else if(c == ')') h--;
			else if(c == '\\'){
				inabs = true;
				if(h == 0){
					type = '.';
					ind1 = i;
				}
			}
			else if(c == '.'){
				if(!inabs) throw new Error("Malformed abs!");
				inabs = false;
				if(h == 0){
					ind2 = i;
				}
				
			}else{
				if(h == 0){
					
				}
			}
		}
		
		if(type == '.' && ind2 != -1){
			try{
				if(ind2-ind1 > 2){
					return new Abstraction(new Variable(s.charAt(ind1+1)), parse(s.substring(ind1+1) + s.substring(ind1+1)));
				}
				return new Abstraction(new Variable(s.charAt(ind1+1)), parse(s.substring(ind1) + s.substring(ind2+1)));
				
			}catch (Exception e){
				System.out.println("<" + s + ">") ;
			}
			
		}
		return null;
	}
	
	enum State{
		ABS(), APP(), VAR();
	}
}
