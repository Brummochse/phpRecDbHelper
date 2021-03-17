package com.mscg.ifo.chains;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.mscg.ifo.chains.categories.ProgramChainCategory;
import com.mscg.ifo.parser.exception.IFOFileException;
import com.mscg.util.BinaryDataReaderUtil;
import com.mscg.util.Pair;

public class ProgramChainsTable
 {

  protected List<ProgramChain> programChains;

  public List<ProgramChain> getProgramChains()
   {
    return programChains;
   }

  public void setProgramChains(List<ProgramChain> programChains)
   {
    this.programChains = programChains;
   }

  public void parseFile(RandomAccessFile file) throws IOException, IFOFileException
   {
    long position = file.getFilePointer();

    short numOfProgramChains = BinaryDataReaderUtil.readBigEndianShort(file);
    // discard 6 useless bytes
    BinaryDataReaderUtil.readBytes(file, 6);

    programChains = new ArrayList<ProgramChain>(numOfProgramChains);
    List<Pair<ProgramChainCategory, Long>> chainInfo = new ArrayList<Pair<ProgramChainCategory, Long>>(numOfProgramChains);
    for(short i = 0; i < numOfProgramChains; i++)
     {
      byte categoryBytes[] = BinaryDataReaderUtil.readBytes(file, 4);
      boolean pgcEntry = (categoryBytes[0] & 0x80) != 0;
      byte titleNumber = (byte)(categoryBytes[0] & 0x7F);
      short parentalMask = (short)(categoryBytes[2] << 8 | categoryBytes[3]);
      ProgramChainCategory category = new ProgramChainCategory(pgcEntry, titleNumber, parentalMask);

      long chainOffset = BinaryDataReaderUtil.readBigEndianUnsignedInt(file) + position;
      chainInfo.add(new Pair<ProgramChainCategory, Long>(category, chainOffset));
     }

    for(Pair<ProgramChainCategory, Long> info : chainInfo)
     {
      ProgramChain chain = new ProgramChain();
      chain.setCategory(info.getObject1());
      // jump to chain info offset
      file.seek(info.getObject2());
      chain.parseFile(file);
      programChains.add(chain);
     }
   }

 }
