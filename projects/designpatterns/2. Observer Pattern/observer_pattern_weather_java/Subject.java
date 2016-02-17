
public interface Subject {
	// observer 등록
	public void registerObserver(Observer o);
	// observer 해지
	public void unregisterObserver(Observer o);
	// 모든 observer에 통지
	public void notifyObservers();
}
