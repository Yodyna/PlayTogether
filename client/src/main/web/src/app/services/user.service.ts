import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Message } from '@angular/compiler/src/i18n/i18n_ast';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private backendUrl = environment.backendUrl;

  constructor(private http: HttpClient) { }

  register(user: JSON) {
    const httpOptions = {
      headers: new HttpHeaders({
       'X-Requested-With' : 'XMLHttpRequest'
      })
    };
    return this.http.post(`${this.backendUrl}/user/register`, user, httpOptions);
  }

  getMessage(): Observable<Array<Message>> {
    return this.http.get<Array<Message>>(`${this.backendUrl}/user/message`);
  }
}
