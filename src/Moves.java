
public interface Moves {

	// void prepSleep(Heroes h, Heroes h1, Heroes h2);
	//
	// // �������� �� ��, ����� ������� ����� ��� �������, ����� ����� ����� ������
	// // �����
	void sleep(Heroes h);

	// ����������, ����� ��� ��� ������ �� ���
	void giveClearPj(Heroes h, Pj p);// ���� ������ ������

	void givePj(Heroes h, Pj p);// ���� ������ ������

	void givePjsmall(Heroes h, Pj p);// ���� ������ ������

	void eatJam(Heroes h, Pj p);// ������ �������

	void suspicious(Heroes h);// �������� ����� ���� � �������

}
// ��������� �������� ������ , �� ��������� � �������