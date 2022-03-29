import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const apiServerUrl = 'http://localhost:8080/api/auth';
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(username:string, password:string): Observable<any>{
    return this.http.post(apiServerUrl + '/login', {
      username,
      password
    }, httpOptions);
  }

  register(username:string, email:string, password:string): Observable<any>{
    return this.http.post(apiServerUrl + '/register', {
      username,
      email,
      password
    }, httpOptions);
  }
}
