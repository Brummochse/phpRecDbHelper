package com.mscg.ifo.parser;

import com.mscg.ifo.*;
import com.mscg.ifo.chains.ProgramChainsTable;
import com.mscg.ifo.parser.exception.IFOFileException;
import com.mscg.ifo.title.vmg.TitleInfo;
import com.mscg.ifo.title.vts.TableOfTitles;
import com.mscg.util.BinaryDataReaderUtil;

import java.io.IOException;
import java.io.RandomAccessFile;

public class IFOFileParser
 {

//  private static final Log LOG = LogFactory.getLog(IFOFileParser.class);

  /**
   * Parses a binary IFO file and produces the corresponding bean.
   *
   * @param file The binary file from which the IFO data will be read.
   * @param closeFile a boolean switch that indicates if the {@link RandomAccessFile file}
   * should be closed after parsing.
   * @return an {@link IFOFile} object with the data read from the file.
   * @throws IFOFileException If an error occurs while parsing the file.
   */
  public static IFOFile parseIFOFile(RandomAccessFile file, boolean closeFile) throws IFOFileException
   {

    try
     {
      String typeHeader = new String(BinaryDataReaderUtil.readBytes(file, IFOFile.TYPE_HEADER_LENGTH), "ISO-8859-1");

      IFOFileType fileType = IFOFileType.getTypeByCode(typeHeader);

//      if(LOG.isDebugEnabled())
//       LOG.debug("Detected IFO file type is \"" + fileType + "\"");

      IFOFile ifo;

      switch(fileType)
       {
        case VMG:
         ifo = new VMG_IFOFile(fileType);
         parseVMGIFOFileBody((VMG_IFOFile)ifo, file);
         break;
        case VTS:
         ifo = new VTS_IFOFile(fileType);
         parseVTSIFOFileBody((VTS_IFOFile)ifo, file);
         break;
        default:
         ifo = null;
       }

      return ifo;
     }
    catch(IOException e)
     {
      throw new IFOFileException("An I/O error prevented parsing of IFO file", e);
     }
    finally
     {
      if(closeFile)
       {
        try
         {
          file.close();
         }
        catch(Throwable ignored){}
       }
     }

   }

  protected static void parseVMGIFOFileBody(VMG_IFOFile ifo, RandomAccessFile file) throws IFOFileException
   {
    try
     {
      long sectorPointer;

      ifo.setBupLastSector(BinaryDataReaderUtil.readBigEndianUnsignedInt(file));

      // jump to 0x001C
      file.seek(0x001C);
      ifo.setIfoLastSector(BinaryDataReaderUtil.readBigEndianUnsignedInt(file));
      short version = BinaryDataReaderUtil.readBigEndianShort(file);
      ifo.setVersion(new IFOFile.IFOFileVersion((byte)((version & 0x00F0) >> 4),
                                                (byte)(version & 0x000F)));
      ifo.setVmgCategory(BinaryDataReaderUtil.readBigEndianInt(file));
      ifo.setVolumes(BinaryDataReaderUtil.readBigEndianShort(file));
      ifo.setSideID(file.readByte());

      // jump to 0x003E
      file.seek(0x003E);
      ifo.setTitleSets(BinaryDataReaderUtil.readBigEndianShort(file));
      ifo.setProviderID(BinaryDataReaderUtil.readBytes(file, 32));
      ifo.setVmgPos(BinaryDataReaderUtil.readBigEndianLong(file));
      ifo.setVMGI_MATEndAddress(BinaryDataReaderUtil.readBigEndianUnsignedInt(file));

      // jump to 0x0084
      file.seek(0x0084);
      sectorPointer = BinaryDataReaderUtil.readBigEndianUnsignedInt(file); // in this case, the value is the address,
                                                                           // not the sector pointer
      file.seek(sectorPointer);
      ProgramChainsTable firstPlayProgramChainTable = new ProgramChainsTable();
      firstPlayProgramChainTable.parseFile(file);
      ifo.setFirstPlayProgramChainTable(firstPlayProgramChainTable);

      // jump to 0x0084
      file.seek(0x00C0);
      ifo.setMenuVOBStartSector(BinaryDataReaderUtil.readBigEndianUnsignedInt(file));

      // jump to 0x00C0
      file.seek(0x00C0);
      ifo.setMenuVOBStartSector(BinaryDataReaderUtil.readBigEndianUnsignedInt(file));

      //table of titles
      sectorPointer = BinaryDataReaderUtil.readBigEndianUnsignedInt(file);
      file.seek(sectorPointer * IFOFile.SECTOR_SIZE);
      short numOfTitles = BinaryDataReaderUtil.readBigEndianShort(file);
//      if(LOG.isDebugEnabled())
//       LOG.debug("Detected number of titles: " + numOfTitles);
      ifo.allocateTitles(numOfTitles);
      // discard 6 useless bytes
      BinaryDataReaderUtil.readBytes(file, 6);
      for(short i = 0; i < numOfTitles; i++)
       {
        TitleInfo title = new TitleInfo();
        title.parseFile(file);
        ifo.getTitles().add(title);
       }

      //TODO parse data between 0x00C8 and EOF
     }
    catch(IOException e)
     {
      throw new IFOFileException("An I/O error prevented parsing of VMG IFO file", e);
     }
   }

  protected static void parseVTSIFOFileBody(VTS_IFOFile ifo, RandomAccessFile file) throws IFOFileException
   {
    try
     {
      long sectorPointer;

      ifo.setBupLastSector(BinaryDataReaderUtil.readBigEndianUnsignedInt(file));

      // jump to 0x001C
      file.seek(0x001C);
      ifo.setIfoLastSector(BinaryDataReaderUtil.readBigEndianUnsignedInt(file));
      short version = BinaryDataReaderUtil.readBigEndianShort(file);
      ifo.setVersion(new IFOFile.IFOFileVersion((byte)((version & 0x00F0) >> 4),
                                                (byte)(version & 0x000F)));
      ifo.setCategory(VTSCategory.getVTSCategoryFromInt(BinaryDataReaderUtil.readBigEndianInt(file)));

      // jump to 0x0080
      file.seek(0x0080);
      ifo.setVTS_MATEndAddress(BinaryDataReaderUtil.readBigEndianUnsignedInt(file));

      // jump to 0x00C0
      file.seek(0x00C0);
      ifo.setVOBMenuStartSector(BinaryDataReaderUtil.readBigEndianUnsignedInt(file));
      ifo.setVOBTitleStartSector(BinaryDataReaderUtil.readBigEndianUnsignedInt(file));

      // table of titles and chapters
      file.seek(0x00C8);
      sectorPointer = BinaryDataReaderUtil.readBigEndianUnsignedInt(file);
      file.seek(sectorPointer * IFOFile.SECTOR_SIZE);
      TableOfTitles titles = new TableOfTitles();
      titles.parseFile(file);
      ifo.setTableOfTitles(titles);

      // title program chain table
      file.seek(0x00CC);
      sectorPointer = BinaryDataReaderUtil.readBigEndianUnsignedInt(file);
      file.seek(sectorPointer * IFOFile.SECTOR_SIZE);
      ProgramChainsTable programChainsTable = new ProgramChainsTable();
      programChainsTable.parseFile(file);
      ifo.setProgramChainsTable(programChainsTable);

      file.seek(0x0200);
      byte[] videoAttrsBytes=BinaryDataReaderUtil.readBytes(file,2);
      VideoAttributes vas=new VideoAttributes(videoAttrsBytes[0],videoAttrsBytes[1]);
      ifo.setVideoAttributes(vas);
     }
    catch(IOException e)
     {
      throw new IFOFileException("An I/O error prevented parsing of VTS IFO file", e);
     }
   }

 }
