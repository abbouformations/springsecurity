package ma.formations.service.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;
	public BusinessException(String message) {
		this.message = message;
	}
}
