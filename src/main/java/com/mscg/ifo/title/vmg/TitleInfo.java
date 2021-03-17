package com.mscg.ifo.title.vmg;

import com.mscg.util.BinaryDataReaderUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class TitleInfo
 {

  protected TitleType type;
  protected byte angles;
  protected short chapters;
  protected short parentalMask;
  protected byte videoTitleSetNumber;
  protected byte titleNumberWithinVTS;
  protected long vtsStartSector;

  public TitleType getType()
   {
    return type;
   }

  public void setType(TitleType type)
   {
    this.type = type;
   }

  public void setType(byte type)
   {
    this.type = new TitleType(type);
   }

  public byte getAngles()
   {
    return angles;
   }

  public void setAngles(byte angles)
   {
    this.angles = angles;
   }

  public short getChapters()
   {
    return chapters;
   }

  public void setChapters(short chapters)
   {
    this.chapters = chapters;
   }

  public short getParentalMask()
   {
    return parentalMask;
   }

  public void setParentalMask(short parentalMask)
   {
    this.parentalMask = parentalMask;
   }

  public byte getVideoTitleSetNumber()
   {
    return videoTitleSetNumber;
   }

  public void setVideoTitleSetNumber(byte videoTitleSetNumber)
   {
    this.videoTitleSetNumber = videoTitleSetNumber;
   }

  public byte getTitleNumberWithinVTS()
   {
    return titleNumberWithinVTS;
   }

  public void setTitleNumberWithinVTS(byte titleNumberWithinVTS)
   {
    this.titleNumberWithinVTS = titleNumberWithinVTS;
   }

  public long getVtsStartSector()
   {
    return vtsStartSector;
   }

  public void setVtsStartSector(long vtsStartSector)
   {
    this.vtsStartSector = vtsStartSector;
   }

  public void parseFile(RandomAccessFile file) throws IOException
   {
    setType(file.readByte());
    setAngles(file.readByte());
    setChapters(BinaryDataReaderUtil.readBigEndianShort(file));
    setParentalMask(BinaryDataReaderUtil.readBigEndianShort(file));
    setVideoTitleSetNumber(file.readByte());
    setTitleNumberWithinVTS(file.readByte());
    setVtsStartSector(BinaryDataReaderUtil.readBigEndianUnsignedInt(file));
   }

  public void parseStream(InputStream is) throws IOException
   {
    setType((byte)is.read());
    setAngles((byte)is.read());
    setChapters(BinaryDataReaderUtil.readBigEndianShort(is));
    setParentalMask(BinaryDataReaderUtil.readBigEndianShort(is));
    setVideoTitleSetNumber((byte)is.read());
    setTitleNumberWithinVTS((byte)is.read());
    setVtsStartSector(BinaryDataReaderUtil.readBigEndianUnsignedInt(is));
   }

  @Override public String toString()
   {
    return "TitleInfo{" +
           "type=" + type +
           ", angles=" + angles +
           ", chapters=" + chapters +
           ", parentalMask=" + parentalMask +
           ", videoTitleSetNumber=" + videoTitleSetNumber +
           ", titleNumberWithinVTS=" + titleNumberWithinVTS +
           ", vtsStartSector=" + vtsStartSector +
           '}';
   }
 }
