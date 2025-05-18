import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HolidayPlanningService } from '../../services/holiday-planning.service';
import { AuthService } from '../../services/auth.service';
import { EmployeeService } from '../../services/employee.service';
import { HolidayPlanning } from '../../models/holiday-planning';
import { Employee } from '../../models/employee';

@Component({
  selector: 'app-holiday-planning-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="container">
      <h2>Planification des congés</h2>
      <button class="btn btn-primary" [routerLink]="['/holiday-planning/create']">Créer une nouvelle planification</button>

      <div *ngIf="plannings.length === 0" class="alert alert-info mt-3">
        Aucune planification disponible.
      </div>

      <table *ngIf="plannings.length > 0" class="table mt-3">
        <thead>
          <tr>
            <th>Employé</th>
            <th>Période</th>
            <th>Statut</th>
            <th>Validations</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let planning of plannings">
            <td>{{ planning.employeeName }}</td>
            <td>{{ planning.holidayPeriodName }}</td>
            <td>
              <span class="badge" [ngClass]="{
                'bg-danger': planning.status === 'RED',
                'bg-warning': planning.status === 'YELLOW',
                'bg-success': planning.status === 'GREEN'
              }">
                {{ getStatusLabel(planning.status) }}
              </span>
            </td>
            <td>
              <div>Manager:
                <span [ngClass]="planning.managerValidated ? 'text-success' : 'text-warning'">
                  {{ planning.managerValidated ? 'Validé' : 'En attente' }}
                </span>
              </div>
              <div>HOS:
                <span [ngClass]="planning.hosValidated ? 'text-success' : 'text-warning'">
                  {{ planning.hosValidated ? 'Validé' : 'En attente' }}
                </span>
              </div>
              <div>DG:
                <span [ngClass]="planning.dgValidated ? 'text-success' : 'text-warning'">
                  {{ planning.dgValidated ? 'Validé' : 'En attente' }}
                </span>
              </div>
            </td>
            <td>
              <button class="btn btn-sm btn-info me-2" [routerLink]="['/holiday-planning', planning.id]">Voir</button>
              <button *ngIf="canValidateAsManager(planning)" class="btn btn-sm btn-success me-2" (click)="validateAsManager(planning.id)">Valider (Manager)</button>
              <button *ngIf="canValidateAsHOS(planning)" class="btn btn-sm btn-success me-2" (click)="validateAsHOS(planning.id)">Valider (HOS)</button>
              <button *ngIf="canValidateAsDG(planning)" class="btn btn-sm btn-success me-2" (click)="validateAsDG(planning.id)">Valider (DG)</button>
              <button class="btn btn-sm btn-danger" (click)="deletePlanning(planning.id)">Supprimer</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `,
  styles: []
})
export class HolidayPlanningListComponent implements OnInit {
  plannings: HolidayPlanning[] = [];
  currentEmployeeId?: number;
  isManager = false;
  isHOS = false;
  isDG = false;

  constructor(
    private planningService: HolidayPlanningService,
    private authService: AuthService,
    private employeeService: EmployeeService
  ) { }

  ngOnInit(): void {
    // Vérifier les rôles
    this.isManager = this.authService.isManager();
    this.isHOS = this.authService.isHOS();
    this.isDG = this.authService.isDG();

    // Récupérer l'employé connecté
    const currentUser = this.authService.currentUserValue;
    if (currentUser && currentUser.id !== undefined) {
      // Maintenant on est sûr que l'ID existe
      this.employeeService.getByUser(currentUser.id).subscribe({
        next: (employee) => {
          this.currentEmployeeId = employee.id;
          this.loadPlannings();
        },
        error: (err) => {
          console.error('Erreur lors de la récupération de l\'employé', err);
          this.loadPlannings();
        }
      });
    } else {
      console.warn('Utilisateur non connecté ou ID non disponible');
      this.loadPlannings();
    }
  }

  loadPlannings(): void {
    if (this.isManager || this.isHOS || this.isDG) {
      // Les managers et au-dessus voient toutes les planifications
      this.planningService.getAll().subscribe({
        next: (data) => {
          this.plannings = data;
        },
        error: (err) => {
          console.error('Erreur lors du chargement des planifications', err);
        }
      });
    } else if (this.currentEmployeeId) {
      // Les employés ne voient que leurs propres planifications
      this.planningService.getByEmployee(this.currentEmployeeId).subscribe({
        next: (data) => {
          this.plannings = data;
        },
        error: (err) => {
          console.error('Erreur lors du chargement des planifications', err);
        }
      });
    }
  }

  getStatusLabel(status: string): string {
    switch (status) {
      case 'RED':
        return 'Travail';
      case 'YELLOW':
        return 'En attente';
      case 'GREEN':
        return 'Congé';
      default:
        return status;
    }
  }

  canValidateAsManager(planning: HolidayPlanning): boolean {
    return this.isManager && !planning.managerValidated;
  }

  canValidateAsHOS(planning: HolidayPlanning): boolean {
    return this.isHOS && planning.managerValidated && !planning.hosValidated;
  }

  canValidateAsDG(planning: HolidayPlanning): boolean {
    return this.isDG && planning.hosValidated && !planning.dgValidated;
  }

  validateAsManager(id?: number): void {
    if (!id) return;

    this.planningService.validateByManager(id).subscribe({
      next: (data) => {
        // Mettre à jour la planification dans la liste
        const index = this.plannings.findIndex(p => p.id === id);
        if (index !== -1) {
          this.plannings[index].managerValidated = true;
        }
      },
      error: (err) => {
        console.error('Erreur lors de la validation manager', err);
      }
    });
  }

  validateAsHOS(id?: number): void {
    if (!id) return;

    this.planningService.validateByHOS(id).subscribe({
      next: (data) => {
        // Mettre à jour la planification dans la liste
        const index = this.plannings.findIndex(p => p.id === id);
        if (index !== -1) {
          this.plannings[index].hosValidated = true;
        }
      },
      error: (err) => {
        console.error('Erreur lors de la validation HOS', err);
      }
    });
  }

  validateAsDG(id?: number): void {
    if (!id) return;

    this.planningService.validateByDG(id).subscribe({
      next: (data) => {
        // Mettre à jour la planification dans la liste
        const index = this.plannings.findIndex(p => p.id === id);
        if (index !== -1) {
          this.plannings[index].dgValidated = true;
        }
      },
      error: (err) => {
        console.error('Erreur lors de la validation DG', err);
      }
    });
  }

  deletePlanning(id?: number): void {
    if (!id) return;

    if (confirm('Êtes-vous sûr de vouloir supprimer cette planification ?')) {
      this.planningService.delete(id).subscribe({
        next: () => {
          this.plannings = this.plannings.filter(p => p.id !== id);
        },
        error: (err) => {
          console.error('Erreur lors de la suppression de la planification', err);
        }
      });
    }
  }
}
