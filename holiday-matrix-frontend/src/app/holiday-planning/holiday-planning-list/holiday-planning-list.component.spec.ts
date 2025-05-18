import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HolidayPlanningListComponent } from './holiday-planning-list.component';

describe('HolidayPlanningListComponent', () => {
  let component: HolidayPlanningListComponent;
  let fixture: ComponentFixture<HolidayPlanningListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HolidayPlanningListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HolidayPlanningListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
