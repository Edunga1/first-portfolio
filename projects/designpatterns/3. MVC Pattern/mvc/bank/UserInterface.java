package dp.mvc.bank;

public interface UserInterface {
	public void removeUserObserver(UserObserver o);
	public void registUserObserver(UserObserver o);
	public void notifyObservers();
}
