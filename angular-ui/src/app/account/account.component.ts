import {Component, OnInit} from '@angular/core';
import {AccountService} from '../services/account/account.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnInit{
  accounts: any;

  constructor(private service: AccountService) {
  }

  ngOnInit(): void {
    this.service.getAccounts().subscribe(
      {
        next: data => {
          this.accounts = data
        },
        error: err => {
          console.log(err)
        },
      }
    );
  }
}
