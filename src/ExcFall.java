public class ExcFall extends Exception {

	public ExcFall() {
		super("��������� ���������� ���� ����������");
	}

	public ExcFall(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "������� " + super.toString();
	}

}
