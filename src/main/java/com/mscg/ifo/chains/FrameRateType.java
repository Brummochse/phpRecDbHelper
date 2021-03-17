package com.mscg.ifo.chains;

public enum FrameRateType
 {
  FPS_30((byte)0x03, 30), FPS_25((byte)0x01, 25), FPS_ILLEGAL((byte)0x00, 0);

  private byte value;
  private int fps;

  FrameRateType(byte value, int fps)
   {
    this.value = value;
    this.fps = fps;
   }

  public byte getValue()
   {
    return value;
   }

  public int getFps()
   {
    return fps;
   }

  public static FrameRateType getFrameRateTypeByValue(byte value)
   {
    for(FrameRateType type : values())
     {
      if(type.getValue() == value)
       return type;
     }
    return FPS_ILLEGAL;
   }
 }
