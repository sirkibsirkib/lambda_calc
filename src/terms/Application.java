package terms;

public class Application implements Term {
	Term left, right;

	public Application(Term left, Term right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString(){
		return String.format("(%s %s)", left.toString(), right.toString());
	}

	@Override
	public Reduction lmomReduce() {
		if(left instanceof Abstraction){
			Abstraction l = (Abstraction) left;
			return l.apply(right);
		}
		Reduction l = left.lmomReduce();
		if(l.reducedFlag){
			return l;
		}
		Reduction r = right.lmomReduce();
		if(r.reducedFlag){
			right = r.term;
			return new Reduction(this, true);
		}
		return new Reduction(this, false);
	}

	@Override
	public Term findAndReplace(Variable var, Term right) {
		left = left.findAndReplace(var, right);
		this.right = this.right.findAndReplace(var, right);
		return this;
	}

	@Override
	public Term deepCopy() {
		return new Application(left.deepCopy(), right.deepCopy());
	}
}
