
public class Heroes  implements Moves {

	protected Type t;
	protected Emotional e;
	protected String name;
	protected EPj epj;
	protected EPjc epjc;

	static Heroes HOST = new Heroes("������ ����", Emotional.HAPPY, Type.HUMAN);// ������� ������� ����
//	static Heroes Carlson= new Heroes("�������", Emotional.HAPPY, Type.FAIRY);
	int hmm;// ����������, ������������ � �������� �����������, ��� ����� ������� ������ �
	// ������
	int bosse;// ����������, ������������ � �������� �����������, ��� ����� ������� ������
	// �����
	static int thoughtful;// ����������, ������������ � �������� ����������� ����, ��� ����� �����
	// ���������� �����

	// HOST = new Heroes("������ ����", e.HAPPY)

	int hashcode = 1;// ��� ��������������� �������

	static int eating;// ����������, ������������ � �������� ����������� ����, ��� ����� ����� �������

	Heroes(String name, Emotional e, Type t) {
		this.name = name;
		this.e = e;
		this.t = t;

	}

	private class Nametion extends RuntimeException {

		public Nametion() {
			super("� ��������� ��� �����");
		}

		public Nametion(String message) {
			super(message);
		}
	}

	public String getName() {
		if (name == "")
			throw new Nametion();
		return this.name;
	}

	void setType() {
		this.t = t;
	};

	void setEmots() {
		this.e = e;
	}

	@Override
	public void sleep(Heroes h) {
		System.out.println();
		System.out.println(h.getName() + " ��� ����� �� ���");
	}

	@Override
	public void suspicious(Heroes h) {
		System.out.printf("%s ��� ������ � ������� � ������� �������," + " �� �� � ���� �������� �����, �����������"
				+ " � ���, ������� �� �������� ������� %n", h.getName());
	}

	public void preparingProcess(Heroes h, Pj p) throws ExcFall {
		do {
			System.out.println(h.getName() + " ��������� ������");
			switch (p.getSize()) {
			case LONG: {
				System.out.printf("� %s ������� � ������ ��������� �������� ��������", h.getName());
				System.out.println();
				givePjsmall(h, p);

				break;
			}
			case SHORT:
				System.out.printf("%s ����� �� ���� ������� � ������� �������� �� ���� �����,"
						+ " ��, ��� �� ��������, ������ �� ����������.", h.getName());
				System.out.println();
				givePj(h, p);
				break;
			case OK:

				sleep(h);
				break;
			}
			eating = (int) (Math.random() * 2);
			if (eating == 0)
				eatJam(h, p);
			if (p.getClearance() == EPjc.UNWASHED) {
				System.out.println(h.getName() + " �������� ������");
				giveClearPj(h, p);
			}
		} while (p.epj != epj.OK || p.epjc != epjc.WASHED);

		thoughtful = (int) (Math.random() * 3);
		if (thoughtful == 1)
			throw new ExcFall();
		{
			suspicious(h);
		}
		System.out.println();

	}

	@Override
	public void givePj(Heroes h, Pj p) {
		bosse = (int) (Math.random() * 2);

		if (bosse == 0) {
			System.out.printf("� � ��� ���� ������ �����, � ������ %s, " + "�������� � ������� ����� � ����� ������ "
					+ "������� ������. ��� ������� � �� ������ " + "��������, ��� %s.%n", HOST, getName());

			p.epj = epj.LONG;

		} else {
			System.out.printf("%s ����� ����� ������ ��� %s, ��� ��� �� ������� %n", HOST, getName());
			p.epj = epj.OK;
		}

	}

	public void eatJam(Heroes h, Pj p) {
		System.out.println(getName() + " ����� ����� ���� ��� �������� ����� � �� ����� ������ ������� \n"
				+ "��... �������, ������ ��������... ����� � ������...");
		p.epjc = epjc.UNWASHED;

	}

	// ��������������� �������

//	public String toString() {

//		return "������ ����";// � ������ ��� �� �������� ����� HOST ����� ��������� ��� ������� ����.
		// ����� �������� ��� ��� ���� �������� ������, ������� ���������� toString
//	}


	public int hashCode(Heroes h, Emotional e) {
		if (h.e == e.SAD) {
			hashcode *= 3;
		}
		if (h.e == e.HAPPY) {
			hashcode *= 9;
		}
		if (h.e == e.NOTSOHAPPY) {
			hashcode *= 27;
		}
		return hashcode;
	}



	@Override
	public void giveClearPj(Heroes h, Pj p) {

		System.out.printf("�������� ����� ������ ������ ��� %s %n", getName());
		p.epjc = epjc.WASHED;
	}

	@Override
	public void givePjsmall(Heroes h, Pj p) {

		hmm = (int) (Math.random() * 2);
		if (hmm == 0) {
			System.out.printf(
					", �� %s ��� �� ����� ����� � ������� ����� �� �� �������. %n"
							+ "%s �� ����� � ����� ���������, �� ��, �� ������ "
							+ "������, ���� �� ����� ���������. %n � ����� ������, "
							+ "��������� ��, ������ � ��� �������, ���� ���������, "
							+ "� ��, ��� ��� �������, �� ����� �������� ��� �������: "
							+ "%n ���� ��� ����� ������������ ������� � " + "%s ��������� � ���� ��������!%n ",
					HOST, getName(), getName());

		} else {
			System.out.printf("%s ����� ����� ������ ��� %s, ��� ��� �� ������� %n", HOST, getName());

		}
		p.epj = epj.OK;

	}

}
