
public interface Moves {

	// void prepSleep(Heroes h, Heroes h1, Heroes h2);
	//
	// // �������� �� ��, ����� ������� ����� ��� �������, ����� ����� ����� ������
	// // �����
	String  sleep(Heroes h, String s);

	// ����������, ����� ��� ��� ������ �� ���
	String giveClearPj(Heroes h, Pj p, String s);// ���� ������ ������

	String givePj(Heroes h, Pj p, String s);// ���� ������ ������

	String givePjsmall(Heroes h, Pj p, String s);// ���� ������ ������

	String eatJam(Heroes h, Pj p, String s);// ������ �������

	String suspicious(Heroes h, String s);// �������� ����� ���� � �������

}
// ��������� �������� ������ , �� ��������� � �������