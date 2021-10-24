package au.com.belong.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7370374340769138994L;
	private static final Logger logger = LogManager.getLogger(ResourceNotFoundException.class);

	public ResourceNotFoundException(String errorMessage) {
		super(errorMessage);
		logger.info(errorMessage);
	}

}
