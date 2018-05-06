
public class Heroes  implements Moves {

	protected Type t;
	protected Emotional e;
	protected String name;
	protected EPj epj;
	protected EPjc epjc;

	static Heroes HOST = new Heroes("Хозяин дома", Emotional.HAPPY, Type.HUMAN);// создали хозяина дома
//	static Heroes Carlson= new Heroes("Карлсон", Emotional.HAPPY, Type.FAIRY);
	int hmm;// переменная, используемая в рассчете вероятности, что герой отрежет рукава у
	// пижамы
	int bosse;// переменная, используемая в рассчете вероятности, что герой получит пижаму
	// Боссе
	static int thoughtful;// переменная, используемая в рассчете вероятности того, что герой будет
	// задумчивым ночью

	// HOST = new Heroes("Хозяин дома", e.HAPPY)

	int hashcode = 1;// для переопределения хэшкода

	static int eating;// переменная, используемая в рассчете вероятности того, что герой съест варенье

	Heroes(String name, Emotional e, Type t) {
		this.name = name;
		this.e = e;
		this.t = t;

	}

	private class Nametion extends RuntimeException {

		public Nametion() {
			super("У персонажа нет имени");
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
		System.out.println(h.getName() + " уже готов ко сну");
	}

	@Override
	public void suspicious(Heroes h) {
		System.out.printf("%s уже улегся в кровать и пытался заснуть," + " но то и дело открывал глаза, задумываясь"
				+ " о том, сколько же осталось варенья %n", h.getName());
	}

	public void preparingProcess(Heroes h, Pj p) throws ExcFall {
		do {
			System.out.println(h.getName() + " примеряет пижаму");
			switch (p.getSize()) {
			case LONG: {
				System.out.printf("У %s штанины и рукава оказались черезчур длинными", h.getName());
				System.out.println();
				givePjsmall(h, p);

				break;
			}
			case SHORT:
				System.out.printf("%s сидел на краю кровати и пытался натянуть на себя штаны,"
						+ " но, как ни старался, ничего не получалось.", h.getName());
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
				System.out.println(h.getName() + " запачкал пижаму");
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
			System.out.printf("— Я дам тебе пижаму Боссе, — сказал %s, " + "метнулся в комнату брата и принёс оттуда "
					+ "большую пижаму. Она налезла и на такого " + "толстяка, как %s.%n", HOST, getName());

			p.epj = epj.LONG;

		} else {
			System.out.printf("%s нашел новую пижаму для %s, как раз по размеру %n", HOST, getName());
			p.epj = epj.OK;
		}

	}

	public void eatJam(Heroes h, Pj p) {
		System.out.println(getName() + " устал ждать пока все улягутся спать и он решил съесть варенье \n"
				+ "Ой... Кажется, пижаму запачкал... Опять в стирку...");
		p.epjc = epjc.UNWASHED;

	}

	// переопределение методов

//	public String toString() {

//		return "Хозяин дома";// в местах где мы вызываем героя HOST будет выводится имя хозяина дома.
		// чтобы изменить это имя надо изменить строку, которую возвращает toString
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

		System.out.printf("Пришлось найти чистую пижаму для %s %n", getName());
		p.epjc = epjc.WASHED;
	}

	@Override
	public void givePjsmall(Heroes h, Pj p) {

		hmm = (int) (Math.random() * 2);
		if (hmm == 0) {
			System.out.printf(
					", но %s тут же нашёл выход — недолго думая он их обрезал. %n"
							+ "%s не успел и слова вымолвить, но он, по правде "
							+ "говоря, даже не очень огорчился. %n В конце концов, "
							+ "рассуждал он, пижама — это пустяки, дело житейское, "
							+ "и то, что она погибла, не может омрачить его радости: "
							+ "%n ведь это такое удивительное событие — " + "%s останется у него ночевать!%n ",
					HOST, getName(), getName());

		} else {
			System.out.printf("%s нашел новую пижаму для %s, как раз по размеру %n", HOST, getName());

		}
		p.epj = epj.OK;

	}

}
