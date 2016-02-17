import java.io.Serializable;

/**
 * ����� ���� �ְ���� �޽��� �����Դϴ�.
 * @author pArk
 *
 */
public class Message implements Serializable{
	private static final long serialVersionUID = -2723363051271966964L;

	/** ���� �̹��� Ÿ�� */
	static public final int TYPE_IMAGES = 1;
	
	int type;
	Object object;
	
	/**
	 * @param type		�޽��� Ÿ��
	 * @param object	������ ��ü
	 */
	public Message(int type, Object object) {
		
		this.type = type;
		this.object = object;
	}
}
