package com.mscg.ifo;

import java.io.File;

public class IFOFile
 {
  /**
   *
   */
  public static class IFOFileVersion
   {
    protected byte majorVersion;
    protected byte minorVersion;

    public IFOFileVersion(byte majorVersion, byte minorVersion)
     {
      this.majorVersion = majorVersion;
      this.minorVersion = minorVersion;
     }

    public byte getMajorVersion()
     {
      return majorVersion;
     }

    public byte getMinorVersion()
     {
      return minorVersion;
     }

    @Override
    public String toString()
     {
      return "[" + majorVersion + ", " + minorVersion + "]";
     }
   }

  public static final int TYPE_HEADER_LENGTH = 12;
  public static final int SECTOR_SIZE = 2048; // in bytes

  protected IFOFileType fileType;
  protected long bupLastSector;
  protected long ifoLastSector;
  protected IFOFileVersion version;
  protected long menuVOBStartSector;
  protected File file;
  
  
  
  public File getFile() {
	return file;
}

public void setFile(File file) {
	this.file = file;
}

public IFOFile(IFOFileType fileType)
   {
    this.fileType = fileType;
   }

  public IFOFileType getFileType()
   {
    return fileType;
   }

  public void setFileType(IFOFileType fileType)
   {
    this.fileType = fileType;
   }

  public long getBupLastSector()
   {
    return bupLastSector;
   }

  public void setBupLastSector(long bupLastSector)
   {
    this.bupLastSector = bupLastSector;
   }

  public long getIfoLastSector()
   {
    return ifoLastSector;
   }

  public void setIfoLastSector(long ifoLastSector)
   {
    this.ifoLastSector = ifoLastSector;
   }

  public IFOFileVersion getVersion()
   {
    return version;
   }

  public void setVersion(IFOFileVersion version)
   {
    this.version = version;
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
