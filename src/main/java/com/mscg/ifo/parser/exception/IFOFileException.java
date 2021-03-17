package com.mscg.ifo.parser.exception;

public class IFOFileException extends Exception
 {

  private static final long serialVersionUID = 1721635402192411103L;

  public IFOFileException()
   {
   }

  public IFOFileException(String message)
   {
    super(message);
   }

  public IFOFileException(String message, Throwable cause)
   {
    super(message, cause);
   }

  public IFOFileException(Throwable cause)
   {
    super(cause);
   }
 }
