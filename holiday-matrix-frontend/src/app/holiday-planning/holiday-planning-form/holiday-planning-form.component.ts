import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { HolidayPlanningService } from '../../services/holiday-planning.service';
import { EmployeeService } from '../../services/employee.service';
import { HolidayPeriodService } from '../../services/holiday-period.service';
import { AuthService } from '../../services/auth.service';
import { HolidayPlanning } from '../../models/holiday-planning';
import { Employee } from '../../models/employee';
import { HolidayPeriod } from '../../models/holiday-period';

@Component({
  selector: 'app-holiday-planning-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  template: `
    <div class="container">
      <h2>{{ editMode ? 'Modifier' : 'Créer' }} une planification de congé</h2>

      <form [formGroup]="planningForm" (ngSubmit)="onSubmit()">
        <div class="mb-3">
          <label for="employeeId" class="form-label">Employé</label>
          <select class="form-select" id="employeeId" formControlName="employeeId" [attr.disabled]="!isManager ? true : null">
            <option value="">Sélectionner un employé</option>
            <option *ngFor="let employee of employees" [value]="employee.id">{{ employee.name }}</option>
          </select>
        </div>

        <div class="mb-3">
          <label for="holidayPeriodId" class="form-label">Période de congé</label>
          <select class="form-select" id="holidayPeriodId" formControlName="holidayPeriodId">
            <option value="">Sélectionner une période</option>
            <option *ngFor="let period of holidayPeriods" [value]="period.id">{{ period.name }} ({{ period.startDate | date:'dd/MM/yyyy' }} - {{ period.endDate | date:'dd/MM/yyyy' }})</option>
          </select>
        </div>

        <div class="mb-3">
          <label for="status" class="form-label">Statut</label>
          <select class="form-select" id="status" formControlName="status">
            <option value="RED">Rouge (Travail)</option>
            <option value="YELLOW">Jaune (En attente)</option>
            <option value="GREEN">Vert (Congé)</option>
          </select>
        </div>

        <div class="mb-3">
          <label for="comment" class="form-label">Commentaire</label>
          <textarea class="form-control" id="comment" rows="3" formControlName="comment"></textarea>
        </div>

        <div>
          <button type="submit" class="btn btn-primary me-2" [disabled]="planningForm.invalid">{{ editMode ? 'Mettre à jour' : 'Créer' }}</button>
          <button type="button" class="btn btn-secondary" routerLink="/holiday-planning">Annuler</button>
        </div>
      </form>
    </div>
  `,
  styles: []
})
export class HolidayPlanningFormComponent implements OnInit {
  planningForm!: FormGroup;
  employees: Employee[] = [];
  holidayPeriods: HolidayPeriod[] = [];
  editMode = false;
  planningId?: number;
  currentEmployeeId?: number;
  isManager = false;

  constructor(
    private fb: FormBuilder,
    private planningService: HolidayPlanningService,
    private employeeService: EmployeeService,
    private holidayPeriodService: HolidayPeriodService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.loadHolidayPeriods();

    // Vérifier les rôles
    this.isManager = this.authService.isManager();

    // Récupérer l'employé connecté
    const currentUser = this.authService.currentUserValue;

    // Vérifier si currentUser existe ET a un ID
    if (currentUser && currentUser.id !== undefined) {
      // Maintenant on est sûr que l'ID existe
      this.employeeService.getByUser(currentUser.id).subscribe({
        next: (employee) => {
          this.currentEmployeeId = employee.id;

          // Préselectionner l'employé courant si pas manager
          if (!this.isManager && this.currentEmployeeId) {
            this.planningForm.patchValue({
              employeeId: this.currentEmployeeId
            });
          }

          this.loadEmployees();
        },
        error: (err) => {
          console.error('Erreur lors de la récupération de l\'employé', err);
          this.loadEmployees();
        }
      });
    } else {
      console.warn('Utilisateur non connecté ou ID non disponible');
      this.loadEmployees();
    }

    // Vérifier s'il s'agit d'une mise à jour
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.editMode = true;
        this.planningId = +params['id'];
        this.loadPlanningData();
      }
    });
  }

  initForm(): void {
    this.planningForm = this.fb.group({
      employeeId: ['', Validators.required],
      holidayPeriodId: ['', Validators.required],
      status: ['YELLOW', Validators.required],
      comment: ['']
    });
  }

  loadEmployees(): void {
    if (this.isManager) {
      // Les managers peuvent voir tous les employés
      this.employeeService.getAll().subscribe({
        next: (data) => {
          this.employees = data;
        },
        error: (err) => {
          console.error('Erreur lors du chargement des employés', err);
        }
      });
    } else if (this.currentEmployeeId) {
      // Les employés ne voient qu'eux-mêmes
      this.employeeService.getById(this.currentEmployeeId).subscribe({
        next: (employee) => {
          this.employees = [employee];
        },
        error: (err) => {
          console.error('Erreur lors du chargement de l\'employé', err);
        }
      });
    }
  }

  loadHolidayPeriods(): void {
    this.holidayPeriodService.getAll().subscribe({
      next: (data) => {
        this.holidayPeriods = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des périodes de congé', err);
      }
    });
  }

  loadPlanningData(): void {
    if (!this.planningId) return;

    this.planningService.getById(this.planningId).subscribe({
      next: (planning) => {
        this.planningForm.patchValue({
          employeeId: planning.employeeId,
          holidayPeriodId: planning.holidayPeriodId,
          status: planning.status,
          comment: planning.comment
        });
      },
      error: (err) => {
        console.error('Erreur lors du chargement de la planification', err);
      }
    });
  }

  onSubmit(): void {
    if (this.planningForm.invalid) return;

    const planningData: HolidayPlanning = {
      employeeId: this.planningForm.value.employeeId,
      holidayPeriodId: this.planningForm.value.holidayPeriodId,
      status: this.planningForm.value.status,
      comment: this.planningForm.value.comment,
      managerValidated: false,
      hosValidated: false,
      dgValidated: false
    };

    if (this.editMode && this.planningId) {
      planningData.id = this.planningId;

      // Pour l'édition, conserver les validations existantes
      this.planningService.getById(this.planningId).subscribe({
        next: (existingPlanning) => {
          planningData.managerValidated = existingPlanning.managerValidated;
          planningData.hosValidated = existingPlanning.hosValidated;
          planningData.dgValidated = existingPlanning.dgValidated;

          this.planningService.create(planningData).subscribe({
            next: () => {
              this.router.navigate(['/holiday-planning']);
            },
            error: (err) => {
              console.error('Erreur lors de la mise à jour de la planification', err);
            }
          });
        },
        error: (err) => {
          console.error('Erreur lors de la récupération de la planification existante', err);
        }
      });
    } else {
      this.planningService.create(planningData).subscribe({
        next: () => {
          this.router.navigate(['/holiday-planning']);
        },
        error: (err) => {
          console.error('Erreur lors de la création de la planification', err);
        }
      });
    }
  }
}
