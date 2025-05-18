import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HolidayPlanningFormComponent } from './holiday-planning-form.component';

describe('HolidayPlanningFormComponent', () => {
  let component: HolidayPlanningFormComponent;
  let fixture: ComponentFixture<HolidayPlanningFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HolidayPlanningFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HolidayPlanningFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
