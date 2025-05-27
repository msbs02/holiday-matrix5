/*import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-dashboard',
  imports: [],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {

}
*/

/*import { Component, OnInit } from '@angular/core';
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
import { Holiday } from '../../models/holiday.model';
import { Notification } from '../../models/notification.model';
import { User, UserDetails } from '../../models/user.model';
import { Comment } from '../../models/comment.model';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  // Données utilisateur
  userDetails?: UserDetails;

  // Onglets
  activeTab: 'matrices' | 'planning' | 'users' = 'matrices';

  // Données des matrices
  matrices: AnnualMatrix[] = [];
  selectedYear: number = new Date().getFullYear();
  years: number[] = [];

  // Données du calendrier
  calendarDays: any[] = [];
  weekDays: string[] = ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'];
  currentMonth: string = '';
  currentYear: number = 0;
  currentDate: Date = new Date();

  // Données de planification d'équipe
  teams: any[] = []; // Simulé pour l'instant
  selectedTeam: string = 'all';
  teamEmployees: any[] = []; // Simulé pour l'instant
  daysInMonth: number[] = [];

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

  showAddUserModal: boolean = false;
  newUser: any = {
    username: '',
    password: '',
    role: 'EMPLOYEE',
    firstName: '',
    lastName: '',
    email: '',
    department: '',
    position: ''
  };

  showAssignManagerModal: boolean = false;
  selectedUser?: User;
  selectedManagerId: number | null = null;
  managers: User[] = [];

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

    // Simuler des équipes pour l'exemple
    this.teams = [
      { id: '1', name: 'Équipe Développement' },
      { id: '2', name: 'Équipe Infrastructure' },
      { id: '3', name: 'Équipe Support' }
    ];

    // Générer les jours du mois actuel
    const daysInMonth = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, 0).getDate();
    this.daysInMonth = Array.from({length: daysInMonth}, (_, i) => i + 1);
  }

  ngOnInit(): void {
    this.loadUserDetails();
    this.loadMatrices();
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

  loadUsers(): void {
    if (this.selectedRole === 'all') {
      this.userService.getAllUsers().subscribe({
        next: (users) => {
          this.users = users;
          this.filterUsers();
          this.managers = users.filter(user => user.role === 'MANAGER');
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

  loadTeamPlanning(): void {
    // Simuler le chargement des employés de l'équipe
    if (this.selectedTeam === 'all') {
      this.teamEmployees = [
        { id: 1, firstName: 'Jean', lastName: 'Dupont' },
        { id: 2, firstName: 'Marie', lastName: 'Martin' },
        { id: 3, firstName: 'Pierre', lastName: 'Durand' }
      ];
    } else if (this.selectedTeam === '1') {
      this.teamEmployees = [
        { id: 1, firstName: 'Jean', lastName: 'Dupont' },
        { id: 2, firstName: 'Marie', lastName: 'Martin' }
      ];
    } else if (this.selectedTeam === '2') {
      this.teamEmployees = [
        { id: 3, firstName: 'Pierre', lastName: 'Durand' },
        { id: 4, firstName: 'Sophie', lastName: 'Petit' }
      ];
    } else if (this.selectedTeam === '3') {
      this.teamEmployees = [
        { id: 5, firstName: 'Lucas', lastName: 'Moreau' },
        { id: 6, firstName: 'Emma', lastName: 'Richard' }
      ];
    }
  }

  // Gestion des matrices
  approveMatrix(matrixId: number | undefined): void {
    if (!matrixId) return;

    this.matrixService.updateHosValidation(matrixId, ValidationStatus.APPROVED).subscribe({
      next: () => {
        this.loadMatrices();
        if (this.selectedMatrix && this.selectedMatrix.id === matrixId) {
          this.selectedMatrix.hosValidationStatus = ValidationStatus.APPROVED;
        }
      },
      error: (error) => console.error('Error approving matrix', error)
    });
  }

  rejectMatrix(matrixId: number | undefined): void {
    if (!matrixId) return;

    this.matrixService.updateHosValidation(matrixId, ValidationStatus.REJECTED).subscribe({
      next: () => {
        this.loadMatrices();
        if (this.selectedMatrix && this.selectedMatrix.id === matrixId) {
          this.selectedMatrix.hosValidationStatus = ValidationStatus.REJECTED;
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

  // Gestion de la planification d'équipe
  getCellClass(employee: any, day: number): string {
    // Simuler des statuts aléatoires pour l'exemple
    const random = Math.floor(Math.random() * 5);

    if (random === 0) return 'holiday-high'; // Congé criticité haute
    if (random === 1) return 'holiday-medium'; // Congé criticité moyenne
    if (random === 2) return 'holiday-low'; // Congé criticité basse

    return 'work'; // Jour de travail
  }

  toggleHolidayStatus(employee: any, day: number): void {
    // Implémenter la modification du statut de congé
    alert(`Modification du statut de congé pour ${employee.firstName} ${employee.lastName} le ${day} ${this.currentMonth}`);
  }

  saveTeamPlanning(): void {
    // Implémenter la sauvegarde du planning d'équipe
    alert('Planning d\'équipe enregistré avec succès !');
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

  showAddUserForm(): void {
    this.newUser = {
      username: '',
      password: '',
      role: 'EMPLOYEE',
      firstName: '',
      lastName: '',
      email: '',
      department: '',
      position: ''
    };
    this.showAddUserModal = true;
  }

  addUser(): void {
    this.authService.register(
      this.newUser.username,
      this.newUser.password,
      this.newUser.role
    ).subscribe({
      next: (user) => {
        // Mettre à jour les informations supplémentaires
        const updatedUser = {
          firstName: this.newUser.firstName,
          lastName: this.newUser.lastName,
          email: this.newUser.email,
          department: this.newUser.department,
          position: this.newUser.position
        };

        this.userService.updateUser(user.id!, updatedUser).subscribe({
          next: () => {
            this.showAddUserModal = false;
            this.loadUsers();
            alert('Utilisateur créé avec succès !');
          }
        });
      },
      error: (error) => {
        console.error('Error creating user', error);
        alert('Erreur lors de la création de l\'utilisateur');
      }
    });
  }

  editUser(userId: number | undefined): void {
    // Implémenter la fonctionnalité d'édition
    alert('Fonctionnalité d\'édition à implémenter');
  }

  showAssignManagerForm(user: User): void {
    this.selectedUser = user;
    this.selectedManagerId = user.managerId || null;
    this.showAssignManagerModal = true;
  }

  assignManager(): void {
    if (!this.selectedUser || !this.selectedUser.id) return;

    if (this.selectedManagerId) {
      this.userService.assignManager(this.selectedUser.id, this.selectedManagerId).subscribe({
        next: () => {
          this.showAssignManagerModal = false;
          this.loadUsers();
          alert('Manager assigné avec succès !');
        },
        error: (error) => {
          console.error('Error assigning manager', error);
          alert('Erreur lors de l\'assignation du manager');
        }
      });
    } else {
      alert('Veuillez sélectionner un manager');
    }
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
  getCriticalityLabel(level: number): string {
    switch (level) {
      case 1: return 'Faible';
      case 2: return 'Moyenne';
      case 3: return 'Élevée';
      default: return 'Inconnue';
    }
  }

  getCriticalityColor(level: number): string {
    switch (level) {
      case 1: return '#4caf50'; // Vert
      case 2: return '#ff9800'; // Orange
      case 3: return '#f44336'; // Rouge
      default: return '#9e9e9e'; // Gris
    }
  }

  getStatusLabel(status: string): string {
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
}*/




