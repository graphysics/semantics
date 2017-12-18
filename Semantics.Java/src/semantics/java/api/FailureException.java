package semantics.java.api;

public class FailureException extends Exception {

	Failure Failure;
	public FailureException(Failure failure)
	{
		super("Exception of Failure " + failure.Type + ":" + failure.Message);
		this.Failure = failure;
	}	

	@Override
	public String toString() {
		return "Exception of Failure " + this.Failure.Type + ":" + this.Failure.Message;
	}
}
