public class ExcFall extends Exception {

	public ExcFall() {
		super("случайную реализацию чекд исключения");
	}

	public ExcFall(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "Словили " + super.toString();
	}

}
