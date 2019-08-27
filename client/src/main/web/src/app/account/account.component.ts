import { Component, OnInit } from '@angular/core';
import { HttpService } from '../services/http.service';
import { Session } from '../models/session';
import { faUserCircle, faIdCard, faCalendar, faEnvelope, faCog,
         faDumbbell, faCity, faRoad, faUsers, faCalendarAlt
} from '@fortawesome/free-solid-svg-icons';
import { Advertisement } from '../models/advertisement';
import { UserDetail } from '../models/userDetail';
import {MatDialog} from '@angular/material/dialog';
import { AccountDialogComponent } from './dialog/account_dialog.component';
import { AdvertisementService } from '../services/advertisement.service';


@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  session: Session;
  iconUser = faUserCircle;
  iconCard = faIdCard;
  iconEvent = faCalendar;
  iconMessage = faEnvelope;
  iconSetting = faCog;
  iconSport = faDumbbell;
  iconCity = faCity;
  iconSteet = faRoad;
  iconUsers = faUsers;
  iconCalendar = faCalendarAlt;
  advertisementArray: Array<Advertisement>;
  userDetail: UserDetail = {email: '', birthday: null, phone: '', gender: ''};
  isManagement = true;
  active: number;
  txt = 'list-profile';

  constructor(private httpService: HttpService, public dialog: MatDialog, private advertisementService: AdvertisementService) { }

  ngOnInit() {
    this.httpService.getUsername().subscribe( (result: Session) => {
      this.session = result;
    });
  }

  onNavigate() {
    window.open('https://trello.com/b/GBMEpE5C/playtogether', '_blank');
  }

  openDialog(name: string, value: any): void {
    const dialogRef = this.dialog.open(AccountDialogComponent, {
      data: {name, value}
    });
    dialogRef.afterClosed().subscribe(result => {
      this.userDetail[result.name] = result.value;
      this.httpService.updateUserDetail(this.userDetail).subscribe( (p) => {
        this.getAdvertisementArrayByUsername();
      });
    });
  }

  getAdvertisementArrayByUsername() {
    this.advertisementService.getAdvertisementArrayByUsername().subscribe( (result: Array<Advertisement>) => {
      this.advertisementArray = result;
    });
  }

  getAllAdvertisementArrayByUsername() {
    this.advertisementService.getAdvertisementArrayPrincipalByUsername().subscribe( (result: Array<Advertisement>) => {
      this.advertisementArray = result;
    });
  }


  getUserDetail() {
    this.httpService.getUserDetail().subscribe( (result: UserDetail) => {
      this.userDetail = result;
    });
  }

  isManagementOfAdvertisement() {
    this.getAdvertisementArrayByUsername();
    this.isManagement = true;
  }

  isAllAdvertisement() {
    this.getAllAdvertisementArrayByUsername();
    this.isManagement = false;
  }
}
