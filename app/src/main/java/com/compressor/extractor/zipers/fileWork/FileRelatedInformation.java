package com.compressor.extractor.zipers.fileWork;

public class FileRelatedInformation {
    private String datefile;
    private long fileLength;
    private String fileName;
    private String filePath;
    private FileType fileType;
    private boolean isFolder;
    private boolean selected;
    private int sizefile;
    private int subCount;

    public FileRelatedInformation(String str, String str2, boolean z, FileType fileType2) {
        this.fileName = str;
        this.filePath = str2;
        this.isFolder = z;
        this.fileType = fileType2;
    }

    public FileRelatedInformation() {
    }

    public long getFileLength() {
        return this.fileLength;
    }

    public void setFileLength(long j) {
        this.fileLength = j;
    }

    public int getSubCount() {
        return this.subCount;
    }

    public void setSubCount(int i) {
        this.subCount = i;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public int getFileSize() {
        return this.sizefile;
    }

    public void setFileSize(int i) {
        this.sizefile = i;
    }

    public String getFileDate() {
        return this.datefile;
    }

    public void setFileDate(String str) {
        this.datefile = str;
    }

    public boolean isFolder() {
        return this.isFolder;
    }

    public void setFolder(boolean z) {
        this.isFolder = z;
    }

    public FileType getFileType() {
        return this.fileType;
    }

    public void setFileType(FileType fileType2) {
        this.fileType = fileType2;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }
}
