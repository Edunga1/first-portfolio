import java.io.Serializable;

/**
 * 통신을 통해 주고받을 메시지 형식입니다.
 * @author pArk
 *
 */
public class Message implements Serializable{
	private static final long serialVersionUID = -2723363051271966964L;

	/** 영상 이미지 타입 */
	static public final int TYPE_IMAGES = 1;
	
	int type;
	Object object;
	
	/**
	 * @param type		메시지 타입
	 * @param object	전송할 객체
	 */
	public Message(int type, Object object) {
		
		this.type = type;
		this.object = object;
	}
}
