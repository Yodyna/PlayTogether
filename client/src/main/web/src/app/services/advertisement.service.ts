import { Advertisement } from './../models/advertisement';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdvertisementService {

  private advertisementListObs = new BehaviorSubject<Array<Advertisement>>([]);
  private backendUrl = environment.backendUrl;

  constructor(private http: HttpClient) {
  }

  getAdvertiSementObs(): Observable<Array<Advertisement>> {
    return this.advertisementListObs.asObservable();
  }

  setAdvertIsementObs(advertisementList: Array<Advertisement>) {
    this.advertisementListObs.next(advertisementList);
  }

  getAdvertisementArray() {
    return this.http.get(`${this.backendUrl}/advertisement`);
  }

  getAdvertisementById(id: number) {
    return this.http.get(`${this.backendUrl}/advertisement/${id}`);
  }

  addAdvertisement(advertisement: Advertisement) {
    return this.http.post(`${this.backendUrl}/advertisement/user`, advertisement, {});
  }

  getAdvertisementArrayBySportAndCity(sport: string, city: string): Observable<Array<Advertisement>> {
    return this.http.get<Array<Advertisement>>(`${this.backendUrl}/advertisement/${sport}/${city}`);
  }

  getAdvertisementArrayByUsername(): Observable<Array<Advertisement>> {
    return this.http.get<Array<Advertisement>>(`${this.backendUrl}/advertisement/user`);
  }

  addUserToParticipant(id: number) {
    return this.http.post(`${this.backendUrl}/advertisement/joinToAdvertisement/${id}`, {}, {});
  }

  removeToAdvertisement(id: number) {
    return this.http.delete(`${this.backendUrl}/advertisement/removeToAdvertisement/${id}`);
  }

  isParticipant(id: number) {
    const requestOptions: Object = {
      responseType: 'text'
    };
    return this.http.get<boolean>(`${this.backendUrl}/advertisement/isParticipant/${id}`, requestOptions);
  }

  getAllSport() {
    return this.http.get<string[]>(`${this.backendUrl}/advertisement/sport`);
  }
}
