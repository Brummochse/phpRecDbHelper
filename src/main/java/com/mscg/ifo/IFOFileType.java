package com.mscg.ifo;

import com.mscg.ifo.parser.exception.IFOFileTypeException;

public enum IFOFileType
 {
  VMG("DVDVIDEO-VMG"), VTS("DVDVIDEO-VTS");

  public static IFOFileType getTypeByCode(String code) throws IFOFileTypeException
   {
    for(IFOFileType type : values())
     {
      if(type.code.equals(code))
       return type;
     }

    throw new IFOFileTypeException("Unknown IFO file type \"" + code + "\"");
   }

  private String code;

  IFOFileType(String code)
   {
    this.code = code;
   }

  public String getCode()
   {
    return code;
   }

  @Override
  public String toString()
   {
    return code;
   }
 }
