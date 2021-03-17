package com.mscg.ifo.chains.streamcontrols;

public class AudioStreamControl
 {

  protected boolean streamAvailable;
  protected byte streamNumber;

  public boolean isStreamAvailable()
   {
    return streamAvailable;
   }

  public void setStreamAvailable(boolean streamAvailable)
   {
    this.streamAvailable = streamAvailable;
   }

  public byte getStreamNumber()
   {
    return streamNumber;
   }

  public void setStreamNumber(byte streamNumber)
   {
    this.streamNumber = streamNumber;
   }

  @Override
  public String toString()
   {
    return "AudioStreamControl{" +
           "streamAvailable=" + streamAvailable +
           ", streamNumber=" + streamNumber +
           '}';
   }
 }
