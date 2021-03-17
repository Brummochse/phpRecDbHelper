package com.mscg.ifo.chains;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.mscg.ifo.chains.categories.CellCategory;
import com.mscg.ifo.parser.exception.IFOFileException;
import com.mscg.util.BinaryDataReaderUtil;

public class CellPlaybackInformation
 {

  protected CellCategory cellCategory;
  protected PlaybackTime playbackTime;
  protected long firstVOBUStartSector;
  protected long firstILVUEncSector;
  protected long lastVOBUStartSector;
  protected long lastVOBUEndSector;

  public CellCategory getCellCategory()
   {
    return cellCategory;
   }

  public void setCellCategory(CellCategory cellCategory)
   {
    this.cellCategory = cellCategory;
   }

  public PlaybackTime getPlaybackTime()
   {
    return playbackTime;
   }

  public void setPlaybackTime(PlaybackTime playbackTime)
   {
    this.playbackTime = playbackTime;
   }

  public long getFirstVOBUStartSector()
   {
    return firstVOBUStartSector;
   }

  public void setFirstVOBUStartSector(long firstVOBUStartSector)
   {
    this.firstVOBUStartSector = firstVOBUStartSector;
   }

  public long getFirstILVUEncSector()
   {
    return firstILVUEncSector;
   }

  public void setFirstILVUEncSector(long firstILVUEncSector)
   {
    this.firstILVUEncSector = firstILVUEncSector;
   }

  public long getLastVOBUStartSector()
   {
    return lastVOBUStartSector;
   }

  public void setLastVOBUStartSector(long lastVOBUStartSector)
   {
    this.lastVOBUStartSector = lastVOBUStartSector;
   }

  public long getLastVOBUEndSector()
   {
    return lastVOBUEndSector;
   }

  public void setLastVOBUEndSector(long lastVOBUEndSector)
   {
    this.lastVOBUEndSector = lastVOBUEndSector;
   }

  public void parseFile(RandomAccessFile file) throws IOException, IFOFileException
   {
    cellCategory = new CellCategory();
    cellCategory.parseByteArray(BinaryDataReaderUtil.readBytes(file, 4));

    playbackTime = new PlaybackTime();
    byte playbackTimes[] = BinaryDataReaderUtil.readBytes(file, 4);
    playbackTime.parseByteArray(playbackTimes);

    firstVOBUStartSector = BinaryDataReaderUtil.readBigEndianUnsignedInt(file);
    firstILVUEncSector = BinaryDataReaderUtil.readBigEndianUnsignedInt(file);
    lastVOBUStartSector = BinaryDataReaderUtil.readBigEndianUnsignedInt(file);
    lastVOBUEndSector = BinaryDataReaderUtil.readBigEndianUnsignedInt(file);
   }
 }
