import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../core/auth.service';
import { Employee, EmployeeService } from '../core/employee.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  session = this.auth.session;

  employees: Employee[] = [];
  loading = false;
  saving = false;
  errorMessage = '';

  form = this.fb.nonNullable.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    department: ['', Validators.required],
    jobTitle: ['', Validators.required],
  });

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private employeeService: EmployeeService,
  ) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.loading = true;
    this.employeeService.list().subscribe({
      next: (list) => {
        this.employees = list;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Could not load employees.';
        this.loading = false;
      },
    });
  }

  save(): void {
    if (this.form.invalid || this.saving) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving = true;
    this.errorMessage = '';

    this.employeeService.create(this.form.getRawValue()).subscribe({
      next: (created) => {
        this.employees = [...this.employees, created].sort((a, b) =>
          a.lastName.localeCompare(b.lastName),
        );
        this.form.reset();
        this.saving = false;
      },
      error: (err) => {
        this.errorMessage =
          err?.error?.message ??
          'Could not save employee. Check the fields and try again.';
        this.saving = false;
      },
    });
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
