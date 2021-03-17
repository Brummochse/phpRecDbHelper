package com.mscg.ifo.title.vts;

public class TitleChain
 {

  protected short programChain;
  protected short program;

  public short getProgramChain()
   {
    return programChain;
   }

  public void setProgramChain(short programChain)
   {
    this.programChain = programChain;
   }

  public short getProgram()
   {
    return program;
   }

  public void setProgram(short program)
   {
    this.program = program;
   }

  @Override public String toString()
   {
    return "{chain: " + programChain + ", program: " + program + "}";
   }
 }
