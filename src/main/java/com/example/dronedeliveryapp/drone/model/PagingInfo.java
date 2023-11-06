package com.example.dronedeliveryapp.drone.model;


import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class PagingInfo implements Serializable {
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 200;
    private int pageSize;
    private int currentPage;
    private int totalRecord;
    private int totalPages;
    private int limitMin;
    private int limitMax;

    public PagingInfo() {
        this(DEFAULT_PAGE_SIZE, 1, 0);
    }

    public PagingInfo(int pageSize, int currentPage, int totalRecord) {
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.totalPages = computeTotalPages(pageSize, totalRecord);
        this.currentPage = currentPage < 1 ? 1 : Math.min(Math.abs(currentPage), totalPages);

        this.limitMin = Math.max((this.currentPage - 5), 1);
        this.limitMax = (Math.min((this.currentPage) + 5, totalPages));
    }

    public PagingInfo(int pageSize, int currentPage) {
        this(pageSize, currentPage, 0);
    }

    public PagingInfo(int currentPage) {
        this(DEFAULT_PAGE_SIZE, currentPage, 0);
    }

    public PagingInfo(PagingInfoBuilder pagingInfoBuilder) {
        this(pagingInfoBuilder.pageSize, pagingInfoBuilder.currentPage, pagingInfoBuilder.totalRecord);
    }

    public static PagingInfoBuilder builder() {
        return new PagingInfoBuilder();
    }

    private int computeTotalPages(int pageSize, int totalRecord) {
        if (pageSize < 1) throw new IllegalStateException("Page size must be greater than zero");
        int totalPagesLocal = (int) Math.ceil((double) totalRecord / pageSize);
        return Math.max(totalPagesLocal, 1);
    }

    public int getPageSize() {
        if ((this.pageSize <= 15)) {
            setPageSize(15);
        }
        if ((this.pageSize > MAX_PAGE_SIZE)) {
            setPageSize(MAX_PAGE_SIZE);
        }
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return (this.currentPage);
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return Math.max(this.totalPages, 1);
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = Math.max(totalPages, 1);
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getLimitMin() {
        return limitMin;
    }

    public void setLimitMin(int limitMin) {
        this.limitMin = limitMin;
    }

    public int getLimitMax() {
        return limitMax;
    }

    public void setLimitMax(int limitMax) {
        this.limitMax = limitMax;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PagingInfo that)) return false;
        return pageSize == that.pageSize && currentPage == that.currentPage && totalRecord == that.totalRecord && totalPages == that.totalPages && limitMin == that.limitMin && limitMax == that.limitMax;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageSize, currentPage, totalRecord, totalPages, limitMin, limitMax);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PagingInfo.class.getSimpleName() + "[", "]")
                .add("pageSize=" + pageSize)
                .add("currentPage=" + currentPage)
                .add("totalRecord=" + totalRecord)
                .add("totalPages=" + totalPages)
                .add("limitMin=" + limitMin)
                .add("limitMax=" + limitMax)
                .toString();
    }

    public static class PagingInfoBuilder {

        private int pageSize;
        private int totalRecord;
        private int currentPage;

        public PagingInfoBuilder totalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
            return this;
        }

        public PagingInfoBuilder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public PagingInfoBuilder currentPage(int currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public PagingInfo build() {
            return new PagingInfo(this);
        }
    }

}
