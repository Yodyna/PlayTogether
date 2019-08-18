import { Component } from '@angular/core';
import { HttpService } from '../services/http.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Session } from '../models/session';
import { faUserAlt, faKey, faEnvelope } from '@fortawesome/free-solid-svg-icons';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  iconUser = faUserAlt;
  iconEmail = faEnvelope;
  iconKey = faKey;

  registerForm = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(5)]],
    password: ['', [Validators.required, Validators.minLength(5)]],
    userDetail: this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    })
  });

  loginForm = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(5)]],
    password: ['', [Validators.required, Validators.minLength(5)]]
  });

  isLogin = true;

  alert = {
    alertMessage: '',
    kindOfAlert: '',
    isAlert: false,
    alertError: function(message: string) {
      this.isAlert = true;
      this.kindOfAlert = 'alert-danger';
      this.alertMessage = message;
    },
    alertSuccess: function(message: string) {
      this.isAlert = true;
      this.kindOfAlert = 'alert-success';
      this.alertMessage = message;
    }
  };

  constructor(private httpService: HttpService, private userService: UserService, private fb: FormBuilder, private router: Router) {}

  isLoginForm() {
    this.alert.isAlert = false;
    this.isLogin = true;
  }

  isRegisterForm() {
    this.alert.isAlert = false;
    this.isLogin = false;
  }

  login() {
    this.httpService.authenticate(this.loginForm.value).subscribe(
      async (result: Session) => {
        console.log(result);
        this.httpService.setUsername(result);
        this.alert.alertSuccess('Jesteś zalogowany');
        setTimeout(() => {
          this.router.navigate(['/']);
        },
        1000);
      },
      error => {
        this.alert.alertError('Nie udało się zalogować');
      });
  }

  register() {
    console.log('witamy');
    console.log(this.registerForm.get('email'));
    this.userService.register(this.registerForm.value).subscribe(
      result => {
        this.alert.alertSuccess('Utworzono konto w systemie. Zaloguj się');
      },
      error => {
        this.alert.alertError('Nie można utworzyć konta');
    });
  }
}
