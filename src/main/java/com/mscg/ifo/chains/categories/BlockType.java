package com.mscg.ifo.chains.categories;

import com.mscg.ifo.parser.exception.BlockTypeCategoryException;

public enum BlockType
 {
  NORMAL(0x00), BLOCK(0x01);

  private int value;

  private BlockType(int value)
   {
    this.value = value;
   }

  public static BlockType getBlockTypeByValue(int value) throws BlockTypeCategoryException
   {
    for(BlockType type : values())
     {
      if(type.value == value)
       return type;
     }
    throw new BlockTypeCategoryException("Invalid value (" + value + ") for block type");
   }
 }
