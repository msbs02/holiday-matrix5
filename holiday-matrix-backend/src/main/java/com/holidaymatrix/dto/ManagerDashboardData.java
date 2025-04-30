package com.holidaymatrix.dto;

import java.util.List;

public class ManagerDashboardData {
    private String managerName;
    private String welcomeMessage;
    private List<LeaveRequest> leaveRequests;

    // Getters et setters
    public String getManagerName() {
        return managerName;
    }
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    public String getWelcomeMessage() {
        return welcomeMessage;
    }
    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }
    public List<LeaveRequest> getLeaveRequests() {
        return leaveRequests;
    }
    public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
        this.leaveRequests = leaveRequests;
    }

    // Classe interne pour une demande de congé
    public static class LeaveRequest {
        private String teamOrPosition;
        private String leaveCategory;
        private String employeeName;
        private String status; // ex : "Approuvé", "En attente", "Refusé"

        // Getters et setters
        public String getTeamOrPosition() {
            return teamOrPosition;
        }
        public void setTeamOrPosition(String teamOrPosition) {
            this.teamOrPosition = teamOrPosition;
        }
        public String getLeaveCategory() {
            return leaveCategory;
        }
        public void setLeaveCategory(String leaveCategory) {
            this.leaveCategory = leaveCategory;
        }
        public String getEmployeeName() {
            return employeeName;
        }
        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
    }
}
