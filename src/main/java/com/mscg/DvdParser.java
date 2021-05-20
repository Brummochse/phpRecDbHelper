package com.mscg;

import com.mscg.ifo.VMG_IFOFile;
import com.mscg.ifo.VTS_IFOFile;
import com.mscg.ifo.chains.PlaybackTime;
import com.mscg.ifo.chains.ProgramChain;
import com.mscg.ifo.parser.IFOFileParser;
import com.mscg.ifo.title.vmg.TitleInfo;
import com.mscg.test.IFOParserTest.IFOFileFilter;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DvdParser {

	public static ArrayList<Title> getDvdTitles(File isoMountedPath) throws Exception {
		ArrayList<Title> dvdTitles = new ArrayList<>();

		File videoFolder = getVideoFolder(isoMountedPath);

		IFOFileFilter filter = new IFOFileFilter();
		File ifoFiles[] = videoFolder.listFiles(filter);

		File videoTSFile = filter.getVideoTSFile();
		VMG_IFOFile videoTS = (VMG_IFOFile) IFOFileParser.parseIFOFile(new RandomAccessFile(videoTSFile, "r"), true);
		Map<Integer, VTS_IFOFile> vtsToIfo = new HashMap<>();
		for (File ifoFile : ifoFiles) {
			Integer vtsIndex = Integer.parseInt(ifoFile.getName().substring(4, 6));
			VTS_IFOFile parseIFOFile = (VTS_IFOFile) IFOFileParser.parseIFOFile(new RandomAccessFile(ifoFile, "r"), true);
			parseIFOFile.setFile(ifoFile);
			vtsToIfo.put(vtsIndex, parseIFOFile);
		}

		for (TitleInfo titleInfo : videoTS.getTitles()) {
			VTS_IFOFile vtsFile = vtsToIfo.get((int) titleInfo.getVideoTitleSetNumber());

			ProgramChain programChain = vtsFile.getProgramChainsTable().getProgramChains().get(titleInfo.getTitleNumberWithinVTS() - 1);
			PlaybackTime playbackTime = programChain.getPlaybackTime();

			Title title = new Title(titleInfo, playbackTime);
			dvdTitles.add(title);
		}

		return dvdTitles;
	}

	public static class Title {
		private final PlaybackTime playbackTime;
		private final TitleInfo titleInfo;

		public Title(TitleInfo titleInfo, PlaybackTime playbackTime) {
			this.titleInfo=titleInfo;
			this.playbackTime=playbackTime;
		}

		@Override
		public String toString() {
			return "Title{" +
					"playbackTime=" + playbackTime +
					", titleInfo=" + titleInfo +
					'}';
		}

		public PlaybackTime getPlaybackTime() {
			return playbackTime;
		}

		public TitleInfo getTitleInfo() {
			return titleInfo;
		}
	}

	private static File getVideoFolder(File basePath) throws Exception {
		File isoVideoFolder = new File(basePath, "VIDEO_TS");
		if (!isoVideoFolder.exists()) {
			isoVideoFolder = new File(basePath, "video_ts");
			if (!isoVideoFolder.exists())
				throw new Exception("Unable to find DVD video_ts folder");
		}

		return isoVideoFolder;
	}

}
