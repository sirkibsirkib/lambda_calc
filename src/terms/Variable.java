package terms;

public class Variable  implements Term {
	String name;

	public Variable(String name) {
		this.name = name;
	}
	

	
	@Override
	public String toString(){
		return name;
	}



	public String getName() {
		return name;
	}



	@Override
	public Reduction lmomReduce() {
		return new Reduction(this, false);
	}



	@Override
	public Term findAndReplace(Variable var, Term right) {
		if(name.equals(var.name)){
			return right.deepCopy();
		}
		return this;
	}



	@Override
	public Term deepCopy() {
		return new Variable(name);
	}

}
