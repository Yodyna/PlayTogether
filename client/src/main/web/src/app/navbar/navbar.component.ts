import { Component, OnInit } from '@angular/core';
import { HttpService } from '../services/http.service';
import { Session } from '../models/session';
import { AdvertisementService } from '../services/advertisement.service';
import { Advertisement } from '../models/advertisement';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  session: Session;
  variableSearch = '';
  messages;

  constructor(private httpService: HttpService, private advertisementService: AdvertisementService, private userService: UserService) {
  }

  ngOnInit() {
    this.httpService.getUsername().subscribe((result: Session) => {
      this.session = result;
      if (this.session) {
        this.userService.getMessage().subscribe( (resultt) => {
          this.messages = resultt;
        });
      }
    });
  }

  logOut() {
    this.httpService.logOut().subscribe(result => {},
        error => {
          console.log(error);
        });
    this.session.authenticated = false;
  }

  search() {
    if (this.variableSearch.length > 0) {
      try {
        const variables = this.variableSearch.split(' ');
        this.advertisementService.getAdvertisementArrayBySportAndCity(variables[0], variables[1]).subscribe( result => {
          this.advertisementService.setAdvertIsementObs(result);
        });
      } catch {}
    } else {
      this.advertisementService.getAdvertisementArray().subscribe( (result: Array<Advertisement>) =>
        this.advertisementService.setAdvertIsementObs(result));
    }
  }
}
