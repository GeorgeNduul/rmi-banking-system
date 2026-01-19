/**
 *   "Java Enterprise in a Nutshell, Third Edition, 
 *    by Jim Farley and William Crawford 
 *    with Prakash Malani, John G. Norman, and Justin Gehtland. 
 *    Copyright 2006 O'Reilly Media, Inc., 0-596-10142-2."
 *    Edited by
 */

/**
 * An exception indicating that the account funds did not support
 * a particular Account operation.
 */

public class InsufficientFundsException extends Exception {
  public InsufficientFundsException(String msg) {
    super(msg);
  }

  public InsufficientFundsException() {
    super();
  }
}