/*
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
import { Holiday } from '../../models/holiday.model';
import { Notification } from '../../models/notification.model';
import { User, UserDetails } from '../../models/user.model';
import { Comment } from '../../models/comment.model';


@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  // Données utilisateur
  userDetails?: UserDetails;

  // Onglets
  activeTab: 'matrices' | 'planning' | 'users' = 'matrices';

  // Données des matrices
  matrices: AnnualMatrix[] = [];
  selectedYear: number = new Date().getFullYear();
  years: number[] = [];

  // Données du calendrier
  calendarDays: any[] = [];
  weekDays: string[] = ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'];
  currentMonth: string = '';
  currentYear: number = 0;
  currentDate: Date = new Date();

  // Données de planification d'équipe
  teams: any[] = []; // Simulé pour l'instant
  selectedTeam: string = 'all';
  teamEmployees: any[] = []; // Simulé pour l'instant
  daysInMonth: number[] = [];

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

  showAddUserModal: boolean = false;
  newUser: any = {
    username: '',
    password: '',
    role: 'EMPLOYEE',
    firstName: '',
    lastName: '',
    email: '',
    department: '',
    position: ''
  };

  showAssignManagerModal: boolean = false;
  selectedUser?: User;
  selectedManagerId: number | null = null;
  managers: User[] = [];

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

    // Simuler des équipes pour l'exemple
    this.teams = [
      { id: '1', name: 'Équipe Développement' },
      { id: '2', name: 'Équipe Infrastructure' },
      { id: '3', name: 'Équipe Support' }
    ];

    // Générer les jours du mois actuel
    const daysInMonth = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, 0).getDate();
    this.daysInMonth = Array.from({length: daysInMonth}, (_, i) => i + 1);
  }

  ngOnInit(): void {
    this.loadUserDetails();
    this.loadMatrices();
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

  loadUsers(): void {
    if (this.selectedRole === 'all') {
      this.userService.getAllUsers().subscribe({
        next: (users) => {
          this.users = users;
          this.filterUsers();
          this.managers = users.filter(user => user.role === 'MANAGER');
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

  loadTeamPlanning(): void {
    // Simuler le chargement des employés de l'équipe
    if (this.selectedTeam === 'all') {
      this.teamEmployees = [
        { id: 1, firstName: 'Jean', lastName: 'Dupont' },
        { id: 2, firstName: 'Marie', lastName: 'Martin' },
        { id: 3, firstName: 'Pierre', lastName: 'Durand' }
      ];
    } else if (this.selectedTeam === '1') {
      this.teamEmployees = [
        { id: 1, firstName: 'Jean', lastName: 'Dupont' },
        { id: 2, firstName: 'Marie', lastName: 'Martin' }
      ];
    } else if (this.selectedTeam === '2') {
      this.teamEmployees = [
        { id: 3, firstName: 'Pierre', lastName: 'Durand' },
        { id: 4, firstName: 'Sophie', lastName: 'Petit' }
      ];
    } else if (this.selectedTeam === '3') {
      this.teamEmployees = [
        { id: 5, firstName: 'Lucas', lastName: 'Moreau' },
        { id: 6, firstName: 'Emma', lastName: 'Richard' }
      ];
    }
  }

  // Gestion des matrices
  approveMatrix(matrixId: number | undefined): void {
    if (!matrixId) return;

    this.matrixService.updateHosValidation(matrixId, ValidationStatus.APPROVED).subscribe({
      next: () => {
        this.loadMatrices();
        if (this.selectedMatrix && this.selectedMatrix.id === matrixId) {
          this.selectedMatrix.hosValidationStatus = ValidationStatus.APPROVED;
        }
      },
      error: (error) => console.error('Error approving matrix', error)
    });
  }

  rejectMatrix(matrixId: number | undefined): void {
    if (!matrixId) return;

    this.matrixService.updateHosValidation(matrixId, ValidationStatus.REJECTED).subscribe({
      next: () => {
        this.loadMatrices();
        if (this.selectedMatrix && this.selectedMatrix.id === matrixId) {
          this.selectedMatrix.hosValidationStatus = ValidationStatus.REJECTED;
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

  // Gestion de la planification d'équipe
  getCellClass(employee: any, day: number): string {
    // Simuler des statuts aléatoires pour l'exemple
    const random = Math.floor(Math.random() * 5);

    if (random === 0) return 'holiday-high'; // Congé criticité haute
    if (random === 1) return 'holiday-medium'; // Congé criticité moyenne
    if (random === 2) return 'holiday-low'; // Congé criticité basse

    return 'work'; // Jour de travail
  }

  toggleHolidayStatus(employee: any, day: number): void {
    // Implémenter la modification du statut de congé
    alert(`Modification du statut de congé pour ${employee.firstName} ${employee.lastName} le ${day} ${this.currentMonth}`);
  }

  saveTeamPlanning(): void {
    // Implémenter la sauvegarde du planning d'équipe
    alert('Planning d\'équipe enregistré avec succès !');
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

  showAddUserForm(): void {
    this.newUser = {
      username: '',
      password: '',
      role: 'EMPLOYEE',
      firstName: '',
      lastName: '',
      email: '',
      department: '',
      position: ''
    };
    this.showAddUserModal = true;
  }

  addUser(): void {
    this.authService.register(
      this.newUser.username,
      this.newUser.password,
      this.newUser.role
    ).subscribe({
      next: (user) => {
        // Mettre à jour les informations supplémentaires
        const updatedUser = {
          firstName: this.newUser.firstName,
          lastName: this.newUser.lastName,
          email: this.newUser.email,
          department: this.newUser.department,
          position: this.newUser.position
        };

        this.userService.updateUser(user.id!, updatedUser).subscribe({
          next: () => {
            this.showAddUserModal = false;
            this.loadUsers();
            alert('Utilisateur créé avec succès !');
          }
        });
      },
      error: (error) => {
        console.error('Error creating user', error);
        alert('Erreur lors de la création de l\'utilisateur');
      }
    });
  }

  editUser(userId: number | undefined): void {
    // Implémenter la fonctionnalité d'édition
    alert('Fonctionnalité d\'édition à implémenter');
  }

  showAssignManagerForm(user: User): void {
    this.selectedUser = user;
    this.selectedManagerId = user.managerId || null;
    this.showAssignManagerModal = true;
  }

  assignManager(): void {
    if (!this.selectedUser || !this.selectedUser.id) return;

    if (this.selectedManagerId) {
      this.userService.assignManager(this.selectedUser.id, this.selectedManagerId).subscribe({
        next: () => {
          this.showAssignManagerModal = false;
          this.loadUsers();
          alert('Manager assigné avec succès !');
        },
        error: (error) => {
          console.error('Error assigning manager', error);
          alert('Erreur lors de l\'assignation du manager');
        }
      });
    } else {
      alert('Veuillez sélectionner un manager');
    }
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
  getCriticalityLabel(level: number): string {
    switch (level) {
      case 1: return 'Faible';
      case 2: return 'Moyenne';
      case 3: return 'Élevée';
      default: return 'Inconnue';
    }
  }

  getCriticalityColor(level: number): string {
    switch (level) {
      case 1: return '#4caf50'; // Vert
      case 2: return '#ff9800'; // Orange
      case 3: return '#f44336';
      default: return '#9e9e9e';
    }
  }

  getStatusLabel(status?: ValidationStatus): string {
    if (!status) return 'Inconnu';

    switch (status) {
      case ValidationStatus.PENDING: return 'En attente';
      case ValidationStatus.APPROVED: return 'Approuvé';
      case ValidationStatus.REJECTED: return 'Rejeté';
      default: return 'Inconnu';
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

   Retourne l’ID ou 0 si undefined *
  safeId(id?: number): number {
    return id || 0;
  }

  /** Retourne le status ou PENDING si undefined *
  safeStatus(status?: ValidationStatus): ValidationStatus {
    return status ?? ValidationStatus.PENDING;
  }

  protected readonly ValidationStatus = ValidationStatus;
}
*/

