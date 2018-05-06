
public interface Moves {

	// void prepSleep(Heroes h, Heroes h1, Heroes h2);
	//
	// // отвечает за то, чтобы вызвать метод для момента, когда герои почти готовы
	// // спать
	void sleep(Heroes h);

	// вызывается, когда все уже готовы ко сну
	void giveClearPj(Heroes h, Pj p);// дать чистую пижаму

	void givePj(Heroes h, Pj p);// дать пижаму больше

	void givePjsmall(Heroes h, Pj p);// дать пижаму меньше

	void eatJam(Heroes h, Pj p);// съесть варенье

	void suspicious(Heroes h);// подумать перед сном о варенье

}
// интерфейс действий героев , не связанных с пижамой