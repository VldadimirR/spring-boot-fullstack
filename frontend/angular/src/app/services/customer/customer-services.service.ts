import { Injectable } from '@angular/core';
import {AuthenticationRequest} from "../../models/authentication-request";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {CustomerDTO} from "../../models/custoner-dto";

@Injectable({
  providedIn: 'root'
})
export class CustomerServicesService {

  constructor(
    private http: HttpClient
  ) { }


  findAll(): Observable<CustomerDTO[]> {
    return this.http.get<CustomerDTO[]>('http://localhost:8080/api/v1/customers');
  }
}
