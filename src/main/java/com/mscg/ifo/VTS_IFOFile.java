package com.mscg.ifo;

import com.mscg.ifo.chains.ProgramChainsTable;
import com.mscg.ifo.title.vts.TableOfTitles;

public class VTS_IFOFile extends IFOFile
 {

  protected VTSCategory category;
  protected long VTS_MATEndAddress;
  protected long VOBMenuStartSector;
  protected long VOBTitleStartSector;
  protected TableOfTitles tableOfTitles;
  protected ProgramChainsTable programChainsTable;
  protected VideoAttributes videoAttributes;

  public VideoAttributes getVideoAttributes() {
	return videoAttributes;
}

public void setVideoAttributes(VideoAttributes videoAttributes) {
	this.videoAttributes = videoAttributes;
}

public VTS_IFOFile(IFOFileType fileType)
   {
    super(fileType);
   }

  public VTSCategory getCategory()
   {
    return category;
   }

  public void setCategory(VTSCategory category)
   {
    this.category = category;
   }

  public long getVTS_MATEndAddress()
   {
    return VTS_MATEndAddress;
   }

  public void setVTS_MATEndAddress(long VTS_MATEndAddress)
   {
    this.VTS_MATEndAddress = VTS_MATEndAddress;
   }

  public long getVOBMenuStartSector()
   {
    return VOBMenuStartSector;
   }

  public void setVOBMenuStartSector(long VOBMenuStartSector)
   {
    this.VOBMenuStartSector = VOBMenuStartSector;
   }

  public long getVOBTitleStartSector()
   {
    return VOBTitleStartSector;
   }

  public void setVOBTitleStartSector(long VOBTitleStartSector)
   {
    this.VOBTitleStartSector = VOBTitleStartSector;
   }

  public TableOfTitles getTableOfTitles()
   {
    return tableOfTitles;
   }

  public void setTableOfTitles(TableOfTitles tableOfTitles)
   {
    this.tableOfTitles = tableOfTitles;
   }

  public ProgramChainsTable getProgramChainsTable()
   {
    return programChainsTable;
   }

  public void setProgramChainsTable(ProgramChainsTable programChainsTable)
   {
    this.programChainsTable = programChainsTable;
   }
 }
