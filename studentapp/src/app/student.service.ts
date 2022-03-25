import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { Student } from './student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiServerUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  public getStudents(): Observable<Student[]>{
    return this.http.get<Student[]>(this.apiServerUrl+'/api/student');
  }

  public addStudents(formData: FormData): Observable<Student>{
    return this.http.post<any>(this.apiServerUrl+'/api/new',formData);
  }

  public updateStudents(student: Student): Observable<Student>{
    return this.http.put<Student>(this.apiServerUrl+'/api/update',student);
  }

  public deleteStudents(studentId: number): Observable<void>{
    return this.http.delete<void>(this.apiServerUrl+'/api/del/'+studentId);
  }
}
