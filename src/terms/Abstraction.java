package terms;

public class Abstraction implements Term {
	Variable var;
	Term term;

	public Abstraction(Variable var, Term term) {
		this.var = var;
		this.term = term;
	}
	
	@Override
	public String toString(){
		return String.format("(\\%s.%s)", var.toString(), term.toString());
	}

	public Reduction apply(Term right) {
		System.out.println("apply");
		term = term.findAndReplace(var, right);
		return new Reduction(term, true);
	}

	@Override
	public Reduction lmomReduce() {
		Reduction r = term.lmomReduce();
		r.term = this;
		return r;
	}

	@Override
	public Term findAndReplace(Variable var, Term right) {
		if(!var.name.equals(this.var.name)){
			term = term.findAndReplace(var, right);
		}
		return this;
	}

	@Override
	public Term deepCopy() {
		return new Abstraction(new Variable(var.getName()), term.deepCopy());
	}
}
