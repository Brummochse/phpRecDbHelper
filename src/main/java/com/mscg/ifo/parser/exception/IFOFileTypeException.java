package com.mscg.ifo.parser.exception;

public class IFOFileTypeException extends IFOFileException
 {

  private static final long serialVersionUID = -6882798019871609314L;

  public IFOFileTypeException()
   {
   }

  public IFOFileTypeException(String message)
   {
    super(message);
   }

  public IFOFileTypeException(String message, Throwable cause)
   {
    super(message, cause);
   }

  public IFOFileTypeException(Throwable cause)
   {
    super(cause);
   }
 }
