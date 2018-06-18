package Enums;

public enum EPjc {
	WASHED(1), UNWASHED(2);
	int id;

	EPjc(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}

}
// enum для степени чистоты пижамы