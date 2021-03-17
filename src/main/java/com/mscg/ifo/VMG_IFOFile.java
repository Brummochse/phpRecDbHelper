package com.mscg.ifo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.mscg.ifo.chains.ProgramChainsTable;
import com.mscg.ifo.title.vmg.TitleInfo;

public class VMG_IFOFile extends IFOFile
 {

  protected int vmgCategory;
  protected short volumes;
  protected short volumeNumber;
  protected byte sideID;
  protected short titleSets;
  protected String providerID;
  protected long vmgPos;
  protected long VMGI_MATEndAddress;
  protected List<TitleInfo> titles;
  protected ProgramChainsTable firstPlayProgramChainTable;
  protected long menuVOBStartSector;

  public VMG_IFOFile(IFOFileType fileType)
   {
    super(fileType);
   }

  public int getVmgCategory()
   {
    return vmgCategory;
   }

  public void setVmgCategory(int vmgCategory)
   {
    this.vmgCategory = vmgCategory;
   }

  public short getVolumes()
   {
    return volumes;
   }

  public void setVolumes(short volumes)
   {
    this.volumes = volumes;
   }

  public short getVolumeNumber()
   {
    return volumeNumber;
   }

  public void setVolumeNumber(short volumeNumber)
   {
    this.volumeNumber = volumeNumber;
   }

  public byte getSideID()
   {
    return sideID;
   }

  public void setSideID(byte sideID)
   {
    this.sideID = sideID;
   }

  public short getTitleSets()
   {
    return titleSets;
   }

  public void setTitleSets(short titleSets)
   {
    this.titleSets = titleSets;
   }

  public String getProviderID()
   {
    return providerID;
   }

  public void setProviderID(String providerID)
   {
    this.providerID = providerID;
   }

  public void setProviderID(byte[] providerID)
   {
    int zeroByte = 0;
    for(int i = 0, l = providerID.length; i < l; i++)
     {
      if(providerID[i] == 0)
       {
        zeroByte = i;
        break;
       }
     }
    if(zeroByte != 0)
     {
      byte tmp[] = new byte[zeroByte];
      System.arraycopy(providerID, 0, tmp, 0, tmp.length);
      try
       {
        this.providerID = new String(tmp, "ISO-8859-1");
       }
      catch(UnsupportedEncodingException e)
       {
        this.providerID = new String(tmp);
       }
     }
    else
     this.providerID = "";
   }

  public long getVmgPos()
   {
    return vmgPos;
   }

  public void setVmgPos(long vmgPos)
   {
    this.vmgPos = vmgPos;
   }

  public long getVMGI_MATEndAddress()
   {
    return VMGI_MATEndAddress;
   }

  public void setVMGI_MATEndAddress(long VMGI_MATEndAddress)
   {
    this.VMGI_MATEndAddress = VMGI_MATEndAddress;
   }

  public void allocateTitles(int titles)
   {
    this.titles = new ArrayList<TitleInfo>(titles);
   }

  public List<TitleInfo> getTitles()
   {
    return titles;
   }

  public ProgramChainsTable getFirstPlayProgramChainTable()
   {
    return firstPlayProgramChainTable;
   }

  public void setFirstPlayProgramChainTable(ProgramChainsTable firstPlayProgramChainTable)
   {
    this.firstPlayProgramChainTable = firstPlayProgramChainTable;
   }

  public long getMenuVOBStartSector()
   {
    return menuVOBStartSector;
   }

  public void setMenuVOBStartSector(long menuVOBStartSector)
   {
    this.menuVOBStartSector = menuVOBStartSector;
   }
 }
