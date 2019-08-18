import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject, Subject } from 'rxjs';
import { Session } from '../models/session';
import { UserDetail } from '../models/userDetail';
import { environment } from '../../environments/environment';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  private session = new BehaviorSubject<Session>(null);
  private backendUrl = environment.backendUrl;

  constructor(private http: HttpClient, private cookieService: CookieService) {
    console.log('test  :  ' + cookieService.get('JSESSIONID') == null ? 'pusto' : 'niepusto');
    cookieService.get('JSESSIONID') === '' ? this.session.next({authenticated: false}) : this.session.next({authenticated: true});
    //sessionStorage.getItem('username') === null ? this.session.next({authenticated: false}) : this.session.next({authenticated: true});
  }

  authenticate(credentials): Observable<Session> {
    console.log(this.backendUrl);
    const httpOptions = {
      headers: new HttpHeaders({
       'Authorization': 'Basic ' + btoa(credentials.username + ':' + credentials.password),
       'X-Requested-With' : 'XMLHttpRequest'
      })
    };
    return this.http.post<Session>(`${this.backendUrl}/welcome`, {}, httpOptions);
  }

  logOut() {
    this.session.next({authenticated: false});
    return this.http.get(`${this.backendUrl}/logmeout`);
  }

  updateUserDetail(userDetail: UserDetail) {
    return this.http.put(`${this.backendUrl}/userDetail`, userDetail);
  }

  setUsername(session: Session) {
    // sessionStorage.setItem('username', session.name);
    this.session.next(session);
  }

  getUsername(): Observable<Session> {
    return this.session.asObservable();
  }

  getUserDetail() {
    return this.http.get(`${this.backendUrl}/user/detail`);
  }
}
