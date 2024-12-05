import { Component } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'angular-ui';
  constructor(private router: Router) {
  }

  isActive = (link: string) : boolean => this.router.url == link;
}
