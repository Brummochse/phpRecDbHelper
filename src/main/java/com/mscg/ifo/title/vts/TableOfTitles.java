package com.mscg.ifo.title.vts;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

import com.mscg.util.BinaryDataReaderUtil;

public class TableOfTitles
 {

  protected short titles;
  protected List<TitleChain> titleChains;

  public short getTitles()
   {
    return titles;
   }

  public void setTitles(short titles)
   {
    this.titles = titles;
   }

  public List<TitleChain> getTitleChains()
   {
    return titleChains;
   }

  public void parseFile(RandomAccessFile file) throws IOException
   {
    long position = file.getFilePointer();

    setTitles(BinaryDataReaderUtil.readBigEndianShort(file));
    // discard 2 useless bytes
    BinaryDataReaderUtil.readBytes(file, 2);

    long lastByte = BinaryDataReaderUtil.readBigEndianUnsignedInt(file) + position;

    long offset = BinaryDataReaderUtil.readBigEndianUnsignedInt(file);

    file.seek(position + offset);

    titleChains = new LinkedList<TitleChain>();
    while((position = file.getFilePointer()) < lastByte)
     {
      TitleChain titleChain = new TitleChain();
      titleChain.setProgramChain(BinaryDataReaderUtil.readBigEndianShort(file));
      titleChain.setProgram(BinaryDataReaderUtil.readBigEndianShort(file));
      titleChains.add(titleChain);
     }
   }

 }
