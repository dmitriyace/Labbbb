
public interface Moves {

	// void prepSleep(Heroes h, Heroes h1, Heroes h2);
	//
	// // отвечает за то, чтобы вызвать метод для момента, когда герои почти готовы
	// // спать
	String  sleep(Heroes h, String s);

	// вызывается, когда все уже готовы ко сну
	String giveClearPj(Heroes h, Pj p, String s);// дать чистую пижаму

	String givePj(Heroes h, Pj p, String s);// дать пижаму больше

	String givePjsmall(Heroes h, Pj p, String s);// дать пижаму меньше

	String eatJam(Heroes h, Pj p, String s);// съесть варенье

	String suspicious(Heroes h, String s);// подумать перед сном о варенье

}
// интерфейс действий героев , не связанных с пижамой