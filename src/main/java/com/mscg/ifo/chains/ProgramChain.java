package com.mscg.ifo.chains;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.mscg.ifo.chains.categories.ProgramChainCategory;
import com.mscg.ifo.chains.streamcontrols.AudioStreamControl;
import com.mscg.ifo.chains.streamcontrols.SubpictureStreamcontrol;
import com.mscg.ifo.parser.exception.IFOFileException;
import com.mscg.util.BinaryDataReaderUtil;

public class ProgramChain
 {

  protected static final int AUDIO_STREAM_CONTROLS_SIZE = 8;
  protected static final int SUBPICUTRE_STREAM_CONTROLS_SIZE = 32;

  protected ProgramChainCategory category;
  protected byte programs;
  protected byte cells;
  protected PlaybackTime playbackTime;
  protected int prohibitedUserOps;
  protected List<AudioStreamControl> audioStreamControls;
  protected List<SubpictureStreamcontrol> subpictureStreamControls;
  protected List<CellPlaybackInformation> cellPlaybackInformations;

  public ProgramChain()
   {
    audioStreamControls = new ArrayList<AudioStreamControl>(AUDIO_STREAM_CONTROLS_SIZE);
    subpictureStreamControls = new ArrayList<SubpictureStreamcontrol>(SUBPICUTRE_STREAM_CONTROLS_SIZE);
   }

  public ProgramChainCategory getCategory()
   {
    return category;
   }

  public void setCategory(ProgramChainCategory category)
   {
    this.category = category;
   }

  public byte getPrograms()
   {
    return programs;
   }

  public void setPrograms(byte programs)
   {
    this.programs = programs;
   }

  public byte getCells()
   {
    return cells;
   }

  public void setCells(byte cells)
   {
    this.cells = cells;
   }

  public PlaybackTime getPlaybackTime()
   {
    return playbackTime;
   }

  public void setPlaybackTime(PlaybackTime playbackTime)
   {
    this.playbackTime = playbackTime;
   }

  public int getProhibitedUserOps()
   {
    return prohibitedUserOps;
   }

  public void setProhibitedUserOps(int prohibitedUserOps)
   {
    this.prohibitedUserOps = prohibitedUserOps;
   }

  public List<AudioStreamControl> getAudioStreamControls()
   {
    return audioStreamControls;
   }

  public List<SubpictureStreamcontrol> getSubpictureStreamControls()
   {
    return subpictureStreamControls;
   }

  public List<CellPlaybackInformation> getCellPlaybackInformations()
   {
    return cellPlaybackInformations;
   }

  public void parseFile(RandomAccessFile file) throws IOException, IFOFileException
   {
    long position = file.getFilePointer();

    // discard 2 useless bytes
    BinaryDataReaderUtil.readBytes(file, 2);
    programs = file.readByte();
    cells = file.readByte();

    playbackTime = new PlaybackTime();
    byte playbackTimes[] = BinaryDataReaderUtil.readBytes(file, 4);
    playbackTime.parseByteArray(playbackTimes);

    prohibitedUserOps = BinaryDataReaderUtil.readBigEndianInt(file);

    for(int i = 0; i < AUDIO_STREAM_CONTROLS_SIZE; i++)
     {
      byte ascByte = file.readByte();
      AudioStreamControl asc = new AudioStreamControl();
      asc.setStreamAvailable((ascByte & 0x80) != 0);
      asc.setStreamNumber((byte)(ascByte & 0x07));
      audioStreamControls.add(asc);
      // discard one reserved byte
      file.readByte();
     }

    for(int i = 0; i < SUBPICUTRE_STREAM_CONTROLS_SIZE; i++)
     {
      byte sscByte = file.readByte();
      SubpictureStreamcontrol ssc = new SubpictureStreamcontrol();
      ssc.setStreamAvailable((sscByte & 0x80) != 0);
      ssc.setStreamNumberFor43((byte)(sscByte & 0x1F));
      ssc.setStreamNumberForWide((byte)(file.readByte() & 0x1F));
      ssc.setStreamNumberForLetterbox((byte)(file.readByte() & 0x1F));
      ssc.setStreamNumberForPanScan((byte)(file.readByte() & 0x1F));
      subpictureStreamControls.add(ssc);
     }

    //TODO parse bytes between offsets 0x009C and 0x00E7

    file.seek(position + 0x00E8);
    int offset = BinaryDataReaderUtil.readBigEndianUnsignedShort(file);
    file.seek(position + offset);
    cellPlaybackInformations = new ArrayList<CellPlaybackInformation>();
    for(int i = 0; i < cells; i++)
     {
      CellPlaybackInformation playbackInfo = new CellPlaybackInformation();
      playbackInfo.parseFile(file);
      cellPlaybackInformations.add(playbackInfo);
     }

    //TODO parse remaining bytes

   }

 }
