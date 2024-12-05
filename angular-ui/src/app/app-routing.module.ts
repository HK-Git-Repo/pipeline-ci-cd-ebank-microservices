import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomerComponent} from './customer/customer.component';
import {AppComponent} from './app.component';
import {AccountComponent} from './account/account.component';
import {HomeComponent} from './home/home.component';

const routes: Routes = [
  {  path :'customers',  component: CustomerComponent  },
  {  path :'accounts',   component: AccountComponent  },
  { path : '', component: HomeComponent, pathMatch:"full" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
