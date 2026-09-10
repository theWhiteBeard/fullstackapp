import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  username: string;
  fullName: string;
}

const STORAGE_KEY = 'employee-info-session';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${environment.apiUrl}/auth/login`, credentials)
      .pipe(tap((session) => this.storeSession(session)));
  }

  logout(): void {
    localStorage.removeItem(STORAGE_KEY);
  }

  get session(): LoginResponse | null {
    const raw = localStorage.getItem(STORAGE_KEY);
    return raw ? (JSON.parse(raw) as LoginResponse) : null;
  }

  get isLoggedIn(): boolean {
    return this.session !== null;
  }

  private storeSession(session: LoginResponse): void {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(session));
  }
}
