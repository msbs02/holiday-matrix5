/*import { Component } from '@angular/core';

@Component({
  selector: 'app-direction-dashboard',
  imports: [],
  templateUrl: './direction-dashboard.component.html',
  styleUrl: './direction-dashboard.component.css'
})
export class DirectionDashboardComponent {

}*/
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { MatrixService } from '../../services/matrix.service';
import { HolidayService } from '../../services/holiday.service';
import { NotificationService } from '../../services/notification.service';
import { UserService } from '../../services/user.service';
import { CommentService } from '../../services/comment.service';
import { AnnualMatrix, ValidationStatus } from '../../models/annual-matrix.model';
import { Holiday, HolidayStatus } from '../../models/holiday.model';
import { Notification } from '../../models/notification.model';
import { User, UserDetails } from '../../models/user.model';
import { Comment } from '../../models/comment.model';

@Component({
  selector: 'app-director-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './direction-dashboard.component.html',
  styleUrls: ['./direction-dashboard.component.css']
})
export class DirectorDashboardComponent implements OnInit {
  // Données utilisateur
  userDetails?: UserDetails;

  // Onglets
  activeTab: 'matrices' | 'holidays' | 'users' = 'matrices';

  // Données des matrices
  matrices: AnnualMatrix[] = [];
  selectedYear: number = new Date().getFullYear();
  years: number[] = [];

  // Données des congés
  upcomingHolidays: Holiday[] = [];
  calendarDays: any[] = [];
  weekDays: string[] = ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'];
  currentMonth: string = '';
  currentYear: number = 0;
  currentDate: Date = new Date();

  // Données des utilisateurs
  users: User[] = [];
  filteredUsers: User[] = [];
  selectedRole: string = 'all';
  searchQuery: string = '';

  // Modals
  showNotificationsModal: boolean = false;
  notifications: Notification[] = [];

