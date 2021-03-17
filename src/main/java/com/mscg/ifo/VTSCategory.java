package com.mscg.ifo;

import com.mscg.ifo.parser.exception.VTSCategoryException;

public enum VTSCategory
 {
  UNSPECIFIED(0), KARAOKE(1);

  private int value;

  VTSCategory(int value)
   {
    this.value = value;
   }

  public int getValue()
   {
    return value;
   }

  public static VTSCategory getVTSCategoryFromInt(int value) throws VTSCategoryException
   {
    for(VTSCategory category : values())
     {
      if(category.value == value)
       return category;
     }
    throw new VTSCategoryException("Invalid category value \"" + value + "\"");
   }
 }
