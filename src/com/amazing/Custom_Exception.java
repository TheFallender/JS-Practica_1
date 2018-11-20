package com.amazing;

public class Custom_Exception extends RuntimeException { //This is a runtime exception, Eclipse can recover from it
	private static final long serialVersionUID = -1375331979705542972L; //Serial of the exception

	public Custom_Exception(String msg, Throwable root_cause) { //Mesage that the exception throws.
        super(msg, root_cause);
    }
}