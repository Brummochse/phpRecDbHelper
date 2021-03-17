package com.mscg.ifo.title.vmg;

public class TitleType
 {

  protected boolean sequential;
  protected byte command;
  protected boolean userOp0;
  protected boolean userOp1;

  public TitleType(byte type)
   {
    setSequential((type & 0x40) != 0);
    setCommand((byte)((type & 0x3C) >> 2));
    setUserOp1((type & 0x02) != 0);
    setUserOp0((type & 0x01) != 0);
   }

  public boolean isSequential()
   {
    return sequential;
   }

  public void setSequential(boolean sequential)
   {
    this.sequential = sequential;
   }

  public byte getCommand()
   {
    return command;
   }

  public void setCommand(byte command)
   {
    this.command = command;
   }

  public boolean isUserOp0()
   {
    return userOp0;
   }

  public void setUserOp0(boolean userOp0)
   {
    this.userOp0 = userOp0;
   }

  public boolean isUserOp1()
   {
    return userOp1;
   }

  public void setUserOp1(boolean userOp1)
   {
    this.userOp1 = userOp1;
   }

  @Override public String toString()
   {
    StringBuilder binaryCommand = new StringBuilder(Integer.toBinaryString(command));
    while(binaryCommand.length() != 4)
     binaryCommand.insert(0, "0");

    return "<-|" + (sequential ? 1 : 0) + "|" +
           binaryCommand + "|" +
           (userOp1 ? 1 : 0) + "|" +
           (userOp0 ? 1 : 0) + ">";
   }
 }
