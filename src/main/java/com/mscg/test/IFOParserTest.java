package com.mscg.test;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;

public class IFOParserTest {

//	private static final Log LOG = LogFactory.getLog(IFOParserTest.class);
//
//	public static void main(String args[]) {
//		RandomAccessFile raf = null;
//		try {
//			// -dvd-device ${input} dvd://${track} -alang it,en -dumpstream
//			// -dumpfile ${output}
//			File videoFolder = getVideoTSFile(args[0]);
//			File videoTSFile = getVideoTSIFOFile(videoFolder);
//			raf = new RandomAccessFile(videoTSFile, "r");
//			IFOFile ifo = IFOFileParser.parseIFOFile(raf, true);
//			LOG.info(videoTSFile.getName() + " => " + objectToString(ifo, ""));
//
//			raf.close();
//
//			for (int i = 0, l = ((VMG_IFOFile) ifo).getTitleSets(); i < l; i++) {
//				System.out.println("==========================================================");
//				File vtsFile = getVTSIFOFile(videoFolder, i + 1);
//				raf = new RandomAccessFile(vtsFile, "r");
//				ifo = IFOFileParser.parseIFOFile(raf, true);
//				LOG.info(vtsFile.getName() + " => " + objectToString(ifo, ""));
//
//				raf.close();
//			}
//
//			File dvdFolder = new File(args[0]);
//			parseDVDStructure(dvdFolder);
//		} catch (Exception e) {
//			LOG.error("An error occurred", e);
//		} finally {
//			try {
//				raf.close();
//			} catch (Exception ignored) {
//			}
//		}
//	}
//
//	private static File getVideoTSFile(String folderName) throws FileNotFoundException {
//		File folder = new File(folderName);
//		if (!folder.exists() || !folder.isDirectory())
//			throw new FileNotFoundException("Folder " + folder.getAbsolutePath() + " doesn't exists");
//		File videoFolder = new File(folder, "VIDEO_TS");
//		if (!videoFolder.exists())
//			videoFolder = new File(folder, "video_ts");
//		if (!videoFolder.exists())
//			throw new FileNotFoundException("Folder " + folder.getAbsolutePath() + " doesn't contain a video folder");
//
//		return videoFolder;
//	}
//
//	private static File getVideoTSIFOFile(File videoFolder) throws FileNotFoundException {
//		File videoTS = new File(videoFolder, "video_ts.ifo");
//		if (!videoTS.exists())
//			videoTS = new File(videoFolder, "VIDEO_TS.IFO");
//		if (!videoTS.exists())
//			throw new FileNotFoundException("Folder " + videoFolder.getAbsolutePath() + " doesn't contain a video_ts.ifo file");
//		return videoTS;
//	}
//
//	private static File getVTSIFOFile(File videoFolder, int index) throws FileNotFoundException {
//		String filename = String.format("vts_%02d_0.ifo", index);
//		File vts = new File(videoFolder, filename);
//		if (!vts.exists())
//			vts = new File(videoFolder, filename.toUpperCase());
//		if (!vts.exists())
//			throw new FileNotFoundException("Folder " + videoFolder.getAbsolutePath() + " doesn't contain a " + filename + " file");
//		return vts;
//	}
//
//	private static String objectToString(Object obj, String prefix) throws Exception {
//		StringBuilder sb = new StringBuilder();
//		Method methods[] = obj.getClass().getMethods();
//
//		Arrays.sort(methods, new Comparator<Method>() {
//			public int compare(Method m1, Method m2) {
//				return m1.getName().compareTo(m2.getName());
//			}
//		});
//
//		sb.append("{\n");
//		for (Method method : methods) {
//			if (!"getClass".equals(method.getName()) && (method.getName().startsWith("get") || method.getName().startsWith("is"))) {
//				sb.append(prefix).append("  ");
//				Object value = method.invoke(obj);
//				int prefixLength = 3;
//				if (method.getName().startsWith("is"))
//					prefixLength = 2;
//				String valueName = method.getName().substring(prefixLength);
//				valueName = valueName.substring(0, 1).toLowerCase() + valueName.substring(1);
//				sb.append(valueName + " = ");
//
//				if (value instanceof byte[])
//					sb.append(Arrays.toString((byte[]) value));
//				else if (value instanceof Collection) {
//					sb.append("[(").append(((Collection) value).size()).append(")\n");
//					for (Object subvalue : (Collection) value) {
//						sb.append(prefix).append("    ");
//						if ((subvalue instanceof ProgramChain) || (subvalue instanceof CellPlaybackInformation))
//							sb.append(objectToString(subvalue, prefix + "    "));
//						else
//							sb.append(subvalue.toString());
//						sb.append(",\n");
//					}
//					sb.append(prefix).append("  ").append("]");
//				} else if ((value instanceof TableOfTitles) || (value instanceof ProgramChainsTable) || (value instanceof CellCategory))
//					sb.append(objectToString(value, prefix + "  "));
//				else
//					sb.append(value);
//				sb.append(",\n");
//			}
//		}
//		sb.append(prefix).append("}");
//		return sb.toString();
//	}
//
//	protected static long parseDVDStructure(File isoMountedPath) throws Exception {
//		long mainTrack = 1l;
//
//		File videoFolder = getVideoFolder(isoMountedPath);
//
//		IFOFileFilter filter = new IFOFileFilter();
//		File ifoFiles[] = videoFolder.listFiles(filter);
//
//		File videoTSFile = filter.getVideoTSFile();
//		VMG_IFOFile videoTS = (VMG_IFOFile) IFOFileParser.parseIFOFile(new RandomAccessFile(videoTSFile, "r"), true);
//		Map<Integer, VTS_IFOFile> vtsToIfo = new HashMap<Integer, VTS_IFOFile>();
//		for (File ifoFile : ifoFiles) {
//			Integer vtsIndex = Integer.parseInt(ifoFile.getName().substring(4, 6));
//			vtsToIfo.put(vtsIndex, (VTS_IFOFile) IFOFileParser.parseIFOFile(new RandomAccessFile(ifoFile, "r"), true));
//		}
//
//		PlaybackTime maxPlaybackTime = null;
//		int index = 1;
//		for (TitleInfo titleInfo : videoTS.getTitles()) {
//			VTS_IFOFile vtsFile = vtsToIfo.get((int) titleInfo.getVideoTitleSetNumber());
//
//			ProgramChain programChain = vtsFile.getProgramChainsTable().getProgramChains().get(titleInfo.getTitleNumberWithinVTS() - 1);
//			LOG.info("Title " + index + " has length: " + programChain.getPlaybackTime());
//			if (maxPlaybackTime == null || maxPlaybackTime.compareTo(programChain.getPlaybackTime()) < 0) {
//				maxPlaybackTime = programChain.getPlaybackTime();
//				mainTrack = index;
//				LOG.info("Track " + mainTrack + " has playback time " + maxPlaybackTime.toString());
//			}
//
//			index++;
//		}
//
//		return mainTrack;
//	}
//
//	private static File getVideoFolder(File basePath) throws Exception {
//		File isoVideoFolder = new File(basePath, "VIDEO_TS");
//		if (!isoVideoFolder.exists()) {
//			isoVideoFolder = new File(basePath, "video_ts");
//			if (!isoVideoFolder.exists())
//				throw new Exception("Unable to find DVD video_ts folder");
//		}
//
//		return isoVideoFolder;
//	}
//
	public static class IFOFileFilter implements FileFilter {
		private static final String VIDEO_TS_FILE_NAME = "video_ts.ifo";
		private static final String IFO_FILE_EXTENSION = "ifo";

		private File videoTSFile;

		public File getVideoTSFile() {
			return videoTSFile;
		}

		public boolean accept(File file) {
			if (VIDEO_TS_FILE_NAME.equals(file.getName().toLowerCase())) {
				videoTSFile = file;
				return false;
			}
			return file.isFile() && IFO_FILE_EXTENSION.equals(FilenameUtils.getExtension(file.getName().toLowerCase()));
		}
	}

}
