/**
 * 
 */
package com.drink.cornerstone.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author kimmking (kimmking.cn@gmail.com)
 * @date 2013-12-13
 */
public class ServiceException extends RuntimeException {

    Log log= LogFactory.getLog(ServiceException.class);
	private static final long serialVersionUID = -7393349766063423131L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Exception e) {
        super(message);
        e.printStackTrace();
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
