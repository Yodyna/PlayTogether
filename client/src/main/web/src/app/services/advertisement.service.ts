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

  getAdvertisementArray(): Observable<Array<Advertisement>> {
    return this.http.get<Array<Advertisement>>(`${this.backendUrl}/advertisement`);
  }

  getAdvertisementById(advertisementId: number): Observable<Advertisement> {
    return this.http.get<Advertisement>(`${this.backendUrl}/advertisement/${advertisementId}`);
  }

  addAdvertisement(advertisement: Advertisement): Observable<Advertisement> {
    return this.http.post<Advertisement>(`${this.backendUrl}/advertisement/create`, advertisement, {});
  }

  getAdvertisementArrayBySportAndCity(sport: string, city: string): Observable<Array<Advertisement>> {
    return this.http.get<Array<Advertisement>>(`${this.backendUrl}/advertisement/${sport}/${city}`);
  }

  getAdvertisementArrayByUsername(): Observable<Array<Advertisement>> {
    return this.http.get<Array<Advertisement>>(`${this.backendUrl}/advertisement/user`);
  }

  getAdvertisementArrayPrincipalByUsername(): Observable<Array<Advertisement>> {
    return this.http.get<Array<Advertisement>>(`${this.backendUrl}/advertisement/userList`);
  }

  addUserToParticipant(advertisementId: number) {
    return this.http.post(`${this.backendUrl}/advertisement/${advertisementId}/joinToAdvertisement`, {}, {});
  }

  removeToAdvertisement(advertisementId: number) {
    return this.http.delete(`${this.backendUrl}/advertisement/${advertisementId}/removeToAdvertisement`);
  }

  isParticipant(advertisementId: number) {
    const requestOptions: Object = {
      responseType: 'text'
    };
    return this.http.get<boolean>(`${this.backendUrl}/advertisement/${advertisementId}/isParticipant`, requestOptions);
  }

  getAllSport(): Observable<string[]> {
    return this.http.get<string[]>(`${this.backendUrl}/advertisement/sport`);
  }

  sendMessage(advertisementId: number, description: string) {
    return this.http.post(`${this.backendUrl}/advertisement/${advertisementId}/message`, description, {});
  }

  removeAdvertisement(advertisementId: number) {
    return this.http.delete(`${this.backendUrl}/advertisement/${advertisementId}/removeAdvertisement`);
  }

  getParticipants(advertisementId: number) {
    return this.http.get(`${this.backendUrl}/advertisement/${advertisementId}/participants`);
  }

  kickUserFromAdvertisement(advertisementId: number, userId: number) {
    return this.http.delete(`${this.backendUrl}/advertisement/${advertisementId}/user/${userId}`);
  }

  sendMessageToUser(advertisementId: number, userId: number, description: string) {
    return this.http.post(`${this.backendUrl}/advertisement/${advertisementId}/user/${userId}`, description);
  }
}
