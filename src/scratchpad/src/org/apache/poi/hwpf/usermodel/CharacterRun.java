/* ====================================================================
   Copyright 2002-2004   Apache Software Foundation

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */



package org.apache.poi.hwpf.usermodel;

import org.apache.poi.hwpf.model.types.CHPAbstractType;
import org.apache.poi.hwpf.model.StyleDescription;
import org.apache.poi.hwpf.model.CHPX;
import org.apache.poi.hwpf.model.StyleSheet;

import org.apache.poi.hwpf.sprm.SprmBuffer;
import org.apache.poi.hwpf.sprm.CharacterSprmCompressor;

/**
 * This class represents a run of text that share common properties.
 *
 * @author Ryan Ackley
 */
public class CharacterRun
  extends Range
  implements Cloneable
{
  public final static short SPRM_FRMARKDEL = (short)0x0800;
  public final static short SPRM_FRMARK = 0x0801;
  public final static short SPRM_FFLDVANISH = 0x0802;
  public final static short SPRM_PICLOCATION = 0x6A03;
  public final static short SPRM_IBSTRMARK = 0x4804;
  public final static short SPRM_DTTMRMARK = 0x6805;
  public final static short SPRM_FDATA = 0x0806;
  public final static short SPRM_SYMBOL = 0x6A09;
  public final static short SPRM_FOLE2 = 0x080A;
  public final static short SPRM_HIGHLIGHT = 0x2A0C;
  public final static short SPRM_OBJLOCATION = 0x680E;
  public final static short SPRM_ISTD = 0x4A30;
  public final static short SPRM_FBOLD = 0x0835;
  public final static short SPRM_FITALIC = 0x0836;
  public final static short SPRM_FSTRIKE = 0x0837;
  public final static short SPRM_FOUTLINE = 0x0838;
  public final static short SPRM_FSHADOW = 0x0839;
  public final static short SPRM_FSMALLCAPS = 0x083A;
  public final static short SPRM_FCAPS = 0x083B;
  public final static short SPRM_FVANISH = 0x083C;
  public final static short SPRM_KUL = 0x2A3E;
  public final static short SPRM_DXASPACE = (short)0x8840;
  public final static short SPRM_LID = 0x4A41;
  public final static short SPRM_ICO = 0x2A42;
  public final static short SPRM_HPS = 0x4A43;
  public final static short SPRM_HPSPOS = 0x4845;
  public final static short SPRM_ISS = 0x2A48;
  public final static short SPRM_HPSKERN = 0x484B;
  public final static short SPRM_YSRI = 0x484E;
  public final static short SPRM_RGFTCASCII = 0x4A4F;
  public final static short SPRM_RGFTCFAREAST = 0x4A50;
  public final static short SPRM_RGFTCNOTFAREAST = 0x4A51;
  public final static short SPRM_CHARSCALE = 0x4852;
  public final static short SPRM_FDSTRIKE = 0x2A53;
  public final static short SPRM_FIMPRINT = 0x0854;
  public final static short SPRM_FSPEC = 0x0855;
  public final static short SPRM_FOBJ = 0x0856;
  public final static short SPRM_PROPRMARK = (short)0xCA57;
  public final static short SPRM_FEMBOSS = 0x0858;
  public final static short SPRM_SFXTEXT = 0x2859;
  public final static short SPRM_DISPFLDRMARK = (short)0xCA62;
  public final static short SPRM_IBSTRMARKDEL = 0x4863;
  public final static short SPRM_DTTMRMARKDEL = 0x6864;
  public final static short SPRM_BRC = 0x6865;
  public final static short SPRM_SHD = 0x4866;
  public final static short SPRM_IDSIRMARKDEL = 0x4867;
  public final static short SPRM_CPG = 0x486B;
  public final static short SPRM_NONFELID = 0x486D;
  public final static short SPRM_FELID = 0x486E;
  public final static short SPRM_IDCTHINT = 0x286F;

  SprmBuffer _chpx;
  CharacterProperties _props;

  /**
   *
   * @param chpx The chpx this object is based on.
   * @param ss The stylesheet for the document this run belongs to.
   * @param istd The style index if this run's base style.
   * @param parent The parent range of this character run (usually a paragraph).
   */
  CharacterRun(CHPX chpx, StyleSheet ss, short istd, Range parent)
  {
    super(Math.max(parent._start, chpx.getStart()), Math.min(parent._end, chpx.getEnd()), parent);
    _props = chpx.getCharacterProperties(ss, istd);
    _chpx = chpx.getSprmBuf();
  }

  /**
   * Here for runtime type determination using a switch statement convenient.
   *
   * @return TYPE_CHARACTER
   */
  public int type()
  {
    return TYPE_CHARACTER;
  }

  public boolean isMarkedDeleted()
  {
    return _props.isFRMarkDel();
  }

  public void markDeleted(boolean mark)
  {
    _props.setFRMarkDel(mark);

    byte newVal = (byte)(mark ? 1 : 0);
    _chpx.addSprm(SPRM_FRMARKDEL, newVal);

  }

  public boolean isBold()
  {
    return _props.isFBold();
  }

  public void setBold(boolean bold)
  {
    _props.setFBold(bold);

    byte newVal = (byte)(bold ? 1 : 0);
    _chpx.addSprm(SPRM_FBOLD, newVal);

  }

  public boolean isItalic()
  {
    return _props.isFItalic();
  }

  public void setItalic(boolean italic)
  {
    _props.setFItalic(italic);

    byte newVal = (byte)(italic ? 1 : 0);
    _chpx.addSprm(SPRM_FITALIC, newVal);

  }

  public boolean isOutlined()
  {
    return _props.isFOutline();
  }

  public void setOutline(boolean outlined)
  {
    _props.setFOutline(outlined);

    byte newVal = (byte)(outlined ? 1 : 0);
    _chpx.addSprm(SPRM_FOUTLINE, newVal);

  }

  public boolean isFldVanished()
  {
    return _props.isFFldVanish();
  }

  public void setFldVanish(boolean fldVanish)
  {
    _props.setFFldVanish(fldVanish);

    byte newVal = (byte)(fldVanish ? 1 : 0);
    _chpx.addSprm(SPRM_FFLDVANISH, newVal);

  }

  public boolean isSmallCaps()
  {
    return _props.isFSmallCaps();
  }

  public void setSmallCaps(boolean smallCaps)
  {
    _props.setFSmallCaps(smallCaps);

    byte newVal = (byte)(smallCaps ? 1 : 0);
    _chpx.addSprm(SPRM_FSMALLCAPS, newVal);

  }

  public boolean isCapitalized()
  {
    return _props.isFCaps();
  }

  public void setCapitalized(boolean caps)
  {
    _props.setFCaps(caps);

    byte newVal = (byte)(caps ? 1 : 0);
    _chpx.addSprm(SPRM_FCAPS, newVal);

  }

  public boolean isVanished()
  {
    return _props.isFVanish();
  }

  public void setVanished(boolean vanish)
  {
    _props.setFVanish(vanish);

    byte newVal = (byte)(vanish ? 1 : 0);
    _chpx.addSprm(SPRM_FVANISH, newVal);

  }

  public boolean isMarkedInserted()
  {
    return _props.isFRMark();
  }

  public void markInserted(boolean mark)
  {
    _props.setFRMark(mark);

    byte newVal = (byte)(mark ? 1 : 0);
    _chpx.addSprm(SPRM_FRMARK, newVal);

  }

  public boolean isStrikeThrough()
  {
    return _props.isFStrike();
  }

  public void strikeThrough(boolean strike)
  {
    _props.setFStrike(strike);

    byte newVal = (byte)(strike ? 1 : 0);
    _chpx.addSprm(SPRM_FSTRIKE, newVal);

  }

  public boolean isShadowed()
  {
    return _props.isFShadow();
  }

  public void setShadow(boolean shadow)
  {
    _props.setFShadow(shadow);

    byte newVal = (byte)(shadow ? 1 : 0);
    _chpx.addSprm(SPRM_FSHADOW, newVal);

  }

  public boolean isEmbossed()
  {
    return _props.isFEmboss();
  }

  public void setEmbossed(boolean emboss)
  {
    _props.setFEmboss(emboss);

    byte newVal = (byte)(emboss ? 1 : 0);
    _chpx.addSprm(SPRM_FEMBOSS, newVal);

  }

  public boolean isImprinted()
  {
    return _props.isFImprint();
  }

  public void setImprinted(boolean imprint)
  {
    _props.setFImprint(imprint);

    byte newVal = (byte)(imprint ? 1 : 0);
    _chpx.addSprm(SPRM_FIMPRINT, newVal);

  }

  public boolean isDoubleStrikeThrough()
  {
    return _props.isFDStrike();
  }

  public void setDoubleStrikethrough(boolean dstrike)
  {
    _props.setFDStrike(dstrike);

    byte newVal = (byte)(dstrike ? 1 : 0);
    _chpx.addSprm(SPRM_FDSTRIKE, newVal);

  }

  public void setFtcAscii(int ftcAscii)
  {
    _props.setFtcAscii(ftcAscii);

    _chpx.addSprm(SPRM_RGFTCASCII, (short)ftcAscii);

  }

  public void setFtcFE(int ftcFE)
  {
    _props.setFtcFE(ftcFE);

    _chpx.addSprm(SPRM_RGFTCFAREAST, (short)ftcFE);

  }

  public void setFtcOther(int ftcOther)
  {
    _props.setFtcOther(ftcOther);

    _chpx.addSprm(SPRM_RGFTCNOTFAREAST, (short)ftcOther);

  }

  public int getFontSize()
  {
    return _props.getHps();
  }

  public void setFontSize(int halfPoints)
  {
    _props.setHps(halfPoints);

    _chpx.addSprm(SPRM_HPS, (short)halfPoints);

  }

  public int getCharacterSpacing()
  {
    return _props.getDxaSpace();
  }

  public void setCharacterSpacing(int twips)
  {
    _props.setDxaSpace(twips);

    _chpx.addSprm(SPRM_DXASPACE, twips);

  }

  public short getSubSuperScriptIndex()
  {
    return _props.getIss();
  }

  public void setSubSuperScriptIndex(short iss)
  {
    _props.setDxaSpace(iss);

    _chpx.addSprm(SPRM_DXASPACE, iss);

  }

  public int getUnderlineCode()
  {
    return _props.getKul();
  }

  public void setUnderlineCode(int kul)
  {
    _props.setKul((byte)kul);
    _chpx.addSprm(SPRM_KUL, (byte)kul);
  }

  public int getColor()
  {
    return _props.getIco();
  }

  public void setColor(int color)
  {
    _props.setIco((byte)color);
    _chpx.addSprm(SPRM_ICO, (byte)color);
  }

  public int getVerticalOffset()
  {
    return _props.getHpsPos();
  }

  public void setVerticalOffset(int hpsPos)
  {
    _props.setHpsPos(hpsPos);
    _chpx.addSprm(SPRM_HPSPOS, (byte)hpsPos);
  }

  public int getKerning()
  {
    return _props.getHpsKern();
  }

  public void setKerning(int kern)
  {
    _props.setHpsKern(kern);
    _chpx.addSprm(SPRM_HPSKERN, (short)kern);
  }

  public boolean isHighlighted()
  {
    return _props.isFHighlight();
  }

  public void setHighlighted(byte color)
  {
    _props.setFHighlight(true);
    _props.setIcoHighlight(color);
    _chpx.addSprm(SPRM_HIGHLIGHT, color);
  }

  public String getFontName()
  {
    return _doc.getFontTable().getMainFont(_props.getFtcAscii());
  }


  /**
  * Get the ico24 field for the CHP record.
  */
  public int getIco24()
  {
    return _props.getIco24();
  }

  /**
   * Set the ico24 field for the CHP record.
   */
  public void setIco24(int colour24)
  {
    _props.setIco24(colour24);
  }

  /**
   * Used to create a deep copy of this object.
   *
   * @return A deep copy.
   * @throws CloneNotSupportedException never
   */
  public Object clone()
    throws CloneNotSupportedException
  {
    CharacterRun cp = (CharacterRun)super.clone();
    cp._props.setDttmRMark((DateAndTime)_props.getDttmRMark().clone());
    cp._props.setDttmRMarkDel((DateAndTime)_props.getDttmRMarkDel().clone());
    cp._props.setDttmPropRMark((DateAndTime)_props.getDttmPropRMark().clone());
    cp._props.setDttmDispFldRMark((DateAndTime)_props.getDttmDispFldRMark().
                                  clone());
    cp._props.setXstDispFldRMark((byte[])_props.getXstDispFldRMark().clone());
    cp._props.setShd((ShadingDescriptor)_props.getShd().clone());

    return cp;
  }

}
