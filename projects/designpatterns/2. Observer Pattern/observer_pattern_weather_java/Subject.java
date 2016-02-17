
public interface Subject {
	// observer ���
	public void registerObserver(Observer o);
	// observer ����
	public void unregisterObserver(Observer o);
	// ��� observer�� ����
	public void notifyObservers();
}
