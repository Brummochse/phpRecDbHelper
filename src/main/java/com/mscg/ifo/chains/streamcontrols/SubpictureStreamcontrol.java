package com.mscg.ifo.chains.streamcontrols;

public class SubpictureStreamcontrol
 {

  protected boolean streamAvailable;
  protected byte streamNumberFor43;
  protected byte streamNumberForWide;
  protected byte streamNumberForLetterbox;
  protected byte streamNumberForPanScan;

  public boolean isStreamAvailable()
   {
    return streamAvailable;
   }

  public void setStreamAvailable(boolean streamAvailable)
   {
    this.streamAvailable = streamAvailable;
   }

  public byte getStreamNumberFor43()
   {
    return streamNumberFor43;
   }

  public void setStreamNumberFor43(byte streamNumberFor43)
   {
    this.streamNumberFor43 = streamNumberFor43;
   }

  public byte getStreamNumberForWide()
   {
    return streamNumberForWide;
   }

  public void setStreamNumberForWide(byte streamNumberForWide)
   {
    this.streamNumberForWide = streamNumberForWide;
   }

  public byte getStreamNumberForLetterbox()
   {
    return streamNumberForLetterbox;
   }

  public void setStreamNumberForLetterbox(byte streamNumberForLetterbox)
   {
    this.streamNumberForLetterbox = streamNumberForLetterbox;
   }

  public byte getStreamNumberForPanScan()
   {
    return streamNumberForPanScan;
   }

  public void setStreamNumberForPanScan(byte streamNumberForPanScan)
   {
    this.streamNumberForPanScan = streamNumberForPanScan;
   }

  @Override
  public String toString()
   {
    return "SubpictureStreamcontrol{" +
           "streamAvailable=" + streamAvailable +
           ", streamNumberFor43=" + streamNumberFor43 +
           ", streamNumberForWide=" + streamNumberForWide +
           ", streamNumberForLetterbox=" + streamNumberForLetterbox +
           ", streamNumberForPanScan=" + streamNumberForPanScan +
           '}';
   }
 }
