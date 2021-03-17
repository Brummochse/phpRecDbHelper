package com.mscg.ifo.chains.categories;

public class ProgramChainCategory
 {

  protected boolean pgcEntry;
  protected byte titleNumber;
  protected short parentalMask;

  public ProgramChainCategory(boolean pgcEntry, byte titleNumber, short parentalMask)
   {
    this.pgcEntry = pgcEntry;
    this.titleNumber = titleNumber;
    this.parentalMask = parentalMask;
   }

  public boolean isPgcEntry()
   {
    return pgcEntry;
   }

  public void setPgcEntry(boolean pgcEntry)
   {
    this.pgcEntry = pgcEntry;
   }

  public byte getTitleNumber()
   {
    return titleNumber;
   }

  public void setTitleNumber(byte titleNumber)
   {
    this.titleNumber = titleNumber;
   }

  public short getParentalMask()
   {
    return parentalMask;
   }

  public void setParentalMask(short parentalMask)
   {
    this.parentalMask = parentalMask;
   }

  @Override public String toString()
   {
    return "ProgramChainCategory{" +
           "pgcEntry=" + pgcEntry +
           ", titleNumber=" + titleNumber +
           ", parentalMask=" + parentalMask +
           '}';
   }
 }
