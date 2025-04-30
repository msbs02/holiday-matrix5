package com.holidaymatrix.dto;

import java.util.List;

public class EmployeeDashboardData {
    private String companyName;
    private CalendarData calendar;
    private List<Notification> notifications;
    private List<NewsItem> news;

    // Getters et setters

    // Classe interne pour les données du calendrier
    public static class CalendarData {
        private String month;
        private int year;
        private List<Integer> holidayDates; // par exemple, une liste de jours fériés
        private int highlightedDate; // ex : 26 pour le 26 février 2025

        // Getters et setters
        public String getMonth() {
            return month;
        }
        public void setMonth(String month) {
            this.month = month;
        }
        public int getYear() {
            return year;
        }
        public void setYear(int year) {
            this.year = year;
        }
        public List<Integer> getHolidayDates() {
            return holidayDates;
        }
        public void setHolidayDates(List<Integer> holidayDates) {
            this.holidayDates = holidayDates;
        }
        public int getHighlightedDate() {
            return highlightedDate;
        }
        public void setHighlightedDate(int highlightedDate) {
            this.highlightedDate = highlightedDate;
        }
    }

    // Classe interne pour les notifications
    public static class Notification {
        private String message;
        private boolean read;

        // Getters et setters
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public boolean isRead() {
            return read;
        }
        public void setRead(boolean read) {
            this.read = read;
        }
    }

    // Classe interne pour les news
    public static class NewsItem {
        private String title;
        private String content;

        // Getters et setters
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
    }

    // Getters et setters pour EmployeeDashboardData
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public CalendarData getCalendar() {
        return calendar;
    }
    public void setCalendar(CalendarData calendar) {
        this.calendar = calendar;
    }
    public List<Notification> getNotifications() {
        return notifications;
    }
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
    public List<NewsItem> getNews() {
        return news;
    }
    public void setNews(List<NewsItem> news) {
        this.news = news;
    }
}
