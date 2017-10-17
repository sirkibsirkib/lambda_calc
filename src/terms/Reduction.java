package terms;

public class Reduction {
	public Term term;
	public boolean reducedFlag;

	public Reduction(Term term, boolean reducedFlag) {
		this.term = term;
		this.reducedFlag = reducedFlag;
	}

}