  showMatrixDetailsModal: boolean = false;
  selectedMatrix?: AnnualMatrix;
  matrixComments: Comment[] = [];
  newComment: string = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    private matrixService: MatrixService,
    private holidayService: HolidayService,
    private notificationService: NotificationService,
    private userService: UserService,
    private commentService: CommentService
  ) {
    // Générer les années pour le filtre
    const currentYear = new Date().getFullYear();
    for (let i = currentYear - 2; i <= currentYear + 2; i++) {
      this.years.push(i);
    }

    // Initialiser le calendrier
    this.updateCalendar(new Date());
  }

  ngOnInit(): void {
    this.loadUserDetails();
    this.loadMatrices();
    this.loadUpcomingHolidays();
    this.loadUsers();
  }

  // Chargement des données
  loadUserDetails(): void {
    this.userService.getCurrentUserDetails().subscribe({
      next: (details) => {
        this.userDetails = details;
      },
      error: (error) => console.error('Error loading user details', error)
    });
  }

  loadMatrices(): void {
    this.matrixService.getYearMatrices(this.selectedYear).subscribe({
      next: (matrices) => {
        this.matrices = matrices;
      },
      error: (error) => console.error('Error loading matrices', error)
    });
  }

  loadUpcomingHolidays(): void {
    this.holidayService.getTeamHolidays().subscribe({
      next: (holidays) => {
        this.upcomingHolidays = holidays.filter(h => new Date(h.startDate) >= new Date());
      },
      error: (error) => console.error('Error loading holidays', error)
    });
  }

  loadUsers(): void {
    if (this.selectedRole === 'all') {
      this.userService.getAllUsers().subscribe({
        next: (users) => {
          this.users = users;
          this.filterUsers();
        },
        error: (error) => console.error('Error loading users', error)
      });
    } else {
      this.userService.getUsersByRole(this.selectedRole).subscribe({
        next: (users) => {
          this.users = users;
          this.filterUsers();
        },
        error: (error) => console.error('Error loading users', error)
      });
    }
  }

  // Gestion des matrices
  approveMatrix(matrixId: number | undefined): void {
    if (!matrixId) return;

    this.matrixService.updateDgValidation(matrixId, ValidationStatus.APPROVED).subscribe({
      next: () => {
        this.loadMatrices();
        if (this.selectedMatrix && this.selectedMatrix.id === matrixId) {
          this.selectedMatrix.dgValidationStatus = ValidationStatus.APPROVED;
        }
      },
      error: (error) => console.error('Error approving matrix', error)
    });
  }

  rejectMatrix(matrixId: number | undefined): void {
    if (!matrixId) return;

    this.matrixService.updateDgValidation(matrixId, ValidationStatus.REJECTED).subscribe({
      next: () => {
        this.loadMatrices();
        if (this.selectedMatrix && this.selectedMatrix.id === matrixId) {
          this.selectedMatrix.dgValidationStatus = ValidationStatus.REJECTED;
        }
      },
      error: (error) => console.error('Error rejecting matrix', error)
    });
  }

  viewMatrixDetails(matrixId: number | undefined): void {
    if (!matrixId) return;

    const matrix = this.matrices.find(m => m.id === matrixId);
    if (matrix) {
      this.selectedMatrix = matrix;
      this.loadMatrixComments(matrixId);
      this.showMatrixDetailsModal = true;
    }
  }

  loadMatrixComments(matrixId: number): void {
    this.commentService.getMatrixComments(matrixId).subscribe({
      next: (comments) => {
        this.matrixComments = comments;
      },
      error: (error) => console.error('Error loading comments', error)
    });
  }

  addComment(): void {
    if (!this.newComment.trim() || !this.selectedMatrix) return;

    this.commentService.addComment({
      content: this.newComment,
      matrixId: this.selectedMatrix.id
    }).subscribe({
      next: (comment) => {
        this.matrixComments.unshift(comment);
        this.newComment = '';
      },
      error: (error) => console.error('Error adding comment', error)
    });
  }

  // Gestion du calendrier
  updateCalendar(date: Date): void {
    this.currentDate = new Date(date);
    this.currentMonth = this.currentDate.toLocaleString('default', { month: 'long' });
    this.currentYear = this.currentDate.getFullYear();

    const firstDay = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth(), 1);
    const lastDay = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, 0);

    // Jour de la semaine du premier jour (0 = Dimanche)
    let firstDayOfWeek = firstDay.getDay();
    if (firstDayOfWeek === 0) firstDayOfWeek = 7; // Ajuster pour commencer par Lundi

    // Jours du mois précédent
    const daysFromPrevMonth = firstDayOfWeek - 1;
    const prevMonth = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() - 1, 0);

    this.calendarDays = [];

    // Ajouter les jours du mois précédent
    for (let i = daysFromPrevMonth; i > 0; i--) {
      const day = prevMonth.getDate() - i + 1;
      this.calendarDays.push({
        day,
        inMonth: false,
        isToday: false,
        holidays: [] // À remplir avec les congés
      });
    }

    // Ajouter les jours du mois actuel
    const today = new Date();
    for (let i = 1; i <= lastDay.getDate(); i++) {
      const isToday =
        today.getDate() === i &&
        today.getMonth() === this.currentDate.getMonth() &&
        today.getFullYear() === this.currentDate.getFullYear();

      this.calendarDays.push({
        day: i,
        inMonth: true,
        isToday,
        holidays: [] // À remplir avec les congés
      });
    }

    // Ajouter les jours du mois suivant
    const totalDaysToShow = 42; // 6 semaines
    const daysFromNextMonth = totalDaysToShow - this.calendarDays.length;
    for (let i = 1; i <= daysFromNextMonth; i++) {
      this.calendarDays.push({
        day: i,
        inMonth: false,
        isToday: false,
        holidays: []
      });
    }

    // Charger les congés pour ce mois
    this.loadCalendarHolidays();
  }

  previousMonth(): void {
    const prevMonth = new Date(this.currentDate);
    prevMonth.setMonth(prevMonth.getMonth() - 1);
    this.updateCalendar(prevMonth);
  }

  nextMonth(): void {
    const nextMonth = new Date(this.currentDate);
    nextMonth.setMonth(nextMonth.getMonth() + 1);
    this.updateCalendar(nextMonth);
  }

  loadCalendarHolidays(): void {
    // Cette fonction chargerait les congés pour le mois actuel
    // et les mettrait dans les jours correspondants du calendrier
    // Pour l'instant, nous utilisons des données fictives

    // Réinitialiser les congés
    this.calendarDays.forEach(day => {
      day.holidays = [];
    });

    // Ajouter quelques congés d'exemple
    this.upcomingHolidays.forEach(holiday => {
      const startDate = new Date(holiday.startDate);
      const endDate = new Date(holiday.endDate);

      if (startDate.getMonth() === this.currentDate.getMonth() &&
        startDate.getFullYear() === this.currentDate.getFullYear()) {
        const dayIndex = this.calendarDays.findIndex(
          day => day.inMonth && day.day === startDate.getDate()
        );
        if (dayIndex !== -1) {
          this.calendarDays[dayIndex].holidays.push(holiday);
        }
      }
    });
  }

  // Gestion des utilisateurs
  filterUsers(): void {
    if (!this.searchQuery) {
      this.filteredUsers = [...this.users];
      return;
    }

    const query = this.searchQuery.toLowerCase();
    this.filteredUsers = this.users.filter(user =>
      user.username.toLowerCase().includes(query) ||
      (user.firstName && user.firstName.toLowerCase().includes(query)) ||
      (user.lastName && user.lastName.toLowerCase().includes(query)) ||
      (user.email && user.email.toLowerCase().includes(query)) ||
      (user.department && user.department.toLowerCase().includes(query)) ||
      (user.position && user.position.toLowerCase().includes(query))
    );
  }

  editUser(userId: number | undefined): void {
    // Implémenter la fonctionnalité d'édition
    alert('Fonctionnalité d\'édition à implémenter');
  }

  // Gestion des notifications
  showNotifications(): void {
    this.notificationService.getUserNotifications().subscribe({
      next: (notifications) => {
        this.notifications = notifications;
        this.showNotificationsModal = true;
      },
      error: (error) => console.error('Error loading notifications', error)
    });
  }

  markNotificationAsRead(notificationId: number | undefined): void {
    if (!notificationId) return;

    this.notificationService.markAsRead(notificationId).subscribe({
      next: () => {
        const notification = this.notifications.find(n => n.id === notificationId);
        if (notification) {
          notification.read = true;
        }

        // Mettre à jour le compteur de notifications
        this.loadUserDetails();
      },
      error: (error) => console.error('Error marking notification as read', error)
    });
  }

  markAllNotificationsAsRead(): void {
    this.notificationService.markAllAsRead().subscribe({
      next: () => {
        this.notifications.forEach(notification => {
          notification.read = true;
        });

        // Mettre à jour le compteur de notifications
        this.loadUserDetails();
      },
      error: (error) => console.error('Error marking all notifications as read', error)
    });
  }

  hasUnreadNotifications(): boolean {
    return this.notifications.some(notification => !notification.read);
  }

  // Utilitaires
  getCriticalityLabel(level: number | undefined): string {
    switch (level) {
      case 1: return 'Faible';
      case 2: return 'Moyenne';
      case 3: return 'Élevée';
      default: return 'Inconnue';
    }
  }

  getCriticalityColor(level: number | undefined): string {
    switch (level) {
      case 1: return '#4caf50'; // Vert
      case 2: return '#ff9800'; // Orange
      case 3: return '#f44336'; // Rouge
      default: return '#9e9e9e'; // Gris
    }
  }

  getStatusLabel(status: string | undefined): string {
    switch (status) {
      case 'PENDING': return 'En attente';
      case 'APPROVED': return 'Approuvé';
      case 'REJECTED': return 'Rejeté';
      default: return 'Inconnu';
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
