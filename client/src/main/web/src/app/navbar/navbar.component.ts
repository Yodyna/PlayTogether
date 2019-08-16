import { Component, OnInit } from '@angular/core';
import { HttpService } from '../services/http.service';
import { Session } from '../models/session';
import { AdvertisementService } from '../services/advertisement.service';
import { Advertisement } from '../models/advertisement';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  session: Session;
  variableSearch = '';

  constructor(private httpService: HttpService, private advertisementService: AdvertisementService, private cookieService: CookieService) {
  }

  ngOnInit() {
    this.httpService.getUsername().subscribe((result: Session) => {
      this.session = result;
    });

    this.cookieService.set( 'Test', 'Hello World' );
    console.log(this.cookieService.get('JSESSIONID'));
  }

  logOut() {
    this.httpService.logOut().subscribe(result => {
      },
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
