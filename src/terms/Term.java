package terms;

public interface Term {
	Reduction lmomReduce();
	Term findAndReplace(Variable var, Term right);
	Term deepCopy();
}
