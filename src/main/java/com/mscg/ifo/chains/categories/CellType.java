package com.mscg.ifo.chains.categories;

import com.mscg.ifo.parser.exception.CellTypeCategoryException;

public enum CellType
 {
  NORMAL(0x00), FIRST_ANGLE_BLOCK(0x01),
  MIDDLE_ANGLE_BLOCK(0x02), LAST_ANGLE_BLOCK(0x03);

  private int value;

  private CellType(int value)
   {
    this.value = value;
   }

  public static CellType getCellTypeByValue(int value) throws CellTypeCategoryException
   {
    for(CellType type : values())
     {
      if(type.value == value)
       return type;
     }
    throw new CellTypeCategoryException("Invalid value (" + value + ") for cell type");
   }
 }