// make en comment le 15/05/2025 a 14:02
/*
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
import { Holiday } from '../../models/holiday.model';
import { Notification } from '../../models/notification.model';
import { User, UserDetails } from '../../models/user.model';
import { Comment } from '../../models/comment.model';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  // Données utilisateur
  userDetails?: UserDetails;

  // Onglets
  activeTab: 'matrices' | 'planning' | 'users' = 'matrices';

  // Données des matrices
  matrices: AnnualMatrix[] = [];
  selectedYear: number = new Date().getFullYear();
  years: number[] = [];

  // Données du calendrier
  calendarDays: any[] = [];
  weekDays: string[] = ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'];
  currentMonth: string = '';
  currentYear: number = 0;
  currentDate: Date = new Date();

  // Données de planification d'équipe
  teams: any[] = []; // Simulé pour l'instant
  selectedTeam: string = 'all';
  teamEmployees: any[] = []; // Simulé pour l'instant
  daysInMonth: number[] = [];

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

  showAddUserModal: boolean = false;
  newUser: any = {
    username: '',
    password: '',
    role: 'EMPLOYEE',
    firstName: '',
    lastName: '',
    email: '',
    department: '',
    position: ''
  };

  showAssignManagerModal: boolean = false;
  selectedUser?: User;
  selectedManagerId: number | null = null;
  managers: User[] = [];

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

    // Simuler des équipes pour l'exemple
    this.teams = [
      { id: '1', name: 'Équipe Développement' },
      { id: '2', name: 'Équipe Infrastructure' },
      { id: '3', name: 'Équipe Support' }
    ];

    // Générer les jours du mois actuel
    const daysInMonth = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, 0).getDate();
    this.daysInMonth = Array.from({length: daysInMonth}, (_, i) => i + 1);
  }

  ngOnInit(): void {
    this.loadUserDetails();
    this.loadMatrices();
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

  loadUsers(): void {
    if (this.selectedRole === 'all') {
      this.userService.getAllUsers().subscribe({
        next: (users) => {
          this.users = users;
          this.filterUsers();
          this.managers = users.filter(user => user.role === 'MANAGER');
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

  loadTeamPlanning(): void {
    // Simuler le chargement des employés de l'équipe
    if (this.selectedTeam === 'all') {
      this.teamEmployees = [
        { id: 1, firstName: 'Jean', lastName: 'Dupont' },
        { id: 2, firstName: 'Marie', lastName: 'Martin' },
        { id: 3, firstName: 'Pierre', lastName: 'Durand' }
      ];
    } else if (this.selectedTeam === '1') {
      this.teamEmployees = [
        { id: 1, firstName: 'Jean', lastName: 'Dupont' },
        { id: 2, firstName: 'Marie', lastName: 'Martin' }
      ];
    } else if (this.selectedTeam === '2') {
      this.teamEmployees = [
        { id: 3, firstName: 'Pierre', lastName: 'Durand' },
        { id: 4, firstName: 'Sophie', lastName: 'Petit' }
      ];
    } else if (this.selectedTeam === '3') {
      this.teamEmployees = [
        { id: 5, firstName: 'Lucas', lastName: 'Moreau' },
        { id: 6, firstName: 'Emma', lastName: 'Richard' }
      ];
    }
  }

  // Gestion des matrices
  approveMatrix(matrixId: number | undefined): void {
    if (!matrixId) return;

    this.matrixService.updateHosValidation(matrixId, ValidationStatus.APPROVED).subscribe({
      next: () => {
        this.loadMatrices();
        if (this.selectedMatrix && this.selectedMatrix.id === matrixId) {
          this.selectedMatrix.hosValidationStatus = ValidationStatus.APPROVED;
        }
      },
      error: (error) => console.error('Error approving matrix', error)
    });
  }

  rejectMatrix(matrixId: number | undefined): void {
    if (!matrixId) return;

    this.matrixService.updateHosValidation(matrixId, ValidationStatus.REJECTED).subscribe({
      next: () => {
        this.loadMatrices();
        if (this.selectedMatrix && this.selectedMatrix.id === matrixId) {
          this.selectedMatrix.hosValidationStatus = ValidationStatus.REJECTED;
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

  // Gestion de la planification d'équipe
  getCellClass(employee: any, day: number): string {
    // Simuler des statuts aléatoires pour l'exemple
    const random = Math.floor(Math.random() * 5);

    if (random === 0) return 'holiday-high'; // Congé criticité haute
    if (random === 1) return 'holiday-medium'; // Congé criticité moyenne
    if (random === 2) return 'holiday-low'; // Congé criticité basse

    return 'work'; // Jour de travail
  }

  toggleHolidayStatus(employee: any, day: number): void {
    // Implémenter la modification du statut de congé
    alert(`Modification du statut de congé pour ${employee.firstName} ${employee.lastName} le ${day} ${this.currentMonth}`);
  }

  saveTeamPlanning(): void {
    // Implémenter la sauvegarde du planning d'équipe
    alert('Planning d\'équipe enregistré avec succès !');
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

  showAddUserForm(): void {
    this.newUser = {
      username: '',
      password: '',
      role: 'EMPLOYEE',
      firstName: '',
      lastName: '',
      email: '',
      department: '',
      position: ''
    };
    this.showAddUserModal = true;
  }

  addUser(): void {
    this.authService.register(
      this.newUser.username,
      this.newUser.password,
      this.newUser.role
    ).subscribe({
      next: (user) => {
        // Mettre à jour les informations supplémentaires
        const updatedUser = {
          firstName: this.newUser.firstName,
          lastName: this.newUser.lastName,
          email: this.newUser.email,
          department: this.newUser.department,
          position: this.newUser.position
        };

        this.userService.updateUser(user.id!, updatedUser).subscribe({
          next: () => {
            this.showAddUserModal = false;
            this.loadUsers();
            alert('Utilisateur créé avec succès !');
          }
        });
      },
      error: (error) => {
        console.error('Error creating user', error);
        alert('Erreur lors de la création de l\'utilisateur');
      }
    });
  }

  editUser(userId: number | undefined): void {
    // Implémenter la fonctionnalité d'édition
    alert('Fonctionnalité d\'édition à implémenter');
  }

  showAssignManagerForm(user: User): void {
    this.selectedUser = user;
    this.selectedManagerId = user.managerId || null;
    this.showAssignManagerModal = true;
  }

  assignManager(): void {
    if (!this.selectedUser || !this.selectedUser.id) return;

    if (this.selectedManagerId) {
      this.userService.assignManager(this.selectedUser.id, this.selectedManagerId).subscribe({
        next: () => {
          this.showAssignManagerModal = false;
          this.loadUsers();
          alert('Manager assigné avec succès !');
        },
        error: (error) => {
          console.error('Error assigning manager', error);
          alert('Erreur lors de l\'assignation du manager');
        }
      });
    } else {
      alert('Veuillez sélectionner un manager');
    }
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
  getCriticalityLabel(level: number): string {
    switch (level) {
      case 1: return 'Faible';
      case 2: return 'Moyenne';
      case 3: return 'Élevée';
      default: return 'Inconnue';
    }
  }

  getCriticalityColor(level: number): string {
    switch (level) {
      case 1: return '#4caf50'; // Vert
      case 2: return '#ff9800'; // Orange
      case 3: return '#f44336'; // Rouge
      default: return '#9e9e9e'; // Gris
    }
  }

  getStatusLabel(status: string): string {
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
}*/

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { MatrixService } from '../../services/matrix.service';
import { HolidayService } from '../../services/holiday.service';
import { NotificationService } from '../../services/notification.service';
import { UserService } from '../../services/user.service';
import { CommentService } from '../../services/comment.service';
import { AnnualMatrix, ValidationStatus } from '../../models/annual-matrix.model';
import { Holiday } from '../../models/holiday.model';
import { Notification } from '../../models/notification.model';
import { User, UserDetails } from '../../models/user.model';
import { Comment } from '../../models/comment.model';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
    imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  // Données utilisateur
  userDetails?: UserDetails;

  // Onglets
  activeTab: 'matrices' | 'planning' | 'users' = 'matrices';

  // Données des matrices
  matrices: AnnualMatrix[] = [];
  selectedYear: number = new Date().getFullYear();
  years: number[] = [];

  // Données du calendrier
  calendarDays: any[] = [];
  weekDays: string[] = ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'];
  currentMonth: string = '';
  currentYear: number = 0;
  currentDate: Date = new Date();

  // Données de planification d'équipe
  teams: any[] = []; // Simulé pour l'instant
  selectedTeam: string = 'all';
  teamEmployees: any[] = []; // Simulé pour l'instant
  daysInMonth: number[] = [];

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

  showAddUserModal: boolean = false;
  newUser: any = {
    username: '',
    password: '',
    role: 'EMPLOYEE',
    firstName: '',
    lastName: '',
    email: '',
    department: '',
    position: ''
  };

  showAssignManagerModal: boolean = false;
  selectedUser?: User;
  selectedManagerId: number | null = null;
  managers: User[] = [];

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

    // Simuler des équipes pour l'exemple
    this.teams = [
      { id: '1', name: 'Équipe Développement' },
      { id: '2', name: 'Équipe Infrastructure' },
      { id: '3', name: 'Équipe Support' }
    ];

    // Générer les jours du mois actuel
    const daysInMonth = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, 0).getDate();
    this.daysInMonth = Array.from({length: daysInMonth}, (_, i) => i + 1);
  }

  ngOnInit(): void {
    this.loadUserDetails();
    this.loadMatrices();
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

  loadUsers(): void {
    if (this.selectedRole === 'all') {
      this.userService.getAllUsers().subscribe({
        next: (users) => {
          this.users = users;
          this.filterUsers();
          this.managers = users.filter(user => user.role === 'MANAGER');
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

  loadTeamPlanning(): void {
    // Simuler le chargement des employés de l'équipe
    if (this.selectedTeam === 'all') {
      this.teamEmployees = [
        { id: 1, firstName: 'Jean', lastName: 'Dupont' },
        { id: 2, firstName: 'Marie', lastName: 'Martin' },
        { id: 3, firstName: 'Pierre', lastName: 'Durand' }
      ];
    } else if (this.selectedTeam === '1') {
      this.teamEmployees = [
        { id: 1, firstName: 'Jean', lastName: 'Dupont' },
        { id: 2, firstName: 'Marie', lastName: 'Martin' }
      ];
    } else if (this.selectedTeam === '2') {
      this.teamEmployees = [
        { id: 3, firstName: 'Pierre', lastName: 'Durand' },
        { id: 4, firstName: 'Sophie', lastName: 'Petit' }
      ];
    } else if (this.selectedTeam === '3') {
      this.teamEmployees = [
        { id: 5, firstName: 'Lucas', lastName: 'Moreau' },
        { id: 6, firstName: 'Emma', lastName: 'Richard' }
      ];
    }
  }

  // Gestion des matrices
  approveMatrix(matrixId: number | undefined): void {
    if (!matrixId) return;

    this.matrixService.updateHosValidation(matrixId, ValidationStatus.APPROVED).subscribe({
      next: () => {
        this.loadMatrices();
        if (this.selectedMatrix && this.selectedMatrix.id === matrixId) {
          this.selectedMatrix.hosValidationStatus = ValidationStatus.APPROVED;
        }
      },
      error: (error) => console.error('Error approving matrix', error)
    });
  }

  rejectMatrix(matrixId: number | undefined): void {
    if (!matrixId) return;

    this.matrixService.updateHosValidation(matrixId, ValidationStatus.REJECTED).subscribe({
      next: () => {
        this.loadMatrices();
        if (this.selectedMatrix && this.selectedMatrix.id === matrixId) {
          this.selectedMatrix.hosValidationStatus = ValidationStatus.REJECTED;
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

  // Gestion de la planification d'équipe
  getCellClass(employee: any, day: number): string {
    // Simuler des statuts aléatoires pour l'exemple
    const random = Math.floor(Math.random() * 5);

    if (random === 0) return 'holiday-high'; // Congé criticité haute
    if (random === 1) return 'holiday-medium'; // Congé criticité moyenne
    if (random === 2) return 'holiday-low'; // Congé criticité basse

    return 'work'; // Jour de travail
  }

  toggleHolidayStatus(employee: any, day: number): void {
    // Implémenter la modification du statut de congé
    alert(`Modification du statut de congé pour ${employee.firstName} ${employee.lastName} le ${day} ${this.currentMonth}`);
  }

  saveTeamPlanning(): void {
    // Implémenter la sauvegarde du planning d'équipe
    alert('Planning d\'équipe enregistré avec succès !');
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

  showAddUserForm(): void {
    this.newUser = {
      username: '',
      password: '',
      role: 'EMPLOYEE',
      firstName: '',
      lastName: '',
      email: '',
      department: '',
      position: ''
    };
    this.showAddUserModal = true;
  }

  addUser(): void {
    this.authService.register(
      this.newUser.username,
      this.newUser.password,
      this.newUser.role
    ).subscribe({
      next: (user) => {
        // Mettre à jour les informations supplémentaires
        const updatedUser = {
          firstName: this.newUser.firstName,
          lastName: this.newUser.lastName,
          email: this.newUser.email,
          department: this.newUser.department,
          position: this.newUser.position
        };

        this.userService.updateUser(user.id!, updatedUser).subscribe({
          next: () => {
            this.showAddUserModal = false;
            this.loadUsers();
            alert('Utilisateur créé avec succès !');
          }
        });
      },
      error: (error) => {
        console.error('Error creating user', error);
        alert('Erreur lors de la création de l\'utilisateur');
      }
    });
  }

  editUser(userId: number | undefined): void {
    // Implémenter la fonctionnalité d'édition
    alert('Fonctionnalité d\'édition à implémenter');
  }

  showAssignManagerForm(user: User): void {
    this.selectedUser = user;
    this.selectedManagerId = user.managerId || null;
    this.showAssignManagerModal = true;
  }

  assignManager(): void {
    if (!this.selectedUser || !this.selectedUser.id) return;

    if (this.selectedManagerId) {
      this.userService.assignManager(this.selectedUser.id, this.selectedManagerId).subscribe({
        next: () => {
          this.showAssignManagerModal = false;
          this.loadUsers();
          alert('Manager assigné avec succès !');
        },
        error: (error) => {
          console.error('Error assigning manager', error);
          alert('Erreur lors de l\'assignation du manager');
        }
      });
    } else {
      alert('Veuillez sélectionner un manager');
    }
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
    if (level === undefined) return 'Inconnue';

    switch (level) {
      case 1: return 'Faible';
      case 2: return 'Moyenne';
      case 3: return 'Élevée';
      default: return 'Inconnue';
    }
  }

  getCriticalityColor(level: number | undefined): string {
    if (level === undefined) return '#9e9e9e'; // Gris

    switch (level) {
      case 1: return '#4caf50'; // Vert
      case 2: return '#ff9800'; // Orange
      case 3: return '#f44336'; // Rouge
      default: return '#9e9e9e'; // Gris
    }
  }

  getStatusLabel(status: string | undefined): string {
    if (!status) return 'Inconnu';

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
