package com.example.dashboard.dto;

public class SummaryStats {
    private long totalUsers;
    private long totalProperties;
    private long totalBookings;
    private long totalTransactions;

    public SummaryStats() {}
    public SummaryStats(long totalUsers,long totalProperties,long totalBookings,long totalTransactions){
        this.totalUsers=totalUsers;this.totalProperties=totalProperties;this.totalBookings=totalBookings;this.totalTransactions=totalTransactions;}
    public long getTotalUsers(){return totalUsers;} public void setTotalUsers(long totalUsers){this.totalUsers=totalUsers;}
    public long getTotalProperties(){return totalProperties;} public void setTotalProperties(long totalProperties){this.totalProperties=totalProperties;}
    public long getTotalBookings(){return totalBookings;} public void setTotalBookings(long totalBookings){this.totalBookings=totalBookings;}
    public long getTotalTransactions(){return totalTransactions;} public void setTotalTransactions(long totalTransactions){this.totalTransactions=totalTransactions;}
}
