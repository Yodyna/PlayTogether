import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject, Subject } from 'rxjs';
import { Session } from '../models/session';
import { CookieService } from 'angular2-cookie/services/cookies.service';
import { UserDetail } from '../models/userDetail';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  private session = new BehaviorSubject<Session>({authenticated: false});
  private backendUrl = environment.backendUrl;

  constructor(private http: HttpClient, private cookieService: CookieService) {
  }

  authenticate(credentials): Observable<Session> {
    const httpOptions = {
      headers: new HttpHeaders({
       'Authorization': 'Basic ' + btoa(credentials.username + ':' + credentials.password),
       'X-Requested-With' : 'XMLHttpRequest'
      })
    };
    return this.http.post<Session>(`${this.backendUrl}/welcome`, {}, httpOptions);
  }

  logOut() {
    return this.http.get(`${this.backendUrl}/logmeout`);
  }

  updateUserDetail(userDetail: UserDetail) {
    return this.http.put(`${this.backendUrl}/userDetail`, userDetail);
  }

  setUsername(session: Session) {
    this.session.next(session);
  }

  getUsername(): Observable<Session> {
    return this.session.asObservable();
  }

  getUserDetail() {
    return this.http.get<UserDetail>(`${this.backendUrl}/user/detail`);
  }
}
