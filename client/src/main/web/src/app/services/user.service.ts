import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';

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
}
