package com.compressor.extractor.zipers.utilitesWorkFile;

import com.compressor.extractor.zipers.fileWork.FileType;
import com.compressor.extractor.zipers.fileWork.FileRelatedInformation;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorStreamFactory;


public class UtilitiesFileWorker {
    private static final String[] ARCHIVE_ARRAY = {"rar", ArchiveStreamFactory.ZIP, ArchiveStreamFactory.SEVEN_Z, "bz2", CompressorStreamFactory.BZIP2, "tbz2", "tbz", CompressorStreamFactory.GZIP, "gzip", "tgz", ArchiveStreamFactory.TAR, CompressorStreamFactory.XZ, "txz"};

    public static FileRelatedInformation getFileInfoFromPath(String str) {
        FileRelatedInformation file_Related_Information = new FileRelatedInformation();
        File file = new File(str);
        file_Related_Information.setFileName(file.getName());
        file_Related_Information.setFilePath(file.getAbsolutePath());
        file_Related_Information.setFileType(FileType.fileunknown);
        file_Related_Information.setFileSize(Integer.parseInt(String.valueOf(file.length() / 1024)));
        file_Related_Information.setFileDate(DateFormat.getDateInstance(2).format(new Date(file.lastModified())));
        if (file.isDirectory()) {
            file_Related_Information.setFolder(true);
            file_Related_Information.setFileType(FileType.folderEmpty);
            String[] list = file.list();
            if (list != null && list.length > 0) {
                file_Related_Information.setSubCount(list.length);
                file_Related_Information.setFileType(FileType.folderFull);
            }
        } else {
            file_Related_Information.setFileLength(file.length());
            if (isArchive(file)) {
                file_Related_Information.setFileType(FileType.filearchive);
            }
        }
        return file_Related_Information;
    }

    private static boolean isArchive(File file) {
        if (file != null) {
            String name = file.getName();
            if (name.isEmpty()) {
                String lowerCase = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
                for (String equals : ARCHIVE_ARRAY) {
                    if (lowerCase.equals(equals)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static ArrayList<FileRelatedInformation> getInfoListFromPath(String str) {
        File[] listFiles;
        ArrayList<FileRelatedInformation> arrayList = new ArrayList<>();
        File file = new File(str);
        if (file.exists() && file.isDirectory() && file.canRead() && (listFiles = file.listFiles()) != null) {
            Arrays.sort(listFiles, new FileComparator());
            for (File path : listFiles) {
                arrayList.add(getFileInfoFromPath(path.getPath()));
            }
        }
        return arrayList;
    }

    private static class FileComparator implements Comparator<File> {
        private FileComparator() {
        }

        public int compare(File file, File file2) {
            int xxx = UtilitiesFileWorker.getFileScore(file2) - UtilitiesFileWorker.getFileScore(file);
            return xxx == 0 ? file.getName().compareToIgnoreCase(file2.getName()) : xxx;
        }
    }
    public static int getFileScore(File file) {
        return (file.isHidden() ^ true) ? 1 : 0;
    }
}
