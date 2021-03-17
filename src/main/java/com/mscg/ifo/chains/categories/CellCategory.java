package com.mscg.ifo.chains.categories;

import java.io.IOException;

import com.mscg.ifo.parser.exception.IFOFileException;

public class CellCategory
 {

  protected CellType cellType;
  protected BlockType blockType;
  protected boolean seamlessMultiplex;
  protected boolean interleaved;
  protected boolean srcDiscontinuity;
  protected boolean seamlessAngle;
  protected boolean vobuStillMode;
  protected boolean restricted;
  protected byte applicationCellType;
  protected byte cellStillTime;
  protected byte cellCommand;

  public CellType getCellType()
   {
    return cellType;
   }

  public void setCellType(CellType cellType)
   {
    this.cellType = cellType;
   }

  public BlockType getBlockType()
   {
    return blockType;
   }

  public void setBlockType(BlockType blockType)
   {
    this.blockType = blockType;
   }

  public boolean isSeamlessMultiplex()
   {
    return seamlessMultiplex;
   }

  public void setSeamlessMultiplex(boolean seamlessMultiplex)
   {
    this.seamlessMultiplex = seamlessMultiplex;
   }

  public boolean isInterleaved()
   {
    return interleaved;
   }

  public void setInterleaved(boolean interleaved)
   {
    this.interleaved = interleaved;
   }

  public boolean isSrcDiscontinuity()
   {
    return srcDiscontinuity;
   }

  public void setSrcDiscontinuity(boolean srcDiscontinuity)
   {
    this.srcDiscontinuity = srcDiscontinuity;
   }

  public boolean isSeamlessAngle()
   {
    return seamlessAngle;
   }

  public void setSeamlessAngle(boolean seamlessAngle)
   {
    this.seamlessAngle = seamlessAngle;
   }

  public boolean isVobuStillMode()
   {
    return vobuStillMode;
   }

  public void setVobuStillMode(boolean vobuStillMode)
   {
    this.vobuStillMode = vobuStillMode;
   }

  public boolean isRestricted()
   {
    return restricted;
   }

  public void setRestricted(boolean restricted)
   {
    this.restricted = restricted;
   }

  public byte getApplicationCellType()
   {
    return applicationCellType;
   }

  public void setApplicationCellType(byte applicationCellType)
   {
    this.applicationCellType = applicationCellType;
   }

  public byte getCellStillTime()
   {
    return cellStillTime;
   }

  public void setCellStillTime(byte cellStillTime)
   {
    this.cellStillTime = cellStillTime;
   }

  public byte getCellCommand()
   {
    return cellCommand;
   }

  public void setCellCommand(byte cellCommand)
   {
    this.cellCommand = cellCommand;
   }

  public void parseByteArray(byte cellCategoryBytes[]) throws IOException, IFOFileException
   {
    if(cellCategoryBytes.length != 4)
     throw new IOException("Invalid length (" + cellCategoryBytes.length + ") of cell category bytes array (4 bytes are required)");

    cellType = CellType.getCellTypeByValue((cellCategoryBytes[0] & 0xC0) >> 6);
    blockType = BlockType.getBlockTypeByValue((cellCategoryBytes[0] & 0x30) >> 3);
    seamlessMultiplex = (cellCategoryBytes[0] & 0x08) != 0;
    interleaved = (cellCategoryBytes[0] & 0x04) != 0;
    srcDiscontinuity = (cellCategoryBytes[0] & 0x02) != 0;
    seamlessAngle = (cellCategoryBytes[0] & 0x01) != 0;

    vobuStillMode = (cellCategoryBytes[1] & 0x40) != 0;
    restricted = (cellCategoryBytes[1] & 0x20) != 0;
    applicationCellType = (byte)(cellCategoryBytes[1] & 0x1F);

    cellStillTime = cellCategoryBytes[2];
    cellCommand = cellCategoryBytes[3];
   }
 }
