import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthenticationRequest} from '../../models/authentication-request';
import {Observable} from 'rxjs';
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly authUrl = `${environment.api.baseUrl}/${environment.api.authUrl}`;

  constructor(
    private http: HttpClient
  ) {
  }

  login(authRequest: AuthenticationRequest): Observable<AuthenticatorResponse> {
    return this.http.post<AuthenticatorResponse>(this.authUrl, authRequest);
  }
}
