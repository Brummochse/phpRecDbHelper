package com.mscg.ifo.chains;

import java.io.IOException;

public class PlaybackTime implements Comparable<PlaybackTime>
 {

  protected byte hours;
  protected byte minutes;
  protected byte seconds;
  protected byte frames;
  protected FrameRateType frameRateType;

  public byte getHours()
   {
    return hours;
   }

  public void setHours(byte hours)
   {
    this.hours = hours;
   }

  public byte getMinutes()
   {
    return minutes;
   }

  public void setMinutes(byte minutes)
   {
    this.minutes = minutes;
   }

  public byte getSeconds()
   {
    return seconds;
   }

  public void setSeconds(byte seconds)
   {
    this.seconds = seconds;
   }

  public byte getFrames()
   {
    return frames;
   }

  public void setFrames(byte frames)
   {
    this.frames = frames;
   }

  public FrameRateType getFrameRateType()
   {
    return frameRateType;
   }

  public void setFrameRateType(FrameRateType frameRateType)
   {
    this.frameRateType = frameRateType;
   }

  public void parseByteArray(byte playbackBytes[]) throws IOException
   {
    if(playbackBytes.length != 4)
     throw new IOException("Invalid length (" + playbackBytes.length + ") of playback bytes array (4 bytes are required)");
    setHours((byte)((playbackBytes[0] / 16) * 10 + (playbackBytes[0] % 16)));
    setMinutes((byte)((playbackBytes[1] / 16) * 10 + (playbackBytes[1] % 16)));
    setSeconds((byte)((playbackBytes[2] / 16) * 10 + (playbackBytes[2] % 16)));
    byte framesByte = playbackBytes[3];
    setFrameRateType(FrameRateType.getFrameRateTypeByValue((byte)((framesByte & 0xC0) >> 6)));
    framesByte = (byte)(framesByte & 0x3F);
    setFrames((byte)((framesByte / 16) * 10 + (framesByte % 16)));
   }

  public long getTotalFrames()
   {
    return (hours * 3600l + minutes * 60 + seconds) * frameRateType.getFps() + frames;
   }

  public void setTotalFrames(long totalFrames)
   {
    frames = (byte)(totalFrames % frameRateType.getFps());
    int totalSeconds = (int)(totalFrames / frameRateType.getFps());
    hours = (byte)(totalSeconds / 3600);
    int reminder = totalSeconds % 3600;
    minutes = (byte)(reminder / 60);
    seconds = (byte)(reminder % 60);
   }

  public PlaybackTime sum(PlaybackTime other)
   {
    PlaybackTime ret = new PlaybackTime();
    ret.setFrameRateType(frameRateType);

    long totFrames1 = getTotalFrames();
    long totFrames2 = other.getTotalFrames();

    ret.setTotalFrames(totFrames1 + totFrames2);

    return ret;
   }

  public int compareTo(PlaybackTime other)
   {
    long totFrames1 = getTotalFrames();
    long totFrames2 = other.getTotalFrames();
    if(totFrames1 == totFrames2)
     return 0;
    else if(totFrames1 < totFrames2)
     return -1;
    else
     return 1;
   }

  @Override
  public boolean equals(Object o)
   {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PlaybackTime that = (PlaybackTime) o;

    return compareTo(that) == 0;
   }

  @Override
  public int hashCode()
   {
    return new Long(getTotalFrames()).hashCode();
   }

  @Override
  public String toString()
   {
    return String.format("%02d:%02d:%02d:%02d (%02d fps)", hours, minutes, seconds, frames, frameRateType.getFps());
   }
 }
