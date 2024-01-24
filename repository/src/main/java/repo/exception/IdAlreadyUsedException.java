package repo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class IdAlreadyUsedException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private int ID;
	private Object fieldValue;

	
	public IdAlreadyUsedException(String resourceName, int ID, Object fieldValue) {
		super(String.format("%s ID already used with %s: %s ", resourceName, ID, fieldValue));
		this.resourceName = resourceName;
		this.ID = ID;
		this.fieldValue = fieldValue;
		
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
	public int getID() {
		return ID;
	}
	
	
	
}
