import { Advertisement } from './../../models/advertisement';
import { AdvertisementService } from './../../services/advertisement.service';
import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { faDumbbell, faCity, faRoad, faUsers, faCalendarAlt } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-advertisement-search',
  templateUrl: './advertisement-search.component.html',
  styleUrls: ['./advertisement-search.component.css']
})
export class AdvertisementSearchComponent implements OnInit {
  iconSport = faDumbbell;
  iconCity = faCity;
  iconSteet = faRoad;
  iconUsers = faUsers;
  iconCalendar = faCalendarAlt;
  advertisements: Array<Advertisement>;

  constructor(private advertisementService: AdvertisementService, private httpService: HttpService) {
    this.advertisementService.getAdvertisementArray().subscribe((result: Array<Advertisement>) => {
      this.advertisements = result;
    });
  }

  ngOnInit() {
    this.advertisementService.getAdvertiSementObs().subscribe( (result: Array<Advertisement>) => {
      this.advertisements = result;
    });
  }
}
