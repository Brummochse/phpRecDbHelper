package com.mscg.ifo;

// http://dvd.sourceforge.net/dvdinfo/ifo.html#vidatt
public class VideoAttributes {

	private byte codingMode;
	private byte standard;
	private byte aspect;
	private byte automaticPanScan;
	private byte automaticLetterbox;

	private byte resolution;
	private byte letterboxed;

	public VideoAttributes(byte byte0, byte byte1) {
		codingMode = (byte) ((byte0 & 0b11000000) >> 6);
		standard = (byte) ((byte0 & 0b00110000) >> 4);
		aspect = (byte) ((byte0 & 0b00001100) >> 2);
		automaticPanScan = (byte) ((byte0 & 0b00000010) >> 1);
		automaticLetterbox = (byte) (byte0 & 0b00000001);

		resolution = (byte) ((byte1 & 0b00110000) >> 4);
		letterboxed = (byte) ((byte1 & 0b00000100) >> 2);
	}

	public byte getCodingMode() {
		return codingMode;
	}

	public byte getStandard() {
		return standard;
	}

	public byte getAspect() {
		return aspect;
	}

	public byte getAutomaticPanScanByte() {
		return automaticPanScan;
	}

	public byte getAutomaticLetterboxByte() {
		return automaticLetterbox;
	}

	public byte getResolution() {
		return resolution;
	}

	public byte getLetterboxedByte() {
		return letterboxed;
	}

	public String getCodingModeString() {
		switch (codingMode) {
			case 0: return "Mpeg-1";
			case 1:	return "Mpeg-2";
			default: throw new RuntimeException("unknown coding mode");
		}
	}

	public String getStandardString() {
		switch (standard) {
			case 0: return "NTSC";
			case 1:	return "PAL";
			default: throw new RuntimeException("unknown standard");
		}
	}

	public String getAspectString() {
		switch (aspect) {
			case 0: return "4:3";
			case 1: 
			case 2: return "reserved";
			case 3:	return "16:9";
			default: throw new RuntimeException("unknown aspect");
		}
	}


	public String getResolutionString() {
		if (standard==0) { //->NTSC
			switch (resolution) {
				case 0: return "720x480";
				case 1:	return "704x480";
				case 2:	return "352x480";
				case 3:	return "352x240";

			}
		} else { //==1 ->PAL
			switch (resolution) {
				case 0: return "720x576";
				case 1:	return "704x576";
				case 2:	return "352x576";
				case 3:	return "352x288";
			}
		}
		throw new RuntimeException("unknown resolution");
	}


}
